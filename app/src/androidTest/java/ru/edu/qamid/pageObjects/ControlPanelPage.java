package ru.edu.qamid.pageObjects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParentIndex;
import static org.hamcrest.Matchers.allOf;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

import io.qameta.allure.kotlin.Allure;
import ru.edu.qamid.R;

public class ControlPanelPage {

    private static final long DEFAULT_TIMEOUT = 10_000L;

    private final int addNewsButtonId = R.id.add_news_image_view;
    private final int newsRecyclerViewId = R.id.news_list_recycler_view;
    private final int swipeRefreshId = R.id.news_control_panel_swipe_to_refresh;
    private final int editButtonId = R.id.news_item_edit_image_view;
    private final int deleteButtonId = R.id.news_item_delete_image_view;

    public void clickAddNewsButton() {
        Allure.step("Нажатие на кнопку добавления новости");
        onView(withId(addNewsButtonId))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void checkIsControlPanelDisplayed() {
        Allure.step("Проверка отображения панели управления новостями");
        onView(isRoot()).perform(waitForView(newsRecyclerViewId, DEFAULT_TIMEOUT));
        onView(withId(newsRecyclerViewId))
                .check(matches(isDisplayed()));
    }

    public void clickEditNewsButton() {
        Allure.step("Нажатие на кнопку редактирования первой новости");
        onView(isRoot()).perform(waitForView(editButtonId, DEFAULT_TIMEOUT));
        onView(allOf(
                withId(editButtonId),
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
        onView(isRoot()).perform(waitForView(deleteButtonId, DEFAULT_TIMEOUT));
        onView(allOf(
                withId(deleteButtonId),
                isDescendantOfA(allOf(
                        withId(R.id.news_item_material_card_view),
                        withParentIndex(0)
                ))
        ))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    // ==================== Диалоги подтверждения (по ID) ====================

    public void confirmDelete() {
        Allure.step("Подтверждение удаления новости");
        onView(withId(android.R.id.button1))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void cancelDelete() {
        Allure.step("Отмена удаления новости");
        onView(withId(android.R.id.button2))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void confirmExitWithoutSaving() {
        Allure.step("Подтверждение выхода без сохранения");
        onView(withId(android.R.id.button1))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void cancelExitWithoutSaving() {
        Allure.step("Отмена выхода без сохранения");
        onView(withId(android.R.id.button2))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void pullToRefresh() {
        Allure.step("Pull-to-Refresh в панели управления");
        onView(withId(swipeRefreshId))
                .check(matches(isDisplayed()))
                .perform(swipeDown());
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
