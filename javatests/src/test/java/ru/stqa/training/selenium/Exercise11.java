package ru.stqa.training.selenium;

import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by aataushk on 16.03.2017.
 */
public class Exercise11 {
    private WebDriver driver;

    public String generateEmail() {
        ArrayList<String> alphaNum = new ArrayList<>();

        for (char c = 'A';c<= 'z';c++){
            String s = new String();
            s +=c;
            alphaNum.add(s);
            //Исключаем лишние символы ( ],[,...)
            if (c == 'Z') c = 'a'-1;
        }

        for (int c = 0;c<10;c++){
            String s = new String();
            s +=c;
            alphaNum.add(s);
        }

        String str = "TestUser_";
        for (int j = 0; j < 20; j++) {
            str += alphaNum.get((int) (Math.random() * alphaNum.size()));
        }
        str += "@domain.no";
        System.out.println("Регистрируем пользователя с адресом электронной почты " + str);
        return str;
    }

    @BeforeMethod
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void regUser() {
        String email = generateEmail();
        driver.get("http://localhost/litecart/en/");
        driver.findElement(By.linkText("New customers click here")).click();
        new Select(driver.findElement(By.cssSelector("select"))).selectByVisibleText("United States");
        driver.findElement(By.cssSelector("input[name=firstname]")).sendKeys("Firstname");
        driver.findElement(By.cssSelector("input[name=lastname]")).sendKeys("Lastname");
        driver.findElement(By.cssSelector("input[name=address1]")).sendKeys("Address_1");
        driver.findElement(By.cssSelector("input[name=postcode]")).sendKeys("12345");
        driver.findElement(By.cssSelector("input[name=city]")).sendKeys("SomeCity");
        driver.findElement(By.cssSelector("input[name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("+19876543210");
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys("password");
        driver.findElement(By.cssSelector("input[name=confirmed_password]")).sendKeys("password");

        driver.findElement(By.cssSelector("button[type=submit]")).click();
        driver.findElement(By.cssSelector("a[href $= logout]")).click();
        driver.findElement(By.cssSelector("input[name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys("password", Keys.ENTER);
        driver.findElement(By.cssSelector("a[href $= logout]")).click();
    }

    @AfterMethod
    public void stop(){
        driver.quit();
    }
}
