package ru.edu.qamid.uiTests;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.Story;
import ru.edu.qamid.pageObjects.AuthPage;
import ru.edu.qamid.pageObjects.MainPage;
import ru.edu.qamid.pageObjects.NewsPage;
import ru.edu.qamid.ui.AppActivity;
import ru.edu.qamid.listeners.AllureScreenshotRule;

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

        try {
            mainPage.waitForMainScreen();
            mainPage.logout();
        } catch (Throwable ignored) {
        }

        authPage.waitForAuthPageLoaded();
        authPage.enterLogin("login2");
        authPage.enterPassword("password2");
        authPage.clickLogin();
        mainPage.waitForMainScreen();
    }

    @Test
    @Story("Отображение списка новостей")
    @Description("TC-009: Проверка отображения списка новостей на главной странице")
    public void testNewsListIsDisplayed_TC009() {
        newsPage.waitForNewsScreenLoaded();
        newsPage.checkNewsListIsDisplayed();
    }

    @Test
    @Story("Разворачивание и сворачивание списка новостей")
    @Description("TC-010: Разворачивание и сворачивание списка новостей")
    public void testExpandAndCollapseNewsList_TC010() {
        newsPage.waitForNewsScreenLoaded();
        newsPage.expandNewsList();
        newsPage.collapseNewsList();

        newsPage.checkNewsListIsDisplayed();
    }

    @Test
    @Story("Обновление списка новостей")
    @Description("TC-012: Обновление списка новостей Pull-to-Refresh на главной странице")
    public void testPullToRefreshOnMain_TC012() {
        newsPage.waitForNewsScreenLoaded();
        newsPage.pullToRefreshOnMainScreen();

        newsPage.checkNewsListIsDisplayed();
    }

    @Test
    @Story("Навигация через меню")
    @Description("TC-013: Переход в раздел Новости из меню")
    public void testNavigateToNews_TC013() {
        newsPage.waitForNewsScreenLoaded();
        newsPage.openMainMenu();
        newsPage.navigateToNews();

        newsPage.checkNewsListIsDisplayed();
    }

    @Test
    @Story("Обновление списка новостей")
    @Description("TC-039: Многократный Pull-to-Refresh на главной странице")
    public void testMultiplePullToRefresh_TC039() {
        newsPage.waitForNewsScreenLoaded();

        for (int i = 0; i < 5; i++) {
            newsPage.pullToRefreshOnMainScreen();
        }

        newsPage.checkNewsListIsDisplayed();
    }

    @Test
    @Story("Навигация через меню")
    @Description("TC-045: Переход в раздел Цитаты из главного меню")
    public void testNavigateToQuotes_TC045() {
        newsPage.waitForNewsScreenLoaded();
        newsPage.openQuotesSection();

        newsPage.checkOurMissionScreenIsDisplayed();
    }
}