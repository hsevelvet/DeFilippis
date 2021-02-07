package com.simplicite.objects.DeFilippis;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object DF_ligne_commande
 */
public class DF_ligne_commande extends ObjectDB {
	private static final long serialVersionUID = 1L;


	// Mise à jour du prix total de ligne commande en fonction de changement de quantité 
	@Override
	public void initUpdate() {
		double prix_unitaire = getField("defiLigneCommandePrixEXWUnitaire").getDouble(0);
		double qte =  getField("defiLigneCommandeQuantite").getDouble(0);
		double poids_u = getField("defiLigneCommandePoidsUnitaire").getDouble(0);
		double qt_livr = 0;
		
		ObjectDB qt = getGrant().getTmpObject("DF_Quantite");
		synchronized(qt){
			qt.resetFilters();
			qt.setFieldFilter("DF_Quantite_DF_ligne_commande_id",this.getRowId());
			
			
			for(String[] qtl : qt.search()){
				qt.setValues(qtl);
				qt_livr += qt.getField("defiQuantiteQte").getDouble(0);
			}
		}
		
		setFieldValue("defiLigneCommandeQteLivr", qte-qt_livr);
		setFieldValue("defiLigneCommandelivre", qt_livr);
		setFieldValue("defiLigneCommandePrixTotalEXW", prix_unitaire*qte);
		setFieldValue("defiLigneCommandePoidsTotal", (qte*poids_u)/1000);
		//String Nom_Fournisseur =  getFieldValue("DF_ligne_commande_DF_Fournisseurs_id.defiFournNom");
		//setFieldValue("defiLigneCommandeNmFourn", Nom_Fournisseur);
	
		save();
		//return null;
	}
	
	@Override
	public void initCopy() {
		setFieldValue("defiLigneCommandeTrelloId", "");
	}
	
	

	
}