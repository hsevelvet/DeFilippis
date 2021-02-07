package com.simplicite.objects.DeFilippis;

import java.io.ByteArrayOutputStream;
import com.simplicite.util.PrintTemplate;
import org.json.JSONObject;

import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import com.simplicite.util.EnumItem;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.lang.Object;
import com.simplicite.util.tools.MailTool; 


import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;  
import com.simplicite.util.*;
import com.simplicite.util.ObjectDB;
import com.simplicite.objects.DeFilippis.DF_Ligne_Devis;
import com.simplicite.util.tools.*;
import com.simplicite.util.tools.HTMLTool; 
import com.simplicite.webapp.web.BootstrapWebPage;
import com.simplicite.util.tools.PDFTool;
import com.simplicite.util.tools.MailTool;
import com.simplicite.util.Mail;
import com.simplicite.util.PrintTemplate; 




/**
 * Business object DF_Devis
*/

public class DF_Devis extends ObjectDB {

	private static final long serialVersionUID = 1L;
	
	
	/** Mail Option
	/** Default Message 
	public static final String DEFAULT_MESSAGE = "test message";
	*/
	//Init Default Message 
	public static final String DEFAULT_MESSAGE = "test message";
	public void initAction(Action action){
		if("Envoyer-mail".equals(action.getName())){
			ObjectField f = action.getConfirmField(getGrant().getLang(),"defiDevisContactMail");
			//if (f!=null) f.setDefaultValue(String.valueOf(DEFAULT_MESSAGE));
			}
	}
	
	
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
	
	
	
	// Valoriser la date validité d'offre lors de la création d'un devis : date actuelle + 3 mois
	@Override
	public void initCreate() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH,3);
		int year = cal.get(Calendar.YEAR);
		setFieldValue("defiDevisDateValiditeOffre",cal.getTime());
		setFieldValue("defiDevisNumero",String.valueOf(year)+String.format(".%05d",Long.valueOf(this.getRowId())));
		
	}

	@Override
	public void initUpdate() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		setFieldValue("defiDevisNumero",String.valueOf(year)+String.format(".%05d",Long.valueOf(this.getRowId())));
		// Création du titre devis
		String num_devis = getFieldValue("defiDevisNumero");
		String trigramme = getFieldValue("DF_Devis_DF_utilisateur_interne_id.defiUsrTrigramme");
		String lieu = getFieldValue("defiDevisLieuProjet");
		String projet = getFieldValue("defiDevisTitreProjet");
		String client = getFieldValue("defiClientNom");
		String indice = getFieldValue("defiDevisIndice");
		
		String titre_devis = trigramme + "." + lieu + "." + projet + "." + num_devis + "." + indice;
		setFieldValue("defiDevisTitre",titre_devis);
		
		// Création du compteur date : utile pour dashboards (devis en chantier > 45j)
		ObjectDB o = getGrant().getTmpObject("DF_Ligne_Devis");
		o.resetFilters();
		o.getField("DF_Ligne_Devis_DF_Devis_id").setFilter(getRowId());
		
		String dateString = getFieldValue("defiDevisDateEmission");
		Date date_em = Tool.fromDateTime(dateString);
		String c_dateString = Tool.getCurrentDateTime();
		Date date_cur = Tool.fromDateTime(c_dateString);
		double date_diff = days(date_em,date_cur);
		setFieldValue("defiDevisCompteurDate",date_diff);
		// Alimentation des champs devis nécessitant des calculs sur ligne devis
		List<String[]> rows = o.search(false);
		if (rows.size() > 0){
			double c = o.getCount();
			
			double t = Double.parseDouble(o.getField("defiLigneDevisPrixVenteImpose").getListOperatorValue());
			double total_achat = Double.parseDouble(o.getField("defiLigneDevisTotalAchatHT").getListOperatorValue());
			double nbc = Double.parseDouble(o.getField("defiLigneDevisNombreCamions").getListOperatorValue());
			double pt = Double.parseDouble(o.getField("defiLigneDevisPoidsTotal").getListOperatorValue());
			double total_ac_trsp = Double.parseDouble(o.getField("defiLigneDevisPrixTransportTotal").getListOperatorValue());
			double total_exw_ht = Double.parseDouble(o.getField("defiLigneDevisTotalEXWHT").getListOperatorValue());
			
			
			// Nombre de camions 
			setFieldValue("defiDevisNombreCamions", nbc);
			// Poids Total
			setFieldValue("defiDevisPoidsTotal", pt);
			// Prix Total HT
			setFieldValue("defiDevisPrixTotalHT", t);
			// Prix Total TTC
			setFieldValue("defiDevisPrixTotal", t + t*0.2);
			// Coefficition global : total ventes / total achats
			setFieldValue("defiDevisCoefficientGlobal", t/total_achat);
			
			//Total Achat Fourniture
			setFieldValue("defiDevisTotalACFournHT", total_exw_ht);
			
			// Total Achat Transport
			setFieldValue("defiDevisTotalAchatTransport", total_ac_trsp);
			
			// Total Achat HT
			setFieldValue("defiDevisTotalACHT", total_ac_trsp + total_exw_ht);
		}
	}
	
	@Override
	public List<String> preValidate() {
		List<String> msgs = new ArrayList<String>();
		
		// Call initUpdate to set field Titre Devis 
		initUpdate();
		
		return msgs;
	}

	/**
 	* Action : Initialisation Commande
	*/

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
		String libelle_affaire = getFieldValue("DF_Devis_DF_Chantier_id.defiAfrLibelleChantier");
		String User = getFieldValue("DF_Devis_DF_utilisateur_interne_id.defiUsrNC");
		String Redacteur = getFieldValue("defiDevisRedacteur");
		String otp_affaire = getFieldValue("DF_Devis_DF_Chantier_id.defiAfrNumero");	// JDE 2020.09.28 : ajout du numero  OTP
		
		int index_accompte = getField("defiDevisAccompte").getList().getItemIndex(getFieldValue("defiDevisAccompte"),false);
		String accompte = getField("defiDevisAccompte").getList().getValue(index_accompte);
		
		int index_conditions = getField("defiDevisIncotermPrix").getList().getItemIndex(getFieldValue("defiDevisIncotermPrix"),false);
		String conditions = getField("defiDevisIncotermPrix").getList().getValue(index_conditions);
		
		int index_contenance = getField("defiDevisContenance").getList().getItemIndex(getFieldValue("defiDevisContenance"),false);
		String contenance = getField("defiDevisContenance").getList().getValue(index_contenance);
		
		int index_pack_transp = getField("defiDevisPackagingTransport").getList().getItemIndex(getFieldValue("defiDevisPackagingTransport"),false);
		String pack_transp = getField("defiDevisPackagingTransport").getList().getValue(index_pack_transp);
		
		// Set Commande
		c.create();

		c.setFieldValue("DF_Commande_DF_Affaire_id", getFieldValue("DF_Devis_DF_Chantier_id"));
		c.setFieldValue("DF_Commande_DF_utilisateur_interne_id", getFieldValue("DF_Devis_DF_utilisateur_interne_id"));	
		c.setFieldValue("DF_Commande_DF_Client_id", this.getFieldValue("DF_Devis_DF_Client_id"));
		c.setFieldValue("DF_Commande_DF_Contact_id", this.getFieldValue("DF_Devis_DF_Contact_id"));
		c.setFieldValue("defiCommandeRedacteur", Redacteur);
		c.setStatus("IN");	
		c.setFieldValue("defiCommandeLieuAffaire",lieu_affaire);
		c.setFieldValue("defiCommandeIntituleAffaire",intitule_affaire);
		c.setFieldValue("defiCommandePoidsTotal",poids_total);
		c.setFieldValue("defiCommandeNombreCamions",nb_camions);
		c.setFieldValue("defiAfrNumero",otp_affaire); // JDE 2020.09.28 : ajout du numero  OTP		
		
		c.setFieldValue("defiCommandeAccompte",accompte);
		c.setFieldValue("defiCommandeIncotermPrix",conditions);
		c.setFieldValue("defiCommandeContenance",contenance);
		c.setFieldValue("defiCommandePackagingTransport",pack_transp);
		c.setFieldValue("defiCommandeCadenceLivraison",getFieldValue("defiDevisCadenceLivraison"));
		
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.WEEK_OF_MONTH,6);
		c.setFieldValue("defiCommandeDate", date);
		c.setFieldValue("defiCommandeDatePremierCamion",cal.getTime());
		
		c.validate();
		c.save();
		
		// Get valeurs ligne devis
		
		ObjectDB ld = getGrant().getTmpObject("DF_Ligne_Devis");
		synchronized(ld){
			ld.resetFilters();
			ld.setFieldFilter("DF_Ligne_Devis_DF_Devis_id",this.getRowId());
			
			
			for(String[] lde : ld.search()){
				ld.setValues(lde);
				String ref_prod = ld.getFieldValue("defiLigneDevisNPrix");
				String type_geo = ld.getFieldValue("defiLigneDevisTypeGeo");
				String apl_com = ld.getFieldValue("defiLigneDevisAppellationCommerciale");
				String finition = ld.getFieldValue("defiLigneDevisFinitionFV");
				String unite_p = ld.getFieldValue("defiLigneDevisU");
				String designation = ld.getFieldValue("defiLigneDevisDesignation");
				String fournisseur = ld.getFieldValue("defiLigneDevisFournisseur");
				
				// Recherche fournisseur produit
				ObjectDB pf = getGrant().getTmpObject("DF_Produit_Finis");
				/**String fournisseur = null;
				synchronized(pf){
					pf.resetFilters();
				
					pf.setFieldFilter("row_id",ld.getFieldValue("DF_Ligne_Devis_DF_Produit_Finis_id"));
					for(String[] pfe : pf.search()){
						pf.setValues(pfe);
						fournisseur = pf.getFieldValue("DF_Produit_Finis_DF_Fournisseurs_id");
						AppLog.info(getClass(), "fournisseur", fournisseur, getGrant());
					}
				}*/
				

				double poids_u = ld.getField("defiLigneDevisPoidsUnitaire").getDouble(0);
				//double prd_long = ld.getField("defiLigneDevisLongueur").getDouble(0);
				//double prd_larg = ld.getField("defiLigneDevisLargeur").getDouble(0);
				//double prd_eps = ld.getField("defiLigneDevisEpaisseur").getDouble(0);
				String prd_long = ld.getFieldValue("defiLigneDevisLongueur");
				String prd_larg = ld.getFieldValue("defiLigneDevisLargeur");
				String prd_eps = ld.getFieldValue("defiLigneDevisEpaisseur");
				double prd_qte = ld.getField("defiLigneDevisQuantite").getDouble(0);
				double cmd_prix_exw_u = ld.getField("defiLigneDevisPrixUnitaireImpose").getDouble(0);
				double cmd_prix_calc = ld.getField("defiLigneDevisPrixVenteCalcule").getDouble(0);
				
				int index_cat_prix = ld.getField("defiLigneDevisCatPrix").getList().getItemIndex(ld.getFieldValue("defiLigneDevisCatPrix"),false);
				String cat_prix = ld.getField("defiLigneDevisCatPrix").getList().getValue(index_cat_prix);
				
				
				ObjectDB lc = getGrant().getTmpObject("DF_ligne_commande");
				lc.resetFilters();
				// set des valeurs ligne commande
				lc.create();
		
		
				//ObjectField s2 = lc.getField("defiLigneCommandeId");
				//s2.setValue(lc.getRowId());
				/**if (fournisseur.equals(null) || fournisseur.isEmpty() || fournisseur.equals("")){
					AppLog.info(getClass(), "fournisseur2", fournisseur, getGrant());
					lc.setFieldValue("DF_ligne_commande_DF_Fournisseurs_id", " ");
				}else{
					lc.getField("DF_ligne_commande_DF_Fournisseurs_id").setValue(fournisseur);
					//lc.setFieldValue("defiLigneCommandeFournisseur", lc.getFieldValue("DF_ligne_commande_DF_Fournisseurs_id.defiFournNom"));
					//lc.save();
				}*/
				
				lc.setFieldValue("defiLigneCommandeNmFourn", fournisseur);
				lc.setFieldValue("defiLigneCommandeCatPrix",ld.getFieldValue("defiLigneDevisCatPrix"));
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
				lc.setFieldValue("defiLigneCommandePoidsTotal", (prd_qte * poids_u)/1000);
				lc.setFieldValue("defiLigneCommandeDesignation",designation);
				if (cmd_prix_exw_u != 0){
					lc.setFieldValue("defiLigneCommandePrixEXWUnitaire",cmd_prix_exw_u);
				} else{
					lc.setFieldValue("defiLigneCommandePrixEXWUnitaire",cmd_prix_calc);
				}
				
				double prix_unitaire = lc.getField("defiLigneCommandePrixEXWUnitaire").getDouble(0);
				double qte =  lc.getField("defiLigneCommandeQuantite").getDouble(0);
				lc.setFieldValue("defiLigneCommandePrixTotalEXW",prix_unitaire * qte);
				
				

				lc.setFieldValue("DF_ligne_commande_DF_Commande_id",c.getRowId());
				lc.validate();
				lc.save();
				
			}
		}

		// sendRedirect pour se rederiger vers la page commande au moment d'éxécution de l'action : initialisation commande 
		return sendRedirect(HTMLTool.getFormURL("DF_Commande","the_main_DF_Commande", c.getRowId(),""));
	}	
	
	/**
 	* Action : Versionner Devis
	*/
	public void versionnerDevis(){
		ObjectDB o =  getGrant().getTmpObject("DF_Devis");
		o.resetFilters();
		
		
		// Fonction pour incrémenter l'indice
		String indice_current = getFieldValue("defiDevisIndice");
		char x = indice_current.charAt(0);
    	String indice_next = String.valueOf( (char) (x + 1));
    	
    	
		
		// Get des valeurs Devis
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

		// Set des valeurs devis
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
		o.setFieldValue("DF_Devis_DF_Chantier_id", this.getFieldValue("DF_Devis_DF_Chantier_id"));
		o.setFieldValue("DF_Devis_DF_Client_id", this.getFieldValue("DF_Devis_DF_Client_id"));
		o.setFieldValue("DF_Devis_DF_Contact_id", this.getFieldValue("DF_Devis_DF_Contact_id"));
		o.setFieldValue("DF_Devis_DF_utilisateur_interne_id", this.getFieldValue("DF_Devis_DF_utilisateur_interne_id"));
		o.save();
		// incrémentation d'indice du devis actuel
		setFieldValue("defiDevisIndice",indice_next);
    	validate();
    	save();
		
		
		// Get des valeurs ligne devis
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

				// set des valeurs ligne devis
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

	////////////////////////////// Print DEVIS //////////////////////////////////////
	public String pubDevis(){
		BootstrapWebPage wp = new BootstrapWebPage(
			HTMLTool.getRoot(), 
			"Webpage publication pattern example", 
			true
		);
		
		// Ajout de valeurs de Devis
	    ObjectDB d = getGrant().getTmpObject("DF_Devis");
	    d.resetFilters();
		d.setFieldFilter("row_id",getRowId());
		initUpdate(); // JDE - 20200928 : pour récupérer les bons montants totaux en cas de modif de ligne de devis.
		
		// section : granit
		ObjectDB ld_gra = getGrant().getTmpObject("DF_Ligne_Devis");
		ld_gra.resetFilters();
		ld_gra.setFieldFilter("DF_Ligne_Devis_DF_Devis_id",getRowId());
		ld_gra.setFieldFilter("defiLigneDevisTypeGeo","GRA");
		
		String gra = ld_gra.toJSON(ld_gra.search(), null, false, false);
		
		double total_gra = 0;
		List<String[]> rows_gra = ld_gra.search(false);
		if (rows_gra.size() > 0){
			double c = ld_gra.getCount();
			
			total_gra = Double.parseDouble(ld_gra.getField("defiLigneDevisPrixVenteImpose").getListOperatorValue());
		}
		
		// section : calcaire
		ObjectDB ld_cal = getGrant().getTmpObject("DF_Ligne_Devis");
		ld_cal.resetFilters();
		ld_cal.setFieldFilter("DF_Ligne_Devis_DF_Devis_id",getRowId());
		ld_cal.setFieldFilter("defiLigneDevisTypeGeo","CAL");
		
		String cal = ld_cal.toJSON(ld_cal.search(), null, false, false);
		
		double total_cal = 0;
		List<String[]> rows_cal = ld_cal.search(false);
		if (rows_cal.size() > 0){
			double c = ld_cal.getCount();
			
			total_cal = Double.parseDouble(ld_cal.getField("defiLigneDevisPrixVenteImpose").getListOperatorValue());
		}
		
		// section : basalte
		ObjectDB ld_bas = getGrant().getTmpObject("DF_Ligne_Devis");
		ld_bas.resetFilters();
		ld_bas.setFieldFilter("DF_Ligne_Devis_DF_Devis_id",getRowId());
		ld_bas.setFieldFilter("defiLigneDevisTypeGeo","BAS");
		
		String bas = ld_bas.toJSON(ld_bas.search(), null, false, false);
		
		double total_bas = 0;
		List<String[]> rows_bas = ld_bas.search(false);
		if (rows_bas.size() > 0){
			double c = ld_bas.getCount();
			
			total_bas = Double.parseDouble(ld_bas.getField("defiLigneDevisPrixVenteImpose").getListOperatorValue());
		}
		
		// section : Grès
		ObjectDB ld_gre = getGrant().getTmpObject("DF_Ligne_Devis");
		ld_gre.resetFilters();
		ld_gre.setFieldFilter("DF_Ligne_Devis_DF_Devis_id",getRowId());
		ld_gre.setFieldFilter("defiLigneDevisTypeGeo","GRE");
		
		String gre = ld_gre.toJSON(ld_gre.search(), null, false, false);
		
		double total_gre = 0;
		List<String[]> rows_gre = ld_gre.search(false);
		if (rows_gre.size() > 0){
			double c = ld_gre.getCount();
			
			total_gre = Double.parseDouble(ld_gre.getField("defiLigneDevisPrixVenteImpose").getListOperatorValue());
		}
		
		// section : Porphyre
		ObjectDB ld_por = getGrant().getTmpObject("DF_Ligne_Devis");
		ld_por.resetFilters();
		ld_por.setFieldFilter("DF_Ligne_Devis_DF_Devis_id",getRowId());
		ld_por.setFieldFilter("defiLigneDevisTypeGeo","POR");
		
		String por = ld_por.toJSON(ld_por.search(), null, false, false);
		
		double total_por = 0;
		List<String[]> rows_por = ld_por.search(false);
		if (rows_por.size() > 0){
			double c = ld_por.getCount();
			
			total_por = Double.parseDouble(ld_por.getField("defiLigneDevisPrixVenteImpose").getListOperatorValue());
		}
		
		// section : Luzerne
		ObjectDB ld_lus = getGrant().getTmpObject("DF_Ligne_Devis");
		ld_lus.resetFilters();
		ld_lus.setFieldFilter("DF_Ligne_Devis_DF_Devis_id",getRowId());
		ld_lus.setFieldFilter("defiLigneDevisTypeGeo","LUS");
		
		String lus = ld_lus.toJSON(ld_lus.search(), null, false, false);
		
		double total_lus = 0;
		List<String[]> rows_lus = ld_lus.search(false);
		if (rows_lus.size() > 0){
			double c = ld_lus.getCount();
			
			total_lus = Double.parseDouble(ld_lus.getField("defiLigneDevisPrixVenteImpose").getListOperatorValue());
		}
		
		// section : Terre cuite
		ObjectDB ld_tec = getGrant().getTmpObject("DF_Ligne_Devis");
		ld_tec.resetFilters();
		ld_tec.setFieldFilter("DF_Ligne_Devis_DF_Devis_id",getRowId());
		ld_tec.setFieldFilter("defiLigneDevisTypeGeo","TEC");
		
		String tec = ld_tec.toJSON(ld_tec.search(), null, false, false);
		
		double total_tec = 0;
		List<String[]> rows_tec = ld_tec.search(false);
		if (rows_tec.size() > 0){
			double c = ld_tec.getCount();
			
			total_tec = Double.parseDouble(ld_tec.getField("defiLigneDevisPrixVenteImpose").getListOperatorValue());
		}
		
		// section : Produit de réemploi
		ObjectDB ld_pre = getGrant().getTmpObject("DF_Ligne_Devis");
		ld_pre.resetFilters();
		ld_pre.setFieldFilter("DF_Ligne_Devis_DF_Devis_id",getRowId());
		ld_pre.setFieldFilter("defiLigneDevisTypeGeo","PRE");
		
		String pre = ld_pre.toJSON(ld_pre.search(), null, false, false);
		
		double total_pre= 0;
		List<String[]> rows_pre = ld_pre.search(false);
		if (rows_pre.size() > 0){
			double c = ld_pre.getCount();
			
			total_pre = Double.parseDouble(ld_pre.getField("defiLigneDevisPrixVenteImpose").getListOperatorValue());
		}
	
		// user (Suiveur)
		ObjectDB u = getGrant().getTmpObject("User");
		u.resetFilters();
		u.setFieldFilter("row_id",getFieldValue("DF_Devis_DF_utilisateur_interne_id"));
		
		// user (Redacteur)
		ObjectDB redacteur = getGrant().getTmpObject("User");
		redacteur.resetFilters();
		redacteur.setFieldFilter("row_id",getFieldValue("DF_Devis_DF_utilisateur_interne_id"));		

		double prix_total = getField("defiDevisPrixTotalHT").getDouble();
		double prix_tva = (int)(Math.round(prix_total*0.2 * 100))/100.0;
		double prix_totalttc = prix_total + prix_tva;
		double poids_total = getField("defiDevisPoidsTotal").getDouble();
		double nombre_camions = getField("defiDevisNombreCamions").getDouble();
		
		// Get des valeurs enum sur devis
		int index_accompte = d.getField("defiDevisAccompte").getList().getItemIndex(getFieldValue("defiDevisAccompte"),false);
		String accompte = d.getField("defiDevisAccompte").getList().getValue(index_accompte);
		
		int index_paiement = d.getField("defiDevisDelaiPaiement").getList().getItemIndex(getFieldValue("defiDevisDelaiPaiement"),false);
		String paiement = d.getField("defiDevisDelaiPaiement").getList().getValue(index_paiement);
		
		int index_pack_transp = d.getField("defiDevisPackagingTransport").getList().getItemIndex(getFieldValue("defiDevisPackagingTransport"),false);
		String pack_transp = d.getField("defiDevisPackagingTransport").getList().getValue(index_pack_transp);
		
		int index_contenance = d.getField("defiDevisContenance").getList().getItemIndex(getFieldValue("defiDevisContenance"),false);
		String contenance = d.getField("defiDevisContenance").getList().getValue(index_contenance);
		
		int index_conditions = d.getField("defiDevisIncotermPrix").getList().getItemIndex(getFieldValue("defiDevisIncotermPrix"),false);
		String conditions = d.getField("defiDevisIncotermPrix").getList().getValue(index_conditions);
		
	
		wp.append(MustacheTool.apply(
			this,
			"DF_Devis_HTML", 
			"{'rows':"+d.toJSON(d.search(), null, false, false)+
			",'rows_ld_bas':"+bas+
			",'rows_ld_cal':"+cal+
			",'rows_ld_gra':"+gra+
			",'rows_ld_gre':"+gre+
			",'rows_ld_lus':"+lus+
			",'rows_ld_pre':"+pre+
			",'rows_ld_por':"+por+
			",'rows_ld_tec':"+tec+
			",'rows_u':"+u.toJSON(u.search(), null, false, false)+
			",'poids_total':"+ "[{'poids_total':"+Double.toString(poids_total)+"}]"+
			",'nombre_camions':"+ "[{'nombre_camions':"+Double.toString(nombre_camions)+"}]"+			
			",'px_totalht':"+ "[{'px_totalht':"+Double.toString(prix_total)+"}]"+
			",'tva':"+ "[{'prix_tva':"+Double.toString(prix_tva)+"}]"+
			",'prix_totalttc':"+ "[{'prix_totalttc':"+Double.toString(prix_totalttc)+"}]"+
			",'prix_totalbas':"+ "[{'prix_totalbas':"+Double.toString(total_bas)+"}]"+
			",'prix_totalcal':"+ "[{'prix_totalcal':"+Double.toString(total_cal)+"}]"+
			",'prix_totalgra':"+ "[{'prix_totalgra':"+Double.toString(total_gra)+"}]"+
			",'prix_totalgre':"+ "[{'prix_totalgre':"+Double.toString(total_gre)+"}]"+
			",'prix_totallus':"+ "[{'prix_totallus':"+Double.toString(total_lus)+"}]"+
			",'prix_totalpre':"+ "[{'prix_totalpre':"+Double.toString(total_pre)+"}]"+
			",'prix_totalpor':"+ "[{'prix_totalpor':"+Double.toString(total_por)+"}]"+
			",'prix_totaltec':"+ "[{'prix_totaltec':"+Double.toString(total_tec)+"}]"+
			",'accompte':"+ "[{'accompte_l':"+accompte+"}]"+
			",'paiement':"+ "[{'paiement_l':"+paiement+"}]"+
			",'pack_transp':"+ "[{'pack_transp_l':"+pack_transp+"}]"+
			",'contenance':"+ "[{'contenance_l':"+contenance+"}]"+
			",'conditions':"+ "[{'conditions_l':"+conditions+"}]"+
			"}"
			));

		return wp.getHTML();
	
	}
	
	public byte[] pubPdf(PrintTemplate pt){
		String url = "http://wkhtml2pdf/";
		String user = null;
		String password = null;
		
		
		JSONObject postData = new JSONObject();
		
		postData.put("contents", Tool.toBase64(pubDevis()));
		String[] headers = {"Content-Type:application/json"};
		String encoding = Globals.BINARY;
		byte[] pdf = null;
		
		ObjectDB devis = getGrant().getTmpObject("DF_Devis");
		ObjectField devis_fiche = devis.getField("defiDevisFicheTechnique"); // must be of type file
		String titreDevis = this.getFieldValue("defiDevisTitre");
		String titreDevis1 = titreDevis.replaceAll("\\s","")+".pdf";
	
		pt.setFilename(titreDevis1);
		//pt.setFilename("AGN.93170BAGNOLET.ZACBenoitHure.2020.00152.C.pdf");
		

		/**https://www.simplicite.io/resources/4.0/javadoc/com/simplicite/util/tools/MailTool.html
		new Mail(getGrant()).send(
					"alfredtw19@gmail.com",
					"hsenoussi@velvetconsulting.com",
					"test-hs",
					"<html><body>" +
					"<h3>Hello,</h3>" +
					"</body></html>");
		**/

		try{
			
			pdf = Tool.readUrlAsByteArray(url, user, password, postData.toString(), headers, encoding);
			
			
		
		}catch(Exception e){
			AppLog.error(getClass(), "pubPdf", "------------", e, getGrant());
		}
		
		return pdf;
	}
	
	// Méthode pour historiser un Devis
	public String generateFile() {
		ObjectDB hst = getGrant().getTmpObject("DF_Hist_Docs");

		try {
			synchronized(hst){
				hst.resetFilters();
					SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy-HH");  
    				Date date = new Date();  
    				PrintTemplate ptDevis = getPrintTemplate("Devis-PDF");
					hst.create();
					hst.getField("defiHstDocsDevis").setDocument(hst, "Devis-"+formatter.format(date).toString()+".pdf", this.pubPdf(ptDevis));
					hst.setFieldValue("DF_Hist_Docs_DF_Devis_id",getRowId());
					hst.setFieldValue("defiHstDocsDateEmission",date);
					hst.setFieldValue("defiHstTitre", this.getFieldValue("defiDevisTitre"));
					
					ObjectField file = hst.getField("defiHstDocsDevis");
					
					hst.save();
			
				
				
			}
			return Message.formatSimpleInfo("Fichier Historisé");
		}	
		catch(Exception e) {
		    AppLog.error(getClass(), "generateFile", "error...", e, getGrant());
		    return Message.formatSimpleError("Error...");
		}
	}
	
	// Méthode Envoi mail
	public String SendMailDevis(Map<String, String> params){
		ObjectDB devis = getGrant().getTmpObject("DF_Devis");
		ObjectField devis_fiche = devis.getField("defiDevisFicheTechnique");
		ObjectField devis_pj1 = devis.getField("defiDevisPieceJointe1");
		ObjectField devis_pj2 = devis.getField("defiDevisPieceJointe2");
		ObjectField devis_pj3 = devis.getField("defiDevisPieceJointe3");
		ObjectField devis_pj4 = devis.getField("defiDevisPieceJointe4");
		//ObjectField email_address = devis.getField("defiDevisContactMail");
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy-HH");  
    	Date date = new Date();  
    	PrintTemplate pt = getPrintTemplate("Devis-PDF");
		
		devis.getField("defiDevisEmailPDF").setDocument(devis,getFieldValue("defiDevisTitre")+".pdf", this.pubPdf(pt));
		
		ObjectField devis_pdf = devis.getField("defiDevisEmailPDF");
		
		String email_address = params.get("defiDevisContactMail") ;
		AppLog.info(getClass(), "Address", params.get("defiDevisContactMail"), getGrant());
		
		MailTool mail = new MailTool();
		mail.addRcpt(email_address);
		mail.setSubject(getFieldValue("defiDevisTitre"));
		
		mail.addAttach(devis, devis_pdf);
		mail.addAttach(devis, devis_fiche);
		mail.addAttach(devis, devis_pj1);
		mail.addAttach(devis, devis_pj2);
		mail.addAttach(devis, devis_pj3);
		mail.addAttach(devis, devis_pj4);
		
		String content = params.get("defiDevisMail");
		mail.setContent(content);
		mail.send();
		
		return null;
	}
		
}