package sample.Controller.Student;

import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;
import sample.Main;
import sample.Model.Lesson;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController extends Control implements Initializable {


    public VBox content;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        content =new VBox();
        for (Lesson lesson : Main.lessons ) {
            content.getChildren().add(lesson.getRoot());
        }
    }
}
