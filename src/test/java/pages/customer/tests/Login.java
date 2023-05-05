package pages.customer.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import utils.Helper;

public class Login {
	private final int DELAY_BIG = 10000, DELAY_MEDIUM = 7500, DELAY_SMALL = 5000;
	private WebDriver driver;
	private List<String> names;
	private Helper helper;
	
	@After
	public void tearDown() {
		// driver.quit();
	}

	@Before
	public void setUp() throws IOException {
		WebDriverManager.chromedriver().setup();
		names = new ArrayList<String>();
		driver = new ChromeDriver();
		helper = new Helper(driver);
	}
	
	@Test
	public void testLogin() {
		driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
		driver.manage().window().maximize();
		helper.driverWait(DELAY_BIG);
		names = helper.usersList();
		helper.driverWait(DELAY_BIG);
		login();
	}
	
	public void login() {
		driver.get(Helper.BASE_URL.concat("login"));
		driver.findElement(By.xpath("//button[@class = 'btn btn-primary btn-lg']")).click();
		String chosen = randomUserFromList();
		Select drpCharacter = new Select(driver.findElement(By.name("userSelect"))); // Using select method
		drpCharacter.selectByVisibleText(chosen);
		helper.driverWait(DELAY_MEDIUM);
		driver.findElement(By.xpath("//button[@class = 'btn btn-default']")).click();
		helper.driverWait(DELAY_MEDIUM);
		if (verify(chosen)) {
			System.out.println("SUCCEED");
		} else {
			System.err.println("FAILED");
		}
	}
	
	private boolean verify(String chosen) {
		String visibleName = driver.findElement(By.xpath("//*[@class = 'fontBig ng-binding']")).getText();
		return visibleName.equals(chosen);
	}

	private String randomUserFromList() {
		int rnd = new Random().nextInt(names.size());
		return names.get(rnd);
	}

	public static void main(String args[]) throws Exception {
		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));
		org.junit.runner.Result result = junit.run(Login.class);
		if (result.getFailureCount() > 0) {
			System.out.println("Test failed.");
			System.exit(1);
		} else {
			System.out.println("Test finished successfully.");
			System.exit(0);
		}
	}
}
