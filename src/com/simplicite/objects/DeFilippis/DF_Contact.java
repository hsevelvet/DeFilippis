package com.simplicite.objects.DeFilippis;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object DF_Contact
 */
public class DF_Contact extends ObjectDB {
	private static final long serialVersionUID = 1L;

	@Override
	public void initRefSelect(ObjectDB parent) {
			
		// Sur devis : ne récupérer que les contacts correspondants au client séléctionné 
		/*if(parent!=null && parent.getName().equals("DF_Devis")){
			AppLog.info(getClass(), "initRefSelect", "------"+parent.getRowId(), getGrant());
			setFieldFilter("DF_Contact_DF_Client_id", parent.getFieldValue("DF_Devis_DF_Client_id"));
		}*/
		// Sur Commande : ne récupérer que les contact correspondants au client séléctionné 
		if(parent!=null && parent.getName().equals("DF_Commande")){
			AppLog.info(getClass(), "initRefSelect", "------"+parent.getRowId(), getGrant());
			setFieldFilter("DF_Contact_DF_Fournisseurs_id", parent.getFieldValue("DF_Commande_DF_Fournisseurs_id"));
		}
	}

	
}