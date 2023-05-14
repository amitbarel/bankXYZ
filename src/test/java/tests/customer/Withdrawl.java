package tests.customer;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.CustomersPage;
import pages.HomePage;
import utils.Helper;

public class Withdrawl {
	private WebDriver driver;
	private Helper helper;
	private Logger logger = LogManager.getLogger(getClass());

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
	public void withdrawMoneyFromAccount() {
		String amountToWithdrawl = "3500";
		driver.get(Helper.BASE_URL.concat("login"));
		HomePage hPage = new HomePage(driver, helper);
		CustomersPage cPage = new CustomersPage(driver, helper);
		hPage.clickCustomer();
		helper.driverWait(Helper.DELAY_MEDIUM);
		String name = cPage.getRandomUser();
		cPage.chooseNameFromList(name);
		int balanceBefore = cPage.readBalance();
		cPage.withdrawl(amountToWithdrawl);
		int balanceAfter = cPage.readBalance();
		if (balanceBefore - balanceAfter == Integer.parseInt(amountToWithdrawl)) {
			logger.info("Withdrawl Succeed");
		} else {
			logger.error("Withdrawl Failed");
		}
	}
}
