var DF_Commande = typeof DF_Commande !== "undefined" ? DF_Commande : (function($) {
	// Display Google Map
	var _odfm = function() {
		$ui.displayWorkflow(null, 'ODFModification');
	};
	
	
	return {
		// Action function
		odfm: function() {
			_odfm();
		}
		
		
		
	}
})(jQuery);