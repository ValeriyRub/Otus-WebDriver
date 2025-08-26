import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebDriverTest {

    WebDriver driver;

    @Test
    void TestInput() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(TestConfig.getBaseUrl());

        WebElement input = driver.findElement(By.id("textInput"));
        input.sendKeys("ОТУС");

        assertEquals("ОТУС", input.getAttribute("value"));
    }

    @Test
    void TestModal() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(TestConfig.getBaseUrl());

        WebElement button = driver.findElement(By.id("openModalBtn"));
        button.click();

        WebElement modal = driver.findElement(By.xpath("//h2[text()='Это модальное окно']"));
        assertTrue(modal.isDisplayed());
    }

    @Test
    void TestForm() {
        ChromeOptions options = new ChromeOptions();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(TestConfig.getBaseUrl());

        WebElement inputName = driver.findElement(By.id("name"));
        inputName.sendKeys("фыв");

        WebElement inputEmail = driver.findElement(By.id("email"));
        inputEmail.sendKeys("asdf@sdfg.rt");

        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        button.click();

        WebElement resultMessage = driver.findElement(By.id("messageBox"));
        String expectedMessage = "Форма отправлена с именем: " + inputName.getAttribute("value") + " и email: " + inputEmail.getAttribute("value");

        assertTrue(resultMessage.isDisplayed());
        assertEquals(expectedMessage, resultMessage.getText());
    }


    @AfterEach
    public void endDriver(){
        if (driver !=null){
            driver.close();
        }
    }
}
