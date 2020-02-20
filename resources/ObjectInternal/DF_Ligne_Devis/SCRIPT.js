
var DF_Ligne_Devis = typeof DF_Ligne_Devis !== "undefined" ? DF_Ligne_Devis : (function($) {
	// Responsive UI hook
	Simplicite.UI.hooks.DF_Ligne_Devis = function(o, cbk) {
		try {
			// Helper to dynamically change unit price when selecting product (also done on server side)
			o.locals.ui.form.onload = function(ctn, obj) {
				var prix = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdPrixUnitaireHT");
				var unit = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdUnite");
				var lrg = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdLargeur");
				prix.ui.on("change", function() {
					$ui.getUIField(ctn, obj, "defiLigneDevisPrixUnitaireHT").ui.val(prix.ui.val());
				});
				
				unit.ui.on("change", function() {
					$ui.getUIField(ctn, obj, "defiLigneDevisUnite").ui.val(unit.ui.val());
				});
				
				lrg.ui.on("change", function() {
					$ui.getUIField(ctn, obj, "defiLigneDevisLargeur").ui.val(lrg.ui.val());
				});
			};
		} catch(e) { console.error(e.message); } finally { cbk && cbk(); }
	};
})(jQuery);