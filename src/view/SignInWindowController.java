/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.User;

/**
 * FXML Controller class
 *
 * @author Leire y Nerea
 */
public class SignInWindowController {

    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfPassword;
    @FXML
    private Hyperlink httpSignUp;
    @FXML
    private Button btnAccept;
    @FXML
    private ImageView imgEye;
    @FXML
    private ToggleButton tgbEye;
    @FXML
    private PasswordField pfPasswd;
    @FXML
    private Text lblError;

    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("package view");

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
        //Ventana no redimensionable.
        stage.setResizable(false);
        //Se vacían los campos email y ambos password.
        tfEmail.setText("");
        pfPasswd.setText("");
        tfPassword.setText("");
        //Se enfoca el campo Email.
        tfEmail.requestFocus();
        //Se deshabilita el botón Accept.
        btnAccept.setDisable(false);
        //Mostrar la ventana. 
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handelAcceptButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/LoggedWindow.fxml"));
            Parent root = (Parent) loader.load();
            LoggedWindowController controller = (LoggedWindowController) loader.getController();
            controller.setStage(stage);
            User user = new User();
            user.setName("Nerea");

            controller.initStage(root, user);
        } catch (IOException ex) {
            Logger.getLogger(SignInWindowController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @FXML
    private void handelEyeToggleButtonAction(ActionEvent event) {
    }

    @FXML
    private void handelSignUpHyperlink(ActionEvent event) {
        try {
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

}
