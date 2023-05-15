package bankXYZ.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import utils.Customer;
import utils.Helper;

public class ManagerPage {
	private WebDriver driver;
	private Helper helper;
	private Logger logger = LogManager.getLogger(this);

	public ManagerPage(WebDriver driver, Helper helper) {
		this.driver = driver;
		this.helper = helper;
	}

	By home = By.xpath("//button[@ng-click = 'home()']");
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

	public void goToHomePage() {
		driver.findElement(home).click();
		helper.driverWait(Helper.DELAY_MEDIUM);
	}
	
	public void clickAddCustomer() {
		logger.debug("Clicking add customer button");
		helper.driverWait(Helper.DELAY_MEDIUM);
		driver.findElement(addCustomer).click();
	}

	public void addNewCustomer(String f, String l, String p) {
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

	public boolean isInCustomersTable(String name, String number) {
		logger.debug("Checking if customer " + name + " with account number " + number + " is in the table");
		ArrayList<Customer> customers = getCustomersFromTable();
		for (Customer c: customers) {
			if (c.getfName().concat(" " + c.getlName()).equals(name)) {
				logger.debug("Accounts for customer " + name + ": " + c.getAccounts());
				return c.getAccounts().contains(number);				
			}
		}
		return false;
	}

	private List<WebElement> readCustomersTable() {
		driver.findElement(customers).click();
		helper.driverWait(Helper.DELAY_SMALL);
		WebElement table = driver.findElement(customersTable);
		List<WebElement> rows = table.findElements(tableRows);
		return rows;
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

	public void openAccountForCustomer(String fullname) {
		helper.driverWait(Helper.DELAY_MEDIUM);
		Select drpCustomer = new Select(driver.findElement(customerDrp));
		drpCustomer.selectByVisibleText(fullname);
		logger.info("Selected customer: " + fullname);
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
		List<WebElement> rows = readCustomersTable();
		int rnd = new Random().nextInt(rows.size());
		List<WebElement> element = rows.get(rnd).findElements(cell);
		String deleteName = element.get(0).getText().concat(" " + element.get(1).getText());
		helper.driverWait(Helper.DELAY_MEDIUM);
		WebElement deletedBtn = driver.findElement(customersTable).findElement(getDeleteBtnLocatorByIndex(rnd + 1));
		logger.info("Clicked Delete button for customer: " + deleteName);
		deletedBtn.click();
		return deleteName;
	}
	
	public ArrayList<Customer> getCustomersFromTable(){
		ArrayList<Customer> customers = new ArrayList<Customer>();
		List<WebElement> rows = readCustomersTable();
		for (int i = 0; i < rows.size(); i++) {
			for (WebElement element : rows) {
				Customer c = new Customer();
				List<WebElement> cells = element.findElements(cell);
				c.setfName(cells.get(0).getText());
				c.setlName(cells.get(1).getText());
				c.setPostCode(cells.get(2).getText());
				c.setAccounts(Arrays.asList(cells.get(3).getText().split(" ")));
				customers.add(c);
			}
		}
		return customers;		
	}

	public void searchDetails(String input) {
		logger.debug("Searching for customer details: " + input);
		helper.driverWait(Helper.DELAY_MEDIUM);
		driver.findElement(searchCustomer).sendKeys(input);
		helper.driverWait(Helper.DELAY_SMALL);
	}
}
