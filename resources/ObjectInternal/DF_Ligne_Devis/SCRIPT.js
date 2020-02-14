/** DF_Ligne_Devis front side hook
(function(ui) {
	if (!ui) return;
	var app = ui.getAjax();
	// Hook called by each object instance
	Simplicite.UI.hooks.DF_Ligne_Devis = function(o, cbk) {
		try {
			console.log("DF_Ligne_Devis hooks loading...");
			var p = o.locals.ui;
			if (p && o.isMainInstance()) {
				p.form.onload = function(ctn, obj) {
					//...
				};
			}
			//...
		}
		catch(e) {
			app.error("Error in Simplicite.UI.hooks.DF_Ligne_Devis: " + e.message);
		}
		finally {
			console.log("DF_Ligne_Devis hooks loaded.");
			cbk && cbk(); // final callback
		}
	};
})(window.$ui);


var DF_Ligne_Devis = typeof DF_Ligne_Devis !== "undefined" ? DF_Ligne_Devis : (function($) {
	// Responsive UI hook
	Simplicite.UI.hooks.DF_Ligne_Devis = function(o, cbk) {
		try {
			// Helper to dynamically change unit price when selecting product (also done on server side)
			o.locals.ui.form.onload = function(ctn, obj) {
				var prix = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdPrixUnitaireHT");
				var unit = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdUnite");
				f.ui.on("change", function() {
					$ui.getUIField(ctn, obj, "defiLigneDevisUnite").ui.val(unit.ui.val());
					$ui.getUIField(ctn, obj, "defiLigneDevisPrixUnitaireHT").ui.val(prix.ui.val());
				});
			};
		} catch(e) { console.error(e.message); } finally { cbk && cbk(); }
	};
})(jQuery);*/