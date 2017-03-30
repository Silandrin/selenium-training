package ru.stqa.training.selenium.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.stqa.training.selenium.pages.CartPage;
import ru.stqa.training.selenium.pages.GoodsPage;
import ru.stqa.training.selenium.pages.MainPage;

public class Application {

    private WebDriver driver;

    private CartPage cartPage;
    private GoodsPage goodsPage;
    private MainPage mainPage;

    public Application() {
        driver = new ChromeDriver();
        cartPage = new CartPage(driver);
        goodsPage = new GoodsPage(driver);
        mainPage = new MainPage(driver);
    }

    public void quit() {
        driver.quit();
    }

    public void buyGoods(int i) {
        mainPage.open();
        mainPage.chooseGoods();
        goodsPage.setOptions();
        goodsPage.addGoods(i);
    }

    public void cleanCart() {
        goodsPage.goToCart();
        cartPage.removeAll();
    }
}