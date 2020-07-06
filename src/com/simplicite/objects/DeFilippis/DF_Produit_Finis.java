package com.simplicite.objects.DeFilippis;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object DF_Produit_Finis
*/
public class DF_Produit_Finis extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void initUpdate() {
		String fournisseur = getFieldValue("DF_Produit_Finis_DF_Fournisseurs_id.defiFournNom");
		setFieldValue("defiPrdFournisseur", fournisseur);
	}
	

}