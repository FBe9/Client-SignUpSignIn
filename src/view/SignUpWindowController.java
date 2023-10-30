/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import exceptions.DatabaseErrorException;
import exceptions.EmailExistsException;
import exceptions.ServerErrorException;
import exceptions.WrongEmailFormatException;
import exceptions.WrongMobileFormatException;
import exceptions.WrongNameFormatException;
import exceptions.WrongPasswordFormatException;
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
 * @author Irati y Olivia
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

    private Image openEye;
    private Image closeEye;

    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("package view");
    protected final int MAX_LENGHT = 300;
    protected final int MAX_LENGHT_MOBILE = 9;

    /**
     * Initializes the controller class.
     *
     * @param root
     */
    public void initStage(Parent root) {
        LOGGER.info("Inicializando la ventana de SignUp");
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
        //Se muestra la ventana con un show and wait.
        stage.showAndWait();
        
        openEye = new Image("resources/eyeB.png", 25, 26, false, true);
        closeEye = new Image("resources/closeEyeB.png", 25, 26, false, true);

        //El icono del ToggleButton será el del ojo abierto. 
        tgbEye.setGraphic(new ImageView(openEye));
        stage.show();
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

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void textPropertyChange(ObservableValue observable,
            String oldValue,
            String newValue) {
        //Validar que los campos full name, email, password, confirm password están informados
        if (!tfFirstName.getText().trim().equals("") && !tfLastName.getText().trim().equals("") && !tfEmail.getText().trim().equals("") && !pfConfirmPassword.getText().trim().equals("")
                && !pfPassword.getText().trim().equals("")) {
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
        if (tfEmail.getText().trim().length() > MAX_LENGHT) {
            lblWrongEmailMax.setVisible(true);
        } else {
            lblWrongEmailMax.setVisible(false);
        }

        if (tfPassword.getText().trim().length() > MAX_LENGHT || tfConfirmPassword.getText().trim().length() > MAX_LENGHT || pfConfirmPassword.getText().trim().length() > MAX_LENGHT || pfPassword.getText().trim().length() > MAX_LENGHT) {
            lblWrongPasswordMax.setVisible(true);
        } else {
            lblWrongPasswordMax.setVisible(false);
        }

        //Validar que el campo teléfono (tfMobile) contenga un máximo de 9 caracteres. Si el usuario excede este límite se le informará mediante un texto hasta que el límite de caracteres sea menor o igual al correspondiente.
        if (tfMobile.getText().trim().length() > MAX_LENGHT_MOBILE) {
            lblWrongMobileMax.setVisible(true);
        } else {
            lblWrongMobileMax.setVisible(false);
        }
    }

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

    @FXML
    private void handlerSignUpButton(ActionEvent event) {
        User user = null;
        try {
            // Validar que el campo de nombre (tfFirstName) y el de apellido (tfLastName) no contengan valores numéricos, si no, lanzaremos la excepción “WrongNameFormatException”.
            if (!tfFirstName.getText().matches("^[A-Za-zÁÉÍÓÚÑáéíóúñ\\s]+$") || !tfLastName.getText().matches("^[A-Za-zÁÉÍÓÚÑáéíóúñ\\s]+$")) {
                throw new WrongNameFormatException("The name can't contain numbers.");
            } else if (lblWrongName.isVisible()) {
                lblWrongName.setVisible(false);
                lblWrongName.setText("");

            }

            //Validar que el campo del email (tfEmail) cumpla con el formato correcto, si no, lanzaremos la excepción “WrongEmailFormatException”.
            if (!tfEmail.getText().matches("([a-z0-9]*)@([a-z]*).(com|org|cn|net|gov|eus|es|io)")) {
                throw new WrongEmailFormatException("The email must have a valid format");
            } else if (lblWrongEmail.isVisible()) {
                lblWrongEmail.setVisible(false);
                lblWrongEmail.setText("");
            }

            //Validar que ambos campos de las contraseñas (pfPassword y pfConfirmPassword) contengan la misma información y contengan mínimo 8 caracteres, de los cuales mínimo 1 mayúscula, 1 minuscula, y al menos 1 caracter especial, si no, lanzaremos la excepción “WrongPasswordFormatException”.
            if (!pfConfirmPassword.getText().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\W).{8,}$") && !pfPassword.getText().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\W).{8,}$")) {
                throw new WrongPasswordFormatException("The password doesn't much with the required format");
            } else if (!pfConfirmPassword.getText().equals(pfPassword.getText())) {
                throw new WrongPasswordFormatException("The passwords do not match.");
            } else {
                lblWrongPassword.setVisible(false);
                lblWrongPassword.setText("");
            }

            //Validar que el campo del teléfono (tfMobile) empiece por 6 o 7, si no, lanzaremos la excepción “WrongMobileFormatException”.
            if (!tfMobile.getText().matches("[67]\\d{8}")) {
                throw new WrongMobileFormatException("The phone number must start with 6 or 7 and can only contain numbers.");
            } else if (lblWrongMobile.isVisible()) {
                lblWrongMobile.setVisible(false);
                lblWrongMobile.setText("");
            }

            // Una vez que todas las validaciones están realizadas, carga los datos de los campos en un objeto User. 
            if (!lblWrongEmail.isVisible() && !lblWrongMobile.isVisible() && !lblWrongName.isVisible() && !lblWrongPassword.isVisible() && !lblWrongPasswordMax.isVisible()
                    && !lblWrongEmailMax.isVisible() && !lblWrongEmailMax.isVisible()) {
                user = new User();
                user.setName(tfFirstName.getText() + " " + tfLastName.getText());
                user.setEmail(tfEmail.getText());
                user.setMobile(tfMobile.getText());
                user.setPassword(pfPassword.getText());
                user.setCity(tfCity.getText());
                user.setPrivilege(Privilege.USER);
                user.setStreet(tfStreet.getText());
                user.setZip(tfZip.getText());
            }

            try {
                User userResponse = ClientFactory.getImplementation().signUp(user);

                if (userResponse != null) {
                    stage.close();
                }

            } catch (ServerErrorException | DatabaseErrorException ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
                Logger.getLogger(SignInWindowController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EmailExistsException ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
                Logger.getLogger(SignInWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (WrongNameFormatException ex) {
            lblWrongName.setText(ex.getMessage());
            lblWrongEmail.setVisible(true);
        } catch (WrongEmailFormatException ex) {
            lblWrongEmail.setText(ex.getMessage());
            lblWrongEmail.setVisible(true);
        } catch (WrongPasswordFormatException ex) {
            lblWrongPassword.setText(ex.getMessage());
            lblWrongPassword.setVisible(true);
        } catch (WrongMobileFormatException ex) {
            lblWrongMobile.setText(ex.getMessage());
            lblWrongMobile.setVisible(true);
        }

    }

    public void handleOnActionExit(Event event) {
        try {
            //Pedir confirmación al usuario para salir:
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to exit?",
                    ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                //Si el usuario confirma, cerrar la ventana.
                stage.close();
            } else {
                //Si no confirma, mantenerse en la ventana.
                event.consume();
            }
        } catch (Exception e) {
            String errorMsg = "Error exiting application:" + e.getMessage();
            new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK).showAndWait();

        }

    }
}
