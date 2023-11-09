package view;

import application.Application;
import java.util.Random;
import java.util.concurrent.TimeoutException;
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
     * Set up the test environment before executing any test cases.
     *
     * @throws TimeoutException if there's a timeout while setting up the JavaFX
     * application.
     */
    /*
    @Before
    public void setUp() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Application.class);
    }*/
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
        verifyThat("#tfEmail", hasText(""));
        verifyThat("#tfPassword", hasText(""));
        verifyThat("#tfConfirmPassword", hasText(""));
        verifyThat("#pfPassword", hasText(""));
        verifyThat("#pfPassword", hasText(""));
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

        clickOn("#tfEmail");
        write(email);

        clickOn("#pfPassword");
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

        clickOn("#tfEmail");
        write("igarzon@gmail.com");

        clickOn("#pfPassword");
        write("Abcd*1234");

        clickOn("#pfConfirmPassword");
        write("Abcd*1234");

        clickOn("#btnSignUp");

        verifyThat("'" + lookup("#tfEmail").queryAs(TextField.class).getText() + "'" + " already exists. Please either try a different email or log in if you already have an account.", isVisible());
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

        clickOn("#tfEmail");
        write(email);

        clickOn("#pfPassword");
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

        clickOn("#tfEmail");
        write(email);

        clickOn("#pfPassword");
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

        clickOn("#tfEmail");
        write(email);

        clickOn("#pfPassword");
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
    //@Test
    public void test6_HandelEyeToggleButtonAction() {
        clickOn("#pfPassword");
        write("abcd*1234");

        clickOn("#tgbEye");

        verifyThat("#pfPassword", isInvisible());
        verifyThat("#tfPassword", isVisible());

        clickOn("#tgbEye");

        verifyThat("#tfPassword", isInvisible());
        verifyThat("#pfPassword", isVisible());

        clickOn("#pfConfirmPassword");
        write("abcd*1234");

        clickOn("#tgbEye");

        verifyThat("#pfConfirmPassword", isInvisible());
        verifyThat("#tfConfirmPassword", isVisible());

        clickOn("#tgbEye");

        verifyThat("#tfConfirmPassword", isInvisible());
        verifyThat("#pfConfirmPassword", isVisible());

    }

    /**
     * Test case to check if the passwords fields have the same text that the
     * text field
     */
    //@Test
    public void test7_PasswordSameText() {
        clickOn("#pfPassword");
        write("abcd*1234");

        verifyThat("#pfPassword", hasText("abcd*1234"));
        verifyThat("#tfPassword", hasText("abcd*1234"));

        clickOn("#pfConfirmPassword");
        write("abcd*1234");

        verifyThat("#pfConfirmPassword", hasText("abcd*1234"));
        verifyThat("#tfConfirmPassword", hasText("abcd*1234"));
    }

    /**
     * Test Signup button functionality to verify if it is enabled with the mandatory fields or not.
     */
    //@Test
    public void test8_SignUpButton() {

        clickOn("#tfFirstName");
        write("Name");
        verifyThat("#btnSignUp", isDisabled());

        clickOn("#tfLastName");
        write("Surname");
        verifyThat("#btnSignUp", isDisabled());

        clickOn("#tfEmail");
        write(email);
        verifyThat("#btnSignUp", isDisabled());

        clickOn("#pfPassword");
        write("Abcd*1234");
        verifyThat("#btnSignUp", isDisabled());

        clickOn("#pfConfirmPassword");
        write("Abcd*1234");

        verifyThat("#btnSignUp", isEnabled());

        clickOn("#pfConfirmPassword");
        eraseText(9);
        verifyThat("#btnSignUp", isDisabled());

        doubleClickOn("#pfPassword");
        eraseText(9);
        verifyThat("#btnSignUp", isDisabled());

        doubleClickOn("#tfEmail");
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
     * Testing the label when you close the window.
     */
    @Test
    public void test9_HandleOnActionExit() {
        closeCurrentWindow();
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
