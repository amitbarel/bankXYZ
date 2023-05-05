package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Helper {
	private final int DELAY_BIG = 10000, DELAY_MEDIUM = 7500, DELAY_SMALL = 5000;
	private WebDriver driver;
	public static final String BASE_URL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/";
	
	
	public String getBASE_URL() {
		return BASE_URL;
	}


	public Helper(WebDriver driver) {
		this.driver = driver;
	}


	public ArrayList<String> usersList() {
		driver.get(this.BASE_URL + "login");
		ArrayList<String> names = new ArrayList<String>();
		driver.findElement(By.xpath("//button[@ng-click = 'manager()']")).click();
		driverWait(DELAY_MEDIUM);
		driver.findElement(By.xpath("//button[@ng-class = 'btnClass3']")).click();
		driverWait(DELAY_SMALL);
		WebElement table = driver.findElement(By.xpath("//table[@class = 'table table-bordered table-striped']"));
		List<WebElement> rows = table.findElements(By.cssSelector("tr.ng-scope"));
		List<WebElement> columns = table.findElements(By.cssSelector("td.ng-binding"));
		for (WebElement row : rows) {
		    List<WebElement> cells = row.findElements(By.cssSelector("td.ng-binding"));
		    names.add(cells.get(0).getText().concat(" "+cells.get(1).getText()));
		}
		return names;
	}
	
	public void driverWait(int delay) {
		driver.manage().timeouts().implicitlyWait(delay, TimeUnit.MILLISECONDS);
	}
}
