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

		// Méthode pour convertir les unités après validatiion du formulaire /
		
		//if (this.isNew())
			
			// conversion tonne
			if (getFieldValue("defiLigneDevisUnite").equals("T") ){
				String c_unite = getFieldValue("defiLigneDevisConversionUnite");
					
				if (c_unite.equals("61")) setFieldValue("defiLigneDevisU","M2");
				else if (c_unite.equals("62")) setFieldValue("defiLigneDevisU","ML");
			}
			
			// conversion m²
			if (getFieldValue("defiLigneDevisUnite").equals("M2") ){
				String c_unite = getFieldValue("defiLigneDevisConversionUnite");
					
				if (c_unite.equals("61")) setFieldValue("defiLigneDevisU","M2");
				else if (c_unite.equals("62")) setFieldValue("defiLigneDevisU","ML");
			}
			
		
		// accès aux valeurs 
        String unite = getField("defiLigneDevisU").getValue(); 
        String des_produit = getField("defiPrdTypeProduit").getValue(); 
        String fin_produit = getField("defiPrdFinitionFacesVues").getValue();
        String ap_commerciale = getField("defiPrdAppellationCommerciale").getValue();
		double mvp = getField("defiLigneDevisMasseVolumique").getDouble(0);

		//calcul longueur bornée
		String longueur = getFieldValue("defiLigneDevisLongueur");
		double lng = 0;
		if(longueur != null && !longueur.isEmpty()){
			if (longueur.contains("/")){
				String [] l_parts = longueur.split("/");
				lng = (Double.parseDouble(l_parts[0])+Double.parseDouble(l_parts[1]))/2;
			}
			else{
				lng = Double.parseDouble(longueur);
			}
		} 
		
		//setFieldValue("defiLigneDevisLongueur",lng);
		
		//calcul largeur bornée
		String largeur = getFieldValue("defiLigneDevisLargeur");
		double lrg = 0;
		if(largeur != null && !largeur.isEmpty()){
			if (largeur.contains("/")){
				String [] la_parts = largeur.split("/");
				lrg = (Double.parseDouble(la_parts[0])+Double.parseDouble(la_parts[1]))/2;
			}
			else{
				lrg = Double.parseDouble(largeur);
			}
		} 
		
		//setFieldValue("defiLigneDevisLargeur",lrg);
		
		// Calcul epaisseur bornée
		String epaisseur = getFieldValue("defiLigneDevisEpaisseur");
		double ep = 0;
		if(epaisseur != null && !epaisseur.isEmpty()){
			if (epaisseur.contains("/")){
				String [] ep_parts = epaisseur.split("/");
				ep = (Double.parseDouble(ep_parts[0])+Double.parseDouble(ep_parts[1]))/2;
			}
			else{
				ep = Double.parseDouble(epaisseur);
			}
		}
		
		
		// Get valeurs référence produit
		//double ep = getField("defiLigneDevisEpaisseur").getDouble(0);
		int qte = getField("defiLigneDevisQuantite").getInt(0);
		double dim_joint = getField("defiLigneDevisDimensionJoints").getDouble(0);
		double ptr = getField("defiLigneDevisPrixTrsp").getDouble(0);
		double prc = getField("defiLigneDevisPrixUnitaireHT").getDouble(0);
		String aut_fin = getFieldValue("DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdAutresFinitions");
		String appel_com = getFieldValue("defiLigneDevisAppellationCommerciale");
		String type_prd = getFieldValue("defiLigneDevisCatPrix");
		
		// valorisation : designation ligne devis		
		String designation =  ap_commerciale+" "+ des_produit +" "+ fin_produit+" "+aut_fin+"\n"+lng +" " + " x "+lrg+"\t" +" x ep. "+ep+" " + "Joint inclus de " +dim_joint +" cm";
		if (type_prd.equals("1")){
			setFieldValue("defiLigneDevisDesignation",appel_com);	
		}
		if (type_prd.equals("2")){
			setFieldValue("defiLigneDevisDesignation",designation);	
		}
		
		
		
		// conversion tonne
		if (getFieldValue("defiLigneDevisUnite").equals("T")) setFieldValue("defiLigneDevisPrixExwTonne",prc);
		else if (prc != 0){setFieldValue("defiLigneDevisPrixExwUnitaire",prc);}

		Double pexwu = getField("defiLigneDevisPrixExwUnitaire").getDouble(0); 
		Double pexwt = getField("defiLigneDevisPrixExwTonne").getDouble(0);
		
		// Cas linéage
		int n_rangs = getField("defiLigneDevisRangs").getInt(1);
		switch (unite){
			case "ML":
				//ObjectDB ld = getGrant().getTmpObject("DF_Ligne_Devis");
				if (getField("defiLigneDevisRangs").hasChanged() || getField("defiLigneDevisDimensionJoints").hasChanged()){
						AppLog.info(getClass(), "changed-----changed", "44444", getGrant());
					//
					if (n_rangs > 1){
						
					lrg = Math.round(lrg*n_rangs+dim_joint);
					//setFieldValue("defiLigneDevisLargeur", Math.round(lrg*n_rangs+dim_joint));
					
				} 
				}
				
				
				
				break;
			
			default :
				lrg = lrg;
				//setFieldValue("defiLigneDevisLargeur", lrg);
				
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
    	if (type_prd.equals("1")){
    		if (prc == 0){
    			setFieldValue("defiLigneDevisPrixUnitaireSec",getFieldValue("defiLigneDevisPrixExwUnitaire"));
    			
    		}
    		else{
    			setFieldValue("defiLigneDevisPrixUnitaireSec",prc);
    		}
    			
    	}
    	else{
    		setFieldValue("defiLigneDevisPrixUnitaireSec",pexwut + ptu);
    	}
        
        
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
       	//setFieldValue("defiLigneDevisPrixUnitaireImpose", pvc);
       	setFieldValue("defiLigneDevisPrixVenteImpose",pvc*qte);
       }
       else{
       	setFieldValue("defiLigneDevisPrixUnitaireImpose", pvi);
       	setFieldValue("defiLigneDevisPrixVenteImpose",pvi*qte);
       }
		
		
		return msgs;
		
	}
	

}