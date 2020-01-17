package com.simplicite.objects.DeFilippis;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.ObjectDB;
import com.simplicite.objects.DeFilippis.DF_Ligne_Devis;
import com.simplicite.util.tools.*;

/**
 * Business object DF_Devis
*/

public class DF_Devis extends ObjectDB {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void initUpdate(){
		ObjectDB o = getGrant().getTmpObject("DF_Ligne_Devis");
		o.resetFilters();
		o.getField("DF_Ligne_Devis_DF_Devis_id").setFilter(getRowId());
		
		List<String[]> rows = o.search(false);
		if (rows.size() > 0){
		double c = o.getCount();
			
		double t = Double.parseDouble(o.getField("df_ligne_devis_prix_vente_impose").getListOperatorValue());
		
		
		setFieldValue("df_devis_prix_total", t + t*0.2);
		}
		
		}
		
			
	}


	
