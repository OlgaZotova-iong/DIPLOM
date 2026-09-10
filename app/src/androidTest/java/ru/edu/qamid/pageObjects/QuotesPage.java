package ru.edu.qamid.pageObjects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.contrib.RecyclerViewActions;

import org.hamcrest.Matchers;

import io.qameta.allure.kotlin.Allure;
import ru.edu.qamid.R;
import ru.edu.qamid.utils.WaitHelper;

public class QuotesPage {

    private static final long DEFAULT_TIMEOUT = 15_000L;

    public void waitForQuotesScreenLoaded() {
        Allure.step("Ожидание загрузки экрана с цитатами");
        WaitHelper.waitForView(R.id.our_mission_item_list_recycler_view, DEFAULT_TIMEOUT);
    }

    public void expandFirstQuote() {
        Allure.step("Разворачивание первой цитаты");
        WaitHelper.waitForView(R.id.our_mission_item_list_recycler_view, DEFAULT_TIMEOUT);
        onView(withId(R.id.our_mission_item_list_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        androidx.test.espresso.action.ViewActions.click()));
    }

    public void collapseFirstQuote() {
        Allure.step("Сворачивание первой цитаты");
        WaitHelper.waitForView(R.id.our_mission_item_list_recycler_view, DEFAULT_TIMEOUT);
        onView(withId(R.id.our_mission_item_list_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        androidx.test.espresso.action.ViewActions.click()));
    }

    public void checkFirstQuoteIsExpanded() {
        Allure.step("Проверка, что первая цитата развернута");
        onView(Matchers.allOf(
                withId(R.id.our_mission_item_description_text_view),
                isDisplayed()))
                .check(matches(isDisplayed()));
    }

    public void checkFirstQuoteIsCollapsed() {
        Allure.step("Проверка, что первая цитата свернута");
        onView(Matchers.allOf(
                withId(R.id.our_mission_item_description_text_view),
                withText(Matchers.containsString("Ну, идеальное устройство мира в моих глазах"))))
                .check(matches(Matchers.not(isDisplayed())));
    }
}
