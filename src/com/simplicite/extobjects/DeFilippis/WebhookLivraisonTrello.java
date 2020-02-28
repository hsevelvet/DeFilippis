package com.simplicite.extobjects.DeFilippis;



import java.util.List;

import org.json.JSONObject;
import com.simplicite.objects.DeFilippis.DF_Livraison;
import com.simplicite.util.AppLog;
import com.simplicite.util.Grant;
import com.simplicite.util.ObjectDB;
import com.simplicite.util.exceptions.HTTPException;
import com.simplicite.util.tools.BusinessObjectTool;
import com.simplicite.util.tools.Parameters;


/**
 * External object WebhoonkLivraisonTrello
 */
public class WebhookLivraisonTrello extends com.simplicite.webapp.services.RESTServiceExternalObject {
	private static final long serialVersionUID = 1L;

	private static final JSONObject OK = new JSONObject().put("result", "ok");

    @Override
    public Object head(Parameters params) throws HTTPException {
        return OK;
    }
	/*
    @Override
    public Object get(Parameters params) throws HTTPException {
        return error(400, "Call me in POST please!");
    }*/


	private void updateCard(JSONObject data) {
		try {
			AppLog.info(getClass(), "call update post has data", data.toString(2), getGrant());
			JSONObject card = null;
			JSONObject listAfter = null;
			JSONObject customFieldItem = null;

			String o = "DF_Livraison";
			String status = null;
			String due = null;
			String customFName=null;
			String customFValue=null;

			//Recupération des informations dans la carte Trello
			if (data.has("card")){
				card = data.getJSONObject("card");
				if (card.has("due"))
					due = card.getString("due");
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

			ObjectDB obj = Grant.getSystemAdmin().getObject("webhook_" + o, o);
			BusinessObjectTool objt = new BusinessObjectTool(obj);
			AppLog.info(getClass(), "HSE TEST WEBHOOK", obj.toJSON(obj.search(), null, false, false), getGrant());
			synchronized(obj){
				obj.resetFilters();
				//obj.getField("df_livraison_trellocardid").setFilter(card.getString("id"));

				List<String[]> rows = obj.search();
				//AppLog.info(getClass(), "HSE TEST WEBHOOK", obj.toJSON(obj.search(), null, false, false), getGrant());
				for (String[] ld: rows){

					obj.setValues(ld);

					if(status.equals("5")){
						obj.create();
						//Mettre à jour les informations de livraison
						if (card.has("name"))
							obj.setFieldValue("df_livraison_id", card.getString("name"));
						if (due!=null){
							obj.setFieldValue("df_livraison_date_livraison_estimee", due);
						}
					//if (status!=null){
					//		obj.setFieldValue("df_livraison_statut", status);
					//	}
						if (customFName!=null){
							if (customFName.equals("Adresse"))
								obj.setFieldValue("df_livraison_adresse", customFValue);
							if (customFName.equals("Quantité"))
								obj.setFieldValue("df_livraison_quantite_chargee", customFValue);

							AppLog.info(getClass(), "Trello update > Simplicte"+data.has("customFieldItem"),customFValue, getGrant());

						}
						objt.save();

					}

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
							//if (data.has("card")) {
							updateCard(data);
							//}
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
} 