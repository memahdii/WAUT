package Pages.ShoppingPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private WebDriver driver;

    // Locators
    private By productLink = By.xpath("//*[@id=\"container\"]/div/div[1]/div/div[1]/div/div/a/h3");
    private By cartLink = By.xpath("//a[contains(text(),'Cart')]");
    private By cartCount = By.xpath("//a[contains(text(),'Cart')]/span[@class='badge badge-danger']");
    private By homeLink = By.linkText("Home");
    private By searchField = By.xpath("//*[@id=\"navbarMenu\"]/form/div/input");
    private By searchButton = By.xpath("//*[@id=\"navbarMenu\"]/form/div/div/button");
    private By accountIcon = By.xpath("//*[@id=\"navbarText\"]/ul/li[1]/a");
    private By recentReviewsLink = By.linkText("Recent reviews");

    // Constructor
    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Account-related methods
    public void clickAccountIcon() {
        driver.findElement(accountIcon).click();
    }

    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement accountIconElement = wait.until(ExpectedConditions.elementToBeClickable(accountIcon));
            accountIconElement.click();
            WebElement logoutElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='navbarText']/ul/div[2]/div/li[2]/a")));
            logoutElement.click();
        } catch (Exception e) {
            System.out.println("Logout action failed: " + e.getMessage());
        }
    }

    // Review-related methods
    public void clickRecentReviews() {
        driver.findElement(recentReviewsLink).click();
    }

    // Search-related methods
    public void enterSearchText(String searchText) {
        driver.findElement(searchField).sendKeys(searchText);
    }

    public void clickSearchButton() {
        driver.findElement(searchButton).click();
    }

    // Product-related methods
    public void clickProductLink() {
        driver.findElement(productLink).click();
    }

    // Cart-related methods
    public void clickCartLink() {
        driver.findElement(cartLink).click();
    }

    public String getCartCount() {
        return driver.findElement(cartCount).getText();
    }

    // Navigation methods
    public void clickHomeLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement homeLinkElement = wait.until(ExpectedConditions.elementToBeClickable(homeLink));
        homeLinkElement.click();
    }
}
