package tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class EndToEndPositiveTests extends BaseTest {

    @Test(description = "Positive test: registration, profile update flow with valid data, delete account")
    public void testProfileFlowPositive() {
        SoftAssert softAssert = new SoftAssert();

        openWelcomePageAndClickLogin();
        softAssert.assertTrue(isHeaderPresent("h1", "Ласкаво просимо до Wantora!"),
            "Login page header is not displayed as expected");

        redirectToRegisterPage();
        softAssert.assertTrue(isHeaderPresent("h1","Реєстрація в Wantora"),
            "Register page header is not displayed as expected");

        fillRegistrationFormAndSubmit(
            "Test", "Testov",
            "test_testov@gmail.com",
            "Password123", "Password123",
            "01012000");
        softAssert.assertTrue(isHeaderPresent("h1","Мої вішлісти"),
            "Main page header is not displayed as expected");

        goToProfile();
        softAssert.assertTrue(isHeaderPresent("h2","Особисті дані"),
            "Profile page header is not displayed as expected");

        String newEmail = "updated.email@example.com";
        updateUserEmail(newEmail);
        softAssert.assertTrue(isProfileUpdateSuccessMessageShown(),
            "Success message for profile update is not displayed");

        deleteUserAccount();

        softAssert.assertAll();
    }

    @Test(description = "Positive test: registration, create wishlist, create wish, delete account")
    public void testUserFlowRegisterAndCreateWishListWithWishPositive(){
        SoftAssert softAssert = new SoftAssert();

        openWelcomePageAndClickLogin();
        softAssert.assertTrue(isHeaderPresent("h1", "Ласкаво просимо до Wantora!"),
            "Login page header is not displayed as expected");

        redirectToRegisterPage();
        softAssert.assertTrue(isHeaderPresent("h1","Реєстрація в Wantora"),
            "Register page header is not displayed as expected");

        fillRegistrationFormAndSubmit(
            "Test", "Testov",
            "test_testov@gmail.com",
            "Password123", "Password123",
            "01012000");
        softAssert.assertTrue(isHeaderPresent("h1","Мої вішлісти"),
            "Main page header is not displayed as expected");

        openCreateWishlistModal();
        softAssert.assertTrue(isHeaderPresent("h2","Створення вішліста"),
            "Create wishlist form did not open properly");

        String wishlistName ="Test wishlist";
        fillCreateWishlistForm(wishlistName);
        softAssert.assertTrue(isWishlistWithTitlePresent(wishlistName),
            "Wishlist with title '" + wishlistName + "' should be visible");

        openWishlistByTitle(wishlistName);
        assertWishlistTitleEquals(wishlistName, softAssert);

        openCreateWishModal();
        softAssert.assertTrue(isHeaderPresent("h2","Нове бажання"),
            "Create wish form did not open properly");

        String wishName = "Мій перший подарунок";
        createWish(wishName);

        goToProfile();
        softAssert.assertTrue(isHeaderPresent("h2","Особисті дані"),
            "Profile page header is not displayed as expected");

        deleteUserAccount();

        softAssert.assertAll();
    }
}
