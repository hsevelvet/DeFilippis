package com.simplicite.workflows.DeFilippis;

import java.util.*;
import com.simplicite.bpm.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Process ODFModification
 */
public class ODFModification extends Processus {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void postValidate(ActivityFile context)
	{
		String step = context.getActivity().getStep();
		if ("SelectionMultipleLivraisons".equals(step))
		{
			// All selected row IDs
			DataFile df = context.getDataFile("Field","row_id",true);
			String[] ids = df!=null ? df.getValues() : null;
			//for (int i=0; ids!=null && i<ids.length; i++) ...
		}
	}
	
	
}
