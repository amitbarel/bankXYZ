package tests.customer;

import java.io.IOException;
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
import pages.CustomersPage;
import pages.HomePage;
import pages.ManagerPage;
import utils.Helper;
import utils.JSONUtils;

public class Withdrawl {
	private WebDriver driver;
	private Helper helper;
	private Logger logger = LogManager.getLogger(this.getClass());

	@After
	public void tearDown() {
//		driver.quit();
	}

	@Before
	public void setUp() throws IOException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		helper = new Helper(driver);
	}

	@Test
	public void withdrawMoneyFromAccount() {
		JSONArray jArray = (JSONArray) JSONUtils
				.getDetailsFromJson(".\\src\\main\\resources\\JSONs\\withdrawl_money_from_account.json")
				.get("testCases");
		driver.get(Helper.BASE_URL.concat("login"));
		HomePage homePage = new HomePage(driver, helper);
		CustomersPage customersPage = new CustomersPage(driver, helper);
		ManagerPage managerPage = new ManagerPage(driver, helper);
		for (int i = 0; i < jArray.size(); i++) {
			homePage.clickManager();
			helper.driverWait(Helper.DELAY_MEDIUM);
			managerPage.clickAddCustomer();
			JSONObject jsonObject = (JSONObject) jArray.get(i);
			String firstName = (String) jsonObject.get("firstName");
			String lastName = (String) jsonObject.get("lastName");
			String postCode = (String) jsonObject.get("postCode");
			managerPage.addNewCustomer(firstName, lastName, postCode);
			driver.switchTo().alert().accept();
			helper.driverWait(Helper.DELAY_SMALL);
			managerPage.clickOpenAccount();
			String fullname = firstName.concat(" " + lastName);
			managerPage.openAccountForCustomer(fullname);
			driver.switchTo().alert().accept();
			managerPage.goToHomePage();
			homePage.clickCustomer();
			customersPage.chooseNameFromList(fullname);
			String amountToDeposit = (String) jsonObject.get("toDeposit");
			String amountToWithdrawl = (String) jsonObject.get("toWithdraw");
			customersPage.deposit(amountToDeposit);
			helper.driverWait(Helper.DELAY_MEDIUM);
			customersPage.withdrawl(amountToWithdrawl);
			int balanceAfter = customersPage.readBalance();
			if(balanceAfter == Integer.parseInt((String)jsonObject.get("expectedOutput"))) {
				logger.info("Withdrawl succeed");
			}else {
				logger.error("Withdrawl from " + fullname + "'s account has failed");
			}
			managerPage.goToHomePage();
		}
	}
}
