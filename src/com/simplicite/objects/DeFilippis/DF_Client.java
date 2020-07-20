package com.simplicite.objects.DeFilippis;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object DF_Client
*/
public class DF_Client extends ObjectDB {
	private static final long serialVersionUID = 1L;

	// Utilisation du hook perValdiate : Valorisation des champs avant validation du formulaire
	
	@Override
	public List<String> preValidate() {
		List<String> msgs = new ArrayList<String>();
		
		
		// calcul du taux de transformation	(devis en chantier / total devis)
		ObjectDB devis = getGrant().getTmpObject("DF_Devis");
		synchronized(devis){
			devis.resetFilters();
			devis.setFieldFilter("DF_Devis_DF_Client_id",this.getRowId());
			
			double c = 0;
			double nb_devis = devis.getCount();
			for(String[] devi : devis.search()){
				devis.setValues(devi);
				AppLog.info(getClass(), "df_dev", devis.getFieldDisplayValue("defiDevisStatut"), getGrant());
				
				if(devis.getStatus().equals("CH")){
					c += 1;
				}
				
				setFieldValue("defiClientTauxTransformation", (1-(c/nb_devis)));
			}
		}

		// calcul de somme de commandes (somme des commandes réalisées,i.e: statut Terminé)
		ObjectDB commandes = getGrant().getTmpObject("DF_Commande");
		synchronized(commandes){
			commandes.resetFilters();
			commandes.setFieldFilter("DF_Commande_DF_Client_id",this.getRowId());
			
			double cm = 0;
			
			for(String[] cmd : commandes.search()){
				commandes.setValues(cmd);
				//if(commandes.getStatus().equals("TE")){
				cm += commandes.getField("defiCommandeMontantHT").getDouble(0);
				//}
				
				setFieldValue("defiClientSommeCommandes",cm);
			}
		}
		
		return msgs;
	}
}