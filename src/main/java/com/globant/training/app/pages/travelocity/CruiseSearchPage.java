package com.globant.training.app.pages.travelocity;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.globant.training.app.pages.BasePage;
import com.globant.training.app.pages.Utils;

/**
 * Cruise Search Page.
 * 
 * @author sebastian.rubio
 *
 */

public class CruiseSearchPage extends BasePage {

	private final String DESTINATION_SELECTED = "destination-select";
	private final String ADULTS_NUMBER_SELECTED = "travelers-select";
	private final String TITLE_FARE_LIST = "title-on-ship-image";
	private final String NIGHTS_10_14_FILTER_CHECKBOX = "//div[@id='cruise-search-display']//input[@name='length-10-14']";
	private final String FARE_RESULTS = "[class='col search-results']";
	private final String SORT_OPTIONS_PRICE = ".option:nth-of-type(3) [aria-pressed]";
	private final String PROMOTION_LIST = "[class='message-flag flex-flag']";
	private final String FARE_CRUISES_LIST = "flex-card";
	private final String DEPARTURE_DATE_LIST = "departing-on";
	private final String DEPARTURE_CITY_LIST = "subtitle-on-ship-image";
	private Utils util = new Utils(getDriver());

	private int fareWithoutCorrectDestination;
	private String titleFareSelected;
	private String departureDateSelected;
	private String departureCitySelected;

	@FindBy(id = DESTINATION_SELECTED)
	private WebElement destinationSelected;

	@FindBy(id = ADULTS_NUMBER_SELECTED)
	private WebElement adultsNumberSelected;

	@FindAll({ @FindBy(className = TITLE_FARE_LIST) })
	private List<WebElement> titleFareList;

	@FindBy(xpath = NIGHTS_10_14_FILTER_CHECKBOX)
	private WebElement nights10To14NightsFilterCheckbox;

	@FindBy(css = FARE_RESULTS)
	private WebElement fareResults;

	@FindBy(css = SORT_OPTIONS_PRICE)
	private WebElement sortOptionsBarPrice;

	@FindAll({ @FindBy(css = PROMOTION_LIST) })
	private List<WebElement> promotionsList;

	@FindAll({ @FindBy(className = FARE_CRUISES_LIST) })
	private List<WebElement> fareCruisesList;

	@FindAll(@FindBy(className = DEPARTURE_DATE_LIST))
	private List<WebElement> departureDateList;

	@FindAll(@FindBy(className = DEPARTURE_CITY_LIST))
	private List<WebElement> departureCityList;

	protected CruiseSearchPage(WebDriver pDriver) {
		super(pDriver);
		setLoggerInfo("Going to Cruise Search Page");
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: Get the destination selected
	 * @return String
	 */

	public String getDestinationSelected() {
		getWait().until(ExpectedConditions.visibilityOf(destinationSelected));
		return destinationSelected.getText();
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: Get the number of adults selected
	 * @return Integer
	 */

	public int getAdultsNumberSelected() {
		return Integer.parseInt(adultsNumberSelected.getText().replace(" adults", ""));
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: true if the destination is present in all fares
	 * @param destination
	 * @return boolean
	 */

	public boolean isCorrectDestinationPresentInAllFares(String destination) {
		boolean destinationPresent = true;
		getWait().until(ExpectedConditions.visibilityOf(fareResults));
		for (int i = 0; i < titleFareList.size(); i++) {

			if (!titleFareList.get(i).getText().contains(destination)) {
				destinationPresent = false;
				setFareWithoutCorrectDestination(i);
				break;
			}
		}

		return destinationPresent;
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: set the filter option "10 to 14 nights"
	 */

	public void setNights10To14FilterCheckbox() {
		nights10To14NightsFilterCheckbox.click();
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: true if the all fares are from 10 to 14 nights
	 * @return boolean
	 */

	public boolean isAllSorted10To14Nights() {
		boolean isSortedCorrectly = true;
		getWait().until(ExpectedConditions.elementToBeClickable(sortOptionsBarPrice));

		for (int i = 0; i < titleFareList.size(); i++) {
			String titleFare = titleFareList.get(i).getText().replaceAll("([A-z]*)( )*", "");
			int nights = Integer.parseInt(titleFare);
			setLoggerInfo("Nights: " + nights);
			if (nights < 10 || nights > 14) {
				isSortedCorrectly = false;
				// fareWithoutCorrectDestination = i;
				break;
			}
		}

		return isSortedCorrectly;
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: true if are promotions present
	 * @return boolean
	 */

	public boolean arePromotionsPresent() {
		boolean promotionsPresent = false;
		if (promotionsList.size() > 0 && fareCruisesList.size() > promotionsList.size()) {
			promotionsPresent = true;
			setLoggerInfo("The total amount of promotions is: " + promotionsList.size());
			setLoggerInfo("The total amount of fares is: " + fareCruisesList.size());
		}
		return promotionsPresent;

	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: select the fare with the highest discount
	 * @return CruiseCabinCategoryPage
	 */

	public CruiseCabinCategoryPage setHigerDiscountFare() {
		getDriver().findElement(By.xpath(getHighestPromo())).click();
		util.chageOfTab();
		return new CruiseCabinCategoryPage(this.getDriver());
	}

	public int getFareWithoutCorrectDestination() {
		return fareWithoutCorrectDestination;
	}

	public void setFareWithoutCorrectDestination(int fareWithoutCorrectDestination) {
		this.fareWithoutCorrectDestination = fareWithoutCorrectDestination;
	}

	public String getTitleFareSelected() {
		return titleFareSelected;
	}

	public void setTitleFareSelected(String titleFareSelected) {
		this.titleFareSelected = titleFareSelected;
	}

	public String getDepartureDateSelected() {
		return departureDateSelected;
	}

	public void setDepartureDateSelected(String departureDateSelected) {
		this.departureDateSelected = departureDateSelected;
	}

	public String getDepartureCitySelected() {
		return departureCitySelected;
	}

	public void setDepartureCitySelected(String departureCitySelected) {
		this.departureCitySelected = departureCitySelected;
	}
	
	/**
	 * @author sebastian.rubio
	 *
	 * @description: Obtains the highest promotion from all cruise fares 
	 * @return String
	 */
	public String getHighestPromo() {
		String totalFares = "//section[@role='main']/div[3]/div";
		int promotion = 0;
		int highestPromo = 0;
		int counter = 0;
		String highestPromoSelectBtn = "Something went wrong";
		String showItinerary = "Something went wrong";

		for (int i = 1; i < getDriver().findElements(By.xpath(totalFares)).size(); i++) {
			String fare = "//section[@role='main']/div[3]/div[" + i + "]//div[@class='flex-card']/div[2]";
			if (getDriver().findElements(By.xpath(fare)).size() != 0) {
				WebElement cruiseFare = getDriver().findElement(By.xpath(fare));
				counter = counter + 1;
				if (cruiseFare.getText().contains("%")) {
					promotion = Integer.parseInt(cruiseFare.getText().replaceAll("([A-z]*)( )*(%)*", ""));
					if (promotion > highestPromo) {
						highestPromo = promotion;
						highestPromoSelectBtn = "//section[@role='main']/div[3]/div[" + i
								+ "]//div[@class='flex-card']//a[@class='btn btn-secondary btn-action select-sailing-button']";
						setLoggerInfo("Highest promotion so far, to fare " + i + ": " + highestPromo);
						showItinerary = "//section[@role='main']/div[3]/div[" + i
								+ "]//div[@class='card-links']//button[text()='Show itinerary']";
						setTitleFareSelected(titleFareList.get(counter - 1).getText());
						setDepartureDateSelected(departureDateList.get(counter - 1).getText());
						setDepartureCitySelected(
								departureCityList.get(counter - 1).getText().replaceAll("[^,]*$", "").replace(",", ""));
					}
				}
			}
		}
		// Not always the fares have the option to see the Itinerary
		if (getDriver().findElements(By.xpath(showItinerary)).size() != 0) {
			WebElement showItineraryBtn = getDriver().findElement(By.xpath(showItinerary));
			getWait().until(ExpectedConditions.visibilityOf(showItineraryBtn));
			showItineraryBtn.click();
			getWait().until(
					ExpectedConditions.visibilityOf(getDriver().findElement(By.className("toggled-map-and-ports"))));
		}
		return highestPromoSelectBtn;
	}
}
