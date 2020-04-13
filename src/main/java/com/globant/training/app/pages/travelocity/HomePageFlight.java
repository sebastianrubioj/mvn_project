package com.globant.training.app.pages.travelocity;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.globant.training.app.pages.BasePage;
import com.globant.training.app.pages.Utils;

/**
 * Home Page Flight Tab.
 * 
 * @author sebastian.rubio
 *
 */

public class HomePageFlight extends BasePage {

	private final String DEPARTURE = "flight-origin-hp-flight";
	private final String RETURN = "flight-destination-hp-flight";
	private final String DEPARTURE_DATE = "flight-departing-hp-flight";
	private final String RETURN_DATE = "flight-returning-hp-flight";
	private final String NEXT_MONTH_BTN = "datepicker-next";
	private final String SEARCH_FLIGHT = "[data-gcw-key='hp-flight'] [data-gcw-change-submit-text='Search']";
	private Utils util = new Utils(getDriver());

	@FindBy(id = DEPARTURE)
	private WebElement departureInput;

	@FindBy(id = RETURN)
	private WebElement returnInput;

	@FindBy(id = DEPARTURE_DATE)
	private WebElement departureDateInput;

	@FindBy(id = RETURN_DATE)
	private WebElement returnDateInput;

	@FindBy(className = NEXT_MONTH_BTN)
	private WebElement nextMonthBtn;

	@FindBy(css = SEARCH_FLIGHT)
	private WebElement searchFlight;

	public HomePageFlight(WebDriver pDriver) {
		super(pDriver);
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: Set information to start the flight booking
	 * @param departureFlight : String
	 */
	public void setFlightInfo(String departureFlight, String returnFlight) {
		util.setInputString(departureInput, departureFlight);
		util.setInputString(returnInput, returnFlight);
		setDateMonthsForward(departureDateInput, 2);
		setDateMonthsForward(returnDateInput, 1);
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: Click on search button
	 * @return FlightSearchPage
	 */
	public FlightSearchPage setSearchFlight() {
		searchFlight.click();
		return new FlightSearchPage(this.getDriver());
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: Set a date in the calendar months in advance
	 * @param element           : WebElement
	 * @param nextMonthBtnTimes : Integer
	 */
	public void setDateMonthsForward(WebElement element, int nextMonthBtnTimes) {
		element.click();
		util.waitUntilDropdonwn();
		for (int i = 0; i < nextMonthBtnTimes; i++) {
			nextMonthBtn.click();
		}
		util.clickDayPickerDate();
	}
}
