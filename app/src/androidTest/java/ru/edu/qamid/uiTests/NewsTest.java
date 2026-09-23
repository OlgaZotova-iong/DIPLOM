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
import ru.edu.qamid.pageObjects.NewsPage;
import ru.edu.qamid.ui.AppActivity;

@Epic("Новости")
@Feature("Работа со списком новостей")
@RunWith(AndroidJUnit4.class)
public class NewsTest {

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);
    @Rule
    public AllureScreenshotRule screenshotRule = new AllureScreenshotRule();

    private AuthPage authPage;
    private MainPage mainPage;
    private NewsPage newsPage;

    @Before
    public void setUp() {
        authPage = new AuthPage();
        mainPage = new MainPage();
        newsPage = new NewsPage();

        // Проверяем состояние: если не авторизован — логинимся
        try {
            mainPage.waitForMainScreen();
        } catch (Throwable ignored) {
            authPage.waitForAuthPageLoaded();
            authPage.enterLogin(TestData.VALID_LOGIN);
            authPage.enterPassword(TestData.VALID_PASSWORD);
            authPage.clickLogin();
            mainPage.waitForMainScreen();
        }
    }

    @Test
    @Story("Отображение списка новостей")
    @Description("TC-009: Проверка отображения списка новостей на главной странице")
    public void testNewsListIsDisplayed_TC009() {
        Allure.step("Ожидание загрузки экрана новостей");
        newsPage.waitForNewsScreenLoaded();

        Allure.step("Проверка отображения списка новостей");
        newsPage.checkNewsListIsDisplayed();
    }

    @Test
    @Story("Разворачивание и сворачивание")
    @Description("TC-010: Разворачивание и сворачивание списка новостей")
    public void testExpandAndCollapseNewsList_TC010() {
        Allure.step("Ожидание загрузки экрана новостей");
        newsPage.waitForNewsScreenLoaded();

        Allure.step("Разворачивание списка новостей");
        newsPage.expandNewsList();

        Allure.step("Сворачивание списка новостей");
        newsPage.collapseNewsList();

        Allure.step("Проверка что список отображается");
        newsPage.checkNewsListIsDisplayed();
    }

    @Test
    @Story("Обновление списка")
    @Description("TC-012: Обновление списка новостей Pull-to-Refresh")
    public void testPullToRefresh_TC012() {
        Allure.step("Ожидание загрузки экрана новостей");
        newsPage.waitForNewsScreenLoaded();

        Allure.step("Выполнение Pull-to-Refresh");
        newsPage.pullToRefreshOnMainScreen();

        Allure.step("Проверка что список новостей отображается после обновления");
        newsPage.checkNewsListIsDisplayed();
    }

    @Test
    @Story("Навигация через меню")
    @Description("TC-013: Переход в раздел Новости из меню")
    public void testNavigateToNews_TC013() {
        Allure.step("Ожидание загрузки экрана новостей");
        newsPage.waitForNewsScreenLoaded();

        Allure.step("Открытие главного меню");
        newsPage.openMainMenu();

        Allure.step("Переход в раздел Новости");
        newsPage.navigateToNews();

        Allure.step("Проверка отображения списка новостей");
        newsPage.checkNewsListIsDisplayed();
    }

    @Test
    @Story("Обновление списка")
    @Description("TC-039: Многократный Pull-to-Refresh на главной странице")
    public void testMultiplePullToRefresh_TC039() {
        Allure.step("Ожидание загрузки экрана новостей");
        newsPage.waitForNewsScreenLoaded();

        Allure.step("Выполнение 5 Pull-to-Refresh");
        for (int i = 0; i < 5; i++) {
            newsPage.pullToRefreshOnMainScreen();
        }

        Allure.step("Проверка что список новостей отображается");
        newsPage.checkNewsListIsDisplayed();
    }

    @Test
    @Story("Навигация через меню")
    @Description("TC-045: Переход в раздел Цитаты из главного меню")
    public void testNavigateToQuotes_TC045() {
        Allure.step("Ожидание загрузки экрана новостей");
        newsPage.waitForNewsScreenLoaded();

        Allure.step("Переход в раздел Цитаты");
        newsPage.openQuotesSection();

        Allure.step("Проверка отображения экрана Цитаты");
        newsPage.checkOurMissionScreenIsDisplayed();
    }
}