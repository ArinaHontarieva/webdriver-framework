package tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class EndToEndNegativeTests extends BaseTest {

    @Test(description = "Verify registration fails with invalid data and proper validation errors are shown")
    public void testRegistrationWithInvalidData() {
        SoftAssert softAssert = new SoftAssert();

        openWelcomePageAndClickLogin();
        softAssert.assertTrue(isHeaderPresent("h1", "Ласкаво просимо до Wantora!"),
            "Login page header is not displayed as expected");

        redirectToRegisterPage();
        softAssert.assertTrue(isHeaderPresent("h1", "Реєстрація в Wantora"),
            "Register page header is not displayed as expected");

        fillRegistrationFormAndSubmit(
            " ", " ",
            "invalidemail",
            "password", "pasword",
            "01012025");

        softAssert.assertTrue(isErrorVisibleById("emptyFname"),
            "Expected error for empty first name is not displayed");

        softAssert.assertTrue(isErrorVisibleById("emptyLname"),
            "Expected error for empty last name is not displayed");

        softAssert.assertTrue(isErrorVisibleById("emailError"),
            "Expected error for invalid email is not displayed");

        softAssert.assertTrue(isErrorVisibleById("passwordError"),
            "Expected error for weak password is not displayed");

        softAssert.assertTrue(isErrorVisibleById("matchError"),
            "Expected error for empty confirm password is not displayed");

        softAssert.assertTrue(isErrorVisibleById("ageError"),
            "Expected error for age restriction is not displayed");

        softAssert.assertAll();
    }
}