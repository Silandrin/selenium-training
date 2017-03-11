package ru.stqa.training.selenium;

import org.testng.annotations.*;
import org.testng.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by aataushk on 11.03.2017.
 */
public class Exercise9 {

    private WebDriver driver;

    public void checkOrder(List<WebElement> elementList) {
        ArrayList<String> list = new ArrayList<>();
        for (WebElement item : elementList) {
            String str = item.getText();
            list.add(str);
        }
        int rowCount = elementList.size();
        for (int i = 1; i < rowCount; i++) {
            int compare = list.get(i).compareTo(list.get(i-1));
            Assert.assertFalse(compare <= 0, "Wrong order: "+list.get(i-1)+" and "+list.get(i));
        }

    }

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
    public void countries() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        checkOrder(driver.findElements(By.cssSelector("table tbody tr.row td:nth-child(5) a")));

    }

    @Test
    public void countryZones() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        ArrayList<Integer> zoneCountList = new ArrayList<>();
        ArrayList<String> allLinksList = new ArrayList<>();
        ArrayList<String> linksList = new ArrayList<>();
        Integer zoneCount;
        String link;
        int i;
        for (WebElement item : driver.findElements(By.cssSelector("table tbody tr.row td:nth-child(6)"))) {
            zoneCount = Integer.parseInt(item.getText());
            zoneCountList.add(zoneCount);
        }
        for (WebElement item : driver.findElements(By.cssSelector("table tbody tr.row td:nth-child(5) a"))) {
            link = item.getAttribute("href");
            allLinksList.add(link);
        }
        for (i = 0; i < driver.findElements(By.cssSelector("table tbody tr.row td:nth-child(6)")).size(); i++) {
            if (zoneCountList.get(i) != 0) {
                linksList.add(allLinksList.get(i));
            }
        }
        for (i = 0; i < linksList.size(); i++) {
            driver.get(linksList.get(i));
            checkOrder(driver.findElements(By.xpath("//*[@id='table-zones']/tbody/tr/td[3]/input[@type='hidden']/..")));
        }
    }

    @Test
    public void geoZones() {
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        ArrayList<String> linksList = new ArrayList<>();
        String link;
        int i;
        for (WebElement item : driver.findElements(By.cssSelector("tr.row td:nth-child(3) a"))) {
            link = item.getAttribute("href");
            linksList.add(link);
        }
        for (i = 0; i < linksList.size(); i++) {
            driver.get(linksList.get(i));
            checkOrder(driver.findElements(By.cssSelector("#table-zones tr td:nth-child(3) option[selected]")));
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
