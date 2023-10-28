package application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import view.*;

/**
 *
 * @author Nerea
 */
public class Application extends javafx.application.Application {
    
    @Override
    public void start(Stage stage) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/SignInWindow.fxml"));
            Parent root = (Parent) loader.load();
            SignInWindowController controller = (SignInWindowController) loader.getController();

            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
