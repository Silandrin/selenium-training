package ru.stqa.training.selenium;

import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by aataushk on 02.03.2017.
 */
public class AdminPage {

    private WebDriver driver;

    @BeforeClass
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void massHeaderCheck() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        ArrayList<String> menu = new ArrayList<>();
        for (WebElement row : driver.findElements(By.cssSelector("ul.list-vertical li"))) {
            String str = row.getText();
            menu.add(str);
        }

        int rowCount = driver.findElements(By.cssSelector("ul.list-vertical li")).size();
        for (int i = 0; i < rowCount ; i++) {
            driver.findElement(new By.ByLinkText(menu.get(i))).click();
            driver.findElement(By.tagName("h1"));
            System.out.println(menu.get(i));
            if (driver.findElements(By.cssSelector("ul.docs li")).size() > 0) {
                ArrayList<String> submenu = new ArrayList<>();
                for (WebElement subrow : driver.findElements(By.cssSelector("ul.docs li"))) {
                    String str = subrow.getText();
                    submenu.add(str);
                }
                int subrowCount = driver.findElements(By.cssSelector("ul.docs li")).size();
                for (int j = 0; j < subrowCount ; j++) {
                    driver.findElement(new By.ByLinkText(submenu.get(j))).click();
                    driver.findElement(By.tagName("h1"));
                    System.out.println(submenu.get(j));
                }
            }
        }
    }

    @AfterClass
    public void stop(){
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
