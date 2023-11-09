package view;

import application.Application;
import java.util.concurrent.TimeoutException;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import static org.testfx.matcher.base.NodeMatchers.isFocused;
import static org.testfx.api.FxAssert.verifyThat;
import static org.junit.Assert.assertEquals;

/**
 * This class is for testing the SignIn controller.
 *
 * @author Leire
 * @author Nerea
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignInWindowControllerTest extends ApplicationTest {

    private PasswordField pfPassword = lookup("#pfPassword").query();

    private TextField tfPassword = lookup("#tfPassword").query();

    private TextField tfEmail = lookup("#tfEmail").query();

    private Label lblEmailError = lookup("#lblEmailError").query();
    
    private Label lblError = lookup("#lblError").query();

    /**
     * Set up Java FX fixture for tests. This is a general approach for using a
     * unique instance of the application in the test.
     *
     * @throws java.util.concurrent.TimeoutException
     */
    @BeforeClass
    public static void ClickApplicationTest() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Application.class);
    }
    
    /**
     * Test of initStage method, of class SignInWindowController.
     */
    @Test
    public void test0_InitStage() {
        verifyThat("#tfEmail", hasText(""));
        verifyThat("#pfPassword", hasText(""));
        verifyThat("#tfPassword", hasText(""));
        verifyThat("#tfEmail", isFocused());
        verifyThat("#btnAccept", isDisabled());
    }

    /**
     * Sign in test with an existing user.
     */
    @Test
    public void test1_SignInCorrect(){
        clickOn("#tfEmail");
        write("nerea@gmail.com");
        clickOn("#pfPassword");
        write("Abcd*1234");
        verifyThat("#btnAccept", isEnabled());
        clickOn("#btnAccept");
        
        verifyThat("#pnLogged", isVisible());
        
        clickOn("#btnLogOut");
        verifyThat("Are you sure that you want to log out?", isVisible());
        clickOn("Aceptar");
        
        verifyThat("#signInPane", isVisible());
    }
    
    /**
     * Sign in test with a not existing user. LoginCredentialException.
     */
    @Test
    public void test2_SignInIncorrectLoginCredentialException(){
        clickOn("#tfEmail");
        write("usernotexit@gmail.com");
        clickOn("#pfPassword");
        write("Abcd*1234");
        verifyThat("#btnAccept", isEnabled());
        clickOn("#btnAccept");       
        
        assertEquals(lblError.getText(), "Unknown user, please change the login or the password.");
        
        clickOn("#tfEmail");
        eraseText(21);
        clickOn("#pfPassword");
        eraseText(9);
    }
    
    /**
     * Sign in test with an existing user. ServerErrorException.
     */
    //@Test 
    public void test3_SignInCorrectWithServerErrorException(){
        clickOn("#tfEmail");
        write("nerea@gmail.com");
        clickOn("#pfPassword");
        write("Abcd*1234");
        verifyThat("#btnAccept", isEnabled());
        clickOn("#btnAccept");
        
        verifyThat("Internal Server Error: We're experiencing technical difficulties. Please try again later or contact our support team for assistance.", isVisible());
        clickOn("Aceptar");
    }

    /**
     * Test that labels works.
     */
    //@Test
    public void test4_TextChangedMaxLenght() {
        clickOn("#tfEmail");
        write("anKWrJSmLstpszSrbQCrLyHXZEALczejpAVWYjkclZMOBSeNXFanKWrJSmLstpszSrbQCrLyHXZEALczejpAVWYjkclZMOBSeNXFanKWrJSmLstpszSrbQCrLyHXZEALczejpAVWYjkclZMOBSeNXFanKWrJSmLstpszSrbQCrLyHXZEALczejpAVWYjkclZMOBSeNXFanKWrJSmLstpszSrbQCrLyHXZEALczejpAVWYjkclZMOBSeNXFanKWrJSmLstpszSrbQCrLyHXZEALcrbQCrLyHXZEALcrbQCrLyH");
        assertEquals(lblEmailError.getText(), "The maximum lenght for the Email is 300 characters,\n please change it.");
        eraseText(301);
        write("nerea@gmail.com");
        eraseText(15);
        
        assertEquals(lblEmailError.getText(), "");
        
        clickOn("#pfPassword");
        write("anKWrJSmLstpszSrbQCrLyHXZEALczejpAVWYjkclZMOBSeNXFanKWrJSmLstpszSrbQCrLyHXZEALczejpAVWYjkclZMOBSeNXFanKWrJSmLstpszSrbQCrLyHXZEALczejpAVWYjkclZMOBSeNXFanKWrJSmLstpszSrbQCrLyHXZEALczejpAVWYjkclZMOBSeNXFanKWrJSmLstpszSrbQCrLyHXZEALczejpAVWYjkclZMOBSeNXFanKWrJSmLstpszSrbQCrLyHXZEALcrbQCrLyHXZEALcrbQCrLyH");
        assertEquals(lblError.getText(), "The maximum lenght for the Password is 300\ncharacters, please change it.");
        eraseText(301);
        write("abcd*1234");
        eraseText(9);

        assertEquals(lblError.getText(), "");
    }

    /**
     * Test of handelEyeToggleButtonAction method, of class
     * SignInWindowController.
     */
    @Test
    public void test5_HandelEyeToggleButtonAction() {
        clickOn("#pfPassword");
        write("abcd*1234");
        
        clickOn("#tgbEye");

        Assert.assertFalse(pfPassword.isVisible());
        verifyThat("#tfPassword", isVisible());

        clickOn("#tgbEye");

        Assert.assertFalse(tfPassword.isVisible());
        verifyThat("#pfPassword", isVisible());
        clickOn("#pfPassword");
        eraseText(9);
    }

    /**
     * Test if the pfPassword and the tfPassword have the same text.
     */
    @Test
    public void test6_PasswordSameText() {
        clickOn("#pfPassword");
        write("abcd*1234");
        
        verifyThat("#pfPassword", hasText("abcd*1234"));
        verifyThat("#tfPassword", hasText("abcd*1234"));

        clickOn("#pfPassword");
        eraseText(9);
    }

    /**
     * Test that button Accept is disabled when email and password fields are
     * not full.
     */
    @Test
    public void test7_AcceptButtonIsDisabled() {
        clickOn("#tfEmail");
        write("nerea@gmail.com");
        verifyThat("#btnAccept", isDisabled());
        eraseText(15);

        clickOn("#pfPassword");
        write("abcd*1234");
        verifyThat("#btnAccept", isDisabled());
        eraseText(9);
        verifyThat("#btnAccept", isDisabled());
    }

    /**
     * Test that button Accept is enabled.
     */
    @Test
    public void test8_HandelAcceptButtonActionEnabled() {
        clickOn("#tfEmail");
        write("nerea@gmail.com");
        verifyThat("#btnAccept", isDisabled());

        clickOn("#pfPassword");
        write("abcd*1234");
        verifyThat("#btnAccept", isEnabled());
    }

    /**
     * Test that SignUp view is opened when hyperlink Sign Up is clicked.
     */
    @Test
    public void test9_HandelSignUpHyperlink() {
        clickOn("#httpSignUp");
        verifyThat("#signUpWindow", isVisible());
    }

    /**
     * Testing the label when you close the window.
     */
    //@Test
    public void test10_HandleOnActionExit() {
        closeCurrentWindow();
        verifyThat("Are you sure you want to exit the application?", isVisible());
        clickOn("Aceptar");
    }
}
