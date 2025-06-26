package Pages.ShoppingPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductInfoPage {
    private WebDriver driver;

    // Locators
    private By addToCartButton = By.xpath("//button[text()='Add to cart']");
    private By addReviewButton = By.xpath("//*[@id=\"add-review\"]");  
    private By reviewTitleField = By.xpath("//*[@id=\"review-title\"]"); 
    private By reviewDescriptionField = By.xpath("//*[@id=\"review-description\"]");
    private By reviewRatingField = By.xpath("//*[@id=\"review-rating\"]");
    private By submitReviewButton = By.xpath("//*[@id=\"addReview\"]");
    private By addProductButton = By.xpath("//*[@id='frm_edit_product_save']");

    // Constructor
    public ProductInfoPage(WebDriver driver) {
        this.driver = driver;
    }

    // Product Actions
    public void clickAddToCart() {
        driver.findElement(addToCartButton).click();
    }

    public void clickAddProductButton() {
        driver.findElement(addProductButton).click();
    }

    // Review Actions
    public void clickAddReviewButton() {
        driver.findElement(addReviewButton).click();
    }

    public void enterReviewTitle(String title) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement reviewTitleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(reviewTitleField));
        reviewTitleElement.sendKeys(title);
    }

    public void enterReviewDescription(String description) {
        driver.findElement(reviewDescriptionField).sendKeys(description);
    }

    public void enterReviewRating(String rating) {
        driver.findElement(reviewRatingField).sendKeys(rating);
    }

    public void clickSubmitReviewButton() {
        driver.findElement(submitReviewButton).click();
    }
}
