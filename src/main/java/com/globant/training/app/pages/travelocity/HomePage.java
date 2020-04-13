package com.globant.training.app.pages.travelocity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.globant.training.app.pages.BasePage;

/**
 * Home Page.
 * 
 * @author sebastian.rubio
 *
 */

public class HomePage extends BasePage {

	private final String FLIGHT_TAB = "tab-flight-tab-hp";
	private final String PACKAGES_TAB = "tab-package-tab-hp";
	private final String HOTEL_TAB = "tab-hotel-tab-hp";
	private final String CRUISE_TAB = "tab-cruise-tab-hp";
	
	@FindBy(id = FLIGHT_TAB)
	private WebElement flightTab;

	@FindBy(id = PACKAGES_TAB)
	private WebElement packagesTab;

	@FindBy(id = HOTEL_TAB)
	private WebElement hotelTab;

	@FindBy(id = CRUISE_TAB)
	private WebElement cruiseTab;

	public HomePage(WebDriver pDriver, String url) {
		super(pDriver);
		setLoggerInfo("Going to travelocity index page");
		getDriver().get(url);
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: select Flight tab option
	 * @return HomePageFlight
	 */
	public HomePageFlight setFlightTab() {
		click(flightTab);
		//flightTab.click();
		return new HomePageFlight(getDriver());
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: select Hotel tab option
	 * @return HomePageHotel
	 */
	public HomePageHotel setHotelTab() {
		click(hotelTab);
		//hotelTab.click();
		return new HomePageHotel(getDriver());
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: select Package tab option
	 * @return HomePagePackage
	 */
	public HomePagePackage setPackageTab() {
		click(packagesTab);
		//packagesTab.click();
		return new HomePagePackage(getDriver());
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: select Cruises tab option
	 * @return HomePageCruise
	 */
	public HomePageCruise setCruisesTabSelect() {
		click(cruiseTab);
		//cruiseTab.click();
		return new HomePageCruise(getDriver());
	}


}
