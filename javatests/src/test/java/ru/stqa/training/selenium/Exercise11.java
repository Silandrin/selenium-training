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
        driver.findElement(By.cssSelector("input[name=firstname]"))
                .sendKeys("Firstname", Keys.TAB, "Lastname", Keys.TAB,
                        "Address_1", Keys.TAB, Keys.TAB,
                        "12345", Keys.TAB, "SomeCity", Keys.TAB,
                        Keys.TAB, Keys.TAB, email, Keys.TAB, "+19876543210", Keys.TAB, Keys.SPACE, Keys.TAB,
                        "password", Keys.TAB, "password");
        driver.findElement(By.cssSelector("button[type=submit]")).click();
        driver.findElement(By.cssSelector("a[href $= logout]")).click();
        driver.findElement(By.cssSelector("input[type=text]")).sendKeys(email, Keys.TAB, "password", Keys.ENTER);
        driver.findElement(By.cssSelector("a[href $= logout]")).click();
    }

    @AfterMethod
    public void stop(){
        driver.quit();
    }
}
