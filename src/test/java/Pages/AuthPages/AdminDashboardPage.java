package Pages.AuthPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import Pages.ProductPages.ProductManagementPage;

public class AdminDashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor
    public AdminDashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    // Check for success message by ID
    public boolean checkMessageById(String expectedMessage) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notify_message")));
            String actualMessage = messageElement.getText();
            System.out.println("Actual success message: '" + actualMessage + "'");
            return actualMessage.contains(expectedMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Logout from the admin panel
    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Wait for the toast message to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("notify_message")));
        // Click on the logout link
        WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Logout")));
        logoutLink.click();
    }

    // Navigate to User Registration page
    public UserRegistrationPage clickAddUser() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[1]/li[6]/a[2]")));
        driver.findElement(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[1]/li[6]/a[2]")).click();
        return new UserRegistrationPage(driver);
    }

    // Navigate to Product Creation page
    public ProductManagementPage clickAddProduct() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[1]/li[3]/a[2]")));
        driver.findElement(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[1]/li[3]/a[2]")).click(); 
        return new ProductManagementPage(driver);
    }

    // Click on the Products link
    public void clickProductsLink() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[1]/li[3]/a[1]")));
        driver.findElement(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[1]/li[3]/a[1]")).click();
    }
}
