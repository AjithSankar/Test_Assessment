package stepDefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Login {
	
	static WebDriver driver;

	@Given("Open the Browser")
	public void open_the_browser() {
		WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
	}
	
	@When("Navigate to Webpage")
	public void navigate_to_webpage() {
		driver.get("https://www.banfico.com/");
	}
	
	@Then("Enter the Credentials")
	public void enter_the_credentials() {
	}
	
	@Then("Log In")
	public void log_in() {
		driver.close();
	}
	
}
