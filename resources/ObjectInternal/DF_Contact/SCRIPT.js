var DF_Contact = typeof DF_Contact !== "undefined" ? DF_Contact : (function($) {
	// Responsive UI hook
	Simplicite.UI.hooks.DF_Contact = function(o, cbk) {
		try {
			// Helper to dynamically change unit price when selecting product (also done on server side)
			o.locals.ui.form.onload = function(ctn, obj) {
			var nm_client = $ui.getUIField(ctn, obj, "DF_Contact_DF_Client_id.defiClientNom");
			
			nm_client.ui.on("change", function() {
					$ui.getUIField(ctn, obj, "defiContactNomClient").ui.val(nm_client.ui.val());
				});
			};
		} catch(e) { console.error(e.message); } finally { cbk && cbk(); }
	};
})(jQuery);