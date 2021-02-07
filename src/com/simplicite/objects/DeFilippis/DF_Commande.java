package com.simplicite.objects.DeFilippis;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import java.util.HashMap;
import com.simplicite.util.AppLog;
import com.simplicite.util.Globals;
import com.simplicite.util.Message;
import com.simplicite.util.Tool;
import com.simplicite.util.exceptions.APIException;
import com.simplicite.util.tools.HTMLTool;
import com.simplicite.util.tools.TrelloTool;

import java.io.ByteArrayOutputStream;
import com.simplicite.util.PrintTemplate;
import org.json.JSONObject;

import java.util.Date;
import java.text.SimpleDateFormat;  

import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;


import com.simplicite.webapp.web.BootstrapWebPage;
import com.simplicite.util.tools.PDFTool;
/**
 * Business object DF_Commande
 */
public class DF_Commande extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	private TrelloTool tt = null;
	private JSONObject settings = null;

	/**
	 * Valorisation du champs Montant Commande à chaque fois où on enregistre une nouvelle valeur de ligne commande 
	 */
	@Override
	public void initCopy() {
		ObjectDB lc = getGrant().getTmpObject("DF_ligne_commande");
		synchronized(lc){
			lc.resetFilters();
			lc.setFieldFilter("DF_ligne_commande_DF_Commande_id",this.getRowId());
			
			
			for(String[] lce : lc.search()){
				lc.setFieldValue("defiLigneCommandeTrelloId", "aa");
				lc.validate();
				lc.save();
	}
			
		}
	}
	
	@Override
	public void initUpdate() {
		// Création Objet temporaire ligne commande
		ObjectDB o = getGrant().getTmpObject("DF_ligne_commande");
		o.resetFilters();
		// filtrer sur les lignes commandes liées à notre commande
		o.getField("DF_ligne_commande_DF_Commande_id").setFilter(getRowId());
		
		List<String[]> rows = o.search(false);
		if (rows.size() > 0){
			// Itérer sur toutes les lignes de commandes et sommer la valeur de Prix Total 
			double c = o.getCount();
			double t = Double.parseDouble(o.getField("defiLigneCommandePrixTotalEXW").getListOperatorValue());
			double poids_total = Double.parseDouble(o.getField("defiLigneCommandePoidsTotal").getListOperatorValue());
			double qte = Double.parseDouble(o.getField("defiLigneCommandeQuantite").getListOperatorValue());
			double ral = Double.parseDouble(o.getField("defiLigneCommandeQteLivr").getListOperatorValue());
			
			setFieldValue("defiCommandeMontantHT", t);
			setFieldValue("defiCommandePoidsTotal", poids_total);
			setFieldValue("defiCommandeNombreCamions", poids_total / 24 );
			setFieldValue("defiCommandeAvcmt", 1-(ral/qte));
			save();
			
		}
		
			
	}

	/*
	@Override
	public String preUpdate() {
		
		// Création Objet temporaire ligne commande
		ObjectDB o = getGrant().getTmpObject("DF_ligne_commande");
		o.resetFilters();
		// filtrer sur les lignes commandes liées à notre commande
		o.getField("DF_ligne_commande_DF_Commande_id").setFilter(getRowId());
		
		List<String[]> rows = o.search(false);
		if (rows.size() > 0){
			// Itérer sur toutes les lignes de commandes et sommer la valeur de Prix Total 
			double c = o.getCount();
			
			double qte = Double.parseDouble(o.getField("defiLigneCommandeQuantite").getListOperatorValue());
			double ral = Double.parseDouble(o.getField("defiLigneCommandeQteLivr").getListOperatorValue());

			setFieldValue("defiCommandeAvcmt", 1-(ral/qte));
			save();
		
	}
		return null;
	}*/
	
	
	
	/**
	 * Action - Création cartes Trello 
	 */
	
	
	// Enregistrement du webhook après chargement des valeurs dans la base de données 

	@Override
	public void postLoad() {
		AppLog.info(getClass(), "postLoad11111", "Instance11111: " + getInstanceName(), getGrant());
		if (!getInstanceName().startsWith("webhook_")) {
			if (getInstanceName().equals("panel_ajax_DF_Livraison_DF_Livraison_DF_Plan_Livraison_id")){
				AppLog.info(getClass(), "postLoad11111", "On a un panel_ajax_DF_Livraison_DF_Livraison_DF_Plan_Livraison_id: " + getInstanceName(), getGrant());
			}
			else {
				tt = new TrelloTool(getGrant());
				AppLog.info(getClass(), "postLoad", "Trello tool API key: " + tt.getKey(), getGrant());
				settings = getGrant().getJSONObjectParameter("TRELLO_CARDEX_SETTINGS");
				AppLog.info(getClass(), "postLoad", "Settings: " + settings.toString(2), getGrant());
				String contextURL = getGrant().getContextURL();
				if (!Tool.isEmpty(contextURL)) {
					String webhookURL = contextURL + HTMLTool.getPublicExternalObjectURL("WebhookLivraisonTrello");
					try {
						String webhookId = tt.registerWebhook(settings.getString("boardId"), webhookURL, "Webhook for " + Globals.getPlatformName());
						AppLog.info(getClass(), "postLoad", "Registered webhook: " + webhookId, getGrant());
					} catch (APIException e) {
						AppLog.error(getClass(), "postLoad", "Unable to register the webhook: " + webhookURL, e, getGrant());
					}
				
			}
			
		}
	}
	}



	// Méthode exécutée lors de mise à jour des cartes
	
	public String updateCard(){
		if (tt == null) return null;
		ObjectDB lc = getGrant().getTmpObject("DF_ligne_commande");
		try {
			/*synchronized(lc){
				lc.resetFilters();
				lc.getField("DF_ligne_commande_DF_Commande_id").setFilter(getRowId());
				long c = lc.getCount();
		    		
				for(String[] lce : lc.search()){
                    lc.setValues(lce);*/
                    
					String id = lc.getFieldValue("defiLigneCommandeTrelloId");
					JSONObject card = tt.getCard(id);
					
					int index_cat_prix = lc.getField("defiLigneCommandeCatPrix").getList().getItemIndex(lc.getFieldValue("defiLigneCommandeCatPrix"),false);
					String cat_prix = lc.getField("defiLigneCommandeCatPrix").getList().getValue(index_cat_prix);
					
					if (cat_prix.equals("Pierre")){
						// Récupérer les 7 premières lettres du libellé de l'affaire
						String int_aff = getFieldValue("DF_Commande_DF_Affaire_id.defiAfrLibelleChantier");
						int_aff.replace(" " , "");
						String firstCharsIntitule ="";
						
						if (int_aff.length()>=7){
							firstCharsIntitule = int_aff.substring(0, 7);
						} else{
							firstCharsIntitule = int_aff;
						}
						
					
						String fourns = lc.getFieldValue("defiLigneCommandeNmFourn");
						//fourns.replace(" " , "");
						//String firstCharsFourns = fourns.substring(0, 3);
					
						double poids_total = lc.getField("defiLigneCommandePoidsTotal").getDouble();
					
						double quantite_lc = lc.getField("defiLigneCommandeQuantite").getDouble();
						// int tonnage_carte = (int)Math.round(poids_total);
						String tonnage_carte = String.valueOf(poids_total);
						tonnage_carte = tonnage_carte.replace(".",",");
					// Nom de la carte
						card.put("name",  (firstCharsIntitule+"."+getFieldValue("defiCommandeIntituleCommande")+"."+lc.getFieldValue("defiLigneCommandeReferenceProduit")+"."+ tonnage_carte).toUpperCase());
					// Description de la carte
						card.put("desc","\n**Numéro de commande**: "+getFieldValue("defiCommandeNumero")+"\n**Titre Affaire / Nom Affaire**: "+int_aff+"\n**Date Livraison confirmée**: "+getFieldValue("defiCommandeDatePremierCamion")+"\n"+"\n**Contact Déchargement Privilégié**: "+getFieldValue("defiCommandeContactLivraison")+"\n"+"\n**Contact En Cas De Problème**: "+"\n"+"\n**Quantité Initiale**: "+ lc.getFieldValue("defiLigneCommandeQuantite"));
						
					// Date limie de la carte 
						card.put("due", getFieldValue("defiCommandeDatePremierCamion"));
					    tt.updateCard(id, card);
					
									
					//Mise à jour les informations custom fields
					
                        tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Adresse"),new JSONObject().put("value",new JSONObject().put("text",getFieldValue("defiCommandeAdresseLivraison"))));
                        tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Quantité"),new JSONObject().put("value",new JSONObject().put("number",lc.getFieldValue("defiLigneCommandeQuantite"))));
                        tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Référence Produit"),new JSONObject().put("value",new JSONObject().put("text",lc.getFieldValue("defiLigneCommandeReferenceProduit"))));	
                        tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Poids Unitaire"),new JSONObject().put("value",new JSONObject().put("number",lc.getFieldValue("defiLigneCommandePoidsUnitaire"))));
                        tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Ville"),new JSONObject().put("value",new JSONObject().put("text",getFieldValue("DF_Commande_DF_Affaire_id.defiAfrLieuAffaire"))));
                        tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Id Ligne de commande"),new JSONObject().put("value",new JSONObject().put("text",lc.getFieldValue("defiLigneCommandeId"))));
                        tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Fournisseur"),new JSONObject().put("value",new JSONObject().put("text",fourns)));
                        tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Trigramme Suiveur"),new JSONObject().put("value",new JSONObject().put("text",getFieldValue("DF_Commande_DF_utilisateur_interne_id.defiUsrTrigramme"))));
                        lc.save();
                        lc.validate();
                        
                    //}
                }
                    
					save();
					validate();
			//}
			
			
			return Message.formatSimpleInfo("Trello card updated");
		} catch (APIException e) { // Prevents deletion if card creation fails
			AppLog.error(getClass(), "postUpdate", null, e, getGrant());
			return Message.formatSimpleError("Card update error: " + e.getMessage());
		}
	}

	// Méthode de création des cartes à partir d'une commande
	public String createCard(){
		if (tt == null) return null;
		
		// Création d'objet temporaire : ligne de commande
		ObjectDB lc = getGrant().getTmpObject("DF_ligne_commande");
		try {
		    JSONObject card = new JSONObject();
		    /*synchronized(lc){
				// Filtrer sur les lignes de commandes correspondantes à la commande qui lance l'action
				lc.resetFilters();
				lc.getField("DF_ligne_commande_DF_Commande_id").setFilter(getRowId());
				long c = lc.getCount();
		    		
				for(String[] lce : lc.search()){
					lc.setValues(lce);
					// Mettre condition sur export trello si produit est de catégorie pierre*/
					int index_cat_prix = lc.getField("defiLigneCommandeCatPrix").getList().getItemIndex(lc.getFieldValue("defiLigneCommandeCatPrix"),false);
					String cat_prix = lc.getField("defiLigneCommandeCatPrix").getList().getValue(index_cat_prix);
					
					if (cat_prix.equals("Pierre")){
						// Récupérer les 7 premières lettres du libellé de l'affaire
						String int_aff = getFieldValue("DF_Commande_DF_Affaire_id.defiAfrLibelleChantier");
						int_aff.replace(" " , "");
						String firstCharsIntitule ="";
						
						if (int_aff.length()>=7){
							firstCharsIntitule = int_aff.substring(0, 7);
						} else{
							firstCharsIntitule = int_aff;
						}
						
					
						String fourns = lc.getFieldValue("defiLigneCommandeNmFourn");
						//fourns.replace(" " , "");
						//String firstCharsFourns = fourns.substring(0, 3);
					
						double poids_total = lc.getField("defiLigneCommandePoidsTotal").getDouble();
					
						double quantite_lc = lc.getField("defiLigneCommandeQuantite").getDouble();
						// int tonnage_carte = (int)Math.round(poids_total);
						String tonnage_carte = String.valueOf(poids_total);
						tonnage_carte = tonnage_carte.replace(".",",");
					// Nom de la carte
						card.put("name",  (firstCharsIntitule+"."+getFieldValue("defiCommandeIntituleCommande")+"."+lc.getFieldValue("defiLigneCommandeReferenceProduit")+"."+ tonnage_carte).toUpperCase());
					// Description de la carte
						card.put("desc","\n**Numéro de commande**: "+getFieldValue("defiCommandeNumero")+"\n**Titre Affaire / Nom Affaire**: "+int_aff+"\n**Date Livraison confirmée**: "+getFieldValue("defiCommandeDatePremierCamion")+"\n"+"\n**Contact Déchargement Privilégié**: "+getFieldValue("defiCommandeContactLivraison")+"\n"+"\n**Contact En Cas De Problème**: "+"\n"+"\n**Quantité Initiale**: "+ lc.getFieldValue("defiLigneCommandeQuantite"));
						
					// Date limie de la carte 
						card.put("due", getFieldValue("defiCommandeDatePremierCamion"));
					// Emplacement de la carte
						card = tt.addCard(getIDList(getFieldValue("defiCommandeStatut")), card);
									
					//Mise à jour les informations custom fields
					
						tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Numéro de commande"),new JSONObject().put("value",new JSONObject().put("text",getFieldValue("defiCommandeNumero"))));
						tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Adresse"),new JSONObject().put("value",new JSONObject().put("text",getFieldValue("defiCommandeAdresseLivraison"))));
						tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Quantité"),new JSONObject().put("value",new JSONObject().put("number",lc.getFieldValue("defiLigneCommandeQuantite"))));
		    			tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Référence Produit"),new JSONObject().put("value",new JSONObject().put("text",lc.getFieldValue("defiLigneCommandeReferenceProduit"))));	
		    			tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Poids Unitaire"),new JSONObject().put("value",new JSONObject().put("number",lc.getFieldValue("defiLigneCommandePoidsUnitaire"))));
		    			tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Ville"),new JSONObject().put("value",new JSONObject().put("text",getFieldValue("DF_Commande_DF_Affaire_id.defiAfrLieuAffaire"))));
		    			tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Id Ligne de commande"),new JSONObject().put("value",new JSONObject().put("text",lc.getFieldValue("defiLigneCommandeId"))));
		    			tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Fournisseur"),new JSONObject().put("value",new JSONObject().put("text",fourns)));
		    			tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Trigramme Suiveur"),new JSONObject().put("value",new JSONObject().put("text",getFieldValue("DF_Commande_DF_utilisateur_interne_id.defiUsrTrigramme"))));
		    			
		    			lc.setFieldValue("defiLigneCommandeTrelloId", card.getString("id"));
		    			lc.save();
						lc.validate();
		    			
					
		    			}
		    			else
		    				;

					
					save();
					validate();
					
					
				return Message.formatSimpleInfo("Trello card created");					
					
		} catch (APIException e) { // Prevents creation if card creation fails
			AppLog.error(getClass(), "preCreate", null, e, getGrant());
			return Message.formatSimpleError("Card creation error: " + e.getMessage());
			}
			
			}
		


	//Fonction appelée par le bouton dans Livraison
	public String commandeTrello(){
		if (tt == null) return null;
		
		
		ObjectDB lc = getGrant().getTmpObject("DF_ligne_commande");
		JSONObject card = new JSONObject();
		synchronized(lc){
			// Filtrer sur les lignes de commandes correspondantes à la commande qui lance l'action
			lc.resetFilters();
			lc.getField("DF_ligne_commande_DF_Commande_id").setFilter(getRowId());
			long c = lc.getCount();
		    		
			for(String[] lce : lc.search()){
				lc.setValues(lce);
					
				String id = lc.getFieldValue("defiLigneCommandeTrelloId");
				if (id.length()>0)
					updateCard();
				
				else
					createCard();
					
					
			}
		}
		return Message.formatSimpleInfo("Trello card created");	
	}
	
	//Récupérer l'id de la colonne dans Trello avec le statut de la livraison
	public String getIDList(String statutLivraison){
		String id = settings.getString("defaultListId");
		try {
			JSONArray lists = tt.getBoardLists(settings.getString("boardId"));
			for (int i = 0; i < lists.length(); i++) {
			    JSONObject list = lists.getJSONObject(i);
			    if (list.getString("name").startsWith(statutLivraison))
			    	id = list.getString("id");
			}
		return id;
		} catch (APIException e) { // Prevents deletion if card creation fails
			AppLog.error(getClass(), "getIDList", null, e, getGrant());
			return null;//Message.formatSimpleError("Card deletion error: " + e.getMessage());
		}
	

	}
	
	// Récupérer Id du customField avec le nom du customfield
	public String getIDCustomField(String customFieldName){
		String id = null;
		try {
			String t = tt.call("/boards/"+settings.getString("boardId")+"/customFields","get","").toString();
			JSONArray mJSONArray = new JSONArray(t);
			id = searchJSONArray("name",customFieldName,"id",mJSONArray);
			AppLog.info(getClass(), "getIDCustomField", id, getGrant());
		} catch (APIException e) { // Prevents deletion if card creation fails
			AppLog.error(getClass(), "getIDCustomField", null, e, getGrant());
			return null;//Message.formatSimpleError("Card deletion error: " + e.getMessage());
		}
		return id;
	}
	
	// Récupérer Id de l'attachement avec le nom de l'attachement	
	public String getIDAttachment(String AttachmentName){
		String id_a = null;
		try {
			String t_a = tt.call("/cards/"+settings.getString("cardId")+"/attachments","get","").toString();
			JSONArray mJSONArray = new JSONArray(t_a);
			id_a = searchJSONArray("name",AttachmentName,"id",mJSONArray);
			AppLog.info(getClass(), "getIDAttachment", id_a, getGrant());
		} catch (APIException e) { // Prevents deletion if card creation fails
			AppLog.error(getClass(), "getIDAttachment", null, e, getGrant());
			return null;//Message.formatSimpleError("Card deletion error: " + e.getMessage());
		}
		return id_a;
	}

	// set attachement 
	public Object setAttachment(java.lang.String cardId, java.lang.String attachmentId) throws APIException{
		try{
		//	String s_a = tt.call();
		tt.call("/cards/"+cardId+"/attachments?url=https://trello.com/c/vUfTBdj3/142-1234","post","");
			
		} catch (APIException e){
			AppLog.error(getClass(), "setAttachement", null, e, getGrant());
			return null;
		}
		return null;
		
	}
		

	// recherche item dans la table des JSONArray l'item qui contient "tagName"=valueName et retourne la valeur de l'element "tagId"
	public String searchJSONArray(String tagName,String valueName, String tagId, JSONArray mJSONArray){
		Boolean found=false;
		int length = mJSONArray.length();
		int i = 0;
		String id = null;
		while(found==false && i<length){
            JSONObject o = mJSONArray.getJSONObject(i);
			if (o.getString(tagName).equals(valueName)){
				id = o.getString(tagId); 
				found = true;
			}
			i=i+1;
        }
        return id;
	}
	



	

	

	@Override
	public String preDelete() {
		if (tt == null) return null;
		try {
			tt.deleteCard(getFieldValue("defiCommandeTrelloId"));
			return null;
		} catch (APIException e) { // Prevents deletion if card creation fails
			AppLog.error(getClass(), "postLoad", null, e, getGrant());
			return null;
		}
	}
	
	////////////////////////// Print ARC //////////////////////////////////////////////
	public String pubARC(){
		BootstrapWebPage wp_arc = new BootstrapWebPage(
			HTMLTool.getRoot(), 
			"Webpage publication pattern example", 
			true
		);
		
		
		// Commande
		ObjectDB c = getGrant().getTmpObject("DF_Commande");
		c.setFieldFilter("row_id",getRowId());

		// Ligne COmmande
		ObjectDB lc = getGrant().getTmpObject("DF_ligne_commande");
		lc.resetFilters();
		lc.setFieldFilter("DF_ligne_commande_DF_Commande_id",getRowId());
		
		
		// Suiveur
		ObjectDB u = getGrant().getTmpObject("User");
		u.resetFilters();
		u.setFieldFilter("row_id",getFieldValue("DF_Commande_DF_utilisateur_interne_id"));
		
		
		// Client 	
		ObjectDB client = getGrant().getTmpObject("DF_Client");
		client.resetFilters();
		client.setFieldFilter("row_id",getFieldValue("DF_Commande_DF_Client_id"));
		
		// Contact Client
		ObjectDB contact_client = getGrant().getTmpObject("DF_Contact");
		contact_client.resetFilters();
		contact_client.setFieldFilter("row_id",getFieldValue("DF_Commande_DF_Contact_id"));
				
		
		List<String[]> rows_l = lc.search(false);
		if (rows_l.size() > 0){
			
			wp_arc.append(MustacheTool.apply(
			c,
			"DF_ARC_HTML", 
			"{'rows':"+c.toJSON(c.search(), null, false, false)+
			",'rows_l':"+lc.toJSON(rows_l, null, false, false)+
			",'rows_u':"+u.toJSON(u.search(), null, false, false)+
			",'rows_client':"+client.toJSON(client.search(), null, false, false)+
			",'contact_client':"+contact_client.toJSON(contact_client.search(), null, false, false)+
			"}"
			));
			
			
					
			
		}
		
		return wp_arc.getHTML();
	
	}
	
	public byte[] pubPdfARC(PrintTemplate pt){
		String url = "http://wkhtml2pdf/";
		String user = null;
		String password = null;
	
		
		JSONObject postData = new JSONObject();
		postData.put("contents", Tool.toBase64(pubARC()));
		
		String FileName = this.getFieldValue("defiCommandeNumero")+"-"+this.getFieldValue("defiCommandeIntituleCommande").replaceAll("\\s","")+".pdf";
		
		pt.setFilename(FileName);

		String[] headers = {"Content-Type:application/json"};
		String encoding = Globals.BINARY;
		byte[] pdf = null;
		
		try{
			pdf = Tool.readUrlAsByteArray(url, user, password, postData.toString(), headers, encoding);
		}catch(Exception e){
			AppLog.error(getClass(), "pubPdf", "------------", e, getGrant());
		}
		return pdf;
	}
	
	// Méthode pour historiser un Devis
	public String generateARC() {
		ObjectDB hst = getGrant().getTmpObject("DF_Hist_Docs");

		try {
			synchronized(hst){
				hst.resetFilters();
					SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy-HH");  
    				Date date = new Date();  
    				PrintTemplate ptARC = getPrintTemplate("ARC-PDF");
					hst.create();	
					hst.getField("defiHstDocsDevis").setDocument(hst, "ARC-"+this.getFieldValue("defiCommandeIntituleCommande")+"-"+formatter.format(date).toString()+".pdf", this.pubPdfARC(ptARC));
					hst.setFieldValue("DF_Hist_Docs_DF_Commande_id",getRowId());
					hst.setFieldValue("defiHstDocsDateEmission",date);
					hst.setFieldValue("defiHstTitre","ARC - "+this.getFieldValue("defiCommandeNumero"));
					
					ObjectField file = hst.getField("defiHstDocsDevis");
					
					hst.save();
			
				
				
			}
			return Message.formatSimpleInfo("Fichier Historisé");
		}	
		catch(Exception e) {
		    AppLog.error(getClass(), "generateFile", "error...", e, getGrant());
		    return Message.formatSimpleError("Error...");
		}
	}
	
	
	////////////////////////// Print Synthèse Livraisons //////////////////////////////////////////////
	public String pubSynth(){
		BootstrapWebPage wp_arc = new BootstrapWebPage(
			HTMLTool.getRoot(), 
			"Webpage publication pattern example", 
			true
		);
		
		
		// Commande
		ObjectDB c = getGrant().getTmpObject("DF_Commande");
		c.setFieldFilter("row_id",getRowId());

		// Ligne Ccmmande
		ObjectDB lc = getGrant().getTmpObject("DF_ligne_commande");
		lc.resetFilters();
		lc.setFieldFilter("DF_ligne_commande_DF_Commande_id",getRowId());
		
		// Livraisons
		ObjectDB liv = getGrant().getTmpObject("DF_Livraison");
		liv.resetFilters();
		liv.setFieldFilter("DF_livraison_DF_Commande_id",getRowId());
		
		
		// Suiveur
		ObjectDB u = getGrant().getTmpObject("User");
		u.resetFilters();
		u.setFieldFilter("row_id",getFieldValue("DF_Commande_DF_utilisateur_interne_id"));
		
		
		// Client 	
		ObjectDB client = getGrant().getTmpObject("DF_Client");
		client.resetFilters();
		client.setFieldFilter("row_id",getFieldValue("DF_Commande_DF_Client_id"));
		
		// Contact Client
		ObjectDB contact_client = getGrant().getTmpObject("DF_Contact");
		contact_client.resetFilters();
		contact_client.setFieldFilter("row_id",getFieldValue("DF_Commande_DF_Contact_id"));
				
		
		List<String[]> rows_l = lc.search(false);
		if (rows_l.size() > 0){
			
			wp_arc.append(MustacheTool.apply(
			c,
			"DF_SYNTH_HTML", 
			"{'rows':"+c.toJSON(c.search(), null, false, false)+
			",'rows_l':"+lc.toJSON(rows_l, null, false, false)+
			",'rows_u':"+u.toJSON(u.search(), null, false, false)+
			",'rows_liv':"+liv.toJSON(liv.search(), null, false, false)+
			",'rows_client':"+client.toJSON(client.search(), null, false, false)+
			",'contact_client':"+contact_client.toJSON(contact_client.search(), null, false, false)+
			"}"
			));
			
			
					
			
		}
		
		return wp_arc.getHTML();
	
	}
	
	public byte[] pubPdfSynth(PrintTemplate pt){
		String url = "http://wkhtml2pdf/";
		String user = null;
		String password = null;
	
		
		JSONObject postData = new JSONObject();
		postData.put("contents", Tool.toBase64(pubSynth()));
		
		String FileName = this.getFieldValue("defiCommandeNumero")+"-"+this.getFieldValue("defiCommandeIntituleCommande").replaceAll("\\s","")+".pdf";
		
		pt.setFilename(FileName);

		String[] headers = {"Content-Type:application/json"};
		String encoding = Globals.BINARY;
		byte[] pdf = null;
		
		try{
			pdf = Tool.readUrlAsByteArray(url, user, password, postData.toString(), headers, encoding);
		}catch(Exception e){
			AppLog.error(getClass(), "pubPdf", "------------", e, getGrant());
		}
		return pdf;
	}
	
	
		


}