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
    @Rule
    public AllureScreenshotRule screenshotRule = new AllureScreenshotRule();

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
        } catch (Throwable e) {
            authPage.waitForAuthPageLoaded();
            authPage.enterLogin(TestData.VALID_LOGIN);
            authPage.enterPassword(TestData.VALID_PASSWORD);
            authPage.clickLogin();
            mainPage.waitForMainScreen();
        }

        newsPage.openMainMenu();
        newsPage.navigateToNews();
        newsPage.waitForNewsManagementScreenLoaded();
        newsPage.openNewsControlPanel();
        controlPanelPage.checkIsControlPanelDisplayed();
    }

    @Test
    @Story("Создание новости")
    @Description("TC-021: Создание новости с валидными данными")
    public void shouldCreateNewsWithValidData() {
        Allure.step("Нажатие кнопки добавления новости");
        controlPanelPage.clickAddNewsButton();

        Allure.step("Заполнение формы создания новости");
        createNewsPage.enterCategory(TestData.NEWS_CATEGORY);
        createNewsPage.enterTitle(TestData.NEWS_TITLE);
        createNewsPage.enterDate(TestData.NEWS_DATE);
        createNewsPage.enterTime(TestData.NEWS_TIME);
        createNewsPage.enterDescription(TestData.NEWS_DESCRIPTION);

        Allure.step("Сохранение новости");
        createNewsPage.clickSaveButton();

        Allure.step("Проверка что новость появилась в списке");
        controlPanelPage.checkNewsExistsInList(TestData.NEWS_TITLE);
    }

    @Test
    @Story("Создание новости")
    @Description("TC-023: Создание новости без выбора категории")
    public void shouldNotCreateNewsWithoutCategory() {
        Allure.step("Нажатие кнопки добавления новости");
        controlPanelPage.clickAddNewsButton();

        Allure.step("Заполнение формы без категории");
        createNewsPage.enterTitle(TestData.NEWS_TITLE_NO_CATEGORY);
        createNewsPage.enterDate(TestData.NEWS_DATE_NO_CATEGORY);
        createNewsPage.enterTime(TestData.NEWS_TIME_NO_CATEGORY);
        createNewsPage.enterDescription(TestData.NEWS_DESCRIPTION);

        Allure.step("Нажатие кнопки Сохранить");
        createNewsPage.clickSaveButton();

        Allure.step("Проверка что остались на экране создания новости");
        createNewsPage.checkCreateNewsScreenIsDisplayed();
    }

    @Test
    @Story("Создание новости")
    @Description("TC-024: Создание новости с пустым заголовком")
    public void shouldNotCreateNewsWithoutTitle() {
        Allure.step("Нажатие кнопки добавления новости");
        controlPanelPage.clickAddNewsButton();

        Allure.step("Заполнение формы без заголовка");
        createNewsPage.enterCategory(TestData.NEWS_CATEGORY);
        createNewsPage.enterDate(TestData.NEWS_DATE_NO_TITLE);
        createNewsPage.enterTime(TestData.NEWS_TIME_NO_TITLE);
        createNewsPage.enterDescription(TestData.NEWS_DESCRIPTION_NO_TITLE);

        Allure.step("Нажатие кнопки Сохранить");
        createNewsPage.clickSaveButton();

        Allure.step("Проверка что остались на экране создания новости");
        createNewsPage.checkCreateNewsScreenIsDisplayed();
    }

    @Test
    @Story("Создание новости")
    @Description("TC-025: Создание новости с пустым описанием")
    public void shouldNotCreateNewsWithoutDescription() {
        Allure.step("Нажатие кнопки добавления новости");
        controlPanelPage.clickAddNewsButton();

        Allure.step("Заполнение формы без описания");
        createNewsPage.enterCategory(TestData.NEWS_CATEGORY);
        createNewsPage.enterTitle(TestData.NEWS_TITLE_NO_DESCRIPTION);
        createNewsPage.enterDate(TestData.NEWS_DATE_NO_DESCRIPTION);
        createNewsPage.enterTime(TestData.NEWS_TIME_NO_DESCRIPTION);

        Allure.step("Нажатие кнопки Сохранить");
        createNewsPage.clickSaveButton();

        Allure.step("Проверка что остались на экране создания новости");
        createNewsPage.checkCreateNewsScreenIsDisplayed();
    }

    @Test
    @Story("Редактирование новости")
    @Description("TC-030: Редактирование заголовка существующей новости")
    public void shouldEditNewsTitle() {
        Allure.step("Нажатие кнопки редактирования первой новости");
        controlPanelPage.clickEditNewsButton();

        Allure.step("Изменение заголовка новости");
        createNewsPage.enterTitle(TestData.NEWS_UPDATED_TITLE);

        Allure.step("Сохранение изменений");
        createNewsPage.clickSaveButton();

        Allure.step("Проверка что новость с новым заголовком появилась в списке");
        controlPanelPage.checkNewsExistsInList(TestData.NEWS_UPDATED_TITLE);
    }

    @Test
    @Story("Редактирование новости")
    @Description("TC-032: Валидация пустого описания при редактировании")
    public void shouldNotSaveNewsWithEmptyDescriptionOnEdit() {
        Allure.step("Нажатие кнопки редактирования первой новости");
        controlPanelPage.clickEditNewsButton();

        Allure.step("Очистка поля описания");
        createNewsPage.enterDescription("");

        Allure.step("Нажатие кнопки Сохранить");
        createNewsPage.clickSaveButton();

        Allure.step("Проверка что остались на экране редактирования");
        createNewsPage.checkCreateNewsScreenIsDisplayed();
    }

    @Test
    @Story("Удаление новости")
    @Description("TC-031: Удаление новости с подтверждением")
    public void shouldDeleteNews() {
        Allure.step("Запоминание заголовка первой новости");
        String titleToDelete = controlPanelPage.getFirstNewsTitle();

        Allure.step("Нажатие кнопки удаления первой новости");
        controlPanelPage.clickDeleteNewsButton();

        Allure.step("Подтверждение удаления");
        controlPanelPage.confirmDelete();

        Allure.step("Проверка что новость исчезла из списка");
        controlPanelPage.checkNewsNotExistsInList(titleToDelete);
    }

    @Test
    @Story("Обновление списка")
    @Description("TC-033: Обновление списка новостей (Pull-to-Refresh)")
    public void shouldRefreshNewsList() {
        Allure.step("Выполнение Pull-to-Refresh");
        controlPanelPage.pullToRefresh();

        Allure.step("Проверка что панель управления отображается");
        controlPanelPage.checkIsControlPanelDisplayed();
    }

    @Test
    @Story("Обновление списка")
    @Description("TC-039: Многократный Pull-to-Refresh")
    public void shouldHandleMultiplePullToRefresh() {
        Allure.step("Выполнение 6 Pull-to-Refresh");
        for (int i = 0; i < 6; i++) {
            controlPanelPage.pullToRefresh();
        }

        Allure.step("Проверка что панель управления отображается");
        controlPanelPage.checkIsControlPanelDisplayed();
    }

    @Test
    @Story("Защита от дублирования")
    @Description("TC-040: Быстрый двойной клик по кнопке Сохранить")
    public void shouldHandleDoubleClickOnSaveButton() {
        Allure.step("Нажатие кнопки добавления новости");
        controlPanelPage.clickAddNewsButton();

        Allure.step("Заполнение формы создания новости");
        createNewsPage.enterCategory(TestData.NEWS_CATEGORY);
        createNewsPage.enterTitle(TestData.NEWS_TITLE_DOUBLE_CLICK);
        createNewsPage.enterDate(TestData.NEWS_DATE_DOUBLE_CLICK);
        createNewsPage.enterTime(TestData.NEWS_TIME_DOUBLE_CLICK);
        createNewsPage.enterDescription(TestData.NEWS_DESCRIPTION_DOUBLE_CLICK);

        Allure.step("Первый клик по кнопке Сохранить");
        createNewsPage.clickSaveButton();

        Allure.step("Попытка второго клика по кнопке Сохранить");
        createNewsPage.tryClickSaveButton();

        Allure.step("Проверка что новость создана только один раз");
        controlPanelPage.checkNewsExistsInList(TestData.NEWS_TITLE_DOUBLE_CLICK);
    }

    @Test
    @Story("Защита от дублирования")
    @Description("TC-041: Быстрый двойной клик по кнопке Удалить")
    public void shouldHandleDoubleClickOnDeleteButton() {
        Allure.step("Запоминание заголовка первой новости");
        String firstTitle = controlPanelPage.getFirstNewsTitle();

        Allure.step("Удаление первой новости");
        controlPanelPage.clickDeleteNewsButton();
        controlPanelPage.confirmDelete();

        Allure.step("Проверка что первая новость удалена");
        controlPanelPage.checkNewsNotExistsInList(firstTitle);

        Allure.step("Запоминание заголовка следующей новости");
        String secondTitle = controlPanelPage.getFirstNewsTitle();

        Allure.step("Удаление следующей новости");
        controlPanelPage.clickDeleteNewsButton();
        controlPanelPage.confirmDelete();

        Allure.step("Проверка что вторая новость удалена");
        controlPanelPage.checkNewsNotExistsInList(secondTitle);
    }

    @Test
    @Story("Отмена создания")
    @Description("TC-042: Возврат назад из формы создания новости без сохранения")
    public void shouldCancelNewsCreationAndReturn() {
        Allure.step("Нажатие кнопки добавления новости");
        controlPanelPage.clickAddNewsButton();

        Allure.step("Заполнение формы создания новости");
        createNewsPage.enterCategory(TestData.NEWS_CATEGORY);
        createNewsPage.enterTitle(TestData.NEWS_TITLE_CANCEL);
        createNewsPage.enterDate(TestData.NEWS_DATE_CANCEL);
        createNewsPage.enterTime(TestData.NEWS_TIME_CANCEL);
        createNewsPage.enterDescription(TestData.NEWS_DESCRIPTION_CANCEL);

        Allure.step("Нажатие кнопки Отмена");
        createNewsPage.clickCancelButton();

        Allure.step("Подтверждение выхода без сохранения");
        controlPanelPage.confirmExitWithoutSaving();

        Allure.step("Проверка что новость не появилась в списке");
        controlPanelPage.checkNewsNotExistsInList(TestData.NEWS_TITLE_CANCEL);
    }
}
