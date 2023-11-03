package view;

import application.Application;
import java.util.concurrent.TimeoutException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import static org.testfx.matcher.base.NodeMatchers.isFocused;
import static org.testfx.api.FxAssert.verifyThat;

/**
 * This class is for testing the SignIn controller.
 *
 * @author Leire
 * @author Nerea
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignInWindowControllerTest extends ApplicationTest {

    @FXML
    private PasswordField pfPassword;
    @FXML
    private TextField tfPassword;

    /**
     * Starts application to be tested.
     *
     * @param stage Primary Stage object
     * @throws Exception if there is any error
     */
    @Override
    public void start(Stage stage) throws Exception {
        new Application().start(stage);
    }

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
    public void test0_InitStage() {
        FxAssert.verifyThat("#tfEmail", hasText(""));
        FxAssert.verifyThat("#pfPassword", hasText(""));
        FxAssert.verifyThat("#tfPassword", hasText(""));
        FxAssert.verifyThat("#btnAccept", isDisabled());
        FxAssert.verifyThat("#tfEmail", isFocused());
        FxAssert.verifyThat("#btnAccept", isDisabled());
    }

    /**
     * Test that labels works. 
     */
    @Test
    public void test1_TextChanged() {
        clickOn("#tfEmail");
        write("anKWrJSmLstpszSrbQCrLyHXZEALczejpAVWYjkclZMOBSeNXFanKWrJSmLstpszSrbQCrLyHXZEALczejpAVWYjkclZMOBSeNXFanKWrJSmLstpszSrbQCrLyHXZEALczejpAVWYjkclZMOBSeNXFanKWrJSmLstpszSrbQCrLyHXZEALczejpAVWYjkclZMOBSeNXFanKWrJSmLstpszSrbQCrLyHXZEALczejpAVWYjkclZMOBSeNXFanKWrJSmLstpszSrbQCrLyHXZEALcrbQCrLyHXZEALcrbQCrLyH");
        verifyThat("#lblEmailError", hasText("The maximum lenght for the Email is 300 characters,\n please change it."));
        eraseText(301);
        write("nerea@gmail.com");

        verifyThat("#lblEmailError", hasText(""));

        clickOn("#pfPassword");
        write("anKWrJSmLstpszSrbQCrLyHXZEALczejpAVWYjkclZMOBSeNXFanKWrJSmLstpszSrbQCrLyHXZEALczejpAVWYjkclZMOBSeNXFanKWrJSmLstpszSrbQCrLyHXZEALczejpAVWYjkclZMOBSeNXFanKWrJSmLstpszSrbQCrLyHXZEALczejpAVWYjkclZMOBSeNXFanKWrJSmLstpszSrbQCrLyHXZEALczejpAVWYjkclZMOBSeNXFanKWrJSmLstpszSrbQCrLyHXZEALcrbQCrLyHXZEALcrbQCrLyH");
        verifyThat("#lblPasswordError", hasText("The maximum lenght for the Password is 300\ncharacters, please change it."));
        eraseText(301);
        write("abcd*1234");

        verifyThat("#lblError", hasText(""));
    }

    /**
     * Test of handelEyeToggleButtonAction method, of class
     * SignInWindowController.
     */
    @Test
    public void test2_HandelEyeToggleButtonAction() {
        clickOn("#tgbEye");

        Assert.assertFalse(pfPassword.isVisible());
        verifyThat("#tfPassword", isVisible());

        clickOn("#tgbEye");

        Assert.assertFalse(tfPassword.isVisible());
        verifyThat("#pfPassword", isVisible());
    }
    
    /**
     * Test if the pfPassword and the tfPassword have the same text.
     */
    @Test
    public void test3_PasswordSameText() {
        verifyThat("#pfPassword", hasText("abcd*1234"));
        eraseText(9);
        verifyThat("#tfPassword", hasText("abcd*1234"));
        eraseText(9);
        
    }

    /**
     * Test that button Accept is disabled when email and password fields are
     * not full.
     */
    @Test
    public void test4_AcceptButtonIsDisabled() {
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
    public void test5_HandelAcceptButtonActionEnabled() {
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
    public void test6_HandelSignUpHyperlink() {
        clickOn("#httpSignUp");
        verifyThat("#signUpWindow", isVisible());
    }

    /**
     * Testing the label when you close the window.
     */
    @Test
    public void test7_HandleOnActionExit() {
        closeCurrentWindow();
        verifyThat("Are you sure you want to exit the application?", isVisible());
        clickOn("Aceptar");
    }
}
