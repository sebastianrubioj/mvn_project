package com.globant.training.app.webAutomation;

import org.apache.log4j.BasicConfigurator;
import org.testng.annotations.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import com.globant.training.app.pages.travelocity.HomePage;

public class BaseTest {
	private MyDriver myDriver;
	private HomePage homePage;

	@BeforeTest(alwaysRun = true)
	public void loggerConfig() {
		BasicConfigurator.configure();
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({ "browser", "url" })
	public void beforeSuite(String browser, String url) {
		myDriver = new MyDriver(browser);
		homePage = new HomePage(myDriver.getDriver(), url);
	}

	@AfterMethod(alwaysRun = true)
	public void afterSuite() {
		myDriver.getDriver().quit();
	}

	@DataProvider(name = "Flights")
	public static Object[][] setFlightsToTravel() {
		return new Object[][] { { "LAS vegas", "Los angeles" } };
	}

	@DataProvider(name = "Hotel")
	public static Object[][] setHotel() {
		return new Object[][] { { "Montevideo, Uruguay" } };
	}

	@DataProvider(name = "CruiserInfo")
	public static Object[][] setCruiserInfo() {
		return new Object[][] { { "Europe", 1, 2 } };
	}

	public HomePage getHomePage() {
		return this.homePage;
	}
}