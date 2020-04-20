package i2p.bote.android.config;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Objects;

import i2p.bote.android.R;

public class AppearancePreferenceFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle paramBundle, String s) {
        addPreferencesFromResource(R.xml.settings_appearance);
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(
                (SettingsActivity) getActivity()
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((SettingsActivity) requireActivity()).getSupportActionBar()).setTitle(R.string.settings_label_appearance);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(
                (SettingsActivity) getActivity()
        );
    }
}
