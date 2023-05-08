package tests.manager;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.HomePage;
import pages.ManagerPage;
import utils.Helper;

public class DeleteCustomer {

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
	public void deleteCustomer() {
		driver.get(Helper.BASE_URL.concat("login"));
		HomePage hPage = new HomePage(driver, helper);
		ManagerPage mPage = new ManagerPage(driver, helper);
		hPage.clickManager();
		helper.driverWait(Helper.DELAY_MEDIUM);
		mPage.clickShowCustomers();
		helper.driverWait(Helper.DELAY_MEDIUM);
		String deleted = mPage.deleteCustomer();
		if (!mPage.isInList(deleted)) {
			System.out.println("Deletion succeed");
		} else {
			System.out.println("Deletion Failed");
		}
	}
}
