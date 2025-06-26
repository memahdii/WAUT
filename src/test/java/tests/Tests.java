package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;

import Pages.AuthPages.AdminDashboardPage;
import Pages.AuthPages.UserLoginPage;
import Pages.AuthPages.UserRegistrationPage;
import Pages.ProductPages.ProductManagementPage;
import Pages.ShoppingPages.ShoppingCartPage;
import Pages.ShoppingPages.MainPage;
import Pages.ShoppingPages.ProductInfoPage;


@TestMethodOrder(OrderAnnotation.class)
public class Tests extends DriverLifeCycle {

    private UserLoginPage userLoginPage;
    private AdminDashboardPage adminDashboardPage;
    private UserRegistrationPage userRegistrationPage;

    private void loginAsAdmin(String email, String password) {
        driver.get("http://localhost:3000/admin/login");
        userLoginPage = new UserLoginPage(driver);
        adminDashboardPage = userLoginPage.loginAsAdmin(email, password);
    }

    private void logout() {
        adminDashboardPage.logout();
    }

    
    @Test
    @Order(1)
    public void testAddUser() {
        loginAsAdmin("owner@test.com", "e2eW3Bt3s71nGB3nchM4rK");
        userRegistrationPage = adminDashboardPage.clickAddUser();
        userRegistrationPage.enterUserName("TestUser000");
        userRegistrationPage.enterUserEmail("test000@test.com");
        userRegistrationPage.enterPassword("password");
        userRegistrationPage.clickCreate();
        boolean isMessagePresent = adminDashboardPage.checkMessageById("User account inserted");
        assertTrue(isMessagePresent, "Expected success message was not found!");
        logout();
    }


    @Test
    @Order(2)
    public void testAddEmptyUser() {
        loginAsAdmin("owner@test.com", "e2eW3Bt3s71nGB3nchM4rK");
        userRegistrationPage = adminDashboardPage.clickAddUser();
        userRegistrationPage.clickCreate();
        assertTrue(userRegistrationPage.areAllFieldsHighlightedInRed(), "Expected all fields to be highlighted in red due to missing information.");
        logout();
    }


    @Test
    @Order(3)
    public void testLoginUser() {
        driver.get("http://localhost:3000/admin/login");
        userLoginPage = new UserLoginPage(driver);
        adminDashboardPage = userLoginPage.loginAsAdmin("test000@test.com", "password");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dashboardElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"container\"]/div/nav/div/ul[1]/li[2]/a")));
        assertTrue(dashboardElement.isDisplayed(), "Expected 'Dashboard' element to be displayed after logging in.");
        logout();
    }


    @Test
    @Order(4)
    public void testAddExistingUserFails() {
        loginAsAdmin("owner@test.com", "e2eW3Bt3s71nGB3nchM4rK");
        userRegistrationPage = adminDashboardPage.clickAddUser();
        userRegistrationPage.enterUserName("TestUser000");
        userRegistrationPage.enterUserEmail("test000@test.com");
        userRegistrationPage.enterPassword("password");
        userRegistrationPage.clickCreate();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement alertElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'alert-danger')]")));
        String alertText = alertElement.getText();
        assertTrue(alertText.contains("A user with that email address already exists"), "Expected error message for duplicate user was not displayed.");
        logout();
    }


    @Test
    @Order(5)
    public void testAddProduct() {
        loginAsAdmin("owner@test.com", "e2eW3Bt3s71nGB3nchM4rK");
        adminDashboardPage.clickAddProduct();
        ProductManagementPage productManagementPage = new ProductManagementPage(driver);
        productManagementPage.enterProductTitle("NewProduct000");
        productManagementPage.enterProductPrice("15.95");
        productManagementPage.enterProductDescription("Description for product 000");
        productManagementPage.clickAddProductButton();
        adminDashboardPage.clickProductsLink();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement productElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"container\"]/div/main/div[3]/ul/li[2]/div/a")));
        assertTrue(productElement.isDisplayed(), "Expected 'NewProduct000' to be displayed in the first row of the product table!.");
        logout();
    }


    @Test
    @Order(6)
    public void testAddEmptyProduct() {
        loginAsAdmin("owner@test.com", "e2eW3Bt3s71nGB3nchM4rK");
        adminDashboardPage.clickAddProduct();
        ProductManagementPage productManagementPage = new ProductManagementPage(driver);
        productManagementPage.clickAddProductButton();
        assertTrue(productManagementPage.areTitleAndPriceFieldsHighlightedInRed(), "Expected 'Product Title' and 'Product Price' fields to be highlighted in red due to missing information.");
        logout();
    }


    @Test
    @Order(7)
    public void testAddNewProdToCart() {
        driver.get("http://localhost:3000/");
        MainPage mainPage = new MainPage(driver);
        ProductInfoPage productInfoPage = new ProductInfoPage(driver);
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        mainPage.clickProductLink();
        productInfoPage.clickAddToCart();
        mainPage.clickHomeLink();
        String cartCount = mainPage.getCartCount();
        assertEquals("1", cartCount, "Expected cart count to be 1 after adding product to cart.");
        mainPage.clickCartLink();
        assertTrue(shoppingCartPage.isProductInCart(), "Expected 'NewProduct000' to be displayed in the cart.");
    }


    @Test
    @Order(8)
    public void testSearchProduct() {
        driver.get("http://localhost:3000/");
        MainPage mainPage = new MainPage(driver);
        mainPage.enterSearchText("NewProduct000");
        mainPage.clickSearchButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchResultsHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'Search results')]")));
        assertTrue(searchResultsHeader.isDisplayed(), "Expected 'Search results' to be displayed.");
        int numberOfProductsInResults = driver.findElements(By.xpath("//div[@class='row product-layout']//h3[contains(text(), 'NewProduct000')]")).size();
        assertEquals(1, numberOfProductsInResults, "Expected only 'NewProduct000' to be displayed in the search results.");
    }



    @Test
    @Order(9)
    public void testAddReview() {
        driver.get("http://localhost:3000/");
        MainPage mainPage = new MainPage(driver);
        ProductInfoPage productInfoPage = new ProductInfoPage(driver);
        mainPage.clickAccountIcon();
        UserLoginPage userLoginPage = new UserLoginPage(driver);
        userLoginPage.enterEmail("test@test.com");
        userLoginPage.enterPassword("e2eW3Bt3s71nGB3nchM4rK");
        userLoginPage.clickSignIn();
        mainPage.clickHomeLink();
        mainPage.clickProductLink(); 
        productInfoPage.clickAddReviewButton();
        productInfoPage.enterReviewTitle("Review000");
        productInfoPage.enterReviewDescription("Description000");
        productInfoPage.enterReviewRating("5");
        productInfoPage.clickSubmitReviewButton();
        mainPage.clickRecentReviews();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement reviewTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"collapseReviews\"]/li/p[3]")));
        WebElement reviewDescription = driver.findElement(By.xpath("//*[@id=\"collapseReviews\"]/li/p[4]"));
        WebElement reviewRating = driver.findElement(By.xpath("//*[@id=\"collapseReviews\"]/li/p[2]"));
        assertTrue(reviewTitle.getText().contains("Title: Review000"), "Expected review title 'Review000' to be shown on the page.");
        assertTrue(reviewDescription.getText().contains("Description: Description000"), "Expected review description 'Description000' to be shown on the page.");
        assertTrue(reviewRating.getText().contains("Rating: 5"), "Expected review rating '5' to be shown on the page.");
        mainPage.clickAccountIcon();
        mainPage.logout();
    }


    @Test
    @Order(10)
    public void testAddEmptyReview() {
        driver.get("http://localhost:3000/");
        MainPage mainPage = new MainPage(driver);
        ProductInfoPage productInfoPage = new ProductInfoPage(driver);
        UserLoginPage userLoginPage = new UserLoginPage(driver);
        mainPage.clickAccountIcon();
        userLoginPage.enterEmail("test@test.com");
        userLoginPage.enterPassword("e2eW3Bt3s71nGB3nchM4rK");
        userLoginPage.clickSignIn();
        mainPage.clickHomeLink();
        mainPage.clickProductLink(); 
        productInfoPage.clickAddReviewButton();
        productInfoPage.clickSubmitReviewButton(); 
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Please supply a review title')]")));
        assertTrue(errorMessage.isDisplayed(), "Expected error message 'Please supply a review title' was not displayed.");
        mainPage.logout();
    }


}
