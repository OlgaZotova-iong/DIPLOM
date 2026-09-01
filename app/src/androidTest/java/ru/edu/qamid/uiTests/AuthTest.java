package ru.edu.qamid.uiTests;

import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Story;
import ru.edu.qamid.pageObjects.AuthPage;
import ru.edu.qamid.pageObjects.MainPage;
import ru.edu.qamid.ui.AppActivity;

@Epic("Авторизация")
@Feature("Авторизация пользователя")
@RunWith(AndroidJUnit4.class)
public class AuthTest {

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule = new ActivityScenarioRule<>(AppActivity.class);

    private AuthPage authPage;
    private MainPage mainPage;

    @Before
    public void setUp() {
        authPage = new AuthPage();
        mainPage = new MainPage();

        try {
            mainPage.waitForMainScreen();
            mainPage.logout();
        } catch (Throwable ignored) {
        }
        authPage.waitForAuthPageLoaded();
    }

    @Test
    @Story("Позитивный сценарий авторизации")
    @Description("Пользователь вводит корректный логин и пароль и успешно входит в приложение")
    public void testPositiveLogin() {
        Allure.step("Ввод логина и пароля");
        authPage.enterLogin("login2");
        authPage.enterPassword("password2");

        Allure.step("Нажатие кнопки Войти");
        authPage.clickLogin();

        mainPage.waitForMainScreen();
        assertTrue(mainPage.checkIsOnNewsScreen());
    }

    @Test
    @Story("Негативный сценарий авторизации")
    @Description("При вводе неверного логина и пароля появляется сообщение об ошибке (тест может падать из-за короткого времени жизни Toast)")
    public void testNegativeLoginWrongCredentials() {
        Allure.step("Ввод неверных данных");
        authPage.enterLogin("wrong");
        authPage.enterPassword("wrong");

        Allure.step("Нажатие кнопки Войти");
        authPage.clickLogin();

        Allure.step("Проверка сообщения об ошибке");
        authPage.checkWrongCredentialsErrorMessage();
    }

    @Test
    @Story("Негативный сценарий авторизации")
    @Description("При пустых полях появляется сообщение об ошибке (тест может падать из-за короткого времени жизни Toast)")
    public void testNegativeLoginEmptyFields() {
        Allure.step("Ввод пустых значений");
        authPage.enterLogin("");
        authPage.enterPassword("");

        Allure.step("Нажатие кнопки Войти");
        authPage.clickLogin();

        Allure.step("Проверка сообщения об ошибке");
        authPage.checkEmptyFieldsErrorMessage();
    }

    @Test
    @Story("Выход из приложения")
    @Description("После успешного входа пользователь может выйти из аккаунта")
    public void testLogout() {
        Allure.step("Авторизация пользователя");
        authPage.enterLogin("login2");
        authPage.enterPassword("password2");
        authPage.clickLogin();

        mainPage.waitForMainScreen();
        mainPage.logout();
        authPage.waitForAuthPageLoaded();
    }

    @Test
    @Story("Выход и повторный вход")
    @Description("Повторная авторизация после выхода без перезапуска приложения")
    public void testLogoutAndReLoginWithoutRestart() {
        Allure.step("Первая авторизация");
        authPage.enterLogin("login2");
        authPage.enterPassword("password2");
        authPage.clickLogin();
        mainPage.waitForMainScreen();

        Allure.step("Выход из аккаунта");
        mainPage.logout();
        authPage.waitForAuthPageLoaded();

        Allure.step("Повторная авторизация");
        authPage.enterLogin("login2");
        authPage.enterPassword("password2");
        authPage.clickLogin();
        mainPage.waitForMainScreen();
        assertTrue(mainPage.checkIsOnNewsScreen());
    }
}