package tests.customer;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.CustomersPage;
import pages.HomePage;
import utils.Helper;

public class Logout {
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
	public void testLogout() {
		driver.get(Helper.BASE_URL.concat("login"));
		HomePage hPage = new HomePage(driver, helper);
		CustomersPage cPage = new CustomersPage(driver, helper);
		hPage.clickCustomer();
		helper.driverWait(Helper.DELAY_MEDIUM);
		String name = cPage.getRandomUser();
		cPage.chooseNameFromList(name);
		helper.driverWait(Helper.DELAY_BIG);
		cPage.logOut();
		if (driver.getCurrentUrl().equals(Helper.BASE_URL.concat("customer"))) {
			System.out.println("SUCCESS");
		}else {
			System.err.println("FAILED");
		}
				
	}

}
