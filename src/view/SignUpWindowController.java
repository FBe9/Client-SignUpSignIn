package view;

import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Irati
 * @author Olivia
 */
public class SignUpWindowController {

    @FXML
    private ImageView imgStar;
    @FXML
    private ImageView imgBackground;
    @FXML
    private Label lblFistName;
    @FXML
    private TextField tfFirstName;
    @FXML
    private Label lblLastName;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private PasswordField pfConfirmPassword;
    @FXML
    private TextField tfStreet;
    @FXML
    private TextField tfCity;
    @FXML
    private TextField tfZip;
    @FXML
    private TextField tfMobile;
    @FXML
    private Button btnSignUp;

    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("package view");

    /**
     * Initializes the controller class.
     * @param root
     */
    public void initStage(Parent root) {
        LOGGER.info("Initializing the sign Up Window");
        //Creates the scene
        Scene scene = new Scene(root);
        //Le estableces la escena al escenario
        stage.setScene(scene);
        //El nombre de la ventana es “Sign Up”.
         stage.setTitle("SignUp");
        //Ventana no redimensionable.
        stage.setResizable(false);
        //El foco se centra en el campo First name (tfFirstName).
        tfFirstName.isFocused();
        //Deshabilita el botón sign up (btnSignUp).
        btnSignUp.setDisable(true);
        //Vaciar el contenido de todos los campos.
        //Se muestra la ventana con un show and wait.
        stage.showAndWait();
        stage.getIcons().add(new Image("../resources/blackStar.png"));
        
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    
}
