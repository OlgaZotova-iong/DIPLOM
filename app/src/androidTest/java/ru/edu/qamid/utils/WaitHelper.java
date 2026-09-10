package ru.edu.qamid.utils;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

public class WaitHelper {

    private static final long DEFAULT_TIMEOUT = 15_000L;

    public static void waitForView(int viewId) {
        waitForView(viewId, DEFAULT_TIMEOUT);
    }

    public static void waitForView(final int viewId, final long timeout) {
        onView(isRoot()).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Ожидание элемента с id: " + viewId;
            }

            @Override
            public void perform(UiController uiController, View rootView) {
                long endTime = System.currentTimeMillis() + timeout;

                while (System.currentTimeMillis() < endTime) {
                    for (View view : androidx.test.espresso.util.TreeIterables
                            .breadthFirstViewTraversal(rootView)) {

                        if (view.getId() == viewId) {
                            if (view.isShown()) {
                                return;
                            }
                        }
                    }
                    uiController.loopMainThreadForAtLeast(200);
                }

                throw new AssertionError("Элемент с id " + viewId + " не появился за " + timeout + " мс");
            }
        });
    }
}
