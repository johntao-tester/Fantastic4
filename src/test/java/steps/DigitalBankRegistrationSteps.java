package steps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.lexer.Th;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import utilities.Driver;
import utilities.MockData;
import domains.User;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * TODO:
 * Add implementations of steps from feature file
 */
public class DigitalBankRegistrationSteps {
    private WebDriver driver;
    private final String LOGIN_PAGE_URL = "http://dbankdemo.com/login";
    private final String HOME_PAGE_URL = "http://dbankdemo.com/home";
    private final String SIGNUP_PAGE_URL = "http://dbankdemo.com/signup";


    @Given("^User navigates to Digital Bank login page$")
    public void user_navigates_to_Digital_Bank_login_page() throws Throwable {
        driver = Driver.getDriver();
        driver.get(LOGIN_PAGE_URL);
        assertEquals(LOGIN_PAGE_URL, driver.getCurrentUrl());
    }

    @Given("^Verify that web title is \"([^\"]*)\"$")
    public void verify_that_web_title_is(String expectedTitle) throws Throwable {
        assertEquals(expectedTitle, driver.getTitle());
    }

    @When("^User logs in with following credentials$")
    public void user_logs_in_with_following_credentials(DataTable dataTable) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
        // E,K,V must be a scalar (String, Integer, Date, enum etc)
        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));

        List<Map<String, String>> creds = dataTable.asMaps(String.class, String.class);

        username.sendKeys(creds.get(0).get("username"));
        password.sendKeys(creds.get(0).get("password"));
        WebElement submit = driver.findElement(By.id("submit"));
        submit.click();
    }

    @Then("^User successfully logged in to home page$")
    public void user_successfully_logged_in_to_home_page() throws Throwable {
        assertEquals(HOME_PAGE_URL, driver.getCurrentUrl());
        Thread.sleep(6000);
        driver.close();
    }

    @When("^User logs in with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void user_logs_in_with_and(String user, String pass) throws Throwable {
        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        username.sendKeys(user);
        password.sendKeys(pass);
        WebElement submit = driver.findElement(By.id("submit"));
        submit.click();
    }

    @Then("^User should be displayed with the error message \"([^\"]*)\"$")
    public void user_should_be_displayed_with_the_error_message(String expectedErrorMessage) throws Throwable {
        WebElement error = driver.findElement(By.xpath("//div[@class='sufee-alert alert with-close alert-danger alert-dismissible fade show']"));
        assertTrue(error.isDisplayed());
        String actualErrorMessage = error.getText().trim().replaceAll("\\n", "").replace("×", "");
//        System.out.println(actualErrorMessage);
        assertEquals(expectedErrorMessage, actualErrorMessage);
        driver.close();

    }

    @Given("^User navigates to Digital Bank signup page$")
    public void user_navigates_to_Digital_Bank_signup_page() throws Throwable {
        driver = Driver.getDriver();
        driver.get(SIGNUP_PAGE_URL);
        assertEquals(SIGNUP_PAGE_URL, driver.getCurrentUrl());

    }

    @When("^User creates account with following fields$")
    public void user_creates_account_with_following_fields(DataTable dataTable) throws Throwable {

        List<User> userInfo = dataTable.asList(User.class);
        MockData mockData = new MockData();
        WebElement title = driver.findElement(By.id("title"));
        WebElement firstName = driver.findElement(By.id("firstName"));
        WebElement lastName = driver.findElement(By.id("lastName"));
        WebElement genderM = driver.findElement(By.cssSelector("input[value='M']"));
        WebElement genderF = driver.findElement(By.cssSelector("input[value='F']"));
        WebElement dob = driver.findElement(By.id("dob"));
        WebElement ssn = driver.findElement(By.id("ssn"));
        WebElement emailAddress = driver.findElement(By.id("emailAddress"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement confirmPassword = driver.findElement(By.id("confirmPassword"));
        WebElement submitBtn = driver.findElement(By.xpath("//button[@type='submit']"));
        new Select(title).selectByVisibleText(userInfo.get(0).getTitle());
        firstName.sendKeys(userInfo.get(0).getFirstName());
        lastName.sendKeys(userInfo.get(0).getLastName());
        if (userInfo.get(0).getGender().equalsIgnoreCase("M")) {
            genderM.click();
        } else {
            genderF.click();
        }

        dob.sendKeys(userInfo.get(0).getDob());
        ssn.sendKeys(mockData.generatesRandomSSN());
        emailAddress.sendKeys(mockData.generateRandomEmail());
        password.sendKeys(userInfo.get(0).getPassword());
        confirmPassword.sendKeys(userInfo.get(0).getPassword());
        submitBtn.click();


        WebElement address = driver.findElement(By.id("address"));
        WebElement locality = driver.findElement(By.id("locality"));
        WebElement region = driver.findElement(By.id("region"));
        WebElement postalCode = driver.findElement(By.id("postalCode"));
        WebElement country = driver.findElement(By.id("country"));
        WebElement homePhone = driver.findElement(By.id("homePhone"));
        WebElement mobilePhone = driver.findElement(By.id("mobilePhone"));
        WebElement workPhone = driver.findElement(By.id("workPhone"));
        WebElement agreeCheckBox = driver.findElement(By.id("agree-terms"));
        WebElement registerButton = driver.findElement(By.xpath("//button[@type='submit']"));

        Thread.sleep(3000);
        address.sendKeys(userInfo.get(0).getAddress());
        locality.sendKeys(userInfo.get(0).getLocality());
        region.sendKeys(userInfo.get(0).getRegion());
        postalCode.sendKeys(userInfo.get(0).getPostalCode());
        country.sendKeys(userInfo.get(0).getCountry());
        homePhone.sendKeys(userInfo.get(0).getHomePhone());
        mobilePhone.sendKeys(userInfo.get(0).getMobilePhone());
        workPhone.sendKeys(userInfo.get(0).getWorkPhone());

        agreeCheckBox.click();
        registerButton.click();


    }

    @Then("^User should be displayed with the message \"([^\"]*)\"$")
    public void user_should_be_displayed_with_the_message(String expectedSuccessMessage) throws Throwable {
        WebElement success = driver.findElement(By.xpath("//span[text()='Registration Successful. Please Login.']"));
        String sucMes =  success.getText().trim();
        assertTrue(success.isDisplayed());
        assertEquals(expectedSuccessMessage, sucMes);
        driver.close();


    }

}

