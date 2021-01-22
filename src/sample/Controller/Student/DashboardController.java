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

                if (LoginPageController.studentLoggedIn.getNotification()[1]) {
                    LoginPageController.loadDialog(stackpane, "Notification", "You added to new Class");
                    LoginPageController.studentLoggedIn.getNotification()[1] = false;
                }

        LoginPageController.loadDialog(stackpane, "Notification", "Welcome" +
                "   " + LoginPageController.studentLoggedIn.getFirstName());
    }

}