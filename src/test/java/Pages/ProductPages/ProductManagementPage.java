package Pages.ProductPages;


import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;




public class ProductManagementPage {
    private WebDriver driver;

    // Locators for product fields
    private By productTitleField = By.id("productTitle"); 
    private By productPriceField = By.id("productPrice"); 
    private By productDescriptionField = By.xpath("//*[@class='note-editable panel-body']");
    private By addProductButton = By.xpath("//*[@id='frm_edit_product_save']");
    


    public ProductManagementPage(WebDriver driver) {
        this.driver = driver;
    }


    // Method to enter product title
    public void enterProductTitle(String title) {
        driver.findElement(productTitleField).sendKeys(title);
    }

    // Method to enter product price
    public void enterProductPrice(String price) {
        driver.findElement(productPriceField).sendKeys(price);
    }

    // Method to enter product description
    public void enterProductDescription(String description) {
        driver.findElement(productDescriptionField).sendKeys(description);
    }    
    

    // Method to click on the 'Add Product' button
    public void clickAddProductButton() {
        driver.findElement(addProductButton).click();
    }
    

    // Method to check if title and price fields are highlighted in red (indicating an error)
    public boolean areTitleAndPriceFieldsHighlightedInRed() {
        List<By> fields = Arrays.asList(productTitleField, productPriceField);
        
        for (By field : fields) {
            WebElement fieldElement = driver.findElement(field);
            WebElement parentDiv = fieldElement.findElement(By.xpath("./ancestor::div[contains(@class, 'form-group')]"));
            String parentClasses = parentDiv.getAttribute("class");
            // Check for error classes in the parent div of the field
            if (!parentClasses.contains("has-error") && !parentClasses.contains("has-danger")) {
                return false;
            }
        }
        return true;
    }
    




}
