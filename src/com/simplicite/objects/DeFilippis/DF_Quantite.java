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
		ObjectDB o = getGrant().getTmpObject("DF_Livraison");
		o.resetFilters();
		o.getField("DF_Livraison_DF_Plan_Livraison_id").setFilter(getRowId());
		
		List<String[]> rows = o.search(false);
		for (int i = 0; i < rows.size(); i++) {
			//AppLog.info(getClass(), "DangLog---------------------------", obj.getFieldValue("df_livraison_id"), getGrant());
			//o.setFieldValue("df_livraison_adresse", "Test");
			//objt.validateAndSave();
		}
		
		} catch (Exception e) {
			AppLog.error(getClass(), "updateCard", null, e, getGrant());
		}
	
		return "Test";
	}
	
}
