package com.simplicite.objects.DeFilippis;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object DF_ligne_commande
 */
public class DF_ligne_commande extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void initUpdate() {
		double prix_unitaire = getField("defiLigneCommandePrixEXWUnitaire").getDouble(0);
		double qte =  getField("defiLigneCommandeQuantite").getDouble(0);
		
		setFieldValue("defiLigneCommandePrixTotalEXW", prix_unitaire*qte);
		save();
	}
	

/*	
	@Override
	public void () {
		double prix_unitaire = getField("defiLigneCommandePrixEXWUnitaire").getDouble(0);
		double qte =  getField("defiLigneCommandeQuantite").getDouble(0);
		
		setFieldValue("defiLigneCommandePrixTotalEXW", prix_unitaire*qte);
	}
*/	
}
