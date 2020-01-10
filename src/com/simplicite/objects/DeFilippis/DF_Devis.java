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
		
			
		double c = o.getCount();
			
		double t = Double.parseDouble(o.getField("df_ligne_devis_prix_total_ht").getListOperatorValue());
		
		double tva = getField("df_devis_tva").getDouble(0);
		
		
		setFieldValue("df_devis_prix_total", t + t*tva);
		}
		
			
	}
	



