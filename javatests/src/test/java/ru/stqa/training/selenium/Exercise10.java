package ru.stqa.training.selenium;

import org.testng.annotations.*;
import org.testng.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.ie.InternetExplorerDriver;
import java.util.concurrent.TimeUnit;

/**
 * Created by aataushk on 11.03.2017.
 */
public class Exercise10 {

    private WebDriver driver;

    private int parseColorRGBa(String color, String channel) {
        String[] numbers = color.replace("rgba(", "").replace(")", "").split(",");
        int r = Integer.parseInt(numbers[0].trim());
        int g = Integer.parseInt(numbers[1].trim());
        int b = Integer.parseInt(numbers[2].trim());
        int a = Integer.parseInt(numbers[3].trim());
        int value = 0;
        switch (channel) {
            case "r": value = r;
                      break;
            case "g": value = g;
                      break;
            case "b": value = b;
                      break;
            case "a": value = a;
                      break;
            default:  System.out.println("Wrong channel ID!");
                      break;
        }
        return value;
    }

    private void checkPriceColor(String locator) {
        String PriceColor = driver.findElement(By.cssSelector(locator)).getCssValue("color");
        int PriceColorR = parseColorRGBa(PriceColor, "r");
        int PriceColorG = parseColorRGBa(PriceColor, "g");
        int PriceColorB = parseColorRGBa(PriceColor, "b");
        Assert.assertEquals(PriceColorG, PriceColorR, "Цвет стандартной цены не серый (R and G channels); Page: "+driver.getCurrentUrl());
        Assert.assertEquals(PriceColorB, PriceColorR, "Цвет стандартной цены не серый (R and B channels); Page: "+driver.getCurrentUrl());
    }

    private void checkDiscPriceColor(String locator) {
        String DiscPriceColor = driver.findElement(By.cssSelector(locator)).getCssValue("color");
        int DiscPriceColorR = parseColorRGBa(DiscPriceColor, "r");
        int DiscPriceColorG = parseColorRGBa(DiscPriceColor, "g");
        int DiscPriceColorB = parseColorRGBa(DiscPriceColor, "b");
        Assert.assertTrue(DiscPriceColorR != 0, "Цвет акционной цены не красный (R channel); Page: "+driver.getCurrentUrl());
        Assert.assertEquals(DiscPriceColorG, 0, "Цвет акционной цены не красный (G channel); Page: "+driver.getCurrentUrl());
        Assert.assertEquals(DiscPriceColorB, 0, "Цвет акционной цены не красный (B channel); Page: "+driver.getCurrentUrl());
    }

    @Test
    public void checkCampaignItem() {
        for (int i = 1; i <= 3; i++ ) {
            switch (i) {
                case 1:
                    driver = new ChromeDriver();
                    break;
                case 2:
                    driver = new InternetExplorerDriver();
                    break;
                default: {
                    DesiredCapabilities caps = new DesiredCapabilities();
                    caps.setCapability(FirefoxDriver.MARIONETTE, false);
                    driver = new FirefoxDriver(caps);
                }
                break;
            }

            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.get("http://localhost/litecart/en/");
            String expoName = driver.findElement(By.cssSelector("div#box-campaigns a.link div.name")).getText();
            String expoPrice = driver.findElement(By.cssSelector("div#box-campaigns a.link s.regular-price")).getText();
            String expoDiscPrice = driver.findElement(By.cssSelector("div#box-campaigns a.link strong.campaign-price")).getText();

            checkPriceColor("div#box-campaigns a.link s.regular-price");
            checkDiscPriceColor("div#box-campaigns a.link strong.campaign-price");

            //FF и Chrome возвращают px, IE возвращает em
            Double expoPriceSize = Double.parseDouble(driver.findElement(By.cssSelector("div#box-campaigns a.link s.regular-price")).getCssValue("fontSize").replace("px", "").replace("em", ""));
            Double expoDiscPriceSize = Double.parseDouble(driver.findElement(By.cssSelector("div#box-campaigns a.link strong.campaign-price")).getCssValue("fontSize").replace("px", "").replace("em", ""));
            Assert.assertTrue(expoPriceSize < expoDiscPriceSize, "Размер шрифта станадртной цены не меньше размера шрифта акционной цены");

            driver.findElement(By.cssSelector("div#box-campaigns a.link")).click();
            String itemName = driver.findElement(By.cssSelector("h1.title")).getText();
            String itemPrice = driver.findElement(By.cssSelector("s.regular-price")).getText();
            String itemDiscPrice = driver.findElement(By.cssSelector("strong.campaign-price")).getText();
            Assert.assertEquals(expoName, itemName, "Названия не совпадают");
            Assert.assertEquals(expoPrice, itemPrice, "Стандартные цены не совпадают");
            Assert.assertEquals(expoDiscPrice, itemDiscPrice, "Акционные цены не совпадают");

            checkPriceColor("s.regular-price");
            checkDiscPriceColor("strong.campaign-price");

            //FF и Chrome возвращают px, IE возвращает em
            Double itemPriceSize = Double.parseDouble(driver.findElement(By.cssSelector("s.regular-price")).getCssValue("fontSize").replace("px", "").replace("em", ""));
            Double itemDiscPriceSize = Double.parseDouble(driver.findElement(By.cssSelector("strong.campaign-price")).getCssValue("fontSize").replace("px", "").replace("em", ""));
            Assert.assertTrue(itemPriceSize < itemDiscPriceSize, "Размер шрифта станадртной цены должен быть меньше размера шрифта акционной цены");
            driver.quit();
        }
    }
}
