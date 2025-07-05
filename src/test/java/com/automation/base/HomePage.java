package com.automation.base;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
	
	WebDriver driver;
	
	By categoryLinks = By.xpath("//ul[@class='nav-pills categorymenu']/li/a[not(contains(@class, 'menu_home'))]");
	
	public HomePage(WebDriver driver){
		this.driver = driver;
	}
	
	public List<WebElement> getAllCategories() {
        return driver.findElements(categoryLinks);
    }

}
