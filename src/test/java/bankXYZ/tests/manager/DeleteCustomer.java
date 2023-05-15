package bankXYZ.tests.manager;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import bankXYZ.pages.HomePage;
import bankXYZ.pages.ManagerPage;
import utils.Helper;

public class DeleteCustomer {

	private WebDriver driver;
	private Helper helper;
	private Logger logger = LogManager.getLogger(this);

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
	public void deleteCustomer() {
		driver.get(Helper.BASE_URL.concat("login"));
		HomePage homePage = new HomePage(driver, helper);
		ManagerPage managerPage = new ManagerPage(driver, helper);
		homePage.clickManager();
		helper.driverWait(Helper.DELAY_MEDIUM);
		managerPage.clickShowCustomers();
		helper.driverWait(Helper.DELAY_MEDIUM);
		String deleted = managerPage.deleteCustomer();
		if (!managerPage.isInList(deleted)) {
			logger.info("Deletion succeed");
		} else {
			logger.error("Deletion Failed");
		}
	}
}
