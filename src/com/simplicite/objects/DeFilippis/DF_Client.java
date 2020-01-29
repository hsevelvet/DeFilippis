package com.simplicite.objects.DeFilippis;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object DF_Client
*/
public class DF_Client extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
/**	
	@Override
	public List<String> postValidate() {
		List<String> msgs = new ArrayList<String>();
	
		ObjectDB devis = getGrant().getTmpObject("DF_Devis");
		synchronized(devis){
			devis.resetFilters();
			devis.setFieldFilter(this.getRowId(),"DF_Devis_DF_Client_id");
			
			for(String[] devi : devis.search()){
				devis.setValues(devi);
				AppLog.info(getClass(), "df_dev", devis.getFieldDisplayValue("df_devis_statut"), getGrant());
				if(devis.getStatus().equals("CH")){
					setFieldValue("df_client_indice",20);
				}
			}
		}
	
		return msgs;
		
	}
*/		
	}


