package com.automation.test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import com.automation.base.BaseClass;
import com.automation.base.HomePage;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;

public class CartTest extends BaseClass{
	
	 @BeforeClass
	    public void setup() {
	    driver = new ChromeDriver();
	    driver.manage().window().maximize();
	    driver.get("https://automationteststore.com/");
	 }
	
	 @Test
	    public void product() throws InterruptedException, IOException {
	        JavascriptExecutor js = (JavascriptExecutor) driver;

	        // Select random category
	        List<WebElement> prod = driver.findElements(By.xpath("//ul[@class='nav-pills categorymenu']/li/a[not(contains(@class, 'menu_home'))]"));
	        WebElement randomCategory = prod.get(1); 
	        randomCategory.click();
	        js.executeScript("window.scrollBy(0,500)");

	        // Get product list
	        List<WebElement> subProd = driver.findElements(By.xpath("//div[@class='thumbnails grid row list-inline']//div[@class='thumbnail']/a"));

	        Random random = new Random();
	        int firstIndex = random.nextInt(subProd.size());
	        int secondIndex;
	        do {
	            secondIndex = random.nextInt(subProd.size());
	        } while (secondIndex == firstIndex);

	        // FileWriter to log issues
	        FileWriter report = new FileWriter("report.txt", true);

	        // Click & validate first product
	        WebElement firstProduct = subProd.get(firstIndex);
	        firstProduct.click();
	        System.out.println("Clicked first product: " + driver.getTitle());
	        js.executeScript("window.scrollBy(0,500)");

	        handleProductAddToCart(report);

	        // Navigate back and re-fetch product list
	        driver.navigate().back();
	        Thread.sleep(2000);
	        subProd = driver.findElements(By.xpath("//div[@class='thumbnails grid row list-inline']//div[@class='thumbnail']/a"));

	        // Click & validate second product
	        WebElement secondProduct = subProd.get(secondIndex);
	        secondProduct.click();
	        System.out.println("Clicked second product: " + driver.getTitle());
	        js.executeScript("window.scrollBy(0,500)");

	        handleProductAddToCart(report);

	        report.close();
	    }

	    // Helper method to validate Add to Cart logic
	 private void handleProductAddToCart(FileWriter report) throws IOException {
		    // Check for out of stock
		    List<WebElement> outOfStock = driver.findElements(By.xpath("//span[contains(@class,'nostock')]"));
		    if (!outOfStock.isEmpty()) {
		        report.write(" OUT OF STOCK: " + driver.getTitle() + " | URL: " + driver.getCurrentUrl() + "\n");
		        return;
		    }

		    // Check for Add to Cart button
		    List<WebElement> addToCart = driver.findElements(By.xpath("//a[@class='cart'] | //a[@title='Add to Cart']"));
		    
		    if (!addToCart.isEmpty() && addToCart.get(0).isDisplayed()) {
		        addToCart.get(0).click();
		        System.out.println(" Added to cart: " + driver.getTitle());

		        WebElement cartTable = new WebDriverWait(driver, Duration.ofSeconds(10))
		                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'cart-info')]//table")));

		        WebElement productRow = cartTable.findElement(By.xpath(".//tr[2]"));

		        String productName = productRow.findElement(By.xpath(".//td[2]/a")).getText();
		        String quantity = productRow.findElement(By.xpath(".//td[5]//input")).getAttribute("value");
		        String unitPrice = productRow.findElement(By.xpath(".//td[4]")).getText();
		        String productUrl = productRow.findElement(By.xpath(".//td[2]/a")).getAttribute("href");

		        System.out.println("  Product Added:");
		        System.out.println("   Name     : " + productName);
		        System.out.println("   Quantity : " + quantity);
		        System.out.println("   Price    : " + unitPrice);
		        System.out.println("   URL      : " + productUrl);

		        report.write("  ADDED TO CART:\n");
		        report.write("   Name     : " + productName + "\n");
		        report.write("   Quantity : " + quantity + "\n");
		        report.write("   Price    : " + unitPrice + "\n");
		        report.write("   URL      : " + productUrl + "\n\n");

		        driver.navigate().back(); // go back after add to cart
		    } else {
		        // Only report if button element truly not present
		        report.write(" Add to Cart button NOT FOUND: " + driver.getTitle() + " | URL: " + driver.getCurrentUrl() + "\n");
		    }
		}
}