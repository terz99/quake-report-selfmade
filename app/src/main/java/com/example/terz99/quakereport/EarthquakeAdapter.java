package com.example.terz99.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by terz99 on 3/27/17.
 */

class EarthquakeAdapter extends ArrayAdapter<Earthquake>{

    // Context object instance to store the Activity/Fragment where the call comes from
    private Context mContext;
    // Location separator
    private static final String LOCATION_SEPARATOR = " of ";
    // Log tag
    private static final String LOG_TAG = EarthquakeAdapter.class.getSimpleName();


    /**
     * Public constructor which calls the super constructor
     * @param context is the Activity/Fragment where the call comes from
     * @param earthquakes is list of earthquakes
     */
    public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquakes){
        super(context, 0, earthquakes);
        mContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }


        // Get the current earthquake object
        Earthquake currentEarthquake = getItem(position);



        // Get link from the magnitude textview
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        // Set magnitude to the current earthquake's magnitude value
        magnitudeTextView.setText(String.valueOf(currentEarthquake.getmMagnitude()));



        // Split the location into two parts
        // Example: "90km S of Skopje, Macedonia" -> "90km S of" and "Skopje, Macedonia"
        // Example: "Pacific Region" -> "Near the" and "Pacific Region"
        String [] strings = split(currentEarthquake.getmLocation());

        // Offset location
        String offsetLocation = strings[0];
        // Primary location
        String primaryLocation = strings[1];

        // Get link to the offset location text view
        TextView offsetLocationTextView = (TextView) listItemView.findViewById(R.id.offset_location);
        // Set the offset location text view to current earthquake's offset location
        offsetLocationTextView.setText(offsetLocation);
        // Get link to the primary location text view
        TextView primaryLocationTextView = (TextView) listItemView.findViewById(R.id.primary_location);
        // Set the offset location text view to current earthquake's offset location
        primaryLocationTextView.setText(primaryLocation);



        // Get the time of the earthquake
        Date date = new Date(currentEarthquake.getmTimeInMilliseconds());
        // Format the date into this format: Jan 10, 1999
        String formattedDate = formatDate(date);
        // Format the time into this format: 7:24 PM
        String formattedTime = formatTime(date);
        // Get link from the date text view
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        // Set the date
        dateTextView.setText(formattedDate);
        // Get link from the time text view
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        // Set the time
        timeTextView.setText(formattedTime);



        // Get link from the magntiude background
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();
        // Set the color according to the magnitude value
        magnitudeCircle.setColor(getColor(currentEarthquake.getmMagnitude()));

        return listItemView;
    }


    /**
     * This method returns a color according to the value of the magnitude
     * @param magnitude is the magnitude of the color
     * @return int ID for the corresponding color
     */
    private int getColor(double magnitude) {

        if(magnitude <= 2.0){
            return ContextCompat.getColor(getContext(), R.color.magnitude1);
        } else if(magnitude <= 3.0){
            return ContextCompat.getColor(getContext(), R.color.magnitude2);
        } else if(magnitude <= 4.0){
            return ContextCompat.getColor(getContext(), R.color.magnitude3);
        } else if(magnitude <= 5.0){
            return ContextCompat.getColor(getContext(), R.color.magnitude4);
        } else if(magnitude <= 6.0){
            return ContextCompat.getColor(getContext(), R.color.magnitude5);
        } else if(magnitude <= 7.0){
            return ContextCompat.getColor(getContext(), R.color.magnitude6);
        } else if(magnitude <= 8.0){
            return ContextCompat.getColor(getContext(), R.color.magnitude7);
        } else if(magnitude <= 9.0){
            return ContextCompat.getColor(getContext(), R.color.magnitude8);
        } else if(magnitude <= 10.0){
            return ContextCompat.getColor(getContext(), R.color.magnitude9);
        } else {
            return ContextCompat.getColor(getContext(), R.color.magnitude10plus);
        }
    }


    /**
     * This method formats the time into this format: 7:24 PM
     * @param date
     * @return formatted time into format 7:24 PM
     */
    private String formatTime(Date date) {
        SimpleDateFormat formattedTime = new SimpleDateFormat("h:mm, a");
        return formattedTime.format(date);
    }


    /**
     * This method formats the date into this format: Jan 10, 1999
     * @param date
     * @return formatted date into format Jan 10, 1999
     */
    private String formatDate(Date date) {
        SimpleDateFormat formattedDate = new SimpleDateFormat("MMM dd, yyyy");
        return formattedDate.format(date);
    }


    /**
     * This method splits one string into two
     * Example: "90km S of Skopje, Macedonia" -> "90km S of" and "Skopje, Macedonia"
     * Example: "Pacific Region" -> "Near the" and "Pacific Region"
     * @param s is a String which is being split
     * @return array of two String objects
     */
    private String[] split(String s) {

        String [] strings = new String[2];

        if(s.contains(LOCATION_SEPARATOR)){
            strings = s.split(LOCATION_SEPARATOR);
            strings[0] = strings[0] + LOCATION_SEPARATOR;
        } else {
            strings[0] = getContext().getResources().getString(R.string.near_the);
            strings[1] = s;
        }

        return strings;
    }
}
