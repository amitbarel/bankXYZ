package pages.customer.tests;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.Helper;

public class Logout {
	private final int DELAY_BIG = 10000, DELAY_MEDIUM = 7500, DELAY_SMALL = 5000;
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
	public void testLogout() {
		driver.get(Helper.BASE_URL.concat("customer"));
		driver.manage().window().maximize();
		helper.driverWait(DELAY_MEDIUM);
		Select drpCharacter = new Select(driver.findElement(By.name("userSelect"))); // Using select method
		drpCharacter.selectByIndex(1);
		helper.driverWait(DELAY_MEDIUM);
		driver.findElement(By.xpath("//button[@class = 'btn btn-default']")).click();
		helper.driverWait(DELAY_MEDIUM);
				
	}

}
