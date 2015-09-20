package com.walmart.testcase;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import com.walmart.testmethod.*;

public class NewTestForWindows {

	private final String PATH_TO_DRIVER = "/chrome_driver/chromedriver.exe";
	private final String HOMEPAGE_URL = "http://www.walmart.com";
	private final String LOGIN_USERNAME = "leotang.walmart.test@gmail.com";
	private final String LOGIN_PASSWORD = "walmarttest";

	private WebPageTestMethod method;
	private WebDriver driver;

	@BeforeTest
	public void setUp() throws Exception {
		// Set up the path of web driver
		System.setProperty("webdriver.chrome.driver", PATH_TO_DRIVER);
		method = new WebPageTestMethod();
		driver = method.getWebDriver();
		// direct to www.walmart.com
		driver.get(HOMEPAGE_URL);
	}

	@DataProvider(name = "Search keywords in pool")
	public Object[][] getData(Method M) {
		if (M.getName().equalsIgnoreCase("addItemToCart")) {
			return new Object[][] { { "tv" }, { "socks" }, { "dvd" },
					{ "toys" }, { "iPhone" } };
		}
		return null;
	}

	@Test(description = "sgin in, search, add to cart and validate", dataProvider = "Search keywords in pool")
	public void addItemToCart(String searchText) throws InterruptedException {
		// Search item, select, add to cart and save the product ID of the
		// item
		method.clickSignInInNavigation();
		method.enterSignInAccountAndPassword(LOGIN_USERNAME, LOGIN_PASSWORD);
		method.clickSignInBTN();

		// Search Text and Click on Search button
		method.enterSearchText(searchText);
		method.clickSearchButton();

		// Store the product ID of the item added to the cart
		method.selectOneItemFromTheSearchList(searchText);
		String itemIDAddedToCart = method.saveItemProductDetailsBeforeAddCart();

		// Add to cart
		method.clickAddToCart();

		// click view cart
		method.clickViewCart();

		// validate item
		method.validateItemsIdInCart(itemIDAddedToCart);
		method.validateItemsNumInCart(itemIDAddedToCart);

		// Clean up : remove item from cart and sign out
		method.removeItemFromCart();
		method.signOut();

	}

	@AfterTest
	public void tearDown() throws Exception {
		driver.quit();
	}
}
