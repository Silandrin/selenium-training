package ru.stqa.training.selenium;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

/**
 * Created by aataushk on 28.03.2017.
 */
public class Exercise13 {
    private WebDriver driver;

    @BeforeClass
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void shopCart() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost/litecart/en/");
        for (int i = 1; i <= 3; i++) {
            driver.findElement(By.cssSelector("li.product")).click();
            if (driver.findElements(By.cssSelector("form[name=buy_now_form] tbody > tr")).size() == 2) {
                new Select(driver.findElement(By.cssSelector("select[name *= options]"))).selectByVisibleText("Small");
            }
            driver.findElement(By.cssSelector("button[name = add_cart_product]")).click();
            wait.until(ExpectedConditions.textToBe(By.cssSelector("span.quantity"), ""+i));
        }
        driver.findElement(new By.ByLinkText("Checkout »")).click();
        int removeCount = driver.findElements(By.cssSelector("li.shortcut")).size();
        if (removeCount > 0) {
            for (int j = 1; j < removeCount; j++) {
                driver.findElement(By.cssSelector("li.shortcut")).click();
                driver.findElement(By.cssSelector("button[name=remove_cart_item]")).click();
                WebElement table = driver.findElement(By.cssSelector("table.dataTable"));
                wait.until(ExpectedConditions.stalenessOf(table));
            }
        }
        driver.findElement(By.cssSelector("button[name=remove_cart_item]")).click();
        WebElement table = driver.findElement(By.cssSelector("table.dataTable"));
        wait.until(ExpectedConditions.stalenessOf(table));
    }

    @AfterClass
    public void stop(){
        driver.quit();
    }
}
