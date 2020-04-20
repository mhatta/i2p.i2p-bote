package i2p.bote.android;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import i2p.bote.android.util.LocaleManager;

@SuppressLint("Registered")
public class BoteActivityBase extends AppCompatActivity {
    private final LocaleManager localeManager = new LocaleManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        localeManager.onCreate(this);
        super.onCreate(savedInstanceState);

        // Initialize I2P settings
        InitActivities init = new InitActivities(this);
        init.initialize();

        // Initialize screen security
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("pref_screen_security", true))
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        else
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    public void onResume() {
        super.onResume();
        localeManager.onResume(this);
    }

    public void notifyLocaleChanged() {
        localeManager.onResume(this);
    }
}
