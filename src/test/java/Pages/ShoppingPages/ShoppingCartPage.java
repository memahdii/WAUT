package Pages.ShoppingPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ShoppingCartPage {
    private WebDriver driver;

    // Locator for the product in the cart
    private By productInCart = By.xpath("//*[@id=\"cart\"]/div[1]/div");

    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
    }

    // Method to check if the product is present in the cart
    public boolean isProductInCart() {
        return driver.findElements(productInCart).size() > 0;
    }
}
