package tests.customer;

import java.io.IOException;
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
import pages.CustomersPage;
import pages.HomePage;
import utils.Helper;
import utils.JSONUtils;

public class Deposit {

	private WebDriver driver;
	private Helper helper;
	private Logger logger = LogManager.getLogger(this);
	private JSONUtils jsonUtils;

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
	public void depositMoneyToAccount() {
		JSONArray jArray = (JSONArray) jsonUtils
				.getDetailsFromJson("/Xyz/src/main/resources/JSONs/Deposit money to account.json").get("testCases");
		driver.get(Helper.BASE_URL.concat("login"));
		HomePage homePage = new HomePage(driver, helper);
		CustomersPage customerPage = new CustomersPage(driver, helper);
		homePage.clickCustomer();
		helper.driverWait(Helper.DELAY_MEDIUM);
		String name = customerPage.getRandomUser();
		customerPage.chooseNameFromList(name);
		int balanceBefore = customerPage.readBalance();
		for (int i = 0; i < jArray.size(); i++) {
			JSONObject customer = (JSONObject) jArray.get(i);
			String amountToDeposit = (String) customer.get("amount");
//			JSONArray expectedResult = (JSONArray) customer.get("expectedOutput");
			customerPage.deposit(amountToDeposit);
			int balanceAfter = customerPage.readBalance();
			if (balanceAfter - balanceBefore == Integer.parseInt(amountToDeposit))
				logger.info("Deposit Succeed");
			else
				logger.error("Deposit Failed");
		}
	}
}
