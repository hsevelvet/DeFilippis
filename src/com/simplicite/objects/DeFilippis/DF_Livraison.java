package com.simplicite.objects.DeFilippis;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * Business object DF_Livraison
 */
public class DF_Livraison extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	public String test(){
		 // Create Client
	     String key = "2a60830dac07cc84886736912e6dd71d";
	     String token = "d21e3ce0d931ba69fb1f6dcd971bd182cfe8e4ca84e00f6c4a46792e6bf967b7";
 String url = "https://api.trello.com/1/members/me/boards?key="+key+"&token="+token;
 
 InputStream is = null;
JSONObject jObj = null;
String json = "";
  // Making HTTP request
    try {
        // defaultHttpClient
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpPost = new HttpGet(url);

        HttpResponse httpResponse = httpClient.execute(httpPost);
     
    	HttpEntity entity = httpResponse.getEntity();
		String responseString = EntityUtils.toString(entity, "UTF-8");
     
     
     
        HttpEntity httpEntity = httpResponse.getEntity();
        is = httpEntity.getContent();

    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    } catch (ClientProtocolException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

    try {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                is, "iso-8859-1"), 8);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
            System.out.println(line);
        }
        is.close();
        json = sb.toString();

    } catch (Exception e) {
    }

    // try parse the string to a JSON object
    try {
        jObj = new JSONObject(json);
    } catch (JSONException e) {
        System.out.println("error on parse data in jsonparser.java");
    }

    // return JSON String
    //return jObj;

		return responseString;
	}
	
	
	
	
	@Override
	public String postUpdate() {
		
		
		//return Message.formatInfo("INFO_CODE", "Message", "fieldName");
		//return Message.formatWarning("WARNING_CODE", "Message", "fieldName");
		//return Message.formatError("ERROR_CODE", "Message", "fieldName");
		return null;
	}
}
