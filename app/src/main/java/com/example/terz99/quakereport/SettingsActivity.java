package com.example.terz99.quakereport;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {


    /**
     * This method creates the SettingsActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }


    /**
     * The fragment which contains the preferences
     */
    public static class EarthquakePreferenceFragment extends PreferenceFragment
    implements Preference.OnPreferenceChangeListener{


        /**
         * This method creates the layout of the fragment
         * @param savedInstanceState
         */
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            // Get the magnitude preference and bind its value to the summary of the preference
            // Summary is a type of indicator which shows which option is chosen in the given
            // preference
            Preference minMagnitude = findPreference(getString(
                    R.string.settings_min_magnitude_key));
            bindPreferenceSummaryToValue(minMagnitude);


            // Get the order by preference and bind its value to the summary of the preference
            // Summary is a type of indicator which shows which option is chosen in the given
            // preference
            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(orderBy);
        }


        /**
         * This method sets the OnChangeListener to the preferences and displays the summary
         * in the passed preference
         * @param preference the preference which is passed from the OnCreate method
         */
        private void bindPreferenceSummaryToValue(Preference preference) {

            // Set the OnChangeListener
            preference.setOnPreferenceChangeListener(this);
            // Get link from the preferences
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(
                    preference.getContext());

            // Get the value with key from the preference
            String preferenceString = preferences.getString(preference.getKey(), "");
            // Change the summary on the passed preference
            onPreferenceChange(preference, preferenceString);
        }


        /**
         * This method changes the summary of the preference
         * @param preference the preference which is changed
         * @param newValue the new value given to the preference
         * @return true
         */
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {

            String stringValue = newValue.toString();

            // If the order by preference is changed
            if(preference instanceof ListPreference){

                // Get link from the list preferences
                ListPreference listPreference = (ListPreference) preference;
                // See which value is selected
                int preferenceIndex = listPreference.findIndexOfValue(stringValue);
                // Check if anything is changed
                if(preferenceIndex >= 0){
                    // Get the labels from the string-array which is instantiated to the list
                    // preferences
                    CharSequence[] labels = listPreference.getEntries();
                    // Set the summary to the new selected value from the labels string-array
                    preference.setSummary(labels[preferenceIndex]);
                }
            } else { // If the minimum magnitude preference is changed
                // Set the minimum magnitude summary to the minimum magnitude preference
                preference.setSummary(stringValue);
            }
            return true;
        }
    }
}
