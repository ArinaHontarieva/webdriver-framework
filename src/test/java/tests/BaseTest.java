package tests;

import factory.DriverFactory;
import helpers.PropertiesHelper;
import hooks.Hooks;
import models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.time.Duration;

public abstract class BaseTest extends Hooks {

    protected final Logger LOG = LogManager.getLogger(this.getClass());

    public WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    protected boolean isHeaderPresent(String tag, String text) {
        LOG.debug("Checking presence of element <{}> with text '{}'", tag, text);
        try {
            new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//" + tag + "[text()='" + text + "']")));
            LOG.info("Header found: <{}>{}</{}>", tag, text, tag);
            return true;
        } catch (TimeoutException e) {
            LOG.warn("Header not found: <{}>{}</{}>", tag, text, tag);
            return false;
        }
    }

    protected void openWelcomePageAndClickLogin() {
        LOG.info("Opening Welcome Page and clicking Login button");
        WelcomePage welcomePage = new WelcomePage(getDriver());
        welcomePage.clickLoginButton();
    }

    protected void redirectToRegisterPage() {
        LOG.info("Redirecting to Registration page");
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.clickRedirectToRegistrationButton();
    }

    protected void fillRegistrationFormAndSubmit(User user) {
        LOG.info("Filling registration form for user: {}", user.getEmail());
        RegisterPage registerPage = new RegisterPage(getDriver());

        registerPage.fillName(user.getName());
        registerPage.fillLastName(user.getLastName());
        registerPage.fillEmail(user.getEmail());
        registerPage.fillPassword(user.getPassword());
        registerPage.fillConfirmPassword(user.getConfirmPassword());
        registerPage.fillBirthDate(user.getBirthDate());

        LOG.info("Submitting registration form");
        registerPage.clickRegisterButton();
    }

    protected void goToProfile() {
        LOG.info("Navigating to user profile");
        MainWishlistPage mainWishlistPage = new MainWishlistPage(getDriver());
        mainWishlistPage.clickProfileIcon();
    }

    protected void updateUserEmail(String newEmail) {
        LOG.info("Updating user email to: {}", newEmail);
        ProfilePage profilePage = new ProfilePage(getDriver());
        profilePage.updateEmail(newEmail);
    }

    protected boolean isProfileUpdateSuccessMessageShown() {
        LOG.debug("Checking if profile update success message is visible");
        ProfilePage profilePage = new ProfilePage(getDriver());
        boolean result = profilePage.isProfileUpdatedMessageVisible();
        LOG.debug("Profile update success message visible: {}", result);
        return result;
    }

    protected void deleteUserAccount() {
        LOG.info("Deleting user account");
        ProfilePage profilePage = new ProfilePage(getDriver());
        profilePage.clickDeleteButton();
    }

    protected void openCreateWishlistModal() {
        LOG.info("Opening create wishlist modal");
        MainWishlistPage mainPage = new MainWishlistPage(getDriver());
        mainPage.clickCreateWishlistButton();
    }

    protected void fillCreateWishlistForm(String wishlistName) {
        LOG.info("Filling create wishlist form with title: {}", wishlistName);
        CreateWishlistWindow window = new CreateWishlistWindow(getDriver());

        window.fillTitle(wishlistName);
        window.fillDate("01012055");
        window.fillDescription("This is a test wishlist");

        window.clickCreate();
        LOG.info("Wishlist created successfully");
    }

    protected boolean isWishlistWithTitlePresent(String title) {
        LOG.debug("Checking if wishlist with title '{}' is present", title);
        MainWishlistPage mainPage = new MainWishlistPage(getDriver());
        boolean result = mainPage.isWishlistWithTitlePresent(title);
        LOG.debug("Wishlist with title '{}' present: {}", title, result);
        return result;
    }

    protected void openWishlistByTitle(String title) {
        LOG.info("Opening wishlist with title: {}", title);
        MainWishlistPage mainPage = new MainWishlistPage(getDriver());
        mainPage.clickWishlistByTitle(title);
    }

    protected void assertWishlistTitleEquals(String expectedTitle, SoftAssert softAssert) {
        LOG.debug("Asserting wishlist title equals expected: {}", expectedTitle);
        WishlistDetailsPage detailsPage = new WishlistDetailsPage(getDriver());
        String actualTitle = detailsPage.getWishlistTitle();
        softAssert.assertEquals(actualTitle, expectedTitle, "Wishlist title should match the expected value");
        LOG.info("Wishlist title assertion done. Expected: '{}', Actual: '{}'", expectedTitle, actualTitle);
    }

    protected void openCreateWishModal() {
        LOG.info("Opening create wish modal");
        WishlistDetailsPage detailsPage = new WishlistDetailsPage(getDriver());
        detailsPage.openCreateWishModal();
    }

    protected void createWish(String title) {
        LOG.info("Creating wish with title: {}", title);
        CreateWishWindow wishWindow = new CreateWishWindow(getDriver());
        wishWindow.fillTitle(title);
        wishWindow.clickSave();
        LOG.info("Wish '{}' created", title);
    }

    protected boolean isErrorVisibleById(String id) {
        LOG.debug("Checking if error with id '{}' is visible", id);
        try {
            WebElement errorElement = getDriver().findElement(By.id(id));
            boolean displayed = errorElement.isDisplayed();
            LOG.debug("Error element with id '{}' visible: {}", id, displayed);
            return displayed;
        } catch (NoSuchElementException e) {
            LOG.warn("Error element with id '{}' not found", id);
            return false;
        }
    }
}
