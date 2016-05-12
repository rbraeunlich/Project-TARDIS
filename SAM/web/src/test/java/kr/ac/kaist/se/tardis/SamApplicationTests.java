package kr.ac.kaist.se.tardis;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
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

	private static final String PASSWORD = "bar";

	private static final String USERNAME = "foo";

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
	public void succesfulLoginAfterRegistration() throws InterruptedException {
		webDriver.get(testUrl);
		// try to log in without account
		webDriver.findElement(By.id("loginButton")).click();
		assertThat(webDriver.findElement(By.id("errorUsername")).getText(), is(notNullValue()));
		assertThat(webDriver.findElement(By.id("errorPassword")).getText(), is(notNullValue()));
		// create account
		webDriver.findElement(By.id("registerButton")).click();
		webDriver.findElement(By.id("username")).sendKeys(USERNAME);
		webDriver.findElement(By.id("password")).sendKeys(PASSWORD);
		webDriver.findElement(By.id("createButton")).click();
		// login
		webDriver.findElement(By.id("username")).sendKeys(USERNAME);
		webDriver.findElement(By.id("password")).sendKeys(PASSWORD);
		webDriver.findElement(By.id("loginButton")).click();
		//check redirect
		String currentUrl = webDriver.getCurrentUrl();
		assertThat(currentUrl, containsString("overview"));
		// create project
		webDriver.findElement(By.id("createProjectIcon")).click();
		// first create an error
		webDriver.findElement(By.id("createProjectButton")).click();
		//check errormessage
		assertThat(webDriver.findElement(By.id("errorProjectName")).getText(), is(notNullValue()));
		//now really create a project
		String projectName = "project";
		webDriver.findElement(By.id("createProjectIcon")).click();
		webDriver.findElement(By.id("projectname")).sendKeys(projectName);
		webDriver.findElement(By.id("description")).sendKeys("An example project");
		webDriver.findElement(By.id("createProjectButton")).click();
		// check that project is being shown
		webDriver.findElement(By.id(projectName)).click();
	}

}
