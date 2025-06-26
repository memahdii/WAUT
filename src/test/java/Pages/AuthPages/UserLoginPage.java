package Pages.AuthPages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserLoginPage {
    private WebDriver driver;

    // Locators for the login page elements
    private By emailField = By.name("email");
    private By passwordField = By.name("password");
    private By signInButton = By.xpath("//button[text()='Sign in']");


    public UserLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Method to enter email in the email field
    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    // Method to enter password in the password field
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    // Method to click on the sign-in button
    public void clickSignIn() {
        driver.findElement(signInButton).click();
    }

    // Method to perform login as admin
    public AdminDashboardPage loginAsAdmin(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
        return new AdminDashboardPage(driver);
    }
}

