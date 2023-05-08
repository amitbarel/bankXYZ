package pages;

import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import utils.Helper;

public class CustomersPage {
	private WebDriver driver;
	private Helper helper;

	public CustomersPage(WebDriver driver, Helper helper) {
		this.driver = driver;
		this.helper = helper;
	}

	By loginBtn = By.xpath("//button[@class = 'btn btn-default']");
	By drp = By.name("userSelect");
	By logoutBtn = By.xpath("//button[@class = 'btn logout']");
	By transactionBtn = By.xpath("//button[@ng-click = 'transactions()']");
	By depositBtn = By.xpath("//button[@ng-click = 'deposit()']");
	By withdrawlBtn = By.xpath("//button[@ng-click = 'withdrawl()']");
	By amount = By.xpath("//input[@ng-model = 'amount']");
	By submitBtn = By.xpath("//button[@type = 'submit']");
	By visibleName = By.xpath("//*[@class = 'fontBig ng-binding']");
	By details = By.xpath("//strong[@class = 'ng-binding']");

	public void chooseNameFromList(String name) {
		helper.driverWait(Helper.DELAY_MEDIUM);
		Select drpCharacter = new Select(driver.findElement(drp));
		drpCharacter.selectByVisibleText(name);
		helper.driverWait(Helper.DELAY_MEDIUM);
		driver.findElement(loginBtn).click();
		helper.driverWait(Helper.DELAY_MEDIUM);
	}

	public void logOut() {
		driver.findElement(logoutBtn).click();
	}

	public String getRandomUser() {
		Select drpCharacter = new Select(driver.findElement(drp));
		int rnd = new Random().nextInt(drpCharacter.getOptions().size());
		return drpCharacter.getOptions().get(rnd).getText();
	}

	public boolean verifyLogin(String name) {
		return driver.findElement(visibleName).getText().equals(name);
	}

	public int readBalance() {
		helper.driverWait(Helper.DELAY_MEDIUM);
		return Integer.parseInt(driver.findElements(details).get(1).getText());

	}

	public void deposit(String amountToDeposit) {
		driver.findElement(depositBtn).click();
		helper.driverWait(Helper.DELAY_MEDIUM);
		driver.findElement(amount).sendKeys(amountToDeposit);
		helper.driverWait(Helper.DELAY_MEDIUM);
		driver.findElement(submitBtn).click();
		helper.driverWait(Helper.DELAY_MEDIUM);
	}

	public void withdrawl(String amountToWithdrawl) {
		driver.findElement(withdrawlBtn).click();
		helper.driverWait(Helper.DELAY_MEDIUM);
		driver.findElement(amount).sendKeys(amountToWithdrawl);
		helper.driverWait(Helper.DELAY_MEDIUM);
		driver.findElement(submitBtn).click();
	}
}
