package com.example.terz99.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by terz99 on 3/27/17.
 */

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>>{

    // Url to fetch data
    private String mUrl;

    /**
     * Public constructor to create new instance of EarthquakeLoader class
     * @param context is the Activity/Fragment where the call comes from
     * @param url is the URL from where we want to fetch data
     */
    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }


    /**
     * This method executes tasks in a background thread
     * It is called with forceLoad() from the onStartLoading() method in this same class
     * @return list of earthquake data
     */
    @Override
    public ArrayList<Earthquake> loadInBackground() {
        return QueryUtils.fetchEarthquakeData(mUrl);
    }


    /**
     * This method initiates the loading of the data
     * It is called after onCreateLoader in MainActivity class
     */
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
