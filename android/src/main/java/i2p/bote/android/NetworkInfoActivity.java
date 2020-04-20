package i2p.bote.android;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class NetworkInfoActivity extends BoteActivityBase {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        // Set the action bar
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        // Enable ActionBar app icon to behave as action to go back
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            NetworkInfoFragment f = new NetworkInfoFragment();
            getSupportFragmentManager().beginTransaction()
                .add(R.id.container, f).commit();
        }
    }
}
