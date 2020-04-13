package com.globant.training.app.pages.travelocity;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.globant.training.app.pages.BasePage;
import com.globant.training.app.pages.Utils;

/**
 * Cruise Cabin Category Page.
 * 
 * @author sebastian.rubio
 *
 */

public class CruiseCabinCategoryPage extends BasePage {

	private final String TITLE_FARE_SELECTED = "[class='small-title trip-title']";
	private final String CRUISE_DATE_SELECTED = "departure-date";
	private final String DEPARTURE_CITY_SELECTED = "departure-port";
	
	@FindBy(css = TITLE_FARE_SELECTED)
	private WebElement titleFareSelected;

	@FindBy(className = CRUISE_DATE_SELECTED)
	private WebElement cruiseDateSelected;

	@FindBy(className = DEPARTURE_CITY_SELECTED)
	private WebElement departureCitySelected;

	protected CruiseCabinCategoryPage(WebDriver pDriver) {
		super(pDriver);
		setLoggerInfo("Going to Cabin Category page");
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: Get the title selected
	 * @return String
	 */

	public String getTitleSelected() {
		getWait().until(ExpectedConditions.visibilityOf(titleFareSelected));
		return titleFareSelected.getText();
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: Get the cruise date selected
	 * @return String
	 */

	public String getCruiseDateSelected() {
		return cruiseDateSelected.getText().replace("Departing on ", "");
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: Get the departure city selected
	 * @return String
	 */

	public String getDepartureCitySelected() {
		return departureCitySelected.getText();
	}

}
