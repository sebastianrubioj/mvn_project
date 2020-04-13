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
 * Hotel Search Page.
 * 
 * @author sebastian.rubio
 *
 */

public class HotelSearchPage extends BasePage {

	private final String ORIGIN_CITY = "button[class='origin fakeLink']";
	private final String DESTINATION_CITY = ".desktopPlayback .destination.fakeLink";
	private final String HEADER_MESSAGE = "section-header-main";
	private final String NUMBER_OF_ROOMS = "button[class='rooms fakeLink']";
	private final String GO_TOP_PAGE_LINK = "backToTop";
	private final String SORT_BY_PRICE = "button[data-opt-group='Price']";
	private final String PRICES = "[class='actualPrice price fakeLink ']";
	private final String ALL_FARES_PACKAGE = "resultsContainer";
	private final String HOTEL_STARS = "rating-secondary";
	private final String FARE_LIST = "div[class='flex-link-wrap']";
	private final String HOTEL_NAME_LIST = "[class='hotelName fakeLink']";
	private final String HOTEL_FARE_MESSAGE = "span[class='uitk-badge-text']";
	private final String ALL_FARES_HOTEL = "[data-stid='section-results']";
	private final String MEMBER_DISCOUNT_MESSAGE = "[class='messaging-card__subtitle uitk-type-200']";
	private Utils util = new Utils(getDriver());

	private String priceSelected;
	private double starsNumberSelected;
	private String hotelNameSelected;

	@FindBy(css = ORIGIN_CITY)
	private WebElement cityOfOrigin;

	@FindBy(css = DESTINATION_CITY)
	private WebElement cityOfDestination;

	@FindBy(className = HEADER_MESSAGE)
	private WebElement headerMessage;

	@FindBy(css = NUMBER_OF_ROOMS)
	private WebElement numberOfRooms;

	@FindBy(className = GO_TOP_PAGE_LINK)
	private WebElement goToTopPageLink;

	@FindBy(css = SORT_BY_PRICE)
	private WebElement sorteByPrice;

	@FindAll({ @FindBy(css = PRICES) })
	private List<WebElement> pricesList;

	@FindBy(id = ALL_FARES_PACKAGE)
	private WebElement allFaresPackage;

	@FindAll({ @FindBy(className = HOTEL_STARS) })
	private List<WebElement> hotelStarsList;

	@FindAll({ @FindBy(css = FARE_LIST) })
	private List<WebElement> fareList;

	@FindAll({ @FindBy(css = HOTEL_NAME_LIST) })
	private List<WebElement> hotelNameList;

	@FindAll({ @FindBy(css = HOTEL_FARE_MESSAGE) })
	private List<WebElement> hotelFareMessageList;

	@FindBy(css = ALL_FARES_HOTEL)
	private WebElement allFaresHotel;

	@FindBy(css = MEMBER_DISCOUNT_MESSAGE)
	private WebElement memberDiscountMessage;

	protected HotelSearchPage(WebDriver pDriver) {
		super(pDriver);
		setLoggerInfo("Opening Hotel Search Page");
		// TODO Auto-generated constructor stub
	}

	public boolean isTopOfPageLinkPresent() {
		return goToTopPageLink.isDisplayed();
	}

	/**
	 * click on store by price button.
	 */

	public void setSortByPrice() {
		sorteByPrice.click();
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: return true if the results are correctly stored by price.
	 * @return boolean
	 */

	public boolean isCorrectlySorted() {
		boolean pricesOrdered = true;
		getWait().until(ExpectedConditions.attributeContains(allFaresPackage, "class", "container no-outline"));
		setLoggerInfo("the price list size is: " + pricesList.size());
		for (int i = 0; i < pricesList.size() - 1; i++) {
			int price1 = getPriceFromHotelList(i);
			int price2 = getPriceFromHotelList(i + 1);
			if (price1 > price2) {
				pricesOrdered = false;
				break;
			}
			setLoggerInfo("The price1 is: " + price1 + "  &   The price2 is: " + price2);
		}

		return pricesOrdered;

	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: get the price from an specific hotel fare
	 * @param hotelIndex : Integer
	 * @return Integer
	 */
	public int getPriceFromHotelList(int hotelIndex) {
		String a = pricesList.get(hotelIndex).getText();
		String priceWithoutSpaces = a.trim();
		String priceFiltered1 = priceWithoutSpaces.replace(",", "");
		String priceFiltered2 = priceFiltered1.replace("$", "");
		int number = Integer.parseInt(priceFiltered2);
		return number;
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: Select a hotel according with the stars amount desired.
	 * @param starsAmount : double
	 * @return ChooseARoomPage
	 */

	public ChooseARoomPage setHotelByStars(double starsAmount) {
		setLoggerInfo("the hotel stars list is: " + hotelStarsList.size());
		for (int i = 0; hotelStarsList.size() > i; i++) {
			String starsText = hotelStarsList.get(i).getText();
			String stars = starsText.replace("out of 5.0", "");
			double starsN = Double.parseDouble(stars);

			if (starsN >= starsAmount) {
				getWait().until(ExpectedConditions.elementToBeClickable(fareList.get(i)));
				setPriceSelected(pricesList.get(i).getText().trim());
				setStarsNumberSelected(starsN);
				setHotelNameSelected(hotelNameList.get(i).getText());
				fareList.get(i).click();
				break;
			}
			setLoggerInfo("fare number: " + (i + 1));

		}
		return new ChooseARoomPage(this.getDriver());
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: Get the sponsored message for the first result
	 * @return String
	 */

	public String getMessageSponsored() {

		if (getDriver().findElements(By.cssSelector("[class='uitk-image all-image-fit-cover uitk-image-background']"))
				.size() != 0) {
			getDriver().findElement(By.cssSelector("[data-icon='tool-close']")).click();
		}
		getWait().until(ExpectedConditions.visibilityOf(allFaresHotel));
		return hotelFareMessageList.get(0).getText();
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: return true if the discount message is present
	 * @return boolean
	 */

	public boolean isTheDiscountMessagePresent() {
		boolean discountMessage = false;
		if (memberDiscountMessage.isDisplayed() && memberDiscountMessage.getText().contains("Member Discounts")) {
			discountMessage = true;
		}
		return discountMessage;
	}

	public String getPriceSelected() {
		util.chageOfTab();
		return priceSelected;
	}

	public String getOriginCity() {
		getWait().until(ExpectedConditions.visibilityOf(headerMessage));
		return cityOfOrigin.getText();
	}

	public String getDestinationCity() {
		return cityOfDestination.getText();
	}

	public String getHeaderMessage() {
		return headerMessage.getText();
	}

	public int getNumberOfRooms() {
		return Integer.parseInt(numberOfRooms.getText());
	}
	
	public void setPriceSelected(String priceSelected) {
		this.priceSelected = priceSelected;
	}

	public double getStarsNumberSelected() {
		return starsNumberSelected;
	}

	public void setStarsNumberSelected(double starsNumberSelected) {
		this.starsNumberSelected = starsNumberSelected;
	}

	public String getHotelNameSelected() {
		return hotelNameSelected;
	}

	public void setHotelNameSelected(String hotelNameSelected) {
		this.hotelNameSelected = hotelNameSelected;
	}
}
