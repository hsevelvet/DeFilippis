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
	public List<String> postValidate() {
		List<String> msgs = new ArrayList<String>();
		
		//msgs.add(Message.formatInfo("INFO_CODE", "Message", "fieldName"));
		//msgs.add(Message.formatWarning("WARNING_CODE", "Message", "fieldName"));
		//msgs.add(Message.formatError("ERROR_CODE", "Message", "fieldName"));
		
		if (this.isNew())
			getField("defiLigneDevisPrixUnitaireHT").setValue(getField("defiPrdPrixUnitaireHT").getValue());
			getField("defiLigneDevisUnite").setValue(getField("defiPrdUnite").getValue());
			getField("defiLigneDevisLargeur").setValue(getField("defiPrdLargeur").getValue());
			
			// conversion tonne
			if (getFieldValue("DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdUnite").equals("T") ){
				String c_unite = getFieldValue("defiLigneDevisConversionUnite");
					
				if (c_unite.equals("61")) setFieldValue("defiLigneDevisUnite","M2");
				else if (c_unite.equals("62")) setFieldValue("defiLigneDevisUnite","ML");
			}
			
			// conversion m²
			if (getFieldValue("DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdUnite").equals("M2") ){
				String c_unite = getFieldValue("defiLigneDevisConversionUnite");
					
				if (c_unite.equals("61")) setFieldValue("defiLigneDevisUnite","M2");
				else if (c_unite.equals("62")) setFieldValue("defiLigneDevisUnite","ML");
			}
			
		
		
		return msgs;
		
	}

	@Override
	public void initUpdate(){
		
		// set id ligne devis
		/*
		Integer id = Integer.valueOf(this.getRowId());
		String id_ld = String.format("%04d",id);
		
		setFieldValue("defiLigneDevisId",id_ld);
		*/
        // accès aux valeurs 
        String unite = getField("defiLigneDevisUnite").getValue(); 
        String des_produit = getField("defiPrdTypeProduit").getValue(); 
        String fin_produit = getField("defiPrdFinitionFacesVues").getValue();

		double mvp = getField("defiPrdMasseVolumique").getDouble(0);
		double lng = getField("defiPrdLongueur").getDouble(0);
		double lrg = getField("defiPrdLargeur").getDouble(0);
		double ep = getField("defiPrdEpaisseur").getDouble(0);
		int qte = getField("defiLigneDevisQuantite").getInt(0);
		double dim_joint = getField("defiLigneDevisDimensionJoints").getDouble(0);
		double ptr = getField("defiLigneDevisPrixTransportReference").getDouble(0);
		double prc = getField("defiLigneDevisPrixUnitaireHT").getDouble(0);
		
		// designation ligne devis
		String designation = "Désignation Produit: "+ des_produit +"\t"+"Finition: "+ fin_produit+
		"\n"+" Unité: " + unite +"\t" + " Longueur: "+lng +"\t" + " Largeur: "+lrg+"\t" +" Epaisseur: "+ep+"\t"  + "Dimension Joints: " +dim_joint;
		
		setFieldValue("defiLigneDevisDesignation",designation);
		
		
		// conversion tonne
		if (getFieldValue("DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdUnite").equals("T")) setFieldValue("defiLigneDevisPrixExwTonne",prc);
		else {setFieldValue("defiLigneDevisPrixExwUnitaire",prc);}

		
		Double pexwu = getField("defiLigneDevisPrixExwUnitaire").getDouble(0); 
		Double pexwt = getField("defiLigneDevisPrixExwTonne").getDouble(0);
		
		// Cas linéage
		int n_rangs = getField("defiLigneDevisRangs").getInt(1);
		switch (unite){
			case "ML":
				if (n_rangs > 1) setFieldValue("defiLigneDevisLargeur", Math.round(lrg*n_rangs+dim_joint));
				break;
			
			default :
				setFieldValue("defiLigneDevisLargeur", lrg);
				
		}
		// calcul nombre d'éléments par unité sans joint
        if (lng == 0 || lrg == 0){
        	setFieldValue("defiLigneDevisNombreElementsSsJoints", 0);
        }
        else {
        	switch(unite){
				case "M2":
					setFieldValue("defiLigneDevisNombreElementsSsJoints", Math.round(1/((lng / 100)*(lrg /100))));
					break;
				case "ML":
					setFieldValue("defiLigneDevisNombreElementsSsJoints", Math.round(1/(lng / 100)));
					break;
				case "U":
					setFieldValue("defiLigneDevisNombreElementsSsJoints", 1);
					break;
			}
        }
		
        // calcul masse unitaire sans joint
        switch(unite){
			case "U":
				setFieldValue("defiLigneDevisMasseUnitaireSsJoints", Math.round(mvp*(lng*lrg*ep / 1000000)));
				break;
			case "ML":
				setFieldValue("defiLigneDevisMasseUnitaireSsJoints", Math.round(mvp*(lrg*ep / 10000)));
				break;
			case "M2":
				setFieldValue("defiLigneDevisMasseUnitaireSsJoints", Math.round(mvp*(ep / 100)));
				break;
		}
        
        // calcul nombre d'éléments par unité avec joint
        if ((lng + dim_joint == 0) || (lrg + dim_joint == 0)){
        	setFieldValue("defiLigneDevisNombreElementsAcJoints", 0);
        }
        else {
        	switch(unite){
				case "M2":
					setFieldValue("defiLigneDevisNombreElementsAcJoints", Math.round(1/((lng + dim_joint)*(lrg + dim_joint)/10000)));
					break;
				case "ML":
					setFieldValue("defiLigneDevisNombreElementsAcJoints", Math.round(1/((lng+dim_joint)/100)));
					break;
				case "U":
					setFieldValue("defiLigneDevisNombreElementsAcJoints", 1);
					break;
			}
        }
        
        // calcul masse unitaire avec joint
        if (dim_joint == 0){
        	setFieldValue("defiLigneDevisMasseUnitaireAcJoints", Math.round((ep * mvp * lrg) / 10000));
        }
        else{
        	double n = getField("defiLigneDevisNombreElementsAcJoints").getDouble(0);
        	setFieldValue("defiLigneDevisMasseUnitaireAcJoints", Math.round(n *(ep * lng * lrg * mvp / 1000000)));
        }
        
        // calcul poids total
        double muaj = getField("defiLigneDevisMasseUnitaireAcJoints").getDouble(0);
        double musj = getField("defiLigneDevisMasseUnitaireSsJoints").getDouble(0);
		if (muaj == 0){
        	setFieldValue("defiLigneDevisPoidsTotal", ((musj * qte) / 1000));
        }
        else{
        	setFieldValue("defiLigneDevisPoidsTotal", ((muaj * qte) / 1000));
        }
        
        // calcul prix transport / unité
        setFieldValue("defiLigneDevisPrixTransportUnitaire", muaj / 24000 * ptr);
        
        // calcul nombre camions 
        double pt = getField("defiLigneDevisPoidsTotal").getDouble(0);
        setFieldValue("defiLigneDevisNombreCamions", pt / 24 );
        
        // calcul prix EXW / unité
        if (pexwu == 0){
        	setFieldValue("defiLigneDevisPrixExwUnite", pexwt * muaj / 1000);
        }
        else {
        	setFieldValue("defiLigneDevisPrixExwUnite", pexwu);
        }
        
        // calcul prix déboursé sec
        double pexwut = getField("defiLigneDevisPrixExwUnite").getDouble(0);
        double ptu = getField("defiLigneDevisPrixTransportUnitaire").getDouble(0);
        setFieldValue("defiLigneDevisPrixUnitaireSec",pexwut + ptu);
        
        // calcul total achat reference ht
        setFieldValue("defiLigneDevisTotalEXWHT", pexwut * qte);
        
        // calcul total achat ht
        double pds = getField("defiLigneDevisPrixUnitaireSec").getDouble(0);
        setFieldValue("defiLigneDevisTotalAchatHT", pds * qte);
        
        // calcul prix x coef
        double coef = getField("defiLigneDevisCoef").getDouble(0);
        double total_achat = getField("defiLigneDevisTotalAchatHT").getDouble(0);
        setFieldValue("defiLigneDevisPrixVenteCalcule", pds * coef);
        
        // calcul prix vente imposé
       Double pvi = getField("defiLigneDevisPrixUnitaireImpose").getDouble(0);
       double pvc = getField("defiLigneDevisPrixVenteCalcule").getDouble(0);
       if (pvi == 0){
       	setFieldValue("defiLigneDevisPrixUnitaireImpose", pvc);
       }
       else{
       	setFieldValue("defiLigneDevisPrixUnitaireImpose", pvi);
       }
       
       // calcul total vente imposé
       
       setFieldValue("defiLigneDevisPrixVenteImpose",pvi*qte);

	}
}