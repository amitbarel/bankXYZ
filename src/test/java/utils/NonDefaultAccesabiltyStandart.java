package utils;

import freemarker.template.TemplateException;
import io.github.sridharbandi.AxeRunner;
//import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.sridharbandi.HtmlCsRunner;
import io.github.sridharbandi.a11y.HTMLCS;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.io.File;

/**
 * A sample test to demonstrate
 */
public class NonDefaultAccesabiltyStandart {

	private WebDriver driver;
	private static HtmlCsRunner htmlCsRunner;

	@Before

	public void beforeTest() {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\amitb\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().fullscreen();

		htmlCsRunner = new HtmlCsRunner(driver);
		htmlCsRunner.setStandard(HTMLCS.WCAG2A);

		String directoryPath = "target\\java-a11y\\htmlcs\\json"; // Replace with the path of the directory you want to
																	// delete files from

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

	}

	@Test
	public void googleTest() {
		driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
	}

}
