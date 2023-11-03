package view;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.User;

/**
 * Controller UI class for Logged view. It contains event handlers and
 * initialization code for the view defined in LoggedWindow.fxml file.
 *
 * @author Leire
 * @author Nerea
 */
public class LoggedWindowController {

    /**
     * Package logger.
     */
    private static final Logger LOGGER = Logger.getLogger("logged view");
    private Stage stage;

    /**
     * Label for the welcome messages.
     */
    @FXML
    private Label lblHellou;
    /**
     * A button to log out.
     */
    @FXML
    private Button btnLogOut;
    /**
     * A button to exit.
     */
    @FXML
    private Button btnExit;

    /*
    - El nombre de la ventana es “Logged".
    - La ventana recibe un objeto Usuario y muestra el valor del parámetro 
      FirstName con un mensaje de bienvenida en una Label.
    - Ventana no redimensionable.
    - Mostrar ventana.
     */
    /**
     * Method for initializing Logged Stage.
     *
     * @param root The Parent object representing root node of view graph.
     * @param user It is the user who is logged in.
     */
    public void initStage(Parent root, User user) {
        try {
            LOGGER.info("Initializing the logged window with the client: "+user.getName());

            //Set the user that log in to the messages.
            lblHellou.setText("Hello " + user.getName() + " to our Application.");

            Scene scene = new Scene(root);

            //Add scene.
            stage.setScene(scene);

            //The window title will be ”Logged”.
            stage.setTitle("Logged");

            //Add a star icon.
            stage.getIcons().add(new Image("resources/blackStar.png"));

            //Not a resizable window.
            stage.setResizable(false);

            //Set event handlers.
            stage.setOnCloseRequest(this::handelExitButtonAction);
            this.btnExit.setOnAction(this::handelExitButtonAction);
            this.btnLogOut.setOnAction(this::handelLogOutButtonAction);

            //Show window.
            stage.show();
        } catch (Exception e) {
            String errorMsg = "Error opening window:\n" + e.getMessage();
            this.showErrorAlert(errorMsg);
            LOGGER.log(Level.SEVERE, errorMsg);
        }
    }

    /*
    - Se cierra ventana con el método close() y se inicia la ventana no modal 
      Login.
    - Pedir confirmación al usuario para salir:
        · Si el usuario confirma, cerrar sesión.
        · Si no confirma, mantenerse en la ventana.
     */
    /**
     * This method is from the "Log Out" button. It asks you for confirmation if
     * you want to log out. If the answer is yes, the window closes and the
     * "Sign in" window opens. If the answer is no, the alert confirmation is
     * closed.
     *
     * @param event The Action event object
     */
    @FXML
    private void handelLogOutButtonAction(ActionEvent event) {
        try {
            //Ask user for confirmation on exit
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure, that you want to log out?");
            Optional<ButtonType> action = alert.showAndWait();

            //If OK to exit 
            if (action.isPresent() && action.get() == ButtonType.OK) {
                stage.close();

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/SignInWindow.fxml"));
                    Parent root = (Parent) loader.load();
                    SignInWindowController controller = (SignInWindowController) loader.getController();
                    controller.setStage(stage);

                    controller.initStage(root);
                } catch (IOException ex) {
                    Logger.getLogger(LoggedWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                event.consume();
            }
        } catch (Exception e) {
            String errorMsg = "Error exiting application:" + e.getMessage();
            this.showErrorAlert(errorMsg);
            LOGGER.log(Level.SEVERE, errorMsg);
        }
    }

    /*
    - Se cierra la ventana con el método close().
    - Pedir confirmación al usuario para salir:
            · Si el usuario confirma, salir de la aplicación.
            · Si no confirma, mantenerse en la ventana.
     */
    /**
     * This method is from the "Exit" and the "X" button. It asks you for
     * confirmation if you want to close the application. If the answer is yes,
     * the window closes. If the answer is no, the alert confirmation is closed.
     *
     * @param event The Action event object
     */
    @FXML
    private void handelExitButtonAction(Event event) {
        try {
            //Ask user for confirmation on exit
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure that you want to close the application?");
            Optional<ButtonType> action = alert.showAndWait();

            //If OK to exit
            if (action.isPresent() && action.get() == ButtonType.OK) {
                stage.close();
            } else {
                event.consume();
            }
        } catch (Exception e) {
            String errorMsg = "Error exiting application:" + e.getMessage();
            this.showErrorAlert(errorMsg);
            LOGGER.log(Level.SEVERE, errorMsg);
        }
    }

    /*
    - Se cierra la ventana con el método close().
    - Pedir confirmación al usuario para salir:
            · Si el usuario confirma, salir de la aplicación.
            · Si no confirma, mantenerse en la ventana.
     */
    /**
     * Stage setter
     *
     * @param stage The stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Utility method for showing messages.
     *
     * @param errorMsg The message to be shown.
     */
    protected void showErrorAlert(String errorMsg) {
        //Shows error dialog.
        Alert alert = new Alert(Alert.AlertType.ERROR,
                errorMsg,
                ButtonType.OK);
        alert.showAndWait();

    }
}
