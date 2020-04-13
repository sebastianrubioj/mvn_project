package com.globant.training.app.pages.travelocity;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.globant.training.app.pages.BasePage;
import com.globant.training.app.pages.Utils;

/**
 * Flight Information Page.
 * 
 * @author sebastian.rubio
 *
 */

public class FlightInformationPage extends BasePage {

	private final String TOTAL_PRICE = ".desktopView .packagePriceTotal";
	private final String FLIGHT_INFO = "flightSummary";
	private final String PRICE_GUARANTEE = ".desktopView .priceGuarantee";
	private final String CONTINUE_BOOKING_BTN = "bookButton";
	private Utils util = new Utils(getDriver());

	@FindBy(css = TOTAL_PRICE)
	private WebElement totalPrice;

	@FindBy(className = FLIGHT_INFO)
	private WebElement flightInfo;

	@FindBy(css = PRICE_GUARANTEE)
	private WebElement priceGuarantee;

	@FindBy(id = CONTINUE_BOOKING_BTN)
	private WebElement continueBookingBtn;

	public FlightInformationPage(WebDriver pDriver) {
		super(pDriver);
		setLoggerInfo("Going to flight information page");
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: return true if the total price is present
	 * @return boolean
	 */

	public boolean getTotalPricePresent() {
		util.chageOfTab();
		getWait().until(ExpectedConditions.visibilityOf(totalPrice));
		boolean pricePresent = false;
		if (totalPrice.isDisplayed() && totalPrice.getText() != null) {
			pricePresent = true;
			setLoggerInfo("the total price of the flight is: " + totalPrice.getText());
		}
		return pricePresent;
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: return true if the flight info is present
	 * @return boolean
	 */

	public boolean getFlightInfoIsPresent() {
		boolean infoPresent = false;
		if (flightInfo.isDisplayed()) {
			infoPresent = true;
		}
		return infoPresent;
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: return true if the "flight guarantee" text is present
	 * @return boolean
	 */

	public boolean getFlightGuaranteeTextPresent() {
		boolean flightGuaranteePresent = false;

		if (priceGuarantee.isDisplayed() && !priceGuarantee.getText().isEmpty()) {
			flightGuaranteePresent = true;
		}

		return flightGuaranteePresent;
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: Click to continue the booking
	 * @return FlightCheckoutPage
	 */

	public FlightCheckoutPage setContinueBooking() {
		continueBookingBtn.click();
		return new FlightCheckoutPage(this.getDriver());
	}

}
