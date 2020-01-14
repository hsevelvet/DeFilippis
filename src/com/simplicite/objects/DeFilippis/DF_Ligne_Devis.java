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
		// calcul total
		//int q = getField("df_ligne_devis_quantite").getInt(0);
		//double c = getField("df_ligne_devis_cout_transport").getDouble(0);
        //double p = getField("df_produit_prix").getDouble(0);
        //double total = p*q + c;
        
        //setFieldValue("df_ligne_devis_prix_total_ht", total);
        
        // céer copie référence base produit

        // calcul masse unitaire sans joint
		String unite = getField("df_ligne_devis_unite").getValue();
		double mvp = getField("df_ligne_devis_masse_volumique").getDouble(0);
		double lng = getField("df_ligne_devis_longueur").getDouble(0);
		double lrg = getField("df_ligne_devis_laregur").getDouble(0);
		double ep = getField("df_ligne_devis_epaisseur").getDouble(0);
        switch(unite){
			case "u":
				setFieldValue("df_ligne_devis_masse_unitaire_ss_joint", mvp*(lng*lrg*ep / 1000000));
				break;
			case "ml":
				setFieldValue("df_ligne_devis_masse_unitaire_ss_joint", mvp*(lng*ep / 10000));
				break;
			case "m²":
				setFieldValue("df_ligne_devis_masse_unitaire_ss_joint", mvp*(ep / 100));
				break;
	}
        
	}
 
	
}
