package ru.stqa.training.selenium.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends Page {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void removeAll() {
        int removeCount = driver.findElements(By.cssSelector("li.shortcut")).size();
        if (removeCount > 0) {
            for (int j = 1; j < removeCount; j++) {
                driver.findElement(By.cssSelector("li.shortcut")).click();
                wait.until(ExpectedConditions.attributeToBe(By.cssSelector("ul.items"), "offsetLeft", "5" ));
                removeGoods();
            }
        }
        removeGoods();
    }

    public void removeGoods() {
        driver.findElement(By.cssSelector("button[name=remove_cart_item]")).click();
        WebElement table = driver.findElement(By.cssSelector("table.dataTable"));
        wait.until(ExpectedConditions.stalenessOf(table));
    }
}
