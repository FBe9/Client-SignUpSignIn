package application;

import factory.ClientFactory;
import interfaces.Signable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import view.*;

/**
 * Main application class. Start the application.
 *
 * @author Nerea
 */
public class Application extends javafx.application.Application {

    /**
     * Default constructor for the class Application.
     */
    public Application() {
    }

    /**
     * Start method for the Application.
     *
     * @param stage The stage for the window.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/SignInWindow.fxml"));
            Parent root = (Parent) loader.load();
            SignInWindowController controller = (SignInWindowController) loader.getController();
            Signable interfaz = ClientFactory.getImplementation();
            controller.setStage(stage);
            controller.initStage(root);
            controller.setImplementation(interfaz);
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Main application method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
