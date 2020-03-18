package com.simplicite.objects.DeFilippis;

import java.io.ByteArrayOutputStream;
import com.simplicite.util.PrintTemplate;
import org.json.JSONObject;

import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;



import java.util.*;
import java.util.Date;
import com.simplicite.util.*;
import com.simplicite.util.ObjectDB;
import com.simplicite.objects.DeFilippis.DF_Ligne_Devis;
import com.simplicite.util.tools.*;
import com.simplicite.util.tools.HTMLTool; 
import com.simplicite.webapp.web.BootstrapWebPage;
import com.simplicite.util.tools.PDFTool;
import com.simplicite.util.tools.MailTool;
import com.simplicite.util.Mail;


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
	public void initCreate() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH,3);
		setFieldValue("defiDevisDateValiditeOffre",cal.getTime());
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
		String User = getFieldValue("DF_Devis_DF_utilisateur_interne_id.defiUsrNomComplet");
		String Redacteur = getFieldValue("defiDevisRedacteur");
		
		// Set Commande
		c.create();

		c.setFieldValue("DF_Commande_DF_Affaire_id", getFieldValue("DF_Devis_DF_Chantier_id"));
		c.setFieldValue("DF_Commande_DF_utilisateur_interne_id", getFieldValue("DF_Devis_DF_utilisateur_interne_id"));	
		c.setFieldValue("DF_Commande_DF_Client_id", getFieldValue("DF_Devis_DF_Client_id"));
		c.setFieldValue("defiCommandeRedacteur", Redacteur);
		c.setStatus("IN");	
		//c.setFieldValue("DF_Commande_DF_Affaire_id.defiAfrLibelleChantier", libelle_affaire );
		//c.setFieldValue("DF_Commande_DF_utilisateur_interne_id.defiUsrNomComplet", User );
	
		//c.setFieldValue("defiCommandeSuiveurAffaire", getFieldValue("DF_Devis_DF_utilisateur_interne_id.defiUsrNomComplet"));
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
				String ref_prod = ld.getFieldValue("defiPrdId");
				String type_geo = ld.getFieldValue("defiPrdTypeGeologique");
				String apl_com = ld.getFieldValue("defiPrdAppellationCommerciale");
				String finition = ld.getFieldValue("defiPrdFinitionFacesVues");
				String unite_p = ld.getFieldValue("defiPrdUnite");
				String designation = ld.getFieldValue("defiLigneDevisDesignation");

				double poids_u = ld.getField("defiLigneDevisPoidsTotal").getDouble(0);
				double prd_long = ld.getField("defiPrdLongueur").getDouble(0);
				double prd_larg = ld.getField("defiPrdLargeur").getDouble(0);
				double prd_eps = ld.getField("defiPrdEpaisseur").getDouble(0);
				double prd_qte = ld.getField("defiLigneDevisQuantite").getDouble(0);
				double cmd_prix_exw_u = ld.getField("defiLigneDevisPrixUnitaireImpose").getDouble(0);
				double cmd_total_exw = ld.getField("defiLigneDevisPrixUnitaireImpose").getDouble(0);
				
				
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
				lc.setFieldValue("defiLigneCommandeDesignation",designation);
				lc.setFieldValue("defiLigneCommandePrixEXWUnitaire",cmd_prix_exw_u);
				
				double prix_unitaire = lc.getField("defiLigneCommandePrixEXWUnitaire").getDouble(0);
				double qte =  lc.getField("defiLigneCommandeQuantite").getDouble(0);
				lc.setFieldValue("defiLigneCommandePrixTotalEXW",prix_unitaire * qte);
				
				

				lc.setFieldValue("DF_ligne_commande_DF_Commande_id",c.getRowId());

				lc.save();
				
			}
		}
		/*
		ObjectDB lc = getGrant().getTmpObject("DF_ligne_commande");
		double t = Double.parseDouble(lc.getField("defiLigneCommandePrixTotalEXW").getListOperatorValue());
		c.setFieldValue("defiCommandeMontantHT", t);
		c.save();*/
		
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
	////////////////////////////// Print DEVIS //////////////////////////////////////
	public String pubDevis(){
		BootstrapWebPage wp = new BootstrapWebPage(
			HTMLTool.getRoot(), 
			"Webpage publication pattern example", 
			true
		);
		
	// Ajout de valeurs de Devis
	    ObjectDB d = getGrant().getTmpObject("DF_Devis");
	    

		d.setFieldFilter("row_id",getRowId());

		
		ObjectDB ld = getGrant().getObject("DF_Ligne_Devis","DF_Ligne_Devis");
		ld.resetFilters();
		ld.setFieldFilter("DF_Ligne_Devis_DF_Devis_id",getRowId());
	
		// user (Suiveur)
		ObjectDB u = getGrant().getTmpObject("User");
		u.resetFilters();
		//ObjectDB ui = getGrant().getTmpObject("DF_utilisateur_interne");
		u.setFieldFilter("row_id",getFieldValue("DF_Devis_DF_utilisateur_interne_id"));
		
		// user (Redacteur)
		ObjectDB redacteur = getGrant().getTmpObject("User");
		redacteur.resetFilters();
		redacteur.setFieldFilter("row_id",getFieldValue("DF_Devis_DF_utilisateur_interne_id"));		
		
		AppLog.info(getClass(), "test user --------- pdf",u.toJSON(u.search(), null, false, false).toString() , getGrant());
		//u.setFieldFilter("DF_Ligne_Devis_DF_Devis_id",getRowId());
		// affaire 
		// client 
		// contact 
		// produits finis 
		
		//List<String> tva = new ArrayList<String>();
		double prix_total = getField("defiDevisPrixTotalHT").getDouble();
		double prix_tva = prix_total*0.2;
		//tva.add(Double.toString(prix_tva));
		//List<String[]> rows_l = ;
		//if (rows_l.size() > 0){
			wp.append(MustacheTool.apply(
			this,
			"DF_Devis_HTML", 
			"{'rows':"+d.toJSON(d.search(), null, false, false)+
			",'rows_l':"+ld.toJSON(ld.search(), null, false, false)+
			",'rows_u':"+u.toJSON(u.search(), null, false, false)+
			",'tva':"+ "[{'prix_tva':"+Double.toString(prix_tva)+"}]"+
			"}"
			));
			AppLog.info(getClass(), "ldqjklf",Double.toString(prix_tva) , getGrant());
			
		//}
		return wp.getHTML();
	
	}
	
	public byte[] pubPdf(){
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

// https://www.simplicite.io/resources/4.0/javadoc/com/simplicite/util/tools/MailTool.html
		new Mail(getGrant()).send(
					"alfredtw19@gmail.com",
					"hsenoussi@velvetconsulting.com",
					"test-hs",
					"<html><body>" +
					"<h3>Hello,</h3>" +
					"</body></html>");
		
	
		try{
			pdf = Tool.readUrlAsByteArray(url, user, password, postData.toString(), headers, encoding);
			
		
		}catch(Exception e){
			AppLog.error(getClass(), "pubPdf", "------------", e, getGrant());
		}
		return pdf;
	}

	public String generateFile() {
		ObjectDB hst = getGrant().getTmpObject("DF_Hist_Docs");
		

		ByteArrayOutputStream baos = null;
		try {
			synchronized(hst){
				hst.resetFilters();
				hst.setFieldFilter("DF_Hist_Docs_DF_Devis_id",getRowId());
				
				for(String[] hstc : hst.search()){
					hst.setValues(hstc);
					hst.create();
					hst.getField("defiHstDocsDevis").setDocument(hst, "Devis", pubPdf());
					hst.setFieldValue("DF_Hist_Docs_DF_Devis_id",getRowId());
					hst.save();
		    		
				}
			}
			return Message.formatSimpleInfo("Finished OK");
		}	
		catch(Exception e) {
		    AppLog.error(getClass(), "generateFile", "error...", e, getGrant());
		    return Message.formatSimpleError("Error...");
		}
	}
	
	/**
	 * Order receipt publication as PDF
	 * @param ord Order object
	 
	public static byte[] pubDevis(DF_Devis ord) {
		try (ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream()) {
			Document pdf = PDFTool.open(bos);

			// Logo
			//pdf.add(PDFTool.getImage("https://www.eurovia.fr/zbwi.axd/cropsz/320x158/~/media/4543035/de_filippis_entreprise.jpg"));

			pdf.add(new Paragraph(ord.getGrant().T("DEMO_RECEIPT"), PDFTool.TITLE1));

			ObjectField f = ord.getField("defiDevisTitre");
			pdf.add(new Paragraph(f.getDisplay() + ": " + f.getValue(), PDFTool.TITLE2));
			f = ord.getField("defiDevisDateEmission");
			pdf.add(new Paragraph(f.getDisplay() + ": " + ord.getGrant().toFormattedDate(f.getValue())));
			

		
			PDFTool.close(pdf);
			return bos.toByteArray();
		} catch (Exception e) {
			AppLog.error(DF_Devis.class, "orderReceipt", "Unable to generate order receipt", e, ord.getGrant());
			return null;
		}
	}*/

	
	/** Publication: PDF receipt */
	//public Object printDevisReceipt(PrintTemplate pt) {
	//	return pubDevis(this); // Implemented in common class
	//}

	
}