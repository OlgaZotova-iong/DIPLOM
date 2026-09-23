package ru.edu.qamid.uiTests;

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
import ru.edu.qamid.data.TestData;
import ru.edu.qamid.listeners.AllureScreenshotRule;
import ru.edu.qamid.pageObjects.AuthPage;
import ru.edu.qamid.pageObjects.MainPage;
import ru.edu.qamid.ui.AppActivity;

@Epic("Авторизация")
@Feature("Авторизация пользователя")
@RunWith(AndroidJUnit4.class)
public class AuthTest {

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);
    @Rule
    public AllureScreenshotRule screenshotRule = new AllureScreenshotRule();

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
    @Story("Позитивный сценарий")
    @Description("TC-001: Успешный вход с валидными данными")
    public void testPositiveLogin_TC001() {
        Allure.step("Ввод логина");
        authPage.enterLogin(TestData.VALID_LOGIN);

        Allure.step("Ввод пароля");
        authPage.enterPassword(TestData.VALID_PASSWORD);

        Allure.step("Нажатие кнопки Войти");
        authPage.clickLogin();

        Allure.step("Проверка что открылся главный экран");
        mainPage.waitForMainScreen();
        mainPage.checkIsOnNewsScreen();
    }

    @Test
    @Story("Негативный сценарий")
    @Description("TC-003: Вход с неверным логином и паролем")
    public void testNegativeLoginWrongCredentials_TC003() {
        Allure.step("Ввод неверного логина");
        authPage.enterLogin(TestData.WRONG_LOGIN);

        Allure.step("Ввод неверного пароля");
        authPage.enterPassword(TestData.WRONG_PASSWORD);

        Allure.step("Нажатие кнопки Войти");
        authPage.clickLogin();

        Allure.step("Проверка сообщения об ошибке");
        authPage.checkWrongCredentialsErrorMessage();
    }

    @Test
    @Story("Негативный сценарий")
    @Description("TC-004: Вход с пустыми полями")
    public void testNegativeLoginEmptyFields_TC004() {
        Allure.step("Оставляем поля пустыми");
        authPage.enterLogin("");
        authPage.enterPassword("");

        Allure.step("Нажатие кнопки Войти");
        authPage.clickLogin();

        Allure.step("Проверка сообщения об ошибке");
        authPage.checkEmptyFieldsErrorMessage();
    }

    @Test
    @Story("Выход из приложения")
    @Description("TC-007: Выход из личного кабинета через меню")
    public void testLogout_TC007() {
        Allure.step("Авторизация пользователя");
        authPage.enterLogin(TestData.VALID_LOGIN);
        authPage.enterPassword(TestData.VALID_PASSWORD);
        authPage.clickLogin();
        mainPage.waitForMainScreen();

        Allure.step("Выход из аккаунта");
        mainPage.logout();

        Allure.step("Проверка что открылся экран авторизации");
        authPage.waitForAuthPageLoaded();
    }

    @Test
    @Story("Выход и повторный вход")
    @Description("TC-008: Выход и повторный вход без перезапуска приложения")
    public void testLogoutAndReLogin_TC008() {
        Allure.step("Первая авторизация");
        authPage.enterLogin(TestData.VALID_LOGIN);
        authPage.enterPassword(TestData.VALID_PASSWORD);
        authPage.clickLogin();
        mainPage.waitForMainScreen();

        Allure.step("Выход из аккаунта");
        mainPage.logout();
        authPage.waitForAuthPageLoaded();

        Allure.step("Повторная авторизация");
        authPage.enterLogin(TestData.VALID_LOGIN);
        authPage.enterPassword(TestData.VALID_PASSWORD);
        authPage.clickLogin();

        Allure.step("Проверка что открылся главный экран");
        mainPage.waitForMainScreen();
        mainPage.checkIsOnNewsScreen();
    }
}