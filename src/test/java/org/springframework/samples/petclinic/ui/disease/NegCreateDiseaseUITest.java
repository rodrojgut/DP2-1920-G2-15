package org.springframework.samples.petclinic.ui.disease;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NegCreateDiseaseUITest {

    @LocalServerPort
    private int port;
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeEach
    public void setUp() throws Exception {
    	String pathToChromeDriver = System.getenv("webdriver.chrome.driver");
    	System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
        driver = new ChromeDriver();
        baseUrl = "https://www.google.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testNegCreateDiseaseUI() throws Exception {
        driver.get("http://localhost:" + port + "/");
        driver.findElement(By.linkText("LOGIN")).click();
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("vet1");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("v3t");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a/span[2]")).click();
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.linkText("George Franklin")).click();
        driver.findElement(By.linkText("Add Disease")).click();
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertEquals("no puede estar vacío", driver.findElement(By.xpath("//form[@id='disease']/div/div/div/span[2]")).getText());
        assertEquals("tiene que corresponder a la expresión regular \"LOW|MEDIUM|HIGH\"", driver.findElement(By.xpath("//form[@id='disease']/div/div[2]/div/span[2]")).getText());
        assertEquals("no puede estar vacío", driver.findElement(By.xpath("//form[@id='disease']/div/div[3]/div/span[2]")).getText());
    }

    @AfterEach
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}