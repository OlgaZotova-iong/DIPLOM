package ru.edu.qamid.utils;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

public class WaitHelper {

    public static void waitForView(int viewId, long timeoutMs) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < timeoutMs) {
            try {
                onView(withId(viewId)).check(matches(isDisplayed()));
                return;
            } catch (Throwable ignored) {
            }
        }
        onView(withId(viewId)).check(matches(isDisplayed()));
    }

    public static void waitForViewWithText(
            int containerId, String text, long timeoutMs) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < timeoutMs) {
            try {
                onView(allOf(
                        withId(containerId),
                        hasDescendant(withText(text))
                )).check(matches(isDisplayed()));
                return;
            } catch (Throwable ignored) {
            }
        }
        onView(allOf(
                withId(containerId),
                hasDescendant(withText(text))
        )).check(matches(isDisplayed()));
    }

    public static void waitForViewWithoutText(
            int containerId, String text, long timeoutMs) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < timeoutMs) {
            try {
                onView(withId(containerId))
                        .check(matches(not(hasDescendant(withText(text)))));
                return;
            } catch (Throwable ignored) {
            }
        }
        onView(withId(containerId))
                .check(matches(not(hasDescendant(withText(text)))));
    }

    public static void waitForItemInRecyclerView(
            int recyclerViewId, int itemViewId, String text, long timeoutMs) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < timeoutMs) {
            try {
                onView(withId(recyclerViewId))
                        .perform(scrollTo(
                                hasDescendant(allOf(
                                        withId(itemViewId),
                                        withText(text)
                                ))
                        ));
                return;
            } catch (Throwable ignored) {
            }
        }
        onView(withId(recyclerViewId))
                .perform(scrollTo(
                        hasDescendant(allOf(
                                withId(itemViewId),
                                withText(text)
                        ))
                ));
    }
}