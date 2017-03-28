package ru.stqa.training.selenium;

import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by aataushk on 28.03.2017.
 */
public class Exercise12 {
    private WebDriver driver;

    @BeforeClass
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void regUser() {
        driver.findElement(By.cssSelector("a[href $= catalog]")).click();
        driver.findElement(By.cssSelector("a[href $= product]")).click();
        driver.findElement(By.cssSelector("label input:not(:checked)")).click();
        driver.findElement(By.cssSelector("input[name *= name]")).sendKeys("BatDuck", Keys.TAB, "bd001",
                Keys.TAB, Keys.SPACE, Keys.TAB, Keys.SPACE, Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.TAB, Keys.SPACE,
                Keys.TAB, "30");
        new Select(driver.findElement(By.cssSelector("select[name = sold_out_status_id]"))).selectByVisibleText("Temporary sold out");
        File file = new File("batduck.jpg");
        driver.findElement(By.cssSelector("input[name *= new_images]")).sendKeys(file.getAbsolutePath());

        driver.findElement(By.cssSelector("a[href $= information]")).click();
        new Select(driver.findElement(By.cssSelector("select[name = manufacturer_id]"))).selectByVisibleText("ACME Corp.");
        driver.findElement(By.cssSelector("input[name *= short_description]")).sendKeys("I'm Batduck", Keys.TAB, "KEEP CALM BECAUSE I'M BATDUCK");

        driver.findElement(By.cssSelector("a[href $= prices]")).click();
        driver.findElement(By.cssSelector("input[name = purchase_price]")).sendKeys(Keys.chord(Keys.CONTROL, "a"), "10");
        new Select(driver.findElement(By.cssSelector("select[name = purchase_price_currency_code]"))).selectByVisibleText("US Dollars");
        driver.findElement(By.cssSelector("input[data-type = currency][name *= USD]")).sendKeys("20");

        driver.findElement(By.cssSelector("button[name = save]")).click();
        driver.findElement(new By.ByLinkText("BatDuck")).click();
    }

    @AfterClass
    public void stop(){
        driver.quit();
    }
}
