package com.example.terz99.quakereport;

/**
 * Created by terz99 on 3/27/17.
 */

class Earthquake {

    // Double instance for the magnitude
    private double mMagnitude;
    // Long instance for the time in milliseconds
    private long mTimeInMilliseconds;
    // String object for the location
    private String mLocation;
    // String object for the URL
    private String mUrl;


    /**
     * Public constructor to create an Earthquake object instance
     * @param mMagnitude is the magnitude of the earthquake
     * @param mTimeInMilliseconds is the time when the earthquake happened in milliseconds passed
     *                            after Jan 1, 1970 (Unix Epoch time)
     * @param mLocation is the location of the earthquake
     * @param mUrl is the URL of the earthquake where the user can get more data
     */
    public Earthquake(double mMagnitude, long mTimeInMilliseconds, String mLocation, String mUrl) {
        this.mMagnitude = mMagnitude;
        this.mTimeInMilliseconds = mTimeInMilliseconds;
        this.mLocation = mLocation;
        this.mUrl = mUrl;
    }


    /**
     * @return the magnitude of the earthquake
     */
    public double getmMagnitude() {
        return mMagnitude;
    }

    /**
     * @return the time of the earthquake in milliseconds passed after Jan 1, 1970 (Unix time)
     */
    public long getmTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    /**
     * @return the location of the earthquake
     */
    public String getmLocation() {
        return mLocation;
    }

    /**
     * @return the URL of the earthquake from the web where the user can get more info
     */
    public String getmUrl() {
        return mUrl;
    }
}
