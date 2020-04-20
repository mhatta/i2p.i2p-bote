package i2p.bote.android.identities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import i2p.bote.android.BoteActivityBase;
import i2p.bote.android.R;
import i2p.bote.email.EmailIdentity;

public class IdentityListActivity extends BoteActivityBase implements
        IdentityListFragment.OnIdentitySelectedListener {
    static final int ALTER_IDENTITY_LIST = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        // Set the action bar
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        // Enable ActionBar app icon to behave as action to go back
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            IdentityListFragment f = new IdentityListFragment();
            getSupportFragmentManager().beginTransaction()
                .add(R.id.container, f).commit();
        }
    }

    @Override
    public void onIdentitySelected(EmailIdentity identity) {
        Intent i = new Intent(this, ViewIdentityActivity.class);
        i.putExtra(ViewIdentityFragment.ADDRESS, identity.getKey());
        startActivityForResult(i, ALTER_IDENTITY_LIST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ALTER_IDENTITY_LIST) {
            if (resultCode == Activity.RESULT_OK) {
                IdentityListFragment f = (IdentityListFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                assert f != null;
                f.updateIdentityList();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
