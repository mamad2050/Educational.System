package sample.Controller.Manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Controller.Login.LoginPageController;
import sample.Main;
import sample.Model.Manager;
import sample.Model.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.zip.CheckedOutputStream;

public class EditManagerProfileController implements Initializable {
//--------------------------- components ------------------
    @FXML
    private AnchorPane showPane;
    @FXML
    private JFXButton backBTN;
    @FXML
    private JFXButton changePassBTN;
    @FXML
    private JFXButton saveBTN;
    @FXML
    private JFXTextField firstnameField;
    @FXML
    private JFXTextField lastnameField;
    @FXML
    private JFXTextField userField;
    @FXML
    private JFXTextField phoneField;
    @FXML
    private JFXTextField mailField;
    @FXML
    private JFXTextField managerIdField;
    @FXML
    private JFXTextField currentPassField;
    @FXML
    private JFXTextField newPassField;
    @FXML
    private StackPane stackPane;
    @FXML
    private Label changeLBL;

    private Stage stage;
    //-----------------------------------------------------

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    static int managerId = LoginPageController.managerLoggedIn.getId() - 1;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

 //------------------------ set profile fields ---------------------------------

        firstnameField.setText(LoginPageController.managerLoggedIn.getFirstName());
        lastnameField.setText(LoginPageController.managerLoggedIn.getLastName());
        userField.setText(LoginPageController.managerLoggedIn.getUserName());
        mailField.setText(LoginPageController.managerLoggedIn.getEmail());
        phoneField.setText(LoginPageController.managerLoggedIn.getPhone());
        managerIdField.setText(Integer.toString(LoginPageController.managerLoggedIn.getId()));

 //------------------------- set on actions -----------------------------
        backBTN.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Manager/ManagerProfilePage.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AnchorPane pane = loader.getRoot();
            showPane.getChildren().setAll(pane);
        });

        saveBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            // --------------------- save profile -------------------------
                if (checkAllField() && checkPhoneField() && checkMailField() && checkUserField() && checkNameField()) {
                    Manager.managerList.get(managerId).setFirstName(firstnameField.getText());
                    Manager.managerList.get(managerId).setLastName(lastnameField.getText());
                    Manager.managerList.get(managerId).setUserName(userField.getText());
                    Manager.managerList.get(managerId).setPhone(phoneField.getText());
                    Manager.managerList.get(managerId).setEmail(mailField.getText());

                    LoginPageController.loadDialog(stackPane,"Edit Profile","Successful");
                }
                }
        });

        changePassBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //------------------------ change password -----------------

                if (currentPassField.getText().equals(Manager.managerList.get(managerId).getPassword())) {
                    if (!newPassField.getText().isEmpty() && !currentPassField.getText().isEmpty() && checkPassword()) {
                        Manager.managerList.get(managerId).setPassword(newPassField.getText());
                        changeLBL.setTextFill(Color.GREEN);
                        LoginPageController.loadDialog(stackPane,"Change Password","Successful");
                    }
                }
                else
                    LoginPageController.loadDialog(stackPane,"Change Password","your current password is wrong");
            }
        });
    }
    // --------------------------------- end set on actions ----------------------


    // --------------------------------- check clear fields --------------------
    private boolean checkAllField() {
        if (firstnameField.getText().isEmpty() || lastnameField.getText().isEmpty() || userField.getText().isEmpty()
                || phoneField.getText().isEmpty() || mailField.getText().isEmpty()) {

            LoginPageController.loadDialog(stackPane,"Edit Profile","Please fill all fields");
            return false;
        }
        return true;
    }

// --------------------------------- check Phone number  --------------------

    public  boolean checkPhoneField() {
        if (phoneField.getText().matches("\\d{11}") && phoneField.getText().startsWith("09")) {
            return true;
        }
        LoginPageController.loadDialog(stackPane,"Edit Profile","Phone must start with (09) and contains 11 digits.");
        return false;
    }
    // --------------------------------- check Email   --------------------

    private boolean checkMailField() {
        if (mailField.getText().matches("^(.+)@(.+)$")) {
            return true;
        }
        LoginPageController.loadDialog(stackPane,"Edit Profile","Mail must contain (@) and (.com)");
        return false;
    }

    // -------------------------------- check username -------------------

    private boolean checkUserField() {
        if (userField.getText().matches("[a-zA-Z0-9]{3,12}")) {
            return true;
        }
        LoginPageController.loadDialog(stackPane,"Edit Profile","Username must 3 - 12 character");
        return false;
    }
  // --------------------------- check firstname and lastname -------------
    private boolean checkNameField(){
        if (firstnameField.getText().matches("^[a-zA-Z\\s]+") && lastnameField.getText().matches("^[a-zA-Z\\s]+") ) {
            return true;
        }
        LoginPageController.loadDialog(stackPane,"Edit Profile","Your Firstname and Lastname must contains a-zA-Z");

        return  false;
    }

    private  boolean checkPassword(){
        if (newPassField.getText().length() >= 5) {
            return true;
        }
        LoginPageController.loadDialog(stackPane,"Change Password","Your Password must contain more than 5" +
                " character or digits");

        return false;
    }

    //--------------------------- end --------------------

}
