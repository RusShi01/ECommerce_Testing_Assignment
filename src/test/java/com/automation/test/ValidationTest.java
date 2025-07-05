package com.automation.test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.automation.base.BaseClass;

public class ValidationTest extends BaseClass {

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://automationteststore.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void registrationWithMissingField() throws InterruptedException, IOException {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Navigate to Account > Register
        WebElement acc = driver.findElement(By.xpath("//ul[@id='main_menu_top']//span[contains(@class,'menu_text')][normalize-space()='Account']"));
        acc.click();
        js.executeScript("window.scrollBy(0,500)");

        driver.findElement(By.xpath("//button[normalize-space()='Continue']")).click();
        js.executeScript("window.scrollBy(0,500)");

        // Fill registration form with missing last name
        driver.findElement(By.xpath("//input[@id='AccountFrm_firstname']")).sendKeys("Aditya");
        // driver.findElement(By.xpath("//input[@id='AccountFrm_lastname']")).sendKeys("Borse"); // <-- Left intentionally blank
        driver.findElement(By.xpath("//input[@id='AccountFrm_email']")).sendKeys("adityaborse16@gmail.com");
        driver.findElement(By.xpath("//input[@id='AccountFrm_address_1']")).sendKeys("karajgao");
        driver.findElement(By.xpath("//input[@id='AccountFrm_city']")).sendKeys("Sambhajinagar");

        WebElement countryDrp = driver.findElement(By.xpath("//select[@id='AccountFrm_country_id']"));
        new Select(countryDrp).selectByVisibleText("India");

        WebElement stateDrp = driver.findElement(By.xpath("//select[@id='AccountFrm_zone_id']"));
        new Select(stateDrp).selectByVisibleText("Maharashtra");

        driver.findElement(By.xpath("//input[@id='AccountFrm_postcode']")).sendKeys("431101");
        driver.findElement(By.xpath("//input[@id='AccountFrm_loginname']")).sendKeys("Adi2242");
        driver.findElement(By.xpath("//input[@id='AccountFrm_password']")).sendKeys("Aditya@123");
        driver.findElement(By.xpath("//input[@id='AccountFrm_confirm']")).sendKeys("Aditya@123");

        // Submit the form
        driver.findElement(By.xpath("//button[normalize-space()='Continue']")).click();

        Thread.sleep(1000); // wait for error to appear

        // Check if error message appears
        try {
            WebElement errorMsg = driver.findElement(By.xpath("//div[@class='alert alert-error alert-danger']"));
            if (errorMsg.isDisplayed()) {
                System.out.println(" Error message displayed: " + errorMsg.getText());
                takeScreenshot("missing_lastname_validation.png");
            } else {
                System.out.println(" No error message. Registration may have succeeded (unexpected).");
            }
        } catch (NoSuchElementException e) {
            System.out.println(" Error message element not found!");
            takeScreenshot("error_element_not_found.png");
        }
    }

    public void takeScreenshot(String fileName) throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File target = new File("screenshots/" + fileName);

        // Make sure directory exists
        target.getParentFile().mkdirs();

        FileUtils.copyFile(screenshot, target);
        System.out.println(" Screenshot saved at: " + target.getAbsolutePath());
    }
}
