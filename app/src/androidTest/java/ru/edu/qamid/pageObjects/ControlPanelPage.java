package ru.edu.qamid.pageObjects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParentIndex;
import static org.hamcrest.Matchers.allOf;

import io.qameta.allure.kotlin.Allure;
import ru.edu.qamid.R;
import ru.edu.qamid.utils.WaitHelper;

public class ControlPanelPage {

    private static final long DEFAULT_TIMEOUT = 15_000L;

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
        WaitHelper.waitForView(R.id.news_item_edit_image_view, DEFAULT_TIMEOUT);
        onView(allOf(
                withId(R.id.news_item_edit_image_view),
                isDescendantOfA(allOf(
                        withId(R.id.news_item_material_card_view),
                        withParentIndex(0)
                ))
        ))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void clickDeleteNewsButton() {
        Allure.step("Нажатие на кнопку удаления первой новости");
        WaitHelper.waitForView(R.id.news_item_delete_image_view, DEFAULT_TIMEOUT);
        onView(allOf(
                withId(R.id.news_item_delete_image_view),
                isDescendantOfA(allOf(
                        withId(R.id.news_item_material_card_view),
                        withParentIndex(0)
                ))
        ))
                .check(matches(isDisplayed()))
                .perform(click());
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
        onView(withId(R.id.news_control_panel_swipe_to_refresh))
                .check(matches(isDisplayed()))
                .perform(swipeDown());
    }
}
