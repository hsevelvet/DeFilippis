package com.simplicite.extobjects.DeFilippis;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.List;
import java.util.*;

import org.json.JSONObject;
import com.simplicite.objects.DeFilippis.DF_Livraison;
import com.simplicite.util.AppLog;
import com.simplicite.util.Grant;
import com.simplicite.util.ObjectDB;
import com.simplicite.util.exceptions.HTTPException;
import com.simplicite.util.tools.BusinessObjectTool;
import com.simplicite.util.tools.Parameters;
import com.simplicite.util.exceptions.APIException;
import com.simplicite.util.tools.HTMLTool;
import com.simplicite.util.tools.TrelloTool;


/**
 * External object WebhoonkLivraisonTrello
 */
public class WebhookLivraisonTrello extends com.simplicite.webapp.services.RESTServiceExternalObject {
	private static final long serialVersionUID = 1L;
	
	private JSONObject settings = null;
	
	private transient TrelloTool tt = null;
	private void initTrelloTool() {
		if (tt == null)
			tt = new TrelloTool(getGrant());
	}

	private static final JSONObject OK = new JSONObject().put("result", "ok");
	
    @Override
    public Object head(Parameters params) throws HTTPException {
        return OK;
    }

    @Override
    public Object get(Parameters params) throws HTTPException {
        return error(400, "Call me in POST please!");
    }
    
    // Récupérer Id de l'attachement avec le nom de l'attachement	
	public List<String> getAttachmentID(String cardId){
	
		initTrelloTool();
		List<String> id_url = new ArrayList<String>();
		List<String> id_a = new ArrayList<String>();
		
		try {
			String t_a = tt.call("/cards/"+cardId+"/attachments","get","").toString();
			AppLog.info(getClass(), "getIDAttachment", t_a, getGrant());
			JSONArray mJSONArray = new JSONArray(t_a);
			id_url = searchJSONArray("isUpload",false,"url",mJSONArray);
			for(String str: id_url) {
				id_a.add(str.split("/")[4].trim());
			}
			
			AppLog.info(getClass(), "ID TEST HSE", id_a.toString(), getGrant());
		} catch (APIException e) { // Prevents deletion if card creation fails
			AppLog.error(getClass(), "getIDAttachment", null, e, getGrant());
			return null;//Message.formatSimpleError("Card deletion error: " + e.getMessage());
		}
		return id_a;
	}
	
	// Récupérer Id du customField avec le nom du customfield
	public String getIDCustomField(String customFieldName){
		String id = null;
		settings = getGrant().getJSONObjectParameter("TRELLO_CARDEX_SETTINGS");
		try {
			String t = tt.call("/boards/"+settings.getString("boardId")+"/customFields","get","").toString();
			JSONArray mJSONArray = new JSONArray(t);
			id = searchJSONArray2("name",customFieldName,"id",mJSONArray);
			AppLog.info(getClass(), "getIDCustomField", id, getGrant());
		} catch (APIException e) { // Prevents deletion if card creation fails
			AppLog.error(getClass(), "getIDCustomField", null, e, getGrant());
			return null;//Message.formatSimpleError("Card deletion error: " + e.getMessage());
		}
		return id;
	}

		

	private void updateCard(JSONObject data) {
		initTrelloTool();
		try {
			AppLog.info(getClass(), "call update post has data", data.toString(2), getGrant());
			JSONObject card = null;
			JSONObject listAfter = null;
			JSONObject customFieldItem = null;
			
			String o = "DF_Livraison";
			String q = "DF_Quantite";
			String status = null;
			String due = null;
			String customFName=null;
			String customFValue=null;
			String id_c = null;
			List<String> attach_id = new ArrayList<String>();
			
			//Recupération des informations dans la carte Trello
			if (data.has("card")){
				card = data.getJSONObject("card");
				
				if (card.has("due"))
					due = card.getString("due");
				
				if (card.has("id")){
					id_c = card.getString("id");
					AppLog.info(getClass(), "HSE_ATTACHMENT", id_c, getGrant());
					attach_id = getAttachmentID(id_c);
					
					String id_list = attach_id.get(0);
					JSONObject att_card = tt.getCard(id_list);
					
					AppLog.info(getClass(), "HSE ATT", att_card.toString(), getGrant());
				}
					

			}
            if (data.has("listAfter")){
            	listAfter = data.getJSONObject("listAfter");
            	status = listAfter.getString("name").split("-")[0].trim();
            }
            if (data.has("customFieldItem")){
            	customFieldItem = data.getJSONObject("customFieldItem").getJSONObject("value");
            	customFName = data.getJSONObject("customField").getString("name");
            	if (customFieldItem.has("text"))
            		customFValue = customFieldItem.getString("text");
            	if (customFieldItem.has("number"))
            		customFValue = customFieldItem.getString("number");
            }
            
            
            // Get Grant des objets Livraison et Quantité
			ObjectDB obj = Grant.getSystemAdmin().getObject("webhook_" + o, o);
			obj.resetFilters();
			BusinessObjectTool objt = new BusinessObjectTool(obj);
			
			
			
			ObjectDB objq = Grant.getSystemAdmin().getObject("webhook_" + q, q);
			objq.resetFilters();
			BusinessObjectTool objtq = new BusinessObjectTool(objq);
			
			// Alimentation des objets avec des données 
			synchronized(obj){
					
					if(status.equals("4")){
						obj.create();
					
						if (card.has("name")){
							obj.setFieldValue("defiLivraisonIntituleCamion", card.getString("name"));
						}
							
						if (due!=null){
							obj.setFieldValue("df_livraison_date_livraison_estimee", due);
						}
						if (status!=null){
							obj.setFieldValue("df_livraison_statut", status);
						}
						if (customFName!=null){
							if (customFName.equals("Adresse"))
								obj.setFieldValue("df_livraison_adresse", customFValue);
							if (customFName.equals("Quantité"))
								obj.setFieldValue("df_livraison_quantite_chargee", customFValue);
							
							AppLog.info(getClass(), "Trello update > Simplicte"+data.has("customFieldItem"),customFValue, getGrant());
		
						}
						
						
						synchronized(objq){
							for(String str: attach_id) {
								objq.create();
								JSONObject att_card = tt.getCard(str);
								//JSONObject att_card_cf == tt.getCustomField()
								if (att_card.has("name")){
									objq.setFieldValue("defiQuantiteIntitule", att_card.getString("name"));
								}
								if (att_card.has("due")){
									objq.setFieldValue("defiQunatiteDateChargement", att_card.getString("due"));
								}
								
								String cf_v = tt.call("/cards/"+att_card.getString("id")+"/customFieldItems","get","").toString();
								JSONArray  cf_json = new JSONArray(cf_v);
								
								JSONObject adresse_i = cf_json.getJSONObject(4);
								JSONObject adresse = adresse_i.getJSONObject("value");
								if (adresse.has("text")){
									AppLog.info(getClass(), "Trello TEST",adresse.getString("text"), getGrant());
									objq.setFieldValue("defiQuantiteAdresse", adresse.getString("text"));
								}
								
								JSONObject qte_i = cf_json.getJSONObject(2);
								JSONObject qte = qte_i.getJSONObject("value");
								if (qte.has("number")){
									AppLog.info(getClass(), "Trello TEST",qte.getString("number"), getGrant());
									objq.setFieldValue("defiQuantiteQte", Integer.parseInt(qte.getString("number")));
								}
								
								JSONObject tonnage_i = cf_json.getJSONObject(1);
								JSONObject tonnage = tonnage_i.getJSONObject("value");
								if (tonnage.has("number")){
									AppLog.info(getClass(), "Trello TEST",tonnage.getString("number"), getGrant());
									objq.setFieldValue("defiQuantiteTonnage", Double.parseDouble(tonnage.getString("number")));
								}
								
								JSONObject refp_i = cf_json.getJSONObject(3);
								JSONObject refp = refp_i.getJSONObject("value");
								if (refp.has("text")){
									AppLog.info(getClass(), "Trello TEST",refp.getString("text"), getGrant());
									objq.setFieldValue("defiQuantiteReferenceProduit", refp.getString("text"));
								}
								
								JSONObject numc_i = cf_json.getJSONObject(0);
								JSONObject numc = numc_i.getJSONObject("value");
								if (numc.has("text")){
									AppLog.info(getClass(), "Trello TEST",numc.getString("text"), getGrant());
									objq.setFieldValue("defiQuantiteNumeroCommande", numc.getString("text"));
								}
								objq.setFieldValue("DF_Quantite_DF_Livraison_id", obj.getRowId());
								objq.save();
							}
							
						}
						
						obj.save();
						
//					}
					
			}
		
			
			}
			
			
			
		} catch (Exception e) {
			AppLog.error(getClass(), "updateCard", null, e, getGrant());
		}
	}

    @Override
	public Object post(Parameters params) throws HTTPException {
		try {
			JSONObject req = params.getJSONObject();
			if (req != null) {
				AppLog.info(getClass(), "post", req.toString(2), getGrant());
				if (req.has("action")) {
					JSONObject action = req.getJSONObject("action");
					String type = action.getString("type");
					if ("updateCard".equals(type)) {
						if (action.has("data")) {
							JSONObject data = action.getJSONObject("data");
							updateCard(data);
						}
					}
				}
				return OK;
			} else {
				return error(400, "Call me with a JSON body please!");
			}
		} catch (Exception e) {
			return error(e);
		}
	}
	
	// recherche item dans la table des JSONArray l'item qui contient "tagName"=valueName et retourne la valeur de l'element "tagId"
	public List<String> searchJSONArray(String tagName,boolean valueName, String tagId, JSONArray mJSONArray){
		
		
		Boolean found=false;
		int length = mJSONArray.length();
		int i = 0;
		List<String> id = new ArrayList<String>();
		while(found==false && i<length){
            JSONObject o = mJSONArray.getJSONObject(i);
			if (o.getBoolean(tagName) == valueName){
				id.add(o.getString(tagId)); 
				//found = true;
			}
			i=i+1;
        }
        return id;
	}
	// recherche item dans la table des JSONArray l'item qui contient "tagName"=valueName et retourne la valeur de l'element "tagId"
	public String searchJSONArray2(String tagName,String valueName, String tagId, JSONArray mJSONArray){
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
	

	
}