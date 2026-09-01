package ru.edu.qamid.pageObjects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

import io.qameta.allure.kotlin.Allure;
import ru.edu.qamid.R;
import ru.edu.qamid.utils.ToastMatcher;

public class AuthPage {

    private static final long AUTH_PAGE_TIMEOUT = 20000L;

    private final int loginInputId = R.id.login_edit_text;
    private final int passwordInputId = R.id.password_edit_text;
    private final int enterButtonId = R.id.enter_button;

    public void waitForAuthPageLoaded() {
        Allure.step("Ожидание загрузки экрана авторизации");
        onView(isRoot()).perform(waitForViews(AUTH_PAGE_TIMEOUT, loginInputId, passwordInputId, enterButtonId));
    }

    public void enterLogin(String login) {
        Allure.step("Ввод логина: " + login);
        onView(withId(loginInputId)).perform(replaceText(login));
    }

    public void enterPassword(String password) {
        Allure.step("Ввод пароля");
        onView(withId(passwordInputId)).perform(replaceText(password));
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
                .inRoot(ToastMatcher.isToast())
                .check(matches(isDisplayed()));
    }

    public void checkEmptyFieldsErrorMessage() {
        Allure.step("Проверка сообщения об ошибке при пустых полях");
        onView(withText("Логин и пароль не могут быть пустыми"))
                .inRoot(ToastMatcher.isToast())
                .check(matches(isDisplayed()));
    }

    private static ViewAction waitForViews(final long timeout, final int... viewIds) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Ожидание экрана авторизации";
            }

            @Override
            public void perform(UiController uiController, View root) {
                long end = System.currentTimeMillis() + timeout;
                while (System.currentTimeMillis() < end) {
                    boolean allVisible = true;
                    for (int id : viewIds) {
                        boolean found = false;
                        for (View v : androidx.test.espresso.util.TreeIterables.breadthFirstViewTraversal(root)) {
                            if (v.getId() == id) {
                                if (v.isShown()) {
                                    found = true;
                                    break;
                                }
                            }
                        }
                        if (!found) {
                            allVisible = false;
                            break;
                        }
                    }
                    if (allVisible) return;
                    uiController.loopMainThreadForAtLeast(200);
                }
                throw new AssertionError("Экран авторизации не загрузился");
            }
        };
    }
}