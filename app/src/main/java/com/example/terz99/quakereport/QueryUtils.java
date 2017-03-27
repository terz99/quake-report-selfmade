package com.example.terz99.quakereport;

import android.util.Log;
import android.util.StringBuilderPrinter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by terz99 on 3/27/17.
 */

class QueryUtils {

    // Time for read timeout in milliseconds
    private static final int READ_TIMEOUT = 10000;

    // Time for connect timeout in milliseconds
    private static final int CONNECT_TIMEOUT = 15000;

    // Log tag
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Public constructor to create instance of this object
     */
    public QueryUtils() {
    }


    /**
     * This method returns list of earthquake data from the given url
     * @param mUrl is the URL where the app will be catching data from
     * @return a list of earthquake data
     */
    public static ArrayList<Earthquake> fetchEarthquakeData(String mUrl) {

        // Check if this is a malicious URL
        if(mUrl == null || mUrl.isEmpty()){
            return null;
        }

        // Create a URL object
        URL url = createUrl(mUrl);

        // If the URL is not good then return null
        if(url == null){
            return null;
        }


        // Try to make HTTP request to get the JSON response
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If the JSON response was not successfully fetched then return null
        if(jsonResponse == null){
            return null;
        }

        // Otherwise, extract data from JSON response
        ArrayList<Earthquake> earthquakes = extractFromJSON(jsonResponse);
        // Return the list of earthquake data
        return earthquakes;
    }


    /**
     * This method extract data from a raw JSON response
     * @param jsonResponse is the raw JSON response
     * @return list of earthquake data
     */
    private static ArrayList<Earthquake> extractFromJSON(String jsonResponse) {

        // Initialize the list which is meant to be returned
        ArrayList<Earthquake> earthquakes = new ArrayList<Earthquake>();

        try {

            // Get the root object from JSON response
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray features = root.getJSONArray("features");

            for(int i = 0; i < features.length(); i++){

                JSONObject featuresObject = features.getJSONObject(i);
                JSONObject properties = featuresObject.getJSONObject("properties");

                // Extract the value of the magnitude
                double magnitude = properties.getDouble("mag");
                // Extract the value of the time
                long time = properties.getLong("time");
                // Extract the value of the location
                String location = properties.getString("place");
                // Extract the value of the url
                String url = properties.getString("url");

                // Add a new Earthquake object to the list
                earthquakes.add(new Earthquake(magnitude, time, location, url));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error parsing JSON response", e);
        }

        // Return the list of data
        return earthquakes;
    }


    /**
     * This method makes an HTTP request to the given URL
     * @param url is the URL from where the request fetches data
     * @return a raw JSON response in a form of String
     * @throws IOException
     */
    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = null;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(READ_TIMEOUT);
        urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
        urlConnection.connect();

        if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } else {
            Log.e(LOG_TAG, "Problem making HTTP URL connection");
        }

        urlConnection.disconnect();

        if(inputStream != null){
            inputStream.close();
        }

        return jsonResponse;
    }


    /**
     * This method helps to read from stream and transfer all the data to a StringBuilder
     * @param inputStream is the input stream from where the data is read
     * @return a raw JSON response in type of String made from the StringBuilder
     * @throws IOException
     */
    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();

        if(inputStream != null){

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                    Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }


    /**
     * This method creates a URL object from a string URL
     * @param mUrl is the String URL from which the URL object is created
     * @return a URL object
     */
    private static URL createUrl(String mUrl) {

        URL url = null;
        try {
            url = new URL(mUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem creating URL", e);
        }

        return url;
    }
}
