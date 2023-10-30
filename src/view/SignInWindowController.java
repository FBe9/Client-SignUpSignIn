package view;

import exceptions.*;
import factory.ClientFactory;
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
import javafx.stage.Stage;
import models.User;

/**
 * FXML Controller class
 *
 * @author Leire
 * @author Nerea
 */
public class SignInWindowController {

    @FXML
    private TextField tfEmail;
    @FXML
    private Hyperlink httpSignUp;
    @FXML
    private Button btnAccept;
    @FXML
    private ToggleButton tgbEye;
    @FXML
    private Label lblError;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private TextField tfPassword;

    // private static String visible;
    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("package view");
    Image openEye = new Image("resources/openEye.png", 25, 26, false, true);
    Image closeEye = new Image("resources/closeEye.png", 25, 26, false, true);

    /**
     * Initializes the controller class.
     *
     * @param root
     */
    public void initStage(Parent root) {
        LOGGER.info("Inicializando la ventana de SignIn");
        //Creas la escena
        Scene scene = new Scene(root);
        //Le estableces la escena al escenario
        stage.setScene(scene);
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
        //Todos los elementos están vacíos al inicializar la ventana.
        lblError.setText("");
        //El icono del ToggleButton será el del ojo abierto. 
        tgbEye.setGraphic(new ImageView(openEye));
        //El botón por defecto será el btnAccept.
        btnAccept.setDefaultButton(true);
        //El botón de cancelar será la “x” que cierra la ventana.

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
    }

    /**
     * Text changed event handler. Validate that all the fields are not empty
     * and that Email not surpass 40 characters and Passwords 300 characters.
     * The Accept button is disabled when one of all fields are empty.
     *
     * @param observable The value being observed.
     * @param oldValue The old value of the observable.
     * @param newValue The new value of the observable.
     */
    private void textChanged(ObservableValue observable, String oldValue, String newValue) {
        //El label se ubicará debajo del campo Password y mostrara el mensaje de color rojo.
        lblError.setText("");
        //El texto del “pfPassword” se copiara en “tfPassword” y viceversa.
        if (pfPassword.isVisible()) {
            tfPassword.setText(pfPassword.getText());
        } else if (tfPassword.isVisible()) {
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
            lblError.setText("The maximum lenght for the Email is 300 characters,\n please change it.");
        }
        if (tfPassword.getText().trim().length() > 300 || pfPassword.getText().trim().length() > 300) {
            lblError.setText("The maximum lenght for the Password is 300\ncharacters, please change it.");
        }
    }

    @FXML
    private void handelEyeToggleButtonAction(ActionEvent event) {
        LOGGER.info("Handeling the eye toggle button");
        //Comprobar el estado del botón:
        if (tgbEye.isSelected()) {
            LOGGER.info("eye pressed");
            /**
             * Si está pulsado, el PasswordField “pfPassword” se volverá
             * invisible y el TextField “tfPassword” se volverá visible. El
             * icono del ToggleButton cambiará a un ojo tachado.
             */
            pfPassword.setVisible(false);
            tfPassword.setVisible(true);
            tgbEye.setGraphic(new ImageView(closeEye));

        } else {
            LOGGER.info("eye normal");
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

    @FXML
    private void handelAcceptButtonAction(ActionEvent event) {
        try {
            LOGGER.info("Handeling the accept button");
            /**
             * Validar que el email introducido tiene el formato válido propio
             * de un email, en caso contrario, informar al usuario con el
             * mensaje de la excepción “WrongEmailFormatException”.
             */
            String patternEmail = "([a-z0-9]*)@([a-z]*).(com|org|cn|net|gov|eus|es|io)";
            if (!Pattern.matches(patternEmail, tfEmail.getText()) || tfEmail.getText().contains(" ")) {
                throw new WrongEmailFormatException("Email format is not acceptable");
            }
            /**
             * Se usará la factoría “ClientFactory” para obtener una
             * implementación de la interfaz “Signable”, y se llamará al método
             * “SignIn” pasándole un nuevo objeto “User” que contenga los
             * valores email y password.
             * 
             * Validar que existe un usuario con el mismo email y contraseña
             * introducida en el servidor. Este devolverá un user en base a la
             * respuesta.
             */
            User user = new User(tfEmail.getText(), pfPassword.getText());
            User serverUser = ClientFactory.getImplementation().signIn(user);
            
            //El ResponseRequest devolverá lo que haya ocurrido en el servidor con esa acción:
            /**
             * Si la respuesta del mensaje es OK, significará que coinciden y se
             * cerrará la ventana. Después se abrirá la ventana Logged pasándole
             * los datos del objeto User devuelto.
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
        } catch (LoginCredentialException | WrongEmailFormatException e) {
            /**
             * Si el email o la contraseña no coinciden con un usuario
             * registrado, se mostrará un texto en rojo debajo del campo Email
             * con el mensaje de la excepción “LoginCredentialException”.
             */
            lblError.setText(e.getMessage());
            Logger.getLogger(SignInWindowController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void handelSignUpHyperlink(ActionEvent event) {
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

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
