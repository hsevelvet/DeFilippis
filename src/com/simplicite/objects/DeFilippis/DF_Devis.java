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
		double nbc = Double.parseDouble(o.getField("df_ligne_devis_nombre_camions").getListOperatorValue());
		double pt = Double.parseDouble(o.getField("df_ligne_devis_poids_total").getListOperatorValue());
	
		setFieldValue("df_devis_nombre_camions", nbc);
		setFieldValue("df_devis_poids_total", pt);
		
		setFieldValue("df_devi_prix_total_ht", t);
		
		setFieldValue("df_devis_prix_total", t + t*0.2);
		}
		
	}
	public void genARC(){
		ObjectDB o = getGrant().getTmpObject("DF_Commande");
		o.resetFilters();
		
		// Get des valeurs de Devis 
		String num = getField("df_devis_numero").getValue();
		
		// Set des valeurs en commande
		o.getField("df_commande_id").setFilter(getRowId());
		List <String[]> rows = o.search(false);
		if (rows.size()>= 0){
			// Création du record 
			o.create();
			// Valorisation des champs
			ObjectField s = o.getField("df_commande_id");
			s.setValue(num);
			
			// Enregistrement et Validation 
			save();
			validate();
		}
			
	}
		
}


	
