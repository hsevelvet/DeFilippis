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
    

	private void updateCard(JSONObject card,JSONObject listAfter) {
		try {
			AppLog.info(getClass(), "updateCard", card.toString(2), getGrant());
			String o = "DF_Livraison";
			ObjectDB obj = Grant.getSystemAdmin().getObject("webhook_" + o, o);
			BusinessObjectTool objt = new BusinessObjectTool(obj);
			obj.resetFilters();
			obj.getField("df_livraison_trellocardid").setFilter(card.getString("id"));

			List<String[]> rows = objt.search();
			if (rows.size() == 1) {
				obj.setValues(rows.get(0), true);
				if (card.has("name"))
					obj.setFieldValue("df_livraison_id", card.getString("name"));
				String status = listAfter.getString("name").split("-")[0].trim();
				AppLog.info(getClass(), "updateCard Dang",status, getGrant());
				if (listAfter.has("name")){
					//String status = listAfter.getString("name").split(".")[1].trim();
					AppLog.info(getClass(), "updateCard Dang",status, getGrant());
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
            	//AppLog.info(getClass(), "post", req.toString(2), getGrant());
            	if (req.has("action")) {
            		JSONObject action = req.getJSONObject("action");
            		String type = action.optString("type");
            		if ("updateCard".equals(type)) {
            			if (action.has("data")) {
            				JSONObject data = action.getJSONObject("data");
            				
            				JSONObject card = data.getJSONObject("card");
            				JSONObject listAfter = data.getJSONObject("listAfter");
            				AppLog.info(getClass(), "updateCard Dang",data.toString(2), getGrant());

            				if (data.has("card")) {
            					updateCard(data.getJSONObject("card"),listAfter);
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
