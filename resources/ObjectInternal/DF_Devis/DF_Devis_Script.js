var DF_Devis = typeof DF_Devis !== "undefined" ? DF_Devis : (function($) {
	// Responsive UI hook
	Simplicite.UI.hooks.DF_Devis = function(o, cbk) {
		try {
			// Helper to dynamically change unit price when selecting product (also done on server side)
			o.locals.ui.form.onload = function(ctn, obj) {
			var nm_client = $ui.getUIField(ctn, obj, "DF_Devis_DF_Client_id.defiClientNom");
			
			nm_client.ui.on("change", function() {
					$ui.getUIField(ctn, obj, "defiDevisCadenceLivraison").ui.val(nm_client.ui.val());
				});
			};
		} catch(e) { console.error(e.message); } finally { cbk && cbk(); }
	};
})(jQuery);