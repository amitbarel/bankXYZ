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
		Logger logger = LogManager.getLogger(this);
		logger.info("opening webiste");
		String amountToDeposit = "7500";
		driver.get(Helper.BASE_URL.concat("login"));
		HomePage hPage = new HomePage(driver, helper);
		CustomersPage cPage = new CustomersPage(driver, helper);
		hPage.clickCustomer();
		helper.driverWait(Helper.DELAY_MEDIUM);
		String name = cPage.getRandomUser();
		cPage.chooseNameFromList(name);
		int balanceBefore = cPage.readBalance();
		cPage.deposit(amountToDeposit);
		int balanceAfter = cPage.readBalance();
		if (balanceAfter - balanceBefore == Integer.parseInt(amountToDeposit)) {
			System.out.println("Deposit Succeed");
		} else {
			System.err.println("Deposit Failed");
		}
	}

	public static void main(String args[]) {
		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));
		org.junit.runner.Result result = junit.run(Deposit.class);

		if (result.getFailureCount() > 0) {
			System.out.println("Test failed.");
			System.exit(1);
		} else {
			System.out.println("Test finished successfully.");
			System.exit(0);
		}
	}
}
