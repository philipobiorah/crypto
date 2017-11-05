package com.example.qfen.crypto.NetworkUtil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class NetworkCall {

    private final static String PAGE = NetworkCall.class.getSimpleName();

    public static HashMap<String, Double> getResponse(String url, String element){
        URL uri = createUrl(url);
        String jsonString ;
        HashMap<String,Double> hashMap = new HashMap<>();

        try {
            jsonString = makeRequest(uri);
            hashMap = sortJson(jsonString, element);
        }
        catch (IOException e){
            Log.e(PAGE,"Exception: "+e);
        }
        Log.e(PAGE+" Method getResponse()","Hashmap: "+hashMap);
        return hashMap;
    }


    private static URL createUrl(String url){
        URL newUrl = null;
        try {
            newUrl = new URL(url);
        }
        catch (MalformedURLException e){
            Log.e(PAGE,"EXCEPTION: "+e);
        }
        return newUrl;
    }

    private static String makeRequest(URL url) throws IOException{
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setReadTimeout(20000);
        connection.setConnectTimeout(30000);
        connection.setRequestMethod("GET");

        InputStream inputStream = connection.getInputStream();
        String response = getJsonString(inputStream);

        return response;
    }

    private static String getJsonString(InputStream inputStream) throws IOException{
        StringBuilder string = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();
        while (line != null){
            string.append(line);
            line = reader.readLine();
        }
        Log.e(PAGE+" METHOD getJsonString()","Json string: "+string.toString());
        return string.toString();
    }

    private static HashMap<String,Double> sortJson(String jsonString, String element){
        JSONObject jsonObject;
        HashMap<String,Double> hashMap = new HashMap();
        try {
            jsonObject = new JSONObject(jsonString);

            double currency = jsonObject.getDouble(element);
            hashMap.put(element, currency);
        }
        catch (JSONException e){
            Log.e(PAGE,"Exception: "+e);
        }
        Log.e(PAGE+" METHOD sortJson()","hashmap: "+hashMap);

        return hashMap;
    }

    //check for internet connection...
    public static boolean connectivity(Context context){
        boolean state = false;
        ConnectivityManager conMgr = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
       NetworkInfo[] info = conMgr.getAllNetworkInfo();
        for(int i =0; i<info.length; i++){
            info[i].getState().toString();
            if(info[i].getState() == NetworkInfo.State.CONNECTED){
                state = true;
            }
        }
        return state;
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
