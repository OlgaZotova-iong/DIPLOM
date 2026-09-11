# Автотесты мобильного приложения «В Хосписе»

## Общая информация

В данном проекте реализована автоматизация тестирования Android-приложения
«В Хосписе» с использованием **Espresso** и **Allure Report**.
Тесты написаны с применением паттерна **Page Object Model**.
В проекте присутствуют тесты для разделов авторизации, новостей,
управления новостями и цитат.

## Технологии

| Технология | Версия |
|---|---|
| Android Espresso | 3.7.0 |
| Allure Kotlin Android | 2.4.0 |
| Allure Kotlin JUnit4 | 2.4.0 |
| JUnit 4 | 4.13.2 |
| UiAutomator | 2.4.0 |
| Gradle | — |

## Структура тестовых классов

| Класс | Описание |
|---|---|
| `AuthTest` | Тесты авторизации |
| `NewsTest` | Тесты раздела «Новости» |
| `NewsManagementTest` | Тесты раздела «Управление новостями» |
| `QuotesTest` | Тесты раздела «Цитаты» |

## Запуск тестов

### Предварительные требования

- Подключённое Android-устройство или запущенный эмулятор
- Android SDK установлен и настроен
- `adb` доступен в переменной окружения PATH

### Через Android Studio

1. Откройте проект.
2. Подключите устройство или запустите эмулятор.
3. Найдите нужный тестовый класс в папке `androidTest`.
4. Запустите тест через зелёную кнопку Run.

### Через командную строку

> На Windows используйте `.\gradlew.bat` вместо `./gradlew`

#### Запуск всех тестов

bash
./gradlew connectedDebugAndroidTest


#### Запуск отдельного класса

bash


Авторизация

./gradlew connectedDebugAndroidTest \
-Pandroid.testInstrumentationRunnerArguments.class=ru.edu.qamid.uiTests.AuthTest


Новости

./gradlew connectedDebugAndroidTest \
-Pandroid.testInstrumentationRunnerArguments.class=ru.edu.qamid.uiTests.NewsTest


Управление новостями

./gradlew connectedDebugAndroidTest \
-Pandroid.testInstrumentationRunnerArguments.class=ru.edu.qamid.uiTests.NewsManagementTest


Цитаты

./gradlew connectedDebugAndroidTest \
-Pandroid.testInstrumentationRunnerArguments.class=ru.edu.qamid.uiTests.QuotesTest


#### Запуск отдельного теста

bash
./gradlew connectedDebugAndroidTest \
-Pandroid.testInstrumentationRunnerArguments.class=ru.edu.qamid.uiTests.AuthTest#testPositiveLogin_TC001


#### Шаблон команды для любого теста

bash
./gradlew connectedDebugAndroidTest \
-Pandroid.testInstrumentationRunnerArguments.class=ru.edu.qamid.uiTests.<ИмяКласса>#<имяМетода>


## Формирование Allure-отчёта

**Шаг 1 — Запустить тесты**

bash
./gradlew connectedDebugAndroidTest


**Шаг 2 — Скачать результаты с устройства**

bash
./gradlew downloadAllureResults


Результаты будут скопированы в папку `allure-results` в корне проекта.

**Шаг 3 — Сформировать отчёт**

bash
allure serve allure-results


## Известные ограничения

Тесты `TC-003` и `TC-004` (негативные сценарии авторизации)
нестабильны при запуске через Gradle из-за ограничений Espresso
при работе с Toast-сообщениями.

Тесты выполняются последовательно на одном подключённом устройстве
или эмуляторе.

## Технические ограничения

При подключении библиотеки `allure-android` возникли конфликты зависимостей
(dependency conflict) с текущей конфигурацией проекта. В качестве альтернативы
используется `allure-kotlin`, которая поддерживает аннотации
(@Epic, @Feature, @Story, @Description) и логирование шагов (Allure.step()),
но не поддерживает автоматическое прикрепление скриншотов (attachment)
при падении тестов. По этой причине скриншоты в Allure отчёте отсутствуют.

## Результаты тестирования

Архив с результатами прогона тестов находится в файле `allure-results.zip`
в корне проекта.

Для просмотра отчёта:
1. Распакуйте `allure-results.zip`
2. Выполните команду `allure serve <папка с результатами>`