

var DF_ligne_commande = typeof DF_ligne_commande !== "undefined" ? DF_ligne_commande : (function($) {
	// Responsive UI hook
	Simplicite.UI.hooks.DF_ligne_commande = function(o, cbk) {
		try {
			var p = o.locals.ui;
            // Instance panel only
            //if (p && o.isPanelInstance()) {
                // When the child list is loaded
                p.form.preload = function(ctn, obj) {
				var qt = $ui.getUIField(ctn, obj, "defiLigneCommandeQteLivr");
				
				//$ui.getUIField(ctn, obj, "defiLigneCommandeQteLivr").ui.val(qt.ui.val());

				console.log("test reussi!");
				
			//};
            }
		} catch(e) { console.error(e.message); } finally { cbk && cbk(); }
	};
})(jQuery);