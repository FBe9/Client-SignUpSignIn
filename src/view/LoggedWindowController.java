package view;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.User;

/**
 * Controller UI class for Logged view. It contains event handlers and
 * initialization code for the view defined in LoggedWindow.fxml file.
 *
 * @author Leire
 * @author Nerea
 */
public class LoggedWindowController {

    private static final Logger LOGGER = Logger.getLogger("view");
    private Stage stage;

    @FXML
    private Label lblHellou;
    @FXML
    private Button btnLogOut;
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
        LOGGER.info("Initializing the logged window");

        //Tooltips
        this.lblHellou.setTooltip(new Tooltip("Message"));
        this.btnLogOut.setTooltip(new Tooltip("Log Out"));
        this.btnExit.setTooltip(new Tooltip("Exit"));

        lblHellou.setText("Hello " + user.getName() + " to our Application");
        Scene scene = new Scene(root);

        //Add scene.
        stage.setScene(scene);

        //The window title will be ”Logged”.
        stage.setTitle("Logged");

        //Add a star icon.
        stage.getIcons().add(new Image("resources/blackStar.png"));

        //Not a resizable window.
        stage.setResizable(false);

        stage.setOnCloseRequest(this::handleOnActionExit);

        //Show window.
        stage.show();
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure that you want log out?");
        Optional<ButtonType> action = alert.showAndWait();

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
    }

    /*
    - Se cierra la ventana con el método close().
    - Pedir confirmación al usuario para salir:
            · Si el usuario confirma, salir de la aplicación.
            · Si no confirma, mantenerse en la ventana.
     */
    /**
     * This method is from the "Exit" button. It asks you for confirmation if
     * you want to close the application. If the answer is yes, the window
     * closes. If the answer is no, the alert confirmation is closed.
     *
     * @param event The Action event object
     */
    @FXML
    private void handelExitButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure that you want to close the application?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent() && action.get() == ButtonType.OK) {
            stage.close();
        } else {
            event.consume();
        }
    }

    /*
    - Se cierra la ventana con el método close().
    - Pedir confirmación al usuario para salir:
            · Si el usuario confirma, salir de la aplicación.
            · Si no confirma, mantenerse en la ventana.
     */
    /**
     * This method is from the "X" button in the window. It asks you for
     * confirmation if you want to close the application. If the answer is yes,
     * the window closes. If the answer is no, the alert confirmation is closed.
     *
     * @param event The Action event object
     */
    public void handleOnActionExit(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure that you want to close the application?",
                ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        } else {
            event.consume();
        }

    }

    /**
     * Return the stage
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
