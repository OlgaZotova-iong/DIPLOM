package ru.edu.qamid.listeners;

import android.graphics.Bitmap;
import androidx.test.runner.screenshot.Screenshot;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import io.qameta.allure.kotlin.Allure;

public class AllureScreenshotRule extends TestWatcher {

    @Override
    protected void failed(Throwable e, Description description) {
        takeScreenshot("Screenshot on failure: " + description.getMethodName());
    }

    private void takeScreenshot(String name) {
        try {
            Bitmap bitmap = Screenshot.capture().getBitmap();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] bytes = outputStream.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(bytes);


            Allure.INSTANCE.attachment(name, inputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}




