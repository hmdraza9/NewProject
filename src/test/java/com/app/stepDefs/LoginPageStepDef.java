package com.app.stepDefs;

import com.app.utils.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.app.utils.Utils;

import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.io.IOException;

import static org.openqa.selenium.By.*;

public class LoginPageStepDef {

	private final WebDriver driver = Utils.getDriver();
	public Scenario scenario;
	Logger log = LogManager.getFormatterLogger(LoginPageStepDef.class);
	private String username;
	private String password;


	@Then("user navigates to page {string}")
	public void userNavigatesToPage(String pageName) throws InterruptedException {
		Helper.click(linkText(pageName));
		Thread.sleep(4000);
		Utils.ts(scenario);
	}

	@Then("user fills the sign up form with given details")
	public void userFillsTheSignUpForm() {
		Helper.fillData(cssSelector("#firstname"), "FN_"+ Utils.getRandomString(10));
		Helper.fillData(name("lastname"), "LN_"+ Utils.getRandomString(10));
		username = "Email_"+ Utils.getRandomString(10)+"@gmail.com";
		Helper.fillData(cssSelector("input[title='Email']"), username);
		password = Utils.createPassword(10);
        log.info("Password: "+ password);
		Helper.fillData(cssSelector(".input-text#password"), password);
		Helper.fillData(cssSelector(".input-text#password-confirmation"), password);
		Helper.click(cssSelector("button[title='Create an Account']"));
		Utils.ts(scenario);
	}

	@Then("user validates the successful sign up")
	public void userValidatesSuccessfulSignUp(){
		log.info("SUCCESS: "+ Helper.getText(xpath("//*[@data-ui-id='message-success']/div/text()")));
		log.info(Helper.getText(xpath("//*[@class='box box-information']/*[@class='box-content']")));
		Utils.ts(scenario);
	}

	@Then("user logs out")
	public void userLogsOut(){
		Helper.click(xpath("//div[@class='panel header']/ul/li[2]//button"));
		Helper.click(xpath("//div[@class='panel header']/ul//ul//a[contains(text(),'Sign Out')]"));
		Utils.ts(scenario);
	}

	@Then("user log in with existing username and password")
	public void userLogInWithExistingUsernameAndPassword(){
		Helper.fillData(cssSelector("input#email.input-text"), username);
		Utils.ts(scenario);
		Helper.fillData(cssSelector("input#pass.input-text[title='Password']"), password);
		Helper.submit(cssSelector("input#pass.input-text[title='Password']"));
		Utils.ts(scenario);
	}

	@Given("user is on app login page")
	public void userLogsIntoSystem() throws IOException {
		driver.get(Utils.getProp("magentoHomePage"));
	}


	@BeforeStep
	public void BeforeStep(Scenario scenario) {
		log.info(new Throwable().getStackTrace()[0].getMethodName());
		this.scenario = scenario;
	}
}
