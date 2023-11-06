package view;

import exceptions.EmailExistsException;
import exceptions.ServerErrorException;
import exceptions.WrongCityFormatException;
import exceptions.WrongEmailFormatException;
import exceptions.WrongMobileFormatException;
import exceptions.WrongNameFormatException;
import exceptions.WrongPasswordFormatException;
import exceptions.WrongZipFormatException;
import factory.ClientFactory;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import message.ResponseRequest;
import models.Privilege;
import models.User;

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
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfConfirmPassword;
    @FXML
    private Label lblWrongName;
    @FXML
    private Label lblWrongEmail;
    @FXML
    private Label lblWrongPassword;
    @FXML
    private Label lblWrongMobile;
    @FXML
    private ToggleButton tgbEye;
    @FXML
    private Button btnCancel;
    @FXML
    private Label lblWrongEmailMax;
    @FXML
    private Label lblWrongMobileMax;
    @FXML
    private Label lblWrongPasswordMax;
    @FXML
    private Label lblWrongCityZip;

    private Image openEye;
    private Image closeEye;

    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("package view");
    protected final int MAX_LENGTH = 300;
    protected final int MAX_LENGTH_MOBILE = 9;

    /**
     * Initialises the controller class.
     *
     * @param root
     */
    public void initStage(Parent root) {
        LOGGER.info("Initialising Sign Up window.");
        //Creas la escena
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
        tfCity.setText("");
        pfConfirmPassword.setText("");
        pfPassword.setText("");
        tfEmail.setText("");
        tfFirstName.setText("");
        tfLastName.setText("");
        tfMobile.setText("");
        tfConfirmPassword.setText("");
        tfPassword.setText("");
        tfStreet.setText("");
        tfZip.setText("");
        //Ventana modal
        stage.initModality(Modality.APPLICATION_MODAL);

        openEye = new Image("resources/eyeB.png", 25, 26, false, true);
        closeEye = new Image("resources/closeEyeB.png", 25, 26, false, true);

        //El icono del ToggleButton será el del ojo abierto. 
        tgbEye.setGraphic(new ImageView(openEye));
        stage.getIcons().add(new Image("resources/blackStar.png"));

        //El botón signUp es el botón por defecto.
        btnSignUp.setDefaultButton(true);
        //El botón Cancel es el botón de escape.
        btnCancel.setCancelButton(true);

        //Asignar Actions y Listeners
        btnSignUp.setOnAction(this::handlerSignUpButton);
        tgbEye.setOnAction(this::handlertgbEye);
        stage.setOnCloseRequest(this::handleOnActionExit);
        btnCancel.setOnAction(this::handleOnActionExit);
        tfEmail.textProperty().addListener(this::textPropertyChange);
        tfFirstName.textProperty().addListener(this::textPropertyChange);
        tfLastName.textProperty().addListener(this::textPropertyChange);
        tfPassword.textProperty().addListener(this::textPropertyChange);
        tfConfirmPassword.textProperty().addListener(this::textPropertyChange);
        pfPassword.textProperty().addListener(this::textPropertyChange);
        pfConfirmPassword.textProperty().addListener(this::textPropertyChange);
        tfMobile.textProperty().addListener(this::textPropertyChange);

        //Se muestra la ventana con un show and wait.
        stage.showAndWait();
        LOGGER.info("Window opened.");
    }

    /**
     * The stage for the window.
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Observes the text change event of the text fields.
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void textPropertyChange(ObservableValue observable,
            String oldValue,
            String newValue) {
        //Validar que los campos full name, email, password, confirm password están informados
        if ((!tfFirstName.getText().trim().equals("") && !tfLastName.getText().trim().equals("") && !tfEmail.getText().trim().equals("") && !pfConfirmPassword.getText().trim().equals("")
                && !pfPassword.getText().trim().equals(""))) {
            btnSignUp.setDisable(false);
        } else {
            btnSignUp.setDisable(true);
        }

        //El texto del “pfPassword” se copiará en “tfPassword” y viceversa. Y el texto del “pfConfirmPassword” se copiará en el “tfConfirmPassword” y viceversa.
        if (pfPassword.isVisible() && !pfPassword.equals(tfPassword)) {
            tfPassword.setText(pfPassword.getText());
        } else if (tfPassword.isVisible()) {
            pfPassword.setText(tfPassword.getText());
        }

        if (pfConfirmPassword.isVisible() && !pfConfirmPassword.equals(tfConfirmPassword)) {
            tfConfirmPassword.setText(pfConfirmPassword.getText());
        } else if (tfCity.isVisible()) {
            pfConfirmPassword.setText(tfConfirmPassword.getText());
        }

        //Validar que tenga un máximo de 300 caracteres. Si el usuario excede este límite se le informará mediante un texto hasta que el límite de caracteres sea menor o igual al correspondiente.
        if (tfEmail.getText().trim().length() > MAX_LENGTH) {
            lblWrongEmailMax.setVisible(true);
        } else {
            lblWrongEmailMax.setVisible(false);
        }

        if (tfPassword.getText().trim().length() > MAX_LENGTH || tfConfirmPassword.getText().trim().length() > MAX_LENGTH || pfConfirmPassword.getText().trim().length() > MAX_LENGTH || pfPassword.getText().trim().length() > MAX_LENGTH) {
            lblWrongPasswordMax.setVisible(true);
        } else {
            lblWrongPasswordMax.setVisible(false);
        }

        //Validar que el campo teléfono (tfMobile) contenga un máximo de 9 caracteres. Si el usuario excede este límite se le informará mediante un texto hasta que el límite de caracteres sea menor o igual al correspondiente.
        if (tfMobile.getText().trim().length() > MAX_LENGTH_MOBILE) {
            lblWrongMobileMax.setVisible(true);
        } else {
            lblWrongMobileMax.setVisible(false);
        }
    }

    /**
     * Handles the toggle event of the Eye Toggle Button.
     *
     * @param event
     */
    @FXML
    private void handlertgbEye(ActionEvent event) {
        ImageView currentImage = (ImageView) tgbEye.getGraphic();
        //Si está pulsado, los PasswordField “pfPassword” y “pfConfirmPassword” se volverán invisibles y los TextField “tfPassword” 
        // y “tfConfirmPassword” se volverán visibles.  El icono del ToggleButton cambiará a un ojo tachado.

        if (currentImage.getImage() == openEye) {
            tgbEye.setGraphic(new ImageView(closeEye));
            pfConfirmPassword.setVisible(false);
            pfPassword.setVisible(false);
            tfPassword.setVisible(true);
            tfConfirmPassword.setVisible(true);
            //Si no está pulsado,  los PasswordField “pfPassword” y “pfConfirmPassword” se volverán visibles y los TextField “tfPassword” 
            //y “tfConfirmPassword” se volverán invisibles.  El icono del ToggleButton cambiará a un ojo abierto.

        } else {
            tgbEye.setGraphic(new ImageView(openEye));
            pfConfirmPassword.setVisible(true);
            pfPassword.setVisible(true);
            tfPassword.setVisible(false);
            tfConfirmPassword.setVisible(false);
        }
    }

    /**
     * Handles the event in the Sign Up button.
     *
     * @param event
     */
    @FXML
    private void handlerSignUpButton(ActionEvent event) {
        boolean flag = true ;
        User user = null;
        try {
            // Validar que el campo de nombre (tfFirstName) y el de apellido (tfLastName) no contengan valores numéricos, si no, lanzaremos la excepción “WrongNameFormatException”.
            if (!Pattern.matches("^[A-Za-zÁÉÍÓÚÑáéíóúñ\\s]+$", tfFirstName.getText()) || !Pattern.matches("^[A-Za-zÁÉÍÓÚÑáéíóúñ\\s]+$", tfLastName.getText())) {
                throw new WrongNameFormatException("The name or last name cannot contain numbers.");
            } else if (lblWrongName.isVisible()) {
                lblWrongName.setVisible(false);

            }
        } catch (WrongNameFormatException ex) {
            lblWrongName.setText(ex.getMessage());
            lblWrongName.setVisible(true);
            Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

            //Validar que el campo del email (tfEmail) cumpla con el formato correcto, si no, lanzaremos la excepción “WrongEmailFormatException”.
            if (!Pattern.matches("^[a-zA-Z0-9-._%+-]+@[a-zA-Z0-0.-]+.(com|org|cn|net|gov|eus|es|io)+$", tfEmail.getText())) {
                throw new WrongEmailFormatException("The email must have a valid format.");
            } else if (lblWrongEmail.isVisible()) {
                lblWrongEmail.setVisible(false);
            }
        } catch (WrongEmailFormatException ex) {
            lblWrongEmail.setText(ex.getMessage());
            lblWrongEmail.setVisible(true);
            Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            //Validar que ambos campos de las contraseñas (pfPassword y pfConfirmPassword) contengan la misma información y contengan mínimo 8 caracteres, de los cuales mínimo 1 mayúscula, 1 minuscula, y al menos 1 caracter especial, si no, lanzaremos la excepción “WrongPasswordFormatException”.
            if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-])[A-Za-z\\d@$!%*?&._-]{8,}$", pfConfirmPassword.getText()) || !Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-])[A-Za-z\\d@$!%*?&._-]{8,}$", pfPassword.getText())) {

                throw new WrongPasswordFormatException("Password doesn't match with required format");
            } else {
                lblWrongPassword.setVisible(false);
            }
        } catch (WrongPasswordFormatException ex) {
            lblWrongPassword.setText(ex.getMessage());
            lblWrongPassword.setVisible(true);
            Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (!pfConfirmPassword.getText().equals(pfPassword.getText())) {
                throw new WrongPasswordFormatException("he passwords don't match.");
            } else {
                if (lblWrongPassword.getText().isEmpty()) {
                    lblWrongPassword.setVisible(false);
                } else {
                    lblWrongPassword.setText(lblWrongPassword.getText() + ".");
                }
            }
        } catch (WrongPasswordFormatException ex) {
            if (lblWrongPassword.getText().equals("Password doesn't match with required format")) {
                lblWrongPassword.setText(lblWrongPassword.getText() + " and t" + ex.getMessage());
            } else {
                lblWrongPassword.setText("T" + ex.getMessage());
            }
            lblWrongPassword.setVisible(true);
            Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, lblWrongPassword.getText());
        }

        try {
            if (!tfCity.getText().isEmpty()) {
                if (!Pattern.matches("^[A-Za-zÁÉÍÓÚÑáéíóúñ\\s]+$", tfCity.getText())) {
                    flag = false;
                    throw new WrongCityFormatException("City cannot contain numbers");
                }
            } else if (lblWrongCityZip.isVisible()) {
                lblWrongCityZip.setVisible(false);
            }
        } catch (WrongCityFormatException ex) {
            lblWrongCityZip.setText(ex.getMessage());
            lblWrongCityZip.setVisible(true);
        }
        try {
            if (!tfZip.getText().isEmpty()) {
                if (!Pattern.matches("^\\d{5}$", tfZip.getText())) {
                    throw new WrongZipFormatException("ip must have 5 numeric numbers.");
                } else if (flag){
                    lblWrongCityZip.setVisible(false);
                    lblWrongCityZip.setText("");
               }
            } else {
                if (lblWrongCityZip.getText().isEmpty()) {
                    lblWrongCityZip.setVisible(false);
                } else {
                    lblWrongCityZip.setText(lblWrongCityZip.getText() + ".");
                }
            }
        } catch (WrongZipFormatException ex) {
            if (lblWrongCityZip.getText().equals("City cannot contain numbers")) {
                lblWrongCityZip.setText(lblWrongCityZip.getText() + " and z" + ex.getMessage());
            } else {
                lblWrongCityZip.setText("Z" + ex.getMessage());
            }
            lblWrongCityZip.setVisible(true);
            Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, lblWrongCityZip.getText());
        }

        try {
            //Validar que el campo del teléfono (tfMobile) empiece por 6 o 7, si no, lanzaremos la excepción “WrongMobileFormatException”.
            if (!tfMobile.getText().isEmpty()) {
                if (!Pattern.matches("^[67]\\d{8}", tfMobile.getText())) {
                    throw new WrongMobileFormatException("The phone number must start with 6 or 7 and can only contain 9 numbers.");
                }
            } else if (lblWrongMobile.isVisible()) {
                lblWrongMobile.setVisible(false);
            }
        } catch (WrongMobileFormatException ex) {
            lblWrongMobile.setText(ex.getMessage());
            lblWrongMobile.setVisible(true);
            Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Una vez que todas las validaciones están realizadas, carga los datos de los campos en un objeto User. 
        if (!lblWrongEmail.isVisible() && !lblWrongMobile.isVisible() && !lblWrongName.isVisible() && !lblWrongPassword.isVisible() && !lblWrongPasswordMax.isVisible()
                && !lblWrongEmailMax.isVisible() && !lblWrongCityZip.isVisible()) {
            user = new User();
            user.setName(tfFirstName.getText() + " " + tfLastName.getText());
            user.setEmail(tfEmail.getText());
            user.setMobile(tfMobile.getText());
            user.setPassword(pfPassword.getText());
            user.setCity(tfCity.getText());
            user.setPrivilege(Privilege.USER);
            user.setStreet(tfStreet.getText());
            user.setZip(tfZip.getText());

            try {

                User userResponse = ClientFactory.getImplementation().signUp(user);

                if (userResponse != null) {
                    new Alert(Alert.AlertType.CONFIRMATION, "You have successfully registered", ButtonType.OK).showAndWait();
                    stage.close();
                }

            } catch (ServerErrorException | EmailExistsException ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
                Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Handles the exit event.
     *
     * @param event
     */
    public void handleOnActionExit(Event event) {
        try {
            LOGGER.info("EXIT pressed.");
            //Pedir confirmación al usuario para salir:
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to exit?",
                    ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                //Si el usuario confirma, cerrar la ventana.
                stage.close();
                LOGGER.info("Window closed.");
            } else {
                //Si no confirma, mantenerse en la ventana.
                event.consume();
                LOGGER.info("Aborted.");
            }
        } catch (Exception e) {
            String errorMsg = "Error exiting application:" + e.getMessage();
            new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK).showAndWait();

        }

    }
}
