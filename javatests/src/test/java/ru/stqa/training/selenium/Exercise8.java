package ru.stqa.training.selenium;

import org.testng.annotations.*;
import org.testng.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

/**
 * Created by aataushk on 02.03.2017.
 */
public class Exercise8 {

    private WebDriver driver;

    @BeforeClass
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void duckStickerCheck() {
        driver.get("http://localhost/litecart/en/");

        //Убедимся, что у каждой утки есть МИНИМУМ один стикер
        for (WebElement image : driver.findElements(By.cssSelector("div.image-wrapper"))) {
            image.findElement(By.cssSelector("div.sticker"));
        }

        //Убедимся, что стикеров столько же, сколько и уток, а значит у каждой лишь по одному
        Assert.assertEquals(driver.findElements(By.cssSelector("div.sticker")).size(),driver.findElements(By.cssSelector("div.image-wrapper")).size(), "Стикеров больше, чем уток!");

    }

    @AfterClass
    public void stop(){
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
