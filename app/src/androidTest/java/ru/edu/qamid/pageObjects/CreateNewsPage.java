package ru.edu.qamid.pageObjects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import io.qameta.allure.kotlin.Allure;
import ru.edu.qamid.R;
import ru.edu.qamid.utils.WaitHelper;

public class CreateNewsPage {

    private static final long DEFAULT_TIMEOUT = 15_000L;

    public void enterCategory(String category) {
        Allure.step("Ввод категории: " + category);
        WaitHelper.waitForView(R.id.news_category_auto_complete, DEFAULT_TIMEOUT);
        onView(withId(R.id.news_category_auto_complete))
                .check(matches(isDisplayed()))
                .perform(replaceText(category));
    }

    public void enterTitle(String title) {
        Allure.step("Ввод заголовка новости: " + title);
        WaitHelper.waitForView(R.id.news_title_edit_text, DEFAULT_TIMEOUT);
        onView(withId(R.id.news_title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(title));
    }

    public void enterDate(String date) {
        Allure.step("Ввод даты публикации: " + date);
        WaitHelper.waitForView(R.id.news_publish_date_edit_text, DEFAULT_TIMEOUT);
        onView(withId(R.id.news_publish_date_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(date));
    }

    public void enterTime(String time) {
        Allure.step("Ввод времени публикации: " + time);
        WaitHelper.waitForView(R.id.news_publish_time_edit_text, DEFAULT_TIMEOUT);
        onView(withId(R.id.news_publish_time_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(time));
    }

    public void enterDescription(String description) {
        Allure.step("Ввод описания новости: " + description);
        WaitHelper.waitForView(R.id.news_description_edit_text, DEFAULT_TIMEOUT);
        onView(withId(R.id.news_description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(description));
    }

    public void clickSaveButton() {
        Allure.step("Нажатие кнопки Сохранить");
        WaitHelper.waitForView(R.id.news_save_button, DEFAULT_TIMEOUT);
        onView(withId(R.id.news_save_button))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void tryClickSaveButton() {
        Allure.step("Попытка повторного нажатия кнопки Сохранить");
        try {
            WaitHelper.waitForView(R.id.news_save_button, 3000);
            onView(withId(R.id.news_save_button))
                    .check(matches(isDisplayed()))
                    .perform(click());
        } catch (Throwable ignored) {

        }
    }

    public void clickCancelButton() {
        Allure.step("Нажатие кнопки Отмена");
        WaitHelper.waitForView(R.id.news_cancel_button, DEFAULT_TIMEOUT);
        onView(withId(R.id.news_cancel_button))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void checkCreateNewsScreenIsDisplayed() {
        Allure.step("Проверка, что экран создания/редактирования новости отображается");
        WaitHelper.waitForView(R.id.news_title_edit_text, DEFAULT_TIMEOUT);
        onView(withId(R.id.news_title_edit_text))
                .check(matches(isDisplayed()));
    }
}