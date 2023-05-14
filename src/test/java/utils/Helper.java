package utils;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;

public class Helper {
	public static final int DELAY_BIG = 10000, DELAY_MEDIUM = 7500, DELAY_SMALL = 5000;
	private WebDriver driver;
	public static final String BASE_URL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/";

	public Helper(WebDriver driver) {
		this.driver = driver;
	}

	public void driverWait(int delay) {
		driver.manage().timeouts().implicitlyWait(delay, TimeUnit.MILLISECONDS);
	}
}
