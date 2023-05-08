package pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import utils.Helper;

public class ManagerPage {
	private WebDriver driver;
	private Helper helper;
	private Logger logger=LogManager.getLogger(this);

	public ManagerPage(WebDriver driver, Helper helper) {
		this.driver = driver;
		this.helper = helper;
	}

	By addCustomer = By.xpath("//button[@ng-click = 'addCust()']");
	By fName = By.xpath("//input[@ng-model = 'fName']");
	By lName = By.xpath("//input[@ng-model = 'lName']");
	By pCode = By.xpath("//input[@ng-model = 'postCd']");
	By openAccount = By.xpath("//button[@ng-click = 'openAccount()']");
	By customerDrp = By.name("userSelect");
	By currencyDrp = By.name("currency");
	By customers = By.xpath("//button[@ng-click = 'showCust()']");
	By submitBtn = By.xpath("//button[@type = 'submit']");
	By deleteBtn = By.xpath("//button[@ng-click = 'deleteCust(cust)']");
	By customersTable = By.xpath("//table[@class = 'table table-bordered table-striped']");
	By tableRows = By.cssSelector("tr.ng-scope");
	By tableCols = By.cssSelector("td.ng-binding");
	By cell = By.tagName("td");
	By searchCustomer = By.xpath("//input[@ng-model = 'searchCustomer']");

	private By getDeleteBtnLocatorByIndex(int index) {
	    logger.debug("Getting delete button locator by index " + index);
	    return By.xpath(".//tr[" + (index) + "]/td[5]/button");
	}

	public void clickAddCustomer() {
	    logger.debug("Clicking add customer button");
	    helper.driverWait(Helper.DELAY_MEDIUM);
	    driver.findElement(addCustomer).click();
	}

	public void insertDetails(String f, String l, String p) {
	    logger.debug("Inserting customer details: first name = " + f + ", last name = " + l + ", postal code = " + p);
	    helper.driverWait(Helper.DELAY_MEDIUM);
	    driver.findElement(fName).sendKeys(f);
	    driver.findElement(lName).sendKeys(l);
	    driver.findElement(pCode).sendKeys(p);
	    helper.driverWait(Helper.DELAY_SMALL);
	    driver.findElement(submitBtn).click();
	    logger.debug("Submitted customer details");
	}

	public boolean isInList(String name) {
	    logger.debug("Checking if customer " + name + " is in the list");
	    clickOpenAccount();
	    helper.driverWait(Helper.DELAY_MEDIUM);
	    Select drpCustomer = new Select(driver.findElement(customerDrp));
	    List<String> customers = new ArrayList<String>();
	    for (WebElement customer : drpCustomer.getOptions()) {
	        customers.add(customer.getText());
	    }
	    return customers.contains(name);
	}

	public boolean isInTable(String name, String number) {
	    logger.debug("Checking if customer " + name + " with account number " + number + " is in the table");
	    Map<String, ArrayList<String>> customersMap = new HashMap<String, ArrayList<String>>();
	    driver.findElement(customers).click();
	    helper.driverWait(Helper.DELAY_SMALL);
	    WebElement table = driver.findElement(customersTable);
	    List<WebElement> rows = table.findElements(tableRows);
	    List<WebElement> columns = table.findElements(tableCols);
	    for (WebElement element : rows) {
	        List<WebElement> cells = element.findElements(cell);
	        String tempName = cells.get(0).getText().concat(" " + cells.get(1).getText());
	        ArrayList<String> accounts = new ArrayList<String>(Arrays.asList(cells.get(3).getText().split(" ")));
	        customersMap.put(tempName, accounts);
	    }
	    logger.debug("Accounts for customer " + name + ": " + customersMap.get(name));
	    return customersMap.get(name).contains(number);
	}

	public String getRandomUser() {
	    logger.debug("Getting a random user");
	    Select drpCharacter = new Select(driver.findElement(customerDrp));
	    int rnd = new Random().nextInt(drpCharacter.getOptions().size());
	    return drpCharacter.getOptions().get(rnd).getText();
	}

	public void clickOpenAccount() {
	    logger.debug("Clicking open account button");
	    helper.driverWait(Helper.DELAY_SMALL);
	    driver.findElement(openAccount).click();
	}

	public void chooseDetails(String name) {
	    Logger logger = LogManager.getLogger(getClass());
	    helper.driverWait(Helper.DELAY_MEDIUM);
	    Select drpCustomer = new Select(driver.findElement(customerDrp));
	    drpCustomer.selectByVisibleText(name);
	    logger.info("Selected customer: " + name);
	    Select drpCurrency = new Select(driver.findElement(currencyDrp));
	    drpCurrency.selectByIndex(drpCurrency.getOptions().size() - 1);
	    helper.driverWait(Helper.DELAY_MEDIUM);
	    driver.findElement(submitBtn).click();
	    logger.info("Clicked Submit button");
	    helper.driverWait(Helper.DELAY_SMALL);
	}

	public void clickShowCustomers() {
	    Logger logger = LogManager.getLogger(getClass());
	    driver.findElement(customers).click();
	    logger.info("Clicked Show Customers button");
	}

	public String deleteCustomer() {
	    Logger logger = LogManager.getLogger(getClass());
	    driver.findElement(customers).click();
	    helper.driverWait(Helper.DELAY_SMALL);
	    WebElement table = driver.findElement(customersTable);
	    List<WebElement> rows = table.findElements(tableRows);
	    List<WebElement> columns = table.findElements(tableCols);
	    int rnd = new Random().nextInt(rows.size());
	    List<WebElement> element = rows.get(rnd).findElements(cell);
	    String deleteName = element.get(0).getText().concat(" " + element.get(1).getText());
	    helper.driverWait(Helper.DELAY_MEDIUM);
	    WebElement deletedBtn = table.findElement(getDeleteBtnLocatorByIndex(rnd + 1));
	    logger.info("Clicked Delete button for customer: " + deleteName);
	    deletedBtn.click();
	    return deleteName;
	}

//	public boolean verifyDeletionOfCustomer(String name) {
//		helper.driverWait(Helper.DELAY_SMALL);
//		driver.findElement(searchCustomer).sendKeys(name);
//		helper.driverWait(Helper.DELAY_MEDIUM);
//		WebElement table = driver.findElement(customersTable);
//		List<WebElement> rows = table.findElements(tableRows);
//		return rows.size()>0;
//	}

}
