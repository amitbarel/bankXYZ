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
