package view;

import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.User;

/**
 * FXML Controller class
 *
 * @author Leire
 * @author Nerea
 */
public class LoggedWindowController {

    @FXML
    private Button btnLogOut;
    @FXML
    private Button btnExit;
    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("package view");
    @FXML
    private Label lblHellou;

    /**
     * Initializes the controller class.
     *
     * @param root
     * @param user
     */
    public void initStage(Parent root, User user) {
        LOGGER.info("Initcializando la ventana de Logged");
        //Creas la escena
        Scene scene = new Scene(root);
        //Le estableces la escena al escenario
        stage.setScene(scene);
        //El nombre de la ventana es “Sign In”.
        stage.setTitle("Logged");
        //Ventana no redimensionable.
        stage.setResizable(false);
        //
        stage.getIcons().add(new Image("resources/blackStar.png"));
        /**
         * La ventana recibe un objeto Usuario y muestra el valor del parámetro
         * FirstName con un mensaje de bienvenida en una label.
         */
        lblHellou.setText("Hello "+user.getName()+" to our Application");
        //Mostrar la ventana. 
        stage.show();
    }

    @FXML
    private void handelLogOutButtonAction(ActionEvent event) {
    }

    @FXML
    private void handelExitButtonAction(ActionEvent event) {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
