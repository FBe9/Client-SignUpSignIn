package view;

import application.Application;
import java.util.concurrent.TimeoutException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.api.FxAssert.verifyThat;

/**
 * This class is for testing the Logged controller.
 *
 * @author Leire
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoggedWindowControllerTest extends ApplicationTest {

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
     * Test Logged view is opened when button Accept is clicked. Test of initial
     * state of Logged view. Test of the alert Log Out Button.
     */
    @Test
    public void test1_LoggedLogOutButton() {
        clickOn("#tfEmail");
        write("nerea@gmail.com");
        clickOn("#pfPassword");
        write("Abcd*1234");
        verifyThat("#btnAccept", isVisible());
        clickOn("#btnAccept");

        verifyThat("#pnLogged", isVisible());
        verifyThat("Hello Nerea Apellido to our Application.", isVisible());

        clickOn("#btnLogOut");
        verifyThat("Are you sure that you want to log out?", isVisible());
        clickOn("Aceptar");
        
        verifyThat("#signInPane", isVisible());
    }

    /**
     * Test Logged view is opened when button Accept is clicked. Test of initial
     * state of Logged view. Test of the alert Exit Button.
     */
    @Test
    public void test2_LoggedExitButton() {
        clickOn("#tfEmail");
        write("nerea@gmail.com");
        clickOn("#pfPassword");
        write("Abcd*1234");
        verifyThat("#btnAccept", isVisible());
        clickOn("#btnAccept");
        
        verifyThat("#pnLogged", isVisible());
        verifyThat("Hello Nerea Apellido to our Application.", isVisible());

        //clickOn("tpMenu");
        clickOn("#btnExit");
        verifyThat("Are you sure that you want to close the application?", isVisible());
        clickOn("Aceptar");
    }

}
