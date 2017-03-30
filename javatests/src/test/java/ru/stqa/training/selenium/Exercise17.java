package ru.stqa.training.selenium;

import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Created by aataushk on 30.03.2017.
 */
public class Exercise17 {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void start() {
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        driver = new ChromeDriver(cap);
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void checkLog() {
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        List<String> linkList = new ArrayList<>();
        for (WebElement item : driver.findElements(By.cssSelector("tr.row td a[title=Edit][href *= product]"))) {
            linkList.add(item.getAttribute("href"));
        }
        driver.manage().logs().get("browser").getAll(); //Очистим лог перед прогоном
        int logEntriesCount = 0;
        for (int i = 0; i < linkList.size(); i++) {
            driver.get(linkList.get(i));
            List<LogEntry> log = driver.manage().logs().get("browser").getAll();
            if (log.size() > 0) {
                logEntriesCount += log.size();
                System.out.println(log);
            }
        }
        System.out.println("Всего найдено записей в логах: " + logEntriesCount);
        Assert.assertEquals(logEntriesCount, 0, "В логе браузера обнаружены записи:");
    }

    @AfterClass
    public void stop(){
        driver.quit();
    }
}