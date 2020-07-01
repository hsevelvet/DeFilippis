var DF_Devis = typeof DF_Devis !== "undefined" ? DF_Devis : (function($) {
	// Responsive UI hook
	Simplicite.UI.hooks.DF_Devis = function(o, cbk) {
		try {
			// Helper to dynamically change unit price when selecting product (also done on server side)
			o.locals.ui.form.onload = function(ctn, obj) {
			var email = $ui.getUIField(ctn, obj, "defiDevisContactMail");
			var v_affaire = $ui.getUIField(ctn, obj, "DF_Devis_DF_Chantier_id__defiAfrLieuAffaire");
			
			v_affaire.on("change", function(){
				$ui.getUIField(ctn, obj, "defiDevisLieuProjet").ui.val(v_affaire.ui.val());
			});
			
			email.ui.on("change", function() {
					$ui.getUIField(ctn, obj, "defiDevisCommentaire").ui.val(email.ui.val());
				});
			};
		} catch(e) { console.error(e.message); } finally { cbk && cbk(); }
	};
})(jQuery);