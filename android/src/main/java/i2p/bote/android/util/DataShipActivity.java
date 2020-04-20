package i2p.bote.android.util;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import i2p.bote.android.BoteActivityBase;
import i2p.bote.android.R;

public abstract class DataShipActivity extends BoteActivityBase implements
        DataShipFragment.Callbacks {
    public static final String EXPORTING = "exporting";

    protected boolean mExporting;

    protected abstract DataShipFragment getDataShipFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_ship);

        // Set the action bar
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        // Enable ActionBar app icon to behave as action to go back
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle args = getIntent().getExtras();
            assert args != null;
            mExporting = args.getBoolean(EXPORTING);
            DataShipFragment f = getDataShipFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.data_ship_frag, f).commit();
        }
    }
}
