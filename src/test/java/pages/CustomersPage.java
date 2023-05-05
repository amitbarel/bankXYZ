package pages;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import tests.customer.Login;
import tests.customer.Logout;
import utils.Helper;

public class CustomersPage {
	private WebDriver driver;
	private Helper helper;
	private Login loginTest;
	private Logout logoutTest;

	@After
	public void tearDown() {
//		 driver.quit();
	}

	@Before
	public void setUp() throws IOException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		helper = new Helper(driver);
		loginTest = new Login();
		logoutTest = new Logout();
	}

	@Test
	public void log_in_to_the_system() {
		loginTest.testLogin();
	}
	
	@Test
	public void log_out_from_the_system() {
		logoutTest.testLogout();
	}

	public static void main(String args[]) {
		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));
		org.junit.runner.Result result = junit.run(CustomersPage.class);

		if (result.getFailureCount() > 0) {
			System.out.println("Test failed.");
			System.exit(1);
		} else {
			System.out.println("Test finished successfully.");
			System.exit(0);
		}
	}
}
