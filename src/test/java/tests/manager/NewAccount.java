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

public class NewAccount {
	
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
	public void openNewAccountForCustomer() {
		driver.get(Helper.BASE_URL.concat("login"));
		HomePage hPage = new HomePage(driver, helper);
		ManagerPage mPage = new ManagerPage(driver, helper);
		hPage.clickManager();
		helper.driverWait(Helper.DELAY_MEDIUM);
		mPage.clickOpenAccount();
		String name = mPage.getRandomUser();
		mPage.chooseDetails(name);
		String text = driver.switchTo().alert().getText();
		String number = text.substring(text.indexOf(":") + 1);
		number.trim();
		driver.switchTo().alert().accept();
		if (text.contains("Account created successfully") && mPage.isInTable(name, number)) {
			System.out.println("Creating an account succeed");
		}else {
			System.err.println("Creating an account failed");
		}
	}
}
