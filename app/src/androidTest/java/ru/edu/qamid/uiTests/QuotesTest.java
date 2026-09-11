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
import ru.edu.qamid.pageObjects.QuotesPage;
import ru.edu.qamid.ui.AppActivity;
import ru.edu.qamid.listeners.AllureScreenshotRule;

@Epic("Цитаты")
@Feature("Работа с разделом Цитаты")
@RunWith(AndroidJUnit4.class)
public class QuotesTest {

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);
    @Rule
    public AllureScreenshotRule screenshotRule = new AllureScreenshotRule();

    private AuthPage authPage;
    private MainPage mainPage;
    private NewsPage newsPage;
    private QuotesPage quotesPage;

    @Before
    public void setUp() {
        authPage = new AuthPage();
        mainPage = new MainPage();
        newsPage = new NewsPage();
        quotesPage = new QuotesPage();

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
    @Story("Навигация через меню")
    @Description("TC-046: Развернуть цитату")
    public void testExpandQuote_TC046() {
        newsPage.waitForNewsScreenLoaded();
        newsPage.openQuotesSection();

        quotesPage.expandFirstQuote();
        quotesPage.checkFirstQuoteIsExpanded();
    }

    @Test
    @Story("Навигация через меню")
    @Description("TC-047: Свернуть цитату")
    public void testCollapseQuote_TC047() {
        newsPage.waitForNewsScreenLoaded();
        newsPage.openQuotesSection();

        quotesPage.expandFirstQuote();
        quotesPage.collapseFirstQuote();
        quotesPage.checkFirstQuoteIsCollapsed();
    }
}
