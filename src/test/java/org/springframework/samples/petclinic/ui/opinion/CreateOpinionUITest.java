package org.springframework.samples.petclinic.ui.opinion;

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
public class CreateOpinionUITest {
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
  public void testCreateOpinionUI() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("LOGIN")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("admin1");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("4dm1n");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
    driver.findElement(By.linkText("Add Opinion")).click();
    driver.findElement(By.id("comentary")).click();
    driver.findElement(By.id("comentary")).clear();
    driver.findElement(By.id("comentary")).sendKeys("Es un poco tonto");
    driver.findElement(By.id("puntuation")).click();
    driver.findElement(By.id("puntuation")).clear();
    driver.findElement(By.id("puntuation")).sendKeys("2");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.linkText("List all opinions")).click();
    assertEquals("James Carter", driver.findElement(By.xpath("//table[@id='opinionsTable']/tbody/tr[7]/td")).getText());
    assertEquals("2", driver.findElement(By.xpath("//table[@id='opinionsTable']/tbody/tr[7]/td[2]")).getText());
    assertEquals("Es un poco tonto", driver.findElement(By.xpath("//table[@id='opinionsTable']/tbody/tr[7]/td[4]")).getText());
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
