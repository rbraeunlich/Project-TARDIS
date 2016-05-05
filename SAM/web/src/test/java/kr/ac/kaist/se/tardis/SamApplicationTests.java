package kr.ac.kaist.se.tardis;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This test requires an installed Firefox browser on your PC!
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SamApplication.class)
@WebIntegrationTest
public class SamApplicationTests {

	private static WebDriver webDriver;

	@Value("${local.server.port}")
	private int port;

	private String testUrl;

	@BeforeClass
	public static void setUpBeforeClass() {
		webDriver = new FirefoxDriver();
	}

	@AfterClass
	public static void tearDownAfterClass() {
		webDriver.quit();
	}

	@Before
	public void setUp() {
		testUrl = "http://localhost:" + port;
	}

	@Test
	public void succesfulLoginAfterRegistration() {
		webDriver.get(testUrl);
		// create account
		webDriver.findElement(By.id("registerButton")).click();
		webDriver.findElement(By.id("username")).sendKeys("foo");
		webDriver.findElement(By.id("password")).sendKeys("bar");
		webDriver.findElement(By.id("createButton")).click();
		// login
		webDriver.findElement(By.id("username")).sendKeys("foo");
		webDriver.findElement(By.id("password")).sendKeys("bar");
		webDriver.findElement(By.id("loginButton")).click();

		String currentUrl = webDriver.getCurrentUrl();
		assertThat(currentUrl, containsString("overview"));
	}

}
