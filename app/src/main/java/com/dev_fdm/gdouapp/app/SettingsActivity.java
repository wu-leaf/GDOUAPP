package com.dev_fdm.gdouapp.app;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.dev_fdm.gdouapp.R;


public class SettingsActivity extends ActionBarActivity {

    private SettingsFragment mSettingsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            mSettingsFragment = new SettingsFragment();
            replaceFragment(R.id.settings_container, mSettingsFragment);
        }


    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void replaceFragment(int viewId, android.app.Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(viewId, fragment).commit();
    }

    /**
     * A placeholder fragment containing a settings view.
     */
    public static class SettingsFragment extends PreferenceFragment {


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            CheckBoxPreference checkbox_all = (CheckBoxPreference) findPreference("checkbox_all");
            CheckBoxPreference checkbox_part = (CheckBoxPreference) findPreference("checkbox_part");
            checkbox_all.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    return true;
                }
            });
            checkbox_part.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    return true;
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
