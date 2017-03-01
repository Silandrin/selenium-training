package ru.stqa.training.selenium;

import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by aataushk on 22.02.2017.
 */
public class MyFirstTest {

    private WebDriver driver;

    @BeforeMethod
    public void start() {
        driver = new ChromeDriver();
    }

    @Test
    public void simpleTest() {
        driver.get("http://www.google.com/");

    }

    @AfterMethod
    public void stop(){
        driver.quit();
        driver = null;
    }
}
