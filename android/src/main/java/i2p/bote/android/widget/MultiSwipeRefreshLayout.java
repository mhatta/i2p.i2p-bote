package i2p.bote.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * A descendant of {@link SwipeRefreshLayout} which supports multiple
 * child views triggering a refresh gesture. You set the views which can trigger the gesture via
 * {@link #setSwipeableChildren(int...)}, providing it the child ids.
 */
public class MultiSwipeRefreshLayout extends SwipeRefreshLayout {

    private View[] mSwipeableChildren;

    public MultiSwipeRefreshLayout(Context context) {
        super(context);
    }

    public MultiSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Set the children which can trigger a refresh by swiping down when they are visible. These
     * views need to be a descendant of this view.
     */
    public void setSwipeableChildren(final int... ids) {
        assert ids != null;

        // Iterate through the ids and find the Views
        mSwipeableChildren = new View[ids.length];
        for (int i = 0; i < ids.length; i++) {
            mSwipeableChildren[i] = findViewById(ids[i]);
        }
    }

    /**
     * This method controls when the swipe-to-refresh gesture is triggered. By returning false here
     * we are signifying that the view is in a state where a refresh gesture can start.
     *
     * <p>As {@link SwipeRefreshLayout} only supports one direct child by
     * default, we need to manually iterate through our swipeable children to see if any are in a
     * state to trigger the gesture. If so we return false to start the gesture.
     */
    @Override
    public boolean canChildScrollUp() {
        if (mSwipeableChildren != null && mSwipeableChildren.length > 0) {
            // Iterate through the scrollable children and check if any of them can not scroll up
            for (View view : mSwipeableChildren) {
                if (view != null && view.isShown() && !canViewScrollUp(view)) {
                    // If the view is shown, and can not scroll upwards, return false and start the
                    // gesture.
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Utility method to check whether a {@link View} can scroll up from it's current position.
     * Handles platform version differences, providing backwards compatible functionality where
     * needed.
     */
    private static boolean canViewScrollUp(View view) {
        // For ICS and above we can call canScrollVertically() to determine this
        return ViewCompat.canScrollVertically(view, -1);
    }
}
