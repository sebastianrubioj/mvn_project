package com.globant.training.app.pages.travelocity;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.globant.training.app.pages.BasePage;
import com.globant.training.app.pages.Utils;

/**
 * Home Page Cruise Tab.
 * 
 * @author sebastian.rubio
 *
 */

public class HomePageCruise extends BasePage {

	private final String DESTINATION_CRUISES_DROPDOWN = "cruise-destination-hp-cruise";
	private final String CRUISE_START_DATE = "cruise-start-date-hp-cruise";
	private final String SEARCH_CRUISE_BTN = "[data-gcw-key='hp-cruise'] .btn-primary";
	private final String ADULTS_NUMBER_CRUISE = "cruise-adults-hp-cruise";
	private Utils util = new Utils(getDriver());

	@FindBy(id = DESTINATION_CRUISES_DROPDOWN)
	private WebElement destinationCruisesDropdown;

	@FindBy(id = CRUISE_START_DATE)
	private WebElement cruiseStartDate;

	@FindBy(css = SEARCH_CRUISE_BTN)
	private WebElement searchCruiseBtn;

	@FindBy(id = ADULTS_NUMBER_CRUISE)
	private WebElement adultsNumberCruise;

	public HomePageCruise(WebDriver pDriver) {
		super(pDriver);
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: Set cruise destination
	 * @param destination : String
	 */

	public void setCruisesDestination(String destination) {
		click(destinationCruisesDropdown);
		destinationCruisesDropdown.sendKeys(destination);
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: Set the date to start the cruise allowing to select an amount
	 *               of months forward from the current date.
	 * @param monthsForward : Integer
	 */

	public void setStartCruiseDate(int monthsForward) {
		util.setDateForward(cruiseStartDate, monthsForward);
	}

	public void SetAdultsCruiseNumber(int adultsNumber) {
		getWait().until(ExpectedConditions.elementToBeClickable(adultsNumberCruise));
		adultsNumberCruise.click();
		String adults = Integer.toString(adultsNumber);
		adultsNumberCruise.sendKeys(adults);
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: click on the search cruise button
	 * @return CruiseSearchPage
	 */

	public CruiseSearchPage setSearchCruises() {
		searchCruiseBtn.click();
		return new CruiseSearchPage(getDriver());
	}

}
