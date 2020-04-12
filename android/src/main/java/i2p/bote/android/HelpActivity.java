package i2p.bote.android;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;

import com.viewpagerindicator.TitlePageIndicator;

public class HelpActivity extends BoteActivityBase {
    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        // Set the action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        // Enable ActionBar app icon to behave as action to go back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the sections adapter.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Bind the page indicator to the pager.
        TitlePageIndicator pageIndicator = (TitlePageIndicator) findViewById(R.id.page_indicator);
        pageIndicator.setViewPager(mViewPager);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the help sections.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 1:
                    return getString(R.string.pref_title_identities);
                case 2:
                    return getString(R.string.changelog);
                case 3:
                    return getString(R.string.about);
                case 0:
                default:
                    return getString(R.string.start);
            }
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    return HelpHtmlFragment.newInstance(R.raw.help_identities);
                case 2:
                    return HelpHtmlFragment.newInstance(R.raw.help_changelog);
                case 3:
                    return new HelpAboutFragment();
                case 0:
                default:
                    return HelpHtmlFragment.newInstance(R.raw.help_start);
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
