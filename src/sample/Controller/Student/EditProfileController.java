package sample.Controller.Student;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Controller.Login.LoginPageController;
import sample.Main;
import sample.Model.Student;
import sample.Model.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditProfileController implements Initializable {
    @FXML
    private AnchorPane showPane;
    @FXML
    private StackPane stackPane;
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
    private Label saveLBL;

    @FXML
    private Label changeLBL;


    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    static int studentId = LoginPageController.studentLoggedIn.getStudentId() - 1;



    @Override
    public void initialize(URL location, ResourceBundle resources) {


        firstnameField.setText(LoginPageController.studentLoggedIn.getFirstName());
        lastnameField.setText(LoginPageController.studentLoggedIn.getLastName());
        userField.setText(LoginPageController.studentLoggedIn.getUserName());
        mailField.setText(LoginPageController.studentLoggedIn.getEmail());
        phoneField.setText(LoginPageController.studentLoggedIn.getPhone());
        managerIdField.setText(Integer.toString(LoginPageController.studentLoggedIn.getStudentId()));


        backBTN.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Student/ProfilePage.fxml"));
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


                if (checkAllField() && checkPhoneField() && checkMailField() && checkUserField() && checkNameField()) {
                    Student.studentList.get(studentId).setFirstName(firstnameField.getText());
                    Student.studentList.get(studentId).setLastName(lastnameField.getText());
                    Student.studentList.get(studentId).setUserName(userField.getText());
                    Student.studentList.get(studentId).setPhone(phoneField.getText());
                    Student.studentList.get(studentId).setEmail(mailField.getText());
//                    saveLBL.setTextFill(Color.GREEN);
//                    saveLBL.setText("Successful.");
                    LoginPageController.loadDialog(stackPane,"change Profile","Successful");
                }
            }
        });

        changePassBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (currentPassField.getText().equals(Student.studentList.get(studentId).getPassword())) {
                    if (!newPassField.getText().isEmpty() && !currentPassField.getText().isEmpty()
                    && checkPassword() ) {
                        Student.studentList.get(studentId).setPassword(newPassField.getText());
//                        changeLBL.setTextFill(Color.GREEN);
//                        changeLBL.setText("Successful.");
                        LoginPageController.loadDialog(stackPane,"Change Password","Successful");
                    }
                }
                else
                    LoginPageController.loadDialog(stackPane,"Change Password","your current password is wrong");
            }
        });
    }
    private boolean checkAllField() {
        if (firstnameField.getText().isEmpty() || lastnameField.getText().isEmpty() || userField.getText().isEmpty()
                || phoneField.getText().isEmpty() || mailField.getText().isEmpty()) {
//            saveLBL.setTextFill(Color.RED);
//            saveLBL.setText("Please fill all fields.");
            LoginPageController.loadDialog(stackPane,"Edit Profile","Please fill all fields");
            return false;
        }
        return true;
    }


    public  boolean checkPhoneField() {
        if (phoneField.getText().matches("\\d{11}") && phoneField.getText().startsWith("09")) {
            return true;
        }
        LoginPageController.loadDialog(stackPane,"Edit Profile","Phone must start with (09) and contains 11 digits.");
//        saveLBL.setTextFill(Color.RED);
//        saveLBL.setText("Phone must start with (09) and contains 11 digits. ");
        return false;
    }

    private boolean checkMailField() {
        if (mailField.getText().matches("^(.+)@(.+)$")) {
            return true;
        }
        LoginPageController.loadDialog(stackPane,"Edit Profile","Mail must contain (@) and (.com)");
//        saveLBL.setTextFill(Color.RED);
//        saveLBL.setText("Mail must contain (@) and (.com)");
        return false;
    }

    private boolean checkUserField() {
        if (userField.getText().matches("[a-zA-Z0-9]{3,12}")) {
            return true;
        }
        LoginPageController.loadDialog(stackPane,"Edit Profile","Username must 3 - 12 character");
//        saveLBL.setTextFill(Color.RED);
//        saveLBL.setText("Username must 3 - 12 character");
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
