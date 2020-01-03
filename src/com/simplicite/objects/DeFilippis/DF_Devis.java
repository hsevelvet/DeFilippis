package com.simplicite.objects.DeFilippis;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object DF_Devis
 */


public class DF_Devis extends ObjectDB {
    private static final long serialVersionUID = 1L;
    
    public static double sum(List<Double> list) {
    	int sum = 0;
    	for (double i: list) {
        	sum += i;
		}
    	return sum;
	}
	
	

	@Override
	public void initUpdate(){
		
		List<Double> pricel = new ArrayList<>();
    
    	while (getField("df_produit_id").getInt(0) == 1){
    		pricel.add(getField("df_ligne_devis_prix_total_ht").getDouble(0));
		}

 
		double t = sum(pricel);
		
		double tva = getField("df_devis_tva").getDouble(0);
		
		
		setFieldValue("df_devis_prix_total", t + t*tva);
		
	}
	
}


