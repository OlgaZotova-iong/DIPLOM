# Автотесты мобильного приложения «В Хосписе»

## Общая информация

В данном проекте реализована автоматизация тестирования Android-приложения
«В Хосписе» с использованием **Espresso** и **Allure Report**.
Тесты написаны с применением паттерна **Page Object Model**.
В проекте присутствуют тесты для разделов авторизации, новостей,
управления новостями и цитат.

---

## Технологии

| Технология | Версия |
|---|---|
| Android Espresso | 3.7.0 |
| Allure Kotlin Android | 2.4.0 |
| Allure Kotlin JUnit4 | 2.4.0 |
| JUnit 4 | 4.13.2 |
| UiAutomator | 2.4.0 |
| Gradle | — |

---

## Структура тестовых классов

| Класс | Описание |
|---|---|
| `AuthTest` | Тесты авторизации |
| `NewsTest` | Тесты раздела «Новости» |
| `NewsManagementTest` | Тесты раздела «Управление новостями» |
| `QuotesTest` | Тесты раздела «Цитаты» |

---

## Структура вспомогательных классов

| Класс | Описание |
|---|---|
| `TestData` | Централизованное хранение тестовых данных |
| `AuthPage` | Page Object для экрана авторизации |
| `MainPage` | Page Object для главного экрана |
| `NewsPage` | Page Object для раздела «Новости» |
| `ControlPanelPage` | Page Object для панели управления новостями |
| `CreateNewsPage` | Page Object для формы создания/редактирования новости |
| `QuotesPage` | Page Object для раздела «Цитаты» |
| `AllureScreenshotRule` | Правило для прикрепления скриншотов к Allure-отчёту |
| `WaitHelper` | Утилита для ожидания появления элементов на экране |

---

## Запуск тестов

### Предварительные требования

- Подключённое Android-устройство или запущенный эмулятор
- Android SDK установлен и настроен
- `adb` доступен в переменной окружения `PATH`

---

### Через Android Studio

1. Откройте проект в Android Studio.
2. Подключите устройство или запустите эмулятор.
3. Найдите нужный тестовый класс в папке `androidTest`.
4. Запустите тест через зелёную кнопку **Run**.

---

### Через командную строку

> На Windows используйте `.\gradlew.bat` вместо `./gradlew`

#### Запуск всех тестов

```bash
./gradlew connectedDebugAndroidTest
Markdown
Запуск отдельного класса
# Авторизация
./gradlew connectedDebugAndroidTest \
-Pandroid.testInstrumentationRunnerArguments.class=ru.edu.qamid.uiTests.AuthTest

# Новости
./gradlew connectedDebugAndroidTest \
-Pandroid.testInstrumentationRunnerArguments.class=ru.edu.qamid.uiTests.NewsTest

# Управление новостями
./gradlew connectedDebugAndroidTest \
-Pandroid.testInstrumentationRunnerArguments.class=ru.edu.qamid.uiTests.NewsManagementTest

# Цитаты
./gradlew connectedDebugAndroidTest \
-Pandroid.testInstrumentationRunnerArguments.class=ru.edu.qamid.uiTests.QuotesTest
Bash
Запуск отдельного теста
./gradlew connectedDebugAndroidTest \
-Pandroid.testInstrumentationRunnerArguments.class=ru.edu.qamid.uiTests.AuthTest#testPositiveLogin_TC001
Bash
Шаблон команды для любого теста
./gradlew connectedDebugAndroidTest \
-Pandroid.testInstrumentationRunnerArguments.class=ru.edu.qamid.uiTests.<ИмяКласса>#<имяМетода>
Bash
Формирование Allure-отчёта
Шаг 1 — Запустить тесты

./gradlew connectedDebugAndroidTest
Bash
Шаг 2 — Скачать результаты с устройства

./gradlew downloadAllureResults
Bash
Результаты будут скопированы в папку allure-results в корне проекта.

Шаг 3 — Сформировать отчёт

allure serve allure-results
Bash
Результаты тестирования
Архив с результатами прогона тестов находится в файле allure-results.zip
в корне проекта.

Для просмотра отчёта:

Распакуйте allure-results.zip
Выполните команду:
allure serve <папка с результатами>
Bash
Известные ограничения
Тесты TC-003 и TC-004 (негативные сценарии авторизации) нестабильны
при запуске через Gradle из-за ограничений Espresso при работе
с Toast-сообщениями на Android 11+.
Тесты выполняются последовательно на одном подключённом устройстве
или эмуляторе.
Технические ограничения
При подключении библиотеки allure-android возникли конфликты зависимостей
с текущей конфигурацией проекта. В качестве альтернативы используется
allure-kotlin, которая поддерживает аннотации (@Epic, @Feature,
@Story, @Description) и логирование шагов (Allure.step()), но не
поддерживает автоматическое прикрепление скриншотов при падении тестов.
По этой причине скриншоты в Allure-отчёте отсутствуют.