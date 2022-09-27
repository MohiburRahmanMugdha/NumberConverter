package com.mrapps.numberconverter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.mrapps.numberconverter.R;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // show settingsfragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_fragment_container, new SettingsFragment())
                .commit();

    }


    public static class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Preference share = findPreference("shareApp");
            if (share != null) {
                share.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        // send text intent

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.mrapps.numberconverter");
                        startActivity(Intent.createChooser(shareIntent, "Share via"));

                        return false;

                    }
                });
            }

            Preference privacy = findPreference("openPrivacy");
            if (privacy != null) {
                privacy.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {

                        // intent open link
                        Intent privacyIntent = new Intent(Intent.ACTION_VIEW);
                        privacyIntent.setData(android.net.Uri.parse("https://google.com"));
                        startActivity(privacyIntent);


                        return false;
                    }
                });
            }



        }
    }

}