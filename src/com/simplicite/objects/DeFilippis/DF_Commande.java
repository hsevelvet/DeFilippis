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

/**
 * Business object DF_Commande
 */
public class DF_Commande extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	private TrelloTool tt = null;
	private JSONObject settings = null;
	
	@Override
	public void initUpdate() {
		// set values ligne devis
		ObjectDB o = getGrant().getTmpObject("DF_ligne_commande");
		o.resetFilters();
		o.getField("DF_ligne_commande_DF_Commande_id").setFilter(getRowId());
		
		List<String[]> rows = o.search(false);
		if (rows.size() > 0){
			double c = o.getCount();
			double t = Double.parseDouble(o.getField("defiLigneCommandePrixTotalEXW").getListOperatorValue());
			setFieldValue("defiCommandeMontantHT", t);


	}
	}
	/*
	@Override
	public String postCreate() {
		ObjectDB a = getGrant().getTmpObject("DF_Affaire");
		a.resetFilters();
		
		a.create();
		
		String num_affaire = getField("defiDevisTitre").getValue();
		String lieu_affaire = getFieldValue("defiDevisLieuProjet");
		String intitule_affaire = getFieldValue("defiDevisTitreProjet");
		
		a.setFieldValue("defiAfrNumero",num_affaire);
		a.setFieldValue("defiAfrLibelleChantier",lieu_affaire);
		a.setFieldValue("defiAfrLieuAffaire",intitule_affaire);
		
		a.save();
		
		//return Message.formatInfo("INFO_CODE", "Message", "fieldName");
		//return Message.formatWarning("WARNING_CODE", "Message", "fieldName");
		//return Message.formatError("ERROR_CODE", "Message", "fieldName");
		return null;
	}
	
	/**
	 * Action - Création cartes Trello 
	 */
	

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
			tt.deleteCard(getFieldValue("defiCommandeTrelloId"));
			return null;
		} catch (APIException e) { // Prevents deletion if card creation fails
			AppLog.error(getClass(), "postLoad", null, e, getGrant());
			return null;//Message.formatSimpleError("Card deletion error: " + e.getMessage());
		}
	}
	

	
	public String updateCard(){
		if (tt == null) return null;
		ObjectDB lc = getGrant().getTmpObject("DF_ligne_commande");
		try {
			synchronized(lc){
				lc.resetFilters();
				lc.getField("DF_ligne_commande_DF_Commande_id").setFilter(getRowId());
				long c = lc.getCount();
		    		
				for(String[] lce : lc.search()){
					lc.setValues(lce);
					String id = getFieldValue("defiCommandeTrelloId");
					JSONObject card = tt.getCard(id);
					
					card.put("name",  getFieldValue("defiCommandeIntituleAffaire")+"-"+lc.getFieldValue("defiLigneCommandeTypeGeologique")+"-"+lc.getFieldValue("defiLigneCommandeQuantite"));
					card.put("desc", createDesc());
					card.put("due", getFieldValue("defiCommandeDate"));
					card.put("idList",getIDList(getFieldValue("defiCommandeStatut")));
					tt.updateCard(id, card);
					
									
					//Mise à jour les informations custom fields
					tt.setCardCustomFieldItem(id,getIDCustomField("Numéro de commande"),new JSONObject().put("value",new JSONObject().put("text",getFieldValue("defiCommandeNumero"))));
					tt.setCardCustomFieldItem(id,getIDCustomField("Adresse"),new JSONObject().put("value",new JSONObject().put("text",getFieldValue("defiCommandeAdresseLivraison"))));
					tt.setCardCustomFieldItem(id,getIDCustomField("Quantité"),new JSONObject().put("value",new JSONObject().put("number",lc.getFieldValue("defiLigneCommandeQuantite"))));
		    		tt.setCardCustomFieldItem(id,getIDCustomField("Référence Produit"),new JSONObject().put("value",new JSONObject().put("text",lc.getFieldValue("defiLigneCommandeReferenceProduit"))));
					
		    		}
			}
			
			
			return Message.formatSimpleInfo("Trello card updated");
		} catch (APIException e) { // Prevents deletion if card creation fails
			AppLog.error(getClass(), "postUpdate", null, e, getGrant());
			return Message.formatSimpleError("Card update error: " + e.getMessage());
		}
	}
	public String createCard(){
		if (tt == null) return null;
		Double poids = getField("defiCommandePoidsTotal").getDouble(0);
		
		ObjectDB lc = getGrant().getTmpObject("DF_ligne_commande");
		try {
		    JSONObject card = new JSONObject();
		    synchronized(lc){
				lc.resetFilters();
				lc.getField("DF_ligne_commande_DF_Commande_id").setFilter(getRowId());
				long c = lc.getCount();
		    		
				for(String[] lce : lc.search()){
					lc.setValues(lce);
					
					
					String int_aff = getFieldValue("DF_Commande_DF_Affaire_id.defiAfrLibelleChantier");
					int_aff.replace(" " , "");
					String firstCharsIntitule = int_aff.substring(0, 7);
					
					String fourns = getFieldValue("DF_Commande_DF_Fournisseurs_id.defiFournNom");
					fourns.replace(" " , "");
					String firstCharsFourns = fourns.substring(0, 3);
					
					
					card.put("name",  (firstCharsIntitule+"."+getFieldValue("defiCommandeIntituleCommande")+"."+firstCharsFourns+"."+lc.getFieldValue("defiLigneCommandeReferenceProduit")+"."+lc.getFieldValue("defiLigneCommandeQuantite")).toUpperCase());
					//card.put("desc", createDesc());
					card.put("due", getFieldValue("defiCommandeDate"));
					card = tt.addCard(getIDList(getFieldValue("defiCommandeStatut")), card);
									
					//Mise à jour les informations custom fields
					
					tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Numéro de commande"),new JSONObject().put("value",new JSONObject().put("text",getFieldValue("defiCommandeNumero"))));
					tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Adresse"),new JSONObject().put("value",new JSONObject().put("text",getFieldValue("defiCommandeAdresseLivraison"))));
					tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Quantité"),new JSONObject().put("value",new JSONObject().put("number",lc.getFieldValue("defiLigneCommandeQuantite"))));
		    		
		    		tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Référence Produit"),new JSONObject().put("value",new JSONObject().put("text",lc.getFieldValue("defiLigneCommandeReferenceProduit"))));
		    		
		    		tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Poids Unitaire"),new JSONObject().put("value",new JSONObject().put("number",lc.getFieldValue("defiLigneCommandePoidsUnitaire"))));
		    		
		    		tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Ville"),new JSONObject().put("value",new JSONObject().put("text",getFieldValue("DF_Commande_DF_Affaire_id.defiAfrLieuAffaire"))));
		    		tt.setCardCustomFieldItem(card.getString("id"),getIDCustomField("Trigramme Suiveur"),new JSONObject().put("value",new JSONObject().put("text",getFieldValue("DF_Commande_DF_utilisateur_interne_id.defiUsrTrigramme"))));
				}
		    		}
		    	AppLog.info(getClass(), "preCreate", card.toString(2), getGrant());
				setFieldValue("defiCommandeTrelloId", card.getString("id"));
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
		String id = getFieldValue("defiCommandeTrelloId");
		if (id.length()>0)
			return createCard();
		else 
			return createCard();
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
	
		
	public String createDesc(){
		String desc = "";
		desc += "\n**Ligne de commande ID**: "+getFieldValue("defiCommandeNumero_ligne_commande");
		desc += "\n**Quantité**: "+getFieldValue("defiCommandePoidsTotal");
		//desc += "\n**Statut Livraison**: "+getFieldValue("defiCommandeStatut");
		desc += "\n**Date Livraison Estimée**: "+getFieldValue("defiCommandeDate");
		desc += "\n";
		desc += "\n**Num BL Client**: "+getFieldValue("df_livraison_num_bl_client");
		desc += "\n**Num BL Fournisseur**: "+getFieldValue("df_livraison_num_bl_fournisseur");
		desc += "\n";
		desc += "\n**Adresse Enlevement**: "+getFieldValue("defiCommandeAdresseLivraison_enlevement");
		desc += "\n**Adresse Livraison**: "+getFieldValue("defiCommandeAdresseLivraison");
		desc += "\n**Adresse De Livraison Confirmée**: "+getFieldValue("defiCommandeAdresseLivraison_de_livraison_confirmee");
		desc += "\n";
		desc += "\n**Nom Transporteur**: "+getFieldValue("df_livraison_nom_transporteur");
		desc += "\n**Contact Transporteur**: "+getFieldValue("df_livraison_contact_transporteur");
		desc += "\n**Num ZEEPO Transporteur**: "+getFieldValue("df_livraison_num_zeepo_transporteur");
		desc += "\n";
		desc += "\n**Contact Déchargement Privilégié**: "+getFieldValue("df_livraison_contact_dechargement_privilegie");
		desc += "\n**Contact En Cas De Problème**: "+getFieldValue("df_livraison_contact_en_cas_de_probleme");
		return desc;
	}


}