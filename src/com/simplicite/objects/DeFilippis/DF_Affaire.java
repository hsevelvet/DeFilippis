package com.simplicite.objects.DeFilippis;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object DF_Chantier

*/
public class DF_Affaire extends ObjectDB {
	private static final long serialVersionUID = 1L;
/**	
	@Override
	public List<String> postValidate() {
		List<String> msgs = new ArrayList<String>();
	
		ObjectDB devis = getGrant().getTmpObject("DF_Devis");
		synchronized(devis){
			devis.resetFilters();
			devis.setFieldFilter(this.getRowId(),"DF_Devis_DF_Chantier_id");
			
			for(String[] devi : devis.search()){
				devis.setValues(devi);
				AppLog.info(getClass(), "df_dev", devis.getFieldDisplayValue("df_devis_statut"), getGrant());
				if(devis.getStatus().equals("CH")){
					setStatus("C");
				}
				else{
					setStatus("I");
				}
			}
		}
	
		return msgs;
		
	}
*/	
}


