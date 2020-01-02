package com.simplicite.objects.DeFilippis;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object DF_Ligne_Devis
 */
public class DF_Ligne_Devis extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void initUpdate(){
		
		int q = getField("df_ligne_devis_quantite").getInt(0);
        double p = getField("df_produit_prix").getDouble(0);
        double total = p*q;
        setFieldValue("df_ligne_devis_prix_total_ht", total);
        
        
	}

}