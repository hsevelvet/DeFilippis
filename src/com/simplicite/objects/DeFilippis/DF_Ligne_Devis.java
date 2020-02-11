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
		return msgs;
		
	}
	
	@Override
	public void initUpdate(){
		
		// set id ligne devis
		
		Integer id = Integer.valueOf(getRowId());
		String id_ld = String.format("%04d",id);
		
		setFieldValue("defiLigneDevisId",id_ld);
		
        // accès aux valeurs 
        String unite = getField("defiPrdUnite").getValue(); 
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
		
		
		// affectation du prix 
		switch (unite){
			case "T":
				setFieldValue("defiLigneDevisUnite","m2");
				setFieldValue("defiLigneDevisPrixExwTonne",prc);
				break;
				
			default :
				setFieldValue("defiLigneDevisPrixExwUnitaire",prc);
				break;
		}
		
		Double pexwu = getField("defiLigneDevisPrixExwUnitaire").getDouble(0); 
		Double pexwt = getField("defiLigneDevisPrixExwTonne").getDouble(0);
		
		// calcul nombre d'éléments par unité sans joint
        if (lng == 0 || lrg == 0){
        	setFieldValue("defiLigneDevisNombreElementsSsJoints", 0);
        }
        else {
        	switch(unite){
				case "m2":
					setFieldValue("defiLigneDevisNombreElementsSsJoints", 1/((lng / 100)*(lrg /100)));
					break;
				case "ml":
					setFieldValue("defiLigneDevisNombreElementsSsJoints", 1/(lng / 100));
					break;
				case "u":
					setFieldValue("defiLigneDevisNombreElementsSsJoints", 1);
					break;
			}
        }
		
        // calcul masse unitaire sans joint
        switch(unite){
			case "u":
				setFieldValue("defiLigneDevisMasseUnitaireSsJoints", mvp*(lng*lrg*ep / 1000000));
				break;
			case "ml":
				setFieldValue("defiLigneDevisMasseUnitaireSsJoints", mvp*(lng*ep / 10000));
				break;
			case "m2":
				setFieldValue("defiLigneDevisMasseUnitaireSsJoints", mvp*(ep / 100));
				break;
		}
        
        // calcul nombre d'éléments par unité avec joint
        if ((lng + dim_joint == 0) || (lrg + dim_joint == 0)){
        	setFieldValue("defiLigneDevisNombreElementsAcJoints", 0);
        }
        else {
        	switch(unite){
				case "m2":
					setFieldValue("defiLigneDevisNombreElementsAcJoints", 1/((lng + dim_joint)*(lrg + dim_joint)/10000));
					break;
				case "ml":
					setFieldValue("defiLigneDevisNombreElementsAcJoints", 0);
					break;
				case "u":
					setFieldValue("defiLigneDevisNombreElementsAcJoints", 0);
					break;
			}
        }
        
        // calcul masse unitaire avec joint
        if (dim_joint == 0){
        	setFieldValue("defiLigneDevisMasseUnitaireAcJoints", ((ep * lng * lrg) / 10000));
        }
        else{
        	double n = getField("defiLigneDevisNombreElementsAcJoints").getDouble(0);
        	setFieldValue("defiLigneDevisMasseUnitaireAcJoints", (n *(ep * lng * lrg * mvp / 1000000)));
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
        setFieldValue("defiLigneDevisPrixVenteCalcule", pds * coef);
        
        // calcul prix vente imposé
       Double pvi = getField("defiLigneDevisPrixVenteImpose").getDouble(0);
       double pvc = getField("defiLigneDevisPrixVenteCalcule").getDouble(0);
       if (pvi == 0){
       	setFieldValue("defiLigneDevisPrixVenteImpose", pvc);
       }
       else{
       	setFieldValue("defiLigneDevisPrixVenteImpose", pvi);
       }
       
       
       // calcul total
		double total = pvi * qte;
    	setFieldValue("defiLigneDevisPrixTotalHT", total);
	}
	

	
}