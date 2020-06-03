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


import java.util.*;
import com.simplicite.util.ObjectDB;
import com.simplicite.webapp.web.BootstrapWebPage;
import com.simplicite.util.tools.*;
import com.simplicite.util.tools.HTMLTool; 

import com.simplicite.util.tools.PDFTool;

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
			setFieldValue("defiLivraisonTotalHT", t);
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

		
		
		// Commande
		ObjectDB commande = getGrant().getTmpObject("DF_Commande");
		commande.resetFilters();
		commande.setFieldFilter("row_id",getFieldValue("DF_Livraison_DF_Commande_id"));
		
		
		// Client 	
		ObjectDB client = getGrant().getTmpObject("DF_Client");
		client.resetFilters();
		client.setFieldFilter("row_id",commande.getFieldValue("DF_Commande_DF_Client_id"));
		AppLog.info(getClass(), "hhmldkqmdslfksd-------77",commande.getFieldValue("DF_Commande_DF_Client_id"),getGrant());
		
		// Contact Client
		ObjectDB contact_client = getGrant().getTmpObject("DF_Contact");
		contact_client.resetFilters();
		contact_client.setFieldFilter("row_id",commande.getFieldValue("DF_Commande_DF_Contact_id"));		
		
	
		wp.append(MustacheTool.apply(
								this,
								"DF_BL_HTML", 
								"{'rows':"+bl.toJSON(bl.search(), null, false, false)+
								",'rows_l':"+q.toJSON(q.search(), null, false, false)+
								",'rows_commande':"+commande.toJSON(commande.search(), null, false, false)+								
								",'rows_client':"+client.toJSON(client.search(), null, false, false)+
								",'contact_client':"+contact_client.toJSON(contact_client.search(), null, false, false)+								
							    "}"
								));
		
		return wp.getHTML();
	}
	

	
	public byte[] pubPdfBL(){
		String url = "http://wkhtml2pdf/";
		String user = null;
		String password = null;
	
		
		JSONObject postData = new JSONObject();
		postData.put("contents", Tool.toBase64(pubBL()));

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
		ObjectDB commande_livraison = Grant.getSystemAdmin().getObject("DF_Commande","DF_Commande");
		//ObjectDB client = Grant.getSystemAdmin().getObject("DF_Client","DF_Client");
		ObjectDB client = getGrant().getTmpObject("DF_Client");
		ObjectDB contact_client = getGrant().getTmpObject("DF_Contact");

		List<String[]> q_search = new ArrayList<String[]>();
		List<String[]> livraison_search = new ArrayList<String[]>();
		
		
	
		double somme = 0;
		for (String id: getSelectedIds()){
			
			synchronized(livraison){
				
				livraison.select(id);
				AppLog.info(getClass(), "cliiiiient", id, getGrant());
				ObjectDB qt = getGrant().getTmpObject("DF_Quantite");
				q.resetFilters();
				q.setFieldFilter("DF_Quantite_DF_Livraison_id",livraison.getRowId());
				q.save();
				
				livraison.setFieldFilter("row_id", livraison.getRowId());
				
				commande_livraison.resetFilters();
				commande_livraison.setFieldFilter("row_id",livraison.getFieldValue("DF_Livraison_DF_Commande_id"));
				commande_livraison.save();
				AppLog.info(getClass(), "cliiiiient", commande_livraison.getRowId(), getGrant());
				//client.resetFilters();
				
				
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
				
				double total_livraison = rows.stream().mapToDouble(Double::doubleValue).sum();
				AppLog.info(getClass(), "rows", rows.toString(), getGrant());
		
				// calcul tva 
				double prix_tva = (int)(Math.round(total_livraison*0.2 * 100))/100.0;
		
				// calcul total ttc
				double total_ttc = prix_tva+total_livraison;
				
				wp.append(MustacheTool.apply(
						this,
						"DF_ODF_HTML", 
						"{'rows_l':"+q.toJSON(q_search, null, false, false)+
						",'bl':"+livraison.toJSON(livraison_search, null, false, false)+
						",'cl':"+commande_livraison.toJSON(commande_livraison.search(), null, false, false)+
						",'client':"+client.toJSON(client.search(), null, false, false)+
						",'contact_client':"+contact_client.toJSON(contact_client.search(), null, false, false)+
						",'total_ht':"+ "[{'total_ht_l':"+Double.toString(total_livraison)+"}]"+
						",'tva':"+ "[{'prix_tva':"+Double.toString(prix_tva)+"}]"+
						",'total_ttc':"+ "[{'total_ttc_l':"+Double.toString(total_ttc)+"}]"+
						"}"
				));
				
			}
			
			
			
	
		}
		// calcul montant total ht
		//DF_Livraison livraison2 = (DF_Livraison) getGrant().getTmpObject("DF_Livraison");
		//livraison2.resetValues();
		//List<Double>rows = new ArrayList<>();
		//List<String> ids = getSelectedIds();
		//AppLog.info(getClass(), "ids", ids.toString(), getGrant());
		
		
		
		// Contact Client 
		//contact_client.resetFilters();
		//contact_client.setFieldFilter("row_id",commande_livraison.getFieldValue("DF_Commande_DF_Contact_id"));	
		//contact_client.save();
				
		// Client
		//client.resetFilters();
		//client.setFieldFilter("row_id",commande_livraison.getFieldValue("DF_Commande_DF_Client_id"));
		//AppLog.info(getClass(), "cliiiiient", commande_livraison.getFieldValue("DF_Commande_DF_Client_id"), getGrant());
		//client.save();
		
		
		
		
		/*ObjectDB commande_client = getGrant().getTmpObject("DF_Commande");
		commande_client.resetFilters();
		commande_client.setFieldFilter("row_id",livraison.getFieldValue("DF_Livraison_DF_Commande_id"));
		client.setFieldFilter("row_id",commande_client.getFieldValue("DF_Commande_DF_Client_id"));
		*/	
		
		
		
		
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
	
}