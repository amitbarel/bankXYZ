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

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.CustomersPage;
import pages.HomePage;
import utils.Helper;
import utils.JSONUtils;

public class Deposit {

	private WebDriver driver;
	private Helper helper;
	private Logger logger = LogManager.getLogger(this);

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
		JSONArray jArray = (JSONArray) JSONUtils
				.getDetailsFromJson(".\\src\\main\\resources\\JSONs\\deposit_money_to_account.json").get("testCases");
		driver.get(Helper.BASE_URL.concat("login"));
		HomePage homePage = new HomePage(driver, helper);
		CustomersPage customerPage = new CustomersPage(driver, helper);
		homePage.clickCustomer();
		helper.driverWait(Helper.DELAY_MEDIUM);
		String name = customerPage.getRandomUser();
		customerPage.chooseNameFromList(name);
		for (int i = 0; i < jArray.size(); i++) {
			int balanceBefore = customerPage.readBalance();
			JSONObject customer = (JSONObject) jArray.get(i);
			String amountToDeposit = (String) customer.get("amount");
			boolean expected = (Boolean) customer.get("expectedOutput");
			customerPage.deposit(amountToDeposit);
			int balanceAfter = customerPage.readBalance();
			if (amountToDeposit.isEmpty()) 
				amountToDeposit = "0";
			boolean isBalanceValid = (balanceAfter - balanceBefore == Integer.parseInt(amountToDeposit));
			if (isBalanceValid == expected)
				logger.info("Deposit test went as expected");
			else
				logger.error("Deposit test did not go as expected");
		}
	}
}
