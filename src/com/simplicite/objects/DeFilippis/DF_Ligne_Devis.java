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
        
        // accès aux valeurs 
        String unite = getField("df_ligne_devis_unite").getValue();
		double mvp = getField("df_ligne_devis_masse_volumique").getDouble(0);
		double lng = getField("df_ligne_devis_longueur").getDouble(0);
		double lrg = getField("df_ligne_devis_laregur").getDouble(0);
		double ep = getField("df_ligne_devis_epaisseur").getDouble(0);
		int qte = getField("df_ligne_devis_quantite").getInt(0);
		double dim_joint = getField("df_ligne_devis_dim_joints").getDouble(0);
		double ptr = getField("df_ligne_devis_prix_transport_reference").getDouble(0);
		double pexwu = getField("df_ligne_devis_prix_exw_u").getDouble(0); 
		double pexwt = getField("df_ligne_devis_prix_exw_t").getDouble(0);
		
		// calcul nombre d'éléments par unité sans joint
        if (lng == 0 || lrg == 0){
        	setFieldValue("df_ligne_devis_nb_elt_ss_joint", 0);
        }
        else {
        	switch(unite){
				case "m²":
					setFieldValue("df_ligne_devis_nb_elt_ss_joint", 1/((lng / 100)*(lrg /100)));
					break;
				case "ml":
					setFieldValue("df_ligne_devis_nb_elt_ss_joint", 1/(lng / 100));
					break;
				case "u":
					setFieldValue("df_ligne_devis_nb_elt_ss_joint", 1);
					break;
			}
        }
		
        // calcul masse unitaire sans joint
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
        
        // calcul nombre d'éléments par unité avec joint
        if ((lng + dim_joint == 0) || (lrg + dim_joint == 0)){
        	setFieldValue("df_ligne_devis_nb_elt_ac_joint", 0);
        }
        else {
        	switch(unite){
				case "m²":
					setFieldValue("df_ligne_devis_nb_elt_ac_joint", 1/((lng + dim_joint)*(lrg + dim_joint)/10000));
					break;
				case "ml":
					setFieldValue("df_ligne_devis_nb_elt_ac_joint", 0);
					break;
				case "u":
					setFieldValue("df_ligne_devis_nb_elt_ac_joint", 0);
					break;
			}
        }
        
        // calcul masse unitaire avec joint
        if (dim_joint == 0){
        	setFieldValue("df_ligne_devis_masse_unitaire_ac_joint", ((ep * lng * lrg) / 100000));
        }
        else{
        	double n = getField("df_ligne_devis_nb_elt_ac_joint").getDouble(0);
        	setFieldValue("df_ligne_devis_masse_unitaire_ac_joint", ((n * ep * lng * lrg * mvp) / 100000));
        }
        
        // calcul poids total
        double muaj = getField("df_ligne_devis_masse_unitaire_ac_joint").getDouble(0);
        double musj = getField("df_ligne_devis_masse_unitaire_ss_joint").getDouble(0);
		if (muaj == 0){
        	setFieldValue("df_ligne_devis_poids_total", ((musj * qte) / 1000));
        }
        else{
        	setFieldValue("df_ligne_devis_poids_total", ((muaj * qte) / 1000));
        }
        
        // calcul prix transport / unité
        setFieldValue("df_ligne_devis_prix_transport_unitaire", muaj / 24000 * ptr);
        
        // calcul nombre camions 
        double pt = getField("df_ligne_devis_poids_total").getDouble(0);
        setFieldValue("df_ligne_devis_nombre_camions", pt / 24 );
        
        // calcul prix EXW / unité
        if (pexwu == 0){
        	setFieldValue("df_ligne_devis_prix_exw_unite", pexwt * muaj / 1000);
        }
        else {
        	setFieldValue("df_ligne_devis_prix_exw_unite", pexwu);
        }
        
        // calcul prix déboursé sec
        double pexwut = getField("df_ligne_devis_prix_exw_unite").getDouble(0);
        double ptu = getField("df_ligne_devis_prix_transport_unitaire").getDouble(0);
        setFieldValue("df_ligne_devis_prix_debourse_sec",pexwut + ptu);
        
        // calcul total achat reference ht
        setFieldValue("df_ligne_devis_total_achat_reference_ht", pexwut * qte);
        
        // calcul total achat ht
        double pds = getField("df_ligne_devis_prix_debourse_sec").getDouble(0);
        setFieldValue("df_ligne_devis_total_achat_ht", pds * qte);
        
        // calcul prix x coef
        double coef = getField("df_ligne_devis_coef").getDouble(0);
        setFieldValue("df_ligne_devis_prix_vente_calcule", pds * coef);
        
        // calcul prix vente imposé
       double pvi = getField("df_ligne_devis_prix_vente_impose").getDouble(0);
       double pvc = getField("df_ligne_devis_prix_vente_calcule").getDouble(0);
       if (pvi == 0){
       	setFieldValue("df_ligne_devis_prix_vente_impose", pvc);
       }
       else{
       	setFieldValue("df_ligne_devis_prix_vente_impose", pvi);
       }
	}
 
	
}
