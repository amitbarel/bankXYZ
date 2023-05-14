package tests.customer;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.CustomersPage;
import pages.HomePage;
import utils.Helper;

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
		String amountToDeposit = "7500";
		driver.get(Helper.BASE_URL.concat("login"));
		HomePage homePage = new HomePage(driver, helper);
		CustomersPage customerPage = new CustomersPage(driver, helper);
		homePage.clickCustomer();
		helper.driverWait(Helper.DELAY_MEDIUM);
		String name = customerPage.getRandomUser();
		customerPage.chooseNameFromList(name);
		int balanceBefore = customerPage.readBalance();
		customerPage.deposit(amountToDeposit);
		int balanceAfter = customerPage.readBalance();
		if (balanceAfter - balanceBefore == Integer.parseInt(amountToDeposit)) {
			logger.info("Deposit Succeed");
		} else {
			logger.error("Deposit Failed");
		}
	}

}
