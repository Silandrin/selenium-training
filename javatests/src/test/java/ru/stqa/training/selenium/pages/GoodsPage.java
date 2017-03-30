package ru.stqa.training.selenium.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class GoodsPage extends Page {

    public GoodsPage(WebDriver driver) {
        super(driver);
    }

    public void setOptions() {
        if (driver.findElements(By.cssSelector("form[name=buy_now_form] tbody > tr")).size() == 2) {
            new Select(driver.findElement(By.cssSelector("select[name *= options]"))).selectByVisibleText("Small");
        }
    }

    public void addGoods(int i) {
        driver.findElement(By.cssSelector("button[name = add_cart_product]")).click();
        wait.until(ExpectedConditions.textToBe(By.cssSelector("span.quantity"), ""+i));
    }

    public void goToCart() {
        driver.findElement(new By.ByLinkText("Checkout »")).click();
    }
}
