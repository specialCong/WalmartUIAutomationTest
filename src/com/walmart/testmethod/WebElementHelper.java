package com.walmart.testmethod;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebElementHelper {

	WebDriver _driver;
	WebDriverWait _wait;

	public WebElementHelper(WebDriver driver, WebDriverWait wait) {
		_driver = driver;
		if (wait == null)
			_wait = new WebDriverWait(driver, 20);
		else
			_wait = wait;
		;
	}
	
	/**
	 * Ask the driver to wait
	 * @param driver
	 */
	public void waitForLoad() {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript(
						"return document.readyState").equals("complete");
			}
		};
		WebDriverWait waitDriver = new WebDriverWait(_driver, 30);
		waitDriver.until(pageLoadCondition);
	}

	WebElement waitAndgetWebElement(String xPath) {
		// SearchText
		waitForLoad();
		By bXPath = By.xpath(xPath);
		_wait.until(ExpectedConditions.visibilityOfElementLocated(bXPath));
		WebElement webElement = getWebElementFromDriver(bXPath);

		return webElement;
	}
	
	WebElement waitAndgetWebElementById(String id) {
		// SearchText
		waitForLoad();
		By bXPath = By.id(id);
		_wait.until(ExpectedConditions.visibilityOfElementLocated(bXPath));
		WebElement webElement = getWebElementFromDriver(bXPath);

		return webElement;
	}

	/**
	 * Get WebElement from the main driver
	 * 
	 * @param xPath
	 * @return
	 */
	WebElement getWebElementFromDriver(By xPath) {
		return _driver.findElement(xPath);
	}

	/**
	 * Get WebElement from the main driver
	 * 
	 * @param xPath
	 * @return the WebElement that was found
	 */
	WebElement getWebElementFromDriver(String xPath) {
		return _driver.findElement(By.xpath(xPath));
	}

	/**
	 * Get WebElement from the main driver
	 * 
	 * @param xPath
	 * @return the WebElement that was found
	 */
	WebElement getWebElementFromDriverById(String id) {
		return _driver.findElement(By.id(id));
	}

	/**
	 * Get WebElement from the element provided
	 * 
	 * @return
	 */
	WebElement getWebElement(WebElement element, String xPath) {
		if (element == null)
			return null;

		return element.findElement(By.xpath(xPath));
	}

	void getWebElementFromDriverAndClick(String xPath) {
        int tries = 0;
        while(tries < 2) {
            try {
            	WebElement elem = getWebElementFromDriver(xPath);
        		if (elem != null)
        			elem.click();
                break;
            } catch(StaleElementReferenceException e) {
            }
            tries++;
        }
	}

	void getWebElementFromDriverByIdAndClick(String id) {
	        int tries = 0;
	        while(tries < 2) {
	            try {
	            	WebElement elem = getWebElementFromDriverById(id);
	        		if (elem != null)
	        			elem.click();
	                break;
	            } catch(StaleElementReferenceException e) {
	            }
	            tries++;
	        }
	}

	public boolean isAttributePresent(WebElement element, String attribute) {
		Boolean flag = false;
		try {
			String value = element.getAttribute(attribute);
			if (value != null) {
				flag = true;
			}
		} catch (Exception e) {
		}

		return flag;
	}

}
