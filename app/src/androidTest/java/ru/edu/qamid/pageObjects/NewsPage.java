package ru.edu.qamid.pageObjects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

import io.qameta.allure.kotlin.Allure;
import ru.edu.qamid.R;

public class NewsPage {

    private static final long NEWS_SCREEN_TIMEOUT = 20000L;

    private final int newsListContainerId = R.id.main_news_list_container;
    private final int expandButtonId = R.id.expand_material_button;
    private final int newsRecyclerViewId = R.id.news_list_recycler_view;
    private final int swipeRefreshMainId = R.id.main_swipe_refresh;
    private final int swipeRefreshNewsId = R.id.news_list_swipe_refresh;

    private final int mainMenuButtonId = R.id.main_menu_image_button;
    private final int ourMissionButtonId = R.id.our_mission_image_button;
    private final int authorizationButtonId = R.id.authorization_image_button;

    public void waitForNewsScreenLoaded() {
        Allure.step("Ожидание загрузки экрана с новостями");
        onView(isRoot()).perform(waitForView(newsListContainerId, NEWS_SCREEN_TIMEOUT));
    }

    public void expandNewsList() {
        Allure.step("Разворачивание списка новостей");
        onView(withId(expandButtonId))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void collapseNewsList() {
        Allure.step("Сворачивание списка новостей");
        onView(withId(expandButtonId))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void pullToRefreshOnMainScreen() {
        Allure.step("Pull-to-Refresh на главной странице");
        onView(withId(swipeRefreshMainId))
                .check(matches(isDisplayed()))
                .perform(swipeDown());
    }

    public void pullToRefreshOnNewsScreen() {
        Allure.step("Pull-to-Refresh в разделе Новости");
        onView(withId(swipeRefreshNewsId))
                .check(matches(isDisplayed()))
                .perform(swipeDown());
    }

    public void checkNewsListIsDisplayed() {
        Allure.step("Проверка отображения списка новостей");
        onView(withId(newsRecyclerViewId))
                .check(matches(isDisplayed()));
    }

    public void openMainMenu() {
        Allure.step("Открытие главного меню");
        onView(withId(mainMenuButtonId))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void openQuotesSection() {
        Allure.step("Переход в раздел Цитаты");
        onView(withId(ourMissionButtonId))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void openAuthorizationMenu() {
        Allure.step("Открытие меню авторизации");
        onView(withId(authorizationButtonId))
                .check(matches(isDisplayed()))
                .perform(click());
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

                throw new AssertionError("Экран новостей не загрузился за " + timeout + " мс");
            }
        };
    }
}
