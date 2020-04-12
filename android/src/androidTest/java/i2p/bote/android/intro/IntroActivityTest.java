package i2p.bote.android.intro;

import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import i2p.bote.android.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class IntroActivityTest {

    @Rule
    public ActivityTestRule<IntroActivity> mRule = new ActivityTestRule<>(IntroActivity.class);

    @Test
    public void enterSetup() {
        onView(withId(R.id.pager)).perform(swipeLeft(), swipeLeft(), swipeLeft(), swipeLeft(), swipeLeft());
        onView(withId(R.id.start_setup_wizard)).perform(click());
        // TODO check result
    }

    @Test
    public void closeIntro() {
        onView(withId(R.id.skip_intro)).perform(click());
        // TODO check result
    }
}