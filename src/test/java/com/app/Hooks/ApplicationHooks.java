package com.app.Hooks;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.app.utils.Utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class ApplicationHooks {

	static Logger log = LogManager.getFormatterLogger(ApplicationHooks.class);
	
	@Before("@UI")
	public static void Before_UI() {
		log.info(new Throwable().getStackTrace()[0].getMethodName());
//		Utilities.getDriver();
	}
	
	
	
	@After("@UI")
	public static void After_UI(Scenario scenario) throws IOException {
		if(scenario.isFailed()) {
			Utils.ts(scenario);
		}
		log.info(new Throwable().getStackTrace()[0].getMethodName());
		Utils.tearDown();
	}

	@Before("@DB")
	public static void setItUpdB() {
		log.info("***************Initiate dB Connection***************");
	}
	
	
	
	@After("@DB")
	public static void clearItOffdB() throws IOException {
//		System.out.println(new Throwable().getStackTrace()[0].getMethodName());
//		System.out.println("***************CLOSE dB Connection***************");
		log.info("***************CLOSE dB Connection***************");
	}
	
}
