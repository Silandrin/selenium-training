package ru.stqa.training.selenium.tests;

import org.testng.annotations.*;
import ru.stqa.training.selenium.app.Application;


public class TestBase {
    public Application app;

    @BeforeMethod
    public void start() {
        app = new Application();
    }

    @AfterMethod
    public void stop(){
        app.quit();
    }
}
