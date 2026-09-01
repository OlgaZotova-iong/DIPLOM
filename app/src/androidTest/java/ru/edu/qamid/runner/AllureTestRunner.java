package ru.edu.qamid.runner;

import android.os.Bundle;
import androidx.test.runner.AndroidJUnitRunner;
import io.qameta.allure.android.runners.AllureAndroidJUnitRunner;

public class AllureTestRunner extends AllureAndroidJUnitRunner {
    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
    }
}
