package bankXYZ.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.Helper;

public class HomePage {
	private WebDriver driver;
	private Helper helper;
	private Logger logger = LogManager.getLogger(this);

	public HomePage(WebDriver driver, Helper helper) {
		this.driver = driver;
		this.helper = helper;
	}

	By customerBtn = By.xpath("//button[@ng-click = 'customer()']");
	By managerBtn = By.xpath("//button[@ng-click = 'manager()']");

	public void clickCustomer() {
		logger.info("Waiting for driver...");
		helper.driverWait(Helper.DELAY_MEDIUM);
		logger.info("Clicking on customer button...");
		driver.findElement(customerBtn).click();
	}

	public void clickManager() {
		logger.info("Waiting for driver...");
		helper.driverWait(Helper.DELAY_MEDIUM);
		logger.info("Clicking on manager button...");
		driver.findElement(managerBtn).click();
	}
}