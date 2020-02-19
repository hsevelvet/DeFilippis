package com.simplicite.objects.DeFilippis;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object DF_Contact
 */
public class DF_Contact extends ObjectDB {
	private static final long serialVersionUID = 1L;
	/*
	
	@Override
	public void initUpdate() {
		Integer pid = Integer.valueOf(getRowId());
		String contact_id = String.format("%04d",pid);
		
		setFieldValue("defiContactId",contact_id);
	}
	
	@Override
	public void initRefSelect(ObjectDB parent) {
	if(parent!=null && parent.getName().equals("DF_Client")){
		setFieldFilter("DF_Contact_DF_Client_id", parent.getRowId());
		}
	}*/

	
}
