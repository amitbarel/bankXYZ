package tests.manager;

import java.io.IOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.HomePage;
import pages.ManagerPage;
import utils.Helper;

public class NewAccount {

	private WebDriver driver;
	private Helper helper;
	private Logger logger = LogManager.getLogger(this.getClass());

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
	public void openNewAccountForCustomer() {
		driver.get(Helper.BASE_URL.concat("login"));
		HomePage hPage = new HomePage(driver, helper);
		ManagerPage mPage = new ManagerPage(driver, helper);
		hPage.clickManager();
		helper.driverWait(Helper.DELAY_MEDIUM);
		mPage.clickOpenAccount();
		String name = mPage.getRandomUser();
		mPage.openAccountForCustomer(name);
		String text = driver.switchTo().alert().getText();
		String number = text.substring(text.indexOf(":") + 1);
		number.trim();
		driver.switchTo().alert().accept();
		if (text.contains("Account created successfully") && mPage.isInCustomersTable(name, number)) {
			logger.info("Creating an account succeed");
		} else {
			logger.error("Creating an account failed");
		}
	}
}
