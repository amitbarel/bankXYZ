package bankXYZ.tests.manager;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import bankXYZ.pages.HomePage;
import bankXYZ.pages.ManagerPage;
import utils.Customer;
import utils.Helper;
import utils.JSONPaths;
import utils.JSONUtils;

public class SearchForCustomer {
	private WebDriver driver;
	private Helper helper;
	private Logger logger = LogManager.getLogger(this);

	@After
	public void tearDown() {
		 driver.quit();
	}

	@Before
	public void setUp() throws IOException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		helper = new Helper(driver);
	}

	@Test
	public void searchForCustomerDetails() {
		JSONArray jArray = (JSONArray) JSONUtils
				.getDetailsFromJson(JSONPaths.SEARCH_FOR_CUSTOMER_DETAILS).get("testCases");
		ArrayList<Customer> customers;
		driver.get(Helper.BASE_URL.concat("manager/list"));
		ManagerPage managerPage = new ManagerPage(driver, helper);
		for (int i = 0; i < jArray.size(); i++) {
			helper.driverWait(Helper.DELAY_SMALL);
			JSONObject test = (JSONObject)jArray.get(i);
			String input = (String)test.get("input");
			managerPage.searchDetails(input);
			helper.driverWait(Helper.DELAY_MEDIUM);
			customers = managerPage.getCustomersFromTable();
			for (Customer c : customers) {
				if(!c.isSubstringExists(input)) {
					logger.error("Failed, found ".concat(c.toString()));
				}else {
					logger.info("Found ".concat(c.toString()).concat(" which has " + input + " in his details"));
				}
			}
			managerPage.clearSearchCustomerInput();
		}
	}
}