package sample.Controller.Student;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sample.Controller.Login.LoginPageController;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentProfileController implements Initializable {
    @FXML
    private AnchorPane showPane;

    @FXML
    private JFXButton editBTN;

    @FXML
    private Label firstNameLBL;

    @FXML
    private Label lastNameLBL;

    @FXML
    private Label userLBL;

    @FXML
    private Label emailLBL;

    @FXML
    private Label idLBL;

    @FXML
    private Label phoneLBL;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstNameLBL.setText(LoginPageController.studentLoggedIn.getFirstName());
        lastNameLBL.setText(LoginPageController.studentLoggedIn.getLastName());
        emailLBL.setText(LoginPageController.studentLoggedIn.getEmail());
        phoneLBL.setText(LoginPageController.studentLoggedIn.getPhone());
        idLBL.setText(Integer.toString(LoginPageController.studentLoggedIn.getStudentId()));
        userLBL.setText(LoginPageController.studentLoggedIn.getUserName());

        editBTN.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Student/EditProfilePage.fxml"));
            AnchorPane pane = null;
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            pane = loader.getRoot();
            showPane.getChildren().setAll(pane);
        });
    }
}
