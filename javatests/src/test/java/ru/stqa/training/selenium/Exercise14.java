package ru.stqa.training.selenium;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by aataushk on 28.03.2017.
 */
public class Exercise14 {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void checkOpenNewWindow() {
        String newWindow = null;
        driver.findElement(By.cssSelector("a[href $= countries]")).click();
        driver.findElement(By.linkText("Add New Country")).click();
        List<WebElement> links = driver.findElements(By.cssSelector("i.fa-external-link"));
        for (int i = 0; i < links.size(); i++) {
            String mainWindow = driver.getWindowHandle();
            Set<String> oldWindows = driver.getWindowHandles();
            links.get(i).click();
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));
            for (String handle : driver.getWindowHandles()) {
                if (!oldWindows.contains(handle)) {
                    newWindow = handle;
                }
            }
            driver.switchTo().window(newWindow);
            driver.close();
            driver.switchTo().window(mainWindow);
        }
    }

    @AfterClass
    public void stop(){
        driver.quit();
    }
}
