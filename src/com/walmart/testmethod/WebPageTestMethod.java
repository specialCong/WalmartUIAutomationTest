package com.walmart.testmethod;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

//import com.walmart.test.UI.Action.testAction;

public class WebPageTestMethod {
	
	private final Logger _log = Logger
			.getLogger(WebPageTestMethod.class.getName());
	
	// path to driver
	private final String PATH_TO_DRIVER = "/chrome_driver/chromedriver.exe";

	private final String HOMEPAGE_URL = "http://www.walmart.com";
	private final String LOGOUT_URL = "https://www.walmart.com/account/logout";
	private final String PRODUCT_ID = "data-product-id";
	
	//ADD_TO_CART button ID and xpath
	
	private final String ADD_TO_CART_BTN_XPATH = "//*[@id='WMItemAddToCartBtn']";
	
	
	//xpath for remove cart item
	private final String CART_ITEM_REMOVE_BTN_ID = "CartRemItemBtn";

	//xpath for pop up window
	private final String POP_UP_WINDOW = "id('spa-layout')/div/div/div/button";
	private final String CHECK_ORDER_BTN_XPATH = "/html/body/div[1]/section/section[4]/div/div/div/div/div[2]/div/div[2]/a";
	private final String VIEW_CART_BTN = "//*[@id='PACViewCartBtn']";
	
	//xpath for search
	private final String SEARCH_TEXT_BAR_XPATH = "id('top')/div[3]/div/div/div/div/div[3]/form/div/div[2]/span/input";
	private final String SEARCH_BTN_XPATH = "id('top')/div[3]/div/div/div/div/div[3]/form/div/div[3]/button";
	private final String ITEMS_VIEW_URL = "//div[1]/section/section[4]/div/div/div[3]/div[1]/div[1]/a";
	
	//xpath for sign in
	private final String SIGN_IN_NAVG_XPATH = "//*[@id='top']/div[3]/div/div/div/div/div[4]/div/div[1]/div[1]/p/span[2]/a";
	private final String SIGN_IN_BTN_XPATH = "/html/body/div[1]/section/section[4]/div/div/div/div/div/div/div/form/div/button";
//	private final String SIGN_IN_NAVG_XPATH = "//id('top')/div[3]/div/div/div/div/div[4]/div/div[1]/div[1]/p/span[1]";

	private final String ITEM_LIST_XPATH = "//div[1]/section/section[4]/div/div/div[3]/div[1]/div[1]";
	
	private final String CLICK_ON_CART_XPATH = "//*[@id='top']/div[3]/div/div/div/div/div[4]/div/div[2]/div/a";
	//xpath for cart
//	private final String XPATH_CART_PRESENT="//h3[contains(.,\"Your cart:\")]/span";
//	private final String ITEM_ADDED_TO_CART_ITEM_ID_XPATH = "//a[@id=\"CartItemInfo\"]";
	private final String CART_ITEM_INFO_XPATH = "//*[@id='spa-layout']/div/div/div[1]/div/div[4]/div[2]/div";
//	private final String NUM_OF_ITEMS_IN_CART = "id('top')/div[3]/div/div/div/div/div[4]/div/div[2]/div/a/b";
	
	// Timer to wait on elements to appear to be visible
	public WebDriver _driver;
	public WebDriverWait _wait;
	public WebElementHelper _elemHelper;
	
	


	public void init() {
		// Set up the path of web driver
		System.setProperty("webdriver.chrome.driver", PATH_TO_DRIVER);
		_driver = new ChromeDriver();
		_wait = new WebDriverWait(_driver, 30);
		_elemHelper = new WebElementHelper(_driver, null);
		//test = new testAction();
		// direct to www.walmart.com
		_driver.get(HOMEPAGE_URL);
		_log.log(Level.FINE, "Direct to walmart homepage");

	}


	/**
	 * Sign in the user
	 * 
	 * @param accountId
	 * @param password
	 */
	public void signIn(String username, String password) {

		_elemHelper.getWebElementFromDriverAndClick(SIGN_IN_NAVG_XPATH);

		WebElement usernameTextField = _elemHelper
				.waitAndgetWebElementById("login-username");
		if (usernameTextField != null) {
			usernameTextField.clear();
			usernameTextField.sendKeys(username);
		}
		WebElement passwordTextField = _driver.findElement(By
				.id("login-password"));
		if (passwordTextField != null)
			passwordTextField.sendKeys(password);
		_log.log(Level.FINE, "Enter the username and password");
	}

	/**
	 * Click on the sign in button
	 */
	public void clickSignInBTN() {
		_elemHelper.getWebElementFromDriverAndClick(SIGN_IN_BTN_XPATH);
		_log.log(Level.FINE, "Waiting for the sign in...");
	}

	/**
	 * type keyword in search bar
	 *
	 */
	public void searchText(String searchText) {
		// SearchText
		_wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(CHECK_ORDER_BTN_XPATH)));
		_elemHelper.waitForLoad();
		WebElement searchTextBar = _elemHelper
				.waitAndgetWebElement(SEARCH_TEXT_BAR_XPATH);
		if (searchTextBar != null) {
			searchTextBar.clear();
			searchTextBar.sendKeys(searchText);
			_log.log(Level.FINE, "Entering " + searchText
					+ " into the search bar");
		}	
	}

	/**
	 * Click on the search button
	 */
	public void clickSearchButton() {
		_elemHelper.getWebElementFromDriverAndClick(SEARCH_BTN_XPATH);
		_log.log(Level.FINE, "Waiting for search results ... ");
	}

	/**
	 * select on 1 item from the results
	 */
	public void selectOneItemFromTheSearchList(String searchText) {
		// until the refine bar has been present
		_elemHelper.waitForLoad();
		try {
			By specialCase = By.xpath(ITEM_LIST_XPATH);
			WebElement clickSeeItems = _driver.findElement(specialCase);
			if (clickSeeItems != null) {
				WebElement urlToSeeItemsContent = _driver.findElement(By
						.xpath(ITEMS_VIEW_URL));
				if (urlToSeeItemsContent != null) {
					urlToSeeItemsContent.click();
					_log.log(Level.FINE, "search in Department: " + searchText);
				}
			}
		} catch (Exception ex) {

		} finally {
			try {
				_wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.id("tile-container")));
				
				//select the first time in search list
				_elemHelper.waitForLoad();

				WebElement searchResultsElement = _driver.findElement(By
						.xpath("//*[@id='tile-container']/div[1]/div/div/h4/a"));
				if (searchResultsElement != null) {
					searchResultsElement.click();
					_log.log(Level.FINE, "Search result loaded.");
				}
			} catch (Exception ex) {
			}

		}
	}

	/**
	 * 
	 * @return
	 */
	public String saveItemProductDetailsBeforeAddCart() {
		// Store the name of the item that we will add to cart in the
		// variable "itemAddedToCart"
		_elemHelper.waitForLoad();
		String itemInfoBeforeAddingToCart = "//div[@class='js-reviews see-all-reviews']";
		WebElement webElement = _elemHelper
				.waitAndgetWebElement(itemInfoBeforeAddingToCart);
		
		Assert.assertTrue(webElement != null);

		boolean isProductIdPresent = _elemHelper.isAttributePresent(webElement,
				PRODUCT_ID);
		Assert.assertTrue(isProductIdPresent,
				"Product Id not found, so can't compare");
		String productIDAddedToCart = webElement
				.getAttribute(PRODUCT_ID);
		_log.log(Level.FINE, "Product ID saved");
		return productIDAddedToCart;
	}

	/**
	 * Click on Add_to_cart button
	 */
	public void clickAddToCart() {
		// Wait for "Add_to_Cart" button to be visible
		
		_elemHelper.waitForLoad();
		_wait.until(ExpectedConditions.elementToBeClickable(By
				.xpath(ADD_TO_CART_BTN_XPATH)));
		
		WebElement addToCart = _elemHelper
				.getWebElementFromDriver(ADD_TO_CART_BTN_XPATH);
		// click Add_to_cart button
		if(addToCart != null) {
			addToCart.click();
			_log.log(Level.FINE, "Add to cart clicked");
		}
	}
	
	/**
	 * Validate the ID and number of items in Cart
	 */
	public void validateItemsIdInCart(String itemIDAddedToCart) {
		// Login through Left nav Bar
		// Check the item in the chart and save the name in variable
		// itemInCart
		_elemHelper.waitForLoad();
		
		_wait.until(ExpectedConditions.elementToBeClickable(By
				.xpath(CART_ITEM_INFO_XPATH)));
		List<WebElement> cartDisplay = _driver.findElements(By
				.xpath(CART_ITEM_INFO_XPATH));

		WebElement cartItemElement = cartDisplay.get(0);
		Assert.assertTrue(cartItemElement != null);

		WebElement x = cartItemElement.findElement(By.id("CartItemInfo"));
		boolean isProductIdofItemInCart = _elemHelper.isAttributePresent(x,
				"data-us-item-id");
		Assert.assertTrue(isProductIdofItemInCart, 
				"ITEM NOT FOUND");

		String itemInCartID = x.getAttribute("data-us-item-id");

		// Assert if the item added to chart is same as the item selected
		Assert.assertEquals(itemInCartID, itemIDAddedToCart);
	}
	
	public void validateItemsNumInCart(String itemIDAddedToCart) {
		_elemHelper.waitForLoad();
		
		_wait.until(ExpectedConditions.elementToBeClickable(By
				.xpath(CART_ITEM_INFO_XPATH)));
		List<WebElement> cartDisplay = _driver.findElements(By
				.xpath(CART_ITEM_INFO_XPATH));
		Assert.assertTrue(cartDisplay != null && cartDisplay.size() == 1,
				"Multiple items in cart!");
	}
	
	/**
	 * Handle all cases until signing into the account
	 * 
	 * @param searchText
	 * @param accountId
	 * @param password
	 * @return
	 */
//	public String handleUntilLoginAndGetItemId(String searchText,
//			String username, String password) {
//
//		// Sign in
//		signIn(username, password);
//		clickSignInBTN();
//
//		// Search Text and Click on Search button
//		searchText(searchText);
//		clickSearchButton();
//
//		// Store the product ID of the item added to the cart
//		selectOneItemFromTheSearchList(searchText);
//		String itemIDAddedToCart = saveItemProductDetailsBeforeAddCart();
//		
//		// Add to cart
//		clickAddToCart();
//
//		//click view cart
//		viewCart();
//
//		// validateItemInCart();
//		// Product ID of the item added to cart
//		return itemIDAddedToCart;
//
//	}

	/**
	 * For special keys like "toys" it will handle a department selection
	 */
	public void HandleDepartmentKeyword() {
		try {
			By specialCase = By.xpath(ITEM_LIST_XPATH);
			WebElement clickSeeItems = _driver.findElement(specialCase);
			if (clickSeeItems != null) {
				WebElement urlToSeeItemsContent = _driver.findElement(By
						.xpath(ITEMS_VIEW_URL));
				if (urlToSeeItemsContent != null) {
					urlToSeeItemsContent.click();
				}
			}
		} catch (Exception ex) {
		}

	}

	/**
	 * Handle the pop up appears after adding item to cart
	 */
	public void viewCart() {
		_wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(POP_UP_WINDOW)));
		_driver.findElement(By.xpath(VIEW_CART_BTN)).click();
	}

	/**
	 * Click on cart
	 */
	public void clickOnCart() {
		_elemHelper.waitForLoad();
		_driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		_wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(CHECK_ORDER_BTN_XPATH)));
		WebElement clickOnCart = _driver.findElement(By
				.xpath(CLICK_ON_CART_XPATH));
		_driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		if (clickOnCart != null)
			clickOnCart.click();
	}

	/**
	 * Remove item from cart
	 */
	public void removeItemFromCart() {
		// remove the first item display in cart
		_elemHelper.waitForLoad();
		By removeButtonXpath = By.id(CART_ITEM_REMOVE_BTN_ID);
		_wait.until(ExpectedConditions.elementToBeClickable(_driver
				.findElement(removeButtonXpath)));
		WebElement removeButton = _driver.findElement(removeButtonXpath);
		if (removeButton != null)
			removeButton.click();

	}
	
	public void removeAllItemsIncart() {
		_elemHelper.waitForLoad();
		
		_wait.until(ExpectedConditions.elementToBeClickable(By
				.xpath(CART_ITEM_INFO_XPATH)));
		List<WebElement> cartDisplay = _driver.findElements(By
				.xpath(CART_ITEM_INFO_XPATH));
		Iterator<WebElement> iterator = cartDisplay.iterator();
		while(iterator.hasNext()) {
			iterator.remove();
			removeItemFromCart();
		}
	}

	/**
	 * Sign out
	 */
	public void signOut() {
		// Sign out at the end of each test
		_driver.get(LOGOUT_URL);

	}
	
	public WebDriver getWebDriver() {
		return _driver;
	}

	public void setWebDriver(WebDriver _driver) {
		this._driver = _driver;
	}
	
	public WebDriverWait getWebDriverWait() {
		return _wait;
	}

	public void setWebDriverWait(WebDriverWait _wait) {
		this._wait = _wait;
	}
	
	public WebElementHelper getWebElemHelper() {
		return _elemHelper;
	}
	
	public void setWebElemHelper(WebElementHelper _elemHelper) {
		this._elemHelper = _elemHelper;
	}
	
	public WebPageTestMethod() {
		init();
	}
}
