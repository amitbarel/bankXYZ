package tests.manager;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.HomePage;
import pages.ManagerPage;
import utils.Customer;
import utils.Helper;

public class SearchForCustomer {
	private WebDriver driver;
	private Helper helper;
	private Logger logger = LogManager.getLogger(this);

	@After
	public void tearDown() {
//		 driver.quit();
	}

	@Before
	public void setUp() throws IOException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		helper = new Helper(driver);
	}

	@Test
	public void searchForCustomerDetails() {
		ArrayList<Customer> customers;
		driver.get(Helper.BASE_URL.concat("login"));
		HomePage hPage = new HomePage(driver, helper);
		ManagerPage mPage = new ManagerPage(driver, helper);
		hPage.clickManager();
		helper.driverWait(Helper.DELAY_MEDIUM);
		mPage.clickShowCustomers();
		String input = "Her";
		mPage.searchDetails(input);
		helper.driverWait(Helper.DELAY_MEDIUM);
		customers = mPage.getCustomersFromTable();
		for (Customer c : customers) {
			if(!c.isSubstringExists(input)) {
				logger.error("Test Failed, found unrelated customers");
			}
		}
		logger.info("Test Succeed, found all customers");
	}
}