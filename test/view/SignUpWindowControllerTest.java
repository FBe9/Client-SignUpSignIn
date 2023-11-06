package view;

import application.Application;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
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
    }

    /**
     * Test case to initialize the application stage and verify initial state.
     */
    public void test0_initStage() {
        clickOn("#httpSignUp");

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
        clickOn("#httpSignUp");

        clickOn("#tfFirstName");
        write("Name");

        clickOn("#tfLastName");
        write("Surname");

        doubleClickOn("#tfEmail");
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

        verifyThat("You have successfully registered", isVisible());
        clickOn("Aceptar");
        verifyThat("#signInPane", isVisible());
    }

    /**
     * Test case for handling an email already exists exception during sign-up.
     */
    @Test
    public void test2_signUpEmailAlreadyExitsException() {
        clickOn("#httpSignUp");

        clickOn("#tfFirstName");
        write("Name");

        clickOn("#tfLastName");
        write("Surname");

        doubleClickOn("#tfEmail");
        write("alexsalineronegro@gmail.com");

        clickOn("#pfPassword");
        write("Abcd*1234");

        clickOn("#pfConfirmPassword");
        write("Abcd*1234");

        clickOn("#btnSignUp");

        verifyThat("Email already exists. Please either try a different email or log in if you already have an account.", isVisible());
        clickOn("Aceptar");

    }

    /**
     * Test case for handling a server error exception during sign-up.
     */
    public void test3_signUpServerErrorException() {

        clickOn("#httpSignUp");

        clickOn("#tfFirstName");
        write("Name");

        clickOn("#tfLastName");
        write("Surname");

        doubleClickOn("#tfEmail");
        write(email);

        clickOn("#pfPassword");
        write("Abcd*1234");

        clickOn("#pfConfirmPassword");
        write("Abcd*1234");

        clickOn("#btnSignUp");

        verifyThat("Internal Server Error: We're experiencing technical difficulties. Please try again later or contact our support team for assistance.", isVisible());
        clickOn("Aceptar");

    }

    /**
     * Clean up the test environment after executing all test cases.
     *
     * @throws TimeoutException if there's a timeout while cleaning up.
     */
    @After
    public void tearDown() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

}
