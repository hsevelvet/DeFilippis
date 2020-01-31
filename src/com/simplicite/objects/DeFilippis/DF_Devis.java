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

		
		
	}
	
	
	public void initialCommande(){
		// Grant Objet Ligne Devis
		
		
		
		// Grant Objet Commande
		ObjectDB c = getGrant().getTmpObject("DF_Commande");
		c.resetFilters();
		
		// Grant Objet ligne commande
		//ObjectDB lc = getGrant().getTmpObject("DF_ligne_commande");
		//lc.resetFilters();
		
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
				double cmd_total_exw = ld.getField("df_ligne_devis_total_achat_reference_ht").getDouble(0);
				
				
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
}


	
