package com.globant.training.app.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
	private final WebDriver driver;
	private final WebDriverWait wait;
	private final Actions actions;
	private static final Logger logger = Logger.getLogger(BasePage.class);

	protected BasePage(WebDriver pDriver) {
		PageFactory.initElements(pDriver, this);
		int timeout = 30;
		wait = new WebDriverWait(pDriver, timeout);
		driver = pDriver;
		actions = new Actions(driver);
	}

	protected WebDriver getDriver() {
		return driver;
	}

	protected WebDriverWait getWait() {
		return wait;
	}

	public Actions getActions() {
		return actions;
	}

	public void dispose() {
		if (driver != null) {
			driver.quit();
		}
	}

	public String getTitle() {
		return driver.getTitle();
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: method to set log info on the pages
	 */

	public void setLoggerInfo(String info) {
		// BasicConfigurator.configure();
		logger.info(info);
	}

	/**
	 * @author sebastian.rubio
	 *
	 * @description: method to wait until element is intractable and click on it
	 * @param element : WebElement
	 */
	public void click(WebElement element) {
		getWait().until(ExpectedConditions.visibilityOf(element));
		getWait().until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}

}
