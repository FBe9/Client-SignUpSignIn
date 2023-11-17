package view;

import exceptions.*;
import factory.ClientFactory;
import interfaces.Signable;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.User;

/**
 * FXML Controller class. It controls what the signIn window components do. It
 * is the controller for the SignInWindow.fxml.
 *
 * @author Leire
 * @author Nerea
 */
public class SignInWindowController {

    /**
     * Default empty constructor for the class SignInWindowController.
     */
    public SignInWindowController() {
    }

    //TODO
    private Signable signable;

    //Text Fields
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfPassword;
    //Password Fields
    @FXML
    private PasswordField pfPassword;
    //Error Labels
    @FXML
    private Label lblError;
    @FXML
    private Label lblEmailError;
    //Buttons
    @FXML
    private Button btnAccept;
    @FXML
    private ToggleButton tgbEye;
    //Hyperlink
    @FXML
    private Hyperlink httpSignUp;
    //Pane
    @FXML
    private Pane signInPane;
    //Stage
    private Stage stage;
    //Logger
    private static final Logger LOGGER = Logger.getLogger("package view");
    //Images
    Image openEye = new Image("resources/openEye.png", 25, 26, false, true);
    Image closeEye = new Image("resources/closeEye.png", 25, 26, false, true);

    /**
     * Initialises the window before showing, and then shows the window.
     *
     * @param root a Parent object with the DOM.
     */
    public void initStage(Parent root) {
        try {
            LOGGER.info("Initialising SignIn window.");
            //Creas la escena
            Scene scene = new Scene(root);
            //Le estableces la escena al escenario
            stage.setScene(scene);

            //TODO
            signable = ClientFactory.getImplementation();

            //El nombre de la ventana es “Sign In”.
            stage.setTitle("SignIn");
            //Ventana no modal.
            //Añadir a la ventana un icono de una estrella.
            stage.getIcons().add(new Image("resources/blackStar.png"));
            //Ventana no redimensionable.
            stage.setResizable(false);
            //Se vacían los campos email y ambos password.
            tfEmail.setText("");
            pfPassword.setText("");
            tfPassword.setText("");
            //Se enfoca el campo Email.
            tfEmail.requestFocus();
            //Se deshabilita el botón Accept.
            btnAccept.setDisable(true);
            //El icono del ToggleButton será el del ojo abierto. 
            tgbEye.setGraphic(new ImageView(openEye));
            //El botón por defecto será el btnAccept.
            btnAccept.setDefaultButton(true);
            tfEmail.textProperty().addListener(this::textChanged);
            pfPassword.textProperty().addListener(this::textChanged);
            tfPassword.textProperty().addListener(this::textChanged);
            //El texto del “pfPassword” se copiara en “tfPassword” y viceversa.
            //tfPassword.textProperty().bindBidirectional(pfPassword.textProperty());
            stage.setOnCloseRequest(this::handleOnActionExit);
            btnAccept.setOnAction(this::handelAcceptButtonAction);
            tgbEye.setOnAction(this::handelEyeToggleButtonAction);
            httpSignUp.setOnAction(this::handelSignUpHyperlink);
            //Mostrar la ventana. 
            stage.show();
            LOGGER.info("Showing the SignIn window.");
        } catch (Exception e) {
            String errorMsg = "Error opening window:\n" + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMsg);
        }

    }

    /**
     * Text changed event handler. Validates that all the fields aren't empty
     * and that Email and Passwords don't surpass 300 characters. The Accept
     * button is disabled when one of all the fields are empty.
     *
     * @param observable The value being observed.
     * @param oldValue The old value of the observable.
     * @param newValue The new value of the observable.
     */
    public void textChanged(ObservableValue observable, String oldValue, String newValue) {
        //El label se ubicará debajo del campo Password y mostrara el mensaje de color rojo.
        lblError.setText("");
        lblEmailError.setText("");
        //Se comprobara cual de los campos passwords es visible y se copiara el texto del visible en el invisible en caso de que no tengan el mismo texto.
        if (pfPassword.isVisible() && !(pfPassword.getText().equals(tfPassword.getText()))) {
            tfPassword.setText(pfPassword.getText());
        } else if (tfPassword.isVisible() && !(tfPassword.getText().equals(pfPassword.getText()))) {
            pfPassword.setText(tfPassword.getText());
        }
        //Validar que el campo Email y los campos Password están informados. 
        if (tfEmail.getText().trim().isEmpty()
                || pfPassword.getText().trim().isEmpty() || tfPassword.getText().trim().isEmpty()) {
            //En el caso de que estén informados, habilitar el botón Accept.
            btnAccept.setDisable(true);
        } else {
            //En el caso de que no lo estén deshabilitar el botón Accept.
            btnAccept.setDisable(false);
        }
        /**
         * El límite de caracteres de los campos será de 300 caracteres. Si el
         * usuario excede este límite se le informará mediante una label hasta
         * que el límite de caracteres sea menor o igual al correspondiente.
         */
        if (tfEmail.getText().trim().length() > 300) {
            lblEmailError.setText("The maximum lenght for the Email is 300 characters,\n please change it.");
        }
        if (tfPassword.getText().trim().length() > 300 || pfPassword.getText().trim().length() > 300) {
            lblError.setText("The maximum lenght for the Password is 300\ncharacters, please change it.");
        }
    }

    /**
     * It handles the events in the toggle button tgbEye.
     *
     * @param event the event that happens with the toggle button.
     */
    @FXML
    public void handelEyeToggleButtonAction(ActionEvent event) {
        LOGGER.info("Handeling the eye toggle button.");
        //Comprobar el estado del botón:
        if (tgbEye.isSelected()) {
            LOGGER.info("Eye pressed.");
            /**
             * Si está pulsado, el PasswordField “pfPassword” se volverá
             * invisible y el TextField “tfPassword” se volverá visible. El
             * icono del ToggleButton cambiará a un ojo tachado.
             */
            pfPassword.setVisible(false);
            tfPassword.setVisible(true);
            tgbEye.setGraphic(new ImageView(closeEye));

        } else {
            LOGGER.info("Eye normal.");
            /**
             * Si no está pulsado, el PasswordField “pfPassword” se volverá
             * visible y el TextField “tfPassword” se volverá invisible. El
             * icono del ToggleButton cambiará a un ojo abierto.
             */
            pfPassword.setVisible(true);
            tfPassword.setVisible(false);
            tgbEye.setGraphic(new ImageView(openEye));
        }
    }

    /**
     * It handles what happens when the button btnAccept is pressed.
     *
     * @param event the event that happens with the button
     */
    @FXML
    public void handelAcceptButtonAction(ActionEvent event) {
        try {
            LOGGER.info("Handeling the accept button.");
            /**
             * Validar que el email introducido tiene el formato válido propio
             * de un email, en caso contrario, informar al usuario con el
             * mensaje de la excepción “WrongEmailFormatException”.
             */
            String patternEmail = "^[a-zA-Z0-9-._%+-]+@[a-zA-Z0-0.-]+.(com|org|cn|net|gov|eus|es|io)+$";
            if (!Pattern.matches(patternEmail, tfEmail.getText()) || tfEmail.getText().contains(" ")) {
                throw new WrongEmailFormatException("Email format is not acceptable");
            }
            /**
             * Se usará la factoría “ClientFactory” para obtener una
             * implementación de la interfaz “Signable”, y se llamará al método
             * “SignIn” pasándole un nuevo objeto “User” que contenga los
             * valores email y password.
             */
            User user = new User(tfEmail.getText(), pfPassword.getText());
            User serverUser = signable.signIn(user);

            /**
             * Si el metodo signIn no produce excepciones, se cerrará la ventana
             * y después se abrirá la ventana Logged pasándole los datos del
             * objeto User devuelto.
             */
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/LoggedWindow.fxml"));
            Parent root = (Parent) loader.load();
            LoggedWindowController controller = (LoggedWindowController) loader.getController();
            controller.setStage(stage);
            controller.initStage(root, serverUser);

        } catch (IOException ex) {
            Logger.getLogger(SignInWindowController.class.getName()).log(Level.SEVERE, null, ex);

        } catch (ServerErrorException se) {
            /**
             * Si se produce un error en el servidor, se abrirá una ventana
             * emergente mostrando el mensaje de la excepción “ServerException”.
             */
            new Alert(Alert.AlertType.ERROR, se.getMessage(), ButtonType.OK).showAndWait();
            Logger.getLogger(SignInWindowController.class.getName()).log(Level.SEVERE, null, se);
            /**
             * Si el email o la contraseña no coinciden con un usuario
             * registrado, se mostrará un texto en rojo debajo del campo Email
             * con el mensaje de la excepción “LoginCredentialException”.
             */
        } catch (LoginCredentialException l) {
            lblError.setText(l.getMessage());
            Logger.getLogger(SignInWindowController.class.getName()).log(Level.SEVERE, null, l);
        } catch (WrongEmailFormatException e) {
            lblEmailError.setText(e.getMessage());
            Logger.getLogger(SignInWindowController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * It handles what happens when the hyperlink httpSignUp is pressed.
     *
     * @param event The event for the hyperlink.
     */
    @FXML
    public void handelSignUpHyperlink(ActionEvent event) {
        try {
            //Se abrirá la ventana Sign Up de manera modal.
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/SignUpWindow.fxml"));
            Parent root = (Parent) loader.load();
            SignUpWindowController controller = (SignUpWindowController) loader.getController();
            //Ventana modal.
            Stage modalStage = new Stage();
            controller.setStage(modalStage);
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(SignInWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Exit button event handler.
     *
     * @param event An ActionEvent object.
     */
    public void handleOnActionExit(Event event) {
        try {
            //Ask user for confirmation on exit
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to exit the application?",
                    ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            //If OK to exit
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Platform.exit();
            } else {
                event.consume();
            }
        } catch (Exception e) {
            String errorMsg = "Error exiting application:" + e.getMessage();
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    errorMsg,
                    ButtonType.OK);
            alert.showAndWait();
            LOGGER.log(Level.SEVERE, errorMsg);
        }
    }

    /**
     * Sets the stage for the window.
     *
     * @param stage The stage information for the window.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
