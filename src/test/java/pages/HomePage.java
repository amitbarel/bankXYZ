package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.Helper;

public class HomePage {
	private WebDriver driver;
	private Helper helper;

	public HomePage(WebDriver driver, Helper helper) {
		this.driver = driver;
		this.helper = helper;
	}

	By customerBtn = By.xpath("//button[@ng-click = 'customer()']");
	By managerBtn = By.xpath("//button[@ng-click = 'manager()']");

	public void clickCustomer() {
		helper.driverWait(Helper.DELAY_MEDIUM);
		driver.findElement(customerBtn).click();
	}

	public void clickManager() {
		helper.driverWait(Helper.DELAY_MEDIUM);
		driver.findElement(managerBtn).click();
	}
}