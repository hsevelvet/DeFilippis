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
import org.json.*;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


/**
 * Business object DF_Livraison
 */
public class DF_Livraison extends ObjectDB {
	private static final long serialVersionUID = 1L;
	private static final String key = "2a60830dac07cc84886736912e6dd71d";
	private static final String token = "d21e3ce0d931ba69fb1f6dcd971bd182cfe8e4ca84e00f6c4a46792e6bf967b7";
	private static final String boardNameValue = "DefilippisLivraison";
	private static final String listNameValue = "À faire";

	
	
	private static String streamToString(InputStream inputStream) {
	    String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
	    return text;
	}
	public static String jsonGetRequest(String urlQueryString,String httpMethod) {
	    String json = null;
	    try {
	      URL url = new URL(urlQueryString);
	      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	      connection.setDoOutput(true);
	      connection.setInstanceFollowRedirects(false);
	      connection.setRequestMethod(httpMethod);
	      connection.setRequestProperty("Content-Type", "application/json");
	      connection.setRequestProperty("charset", "utf-8");
	      connection.connect();
	      InputStream inStream = connection.getInputStream();
	      json = streamToString(inStream); // input stream to string
	    } catch (IOException ex) {
	      ex.printStackTrace();
	    }
	    return json;
	}
	
	public String test(){
		 // Create Client
		String urlBoard = "https://api.trello.com/1/members/me/boards?key="+key+"&token="+token;
		String urlList = "";
		String urlCreateCard="";

		String json = jsonGetRequest(urlBoard,"GET");
		JSONArray arr =   new JSONArray(json);
		
		String boardName= "";
		String shortLink ="";
		String listName= "";
		String idList= "";
		String result="defaut";
		Boolean trouve = false;
		
		//Chercher le softlink
		for (int i=0; i < arr.length(); i++) {
		    boardName = arr.getJSONObject(i).getString("name");
		    if (boardName.equals(boardNameValue)){
		    	shortLink = arr.getJSONObject(i).getString("shortLink");
		    	urlList = "https://api.trello.com/1/boards/"+shortLink+"/lists?key="+key+"&token="+token;
		    }
		}
		
		//Chercher l'idList
		if (urlList.length()>0) {
			String jsonList = jsonGetRequest(urlList,"GET");
			JSONArray arrList =   new JSONArray(jsonList);
			for (int i=0; i < arrList.length(); i++) {
			    listName = arrList.getJSONObject(i).getString("name");
			    if (listName.equals(listNameValue)){
			    	idList = arrList.getJSONObject(i).getString("id");
			    	urlCreateCard = "https://api.trello.com/1/cards?name="+"TTESST"+"&desc=test&idList="+idList+"&keepFromSource=all&key="+key+"&token="+token;
			    }
			}
		}
		
		if (idList.length()>0) {
		    String jsonCard = jsonGetRequest(urlCreateCard,"POST");
			JSONObject cardJson =   new JSONObject(jsonCard);
			result = cardJson.getString("shortUrl");
		}

		
		return result;
	}
	
	
	
	
	
	
	@Override
	public String postUpdate() {
		
		
		//return Message.formatInfo("INFO_CODE", "Message", "fieldName");
		//return Message.formatWarning("WARNING_CODE", "Message", "fieldName");
		//return Message.formatError("ERROR_CODE", "Message", "fieldName");
		return null;
	}
}
