package com.walmart.testmethod;

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
	
	/* modify PATH_TO_DRIVER right here */
	private final String PATH_TO_DRIVER = "/chrome_driver/chromedriver.exe";//this is for Windows
//	private final String PATH_TO_DRIVER = "/chrome_driver/chromedriver";	//this is for Mac

	private final String HOMEPAGE_URL = "http://www.walmart.com";
	private final String LOGOUT_URL = "https://www.walmart.com/account/logout";
	private final String PRODUCT_ID = "data-product-id";
	
	//ADD_TO_CART button ID and xpath
	
	private final String ADD_TO_CART_BTN_XPATH = "//*[@id='WMItemAddToCartBtn']";
	
	//xpath for remove cart item
	private final String CART_ITEM_REMOVE_BTN_ID = "CartRemItemBtn";

	//xpath for pop up window
//	private final String POP_UP_WINDOW = "id('spa-layout')/div/div/div/button";
	private final String CHECK_ORDER_BTN_XPATH = "/html/body/div[1]/section/section[4]/div/div/div/div/div[2]/div/div[2]/a";

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
	private final String VIEW_CART_BTN = "//*[@id='PACViewCartBtn']";
	private final String CART_ITEM_INFO_XPATH = "//*[@id='spa-layout']/div/div/div[1]/div/div[4]/div[2]/div";
	private final String PRODUCT_CONTROL_XPATH = "/html/body/div[1]/section/section[4]/div/div[2]/div[1]/div[4]/div[2]/div/div[2]/div/div[2]";
	
//	private final String ITEM_ADDED_TO_CART_ITEM_ID_XPATH = "//a[@id=\"CartItemInfo\"]";
	
	// Timer to wait on elements to appear to be visible
	public WebDriver _driver;
	public WebDriverWait _wait;
	public WebElementHelper _elemHelper;
	
	


	public void init() {
		// Set up the path of web driver
		System.setProperty("webdriver.chrome.driver", PATH_TO_DRIVER);
		_driver = new ChromeDriver();
		_wait = new WebDriverWait(_driver, 15);
		_elemHelper = new WebElementHelper(_driver, null);
		//test = new testAction();
		// direct to www.walmart.com
		_driver.get(HOMEPAGE_URL);
		_log.log(Level.FINE, "Direct to walmart homepage");

	}


	/**
	 * Click the sign in on right navigation
	 * 
	 */
	public void clickSignInInNavigation() {
		_wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(SIGN_IN_NAVG_XPATH)));
		_elemHelper.getWebElementFromDriverAndClick(SIGN_IN_NAVG_XPATH);
	}
	
	
	/**
	 * Sign in the user
	 * 
	 * @param accountId
	 * @param password
	 */
	public void enterSignInAccountAndPassword(String username, String password) {
		_wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id("login-username")));
		
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
		_elemHelper.waitForLoad();
		_wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(SIGN_IN_BTN_XPATH)));
		
		_elemHelper.getWebElementFromDriverAndClick(SIGN_IN_BTN_XPATH);
		_log.log(Level.FINE, "Waiting for the sign in...");
	}

	/**
	 * type keyword in search bar
	 *
	 */
	public void enterSearchText(String searchText) {
		// SearchText 
		_elemHelper.waitForLoad();
		_wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(CHECK_ORDER_BTN_XPATH)));

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
		_elemHelper.waitForLoad();
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
			/**
			 * For special keys like "toys" it will handle a department selection
			 */
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
	 * Save Item ID Before Add to Cart
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
		_wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(PRODUCT_CONTROL_XPATH)));
		
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
		_wait.until(ExpectedConditions.visibilityOfElementLocated(By
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
		_wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(CART_ITEM_INFO_XPATH)));
		
		List<WebElement> cartDisplay = _driver.findElements(By
				.xpath(CART_ITEM_INFO_XPATH));
		Assert.assertTrue(cartDisplay != null && cartDisplay.size() == 1,
				"Multiple items in cart!");
	}

	/**
	 * click view cart button after click add to cart
	 */
	public void clickViewCart() {
		_driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		_wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(VIEW_CART_BTN)));
		_elemHelper.getWebElementFromDriverAndClick(VIEW_CART_BTN);
	}

	/**
	 * Click on cart on right navigation
	 */
	public void clickOnCart() {
		_elemHelper.waitForLoad();
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
	
	/**
	 * Remove all items in cart
	 */
	public void removeAllItemsIncart() {
		_elemHelper.waitForLoad();
		_wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath(CART_ITEM_INFO_XPATH)));
		
		List<WebElement> cartDisplay = _driver.findElements(By
				.xpath(CART_ITEM_INFO_XPATH));
		int size = cartDisplay.size();
		while(cartDisplay != null && size != 0) {
			size--;
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
