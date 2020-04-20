package i2p.bote.android.config;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Objects;

import i2p.bote.android.R;

public class AppProtectionPreferenceFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle paramBundle, String s) {
        addPreferencesFromResource(R.xml.settings_app_protection);
        setupAppProtectionSettings();
    }

    @Override
    public void onResume() {
        super.onResume();
        //noinspection ConstantConditions
        ((SettingsActivity) getActivity()).getSupportActionBar().setTitle(R.string.settings_label_app_protection);

        // Screen security only works from API 14
        Preference screenSecurityPreference = findPreference("pref_screen_security");
    }

    private void setupAppProtectionSettings() {
        Objects.requireNonNull(findPreference("pref_change_password")).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(getActivity(), SetPasswordActivity.class));
                return true;
            }
        });
    }
}
