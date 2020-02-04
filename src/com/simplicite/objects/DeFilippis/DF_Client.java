package com.simplicite.objects.DeFilippis;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object DF_Client
*/
public class DF_Client extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void initUpdate() {
		Integer id = Integer.valueOf(getRowId());
		String cli_id = String.format("%04d",id);
		setFieldValue("df_client_id",cli_id);
	}
	
	@Override
	public List<String> preValidate() {
		List<String> msgs = new ArrayList<String>();
		
		
		// calcul du taux de transformation	
		ObjectDB devis = getGrant().getTmpObject("DF_Devis");
		synchronized(devis){
			devis.resetFilters();
			devis.setFieldFilter("DF_Devis_DF_Client_id",this.getRowId());
			
			double c = 0;
			double nb_devis = devis.getCount();
			for(String[] devi : devis.search()){
				devis.setValues(devi);
				AppLog.info(getClass(), "df_dev", devis.getFieldDisplayValue("df_devis_statut"), getGrant());
				
				if(devis.getStatus().equals("CH")){
					c += 1;
				}
				
				setFieldValue("df_client_taux_transformation", (1-(c/nb_devis))*100);
			}
		}
		// calcul de somme de commandes 
		ObjectDB commandes = getGrant().getTmpObject("DF_Commande");
		synchronized(commandes){
			commandes.resetFilters();
			commandes.setFieldFilter("DF_Commande_DF_Client_id",this.getRowId());
			
			double cm = 0;
			
			for(String[] cmd : commandes.search()){
				commandes.setValues(cmd);
				if(commandes.getStatus().equals("TE")){
					cm += commandes.getField("df_commande_montant_ht").getDouble(0);
				}
				
				setFieldValue("df_client_sum_cmd",cm);
			}
		}
		
		return msgs;
	}
}

