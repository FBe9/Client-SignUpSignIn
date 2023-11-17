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

    /**
     * Default empty constructor for the class SignUpWIndowController.
     */
    public SignUpWindowController() {
    }

    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfEmailSignUp;
    @FXML
    private PasswordField pfPasswordSignUp;
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
    private TextField tfPasswordSignUp;
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
    private ToggleButton tgbEyeSignUp;
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
    /**
     * The default maximum length permitted for the fields.
     */
    protected final int MAX_LENGTH = 300;
    /**
     * The maximum length permitted for the mobile phone field.
     */
    protected final int MAX_LENGTH_MOBILE = 9;

    /**
     * Initialises the controller class.
     *
     * @param root The parent window.
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
        pfPasswordSignUp.setText("");
        tfEmailSignUp.setText("");
        tfFirstName.setText("");
        tfLastName.setText("");
        tfMobile.setText("");
        tfConfirmPassword.setText("");
        tfPasswordSignUp.setText("");
        tfStreet.setText("");
        tfZip.setText("");
        //Ventana modal
        stage.initModality(Modality.APPLICATION_MODAL);
        //Establecer las imágenes para openEye y closeEye
        openEye = new Image("resources/eyeB.png", 25, 26, false, true);
        closeEye = new Image("resources/closeEyeB.png", 25, 26, false, true);

        //El icono del ToggleButton será el del ojo abierto. 
        tgbEyeSignUp.setGraphic(new ImageView(openEye));
        stage.getIcons().add(new Image("resources/blackStar.png"));

        //El botón signUp es el botón por defecto.
        btnSignUp.setDefaultButton(true);
        //El botón Cancel es el botón de escape.
        btnCancel.setCancelButton(true);

        //Asignar Actions y Listeners
        btnSignUp.setOnAction(this::handlerSignUpButton);
        tgbEyeSignUp.setOnAction(this::handlertgbEye);
        stage.setOnCloseRequest(this::handleOnActionExit);
        btnCancel.setOnAction(this::handleOnActionExit);
        tfEmailSignUp.textProperty().addListener(this::textPropertyChange);
        tfFirstName.textProperty().addListener(this::textPropertyChange);
        tfLastName.textProperty().addListener(this::textPropertyChange);
        tfPasswordSignUp.textProperty().addListener(this::textPropertyChange);
        tfConfirmPassword.textProperty().addListener(this::textPropertyChange);
        pfPasswordSignUp.textProperty().addListener(this::textPropertyChange);
        pfConfirmPassword.textProperty().addListener(this::textPropertyChange);
        tfMobile.textProperty().addListener(this::textPropertyChange);

        //Se muestra la ventana con un show and wait.
        LOGGER.info("Window opened.");
        stage.showAndWait();
        
    }

    /**
     * Sets the stage for the Sign Up window.
     *
     * @param stage The stage for the window.
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
        //Validar que los campos full name, email, password, confirm password están informados.
        if ((!tfFirstName.getText().trim().equals("") && !tfLastName.getText().trim().equals("") && !tfEmailSignUp.getText().trim().equals("") && !pfConfirmPassword.getText().trim().equals("")
                && !pfPasswordSignUp.getText().trim().equals(""))) {
            btnSignUp.setDisable(false);
        } else {
            btnSignUp.setDisable(true);
        }

        //Se comprobara cual de los campos passwords es visible y se copiara el texto del visible en el invisible en caso de que no tengan el mismo texto.
        if (pfPasswordSignUp.isVisible() && !pfPasswordSignUp.equals(tfPasswordSignUp)) {
            tfPasswordSignUp.setText(pfPasswordSignUp.getText());
        } else if (tfPasswordSignUp.isVisible()) {
            pfPasswordSignUp.setText(tfPasswordSignUp.getText());
        }
        //Se comprobara cual de los campos passwords es visible y se copiara el texto del visible en el invisible en caso de que no tengan el mismo texto.
        if (pfConfirmPassword.isVisible() && !pfConfirmPassword.equals(tfConfirmPassword)) {
            tfConfirmPassword.setText(pfConfirmPassword.getText());
        } else if (tfCity.isVisible()) {
            pfConfirmPassword.setText(tfConfirmPassword.getText());
        }

        //Validar que el campo email tenga un máximo de 300 caracteres. Si el usuario excede este límite se le informará mediante un texto hasta que el límite de caracteres sea menor o igual al correspondiente.
        if (tfEmailSignUp.getText().trim().length() > MAX_LENGTH) {
            lblWrongEmailMax.setVisible(true);
        } else {
            lblWrongEmailMax.setVisible(false);
        }
        //Validar que el campo contraseña tenga un máximo de 300 caracteres. Si el usuario excede este límite se le informará mediante un texto hasta que el límite de caracteres sea menor o igual al correspondiente.
        if (tfPasswordSignUp.getText().trim().length() > MAX_LENGTH || tfConfirmPassword.getText().trim().length() > MAX_LENGTH || pfConfirmPassword.getText().trim().length() > MAX_LENGTH || pfPasswordSignUp.getText().trim().length() > MAX_LENGTH) {
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
        
        if(!tfMobile.getText().equals(oldValue) && tfMobile.getText().equals(newValue)){
            lblWrongMobile.setVisible(false);
        }
    }

    /**
     * Handles the toggle event of the Eye Toggle Button.
     *
     * @param event The event for the eye toggle button handler.
     */
    @FXML
    private void handlertgbEye(ActionEvent event) {
        ImageView currentImage = (ImageView) tgbEyeSignUp.getGraphic();
        //Si está pulsado, los PasswordField “pfPasswordSignUp” y “pfConfirmPassword” se volverán invisibles y los TextField “tfPasswordSignUp” 
        // y “tfConfirmPassword” se volverán visibles.  El icono del ToggleButton cambiará a un ojo tachado.

        if (currentImage.getImage() == openEye) {
            tgbEyeSignUp.setGraphic(new ImageView(closeEye));
            pfConfirmPassword.setVisible(false);
            pfPasswordSignUp.setVisible(false);
            tfPasswordSignUp.setVisible(true);
            tfConfirmPassword.setVisible(true);

            //Si no está pulsado,  los PasswordField “pfPasswordSignUp” y “pfConfirmPassword” se volverán visibles y los TextField “tfPasswordSignUp” 
            //y “tfConfirmPassword” se volverán invisibles.  El icono del ToggleButton cambiará a un ojo abierto.
        } else {
            tgbEyeSignUp.setGraphic(new ImageView(openEye));
            pfConfirmPassword.setVisible(true);
            pfPasswordSignUp.setVisible(true);
            tfPasswordSignUp.setVisible(false);
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
        boolean flag = true;
        User user = null;
        try {
            LOGGER.info("Accept. Starting user info validation.");
            // Validar que el campo de nombre (tfFirstName) y el de apellido (tfLastName) no contengan valores numéricos, si no, lanzaremos la excepción “WrongNameFormatException”.
            if (!Pattern.matches("^[A-Za-zÁÉÍÓÚÑáéíóúñ\\s]+$", tfFirstName.getText()) || !Pattern.matches("^[A-Za-zÁÉÍÓÚÑáéíóúñ\\s]+$", tfLastName.getText())) {
                throw new WrongNameFormatException("The name or last name cannot contain numbers.");
                //Si cumple con el patrón correcto, verificamos si la etiqueta de error está visible.
            } else if (lblWrongName.isVisible()) {
                //Si la etiqueta se había mostrado pero el error ya no existe, la ocultamos.
                lblWrongName.setVisible(false);

            }
        } catch (WrongNameFormatException ex) {
            //Establecemos el mensaje de error en la etiqueta.
            lblWrongName.setText(ex.getMessage());
            //Mostramos la etiqueta de error.
            lblWrongName.setVisible(true);
            Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

            //Validar que el campo del email (tfEmailSignUp) cumpla con el formato correcto, si no, lanzaremos la excepción “WrongEmailFormatException”.
            if (!Pattern.matches("^[a-zA-Z0-9-._%+-]+@[a-zA-Z0-0.-]+.(com|org|cn|net|gov|eus|es|io)+$", tfEmailSignUp.getText())) {
                throw new WrongEmailFormatException("The email must have a valid format.");
                //Si cumple con el patrón correcto, verificamos si la etiqueta de error está visible.
            } else if (lblWrongEmail.isVisible()) {
                //Si la etiqueta se había mostrado pero el error ya no existe, la ocultamos.
                lblWrongEmail.setVisible(false);
            }
        } catch (WrongEmailFormatException ex) {
            //Establecemos el mensaje de error en la etiqueta.
            lblWrongEmail.setText(ex.getMessage());
            //Mostramos la etiqueta de error.
            lblWrongEmail.setVisible(true);
            Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            //Validar que ambos campos de las contraseñas (pfPasswordSignUp y pfConfirmPassword) contengan la misma información y contengan mínimo 8 caracteres, de los cuales mínimo 1 mayúscula, 1 minuscula, y al menos 1 caracter especial, si no, lanzaremos la excepción “WrongPasswordFormatException”.
            if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-])[A-Za-z\\d@$!%*?&._-]{8,}$", pfConfirmPassword.getText()) || !Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-])[A-Za-z\\d@$!%*?&._-]{8,}$", pfPasswordSignUp.getText())) {
                throw new WrongPasswordFormatException("Password doesn't match with required format.");
            } else {
                //Si la etiqueta se había mostrado pero el error ya no existe, la ocultamos.
                lblWrongPassword.setVisible(false);
            }
        } catch (WrongPasswordFormatException ex) {
            //Establecemos el mensaje de error en la etiqueta.
            lblWrongPassword.setText(ex.getMessage());
            //Mostramos la etiqueta de error.
            lblWrongPassword.setVisible(true);
            Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (!pfConfirmPassword.getText().equals(pfPasswordSignUp.getText())) {
                throw new WrongPasswordFormatException("The passwords don't match.");
            }
        } catch (WrongPasswordFormatException ex) {
            //Comprobamos si la label de error contiene ya el error anterior
            if (lblWrongPassword.getText().equals("Password doesn't match with required format.")) {
                //Establecemos un nuevo mensaje de error uniendo los dos errores
                lblWrongPassword.setText(lblWrongPassword.getText().substring(0, lblWrongPassword.getText().length() - 1) + " and t" + ex.getMessage().substring(1, ex.getMessage().length()));
            } else {
                //Si la label de error esta vacia cogemos el error de la excepción
                lblWrongPassword.setText(ex.getMessage());
            }
            //Mostramos la label de error
            lblWrongPassword.setVisible(true);
            Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, lblWrongPassword.getText());
        }

        try {
            if (!tfCity.getText().isEmpty()) {
                if (!Pattern.matches("^[A-Za-zÁÉÍÓÚÑáéíóúñ\\s]+$", tfCity.getText())) {
                    //Si el pattern no se cumple pondremos la flag a false. Validación que nos sirve para reutilizar la label de error.
                    flag = false;
                    throw new WrongCityFormatException("City cannot contain numbers.");
                }
                //Si cumple con el patrón correcto, verificamos si la etiqueta de error está visible.
            } else if (lblWrongCityZip.isVisible()) {
                //Si la etiqueta se había mostrado pero el error ya no existe, la ocultamos.
                lblWrongCityZip.setVisible(false);
            }
        } catch (WrongCityFormatException ex) {
            //Establecemos el mensaje de error en la etiqueta.
            lblWrongCityZip.setText(ex.getMessage());
            //Mostramos la etiqueta de error.
            lblWrongCityZip.setVisible(true);
            Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (!tfZip.getText().isEmpty()) {
                if (!Pattern.matches("^\\d{5}$", tfZip.getText())) {
                    throw new WrongZipFormatException("Zip must have 5 numeric numbers.");
                    //Validamos que no haya error en el campo tfCity para ocultar la label.
                } else if (flag) {
                    lblWrongCityZip.setVisible(false);
                }
            }
        } catch (WrongZipFormatException ex) {
            //Comprobamos si la label de error contiene ya el error anterior
            if (lblWrongCityZip.getText().equals("City cannot contain numbers.")) {
                //Establecemos un nuevo mensaje de error uniendo los dos errores
                lblWrongCityZip.setText(lblWrongCityZip.getText().substring(0, lblWrongCityZip.getText().length() - 1) + " and z" + ex.getMessage().substring(1, ex.getMessage().length()));
            } else {
                //Si la label de error esta vacia cogemos el error de la excepción
                lblWrongCityZip.setText(ex.getMessage());
            }
            //Mostramos la etiqueta de error.
            lblWrongCityZip.setVisible(true);
            Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, lblWrongCityZip.getText());
        }

        try {
            //Validar que el campo del teléfono (tfMobile) empiece por 6 o 7, si no, lanzaremos la excepción “WrongMobileFormatException”.
            if (!tfMobile.getText().isEmpty()) {
                if (!Pattern.matches("^[67]\\d{8}", tfMobile.getText())) {
                    throw new WrongMobileFormatException("The phone number must start with 6 or 7 and can only contain 9 numbers.");
                    //Si cumple con el patrón correcto, verificamos si la etiqueta de error está visible.
                } else if (lblWrongMobile.isVisible()) {
                    //Si la etiqueta se había mostrado pero el error ya no existe, la ocultamos
                    lblWrongMobile.setVisible(false);
                }
            }
        } catch (WrongMobileFormatException ex) {
            //Establecemos el mensaje de error en la etiqueta.
            lblWrongMobile.setText(ex.getMessage());
            //Mostramos la etiqueta de error.
            lblWrongMobile.setVisible(true);
            Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, ex.getMessage());
        }

        // Una vez que todas las validaciones están realizadas, carga los datos de los campos en un objeto User. 
        if (!lblWrongEmail.isVisible() && !lblWrongMobile.isVisible() && !lblWrongName.isVisible() && !lblWrongPassword.isVisible() && !lblWrongPasswordMax.isVisible()
                && !lblWrongEmailMax.isVisible() && !lblWrongCityZip.isVisible()) {
            user = new User();
            user.setName(tfFirstName.getText() + " " + tfLastName.getText());
            user.setEmail(tfEmailSignUp.getText());
            user.setMobile(tfMobile.getText());
            user.setPassword(pfPasswordSignUp.getText());
            user.setCity(tfCity.getText());
            user.setPrivilege(Privilege.USER);
            user.setStreet(tfStreet.getText());
            user.setZip(tfZip.getText());

            try {
                //Recibir la respuesta de la implementación
                User userResponse = ClientFactory.getImplementation().signUp(user);

                if (userResponse != null) {
                    //Se muestra una alerta cuando el registro ha sido correcto.
                    new Alert(Alert.AlertType.CONFIRMATION, user.getName() + ", you have successfully registered.", ButtonType.OK).showAndWait();
                    //Se cierra la ventana de SignUp.
                    stage.close();
                }

            } catch (ServerErrorException ex) {
                //Muestra la alerta con el error que viene del servidor
                String errorMessage = ex.getMessage().replace("{name}", user.getName());
                new Alert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK).showAndWait();
                Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EmailExistsException ex) {
                String errorMessage = ex.getMessage().replace("{email}" , "'" + user.getEmail() + "'");
                new Alert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK).showAndWait();
                Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Handles the exit event.
     *
     * @param event The event for the exit action.
     */
    public void handleOnActionExit(Event event) {
        try {
            LOGGER.info("EXIT pressed.");
            //Pedir confirmación al usuario para salir:
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                   "Are you sure you want to exit the application?",
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
