package Pages.AuthPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class UserRegistrationPage {
    private WebDriver driver;

    // Locators for the user creation fields
    private By usersNameField = By.id("usersName");
    private By userEmailField = By.id("userEmail");
    private By userPasswordField = By.id("userPassword");
    private By passwordConfirmField = By.xpath("//*[@id='userNewForm']/div[4]/input");
    private By createButton = By.xpath("//*[@id=\"btnUserAdd\"]");

    // Constructor
    public UserRegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    // Method to enter the user's name
    public void enterUserName(String userName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement userNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(usersNameField));
        userNameInput.sendKeys(userName);
    }

    // Method to enter the user's email
    public void enterUserEmail(String userEmail) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement userEmailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(userEmailField));
        userEmailInput.sendKeys(userEmail);
    }

    // Method to enter the password and confirm it
    public void enterPassword(String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement userPasswordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(userPasswordField));
        userPasswordInput.sendKeys(password);

        WebElement passwordConfirmInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordConfirmField));
        passwordConfirmInput.sendKeys(password);
    }

    // Method to click the create button
    public void clickCreate() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement createBtn = wait.until(ExpectedConditions.elementToBeClickable(createButton));
        createBtn.click();
    }

    // Method to check if all fields are highlighted in red (indicating an error)
    public boolean areAllFieldsHighlightedInRed() {
        List<By> fields = Arrays.asList(usersNameField, userEmailField, userPasswordField, passwordConfirmField);

        for (By field : fields) {
            WebElement fieldElement = driver.findElement(field);
            WebElement parentDiv = fieldElement.findElement(By.xpath("./ancestor::div[contains(@class, 'form-group')]"));
            String parentClasses = parentDiv.getAttribute("class");
            if (!parentClasses.contains("has-error") || !parentClasses.contains("has-danger")) {
                return false;
            }
        }
        return true;
    }
}
