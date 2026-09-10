package ru.edu.qamid.pageObjects;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.MenuItem;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import io.qameta.allure.kotlin.Allure;
import ru.edu.qamid.R;
import ru.edu.qamid.utils.WaitHelper;

public class NewsPage {

    private static final long DEFAULT_TIMEOUT = 15_000L;

    public void waitForNewsScreenLoaded() {
        Allure.step("Ожидание загрузки экрана с новостями");
        WaitHelper.waitForView(R.id.main_news_list_container, DEFAULT_TIMEOUT);
    }

    public void waitForNewsManagementScreenLoaded() {
        Allure.step("Ожидание загрузки экрана управления новостями");
        WaitHelper.waitForView(R.id.news_list_recycler_view, DEFAULT_TIMEOUT);
    }

    public void expandNewsList() {
        Allure.step("Разворачивание списка новостей");
        onView(withId(R.id.expand_material_button))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void collapseNewsList() {
        Allure.step("Сворачивание списка новостей");
        onView(withId(R.id.expand_material_button))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void pullToRefreshOnMainScreen() {
        Allure.step("Pull-to-Refresh на главной странице");
        onView(withId(R.id.main_swipe_refresh))
                .check(matches(isDisplayed()))
                .perform(swipeDown());
    }

    public void checkNewsListIsDisplayed() {
        Allure.step("Проверка отображения списка новостей");
        WaitHelper.waitForView(R.id.news_list_recycler_view, DEFAULT_TIMEOUT);
        onView(withId(R.id.news_list_recycler_view))
                .check(matches(withEffectiveVisibility(androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE)));
    }

    public void openMainMenu() {
        Allure.step("Открытие главного меню");
        onView(withId(R.id.main_menu_image_button))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void navigateToNews() {
        Allure.step("Переход в раздел Новости через меню");
        onData(menuItemWithId(R.id.menu_item_news))
                .inRoot(isPlatformPopup())
                .perform(click());
    }

    public void openQuotesSection() {
        Allure.step("Переход в раздел Цитаты");
        onView(withId(R.id.our_mission_image_button))
                .check(matches(isDisplayed()))
                .perform(click());

        WaitHelper.waitForView(R.id.our_mission_item_list_recycler_view, DEFAULT_TIMEOUT);
    }

    public void openNewsControlPanel() {
        Allure.step("Открытие панели управления новостями");
        onView(withId(R.id.news_edit_button))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void checkOurMissionScreenIsDisplayed() {
        Allure.step("Проверка экрана 'О приложении'");
        onView(withId(R.id.our_mission_title_text_view))
                .check(matches(isDisplayed()));
    }

    private static Matcher<Object> menuItemWithId(final int menuItemId) {
        return new TypeSafeMatcher<Object>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("MenuItem with id: " + menuItemId);
            }

            @Override
            protected boolean matchesSafely(Object item) {
                if (item instanceof MenuItem) {
                    return ((MenuItem) item).getItemId() == menuItemId;
                }
                return false;
            }
        };
    }
}
