package com.automation.test;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.automation.base.BaseClass;
import com.automation.base.HomePage;


public class HomePage_and_Category extends BaseClass {
	
	WebDriver driver;
    HomePage homePage;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://automationteststore.com/");
        homePage = new HomePage(driver);
    }

    @Test
    public void testRandomCategoryNavigation() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul[@class='nav-pills categorymenu']/li/a[not(contains(@class, 'menu_home'))]")));

        List<WebElement> categories = homePage.getAllCategories();
        System.out.println("Total main categories found: " + categories.size());
        for (WebElement cat : categories) {
            System.out.println("Category Name: " + cat.getText());
        }

        Assert.assertTrue(categories.size() > 0, "No categories found on homepage");

        // Select random category and hover + click
        Random random = new Random();
        WebElement randomCategory = categories.get(random.nextInt(categories.size()));
        String selectedCategory = randomCategory.getText().trim();
        System.out.println("Navigating to random category: " + selectedCategory);

        Actions actions = new Actions(driver);
        actions.moveToElement(randomCategory).pause(Duration.ofSeconds(1)).click().perform();

        // Wait and validate products
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".thumbnail")));
        List<WebElement> products = driver.findElements(By.cssSelector(".thumbnail"));

        long visibleCount = products.stream().filter(WebElement::isDisplayed).count();
        System.out.println("Visible products in '" + selectedCategory + "': " + visibleCount);

        Assert.assertTrue(visibleCount >= 3, "Less than 3 visible products found in category: " + selectedCategory);
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
