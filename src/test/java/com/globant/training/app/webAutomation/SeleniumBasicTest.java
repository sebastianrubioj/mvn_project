package com.globant.training.app.webAutomation;

//import org.openqa.selenium.remote.internal.WebElementToJsonConverter;
import org.testng.Assert;
import org.testng.annotations.Test;
//import java.lang.Thread;

import com.globant.training.app.pages.travelocity.CarSearchPage;
import com.globant.training.app.pages.travelocity.ChooseARoomPage;
import com.globant.training.app.pages.travelocity.CruiseCabinCategoryPage;
import com.globant.training.app.pages.travelocity.CruiseSearchPage;
import com.globant.training.app.pages.travelocity.FlightCheckoutPage;
import com.globant.training.app.pages.travelocity.FlightInformationPage;
import com.globant.training.app.pages.travelocity.FlightSearchPage;
import com.globant.training.app.pages.travelocity.HomePage;
import com.globant.training.app.pages.travelocity.HomePageCruise;
import com.globant.training.app.pages.travelocity.HomePageFlight;
import com.globant.training.app.pages.travelocity.HomePageHotel;
import com.globant.training.app.pages.travelocity.HomePagePackage;
import com.globant.training.app.pages.travelocity.HotelSearchPage;

public class SeleniumBasicTest extends BaseTest {

	@Test(groups = { "test1",
			"test" }, dataProvider = "Flights", description = "Exercise 1 Begin the process of booking a flight")
	public void flightBooking(String departureCity, String returnCity) {

		HomePage home = getHomePage();
		/*
		 * 1. Search for a flight from LAS to LAX, 1 adult (clicking on
		 * Flight/Roundtrip). Dates should be at least two month in the future and
		 * ​MUST​ be selected using the datepicker calendar.
		 */
		HomePageFlight homeFlight = home.setFlightTab();
		homeFlight.setFlightInfo(departureCity, returnCity);

		FlightSearchPage search = homeFlight.setSearchFlight();

		/*
		 * 2. Verify the result page using the following: a. There is a box that allow
		 * you to order by Price, Departure, Arrival and Duration.
		 */

		Assert.assertTrue(search.areAllDropDownBoxOptions(), "The dropdown box is not available");

		/* b. The select button is present on every result */
		Assert.assertTrue(search.isSelectBtnForAllFlights(),
				"The fare number " + search.getButtonMissed() + " does not have the select button");

		/* c. Flight duration is present on every result */
		Assert.assertTrue(search.isFlightDurationForAllFares(),
				"The fare number " + search.getDurationMissed() + " does not have the time duration information");

		/* d. The flight detail and baggage fees is present on every result */
		Assert.assertTrue(search.isFlightDetailsForAllFares(),
				"The fare number " + search.getFareDetailMissed() + " does not have the Fare details link");

		/* 3. Sort by duration > shorter */
		search.setStoreByDuration();

		/* Verify the list was correctly sorted */
		Assert.assertTrue(search.areFaresOrderByDuration(),
				"The fares are not ordered by the Duration (shortest) as expected");

		/*
		 * 4. In the page (Select your departure to Los Angeles), select the first
		 * result.
		 */
		search.setDepartureFlight(1);

		/*
		 * 5. In the new page (Select your departure to Las Vegas), select the third
		 * result.
		 */

		FlightInformationPage information = search.setReturnFlight(3);
		/*
		 * 6. Verify trip details in the new page, by: a. Trip total price is present
		 */
		Assert.assertTrue(information.getTotalPricePresent(),
				"The total price is not present on the flight information page");

		/* b. Departure and return information is present */
		Assert.assertTrue(information.getFlightInfoIsPresent(),
				"The flight information is not appearing on the information page");

		/* c.Price guarantee text is present */
		// Assert.assertTrue(information.getFlightGuaranteeTextPresent(), "The flight
		// guarantee text is not present on the information page");

		/* 7. Press Continue Booking button. */

		FlightCheckoutPage checkout = information.setContinueBooking();

		/*
		 * 8. Verify the “Who’s traveling” page is opened by choosing at least 5
		 * validations to be performed.
		 */
		Assert.assertTrue(checkout.isFlightProductSummaryPresent(), "The product summary information is not displayed");
		Assert.assertEquals(checkout.getWhoTravelsMessage(), "Who's traveling?");
		Assert.assertTrue(checkout.isFirstNameInputIsPresent(),
				"The input Field to write the Name of the passenger is not pressent");
		Assert.assertTrue(checkout.isTermsAndConditionsPresent(), "The terms and conditions link is not present");
		Assert.assertTrue(checkout.isCompleteBookingBtnPresent(), "The Complete Booking Button is not present");

	}

	@Test(groups = { "test2",
			"test" }, dataProvider = "Flights", description = "Exercise 2 Begin the process of booking a flight with hotel and car")
	public void flightHotelCarBooking(String departureCity, String returnCity) {

		HomePage home = getHomePage();
		// CarSearchPage carSearch = getCarSearchPage();

		/* 1. Go to “Flight + Hotel + car” page. */
		HomePagePackage homePackage = home.setPackageTab();
		homePackage.setFlightHotelAndCarTab();
		/*
		 * 2. Search for a flight from LAS to LAX, 1 adult. Date should be at least two
		 * month in the future and ​MUST ​be selected using the datepicker calendar. The
		 * trip must last 13 days.
		 */
		homePackage.setPackageInfo(departureCity, returnCity, 1);

		HotelSearchPage searchHotel = homePackage.setSearchBtn();

		/* 3. Verify results page by choosing at least 5 validations to be performed. */
		Assert.assertTrue(searchHotel.getOriginCity().contains("Las Vegas"),
				"The city of departure should be Las Vegas but is: " + searchHotel.getOriginCity());
		Assert.assertTrue(searchHotel.getDestinationCity().contains("Los Angeles"),
				"The city of departure should be Los Angeles but is: " + searchHotel.getDestinationCity());
		Assert.assertTrue(searchHotel.getHeaderMessage().contains("Start by choosing your hotel"),
				"The Header Message is not the expected one, is showing: " + searchHotel.getHeaderMessage()
						+ ". And should show: Start by choosing your hotel");
		Assert.assertEquals(searchHotel.getNumberOfRooms(), 1);
		Assert.assertTrue(searchHotel.isTopOfPageLinkPresent(), "The link to go to the top of the page is not present");

		/* 4. Sort by price. Verify the results were correctly sorted. */
		searchHotel.setSortByPrice();
		Assert.assertTrue(searchHotel.isCorrectlySorted(), "The fares are not correctly stored by price");

		/* 5. Select the first result with at least 3 stars. */
		ChooseARoomPage chooseRoom = searchHotel.setHotelByStars(3);
		/*
		 * 6. In the new page, verify the hotel is the selected in the previous step by
		 * choosing at least 3 validations to be performed.
		 */
		Assert.assertEquals(searchHotel.getPriceSelected(), chooseRoom.getPrice());
		Assert.assertEquals(searchHotel.getHotelNameSelected(), chooseRoom.getHotelName());
		Assert.assertEquals(searchHotel.getStarsNumberSelected(), chooseRoom.getStarsNumber());

		/* 7. Select the first room option */
		FlightSearchPage searchFlight = chooseRoom.setRoom(1);

		/*
		 * 8. In the new page,(Now select your departing flight), select the first
		 * result.
		 */
		searchFlight.setDepartureFlight(1);

		/*
		 * 9. In the new page (Now select your return flight), select the third result.
		 */
		CarSearchPage carSearch = searchFlight.setReturnFlightToCar(3);

		/* 10. Select a car */
		FlightCheckoutPage checkout = carSearch.setCarToRent(4);

		String carTypeSelected = carSearch.getCarTypeSelected();

		/*
		 * 13. Verify the “Who’s traveling” page is opened by choosing at least 5
		 * validations to be performed.
		 */
		Assert.assertTrue(checkout.isFlightProductSummaryPresent(),
				"The Flight product summary information is not displayed");
		Assert.assertTrue(checkout.isHotelProductSummaryPresent(),
				"The Hotel product summary information is not displayed");
		Assert.assertTrue(checkout.isCarProductSummaryPresent(),
				"The Car product summary information is not displayed");
		Assert.assertEquals(checkout.getHotelNameSelected(), searchHotel.getHotelNameSelected());
		Assert.assertEquals(checkout.getCarTypeSelected(), carTypeSelected);

		/*
		 * Note: After Selecting a car, the user gets redirected to the
		 * "Who is Traveling" page so I can not perform the validations described on the
		 * points 11 & 12
		 */
	}

	@Test(groups = { "test3",
			"test" }, dataProvider = "Hotel", description = "Exercise 3: Verify that search by hotel name works properly")
	public void hotelBooking(String hotelDestination) {

		HomePage home = getHomePage();

		/* 1. Go to Hotels page. (Clicking on menu ”Hotels/Hotel Only”) */
		HomePageHotel homeHotel = home.setHotelTab();

		/*
		 * 2. Complete “​Going to​” field with the word “Montevideo, Uruguay”. Do the
		 * Search
		 */
		homeHotel.setHotelDestination(hotelDestination);
		homeHotel.setHotelCheckinDate();
		HotelSearchPage hotel = homeHotel.setSearchHotel();

		/*
		 * 3. Verify that: a. Sponsored results appear first
		 */
		// Assert.assertTrue(hotel.getMessageSponsored().contains("Sponsored"),"The
		// first fare is not sponsored");

		/*
		 * b. You have the option of receive a discount by entering your email address
		 */
		Assert.assertTrue(hotel.isTheDiscountMessagePresent(), "The Discount Message is not present");

	}

	@Test(groups = { "test4",
			"test" }, dataProvider = "Flights", description = "Exercise 4:  Verify that the error message displayed...")
	public void flightHotel(String departureCity, String returnCity) {
		HomePage home = getHomePage();

		/* 1. Click on “Flight + Hotel” option. */
		HomePagePackage homePackage = home.setPackageTab();
		homePackage.setFlightHotelTab();

		/* 2. Complete all the fields. */
		homePackage.setPackageInfo(departureCity, returnCity, 1);

		/* 3. Select the checkbox “I only need a hotel for part of my stay” */
		homePackage.setPartialStayCheckbox();

		/*
		 * 4. Complete the new dates fields with dates that are not included in the
		 * period of the flight dates. Do the Search
		 */
		homePackage.setCheckinDate();
		homePackage.setCheckoutDate();
		homePackage.setSearchBtn();

		/*
		 * 5. Verify the following error message is displayed: “Your partial check-in
		 * and check-out dates must fall within your arrival and departure dates. Please
		 * review your dates.”
		 */
		Assert.assertEquals(homePackage.getMismatchDatesErrorMessage(),
				"Your partial check-in and check-out dates must fall within your arrival and departure dates. Please review your dates.");

	}

	@Test(groups = { "test5",
			"test" }, dataProvider = "CruiserInfo", description = "Exercise 5:  Cruises discount is displayed ")
	public void cruises(String cruiseDestination, int monthsForwardFromNow, int adultsCruiseNumber) {
		HomePage home = getHomePage();

		/* 1. Go to Cruises page. */
		HomePageCruise homeCruises = home.setCruisesTabSelect();

		/* 2. In the Going to drop down select “Europe” */
		homeCruises.setCruisesDestination(cruiseDestination);

		/* 3. In the “Departure month” dropdown select a month. Do the Search */
		homeCruises.setStartCruiseDate(monthsForwardFromNow);
		homeCruises.SetAdultsCruiseNumber(adultsCruiseNumber);

		CruiseSearchPage cruiseSearch = homeCruises.setSearchCruises();

		/*
		 * 4. Verify the Filter information selected before appears in the refine
		 * results section below each dropdown. Note: for this point I just made some
		 * verifications on the page because I didn't find the results section
		 */
		Assert.assertEquals(cruiseSearch.getDestinationSelected(), cruiseDestination);
		Assert.assertTrue(cruiseSearch.getAdultsNumberSelected() == adultsCruiseNumber,
				"The amount of adults selected doesn't match with the amount showed on the search page");
		Assert.assertTrue(cruiseSearch.isCorrectDestinationPresentInAllFares(cruiseDestination),
				"The fare Number " + (cruiseSearch.getFareWithoutCorrectDestination() + 1)
						+ " Does not have the destination " + cruiseDestination);

		/*
		 * 5. In the “Cruise Length” filter, select “10-14 nights” (Verify this
		 * information is displayed below the dropdown).
		 */
		cruiseSearch.setNights10To14FilterCheckbox();
		Assert.assertTrue(cruiseSearch.isAllSorted10To14Nights());

		/* 6. Verify that result page shows cruises with and without discounts */
		Assert.assertTrue(cruiseSearch.arePromotionsPresent());

		/*
		 * 7. Select the cruise option with more discount, pressing the show dates
		 * button first
		 */
		CruiseCabinCategoryPage cabinCategory = cruiseSearch.setHigerDiscountFare();

		/* 8. Validate that cruise information is displayed for the selected one */
		Assert.assertTrue(cabinCategory.getTitleSelected().equalsIgnoreCase(cruiseSearch.getTitleFareSelected()),
				"The title fare selected does not match with the one that appears on "
						+ "the Cabin Category page. Expected: " + cruiseSearch.getTitleFareSelected() + ", but found: "
						+ cabinCategory.getTitleSelected());
		Assert.assertTrue(cabinCategory.getCruiseDateSelected().contains(cruiseSearch.getDepartureDateSelected()),
				"The departure date selected does not match with the one that "
						+ "appears on the Cabin Category page. Expected: " + cruiseSearch.getDepartureDateSelected()
						+ ", but found: " + cabinCategory.getCruiseDateSelected());
		Assert.assertTrue(cabinCategory.getDepartureCitySelected().contains(cruiseSearch.getDepartureCitySelected()),
				"The departure city selected does not match with the one "
						+ "that appears on the Cabin Category page. Expected: "
						+ cruiseSearch.getDepartureCitySelected() + ", but found: "
						+ cabinCategory.getDepartureCitySelected());

	}

}
