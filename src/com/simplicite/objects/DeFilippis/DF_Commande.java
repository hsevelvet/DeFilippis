package com.simplicite.objects.DeFilippis;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object DF_Commande
 */
public class DF_Commande extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void initUpdate() {

		
		// set values ligne devis
		ObjectDB o = getGrant().getTmpObject("DF_ligne_commande");
		o.resetFilters();
		o.getField("DF_ligne_commande_DF_Commande_id").setFilter(getRowId());
		
		List<String[]> rows = o.search(false);
		if (rows.size() > 0){
			double c = o.getCount();
				
			double t = Double.parseDouble(o.getField("df_ligne_commande_prix_total_ht").getListOperatorValue());

			
			setFieldValue("df_commande_montant_ht", t);


	}
	}
	
}