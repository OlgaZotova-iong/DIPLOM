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
import ru.edu.qamid.pageObjects.ControlPanelPage;
import ru.edu.qamid.pageObjects.CreateNewsPage;
import ru.edu.qamid.pageObjects.MainPage;
import ru.edu.qamid.pageObjects.NewsPage;
import ru.edu.qamid.ui.AppActivity;

@Epic("Управление новостями")
@Feature("CRUD операций с новостями")
@RunWith(AndroidJUnit4.class)
public class NewsManagementTest {

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    private AuthPage authPage;
    private MainPage mainPage;
    private NewsPage newsPage;
    private ControlPanelPage controlPanelPage;
    private CreateNewsPage createNewsPage;

    @Before
    public void setUp() {
        authPage = new AuthPage();
        mainPage = new MainPage();
        newsPage = new NewsPage();
        controlPanelPage = new ControlPanelPage();
        createNewsPage = new CreateNewsPage();

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

        newsPage.openMainMenu();
        newsPage.navigateToNews();
        newsPage.waitForNewsManagementScreenLoaded();
    }

    @Test
    @Story("Создание новости")
    @Description("TC-021: Создание новости с валидными данными")
    public void shouldCreateNewsWithValidData() {
        newsPage.openNewsControlPanel();
        controlPanelPage.clickAddNewsButton();

        createNewsPage.enterCategory("Объявление");
        createNewsPage.enterTitle("Тестовая новость");
        createNewsPage.enterDate("01.01.2025");
        createNewsPage.enterTime("12:00");
        createNewsPage.enterDescription("Описание тестовой новости");
        createNewsPage.clickSaveButton();

        controlPanelPage.checkIsControlPanelDisplayed();
    }

    @Test
    @Story("Создание новости")
    @Description("TC-023: Создание новости без выбора категории")
    public void shouldNotCreateNewsWithoutCategory() {
        newsPage.openNewsControlPanel();
        controlPanelPage.clickAddNewsButton();

        createNewsPage.enterTitle("Новость без категории");
        createNewsPage.enterDate("02.02.2025");
        createNewsPage.enterTime("13:00");
        createNewsPage.enterDescription("Описание");
        createNewsPage.clickSaveButton();

        createNewsPage.checkCreateNewsScreenIsDisplayed();
    }

    @Test
    @Story("Создание новости")
    @Description("TC-024: Создание новости с пустым заголовком")
    public void shouldNotCreateNewsWithoutTitle() {
        newsPage.openNewsControlPanel();
        controlPanelPage.clickAddNewsButton();

        createNewsPage.enterCategory("Объявление");
        createNewsPage.enterDate("03.03.2025");
        createNewsPage.enterTime("14:00");
        createNewsPage.enterDescription("Описание без заголовка");
        createNewsPage.clickSaveButton();

        createNewsPage.checkCreateNewsScreenIsDisplayed();
    }

    @Test
    @Story("Создание новости")
    @Description("TC-025: Создание новости с пустым описанием")
    public void shouldNotCreateNewsWithoutDescription() {
        newsPage.openNewsControlPanel();
        controlPanelPage.clickAddNewsButton();

        createNewsPage.enterCategory("Объявление");
        createNewsPage.enterTitle("Новость без описания");
        createNewsPage.enterDate("04.04.2025");
        createNewsPage.enterTime("15:00");
        createNewsPage.clickSaveButton();

        createNewsPage.checkCreateNewsScreenIsDisplayed();
    }

    @Test
    @Story("Редактирование новости")
    @Description("TC-032: Валидация пустого описания при редактировании")
    public void shouldNotSaveNewsWithEmptyDescriptionOnEdit() {
        newsPage.openNewsControlPanel();
        controlPanelPage.clickEditNewsButton();

        createNewsPage.enterDescription("");
        createNewsPage.clickSaveButton();

        createNewsPage.checkCreateNewsScreenIsDisplayed();
    }

    @Test
    @Story("Редактирование новости")
    @Description("TC-030: Редактирование заголовка существующей новости")
    public void shouldEditNewsTitle() {
        newsPage.openNewsControlPanel();
        controlPanelPage.clickEditNewsButton();

        createNewsPage.enterTitle("Обновлённый заголовок");
        createNewsPage.clickSaveButton();

        controlPanelPage.checkIsControlPanelDisplayed();
    }

    @Test
    @Story("Удаление новости")
    @Description("TC-031: Удаление новости с подтверждением")
    public void shouldDeleteNews() {
        newsPage.openNewsControlPanel();
        controlPanelPage.clickDeleteNewsButton();
        controlPanelPage.confirmDelete();

        controlPanelPage.checkIsControlPanelDisplayed();
    }

    @Test
    @Story("Обновление списка")
    @Description("TC-033: Обновление списка новостей (Pull-to-Refresh)")
    public void shouldRefreshNewsList() {
        newsPage.openNewsControlPanel();
        controlPanelPage.pullToRefresh();

        controlPanelPage.checkIsControlPanelDisplayed();
    }

    @Test
    @Story("Обновление списка")
    @Description("TC-039: Многократный Pull-to-Refresh")
    public void shouldHandleMultiplePullToRefresh() {
        newsPage.openNewsControlPanel();

        for (int i = 0; i < 6; i++) {
            controlPanelPage.pullToRefresh();
        }

        controlPanelPage.checkIsControlPanelDisplayed();
    }

    @Test
    @Story("Защита от дублирования")
    @Description("TC-040: Быстрый двойной клик по кнопке Сохранить")
    public void shouldHandleDoubleClickOnSaveButton() {
        newsPage.openNewsControlPanel();
        controlPanelPage.clickAddNewsButton();

        createNewsPage.enterCategory("Объявление");
        createNewsPage.enterTitle("Двойной клик");
        createNewsPage.enterDate("05.05.2025");
        createNewsPage.enterTime("10:00");
        createNewsPage.enterDescription("Тест двойного клика");

        createNewsPage.clickSaveButton();
        createNewsPage.tryClickSaveButton();

        controlPanelPage.checkIsControlPanelDisplayed();
    }

    @Test
    @Story("Защита от дублирования")
    @Description("TC-041: Быстрый двойной клик по кнопке Удалить")
    public void shouldHandleDoubleClickOnDeleteButton() {
        newsPage.openNewsControlPanel();
        controlPanelPage.clickDeleteNewsButton();
        controlPanelPage.confirmDelete();
        controlPanelPage.clickDeleteNewsButton();
        controlPanelPage.confirmDelete();

        controlPanelPage.checkIsControlPanelDisplayed();
    }

    @Test
    @Story("Отмена создания")
    @Description("TC-042: Возврат назад из формы создания новости без сохранения")
    public void shouldCancelNewsCreationAndReturn() {
        newsPage.openNewsControlPanel();
        controlPanelPage.clickAddNewsButton();

        createNewsPage.enterCategory("Объявление");
        createNewsPage.enterTitle("Новость без сохранения");
        createNewsPage.enterDate("06.06.2025");
        createNewsPage.enterTime("11:00");
        createNewsPage.enterDescription("Эта новость не сохранится");

        createNewsPage.clickCancelButton();
        controlPanelPage.confirmExitWithoutSaving();
        controlPanelPage.checkIsControlPanelDisplayed();
    }
}
