package com.automation.test;

import com.automation.base.BaseClass;
import com.automation.listeners.TestUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.*;

public class CheckoutTest1 extends BaseClass {

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://automationteststore.com/");
    }

    @Test
    public void product() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //Add First Product
        addProductToCart(wait, js);

        //Click Continue Shopping
        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[normalize-space()='Continue Shopping']")));
        continueBtn.click();

        //Add Second Product
        addProductToCart(wait, js);

        // Open Cart
        WebElement cartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a.top.nobackground[href*='checkout/cart']")));
        cartBtn.click();

        // Validate 2 Cart Items
        WebElement cartTable = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'cart-info')]//table")));

        List<WebElement> cartRows = cartTable.findElements(By.xpath(".//tr[position()>1]"));
        if (cartRows.size() != 2) {
            String msg = "Cart doesn't contain exactly 2 items. Found: " + cartRows.size();
            TestUtils.logToReport(msg);
            throw new IllegalStateException(msg);
        }

        double expectedTotal = 0;

        for (WebElement row : cartRows) {
            String name = row.findElement(By.xpath(".//td[2]/a")).getText();
            String qty = row.findElement(By.xpath(".//td[5]//input")).getAttribute("value");
            String unit = row.findElement(By.xpath(".//td[4]"))
                    .getText().replace("$", "").replace(",", "").trim();
            String total = row.findElement(By.xpath(".//td[6]"))
                    .getText().replace("$", "").replace(",", "").trim();

            int quantity = Integer.parseInt(qty);
            double unitPrice = Double.parseDouble(unit);
            double totalPrice = Double.parseDouble(total);

            System.out.println( name + " | Qty: " + qty + " | Unit: $" + unit + " | Total: $" + total);
            TestUtils.logToReport(name + " | Qty: " + qty + " | Unit: $" + unit + " | Total: $" + total);

            if (totalPrice != unitPrice * quantity) {
                TestUtils.logToReport("Mismatch in total for: " + name);
            }

            expectedTotal += totalPrice;
        }

        // Validate Subtotal
        WebElement subtotalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//table[@class='totals']//span[contains(@class,'cart_block_total')]")));

        String subtotalText = subtotalElement.getText().replace("$", "").replace(",", "").trim();
        double displayedSubtotal = Double.parseDouble(subtotalText);

        if (expectedTotal == displayedSubtotal) {
            TestUtils.logToReport("Subtotal matches. Total: $" + expectedTotal);
            System.out.println("Subtotal and all products validated successfully.");
        } else {
            TestUtils.logToReport(" Subtotal mismatch. Expected: $" + expectedTotal + ", Found: $" + displayedSubtotal);
        }
    }

    private void addProductToCart(WebDriverWait wait, JavascriptExecutor js) {
        try {
            List<WebElement> categories = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//ul[@class='nav-pills categorymenu']/li/a[not(contains(@class, 'menu_home'))]")));
            categories.get(new Random().nextInt(categories.size())).click();

            js.executeScript("window.scrollBy(0,600)");

            List<WebElement> allProducts = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//div[@class='thumbnails grid row list-inline']//div[@class='thumbnail']")));

            boolean added = false;
            for (WebElement product : allProducts) {
                try {
                    if (!product.findElements(By.xpath(".//span[contains(@class,'nostock')]"))
                            .isEmpty()) continue;

                    WebElement nameLink = product.findElement(By.xpath(".//a[@class='prdocutname']"));
                    String name = nameLink.getText();
                    nameLink.click();

                    js.executeScript("window.scrollBy(0,500)");

                    List<WebElement> addToCart = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.xpath("//a[@class='cart'] | //a[@title='Add to Cart']")));

                    if (!addToCart.isEmpty()) {
                        addToCart.get(0).click();
                        System.out.println("Added to cart: " + name);
                        TestUtils.logToReport(" Added to cart: " + name);
                        added = true;
                    }
                    break;
                } catch (StaleElementReferenceException e) {
                    continue;
                }
            }

            if (!added) {
                String msg = " No available product found to add.";
                TestUtils.logToReport(msg);
                throw new RuntimeException(msg);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            TestUtils.logToReport(" Failed to add product to cart.");
            throw new RuntimeException("s Failed to add product to cart.");
        }
    }
}