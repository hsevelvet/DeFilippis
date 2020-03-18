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
				
				//if (card.has("due"));
				//	due = card.getString("due");
				
				if (card.has("id")){
					id_c = card.getString("id");
					AppLog.info(getClass(), "HSE_ATTACHMENT", id_c, getGrant());
					attach_id = getAttachmentID(id_c);
					
					//String id_list = attach_id.get(0);
					//AppLog.info(getClass(), "HSE MLJMKCSM", id_list, getGrant());
					//JSONObject att_card = tt.getCard(id_list);
					
					//AppLog.info(getClass(), "HSE ATT", att_card.toString(), getGrant());
				}
					

			}
            if (data.has("listAfter")){
            	listAfter = data.getJSONObject("listAfter");
            	status = listAfter.getString("name").split("-")[0].trim();
            	AppLog.info(getClass(), "STATUS--------", status, getGrant());
            }
            if (data.has("customFieldItem")){
            	customFieldItem = data.getJSONObject("customFieldItem").getJSONObject("value");
            	customFName = data.getJSONObject("customField").getString("name");
            	if (customFieldItem.has("text"))
            		customFValue = customFieldItem.getString("text");
            	if (customFieldItem.has("number"))
            		customFValue = customFieldItem.getString("number");
            }
            AppLog.info(getClass(), "mljqsckjqmvs_____", status, getGrant());
            
           
            
            
            // Get Grant des objets Livraison et Quantité
			ObjectDB obj = Grant.getSystemAdmin().getObject("webhook_" + o, o);
			obj.resetFilters();
			BusinessObjectTool objt = new BusinessObjectTool(obj);
			
			
			
			ObjectDB objq = Grant.getSystemAdmin().getObject("webhook_" + q, q);
			objq.resetFilters();
			BusinessObjectTool objtq = new BusinessObjectTool(objq);
			
			// Alimentation des objets avec des données 
			synchronized(obj){
					
					List<String> rows_id = new ArrayList<String>();
					if(status.equals("4")){
						Set<String> unique_commande = new LinkedHashSet<String>();
						// vérifier les numéro de commandes sur les cartes en PJ et créer des livraisons selon le num_commande
						for(String str: attach_id) {
								JSONObject att_card = tt.getCard(str);
								String cf_v = tt.call("/cards/"+att_card.getString("id")+"/customFieldItems","get","").toString();
								JSONArray  cf_json = new JSONArray(cf_v);
								 
								for (int i = 0; i < cf_json.length(); i++){
									JSONObject cs = cf_json.getJSONObject(i);
				                    if(cs.getString("idCustomField").equals("5e4af84bcd0b6c73365a1f20"))
				                    {
				                        JSONObject numc = cs.getJSONObject("value");
										if (numc.has("text")){
											unique_commande.add(numc.getString("text"));
										}
				                    }
								}
    								
								}
						// Créer les données livraison
						for(String uc: unique_commande) {
								obj.create();
								
								
								obj.setFieldValue("defiLivraisonIdCommande", uc);
							
								if (card.has("name")){
									obj.setFieldValue("defiLivraisonIntituleCamion", card.getString("name"));
								}
								
								String card_json_string = tt.call("/cards/"+card.getString("id"),"get","").toString();
								JSONObject  card_json = new JSONObject(card_json_string);
								//if (due!=null){
								obj.setFieldValue("df_livraison_date_livraison_estimee", card_json.getString("due"));
								AppLog.info(getClass(), "date-------", card_json.getString("due"), getGrant());
								//}
								if (status!=null){
									obj.setFieldValue("df_livraison_statut", status);
								}
								if (customFName!=null){
									if (customFName.equals("Adresse"))
										obj.setFieldValue("df_livraison_adresse", customFValue);
									if (customFName.equals("Quantité"))
										obj.setFieldValue("df_livraison_quantite_chargee", customFValue);
								}
								
								ObjectDB aac = Grant.getSystemAdmin().getObject("webhook_"+"DF_Commande","DF_Commande");
								
								synchronized(aac){
									aac.resetFilters();
									aac.setFieldFilter("defiCommandeNumero", uc);
									
									for (String[] a : aac.search()){
										aac.setValues(a);
										AppLog.info(getClass(), "hkkldkkjmlk----------------mlmklmmkl", aac.getRowId(), getGrant());
										obj.setFieldValue("DF_Livraison_DF_Commande_id",aac.getRowId());
										
										obj.setFieldValue("DF_Livraison_DF_Affaire_id", aac.getFieldValue("DF_Commande_DF_Affaire_id"));
									}
								}
						
								obj.save();
								
								
								rows_id.add(obj.getRowId());
							
						// if uc matches with num commande sur quanité 
						
						
						}
						AppLog.info(getClass(), "RRRRRRR------------------",rows_id.toString(), getGrant());
						
							// Remplir l'objet quantités avec les informations de cartes en PJ de camions 
						
						synchronized(objq){
							for(String str: attach_id) {
								objq.create();
								JSONObject att_card = tt.getCard(str);
								AppLog.info(getClass(), "Trello------------------",att_card.toString(), getGrant());
								//JSONObject att_card_cf == tt.getCustomField()
								//if (att_card.has("name")){
								//	objq.setFieldValue("defiQuantiteNumCommande", att_card.getString("name"));
								//}
								/*if (att_card.has("due")){
									objq.setFieldValue("defiQunatiteDateChargement", att_card.getString("due"));
								}*/
								
								String cf_v = tt.call("/cards/"+att_card.getString("id")+"/customFieldItems","get","").toString();
								
								JSONArray  cf_json = new JSONArray(cf_v);
								AppLog.info(getClass(), "CFCFCFCF------------------",cf_json.toString(), getGrant());
								
								/*JSONObject adresse_i = cf_json.getJSONObject(4);
								JSONObject adresse = adresse_i.getJSONObject("value");
								if (adresse.has("text")){
									AppLog.info(getClass(), "Trello TEST",adresse.getString("text"), getGrant());
									objq.setFieldValue("defiQuantiteAdresse", adresse.getString("text"));
								}*/
								
							
								
					
								
								JSONObject refp_i = cf_json.getJSONObject(3);
								JSONObject refp = refp_i.getJSONObject("value");
								if (refp.has("text")){
									AppLog.info(getClass(), "Trello TEST",refp.getString("text"), getGrant());
									objq.setFieldValue("defiQuantiteRefProduit", refp.getString("text"));
								}
								
								for (int i = 0; i < cf_json.length(); i++){
									JSONObject cs = cf_json.getJSONObject(i);
									AppLog.info(getClass(), "TOTO---------------------CS", cs.toString(), getGrant());
									// Chercher num commande
				                    if(cs.getString("idCustomField").equals("5e4af84bcd0b6c73365a1f20"))
				                    {
				                        JSONObject numc = cs.getJSONObject("value");
				                        objq.setFieldValue("defiQuantiteNumCommande", numc.getString("text"));
				
										
				                    }	
				                    // Chercher quantité commande
				                    if(cs.getString("idCustomField").equals("5e4ad07f4bcdbc6e6de1cdf9"))
				                    {
				                        JSONObject qteC = cs.getJSONObject("value");
				                        objq.setFieldValue("defiQuantiteQte", Double.parseDouble(qteC.getString("number")));
										
				                    }
				                    // Chercher Poids Unitaire commande
				                    if(cs.getString("idCustomField").equals("5e610e279cc788890583a5a6"))
				                    {
				                        JSONObject tonnageC = cs.getJSONObject("value");
				                        objq.setFieldValue("defiQuantitePoidsUnitaire", Double.valueOf(tonnageC.getString("number")));
										
				                    }
				                    // Chercher Ref Produit
				                    if(cs.getString("idCustomField").equals("5e4ad07518679b5fe7059699"))
				                    {
				                        JSONObject refC = cs.getJSONObject("value");
				                        objq.setFieldValue("defiQuantiteRefProduit", refC.getString("text"));
										
				                    }
				                    // Chercher Id Ligne Commande
				                    if(cs.getString("idCustomField").equals("5e6b943e9405a033888e385e"))
				                    {
				                        JSONObject lcID = cs.getJSONObject("value");
				                        ObjectDB lc = Grant.getSystemAdmin().getObject("webhook_"+"DF_ligne_commande","DF_ligne_commande");
				                        synchronized(lc){
				                        	lc.resetFilters();
											lc.setFieldFilter("defiLigneCommandeId",lcID.getString("text"));
											for(String[] lce : lc.search()){
														lc.setValues(lce);
														objq.setFieldValue("DF_Quantite_DF_ligne_commande_id",lc.getRowId());
												}
				                        }
				                    }
				                    /* Chercher Trigramme Transporteur 
				                    
				                    String truck_customfield = tt.call("/cards/"+card.getString("id")+"/customFieldItems","get","").toString();
									
									JSONArray  truck_json = new JSONArray(truck_customfield);
									AppLog.info(getClass(), "-----------------------------------", truck_json.toString(), getGrant());
									JSONObject tjson = truck_json.getJSONObject(3);
				                    if(tjson.getString("idCustomField").equals("5e721809b1759f6e20a2b522"))
				                    {
				                    	JSONObject tjsonvalue = truck_json.getJSONObject(2);
				                        JSONObject trigTrsp = tjsonvalue.getJSONObject("value");
				                        String trigramme_trsp = trigTrsp.getString("text");
				                        AppLog.info(getClass(), "trigramme-----", trigramme_trsp, getGrant());
				                        ObjectDB trsp = Grant.getSystemAdmin().getObject("webhook_"+"DF_Transport","DF_Transport");
				                        synchronized(trsp){
				                        	trsp.resetFilters();
											trsp.setFieldFilter("defiTrspTrigramme",trigramme_trsp);
											AppLog.info(getClass(), "trig", trigramme_trsp, getGrant());
											for(String[] tr : trsp.search()){
														trsp.setValues(tr);
														obj.setFieldValue("DF_Livraison_DF_Transport_id",trsp.getRowId());
												}
				                        }	
			
										
				                    }*/
				                    
				                    

								}
								
								
	
				
								objq.save();
								AppLog.info(getClass(), "CMMMM-----------------",obj.getFieldValue("defiLivraisonIdCommande"), getGrant());
								AppLog.info(getClass(), "AAAAA-----------------",objq.getFieldValue("defiQuantiteNumCommande"), getGrant());
				
								for(String row:rows_id){
									if(obj.select(row)){
										if (obj.getFieldValue("defiLivraisonIdCommande").equals(objq.getFieldValue("defiQuantiteNumCommande"))){
											objq.setFieldValue("DF_Quantite_DF_Livraison_id", row);
											
										    
											objq.save();
										}
									}
									}
								
								
								
								
							}
							
						}	
								
						}
						
				
					
						
					
						
						
						
//					}
					
			}
		
			
			 if (status.equals("5")){
						
						// récupérer les cartes en pièce jointe 
						 for(String str: attach_id) {
							JSONObject att_card = tt.getCard(str);
							AppLog.info(getClass(), "attachments -----------test", att_card.toString(), getGrant());
							// récupérer le custom field : id ligne de commande
							String cf_v = tt.call("/cards/"+att_card.getString("id")+"/customFieldItems","get","").toString();
							JSONArray  cf_json = new JSONArray(cf_v);
							String id_ligne_commande = null;
							for (int i = 0; i < cf_json.length(); i++){
									JSONObject cs = cf_json.getJSONObject(i);
									// Vérifier si l'idcustomfield = id ligne de commande
									if(cs.getString("idCustomField").equals("5e6b943e9405a033888e385e"))
				                    {
				                        JSONObject id_ligne_commande_js = cs.getJSONObject("value");
				                        // récupérer la valeur id ligne de commande
				                    	id_ligne_commande = id_ligne_commande_js.getString("text");
				                    	AppLog.info(getClass(), "Id ligne commande Value", id_ligne_commande, getGrant());
										
				                    }
							}
							
							// faire un call sur les cartes de la première liste 
							String prem_liste = tt.call("/lists/5e4a8f43ad23676d8487ff9c/cards","get","").toString();
							JSONArray cards_json = new JSONArray(prem_liste);
							
							for (int i = 0; i < cards_json.length(); i++){
								JSONObject card_1 = cards_json.getJSONObject(i);
								
								String cf_v_1 = tt.call("/cards/"+card_1.getString("id")+"/customFieldItems","get","").toString();
								JSONArray  cf_json_1 = new JSONArray(cf_v_1);
								// boucler sur les cartes de la liste 1 
								for (int j = 0; j < cf_json_1.length(); j++){
									JSONObject cs_1 = cf_json_1.getJSONObject(j);
									
									// Vérifier si l'idcustomfield = id ligne de commande
									if(cs_1.getString("idCustomField").equals("5e6b943e9405a033888e385e"))
				                    {
				                        JSONObject id_ligne_commande_js_1 = cs_1.getJSONObject("value");
				                        AppLog.info(getClass(), "TESTSSTSTST------------", id_ligne_commande_js_1.toString(), getGrant());
				                        // récupérer la valeur id ligne de commande
				                        String id_ligne_commande_1 = id_ligne_commande_js_1.getString("text");
				                        // renommer la carte dans la liste 1
				                        if (id_ligne_commande_1 == id_ligne_commande){
				                        	card_1.put("name","test_successful_hs");
				                        	
				                        }
				                        //card_1.put("name","test_successful");
										tt.updateCard(card_1.getString("id"), card_1);
				                    }
							}
								
							}
					
						}
				
						
					}
			
			}
			
	
			
			
		catch (Exception e) {
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