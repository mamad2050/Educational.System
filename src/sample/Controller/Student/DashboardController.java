package sample.Controller.Student;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.layout.StackPane;
import sample.Controller.Login.LoginPageController;
import sample.Model.Student;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController extends Control implements Initializable {

    @FXML
    private StackPane stackpane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


//        LoginPageController.loadDialog(stackpane,"Notification","Welcome");

        System.out.println(LoginPageController.studentLoggedIn.isNotification());
//
//
//        for (int i = 0; i < ; i++) {
//
//        }
        for (Student student :Student.studentList
                ) {
            if (student == LoginPageController.studentLoggedIn) {
                if (student.isNotification()) {
                    LoginPageController.loadDialog(stackpane,"Notification","You added to new Class");
                    LoginPageController.studentLoggedIn.setNotification(false);
                }
            }
        }

    }
}