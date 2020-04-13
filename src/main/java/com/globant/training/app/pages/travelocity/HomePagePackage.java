package com.globant.training.app.pages.travelocity;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.globant.training.app.pages.BasePage;
import com.globant.training.app.pages.Utils;

/**
 * Home Page Package Tab.
 * 
 * @author sebastian.rubio
 *
 */

public class HomePagePackage extends BasePage {

	private final String FLIGHT_HOTEL_CAR_TAB = "[for='fhc-fhc-hp-package']";
	private final String DEPARTURE_PACKAGE = "package-origin-hp-package";
	private final String RETURN_PACKAGE = "package-destination-hp-package";
	private final String DEPARTURE_PACKAGE_DATE = "package-departing-hp-package";
	private final String RETURN_PACKAGE_DATE = "package-returning-hp-package";
	private final String SEARCH_PACKAGE_BTN = "search-button-hp-package";
	private final String NEXT_MONTH_BTN = "datepicker-next";
	private final String DATE_PICKER_DAY_13 = "//div[@class='datepicker-cal-month'][2]//tr[3]//td[@class='datepicker-day-number notranslate'][7]";
	private final String ADULTS_NUMBER = "package-1-adults-hp-package";
	private final String FLIGHT_HOTEL_BTN = "[class='check col gcw-option'][for='fh-fh-hp-package']";
	private final String PARTIAL_STAY_CHECKBOX = "partialHotelBooking-hp-package";
	private final String CHECK_IN_DATE = "package-checkin-hp-package";
	private final String CHECK_OUT_DATE = "package-checkout-hp-package";
	private final String ERROR_LINK = "error-link";
	private Utils util = new Utils(getDriver());

	@FindBy(css = FLIGHT_HOTEL_CAR_TAB)
	private WebElement flightHotelCarTab;

	@FindBy(id = DEPARTURE_PACKAGE)
	private WebElement departurePackageInput;

	@FindBy(className = NEXT_MONTH_BTN)
	private WebElement nextMonthBtn;

	@FindBy(xpath = DATE_PICKER_DAY_13)
	private WebElement datePickerDay13;

	@FindBy(id = RETURN_PACKAGE)
	private WebElement returnPackageInput;

	@FindBy(id = SEARCH_PACKAGE_BTN)
	private WebElement searchButton;

	@FindBy(id = DEPARTURE_PACKAGE_DATE)
	private WebElement departurePackageDate;

	@FindBy(id = RETURN_PACKAGE_DATE)
	private WebElement returnPackageDate;

	@FindBy(id = ADULTS_NUMBER)
	private WebElement adultsNumber;

	@FindBy(css = FLIGHT_HOTEL_BTN)
	private WebElement flightHotelTab;

	@FindBy(id = PARTIAL_STAY_CHECKBOX)
	private WebElement partialStayCheckbox;

	@FindBy(id = CHECK_IN_DATE)
	private WebElement checkinDate;

	@FindBy(id = CHECK_OUT_DATE)
	private WebElement checkoutDate;

	@FindBy(className = ERROR_LINK)
	private WebElement errorLink;

	public HomePagePackage(WebDriver pDriver) {
		super(pDriver);
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: select flight + hotel + car option
	 */
	public void setFlightHotelAndCarTab() {
		flightHotelCarTab.click();
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: select flight + hotel option
	 */
	public void setFlightHotelTab() {
		flightHotelTab.click();
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: Set departure Info to start the package reservation
	 * @param departureFlight : String
	 * @param returnFlight    : String
	 * @param adultsNumber    : Integer
	 */
	public void setPackageInfo(String departureFlight, String returnFlight, int adultsNumber) {
		util.setInputString(departurePackageInput, departureFlight);
		util.setInputString(returnPackageInput, returnFlight);
		showPackageCalendar(departurePackageDate, 1);
		util.clickDayPickerDate();
		showPackageCalendar(returnPackageDate);
		datePickerDay13.click();
		setNumberOfAdults(adultsNumber);
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: Actions to get ready to select a date on the calendar picker
	 * @param element : WebElement
	 */
	public void showPackageCalendar(WebElement element) {
		element.click();
		util.waitUntilDropdonwn();
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: Actions to get ready to select a date on the calendar picker
	 * @param element           : WebElement
	 * @param nextMonthBtnTimes : Integer
	 */
	public void showPackageCalendar(WebElement element, int nextMonthBtnTimes) {
		element.click();
		util.waitUntilDropdonwn();
		for (int i = 0; i < nextMonthBtnTimes; i++) {
			nextMonthBtn.click();
		}
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: click on the search button to go to select an hotel
	 * @return HotelSearchPage
	 */
	public HotelSearchPage setSearchBtn() {
		searchButton.click();
		return new HotelSearchPage(this.getDriver());
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: set number of adults
	 * @param adults : Integer
	 */
	public void setNumberOfAdults(Integer adults) {
		adultsNumber.click();
		adultsNumber.sendKeys(adults.toString());
	}

	public void setPartialStayCheckbox() {
		partialStayCheckbox.click();
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: Set check in date 8 months forward from the current date
	 */

	public void setCheckinDate() {
		util.setDateForward(checkinDate, 8);
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: Set check out date 9 months forward from the current date
	 */

	public void setCheckoutDate() {
		util.setDateForward(checkoutDate, 9);
	}

	public String getMismatchDatesErrorMessage() {
		getWait().until(ExpectedConditions.visibilityOf(errorLink));
		return errorLink.getText();
	}

}
