package ru.stqa.training.selenium.tests;

import org.testng.annotations.*;

public class Exercise19 extends TestBase {

    @Test
    public void shopCartPO() {
        for (int i = 1; i <= 3; i++) {
            app.buyGoods(i);
        }
        app.cleanCart();

    }
}