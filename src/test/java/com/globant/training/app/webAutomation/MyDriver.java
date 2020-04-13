package com.globant.training.app.webAutomation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class MyDriver {

	private WebDriver mDriver;

	public MyDriver(String browser) {
		switch (browser) {
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			mDriver = new FirefoxDriver();
			break;
		case "chrome":
			WebDriverManager.chromedriver().setup();
			mDriver = new ChromeDriver();
			break;
		default:
			break;
		}
	}

	public WebDriver getDriver() {
		return this.mDriver;
	}
}
