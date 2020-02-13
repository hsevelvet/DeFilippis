package com.simplicite.extobjects.DeFilippis;

import java.util.List;

import org.json.JSONObject;

import com.simplicite.util.AppLog;
import com.simplicite.objects.DeFilippis.DF_Livraison;
import com.simplicite.util.tools.BusinessObjectTool;
import com.simplicite.util.Grant;
import com.simplicite.util.ObjectDB;
import com.simplicite.util.Tool;
import com.simplicite.util.tools.Parameters;
import com.simplicite.util.exceptions.HTTPException;


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

    @Override
    public Object get(Parameters params) throws HTTPException {
        return error(400, "Call me in POST please!");
    }
    

	private void updateCard(JSONObject data) {
		try {
			AppLog.info(getClass(), "call update post has data", data.toString(2), getGrant());
			String o = "DF_Livraison";
			
			JSONObject card = null;
			JSONObject listAfter = null;
			String status = null;
			String due = null;
			if (data.has("card")){
				card = data.getJSONObject("card");
				if (card.has("due"))
					due = card.getString("due");
			}
            if (data.has("listAfter")){
            	listAfter = data.getJSONObject("listAfter");
            	status = listAfter.getString("name").split("-")[0].trim();

            }
            	
			ObjectDB obj = Grant.getSystemAdmin().getObject("webhook_" + o, o);
			BusinessObjectTool objt = new BusinessObjectTool(obj);
			obj.resetFilters();
			obj.getField("df_livraison_trellocardid").setFilter(card.getString("id"));

			List<String[]> rows = objt.search();
			if (rows.size() == 1) {
				AppLog.info(getClass(), "DangLog", rows.get(0).toString(), getGrant());

				obj.setValues(rows.get(0), true);
				if (card.has("name"))
					obj.setFieldValue("df_livraison_id", card.getString("name"));
				if (due!=null){
					obj.setFieldValue("df_livraison_date_livraison_estimee", due);
				}

				AppLog.info(getClass(), "call update post has data0", status, getGrant());
	
				if (status!=null){
					obj.setFieldValue("df_livraison_statut", status);
				}
				objt.validateAndSave();
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
				AppLog.info(getClass(), "call update post has data0", req.toString(2), getGrant());
            	if (req.has("action")) {
            		JSONObject action = req.getJSONObject("action");
            		String type = action.optString("type");
            		if ("updateCard".equals(type)) {
            			if (action.has("data")) {
            				JSONObject data = action.getJSONObject("data");
            				if(data.has("card")){
            					updateCard(data.getJSONObject("card"));
            				}
            				
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
