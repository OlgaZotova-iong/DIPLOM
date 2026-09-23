package ru.edu.qamid.pageObjects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

import io.qameta.allure.kotlin.Allure;
import ru.edu.qamid.R;
import ru.edu.qamid.utils.WaitHelper;

public class ControlPanelPage {

    private static final long DEFAULT_TIMEOUT = 15_000L;
    private static final long EXTENDED_TIMEOUT = 30_000L;

    private static ViewAction clickChildViewWithId(final int viewId) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isDisplayed();
            }

            @Override
            public String getDescription() {
                return "Клик по дочернему view с id: " + viewId;
            }

            @Override
            public void perform(UiController uiController, View view) {
                View childView = view.findViewById(viewId);
                if (childView != null) {
                    childView.performClick();
                }
            }
        };
    }

    private static ViewAction getTextFromChildViewWithId(
            final int viewId, final String[] result) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isDisplayed();
            }

            @Override
            public String getDescription() {
                return "Получение текста из дочернего view с id: " + viewId;
            }

            @Override
            public void perform(UiController uiController, View view) {
                android.widget.TextView tv = view.findViewById(viewId);
                if (tv != null) {
                    result[0] = tv.getText().toString();
                }
            }
        };
    }

    public void clickAddNewsButton() {
        Allure.step("Нажатие на кнопку добавления новости");
        WaitHelper.waitForView(R.id.add_news_image_view, DEFAULT_TIMEOUT);
        onView(withId(R.id.add_news_image_view))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void checkIsControlPanelDisplayed() {
        Allure.step("Проверка отображения панели управления новостями");
        WaitHelper.waitForView(R.id.news_list_recycler_view, DEFAULT_TIMEOUT);
        onView(withId(R.id.news_list_recycler_view))
                .check(matches(isDisplayed()));
    }

    public void clickEditNewsButton() {
        Allure.step("Нажатие на кнопку редактирования первой новости");
        WaitHelper.waitForView(R.id.news_list_recycler_view, DEFAULT_TIMEOUT);
        onView(withId(R.id.news_list_recycler_view))
                .perform(actionOnItemAtPosition(0,
                        clickChildViewWithId(R.id.news_item_edit_image_view)));
    }

    public void clickDeleteNewsButton() {
        Allure.step("Нажатие на кнопку удаления первой новости");
        WaitHelper.waitForView(R.id.news_list_recycler_view, DEFAULT_TIMEOUT);
        onView(withId(R.id.news_list_recycler_view))
                .perform(actionOnItemAtPosition(0,
                        clickChildViewWithId(R.id.news_item_delete_image_view)));
    }

    public void confirmDelete() {
        Allure.step("Подтверждение удаления новости");
        WaitHelper.waitForView(android.R.id.button1, DEFAULT_TIMEOUT);
        onView(withId(android.R.id.button1))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void cancelDelete() {
        Allure.step("Отмена удаления новости");
        WaitHelper.waitForView(android.R.id.button2, DEFAULT_TIMEOUT);
        onView(withId(android.R.id.button2))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void confirmExitWithoutSaving() {
        Allure.step("Подтверждение выхода без сохранения");
        WaitHelper.waitForView(android.R.id.button1, DEFAULT_TIMEOUT);
        onView(withId(android.R.id.button1))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void cancelExitWithoutSaving() {
        Allure.step("Отмена выхода без сохранения");
        WaitHelper.waitForView(android.R.id.button2, DEFAULT_TIMEOUT);
        onView(withId(android.R.id.button2))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void pullToRefresh() {
        Allure.step("Pull-to-Refresh в панели управления");
        WaitHelper.waitForView(
                R.id.news_control_panel_swipe_to_refresh, DEFAULT_TIMEOUT);
        onView(withId(R.id.news_control_panel_swipe_to_refresh))
                .check(matches(isDisplayed()))
                .perform(swipeDown());
    }

    public String getFirstNewsTitle() {
        Allure.step("Получение заголовка первой новости в списке");
        final String[] title = {""};
        WaitHelper.waitForView(R.id.news_list_recycler_view, DEFAULT_TIMEOUT);
        onView(withId(R.id.news_list_recycler_view))
                .perform(actionOnItemAtPosition(0,
                        getTextFromChildViewWithId(
                                R.id.news_item_title_text_view, title)));
        return title[0];
    }

    public void checkNewsExistsInList(String newsTitle) {
        Allure.step("Проверка наличия новости в списке: " + newsTitle);
        WaitHelper.waitForView(R.id.news_list_recycler_view, EXTENDED_TIMEOUT);
        WaitHelper.waitForItemInRecyclerView(
                R.id.news_list_recycler_view,
                R.id.news_item_title_text_view,
                newsTitle,
                EXTENDED_TIMEOUT
        );
    }

    public void checkNewsNotExistsInList(String newsTitle) {
        Allure.step("Проверка отсутствия новости в списке: " + newsTitle);
        WaitHelper.waitForView(R.id.news_list_recycler_view, DEFAULT_TIMEOUT);
        WaitHelper.waitForViewWithoutText(
                R.id.news_list_recycler_view, newsTitle, EXTENDED_TIMEOUT);
    }
}
