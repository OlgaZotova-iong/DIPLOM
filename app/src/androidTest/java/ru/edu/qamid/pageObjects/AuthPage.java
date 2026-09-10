package ru.edu.qamid.pageObjects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import io.qameta.allure.kotlin.Allure;
import ru.edu.qamid.R;
import ru.edu.qamid.utils.WaitHelper;

public class AuthPage {

    private static final long DEFAULT_TIMEOUT = 20_000L;

    private final int loginInputId = R.id.login_edit_text;
    private final int passwordInputId = R.id.password_edit_text;
    private final int enterButtonId = R.id.enter_button;

    public void waitForAuthPageLoaded() {
        Allure.step("Ожидание загрузки экрана авторизации");
        WaitHelper.waitForView(loginInputId, DEFAULT_TIMEOUT);
    }

    public void enterLogin(String login) {
        Allure.step("Ввод логина: " + login);
        onView(withId(loginInputId))
                .check(matches(isDisplayed()))
                .perform(replaceText(login));
    }

    public void enterPassword(String password) {
        Allure.step("Ввод пароля");
        onView(withId(passwordInputId))
                .check(matches(isDisplayed()))
                .perform(replaceText(password));
    }

    public void clickLogin() {
        Allure.step("Нажатие кнопки Войти");
        onView(withId(enterButtonId))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()))
                .perform(click());
    }

    public void checkWrongCredentialsErrorMessage() {
        Allure.step("Проверка сообщения об ошибке при неверных данных");
        onView(withText("Что-то пошло не так. Попробуйте позднее."))
                .inRoot(ru.edu.qamid.utils.ToastMatcher.isToast())
                .check(matches(isDisplayed()));
    }

    public void checkEmptyFieldsErrorMessage() {
        Allure.step("Проверка сообщения об ошибке при пустых полях");
        onView(withText("Логин и пароль не могут быть пустыми"))
                .inRoot(ru.edu.qamid.utils.ToastMatcher.isToast())
                .check(matches(isDisplayed()));
    }
}