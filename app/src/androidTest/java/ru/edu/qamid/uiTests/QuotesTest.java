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
import ru.edu.qamid.pageObjects.QuotesPage;
import ru.edu.qamid.ui.AppActivity;

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
        } catch (Throwable ignored) {
            authPage.waitForAuthPageLoaded();
            authPage.enterLogin(TestData.VALID_LOGIN);
            authPage.enterPassword(TestData.VALID_PASSWORD);
            authPage.clickLogin();
            mainPage.waitForMainScreen();
        }


        Allure.step("Переход в раздел Цитаты");
        newsPage.waitForNewsScreenLoaded();
        newsPage.openQuotesSection();
        quotesPage.waitForQuotesScreenLoaded();
    }

    @Test
    @Story("Разворачивание цитаты")
    @Description("TC-046: Развернуть первую цитату")
    public void testExpandQuote_TC046() {
        Allure.step("Разворачивание первой цитаты");
        quotesPage.expandFirstQuote();

        Allure.step("Проверка что цитата развёрнута");
        quotesPage.checkFirstQuoteIsExpanded();
    }

    @Test
    @Story("Сворачивание цитаты")
    @Description("TC-047: Свернуть первую цитату")
    public void testCollapseQuote_TC047() {
        Allure.step("Разворачивание первой цитаты");
        quotesPage.expandFirstQuote();

        Allure.step("Сворачивание первой цитаты");
        quotesPage.collapseFirstQuote();

        Allure.step("Проверка что цитата свёрнута");
        quotesPage.checkFirstQuoteIsCollapsed();
    }
}
