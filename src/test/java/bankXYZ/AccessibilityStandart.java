package bankXYZ;

import freemarker.template.TemplateException;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.sridharbandi.HtmlCsRunner;
import io.github.sridharbandi.a11y.HTMLCS;
import utils.Helper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import bankXYZ.pages.CustomersPage;
import bankXYZ.pages.HomePage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class AccessibilityStandart {

	private WebDriver driver;
	private static HtmlCsRunner htmlCsRunner;
	private Helper helper;
	private final String directoryPath = "target\\java-a11y\\htmlcs\\json";

	@Before
	public void beforeTest() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		helper = new Helper(driver);
		htmlCsRunner = new HtmlCsRunner(driver);
		htmlCsRunner.setStandard(HTMLCS.WCAG2A);

		File directory = new File(directoryPath);
		File[] files = directory.listFiles();

		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					file.delete();
				}
			}
		}
	}

	@After
	public void tearDown() throws TemplateException, IOException, URISyntaxException {
		htmlCsRunner.execute();
		driver.quit();
		htmlCsRunner.generateHtmlReport();
		extractDetailsToLog();
	}

	public void extractDetailsToLog() {
		String detailsStr = "";
		File directory = new File(directoryPath);
		File[] files = directory.listFiles();
		JSONParser jsonParser = new JSONParser();
		detailsStr += "------- Accessibility Test -------\n";
		System.out.println(detailsStr);
		try {
			Object obj = jsonParser.parse(new FileReader(files[0].getPath()));
			JSONObject jsonObject = (JSONObject) obj;
			String browser = (String) jsonObject.get("browser");
			detailsStr += "The test is operated on " + browser + "\n";
			String date = (String) jsonObject.get("date");
			detailsStr += "On " + date + "\n";
			Long errors = (Long) jsonObject.get("errors");
			detailsStr += "And produced " + errors + " errors:\n";
			JSONArray resultsArray = (JSONArray) jsonObject.get("results");
			for (int i = 0; i < resultsArray.size(); i++) {
				JSONObject res = (JSONObject) resultsArray.get(i);
				Long type = (Long) res.get("type");
				if (type == 1) { // 1 is for error
					String message = (String) res.get("msg");
					detailsStr += "Error type " + type + ": " + message + "\n";
				}
			}
			FileWriter writer = new FileWriter(".\\Logs\\AccessibilityResults.txt");
			writer.write(detailsStr);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void accesabilityTest() {
		HomePage homePage = new HomePage(driver, helper);
		CustomersPage customersPage = new CustomersPage(driver, helper);
		driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
		homePage.clickCustomer();
		String user = customersPage.getRandomUser();
		customersPage.chooseNameFromList(user);
	}

}
