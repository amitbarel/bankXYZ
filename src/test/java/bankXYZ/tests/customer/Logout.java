package bankXYZ.tests.customer;

import java.io.IOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import bankXYZ.pages.CustomersPage;
import bankXYZ.pages.HomePage;
import utils.Helper;

public class Logout {
	private WebDriver driver;
	private Helper helper;
	private Logger logger = LogManager.getLogger(getClass());

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
	public void logOutFromSystem() {
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
			logger.info("Logging out succeed");
		} else {
			logger.error("Logging out failed");
		}

	}

}
