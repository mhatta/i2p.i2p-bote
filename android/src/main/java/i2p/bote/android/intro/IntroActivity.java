package i2p.bote.android.intro;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.viewpagerindicator.LinePageIndicator;

import java.util.Objects;

import i2p.bote.android.BoteActivityBase;
import i2p.bote.android.R;

public class IntroActivity extends BoteActivityBase {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // Create the sections adapter.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Bind the page indicator to the pager.
        LinePageIndicator pageIndicator = findViewById(R.id.page_indicator);
        pageIndicator.setViewPager(mViewPager);

        findViewById(R.id.skip_intro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the intro sections.
     */
    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 6;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            assert getArguments() != null;
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    return inflater.inflate(R.layout.fragment_intro_1, container, false);
                case 2:
                    return inflater.inflate(R.layout.fragment_intro_2, container, false);
                case 3:
                    return inflater.inflate(R.layout.fragment_intro_3, container, false);
                case 4:
                    return inflater.inflate(R.layout.fragment_intro_4, container, false);
                case 5:
                    View v5 = inflater.inflate(R.layout.fragment_intro_5, container, false);
                    Button b = v5.findViewById(R.id.start_setup_wizard);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            requireActivity().setResult(Activity.RESULT_OK);
                            requireActivity().finish();
                        }
                    });
                    return v5;

                default:
                    View v0 = inflater.inflate(R.layout.fragment_intro_0, container, false);
                    TextView tv = v0.findViewById(R.id.intro_app_name);
                    tv.append(".");

                    TextView swipe = v0.findViewById(R.id.intro_swipe_to_start);
                    swipe.setCompoundDrawablesWithIntrinsicBounds(
                            new IconicsDrawable(requireActivity(), GoogleMaterial.Icon.gmd_arrow_back)
                                    .colorRes(R.color.md_grey_600).sizeDp(24).paddingDp(4),
                            null, null, null
                    );

                    return v0;
            }
        }
    }
}
