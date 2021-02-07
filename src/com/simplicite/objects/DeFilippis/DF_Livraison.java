package com.simplicite.objects.DeFilippis;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.HashMap;
import com.simplicite.util.AppLog;
import com.simplicite.util.Globals;
import com.simplicite.util.Grant;
import com.simplicite.util.Message;
import com.simplicite.util.Tool;
import com.simplicite.util.exceptions.APIException;
import com.simplicite.util.tools.HTMLTool;
import com.simplicite.util.tools.TrelloTool;

import java.util.List;

import java.util.Date;
import java.text.SimpleDateFormat;  

import java.util.*;
import com.simplicite.util.ObjectDB;
import com.simplicite.webapp.web.BootstrapWebPage;
import com.simplicite.util.tools.*;
import com.simplicite.util.tools.HTMLTool; 

import com.simplicite.util.tools.PDFTool;
import com.simplicite.util.PrintTemplate; 

/**
 * Trello card business object example
 */
public class DF_Livraison extends ObjectDB {
	private static final long serialVersionUID = 1L;

	private TrelloTool tt = null;
	private JSONObject settings = null;
	


	@Override
	public void postLoad() {
		AppLog.info(getClass(), "postLoad11111", "Instance11111: " + getInstanceName(), getGrant());
		if (!getInstanceName().startsWith("webhook_")) {
			if (getInstanceName().equals("panel_ajax_DF_Livraison_DF_Livraison_DF_Plan_Livraison_id")){
				AppLog.info(getClass(), "postLoad11111", "On a un panel_ajax_DF_Livraison_DF_Livraison_DF_Plan_Livraison_id: " + getInstanceName(), getGrant());
			}
			else {
				tt = new TrelloTool(getGrant());
				//tt.addCustomField("","");
				tt.setDebug(true);
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

	@Override
	public String preCreate() {
	 String result = null;
	 //result = createCard();
	 return result;
	}

	@Override
	public String preUpdate() {
		String result = null;
		//result = updateCard();
		return result;
	}

	@Override
	public String preDelete() {
		if (tt == null) return null;
		try {
			tt.deleteCard(getFieldValue("df_livraison_trellocardid"));
			return null;
		} catch (APIException e) { // Prevents deletion if card creation fails
			AppLog.error(getClass(), "postLoad", null, e, getGrant());
			return null;//Message.formatSimpleError("Card deletion error: " + e.getMessage());
		}
	}
	
	
	public String createDesc(){
		String desc = "";
		desc += "\n**Ligne de commande ID**: "+getFieldValue("df_livraison_id_ligne_commande");
		desc += "\n**Quantité**: "+getFieldValue("df_livraison_quantite_chargee");

		desc += "\n**Date Livraison Estimée**: "+getFieldValue("df_livraison_date_livraison_estimee");
		desc += "\n";
		desc += "\n**Num BL Client**: "+getFieldValue("df_livraison_num_bl_client");
		desc += "\n**Num BL Fournisseur**: "+getFieldValue("df_livraison_num_bl_fournisseur");
		desc += "\n";
		desc += "\n**Adresse Enlevement**: "+getFieldValue("df_livraison_adresse_enlevement");
		desc += "\n**Adresse Livraison**: "+getFieldValue("df_livraison_adresse");
		desc += "\n**Adresse De Livraison Confirmée**: "+getFieldValue("df_livraison_adresse_de_livraison_confirmee");
		desc += "\n";
		desc += "\n**Nom Transporteur**: "+getFieldValue("df_livraison_nom_transporteur");
		desc += "\n**Contact Transporteur**: "+getFieldValue("df_livraison_contact_transporteur");
		desc += "\n**Num ZEEPO Transporteur**: "+getFieldValue("df_livraison_num_zeepo_transporteur");
		desc += "\n";
		desc += "\n**Contact Déchargement Privilégié**: "+getFieldValue("df_livraison_contact_dechargement_privilegie");
		desc += "\n**Contact En Cas De Problème**: "+getFieldValue("df_livraison_contact_en_cas_de_probleme");
		return desc;
	}
	
	
	
	
	public String updateCard(){
		if (tt == null) return null;
		try {
			String idCustomFieldQuantite=getIDCustomField("Test");
			JSONObject data = new JSONObject();
			data.put("idModel", "5e4a8cb187a7fe737ba78949");
			JSONObject value = new JSONObject();
			value.put("text","Hello, world!1111111111111");
			data.put("value", value);
			

			tt.updateCustomField(idCustomFieldQuantite,data);
			String id = getFieldValue("df_livraison_trellocardid");
			JSONObject card = tt.getCard(id);
			card.put("name", getFieldValue("df_livraison_id"));
			card.put("desc", createDesc());
			card.put("due", getFieldValue("df_livraison_date_livraison_estimee"));
			card.put("idList",getIDList(getFieldValue("df_livraison_statut")));
			AppLog.info(getClass(), "DangLog", getIDList(getFieldValue("df_livraison_statut")), getGrant());
			tt.updateCard(id, card);
			AppLog.info(getClass(), "preUpdate", card.toString(2), getGrant());
			return Message.formatSimpleInfo("Trello card updated");
		} catch (APIException e) { // Prevents deletion if card creation fails
			AppLog.error(getClass(), "postUpdate", null, e, getGrant());
			return Message.formatSimpleError("Card update error: " + e.getMessage());
		}
	}
	public String createCard(){
		if (tt == null) return null;
		try {
			JSONObject card = new JSONObject();
			card.put("name", getFieldValue("df_livraison_id"));
			card.put("desc", createDesc());
			card.put("due", getFieldValue("df_livraison_date_livraison_estimee"));
			card = tt.addCard(getIDList(getFieldValue("df_livraison_statut")), card);
			AppLog.info(getClass(), "preCreate", card.toString(2), getGrant());
			setFieldValue("df_livraison_trellocardid", card.getString("id"));
			save();
			validate();
			return Message.formatSimpleInfo("Trello card created");
		} catch (APIException e) { // Prevents creation if card creation fails
			AppLog.error(getClass(), "preCreate", null, e, getGrant());
			return Message.formatSimpleError("Card creation error: " + e.getMessage());
		}
	}
	
	public String synchroTrello(){
		if (tt == null) return null;
		String id = getFieldValue("df_livraison_trellocardid");
		if (id.length()>0)
			return updateCard();
		else 
			return createCard();
	}
	
	public String getIDList(String statutLivraison){
		String id = settings.getString("defaultListId");
		try {
			JSONArray lists = tt.getBoardLists(settings.getString("boardId"));
			AppLog.info(getClass(), "DangLog:", lists.toString(2), getGrant());
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
	
	public String getIDCustomField(String customFieldName){
		String id = null;
		try {
			String t = tt.call("/boards/5e4acb149a3b017c38138715/customFields","get","").toString();
			JSONArray mJSONArray = new JSONArray(t);
			id = searchJSONArray("name",customFieldName,"id",mJSONArray);
			AppLog.info(getClass(), "getIDCustomField", id, getGrant());
		} catch (APIException e) { // Prevents deletion if card creation fails
			AppLog.error(getClass(), "getIDCustomField", null, e, getGrant());
			return null;//Message.formatSimpleError("Card deletion error: " + e.getMessage());
		}
		return id;
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
	public void initUpdate() {
		
		ObjectDB q = getGrant().getTmpObject("DF_Quantite");
		q.resetFilters();
		q.setFieldFilter("DF_Quantite_DF_Livraison_id", getRowId());
		
		
		List<String[]> rows = q.search(false);
		if (rows.size() > 0){
			double c = q.getCount();
			double t = Double.parseDouble(q.getField("defiQuantiteMontant").getListOperatorValue());
			double p = Double.parseDouble(q.getField("defiQuantiteTonnage").getListOperatorValue());
			setFieldValue("defiLivraisonTotalHT", t);
			setFieldValue("df_livraison_quantite_chargee",p);
			save();
		}	
	}
	
		////////////////////////// Print BL //////////////////////////////////////////////
	public String pubBL(){
		BootstrapWebPage wp = new BootstrapWebPage(
			HTMLTool.getRoot(), 
			"Webpage publication pattern example", 
			true
		);
		
		
		// Livraison
		ObjectDB bl = getGrant().getTmpObject("DF_Livraison");
		bl.setFieldFilter("row_id",getRowId());

		// Quantite
		ObjectDB q = Grant.getSystemAdmin().getObject("DF_Quantite","DF_Quantite");
		q.resetFilters();
		q.setFieldFilter("DF_Quantite_DF_Livraison_id",getRowId());
	
		
		ObjectDB lignescommandes = Grant.getSystemAdmin().getObject("DF_ligne_commande","DF_ligne_commande");

		ObjectDB contact_client = getGrant().getTmpObject("DF_Contact");
		ObjectDB client = getGrant().getTmpObject("DF_Client");
		ObjectDB u = getGrant().getTmpObject("User");
		// Commande
		ObjectDB commande = getGrant().getTmpObject("DF_Commande");
		//List<String[]> commande_search = new ArrayList<String[]>();
		synchronized(commande){
			commande.resetFilters();
			commande.setFieldFilter("row_id",getFieldValue("DF_Livraison_DF_Commande_id"));
			AppLog.info(getClass(), "Suiveur Livraison1", getFieldValue("DF_Livraison_DF_Commande_id"), getGrant());
			
			for(String[] ce : commande.search()){
				commande.setValues(ce);
				AppLog.info(getClass(), "Suiveur Livraison", commande.getFieldValue("DF_Commande_DF_utilisateur_interne_id.defiUsrNC"), getGrant());
				AppLog.info(getClass(), "hhmldkqmdslfksd-------77",commande.toString(),getGrant());
				
				// Suiveur
				
				u.resetFilters();
				u.setFieldFilter("row_id",commande.getFieldValue("DF_Commande_DF_utilisateur_interne_id"));		
		
		
				// Client 	
				
				client.resetFilters();
				client.setFieldFilter("row_id",commande.getFieldValue("DF_Commande_DF_Client_id"));
				AppLog.info(getClass(), "hhmldkqmdslfksd-------77",commande.getFieldValue("DF_Commande_DF_Client_id"),getGrant());
		
				// Contact Client
				
				contact_client.resetFilters();
				contact_client.setFieldFilter("row_id",commande.getFieldValue("DF_Commande_DF_Contact_id"));
				
			//	commande_search.addAll(commande.search());
				
			}
		}
		wp.append(MustacheTool.apply(
								this,
								"DF_BL_HTML", 
								"{'rows':"+bl.toJSON(bl.search(), null, false, false)+
								",'rows_l':"+q.toJSON(q.search(), null, false, false)+
								",'rows_commande':"+commande.toJSON(commande.search(), null, false, false)+								
								",'rows_u':"+u.toJSON(u.search(), null, false, false)+								
								",'rows_client':"+client.toJSON(client.search(), null, false, false)+
								",'contact_client':"+contact_client.toJSON(contact_client.search(), null, false, false)+								
							    "}"
								));
		
		return wp.getHTML();
	}
	

	
	public byte[] pubPdfBL(PrintTemplate pt){
		String url = "http://wkhtml2pdf/";
		String user = null;
		String password = null;
	
		
		JSONObject postData = new JSONObject();
		postData.put("contents", Tool.toBase64(pubBL()));
		
		
		ObjectDB commande = getGrant().getTmpObject("DF_Commande");
	
		synchronized(commande){
			commande.resetFilters();
			commande.setFieldFilter("row_id",this.getFieldValue("DF_Livraison_DF_Commande_id"));
						
			for(String[] cmde : commande.search()){
				commande.setValues(cmde);
				String numBl = this.getFieldValue("defiLivraisonNumBL");
				pt.setFilename(commande.getFieldValue("defiCommandeNumero")+"-"+this.getFieldValue("defiLivraisonNumBL")+".pdf");
				}
			}
		
		
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
	
	// Méthode pour historiser un BL
	public String generateBL() {
		ObjectDB hst = getGrant().getTmpObject("DF_Hist_Docs");

		try {
			synchronized(hst){
				hst.resetFilters();
				
					SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy-HH");  
    				Date date = new Date();  
					PrintTemplate ptLV = getPrintTemplate("BL-PDF");

					hst.create();	
					hst.getField("defiHstDocsDevis").setDocument(hst, "BL"+formatter.format(date).toString()+".pdf", this.pubPdfBL(ptLV));
					hst.setFieldValue("DF_Hist_Docs_DF_Commande_id",this.getFieldValue("DF_Livraison_DF_Commande_id"));
					hst.setFieldValue("defiHstDocsDateEmission",date);
					
					ObjectDB commande = getGrant().getTmpObject("DF_Commande");
	
					synchronized(commande){
						commande.resetFilters();
						commande.setFieldFilter("row_id",this.getFieldValue("DF_Livraison_DF_Commande_id"));
						
						for(String[] cmde : commande.search()){
							commande.setValues(cmde);
							hst.setFieldValue("defiHstTitre", "BL - "+commande.getFieldValue("defiCommandeNumero")+" - "+this.getFieldValue("defiLivraisonNumBL"));
						}
					}
					
					hst.save();
			
				
				
			}
			return Message.formatSimpleInfo("Fichier Historisé");
		}	
		catch(Exception e) {
		    AppLog.error(getClass(), "generateFile", "error...", e, getGrant());
		    return Message.formatSimpleError("Error...");
		}
	}
	
	
		////////////////////////// Print ODF //////////////////////////////////////////////
	public String pubODF(){
		
		
		BootstrapWebPage wp = new BootstrapWebPage(
			HTMLTool.getRoot(), 
			"Webpage publication pattern example", 
			true
		);
		
		

		DF_Livraison livraison = (DF_Livraison) getGrant().getTmpObject("DF_Livraison");
		livraison.resetValues();
		
		ObjectDB q = Grant.getSystemAdmin().getObject("DF_Quantite","DF_Quantite");
		
		ObjectDB commande_livraison = getGrant().getTmpObject("DF_Commande");
		
		ObjectDB client = getGrant().getTmpObject("DF_Client");
		ObjectDB contact_client = getGrant().getTmpObject("DF_Contact");
		ObjectDB u = getGrant().getTmpObject("User");

		List<String[]> q_search = new ArrayList<String[]>();
		List<String[]> livraison_search = new ArrayList<String[]>();
		
		
		double total_livraison =0;
		double prix_tva =0;
		double total_ttc =0;
		double somme = 0;
		for (String id: getSelectedIds()){
			
			synchronized(livraison){
				
				livraison.select(id);
				
				
				q.resetFilters();
				q.setFieldFilter("DF_Quantite_DF_Livraison_id",livraison.getRowId());
				//q.save();
				
				livraison.setFieldFilter("row_id", livraison.getRowId());
				
				commande_livraison.resetFilters();
				commande_livraison.setFieldFilter("row_id",livraison.getFieldValue("DF_Livraison_DF_Commande_id"));
				
				// Suiveur
				
				
				
				u.resetFilters();
				u.setFieldFilter("row_id",commande_livraison.getFieldValue("DF_Commande_DF_utilisateur_interne_id"));
				AppLog.info(getClass(), "cliiiiient", commande_livraison.getFieldValue("DF_Commande_DF_utilisateur_interne_id"), getGrant());
				client.resetFilters();
				
				
				// Contact Client 
				contact_client.resetFilters();
				contact_client.setFieldFilter("row_id",commande_livraison.getFieldValue("DF_Commande_DF_Contact_id"));	
				
				// Client
				client.resetFilters();
				client.setFieldFilter("row_id",commande_livraison.getFieldValue("DF_Commande_DF_Client_id"));
				

				livraison_search.addAll(livraison.search());
				q_search.addAll(q.search());
				
				List<Double>rows = new ArrayList<>();
				List<String> ids = getSelectedIds();
				
				
				if (!Tool.isEmpty(ids)) {
					for (int k = 0; k < ids.size(); k++)
						if (livraison.select(ids.get(k)))
							rows.add(livraison.getField("defiLivraisonTotalHT").getDouble(0));
				} else {
					rows.add(0.0);
				}
				
				total_livraison = rows.stream().mapToDouble(Double::doubleValue).sum();
				AppLog.info(getClass(), "rows", rows.toString(), getGrant());
		
				// calcul tva 
				prix_tva = (int)(Math.round(total_livraison*0.2 * 100))/100.0;
		
				// calcul total ttc
				total_ttc = prix_tva+total_livraison;
			}
		}
				wp.append(MustacheTool.apply(
						this,
						"DF_ODF_HTML", 
						"{'rows_l':"+q.toJSON(q_search, null, false, false)+
						",'bl':"+livraison.toJSON(livraison_search, null, false, false)+
						",'cl':"+commande_livraison.toJSON(commande_livraison.search(), null, false, false)+
						",'usr':"+u.toJSON(u.search(), null, false, false)+
						",'client':"+client.toJSON(client.search(), null, false, false)+
						",'contact_client':"+contact_client.toJSON(contact_client.search(), null, false, false)+
						",'total_ht':"+ "[{'total_ht_l':"+Double.toString(total_livraison)+"}]"+
						",'tva':"+ "[{'prix_tva':"+Double.toString(prix_tva)+"}]"+
						",'total_ttc':"+ "[{'total_ttc_l':"+Double.toString(total_ttc)+"}]"+
						"}"
				));
		
		livraison.setFieldValue("df_livraison_statut","6");
		livraison.save();
	    
		
		return wp.getHTML();
}
	
	public byte[] pubPdfODF(){
		String url = "http://wkhtml2pdf/";
		String user = null;
		String password = null;
	
		
		JSONObject postData = new JSONObject();
		postData.put("contents", Tool.toBase64(pubODF()));

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
	
	// Méthode pour imprimer un odf en action processus métier 
	public String pubODFWorkflow(){
		pubPdfODF();
		return Message.formatSimpleInfo("ODF imprimé");
		
	}
	// Méthode pour historiser un ODF
	public String generateODF() {
		ObjectDB hst = getGrant().getTmpObject("DF_Hist_Docs");

		try {
			synchronized(hst){
				hst.resetFilters();
					SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy-HH");  
    				Date date = new Date();  
   
					hst.create();	
					hst.getField("defiHstDocsDevis").setDocument(hst, "ODF-"+formatter.format(date).toString()+".pdf", this.pubPdfODF());
					hst.setFieldValue("DF_Hist_Docs_DF_Commande_id",this.getFieldValue("DF_Livraison_DF_Commande_id"));
					hst.setFieldValue("defiHstDocsDateEmission",date);
					ObjectDB commande = getGrant().getTmpObject("DF_Commande");
	
					synchronized(commande){
						commande.resetFilters();
						commande.setFieldFilter("row_id",this.getFieldValue("DF_Livraison_DF_Commande_id"));
						
						for(String[] cmde : commande.search()){
							commande.setValues(cmde);
							hst.setFieldValue("defiHstTitre", "ODF - "+commande.getFieldValue("defiCommandeNumero")+" - "+formatter.format(date).toString());
						}
					}
					//ObjectField file = hst.getField("defiHstDocsDevis");
					
					hst.save();
			
				
				
			}
			return Message.formatSimpleInfo("Fichier Historisé");
		}	
		catch(Exception e) {
		    AppLog.error(getClass(), "generateFile", "error...", e, getGrant());
		    return Message.formatSimpleError("Error...");
		}
	}
	
	
	
}