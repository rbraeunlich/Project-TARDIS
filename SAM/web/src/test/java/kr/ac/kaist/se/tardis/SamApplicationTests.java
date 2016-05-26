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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
		testUrl = "https://localhost:" + port;
	}

	@Test
	public void succesfulLoginAfterRegistration() throws InterruptedException, Exception {
		webDriver.get(testUrl);
		// try to log in without account
		webDriver.findElement(By.id("loginButton")).click();
		assertThat(webDriver.findElement(By.id("errorLogin")).getText(), is(notNullValue()));
		// create account
		webDriver.findElement(By.id("registerButton")).click();
		webDriver.findElement(By.id("username")).sendKeys(USERNAME);
		webDriver.findElement(By.id("password")).sendKeys(PASSWORD);
		webDriver.findElement(By.id("usernameRepeated")).sendKeys(USERNAME);
		webDriver.findElement(By.id("passwordRepeated")).sendKeys(PASSWORD);
		webDriver.findElement(By.id("createButton")).click();
		// create another account
		webDriver.findElement(By.id("registerButton")).click();
		webDriver.findElement(By.id("username")).sendKeys("User1");
		webDriver.findElement(By.id("password")).sendKeys("dummy");
		webDriver.findElement(By.id("usernameRepeated")).sendKeys("User1");
		webDriver.findElement(By.id("passwordRepeated")).sendKeys("dummy");
		webDriver.findElement(By.id("createButton")).click();
		// login
		webDriver.findElement(By.id("username")).sendKeys(USERNAME);
		webDriver.findElement(By.id("password")).sendKeys(PASSWORD);
		webDriver.findElement(By.id("loginButton")).click();
		// check redirect
		String currentUrl = webDriver.getCurrentUrl();
		assertThat(currentUrl, containsString("overview"));
		// create project
		webDriver.findElement(By.id("createProjectIcon")).click();
		// first create an error
		webDriver.findElement(By.id("createProjectButton")).click();
		// check errormessage
		assertThat(webDriver.findElement(By.id("errorProjectName")).getText(), is(notNullValue()));
		// now really create a project
		String projectName = "project";
		webDriver.findElement(By.id("createProjectIcon")).click();
		webDriver.findElement(By.id("projectname")).sendKeys(projectName);
		webDriver.findElement(By.id("description")).sendKeys("An example project");
		webDriver.findElement(By.id("createProjectButton")).click();
		// check that project is being shown
		webDriver.findElement(By.id(projectName)).click();
		// go into Kanban Board
		webDriver.findElement(By.id(projectName + "Kanban")).click();
		// check url
		currentUrl = webDriver.getCurrentUrl();
		assertThat(currentUrl, containsString("kanbanboard"));
		assertThat(currentUrl, containsString("projectId="));
		// check elements in Kanban Board
		new WebDriverWait(webDriver, 10L).until(ExpectedConditions.presenceOfElementLocated(By.id("projectName")));
		assertThat(webDriver.findElement(By.id("projectName")).getText(), containsString(projectName));
		// project setting - 1. change project name
		// go into project setting
		webDriver.findElement(By.id("projectSetting")).click();
		// check url
		currentUrl = webDriver.getCurrentUrl();
		assertThat(currentUrl, containsString("projectsettingview"));
		// try to rename project with 1 characters
		String newProjectName = "f";
		webDriver.findElement(By.id("projectName")).clear();
		webDriver.findElement(By.id("projectName")).sendKeys(newProjectName);
		webDriver.findElement(By.id("projectSettingSubmit")).click();
		new WebDriverWait(webDriver, 10L).until(ExpectedConditions.presenceOfElementLocated(By.id("projectNameError")));
		assertThat(webDriver.findElement(By.id("projectNameError")).getText(),
				containsString("Project name must contain at least three characters"));
		// change project name with more than 3 characters
		newProjectName = "new name";
		webDriver.findElement(By.id("projectName")).sendKeys(newProjectName);
		webDriver.findElement(By.id("projectSettingSubmit")).click();
		// check url
		currentUrl = webDriver.getCurrentUrl();
		assertThat(currentUrl, containsString("kanbanboard"));
		Thread.sleep(50); //give it a little time to load
		// check new project name
		assertThat(webDriver.findElement(By.id("projectName")).getText(), containsString(newProjectName));
		// project setting - 2. add new member
		// go into project setting
		webDriver.findElement(By.id("projectSetting")).click();
		// check url
		currentUrl = webDriver.getCurrentUrl();
		assertThat(currentUrl, containsString("projectsettingview"));
		// add un-exist user
		String newMemberName = "fake";
		webDriver.findElement(By.id("newMember")).sendKeys(newMemberName);
		webDriver.findElement(By.id("projectSettingSubmit")).click();
		new WebDriverWait(webDriver, 10L).until(ExpectedConditions.presenceOfElementLocated(By.id("newMemberError")));
		assertThat(webDriver.findElement(By.id("newMemberError")).getText(), containsString("No Existing User"));
		// add proper user
		newMemberName = "User1";
		webDriver.findElement(By.id("newMember")).clear();
		webDriver.findElement(By.id("newMember")).sendKeys(newMemberName);
		webDriver.findElement(By.id("projectSettingSubmit")).click();
		// check url
		currentUrl = webDriver.getCurrentUrl();
		assertThat(currentUrl, containsString("kanbanboard"));
		// try to create a task
		webDriver.findElement(By.id("createTaskIcon")).click();
		webDriver.findElement(By.id("createTaskButton")).click();
		assertThat( webDriver.getCurrentUrl(), containsString("kanbanboard"));
		new WebDriverWait(webDriver, 10L).until(ExpectedConditions.presenceOfElementLocated(By.id("errorwrapper")));
		// check error
		WebElement errorWrapper = webDriver.findElement(By.id("errorwrapper"));
		assertThat(errorWrapper.getText(), containsString("Task name must contain at least three characters"));
		assertThat(errorWrapper.getText(), containsString("Task must set due date"));
		// now really create one
		webDriver.findElement(By.id("createTaskIcon")).click();
		String taskName = "task name";
		webDriver.findElement(By.id("taskName")).sendKeys(taskName);
		String taskDescription = "task description";
		webDriver.findElement(By.id("taskDescription")).sendKeys(taskDescription);
		webDriver.findElement(By.id("dueDate")).sendKeys("2016-01-01");
		webDriver.findElement(By.id("createTaskButton")).click();
		// check new task present
		new WebDriverWait(webDriver, 10L).until(ExpectedConditions.presenceOfElementLocated(By.id("ToDo")));
		assertThat(webDriver.findElement(By.id("ToDo")).getText(), containsString(taskName));
		assertThat(webDriver.findElement(By.id("ToDo")).getText(), containsString(taskDescription));
		// HTML 5 drag and drop cannot be tested by Selenium
	}

}
