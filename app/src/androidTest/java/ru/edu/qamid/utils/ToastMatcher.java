package ru.edu.qamid.utils;

import android.view.View;
import androidx.test.espresso.Root;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ToastMatcher extends TypeSafeMatcher<Root> {

    @Override
    public void describeTo(Description description) {
        description.appendText("is a Toast");
    }

    @Override
    public boolean matchesSafely(Root root) {
        int type = root.getWindowLayoutParams().get().type;
        if (type == android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY) {
            final View decorView = root.getDecorView();
            if (decorView.getWindowToken() != null) {
                if (decorView.getWindowVisibility() == View.VISIBLE) {
                    return true;
                }
            }
        }
        if (type == android.view.WindowManager.LayoutParams.TYPE_TOAST) {
            final View decorView = root.getDecorView();
            if (decorView.getWindowToken() != null) {
                if (decorView.getWindowVisibility() == View.VISIBLE) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Matcher<Root> isToast() {
        return new ToastMatcher();
    }
}
