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
	public void initUpdate() {
		
		// set values Devis
		String num_devis = getFieldValue("defiDevisNumero");
		//String full_name = getFieldValue("defiUsrNomComplet");
		//String[] nameparts = full_name.split(" ");
		//String trigramme = String.valueOf(nameparts[0].charAt(0)).toUpperCase() +
		//String.valueOf(nameparts[1].charAt(0)).toUpperCase() + String.valueOf(nameparts[1].charAt(2)).toUpperCase();
		String trigramme = getFieldValue("DF_Devis_DF_utilisateur_interne_id.defiUsrTrigramme");
		String lieu = getFieldValue("defiDevisLieuProjet");
		String projet = getFieldValue("defiDevisTitreProjet");
		String client = getFieldValue("defiClientNom");
		String indice = getFieldValue("defiDevisIndice");
		
		String titre_devis = trigramme + "." + lieu + "." + projet + "." + num_devis + "." + indice;
		setFieldValue("defiDevisTitre",titre_devis);
		
		// set values ligne devis
		ObjectDB o = getGrant().getTmpObject("DF_Ligne_Devis");
		o.resetFilters();
		o.getField("DF_Ligne_Devis_DF_Devis_id").setFilter(getRowId());
		
		String dateString = getFieldValue("defiDevisDateEmission");
		Date date_em = Tool.fromDateTime(dateString);
		String c_dateString = Tool.getCurrentDateTime();
		Date date_cur = Tool.fromDateTime(c_dateString);
		double date_diff = days(date_em,date_cur);
		setFieldValue("defiDevisCompteurDate",date_diff);
		
		List<String[]> rows = o.search(false);
		if (rows.size() > 0){
			double c = o.getCount();
			
			double t = Double.parseDouble(o.getField("defiLigneDevisPrixVenteImpose").getListOperatorValue());
			double total_achat = Double.parseDouble(o.getField("defiLigneDevisTotalAchatHT").getListOperatorValue());
			double nbc = Double.parseDouble(o.getField("defiLigneDevisNombreCamions").getListOperatorValue());
			double pt = Double.parseDouble(o.getField("defiLigneDevisPoidsTotal").getListOperatorValue());
			
			
			
			setFieldValue("defiDevisNombreCamions", nbc);
			setFieldValue("defiDevisPoidsTotal", pt);
			
			setFieldValue("defiDevisPrixTotalHT", t);
			
			setFieldValue("defiDevisPrixTotal", t + t*0.2);
			setFieldValue("defiDevisCoefficientGlobal", t/total_achat);
		}
	}
	
	@Override
	public List<String> preValidate() {
		List<String> msgs = new ArrayList<String>();
		
		// Call initUpdate to set field Titre Devis 
		initUpdate();
		
		return msgs;
	}
	
	public String creationAffaire(){
		ObjectDB a = getGrant().getTmpObject("DF_Affaire");
		a.resetFilters();
		
		a.create();
		
		String num_affaire = getField("defiDevisTitre").getValue();
		String lieu_affaire = getFieldValue("defiDevisLieuProjet");
		String intitule_affaire = getFieldValue("defiDevisTitreProjet");
		
		a.setFieldValue("defiAfrNumero",num_affaire);
		a.setFieldValue("defiAfrLibelleChantier",lieu_affaire);
		a.setFieldValue("defiAfrLieuAffaire",intitule_affaire);
		
		a.save();
		
		return sendRedirect(HTMLTool.getFormURL("DF_Affaire","the_main_DF_Affaire", a.getRowId(),""));
	}


	public String initialCommande(){
		
		// Grant Objet Commande
		ObjectDB c = getGrant().getTmpObject("DF_Commande");
		c.resetFilters();
		
		
		// Get des valeurs devis 
		String num = getField("defiDevisTitre").getValue();
		String lieu_affaire = getFieldValue("defiDevisLieuProjet");
		String intitule_affaire = getFieldValue("defiDevisTitreProjet");
		
		double poids_total = getField("defiDevisPoidsTotal").getDouble(0);
		double nb_camions = getField("defiDevisNombreCamions").getDouble(0);
		
		
		// Set Commande
		c.create();
		ObjectField s = c.getField("defiCommandeId");
		s.setValue(num);
			
		c.setStatus("IN");	
		
		c.setFieldValue("defiCommandeLieuAffaire",lieu_affaire);
		c.setFieldValue("defiCommandeIntituleAffaire",intitule_affaire);
		c.setFieldValue("defiCommandePoidsTotal",poids_total);
		c.setFieldValue("defiCommandeNombreCamions",nb_camions);
		c.save();
		
		// Get valeurs ligne devis
		
		ObjectDB ld = getGrant().getTmpObject("DF_Ligne_Devis");
		synchronized(ld){
			ld.resetFilters();
			ld.setFieldFilter("DF_Ligne_Devis_DF_Devis_id",this.getRowId());
			
			for(String[] lde : ld.search()){
				ld.setValues(lde);
				int ref_prod = ld.getField("defiPrdId").getInt(0);
				String type_geo = ld.getFieldValue("defiPrdTypeGeologique");
				String apl_com = ld.getFieldValue("defiPrdAppellationCommerciale");
				String finition = ld.getFieldValue("defiPrdFinitionFacesVues");
				String unite_p = ld.getFieldValue("defiPrdUnite");

				double poids_u = ld.getField("defiLigneDevisPoidsTotal").getDouble(0);
				double prd_long = ld.getField("defiPrdLongueur").getDouble(0);
				double prd_larg = ld.getField("defiPrdLargeur").getDouble(0);
				double prd_eps = ld.getField("defiPrdEpaisseur").getDouble(0);
				double prd_qte = ld.getField("defiLigneDevisQuantite").getDouble(0);
				double cmd_prix_exw_u = ld.getField("defiLigneDevisPrixExwUnitaire").getDouble(0);
				double cmd_total_exw = ld.getField("defiLigneDevisPrixVenteImpose").getDouble(0);
				
				
				ObjectDB lc = getGrant().getTmpObject("DF_ligne_commande");
				lc.resetFilters();
				
				lc.create();
		
		
				ObjectField s2 = lc.getField("defiLigneCommandeId");
				s2.setValue(lc.getRowId());
				
				lc.setFieldValue("defiLigneCommandeReferenceProduit",ref_prod);
				lc.setFieldValue("defiLigneCommandeTypeGeologique", type_geo);
				lc.setFieldValue("defiLigneCommandeAppellationCommerciale",apl_com);
				lc.setFieldValue("defiLigneCommandeFinitionFacesVues",finition);
				lc.setFieldValue("defiLigneCommandeLongueur",prd_long);
				lc.setFieldValue("defiLigneCommandeLargeur",prd_larg);
				lc.setFieldValue("defiLigneCommandeEpaisseur",prd_eps);
				lc.setFieldValue("defiLigneCommandePoidsUnitaire",poids_u);
				lc.setFieldValue("defiLigneCommandeUnite",unite_p);
				lc.setFieldValue("defiLigneCommandeQuantite",prd_qte);
				lc.setFieldValue("defiLigneCommandePrixEXWUnitaire",cmd_prix_exw_u);
				lc.setFieldValue("defiLigneCommandePrixTotalEXW",cmd_total_exw);

				lc.setFieldValue("DF_ligne_commande_DF_Commande_id",c.getRowId());

				lc.save();
				
			}
		}
		
		return sendRedirect(HTMLTool.getFormURL("DF_Commande","the_main_DF_Commande", c.getRowId(),""));
	}	
	
	public void versionnerDevis(){
		ObjectDB o =  getGrant().getTmpObject("DF_Devis");
		o.resetFilters();
		
		// Versionner Devis	
		
		
		// Versionner Devis	
		String indice_current = getFieldValue("defiDevisIndice");
		char x = indice_current.charAt(0);
    	String indice_next = String.valueOf( (char) (x + 1));
    	
    	
		
		
		String num = getField("defiDevisNumero").getValue();
		String titre = getField("defiDevisTitre").getValue();
		String lieu_affaire = getFieldValue("defiDevisLieuProjet");
		String intitule_affaire = getFieldValue("defiDevisTitreProjet");
		String date_emission = getFieldValue("defiDevisDateEmission");
		
		double prix_total_ht = getField("defiDevisPrixTotalHT").getDouble(0);
		double prix_total = getField("defiDevisPrixTotal").getDouble(0);
		double coef_global = getField("defiDevisCoefficientGlobal").getDouble(0);
		
		double poids_total = getField("defiDevisPoidsTotal").getDouble(0);
		double nb_camions = getField("defiDevisNombreCamions").getDouble(0);
		
		o.create();
		o.setStatus("VR");
		o.setFieldValue("defiDevisIndice",indice_current);
		o.setFieldValue("defiDevisNumero",num);
		o.setFieldValue("defiDevisTitre",titre);
		o.setFieldValue("defiDevisLieuProjet",lieu_affaire);
		o.setFieldValue("defiDevisTitreProjet",intitule_affaire);
		o.setFieldValue("defiDevisDateEmission",date_emission);
		
		o.setFieldValue("defiDevisPrixTotalHT", prix_total_ht);
			
		o.setFieldValue("defiDevisPrixTotal", prix_total);
		o.setFieldValue("defiDevisCoefficientGlobal", coef_global);
		o.setFieldValue("defiDevisPoidsTotal",poids_total);
		o.setFieldValue("defiDevisNombreCamions",nb_camions);
		o.save();
		
		setFieldValue("defiDevisIndice",indice_next);
    	validate();
    	save();
		
		// Versionner Ligne_Devis
		ObjectDB ld2 = getGrant().getTmpObject("DF_Ligne_Devis");
		synchronized(ld2){
			ld2.resetFilters();
			ld2.setFieldFilter("DF_Ligne_Devis_DF_Devis_id",getRowId());
			
			for(String[] lde : ld2.search()){
				ld2.setValues(lde);
				int ref_prod = ld2.getField("defiPrdId").getInt(0);
				String type_geo = ld2.getFieldValue("defiPrdTypeGeologique");
				String apl_com = ld2.getFieldValue("defiPrdAppellationCommerciale");
				String finition = ld2.getFieldValue("defiPrdFinitionFacesVues");
				String unite_p = ld2.getFieldValue("defiPrdUnite");

				double poids_u = ld2.getField("defiLigneDevisPoidsTotal").getDouble(0);
				double prd_long = ld2.getField("defiPrdLongueur").getDouble(0);
				double prd_larg = ld2.getField("defiPrdLargeur").getDouble(0);
				double prd_eps = ld2.getField("defiPrdEpaisseur").getDouble(0);
				double prd_qte = ld2.getField("defiLigneDevisQuantite").getDouble(0);
				double cmd_prix_exw_u = ld2.getField("defiLigneDevisPrixExwUnitaire").getDouble(0);
				double cmd_total_exw = ld2.getField("defiLigneDevisTotalEXWHT").getDouble(0);

				
				ld2.create();
				
				ld2.setFieldValue("defiPrdId",ref_prod);
				ld2.setFieldValue("defiPrdTypeGeologique", type_geo);
				ld2.setFieldValue("defiPrdAppellationCommerciale",apl_com);
				ld2.setFieldValue("defiLigneDevisDesignation",finition);
				ld2.setFieldValue("defiPrdLongueur",prd_long);
				ld2.setFieldValue("defiPrdLargeur",prd_larg);
				ld2.setFieldValue("defiPrdEpaisseur",prd_eps);
				ld2.setFieldValue("defiLigneDevisPoidsTotal",poids_u);
				ld2.setFieldValue("defiPrdUnite",unite_p);
				ld2.setFieldValue("defiLigneDevisQuantite",prd_qte);
				ld2.setFieldValue("defiLigneDevisPrixExwUnitaire",cmd_prix_exw_u);
				ld2.setFieldValue("defiLigneDevisTotalEXWHT",cmd_total_exw);

				ld2.setFieldValue("DF_Ligne_Devis_DF_Devis_id",o.getRowId());

				ld2.save();
	}
}

}
	
}