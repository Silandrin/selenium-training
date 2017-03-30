package ru.stqa.training.selenium.pages;

import org.openqa.selenium.*;

public class MainPage extends Page {

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public MainPage open() {
        driver.get("http://localhost/litecart/en/");
        return this;
    }

    public void chooseGoods() {
        driver.findElement(By.cssSelector("li.product")).click();
    }
}
