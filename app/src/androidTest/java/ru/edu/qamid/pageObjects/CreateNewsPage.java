package ru.edu.qamid.pageObjects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.PerformException;

import io.qameta.allure.kotlin.Allure;
import ru.edu.qamid.R;

public class CreateNewsPage {

    private static final int MAX_RETRIES = 12;
    private static final long RETRY_DELAY = 350;

    public void enterCategory(String category) {
        Allure.step("Ввод категории: " + category);
        waitForViewAndPerform(R.id.news_category_auto_complete, replaceText(category));
    }

    public void enterTitle(String title) {
        Allure.step("Ввод заголовка новости: " + title);
        waitForViewAndPerform(R.id.news_title_edit_text, replaceText(title));
    }

    public void enterDate(String date) {
        Allure.step("Ввод даты публикации: " + date);
        waitForViewAndPerform(R.id.news_publish_date_edit_text, replaceText(date));
    }

    public void enterTime(String time) {
        Allure.step("Ввод времени публикации: " + time);
        waitForViewAndPerform(R.id.news_publish_time_edit_text, replaceText(time));
    }

    public void enterDescription(String description) {
        Allure.step("Ввод описания новости: " + description);
        waitForViewAndPerform(R.id.news_description_edit_text, replaceText(description));
    }

    public void clickSaveButton() {
        Allure.step("Нажатие кнопки Сохранить");
        waitForViewAndClick(R.id.news_save_button);
    }

    public boolean tryClickSaveButton() {
        Allure.step("Попытка нажатия кнопки Сохранить (без падения)");
        return tryWaitForViewAndClick(R.id.news_save_button);
    }

    public void clickCancelButton() {
        Allure.step("Нажатие кнопки Отмена");
        waitForViewAndClick(R.id.news_cancel_button);
    }

    public void checkCreateNewsScreenIsDisplayed() {
        Allure.step("Проверка, что экран создания/редактирования новости отображается");
        for (int i = 0; i < MAX_RETRIES; i++) {
            try {
                onView(withId(R.id.news_title_edit_text))
                        .check(matches(isDisplayed()));
                return;
            } catch (NoMatchingViewException | PerformException e) {
                sleep(RETRY_DELAY);
            }
        }
        throw new AssertionError("Экран создания новости не отобразился");
    }

    // ==================== Вспомогательные методы ====================

    private void waitForViewAndPerform(int viewId, androidx.test.espresso.ViewAction action) {
        for (int i = 0; i < MAX_RETRIES; i++) {
            try {
                onView(withId(viewId))
                        .check(matches(isDisplayed()))
                        .perform(action);
                return;
            } catch (NoMatchingViewException | PerformException e) {
                sleep(RETRY_DELAY);
            }
        }
        throw new AssertionError("Не удалось выполнить действие на view с id: " + viewId);
    }

    private void waitForViewAndClick(int viewId) {
        for (int i = 0; i < MAX_RETRIES; i++) {
            try {
                onView(withId(viewId))
                        .check(matches(isDisplayed()))
                        .perform(click());
                return;
            } catch (NoMatchingViewException | PerformException e) {
                sleep(RETRY_DELAY);
            }
        }
        throw new AssertionError("Не удалось кликнуть по кнопке с id: " + viewId);
    }

    private boolean tryWaitForViewAndClick(int viewId) {
        for (int i = 0; i < MAX_RETRIES; i++) {
            try {
                onView(withId(viewId))
                        .check(matches(isDisplayed()))
                        .perform(click());
                return true;
            } catch (NoMatchingViewException | PerformException e) {
                sleep(RETRY_DELAY);
            }
        }
        return false; // не падаем, если кнопка уже исчезла
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}
