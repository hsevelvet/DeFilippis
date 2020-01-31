package com.simplicite.objects.DeFilippis;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object DF_DevisHistoric
 */
public class DF_DevisHistoric extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void initUpdate() {
		// set numero devis
		
		String date = getCreatedDate();
		String[] year = date.split("-");
		String y = year[0];
		
		//Integer result = Integer.valueOf(getRowId());
		int row_ref = getField("row_ref_id").getInt(0);
		String id = String.format("%04d",row_ref);
		
		String num_devis = y +"."+id+"."+"A";
		setFieldValue("df_devis_numero",num_devis);
	}
	
}
