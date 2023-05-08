package tests.manager;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.HomePage;
import pages.ManagerPage;
import utils.Helper;

public class NewCustomer {
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
	public void insertNewCustomer() {
		ArrayList<String> details = new ArrayList<String>() {
			{
				add("Amit");
				add("Barel");
				add("2A3C3D");
			}
		};
		driver.get(Helper.BASE_URL.concat("login"));
		HomePage hPage = new HomePage(driver, helper);
		ManagerPage mPage = new ManagerPage(driver, helper);
		hPage.clickManager();
		helper.driverWait(Helper.DELAY_MEDIUM);
		mPage.clickAddCustomer();
		mPage.insertDetails(details.get(0), details.get(1), details.get(2));
		String name = details.get(0).concat(" " + details.get(1));
		helper.driverWait(Helper.DELAY_SMALL);
		String text = driver.switchTo().alert().getText();
		driver.switchTo().alert().accept();
		helper.driverWait(Helper.DELAY_SMALL);
		if (text.contains("Customer added successfully") && mPage.isInList(name)) {
			System.out.println("Adding a customer succeed");
		} else {
			System.err.println("Adding a customer failed");
		}
	}
}
