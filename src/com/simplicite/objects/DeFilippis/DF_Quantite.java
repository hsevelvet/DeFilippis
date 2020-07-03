package com.simplicite.objects.DeFilippis;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object DF_Plan_Livraison
 */
public class DF_Quantite extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void initCreate() {
		Double qte = getField("defiQuantiteQte").getDouble(0);
		Double poids_u = getField("defiQuantitePoidsUnitaire").getDouble(0);
		//Double prix_u = getField("DF_Quantite_DF_ligne_commande_id.defiLigneCommandePrixEXWUnitaire").getDouble(0);
		
		//setFieldValue("defiQuantiteMontant", qte*prix_u);
		setFieldValue("defiQuantiteTonnage", qte*poids_u/1000);
		validate();
		save();
	}
	
	@Override
	public void initUpdate() {
		// Mise à jour du tonnage lors de changement de quantité produit
		Double qte = getField("defiQuantiteQte").getDouble(0);
		Double poids_u = getField("defiQuantitePoidsUnitaire").getDouble(0);
		Double prix_u = getField("DF_Quantite_DF_ligne_commande_id.defiLigneCommandePrixEXWUnitaire").getDouble(0);
		
		setFieldValue("defiQuantiteTonnage", qte*poids_u/1000);
		setFieldValue("defiQuantiteMontant", qte*prix_u);
		validate();
		save();
		
	}
	
	public String createAllTrelloCards(){
		try {
		ObjectDB o = getGrant().getTmpObject("DF_Livraison");
		o.resetFilters();
		o.getField("DF_Livraison_DF_Plan_Livraison_id").setFilter(getRowId());
		
		List<String[]> rows = o.search(false);
		for (int i = 0; i < rows.size(); i++) {
			//AppLog.info(getClass(), "DangLog---------------------------", obj.getFieldValue("df_livraison_id"), getGrant());
			//o.setFieldValue("df_livraison_adresse", "Test");
			//objt.validateAndSave();
		}
		
		} catch (Exception e) {
			AppLog.error(getClass(), "updateCard", null, e, getGrant());
		}
	
		return "Test";
	}
	
}