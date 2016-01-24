package firstlft;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hp.lft.report.ReportException;
import com.hp.lft.report.Reporter;
import com.hp.lft.report.Status;
import com.hp.lft.sdk.*;
import com.hp.lft.sdk.web.*;

// import junit.framework.Assert;
import unittesting.*;

public class LeanFtTest extends UnitTestClassBase {
	
	private Browser browser;

	public LeanFtTest() {
		//Change this constructor to private if you supply your own public constructor
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		instance = new LeanFtTest();
		globalSetup(LeanFtTest.class);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		globalTearDown();
	}

	@Before
	public void setUp() throws Exception {
		// BrowserType BrowserType.INTERNET_EXPLORER;
		browser = BrowserFactory.launch(BrowserType.INTERNET_EXPLORER);
		browser.navigate("http://swongdemo.cloudapp.net");
	}

	@After
	public void tearDown() throws Exception {
		browser.close();
	}

	@Test
	public void test() throws GeneralLeanFtException {
		EditField usernameEdit = browser.describe(EditField.class, new EditFieldDescription.Builder()
				.type("text").tagName("INPUT").name("user_name").build());
		EditField passwordEdit = browser.describe(EditField.class, new EditFieldDescription.Builder()
				.type("password").tagName("INPUT").name("user_password").build());
		
		Button loginButton = browser.describe(Button.class, new ButtonDescription.Builder()
				.buttonType("submit").tagName("INPUT").name("Log In").build());
		
		usernameEdit.setValue("admin");
		passwordEdit.setValue("HPS0ftw@re!");
		
		loginButton.click();
		
		// verify that the Welcome message is displayed
		WebElement welcomeMessage = browser.describe(WebElement.class, new WebElementDescription.Builder()
				.tagName("DIV").innerText("Welcome, SugarCRM Administrator [ Log Out ]").build());
		Assert.assertTrue(welcomeMessage.exists());
		
		// verify using Regular Expression
		WebElement welcomeMessageRegExp = browser.describe(WebElement.class, new WebElementDescription.Builder()
				.tagName("DIV").innerText(new RegExpProperty("Welcome, SugarCRM .* \\[ Log Out \\]")).build());
		Assert.assertTrue(welcomeMessageRegExp.exists());
		
		try {
			// Reporter.reportEvent("Logged In", "Log in successful");
			Reporter.reportEvent("Log In", "Logged In Successfully", Status.Passed, browser.getSnapshot());
		} catch (ReportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// logout
		Link logoutLink = browser.describe(Link.class, new LinkDescription.Builder()
				.tagName("A").innerText("Log Out").build());
		
		logoutLink.click();
		
		// Verify logged out successfully by checking that the username field is shown again
		Assert.assertTrue(usernameEdit.exists());
		
		browser.getSnapshot();
		
		
	}

}
 