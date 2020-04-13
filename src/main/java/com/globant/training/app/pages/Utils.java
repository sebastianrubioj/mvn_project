package com.globant.training.app.pages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Utils extends BasePage {

	private final String AUTOCOMPLETED_DROPDOWN = "typeaheadDataPlain";
	private final String DATE_DROPDOWN = "datepicker-cal";
	/*
	 * To select the day I decided to use this xpath so I can be sure that whenever
	 * you run this scenario this will select a date three months later
	 */
	private final String DATE_PICKER_DAY = "//div[@class='datepicker-cal-month'][2]//tr[2]//td[@class='datepicker-day-number notranslate'][1]";

	private Calendar date = Calendar.getInstance();

	@FindBy(id = AUTOCOMPLETED_DROPDOWN)
	private WebElement autocompletedDropdown;

	@FindBy(className = DATE_DROPDOWN)
	private WebElement datePickerDropdown;

	@FindBy(xpath = DATE_PICKER_DAY)
	private WebElement datePickerDay;

	public Utils(WebDriver pDriver) {
		super(pDriver);
		// TODO Auto-generated constructor stub
	}

	public void setInputString(WebElement inputElement, String inputString) {
		getWait().until(ExpectedConditions.visibilityOf(inputElement));
		getWait().until(ExpectedConditions.elementToBeClickable(inputElement));
		inputElement.sendKeys(inputString);
		inputElement.sendKeys(Keys.SPACE);
		getWait().until(ExpectedConditions.visibilityOf(autocompletedDropdown));
		inputElement.sendKeys(Keys.TAB);
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: Set a date an amount of months forward from the current date
	 * @param inputElement : WebElement
	 * @param inputString  : String
	 * 
	 */

	public void setDateForward(WebElement element, int monthsForward) {
		getWait().until(ExpectedConditions.visibilityOf(element));
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		date.setTime(new Date());
		date.add(Calendar.MONTH, monthsForward);
		element.click();
		element.clear();
		setLoggerInfo("check out date: " + formatter.format(date.getTime()));
		element.sendKeys(formatter.format(date.getTime()));
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: method to change the browser tab to continue the flow
	 */
	public void chageOfTab() {
		String currentHandle = getDriver().getWindowHandle();
		Set<String> allHandles = getDriver().getWindowHandles();
		allHandles.remove(currentHandle);
		getDriver().switchTo().window((String) allHandles.toArray()[0]);
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: wait until drop down appear
	 */
	public void waitUntilDropdonwn() {
		getWait().until(ExpectedConditions.visibilityOf(datePickerDropdown));
	}
	
	/**
	 * @author sebastian.rubio
	 *
	 * @description: select a day on calendar
	 */
	public void clickDayPickerDate() {
		datePickerDay.click();
	}
}
