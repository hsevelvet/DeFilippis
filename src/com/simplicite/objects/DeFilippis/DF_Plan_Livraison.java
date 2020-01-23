package com.simplicite.objects.DeFilippis;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object DF_Plan_Livraison
 */
public class DF_Plan_Livraison extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	public String createAllTrelloCards(){
		try {
		String o = "DF_Livraison";
		ObjectDB obj = Grant.getSystemAdmin().getObject("webhook_" + o, o);
		BusinessObjectTool objt = new BusinessObjectTool(obj);
		obj.resetFilters();
		//obj.getField("df_livraison_trellocardid").setFilter("id");
		List<String[]> rows = objt.search();
		for (int i = 0; i < rows.size(); i++) {
			obj.setValues(rows.get(i), true);
			AppLog.info(getClass(), "DangLog", obj.getFieldValue("df_livraison_id"), getGrant());
		}
		
		} catch (Exception e) {
			AppLog.error(getClass(), "updateCard", null, e, getGrant());
		}
	
		return "Test";
	}
	
}
