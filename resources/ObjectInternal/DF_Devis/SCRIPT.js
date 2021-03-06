// DF_Devis front side hook
(function(ui) {
	if (!ui) return;
	var app = ui.getAjax();
	// Hook called by each object instance
	Simplicite.UI.hooks.DF_Devis = function(o, cbk) {
		try {
			console.log("DF_Devis hooks loading...");
			var p = o.locals.ui;
			if (p && o.isMainInstance()) {
				p.form.onload = function(ctn, obj) {
					var v_affaire = $ui.getUIField(ctn, obj, "DF_Devis_DF_Chantier_id.defiAfrLieuAffaire");
			
					console.log(v_affaire.v);
					v_affaire.ui.on("change", function(){
						console.log("bonjour");
						$ui.getUIField(ctn, obj, "defiDevisLieuProjet").ui.val(v_affaire.v);
					});
				};
			}
			//...
		}
		catch(e) {
			app.error("Error in Simplicite.UI.hooks.DF_Devis: " + e.message);
		}
		finally {
			console.log("DF_Devis hooks loaded.");
			cbk && cbk(); // final callback
		}
	};
})(window.$ui);