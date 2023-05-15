package bankXYZ.pages;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import utils.Helper;

public class CustomersPage {
	private WebDriver driver;
	private Helper helper;
	private Logger logger = LogManager.getLogger(this);

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
		logger.info("Clicked on login button.");
		helper.driverWait(Helper.DELAY_MEDIUM);
	}

	public void logOut() {
		driver.findElement(logoutBtn).click();
		logger.info("Clicked on logout button.");
	}

	public String getRandomUser() {
		Select drpCharacter = new Select(driver.findElement(drp));
		int rnd = new Random().nextInt(drpCharacter.getOptions().size()-1)+1;
		String randomUser = drpCharacter.getOptions().get(rnd).getText();
		logger.info("Selected random user: " + randomUser);
		return randomUser;
	}

	public boolean verifyLogin(String name) {
		boolean isLoginSuccessful = driver.findElement(visibleName).getText().equals(name);
		if (isLoginSuccessful) {
			logger.info("Login successful for user: " + name);
		} else {
			logger.info("Login unsuccessful for user: " + name);
		}
		return isLoginSuccessful;
	}

	public int readBalance() {
		helper.driverWait(Helper.DELAY_MEDIUM);
		int balance = Integer.parseInt(driver.findElements(details).get(1).getText());
		logger.info("Read balance: " + balance);
		return balance;
	}

	public void deposit(String amountToDeposit) {
		driver.findElement(depositBtn).click();
		logger.info("Clicked on deposit button.");
		helper.driverWait(Helper.DELAY_MEDIUM);
		driver.findElement(amount).sendKeys(amountToDeposit);
		logger.info("Entered deposit amount: " + amountToDeposit);
		helper.driverWait(Helper.DELAY_MEDIUM);
		driver.findElement(submitBtn).click();
		logger.info("Clicked on submit button.");
		helper.driverWait(Helper.DELAY_MEDIUM);
		driver.findElement(amount).clear();
	}

	public void withdrawl(String amountToWithdrawl) {
		driver.findElement(withdrawlBtn).click();
		logger.info("Clicked on withdraw button.");
		helper.driverWait(Helper.DELAY_MEDIUM);
		driver.findElement(amount).sendKeys(amountToWithdrawl);
		logger.info("Entered withdrawal amount: " + amountToWithdrawl);
		helper.driverWait(Helper.DELAY_MEDIUM);
		driver.findElement(submitBtn).click();
		logger.info("Clicked on submit button.");
	}

}
