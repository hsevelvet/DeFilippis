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
/**		
		
		// set numero devis
		
		String date = getCreatedDate();
		String[] year = date.split("-");
		String y = year[0];
		
		Integer result = Integer.valueOf(getRowId());
		String id = String.format("%04d",result);
		
		String num_devis = y +"."+id+"."+"A";
		setFieldValue("df_devis_numero",num_devis);
		
		// set titre devis
		String full_name = getFieldValue("df_utilisateur_interne_nc");
		String[] nameparts = full_name.split(" ");
		String trigramme = String.valueOf(nameparts[0].charAt(0)).toUpperCase() +
		String.valueOf(nameparts[1].charAt(0)).toUpperCase() + String.valueOf(nameparts[1].charAt(2)).toUpperCase();
		
		String lieu = getFieldValue("df_devis_lieu_projet");
		String projet = getFieldValue("df_devis_titre_projet");
		String client = getFieldValue("df_client_nom");
		
		String titre_devis = trigramme + "." + lieu + "." + projet + "." + client + "." + num_devis;
		setFieldValue("df_devis_titre",titre_devis);

*/		
		
		// set values ligne devis
		ObjectDB o = getGrant().getTmpObject("DF_Ligne_Devis");
		o.resetFilters();
		o.getField("DF_Ligne_Devis_DF_Devis_id").setFilter(getRowId());
		
		List<String[]> rows = o.search(false);
		if (rows.size() > 0){
			double c = o.getCount();
				
			double t = Double.parseDouble(o.getField("df_ligne_devis_prix_vente_impose").getListOperatorValue());
			double total_achat = Double.parseDouble(o.getField("df_ligne_devis_total_achat_ht").getListOperatorValue());
			double nbc = Double.parseDouble(o.getField("df_ligne_devis_nombre_camions").getListOperatorValue());
			double pt = Double.parseDouble(o.getField("df_ligne_devis_poids_total").getListOperatorValue());
		
			setFieldValue("df_devis_nombre_camions", nbc);
			setFieldValue("df_devis_poids_total", pt);
			
			setFieldValue("df_devis_prix_total_ht", t);
			
			setFieldValue("df_devis_prix_total", t + t*0.2);
			setFieldValue("df_devis_coef_global", t/total_achat);
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


	
