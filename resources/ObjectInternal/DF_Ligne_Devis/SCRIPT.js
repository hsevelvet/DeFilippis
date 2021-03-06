

var DF_Ligne_Devis = typeof DF_Ligne_Devis !== "undefined" ? DF_Ligne_Devis : (function($) {
	// Responsive UI hook
	Simplicite.UI.hooks.DF_Ligne_Devis = function(o, cbk) {
		try {
			// Helper to dynamically change unit price when selecting product (also done on server side)
			o.locals.ui.form.onload = function(ctn, obj) {
				var ap_com = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdAppellationCommerciale");
				var type_prd = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdTypeProduit");
				var prix = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdPrixUnitaireHT");
				var unit = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdUnite");
				var lrg = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdLargeur");
				var long = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdLongueur");
				var eps = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdEpaisseur");
				var mvp = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdMasseVolumique");
				var des = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdTypeProduit");
				var nom = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdTypeGeologique");
				var fournisseur = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdFournisseur");
				var couleur = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdCouleur");
				
				var type_geo = $ui.getUIField(ctn, obj, "defiLigneDevisTypeGeo");
				
				var finition = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdFinitionFacesVues");
				var aut_fin = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdAutresFinitions");
				
				var ptrsp = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Prix_Transport_id.defiPrTrspPrix");
				var categ_prix = $ui.getUIField(ctn, obj, "DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdCategoriePrix");
				
				fournisseur.ui.on("change", function(){
					$ui.getUIField(ctn, obj, "defiLigneDevisFournisseur").ui.val(fournisseur.ui.val());
				});
				
				ap_com.ui.on("change", function(){
					$ui.getUIField(ctn, obj, "defiLigneDevisAppellationCommerciale").ui.val(ap_com.ui.val());
				});
				
				couleur.ui.on("change", function(){
					$ui.getUIField(ctn, obj, "defiLigneDevisCouleur").ui.val(couleur.ui.val());
				});
				
				type_prd.ui.on("change", function(){
					$ui.getUIField(ctn, obj, "defiLigneDevisTypePrd").ui.val(type_prd.ui.val());
				});
				
				
				prix.ui.on("change", function() {
					$ui.getUIField(ctn, obj, "defiLigneDevisPrixUnitaireHT").ui.val(prix.ui.val());
				});
				
				unit.ui.on("change", function() {
					$ui.getUIField(ctn, obj, "defiLigneDevisUnite").ui.val(unit.ui.val());
				});
				
				lrg.ui.on("change", function() {
					$ui.getUIField(ctn, obj, "defiLigneDevisLargeur").ui.val(lrg.ui.val());
				});
				long.ui.on("change", function() {
					$ui.getUIField(ctn, obj, "defiLigneDevisLongueur").ui.val(long.ui.val());
				});
				eps.ui.on("change", function() {
					$ui.getUIField(ctn, obj, "defiLigneDevisEpaisseur").ui.val(eps.ui.val());
				});
				mvp.ui.on("change", function() {
					$ui.getUIField(ctn, obj, "defiLigneDevisMasseVolumique").ui.val(mvp.ui.val());
				});
				
				finition.ui.on("change", function() {
					$ui.getUIField(ctn, obj, "defiLigneDevisFinitionFV").ui.val(finition.ui.val());
				});
				aut_fin.ui.on("change", function() {
					$ui.getUIField(ctn, obj, "defiLigneDevisAutresFinitions").ui.val(aut_fin.ui.val());
				});
				des.ui.on("change", function() {
					$ui.getUIField(ctn, obj, "defiLigneDevisDesignationProduit").ui.val(des.ui.val());
				});
				nom.ui.on("change", function() {
					$ui.getUIField(ctn, obj, "defiLigneDevisTypeGeo").ui.val(nom.ui.val());
				});
				
				type_geo.ui.on("change", function() {
					$ui.getUIField(ctn, obj, "defiLigneDevisTypePierre").ui.val(type_geo.ui.val());
				});
				
				ptrsp.ui.on("change", function() {
					$ui.getUIField(ctn, obj, "defiLigneDevisPrixTrsp").ui.val(ptrsp.ui.val());
				});
				
				categ_prix.ui.on("change", function() {
					$ui.getUIField(ctn, obj, "defiLigneDevisCatPrix").ui.val(categ_prix.ui.val());
				});
				
			};
		} catch(e) { console.error(e.message); } finally { cbk && cbk(); }
	};
})(jQuery);