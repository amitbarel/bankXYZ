package bankXYZ.tests.manager;

import java.io.IOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.github.bonigarcia.wdm.WebDriverManager;
import bankXYZ.pages.HomePage;
import bankXYZ.pages.ManagerPage;
import utils.Helper;
import utils.JSONUtils;

public class NewAccount {

	private WebDriver driver;
	private Helper helper;
	private Logger logger = LogManager.getLogger(this.getClass());

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
	public void openNewAccountForCustomer() {
		JSONArray jArray = (JSONArray) JSONUtils
				.getDetailsFromJson(".\\src\\main\\resources\\JSONs\\open_new_account_for_customer.json").get("testCases");
		driver.get(Helper.BASE_URL.concat("login"));
		HomePage homePage = new HomePage(driver, helper);
		ManagerPage managerPage = new ManagerPage(driver, helper);
		for (int i = 0; i < jArray.size(); i++) {
			homePage.clickManager();
			helper.driverWait(Helper.DELAY_MEDIUM);
			managerPage.clickOpenAccount();
			JSONObject jsonObject = (JSONObject) jArray.get(i);
			String name = (String) jsonObject.get("fullName");
			managerPage.openAccountForCustomer(name);
			if (ExpectedConditions.alertIsPresent() == null) {
				logger.error("Creating an account failed");
			} else {
				String text = driver.switchTo().alert().getText();
				String number = text.substring(text.indexOf(":") + 1);
				number.trim();
				driver.switchTo().alert().accept();
				if (text.contains((String)jsonObject.get("expectedOutput")) && managerPage.isInCustomersTable(name, number)) {
					logger.info("Creating an account succeed");
				} else {
					logger.error("Creating an account failed");
				}
				managerPage.goToHomePage();
			}

		}
	}
}
