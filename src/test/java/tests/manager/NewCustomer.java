package tests.manager;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.HomePage;
import pages.ManagerPage;
import utils.Helper;
import utils.JSONUtils;

public class NewCustomer {
	private WebDriver driver;
	private Helper helper;
	private Logger logger = LogManager.getLogger(getClass());

	@After
	public void tearDown() {
		// driver.quit();
	}

	@Before
	public void setUp() throws IOException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		helper = new Helper(driver);
	}

	@Test
	public void insertNewCustomer() {
		JSONArray jArray = (JSONArray) JSONUtils
				.getDetailsFromJson(".\\src\\main\\resources\\JSONs\\insert_new_customer.json").get("testCases");
		driver.get(Helper.BASE_URL.concat("login"));
		HomePage hPage = new HomePage(driver, helper);
		ManagerPage mPage = new ManagerPage(driver, helper);
		hPage.clickManager();
		helper.driverWait(Helper.DELAY_MEDIUM);
		mPage.clickAddCustomer();
		for (int i = 0; i< jArray.size(); i++) {
			JSONObject jsonObject = (JSONObject) jArray.get(i);
			String firstName = (String) jsonObject.get("firstName");
			String lastName = (String) jsonObject.get("lastName");
			String postCode = (String) jsonObject.get("postCode");
			mPage.addNewCustomer(firstName, lastName, postCode);
			helper.driverWait(Helper.DELAY_SMALL);
			String text = driver.switchTo().alert().getText();
			driver.switchTo().alert().accept();
			helper.driverWait(Helper.DELAY_SMALL);
			if (text.contains((String)jsonObject.get("expectedOutput"))) {
				logger.info("Adding a customer test succeed");
			} else {
				logger.error("Adding a customer test failed");
			}
		}
	}
}
