package com.simplicite.workflows.DeFilippis;

import java.util.*;
import com.simplicite.bpm.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.objects.DeFilippis.DF_Livraison;

/**
 * Process DF_Process_01
 */
public class DF_Process_01 extends Processus {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void postValidate(ActivityFile context){
		String step = context.getActivity().getStep();
		if ("ODFM-END".equals(step))
		{
			DF_Livraison livraison = new DF_Livraison();
			livraison.pubPdfODF();
		}
}
	
}