package com.globant.training.app.pages.travelocity;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.globant.training.app.pages.BasePage;


/**
 * Flight Checkout Page.
 * 
 * @author sebastian.rubio
 *
 */

public class FlightCheckoutPage extends BasePage{

	private final String PRODUCT_SUMMARY = "product-summary";
	private final String FIRST_NAME_INPUT = "firstname[0]";
	private final String WHO_TRAVELS_MESSAGE = "[class='allTravelerDetails'] [class='faceoff-module-title']";
	private final String TERMS_AND_CONDITIONS_LINK = "terms-minimal-link";
	private final String COMPLETE_BOOKING_BTN = "complete-booking";
	private final String PRODUCTS_SELECTED_LIST = "product-content-title";	
	
	@FindAll({@FindBy(className= PRODUCT_SUMMARY)})
	private List<WebElement> productSummary;
	
	@FindBy(id= FIRST_NAME_INPUT)
	private WebElement firstNameInput;
	
	@FindBy(css= WHO_TRAVELS_MESSAGE)
	private WebElement whoTravelsMessage;
	
	@FindBy(id= TERMS_AND_CONDITIONS_LINK)
	private WebElement termsAndConditionsLink;
	
	@FindBy(id= COMPLETE_BOOKING_BTN)
	private WebElement completeBookingBtn;
	
	@FindAll({@FindBy(className= PRODUCTS_SELECTED_LIST)})
	private List<WebElement> productSelectedList;
	
	public FlightCheckoutPage(WebDriver pDriver) {
		super(pDriver);
		setLoggerInfo("Going to checkout Page");
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: return true if summary of flight products is present
	 * @return boolean
	 */
	
	public boolean isFlightProductSummaryPresent() {
		boolean productSummaryPresent = false;
		getWait().until(ExpectedConditions.visibilityOf(firstNameInput));
		getWait().until(ExpectedConditions.visibilityOf(productSummary.get(0)));
		
		if(productSummary.get(0).isDisplayed()) {
			productSummaryPresent = true;
		}
		
		return productSummaryPresent;
		
	}
	
	/**
	 * @author sebastian.rubio
	 *
	 * @description: return true if the text input for the first name is present
	 * @return boolean
	 */
	
	public boolean isFirstNameInputIsPresent() {
		boolean firstNameIsPresent = false;
		
		if(firstNameInput.isDisplayed()) {
			firstNameIsPresent = true;
		}
		
		return firstNameIsPresent;
		
	}
	
	/**
	 * @author sebastian.rubio
	 *
	 * @description: Get the 'Who Travels' message
	 * @return String
	 */
	
	public String getWhoTravelsMessage() {
		String whoTravelMessage = whoTravelsMessage.getText();
		return whoTravelMessage;
		
	}
	
	/**
	 * @author sebastian.rubio
	 *
	 * @description: return true if the "terms and conditions" text is present
	 * @return boolean
	 */
	
	public boolean isTermsAndConditionsPresent() {
		boolean termsAndConditionsIsPresent = false;
		
		if(termsAndConditionsLink.isDisplayed()) {
			termsAndConditionsIsPresent = true;
		}
		return termsAndConditionsIsPresent;
		
	}
	
	/**
	 * @author sebastian.rubio
	 *
	 * @description: return true if the "complete booking" button is present
	 * @return boolean
	 */
	
	public boolean isCompleteBookingBtnPresent() {
		boolean completeBookingButtonIsPresent = false;
		
		if (completeBookingBtn.isDisplayed()) {
			completeBookingButtonIsPresent = true;
		}
		
		return completeBookingButtonIsPresent;
	}
	
	/**
	 * @author sebastian.rubio
	 *
	 * @description: return true if the summary of hotel products is present
	 * @return boolean
	 */
	
	public boolean isHotelProductSummaryPresent() {
		boolean productSummaryPresent = false;
		getWait().until(ExpectedConditions.visibilityOf(productSummary.get(1)));
		
		if(productSummary.get(1).isDisplayed()) {
			productSummaryPresent = true;
		}
		
		return productSummaryPresent;
		
	}
	
	/**
	 * @author sebastian.rubio
	 *
	 * @description: return true if the summary of car products is present
	 * @return boolean
	 */
	
	public boolean isCarProductSummaryPresent() {
		boolean productSummaryPresent = false;
		getWait().until(ExpectedConditions.visibilityOf(productSummary.get(2)));
		
		if(productSummary.get(2).isDisplayed()) {
			productSummaryPresent = true;
		}
		
		return productSummaryPresent;
		
	}
	
	public String getHotelNameSelected() {
		return productSelectedList.get(1).getText();
	}
	
	public String getCarTypeSelected() {
		return productSelectedList.get(2).getText();
	}
	
}
