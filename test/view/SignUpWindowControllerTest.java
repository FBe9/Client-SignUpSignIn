package view;

import application.Application;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 * This class contains test cases for the SignUpWindowController in a JavaFX
 * application.
 *
 *
 * @author Irati
 * @author Olivia
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpWindowControllerTest extends ApplicationTest {

    //Generates a randon number
    private int randomNumber = new Random().nextInt(9000) + 1000;
    //Creates a email with a random number
    private String email = "example" + randomNumber + "@gmail.com";

    /**
     * Initializes the application to perform the tests.
     *
     * @param stage The JavaFX Stage object provided by the system to start the
     * application.
     * @throws Exception If an exception occurs while starting the application.
     */
    @Override
    public void start(Stage stage) throws Exception {
        new Application().start(stage);
        clickOn("#httpSignUp");

    }

    /**
     * Test case to initialize the application stage and verify initial state.
     */
    @Test
    public void test0_initStage() {
        verifyThat("#tfFirstName", hasText(""));
        verifyThat("#tfLastName", hasText(""));
        verifyThat("#tfEmailSignUp", hasText(""));
        verifyThat("#tfPasswordSignUp", hasText(""));
        verifyThat("#tfConfirmPassword", hasText(""));
        verifyThat("#pfPasswordSignUp", hasText(""));
        verifyThat("#pfConfirmPassword", hasText(""));
        verifyThat("#tfStreet", hasText(""));
        verifyThat("#tfZip", hasText(""));
        verifyThat("#tfMobile", hasText(""));
        verifyThat("#btnSignUp", isDisabled());
    }

    /**
     * Test case for the sign-up process.
     */
   @Test
    public void test1_signUp() {

        clickOn("#tfFirstName");
        write("Name");

        clickOn("#tfLastName");
        write("Surname");

        clickOn("#tfEmailSignUp");
        write(email);

        clickOn("#pfPasswordSignUp");
        write("Abcd*1234");

        clickOn("#pfConfirmPassword");
        write("Abcd*1234");

        clickOn("#tfStreet");
        write("Tartanga");

        clickOn("#tfCity");
        write("Erandio");

        clickOn("#tfZip");
        write("48013");

        clickOn("#tfMobile");
        write("667285969");

        clickOn("#btnSignUp");

        verifyThat(lookup("#tfFirstName").queryAs(TextField.class).getText() + " " + lookup("#tfLastName").queryAs(TextField.class).getText() + ", you have successfully registered.", isVisible());
        clickOn("Aceptar");
        verifyThat("#signInPane", isVisible());
    }

    /**
     * Test case for handling an email already exists exception during sign-up.
     */
    @Test
    public void test2_signUpEmailAlreadyExitsException() {

        clickOn("#tfFirstName");
        write("Name");

        clickOn("#tfLastName");
        write("Surname");

        clickOn("#tfEmailSignUp");
        write("igarzon@gmail.com");

        clickOn("#pfPasswordSignUp");
        write("Abcd*1234");

        clickOn("#pfConfirmPassword");
        write("Abcd*1234");

        clickOn("#btnSignUp");

        verifyThat("'" + lookup("#tfEmailSignUp").queryAs(TextField.class).getText() + "'" + " already exists. Please either try a different email or log in if you already have an account.", isVisible());
        clickOn("Aceptar");

    }

    /**
     * Test case for handling a server error exception during sign-up.
     */
   @Test
    public void test3_signUpServerErrorException() {
        clickOn("#tfFirstName");
        write("Name");

        clickOn("#tfLastName");
        write("Surname");

        clickOn("#tfEmailSignUp");
        write(email);

        clickOn("#pfPasswordSignUp");
        write("Abcd*1234");

        clickOn("#pfConfirmPassword");
        write("Abcd*1234");

        clickOn("#btnSignUp");

        verifyThat(lookup("#tfFirstName").queryAs(TextField.class).getText() + " " + lookup("#tfLastName").queryAs(TextField.class).getText() + ", we're experiencing technical difficulties. Please try again later or contact our support team for assistance.", isVisible());
        clickOn("Aceptar");

    }

    /**
     * Test case for handling a max error exception during sign-up.
     */
    @Test
    public void test4_signUpServerErrorExceptionMaxCapacity() {

        clickOn("#tfFirstName");
        write("Name");

        clickOn("#tfLastName");
        write("Surname");

        clickOn("#tfEmailSignUp");
        write(email);

        clickOn("#pfPasswordSignUp");
        write("Abcd*1234");

        clickOn("#pfConfirmPassword");
        write("Abcd*1234");

        clickOn("#btnSignUp");

        verifyThat(lookup("#tfFirstName").queryAs(TextField.class).getText() + " " + lookup("#tfLastName").queryAs(TextField.class).getText() + ", the server is at max capacity, please try again later.", isVisible());
        clickOn("Aceptar");

    }

    /**
     * Test case for handling a timeout exception during sign-up.
     */
    @Test
    public void test5_signUpServerErrorExceptionTimeOut() {
        clickOn("#tfFirstName");
        write("Name");

        clickOn("#tfLastName");
        write("Surname");

        clickOn("#tfEmailSignUp");
        write(email);

        clickOn("#pfPasswordSignUp");
        write("Abcd*1234");

        clickOn("#pfConfirmPassword");
        write("Abcd*1234");

        clickOn("#btnSignUp");

        verifyThat(lookup("#tfFirstName").queryAs(TextField.class).getText() + " " + lookup("#tfLastName").queryAs(TextField.class).getText() + ", the connection has timed out. Our server is taking too long to respond.", isVisible());
        clickOn("Aceptar");

    }

    /**
     * Test case for verify the behavior of the "Eye" toggle button.
     */
    @Test
    public void test6_HandelEyeToggleButtonAction() {
        clickOn("#pfPasswordSignUp");
        write("abcd*1234");

        clickOn("#tgbEyeSignUp");

        verifyThat("#pfPasswordSignUp", isInvisible());
        verifyThat("#tfPasswordSignUp", isVisible());

        clickOn("#tgbEyeSignUp");

        verifyThat("#tfPasswordSignUp", isInvisible());
        verifyThat("#pfPasswordSignUp", isVisible());

        clickOn("#pfConfirmPassword");
        write("abcd*1234");

        clickOn("#tgbEyeSignUp");

        verifyThat("#pfConfirmPassword", isInvisible());
        verifyThat("#tfConfirmPassword", isVisible());

        clickOn("#tgbEyeSignUp");

        verifyThat("#tfConfirmPassword", isInvisible());
        verifyThat("#pfConfirmPassword", isVisible());

    }

    /**
     * Test case to check if the passwords fields have the same text that the
     * text field
     */
   @Test
    public void test7_PasswordSameText() {
        clickOn("#pfPasswordSignUp");
        write("abcd*1234");

        verifyThat("#pfPasswordSignUp", hasText("abcd*1234"));
        verifyThat("#tfPasswordSignUp", hasText("abcd*1234"));

        clickOn("#pfConfirmPassword");
        write("abcd*1234");

        verifyThat("#pfConfirmPassword", hasText("abcd*1234"));
        verifyThat("#tfConfirmPassword", hasText("abcd*1234"));
    }

    /**
     * Test Signup button functionality to verify if it is enabled with the
     * mandatory fields or not.
     */
    @Test
    public void test8_SignUpButton() {

        clickOn("#tfFirstName");
        write("Name");
        verifyThat("#btnSignUp", isDisabled());

        clickOn("#tfLastName");
        write("Surname");
        verifyThat("#btnSignUp", isDisabled());

        clickOn("#tfEmailSignUp");
        write("no@gmail.com");
        verifyThat("#btnSignUp", isDisabled());

        clickOn("#pfPasswordSignUp");
        write("Abcd*1234");
        verifyThat("#btnSignUp", isDisabled());

        clickOn("#pfConfirmPassword");
        write("Abcd*1234");

        verifyThat("#btnSignUp", isEnabled());

        doubleClickOn("#pfConfirmPassword");
        eraseText(9);
        verifyThat("#btnSignUp", isDisabled());

        doubleClickOn("#pfPasswordSignUp");
        eraseText(9);
        verifyThat("#btnSignUp", isDisabled());

        doubleClickOn("#tfEmailSignUp");
        eraseText(21);
        verifyThat("#btnSignUp", isDisabled());

        doubleClickOn("#tfLastName");
        eraseText(7);
        verifyThat("#btnSignUp", isDisabled());

        doubleClickOn("#tfFirstName");
        eraseText(4);
        verifyThat("#btnSignUp", isDisabled());

    }

    /**
     * Testing the alert when you close the window.
     */
    @Test
    public void test9_HandleOnActionExit() {
        closeCurrentWindow();
        verifyThat("Are you sure you want to exit the application?", isVisible());
        clickOn("Aceptar");
    }

    /**
     * Testing the alert when you click in cancel button.
     */
    @Test
    public void test91_handleCancelButton() {
        clickOn("#btnCancel");
        verifyThat("Are you sure you want to exit the application?", isVisible());
        clickOn("Aceptar");
    }

    /**
     * Clean up the test environment after executing all test cases.
     *
     * @throws TimeoutException if there's a timeout while cleaning up.
     */
    @After
    public void tearDown() throws TimeoutException {
        //Cleaning up and closing JavaFX stages.
        FxToolkit.cleanupStages();
    }

}
