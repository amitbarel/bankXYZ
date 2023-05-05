package pages.customer.tests;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.Helper;

public class Deposit_money_to_account {
	
	private final int DELAY_BIG = 10000, DELAY_MEDIUM = 7500, DELAY_SMALL = 5000;
	private WebDriver driver;
	private Helper helper;
	
	@After
	public void tearDown() {
		 //driver.quit();
	}

	@Before
	public void setUp() throws IOException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		helper = new Helper(driver);
	}
	
	@Test
	public void testDeposit() {
		driver.get(Helper.BASE_URL.concat("customer"));
		driver.manage().window().maximize();
		helper.driverWait(DELAY_MEDIUM);
		Select drpCharacter = new Select(driver.findElement(By.name("userSelect"))); // Using select method
		drpCharacter.selectByIndex(1);
		helper.driverWait(DELAY_MEDIUM);
		driver.findElement(By.xpath("//button[@class = 'btn btn-default']")).click();
		helper.driverWait(DELAY_MEDIUM);
		WebElement deposit = driver.findElement(By.xpath("//button[@ng-click = 'deposit()']"));
		deposit.click();
		helper.driverWait(DELAY_MEDIUM);
		WebElement amountInput = driver.findElement(By.xpath("//input[@ng-model = 'amount']"));
		amountInput.sendKeys("100");
		driver.findElement(By.xpath("//button[@type = 'submit']")).click();

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
