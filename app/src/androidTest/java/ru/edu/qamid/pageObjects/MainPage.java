package ru.edu.qamid.pageObjects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

import io.qameta.allure.kotlin.Step;
import ru.edu.qamid.R;

public class MainPage {

    private static final long MAIN_SCREEN_TIMEOUT = 20_000L;

    @Step("Проверка отображения главного экрана")
    public boolean checkIsOnNewsScreen() {
        try {
            onView(withId(R.id.main_news_list_container))
                    .check(matches(isDisplayed()));

            return true;
        } catch (Throwable exception) {
            return false;
        }
    }

    @Step("Ожидание открытия главного экрана")
    public void waitForMainScreen() {
        onView(isRoot()).perform(
                waitForView(
                        R.id.main_news_list_container,
                        MAIN_SCREEN_TIMEOUT
                )
        );
    }

    @Step("Выход из личного кабинета через меню")
    public void logout() {
        onView(withId(R.id.authorization_image_button))
                .perform(click());

        onView(withText(R.string.log_out))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    private static ViewAction waitForView(
            int viewId,
            long timeout
    ) {
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
            public void perform(
                    UiController uiController,
                    View rootView
            ) {
                long endTime =
                        System.currentTimeMillis() + timeout;

                while (System.currentTimeMillis() < endTime) {
                    for (View view :
                            androidx.test.espresso.util.TreeIterables
                                    .breadthFirstViewTraversal(rootView)) {

                        if (view.getId() == viewId) {
                            if (view.isShown()) {
                                return;
                            }
                        }
                    }

                    uiController.loopMainThreadForAtLeast(200);
                }

                throw new AssertionError(
                        "Главный экран не появился за "
                                + timeout
                                + " мс"
                );
            }
        };
    }
}

