package ru.edu.qamid.pageObjects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import io.qameta.allure.kotlin.Allure;
import ru.edu.qamid.R;
import ru.edu.qamid.utils.WaitHelper;

public class MainPage {

    private static final long MAIN_SCREEN_TIMEOUT = 20_000L;

    public void waitForMainScreen() {
        Allure.step("Ожидание открытия главного экрана");
        WaitHelper.waitForView(R.id.main_news_list_container, MAIN_SCREEN_TIMEOUT);
    }

    public void checkIsOnNewsScreen() {
        Allure.step("Проверка, что открыт главный экран (Новости)");
        onView(withId(R.id.main_news_list_container))
                .check(matches(isDisplayed()));
    }

    public void logout() {
        Allure.step("Выход из личного кабинета через меню");
        onView(withId(R.id.authorization_image_button))
                .perform(click());

        onView(withText(R.string.log_out))
                .check(matches(isDisplayed()))
                .perform(click());
    }
}

