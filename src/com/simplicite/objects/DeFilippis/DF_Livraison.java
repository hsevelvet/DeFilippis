package com.simplicite.objects.DeFilippis;
import org.json.JSONObject;

import com.simplicite.util.AppLog;
import com.simplicite.util.Globals;
import com.simplicite.util.Message;
import com.simplicite.util.Tool;
import com.simplicite.util.exceptions.APIException;
import com.simplicite.util.tools.HTMLTool;
import com.simplicite.util.tools.TrelloTool;

/**
 * Trello card business object example
 */
public class DF_Livraison extends com.simplicite.util.ObjectDB {
	private static final long serialVersionUID = 1L;

	private TrelloTool tt = null;
	private JSONObject settings = null;

	@Override
	public void postLoad() {
		AppLog.info(getClass(), "postLoad", "Instance: " + getInstanceName(), getGrant());
		if (!getInstanceName().startsWith("webhook_")) {
			tt = new TrelloTool(getGrant());
			//tt.setDebug(true);
			AppLog.info(getClass(), "postLoad", "Trello tool API key: " + tt.getKey(), getGrant());
			settings = getGrant().getJSONObjectParameter("TRELLO_CARDEX_SETTINGS");
			AppLog.info(getClass(), "postLoad", "Settings: " + settings.toString(2), getGrant());

			String contextURL = getGrant().getContextURL();
			if (!Tool.isEmpty(contextURL)) {
				String webhookURL = contextURL + HTMLTool.getPublicExternalObjectURL("WebhookLivraisonTrello");
				try {
					String webhookId = tt.registerWebhook(settings.getString("boardId"), webhookURL, "Webhook for " + Globals.getPlatformName());
					AppLog.info(getClass(), "postLoad", "Registered webhook: " + webhookId, getGrant());
				} catch (APIException e) {
					AppLog.error(getClass(), "postLoad", "Unable to register the webhook: " + webhookURL, e, getGrant());
				}
			}
		}
	}

	@Override
	public String preCreate() {
		if (tt == null) return null;
		try {
			JSONObject card = new JSONObject();
			
			card.put("name", getFieldValue("df_livraison_id"));
			card.put("desc", createDesc());
			card.put("due", getFieldValue("df_livraison_date_livraison_estimee"));
			
			card = tt.addCard(settings.getString("defaultListId"), card);
			AppLog.info(getClass(), "preCreate", card.toString(2), getGrant());
			setFieldValue("df_livraison_trellocardid", card.getString("id"));
			return Message.formatSimpleInfo("Trello card created");
		} catch (APIException e) { // Prevents creation if card creation fails
			AppLog.error(getClass(), "preCreate", null, e, getGrant());
			return Message.formatSimpleError("Card creation error: " + e.getMessage());
		}
	}

	@Override
	public String preUpdate() {
		if (tt == null) return null;
		try {
			String id = getFieldValue("df_livraison_trellocardid");
			JSONObject card = tt.getCard(id);

			card.put("name", getFieldValue("df_livraison_id"));
			card.put("desc", createDesc());
			card.put("due", getFieldValue("df_livraison_date_livraison_estimee"));
			
			tt.updateCard(id, card);
			AppLog.info(getClass(), "preUpdate", card.toString(2), getGrant());

			return Message.formatSimpleInfo("Trello card updated");
		} catch (APIException e) { // Prevents deletion if card creation fails
			AppLog.error(getClass(), "postUpdate", null, e, getGrant());
			return Message.formatSimpleError("Card update error: " + e.getMessage());
		}
	}

	@Override
	public String preDelete() {
		if (tt == null) return null;
		try {
			tt.deleteCard(getFieldValue("df_livraison_trellocardid"));
			return null;
		} catch (APIException e) { // Prevents deletion if card creation fails
			AppLog.error(getClass(), "postLoad", null, e, getGrant());
			return null;//Message.formatSimpleError("Card deletion error: " + e.getMessage());
		}
	}
	
	
	public String createDesc(){
		String desc = "";
		desc += "\n**Ligne de commande ID**: "+getFieldValue("df_livraison_id_ligne_commande");
		desc += "\n**Quantité**: "+getFieldValue("df_livraison_quantite_chargee");
		//desc += "\n**Statut Livraison**: "+getFieldValue("df_livraison_statut");
		desc += "\n**Date Livraison Estimée**: "+getFieldValue("df_livraison_date_livraison_estimee");
		desc += "\n";
		desc += "\n**Num BL Client**: "+getFieldValue("df_livraison_num_bl_client");
		desc += "\n**Num BL Fournisseur**: "+getFieldValue("df_livraison_num_bl_fournisseur");
		desc += "\n";
		desc += "\n**Adresse Enlevement**: "+getFieldValue("df_livraison_adresse_enlevement");
		desc += "\n**Adresse Livraison**: "+getFieldValue("df_livraison_adresse");
		desc += "\n**Adresse De Livraison Confirmée**: "+getFieldValue("df_livraison_adresse_de_livraison_confirmee");
		desc += "\n";
		desc += "\n**Nom Transporteur**: "+getFieldValue("df_livraison_nom_transporteur");
		desc += "\n**Contact Transporteur**: "+getFieldValue("df_livraison_contact_transporteur");
		desc += "\n**Num ZEEPO Transporteur**: "+getFieldValue("df_livraison_num_zeepo_transporteur");
		desc += "\n";
		desc += "\n**Contact Déchargement Privilégié**: "+getFieldValue("df_livraison_contact_dechargement_privilegie");
		desc += "\n**Contact En Cas De Problème**: "+getFieldValue("df_livraison_contact_en_cas_de_probleme");
		return desc;
	}
	
	public String synchroTrello(){
		if (tt == null) return null;
		String id = getFieldValue("df_livraison_trellocardid");
		if (id.length()>0)
			return preUpdate();
		else 
			return preCreate();
	}
	
}