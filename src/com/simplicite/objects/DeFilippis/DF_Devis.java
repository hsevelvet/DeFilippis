package com.simplicite.objects.DeFilippis;

import java.util.*;
import java.util.Date;
import com.simplicite.util.*;
import com.simplicite.util.ObjectDB;
import com.simplicite.objects.DeFilippis.DF_Ligne_Devis;
import com.simplicite.util.tools.*;

/**
 * Business object DF_Devis
*/

public class DF_Devis extends ObjectDB {

	private static final long serialVersionUID = 1L;
	
	
	// compteur date
	static long days(Date start, Date end){
	    //Ignore argument check
	
	    Calendar c1 = GregorianCalendar.getInstance();
	    c1.setTime(start);
	    int w1 = c1.get(Calendar.DAY_OF_WEEK);
	    c1.add(Calendar.DAY_OF_WEEK, -w1 + 1);
	
	    Calendar c2 = GregorianCalendar.getInstance();
	    c2.setTime(end);
	    int w2 = c2.get(Calendar.DAY_OF_WEEK);
	    c2.add(Calendar.DAY_OF_WEEK, -w2 + 1);
	
	    //end Saturday to start Saturday 
	    long days = (c2.getTimeInMillis()-c1.getTimeInMillis())/(1000*60*60*24);
	    long daysWithoutSunday = days-(days*2/7);
	
	    if (w1 == Calendar.SUNDAY) {
	        w1 = Calendar.MONDAY;
	    }
	    if (w2 == Calendar.SUNDAY) {
	        w2 = Calendar.MONDAY;
	    }
	    return daysWithoutSunday-w1+w2;
	}
	
	@Override
	public void initUpdate(){
		
		// set numero devis
		String y = Tool.getCurrentYear();
		
		Integer result = Integer.valueOf(getRowId());
		String id = String.format("%04d",result);
		
		String num_devis = y +"."+id;
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
		
		// set values ligne devis
		ObjectDB o = getGrant().getTmpObject("DF_Ligne_Devis");
		o.resetFilters();
		o.getField("DF_Ligne_Devis_DF_Devis_id").setFilter(getRowId());
		
		String dateString = getFieldValue("df_devis_date_emission");
		Date date_em = Tool.fromDateTime(dateString);
		String c_dateString = Tool.getCurrentDateTime();
		Date date_cur = Tool.fromDateTime(c_dateString);
		double date_diff = days(date_em,date_cur);
		setFieldValue("df_devis_date_count",date_diff);
		
		List<String[]> rows = o.search(false);
		if (rows.size() > 0){
			double c = o.getCount();
			
			double t = Double.parseDouble(o.getField("df_ligne_devis_prix_vente_impose").getListOperatorValue());
			double total_achat = Double.parseDouble(o.getField("df_ligne_devis_total_achat_ht").getListOperatorValue());
			double nbc = Double.parseDouble(o.getField("df_ligne_devis_nombre_camions").getListOperatorValue());
			double pt = Double.parseDouble(o.getField("df_ligne_devis_poids_total").getListOperatorValue());
			
			
			
			setFieldValue("df_devis_nombre_camions", nbc);
			setFieldValue("df_devis_poids_total", pt);
			
			setFieldValue("df_devi_prix_total_ht", t);
			
			setFieldValue("df_devis_prix_total", t + t*0.2);
			setFieldValue("df_devis_coef_global", t/total_achat);

		}
	}
	
	public void initialCommande(){
		
		// Grant Objet Commande
		ObjectDB c = getGrant().getTmpObject("DF_Commande");
		c.resetFilters();
		
		
		// Get des valeurs devis 
		String num = getField("df_devis_titre").getValue();
		String lieu_affaire = getFieldValue("df_devis_lieu_projet");
		String intitule_affaire = getFieldValue("df_devis_titre_projet");
		
		double poids_total = getField("df_devis_poids_total").getDouble(0);
		double nb_camions = getField("df_devis_nombre_camions").getDouble(0);
		
		// Set Commande
		c.create();
		ObjectField s = c.getField("df_commande_id");
		s.setValue(num);
			
		c.setStatus("IN");	
		
		c.setFieldValue("df_commande_lieu_affaire",lieu_affaire);
		c.setFieldValue("df_commande_intitule_affaire",intitule_affaire);
		c.setFieldValue("df_commande_poids_total",poids_total);
		c.setFieldValue("df_commande_nb_camions",nb_camions);
		c.save();
		
		// Get valeurs ligne devis
		
		ObjectDB ld = getGrant().getTmpObject("DF_Ligne_Devis");
		synchronized(ld){
			ld.resetFilters();
			ld.setFieldFilter("DF_Ligne_Devis_DF_Devis_id",this.getRowId());
			
			for(String[] lde : ld.search()){
				ld.setValues(lde);
				int ref_prod = ld.getField("df_produit_id").getInt(0);
				String type_geo = ld.getFieldValue("df_produit_nom");
				String apl_com = ld.getFieldValue("df_produit_appellation_commerciale");
				String finition = ld.getFieldValue("df_produit_finition");
				String unite_p = ld.getFieldValue("df_produit_unite");

				double poids_u = ld.getField("df_ligne_devis_poids_total").getDouble(0);
				double prd_long = ld.getField("df_produit_long").getDouble(0);
				double prd_larg = ld.getField("df_produit_larg").getDouble(0);
				double prd_eps = ld.getField("df_produit_haut").getDouble(0);
				double prd_qte = ld.getField("df_ligne_devis_quantite").getDouble(0);
				double cmd_prix_exw_u = ld.getField("df_ligne_devis_prix_exw_u").getDouble(0);
				double cmd_total_exw = ld.getField("df_ligne_devis_prix_vente_impose").getDouble(0);
				
				
				ObjectDB lc = getGrant().getTmpObject("DF_ligne_commande");
				lc.resetFilters();
				
				lc.create();
		
		
				ObjectField s2 = lc.getField("df_ligne_commande_id");
				s2.setValue(lc.getRowId());
				
				lc.setFieldValue("df_ligne_commande_ref_prod",ref_prod);
				lc.setFieldValue("df_ligne_commande_type_geo", type_geo);
				lc.setFieldValue("df_ligne_commande_apl_com",apl_com);
				lc.setFieldValue("df_ligne_commande_finition",finition);
				lc.setFieldValue("df_ligne_commande_long",prd_long);
				lc.setFieldValue("df_ligne_commande_larg",prd_larg);
				lc.setFieldValue("df_ligne_commande_eps",prd_eps);
				lc.setFieldValue("df_ligne_commande_poids_u",poids_u);
				lc.setFieldValue("df_ligne_commande_unite",unite_p);
				lc.setFieldValue("df_ligne_commande_qte",prd_qte);
				lc.setFieldValue("df_ligne_commande_prix_exw_u",cmd_prix_exw_u);
				lc.setFieldValue("df_ligne_commande_prix_total_ht",cmd_total_exw);

				lc.setFieldValue("DF_ligne_commande_DF_Commande_id",c.getRowId());

				lc.save();
				
			}
		}
		
		
	}	
	
	public void versionnerDevis(){
		ObjectDB o =  getGrant().getTmpObject("DF_Devis");
		o.resetFilters();
		
		// Versionner Devis	
		
		
		// increment indice 
		String value = getFieldValue("df_devis_indice");
		int charValue = value.charAt(0);
		String next = String.valueOf( (char) (charValue + 1));
		
		
		String num = getField("df_devis_numero").getValue();
		String titre = getField("df_devis_titre").getValue();
		String lieu_affaire = getFieldValue("df_devis_lieu_projet");
		String intitule_affaire = getFieldValue("df_devis_titre_projet");
		
		double poids_total = getField("df_devis_poids_total").getDouble(0);
		double nb_camions = getField("df_devis_nombre_camions").getDouble(0);
		
		o.create();
		o.setStatus("VR");
		o.setFieldValue("df_devis_indice",next);
		o.setFieldValue("df_devis_numero",num);
		o.setFieldValue("df_devis_titre",titre);
		o.setFieldValue("df_devis_lieu_projet",lieu_affaire);
		o.setFieldValue("df_devis_titre_projet",intitule_affaire);
		
		o.setFieldValue("df_devis_poids_total",poids_total);
		o.setFieldValue("df_devis_nombre_camions",nb_camions);
		o.save();
		
		// Versionner Ligne_Devis
		ObjectDB ld2 = getGrant().getTmpObject("DF_Ligne_Devis");
		synchronized(ld2){
			ld2.resetFilters();
			ld2.setFieldFilter("DF_Ligne_Devis_DF_Devis_id",getRowId());
			
			for(String[] lde : ld2.search()){
				ld2.setValues(lde);
				int ref_prod = ld2.getField("df_produit_id").getInt(0);
				String type_geo = ld2.getFieldValue("df_produit_nom");
				String apl_com = ld2.getFieldValue("df_produit_appellation_commerciale");
				String finition = ld2.getFieldValue("df_produit_finition");
				String unite_p = ld2.getFieldValue("df_produit_unite");

				double poids_u = ld2.getField("df_ligne_devis_poids_total").getDouble(0);
				double prd_long = ld2.getField("df_produit_long").getDouble(0);
				double prd_larg = ld2.getField("df_produit_larg").getDouble(0);
				double prd_eps = ld2.getField("df_produit_haut").getDouble(0);
				double prd_qte = ld2.getField("df_ligne_devis_quantite").getDouble(0);
				double cmd_prix_exw_u = ld2.getField("df_ligne_devis_prix_exw_u").getDouble(0);
				double cmd_total_exw = ld2.getField("df_ligne_devis_total_achat_reference_ht").getDouble(0);

				
				ld2.create();
				
				//ObjectField s2 = ld2.getField("df_ligne_devis_id");
				//s2.setValue(getRowId());
				
				ld2.setFieldValue("df_produit_id",ref_prod);
				ld2.setFieldValue("df_produit_nom", type_geo);
				ld2.setFieldValue("df_produit_appellation_commerciale",apl_com);
				ld2.setFieldValue("df_ligne_devis_finition",finition);
				ld2.setFieldValue("df_produit_long",prd_long);
				ld2.setFieldValue("df_produit_larg",prd_larg);
				ld2.setFieldValue("df_produit_haut",prd_eps);
				ld2.setFieldValue("df_ligne_devis_poids_total",poids_u);
				ld2.setFieldValue("df_produit_unite",unite_p);
				ld2.setFieldValue("df_ligne_devis_quantite",prd_qte);
				ld2.setFieldValue("df_ligne_devis_prix_exw_u",cmd_prix_exw_u);
				ld2.setFieldValue("df_ligne_devis_total_achat_reference_ht",cmd_total_exw);

				ld2.setFieldValue("DF_Ligne_Devis_DF_Devis_id",o.getRowId());

				ld2.save();
	}
}

}
	
}