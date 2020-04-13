package com.globant.training.app.pages.travelocity;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.globant.training.app.pages.BasePage;

/**
 * Flight Search Page.
 * 
 * @author sebastian.rubio
 *
 */

public class FlightSearchPage extends BasePage {

	private final String SORT_DROPDOWN_BOX = "sortDropdown";
	private final String FLIGHT_LIST = "li[class='flight-module segment offer-listing']";
	private final String FLIGHT_SELECT_BTN = "div[class='uitk-col standard-col-l-margin all-col-shrink display-larger-screens-only'] button";
	private final String FLIGHT_SEARCH_RESULT = "flightModuleList";
	private final String FLIGHT_DURATION = "duration-emphasis";
	private final String PROGRESS_BAR = "div[class='progress-bar'] div[style='width: 100%;']";
	private final String FLIGHT_DETAIL_FEES = "show-flight-details";
	private final String FLIGHT_TEXT = "title-city-text";
	private final String FARE_VERIFYCATION = "bCol";
	private final String SORT_OPTIONS = "select[id='sortDropdown'] option";

	private int buttonMissed;
	private int durationMissed;
	private int fareDetailMissed;

	@FindBy(id = SORT_DROPDOWN_BOX)
	private WebElement sortDropdownElement;

	@FindAll({ @FindBy(css = FLIGHT_LIST) })
	private List<WebElement> allFlightList;

	@FindAll({ @FindBy(css = FLIGHT_SELECT_BTN) })
	private List<WebElement> flightSelectBtn;

	@FindBy(id = FLIGHT_SEARCH_RESULT)
	private WebElement allFlights;

	@FindAll({ @FindBy(className = FLIGHT_DURATION) })
	private List<WebElement> FlightDuration;

	@FindBy(css = PROGRESS_BAR)
	private WebElement progressBar;

	@FindAll({ @FindBy(className = FLIGHT_DETAIL_FEES) })
	private List<WebElement> flightDetailsAndFees;

	@FindBy(className = FLIGHT_TEXT)
	private WebElement flightText;

	@FindBy(id = FARE_VERIFYCATION)
	private WebElement fareVerifycation;

	@FindAll({ @FindBy(className = SORT_OPTIONS) })
	private List<WebElement> sortOptionList;

	public FlightSearchPage(WebDriver pDriver) {
		super(pDriver);
		setLoggerInfo("Start flight Search Page");
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: return true if the sort drop down have all the options listed
	 * @return boolean
	 */

	public boolean areAllDropDownBoxOptions() {
		getWait().until(ExpectedConditions.invisibilityOf(progressBar));
		getWait().until(ExpectedConditions.elementToBeClickable(sortDropdownElement));
		boolean allSortOptionsPresent = false;

		if (sortDropdownElement.getText().contains("Price") && sortDropdownElement.getText().contains("Duration")
				&& sortDropdownElement.getText().contains("Departure")
				&& sortDropdownElement.getText().contains("Arrival")) {
			allSortOptionsPresent = true;
		}

		return allSortOptionsPresent;
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: return true if the amount of fares is the same than the amount
	 *               of Select buttons
	 * @return boolean
	 */

	public boolean isSelectBtnForAllFlights() {
		setLoggerInfo("Number of select Button: " + flightSelectBtn.size());
		return isPresentForAllFares(flightSelectBtn);
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: return true if the amount of fares is the same than the amount
	 *               of flight duration texts
	 * @return boolean
	 */

	public boolean isFlightDurationForAllFares() {
		setLoggerInfo("Number of Flight Duration info: " + FlightDuration.size());
		return isPresentForAllFares(FlightDuration);
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: return true if the amount of fares is the same than the amount
	 *               of flight details
	 * @return boolean
	 */

	public boolean isFlightDetailsForAllFares() {
		setLoggerInfo("Number of Detail and Fees: " + flightDetailsAndFees.size());
		setLoggerInfo("Number of fares: " + allFlightList.size());
		return isPresentForAllFares(flightDetailsAndFees);
	}

	public void setStoreByDuration() {
		sortDropdownElement.click();
		sortDropdownElement.sendKeys("Duration (Shortest)");
		sortDropdownElement.sendKeys(Keys.ENTER);
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: return true if the flights are correctly ordered by Duration
	 * @return boolean
	 */

	public boolean areFaresOrderByDuration() {

		boolean faresOrdered = true;

		getWait().until(
				ExpectedConditions.attributeContains(allFlights, "class", "segmented-list results-list duration-sort"));

		for (int i = 0; i < FlightDuration.size() - 1; i++) {

			int hour = getDuration(i, 0, "h");
			int min = getDuration(i, 1, "m");
			int hour2 = getDuration(i + 1, 0, "h");
			int min2 = getDuration(i + 1, 1, "m");

			if (hour > hour2) {
				faresOrdered = false;
				break;
			} else if (hour == hour2) {
				if (min > min2) {
					faresOrdered = false;
					break;
				}
			}
			setLoggerInfo("Hour Fare Number " + i + ": " + hour + "  ");
			setLoggerInfo("Min Fare Number " + i + ": " + min);

		}
		return faresOrdered;

	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: method to select the departure flight
	 * @param fareNumber : Integer
	 */

	public void setDepartureFlight(int fareNumber) {
		getWait().until(ExpectedConditions.invisibilityOf(progressBar));
		selectFlight(fareNumber);
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: method to select the return flight
	 * @param fareNumber : Integer
	 * @return FlightInformationPage
	 */

	public FlightInformationPage setReturnFlight(int fareNumber) {
		getWait().until(ExpectedConditions.attributeContains(allFlights, "class", " price-sort"));
		selectFlight(fareNumber);
		modalHandler();
		return new FlightInformationPage(this.getDriver());
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: method to select the return flight
	 * @param fareNumber : Integer
	 * @return CarSearchPage
	 */

	public CarSearchPage setReturnFlightToCar(int fareNumber) {
		getWait().until(ExpectedConditions.attributeContains(allFlights, "class", " price-sort"));
		selectFlight(fareNumber);
		modalHandler();
		return new CarSearchPage(this.getDriver());
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: method to select a flight
	 * @param fareNumber : Integer
	 */

	public void selectFlight(int fareNumber) {
		String selectFareBtn = "//li[" + fareNumber + "]/div[2]/div//button";

		if (getDriver().findElements(By.xpath(selectFareBtn)).size() != 0) {
			WebElement selectThisFareBtn = getDriver().findElement(By.xpath(selectFareBtn));
			flightSelectBtn.get(fareNumber - 1).click();
			click(selectThisFareBtn);

		} else {
			flightSelectBtn.get(fareNumber - 1).click();
			if (getDriver().findElements(By.xpath(selectFareBtn)).size() != 0
					&& getDriver().findElement(By.xpath(selectFareBtn)).isEnabled()) {
				WebElement selectThisFareBtn = getDriver().findElement(By.xpath(selectFareBtn));
				click(selectThisFareBtn);
			}
		}
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: method to handle if the "add Hotel" message is Displayed or not
	 */

	public void modalHandler() {
		boolean modalPresent = getDriver().findElements(By.id("xSellHotelForcedChoice")).size() != 0;

		if (modalPresent) {
			WebElement noThanks = getDriver().findElement(By.id("forcedChoiceNoThanks"));
			getWait().until(ExpectedConditions.elementToBeClickable(noThanks));
			noThanks.click();
		} else {
			setLoggerInfo("Modal Wasn't Displayed");
		}
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: return true if an element is present in all the fares
	 * @param elementList : List<WebElement>
	 * @return boolean
	 */

	public boolean isPresentForAllFares(List<WebElement> elementList) {
		boolean elementPresent = false;

		if (elementList.size() == allFlightList.size()) {
			elementPresent = true;

			for (int i = 0; i < allFlightList.size(); i++) {
				if (!elementList.get(i).isDisplayed()) {
					elementPresent = false;
					setDurationMissed(i);
					break;
				}
			}
		} else {
			setLoggerInfo("Comparison between the number of elements and the number of fares doesn't match");
		}

		return elementPresent;
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: return an integer Obtained from the flight duration info
	 * @param flightIndex  : Integer
	 * @param stringIndex  : Integer
	 * @param textToRemove : String
	 * @return Integer
	 */
	public int getDuration(int flightIndex, int stringIndex, String textToRemove) {
		String a = FlightDuration.get(flightIndex).getText();
		String[] number = a.split(" ");
		String filtered1 = number[stringIndex].replaceAll(textToRemove, "");
		int time = Integer.parseInt(filtered1);
		return time;
	}

	public int getButtonMissed() {
		return buttonMissed;
	}

	public void setButtonMissed(int buttonMissed) {
		this.buttonMissed = buttonMissed;
	}

	public int getDurationMissed() {
		return durationMissed;
	}

	public void setDurationMissed(int durationMissed) {
		this.durationMissed = durationMissed;
	}

	public int getFareDetailMissed() {
		return fareDetailMissed;
	}

	public void setFareDetailMissed(int fareDetailMissed) {
		this.fareDetailMissed = fareDetailMissed;
	}
}
