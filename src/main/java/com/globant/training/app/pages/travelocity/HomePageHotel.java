package com.globant.training.app.pages.travelocity;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.globant.training.app.pages.BasePage;
import com.globant.training.app.pages.Utils;

/**
 * Home Page Hotel Tab.
 * 
 * @author sebastian.rubio
 *
 */

public class HomePageHotel extends BasePage {

	private final String HOTEL_DESTINATION_INPUT = "hotel-destination-hp-hotel";
	private final String HOTEL_SEARCH_BTN = "[data-gcw-datapreloading-function='hotels'] [data-gcw-change-submit-text='Search']";
	private final String HOTEL_CHECKIN_DATE = "hotel-checkin-hp-hotel";
	private Utils util = new Utils(getDriver());

	@FindBy(id = HOTEL_DESTINATION_INPUT)
	private WebElement hotelDestinationInput;

	@FindBy(css = HOTEL_SEARCH_BTN)
	private WebElement hotelSearchBtn;

	@FindBy(id = HOTEL_CHECKIN_DATE)
	private WebElement hotelCheckinDate;

	public HomePageHotel(WebDriver pDriver) {
		super(pDriver);
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: Set hotel destination
	 * @param destination : String
	 */

	public void setHotelDestination(String destination) {
		util.setInputString(hotelDestinationInput, destination);
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: get hotel destination
	 * @return String
	 */

	public String getHotelDestination() {
		getWait().until(ExpectedConditions.visibilityOf(hotelDestinationInput));
		getWait().until(ExpectedConditions.elementToBeClickable(hotelDestinationInput));
		return hotelDestinationInput.getText();
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: click on the search button to go to select an hotel
	 * @return HotelSearchPage
	 */
	public HotelSearchPage setSearchHotel() {
		click(hotelSearchBtn);
		return new HotelSearchPage(this.getDriver());
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description:
	 * @return HotelSearchPage
	 */
	public void setHotelCheckinDate() {
		hotelCheckinDate.click();
		util.waitUntilDropdonwn();
		util.clickDayPickerDate();
	}

}
