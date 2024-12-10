package com.app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
//import org.testng.Reporter;

import io.cucumber.java.Scenario;
//import io.github.bonigarcia.wdm.WebDriverManager;//Post Selenium 4.10, not required

public class Utils {



	private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
	static Logger log = LogManager.getFormatterLogger(Utils.class);
	static JavascriptExecutor js ;


	public static WebDriver getDriver() {
		if (tlDriver.get() == null) { // Initialize only if not already set
			log.info("*************** Set up new browser ***************");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			WebDriver driver = new ChromeDriver(options);
			tlDriver.set(driver);
			driver.manage().timeouts().implicitlyWait(Duration.ofMillis(20000));
			driver.manage().window().maximize();
		}
		return tlDriver.get();
	}

	public static synchronized WebDriver getDriver1() {
		return tlDriver.get();
	}

	public static void tearDown() {
		log.info("*************** Kill Browser ***************");
		WebDriver driver = getDriver1();
		if (driver != null) {
			driver.quit();
			tlDriver.remove(); // Remove the ThreadLocal reference after quitting
		}
	}


	public static String timePrint(String tFormat) {
		DateTimeFormatter dtf;
		if (tFormat != null && tFormat.contains("yyyy")) {
			dtf = DateTimeFormatter.ofPattern(tFormat);
		} else {
			dtf = DateTimeFormatter.ofPattern("ddMMMyyyy_HH-mm-ss");
		}
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public static String getProp(String prop) throws IOException {
		FileInputStream fis = new FileInputStream(new File("ProjectProps.properties"));
		Properties projProp = new Properties();
		projProp.load(fis);
		return projProp.get(prop).toString();
	}

	public static String getRandomString(int length) {
		// Define the characters to choose from
		String characters = "abcde0123456789";
		StringBuilder randomString = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < length; i++) {
			// Pick a random character from the set
			int index = random.nextInt(characters.length());
			randomString.append(characters.charAt(index));
		}
		return randomString.toString();
	}

	public static String createPassword(int length) {
		// Define the characters to choose from
		String smallLetters = "abcdefghijklmnopqrstuvqxyz";
		String numbers = "0123456789";
		String spChars = "!@#$%^&*()_";
		String capLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder passwordString = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < length/3 && i<smallLetters.length(); i++) {
			// Pick a random character from the set
			int index = random.nextInt(smallLetters.length());
			passwordString.append(smallLetters.charAt(index));
		}

		for (int i = 0; i < length/3 && i<numbers.length(); i++) {
			// Pick a random character from the set
			int index = random.nextInt(numbers.length());
			passwordString.append(numbers.charAt(index));
		}

		for (int i = 0; i < length/3 && i<spChars.length(); i++) {
			// Pick a random character from the set
			int index = random.nextInt(spChars.length());
			passwordString.append(spChars.charAt(index));
		}

		for (int i = 0; i < length/3 && i<capLetters.length(); i++) {
			// Pick a random character from the set
			int index = random.nextInt(capLetters.length());
			passwordString.append(capLetters.charAt(index));
		}
		return passwordString.toString();
	}

	public static void ts(Scenario scenario) {
		try {
			WebDriver driver = Utils.getDriver1();
			TakesScreenshot screenshot = (TakesScreenshot) driver;
			File scrFile = screenshot.getScreenshotAs(OutputType.FILE);
			String currScrPath = System.getProperty("user.dir") + "/Screenshots/" + timePrint("dd-MMM-yyyy") + "/"
					+ scenario.getName().substring(0, 22) + "_" + timePrint(null) + ".png";
			File currScr = new File(currScrPath);
			FileUtils.copyFile(scrFile, currScr);
			byte[] fileContent = FileUtils.readFileToByteArray(currScr);
			scenario.attach(fileContent, "image/png", null);
		} catch (WebDriverException | IOException e) {
			e.printStackTrace();
		}
	}

}
