package pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.Helper;

public class ManagerPage {
	private final int DELAY_BIG = 10000, DELAY_MEDIUM = 7500, DELAY_SMALL = 5000;
	private WebDriver driver;
	private Helper helper;
	private ArrayList<String> accounts;

	@After
	public void tearDown() {
		// driver.quit();
	}

	@Before
	public void setUp() throws IOException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		helper = new Helper(driver);
		accounts = new ArrayList();
	}

	@Test
	public void testLogin() {
		Map<String, ArrayList<String>> customers = new HashMap<String, ArrayList<String>>();
		driver.get(Helper.BASE_URL.concat("manager"));
		driver.manage().window().maximize();
		helper.driverWait(DELAY_MEDIUM);
		driver.findElement(By.xpath("//button[@ng-class = 'btnClass3']")).click();
		helper.driverWait(DELAY_SMALL);
		WebElement table = driver.findElement(By.xpath("//table[@class = 'table table-bordered table-striped']"));
		List<WebElement> rows = table.findElements(By.cssSelector("tr.ng-scope"));
		List<WebElement> columns = table.findElements(By.cssSelector("td.ng-binding"));
		for (WebElement row : rows) {
		    List<WebElement> cells = row.findElements(By.tagName("td"));
		    String name = cells.get(0).getText().concat(" "+cells.get(1).getText());
		    ArrayList<String> accounts = new ArrayList<String>(Arrays.asList(cells.get(3).getText().split(" ")));
		    customers.put(name, accounts);
		}
		System.out.println(customers.toString());
	}

	public static void main(String args[]) {
		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));
		org.junit.runner.Result result = junit.run(ManagerPage.class);

		if (result.getFailureCount() > 0) {
			System.out.println("Test failed.");
			System.exit(1);
		} else {
			System.out.println("Test finished successfully.");
			System.exit(0);
		}
	}
}
