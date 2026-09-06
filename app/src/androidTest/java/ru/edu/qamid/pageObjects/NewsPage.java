package ru.edu.qamid.pageObjects;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.MenuItem;
import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import io.qameta.allure.kotlin.Allure;
import ru.edu.qamid.R;

public class NewsPage {

    private static final long DEFAULT_TIMEOUT = 15_000L;

    public void waitForNewsScreenLoaded() {
        Allure.step("Ожидание загрузки экрана с новостями");
        onView(isRoot()).perform(waitForView(R.id.main_news_list_container, DEFAULT_TIMEOUT));
    }

    // Новый метод специально для экрана управления новостями
    public void waitForNewsManagementScreenLoaded() {
        Allure.step("Ожидание загрузки экрана управления новостями");
        onView(isRoot()).perform(waitForView(R.id.news_list_recycler_view, DEFAULT_TIMEOUT));
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
        onView(withId(R.id.news_list_recycler_view))
                .check(matches(isDisplayed()));
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

    public void openQuotesSection() {
        Allure.step("Переход в раздел Цитаты");
        onView(withId(R.id.our_mission_image_button))
                .check(matches(isDisplayed()))
                .perform(click());
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

    private static ViewAction waitForView(final int viewId, final long timeout) {
        return new ViewAction() {
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

                throw new AssertionError("Элемент с id " + viewId + " не найден за " + timeout + " мс");
            }
        };
    }
}
