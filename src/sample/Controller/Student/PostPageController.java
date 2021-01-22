package sample.Controller.Student;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import sample.Main;
import sample.Model.Post;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PostPageController implements Initializable {


    @FXML
    private AnchorPane showPane;

    @FXML
    private JFXButton backBTN;

    @FXML
    private JFXTextArea area;

//    @FXML
//    private JFXTextField subjectField;


    @FXML
    private ImageView imageField;

    @FXML
    private JFXTextField classNumberField;

    @FXML
    private JFXTextField lessonField;

    @FXML
    private JFXTextField masterField;

    @FXML
    private Label senderLBL;

    @FXML
    private Label lessonLBL;

    @FXML
    private ImageView masterIMG;

    AnchorPane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        masterIMG.setImage(MyClassController.selectedClass.getMasterObj().getPhoto());
        imageField.setImage(MyClassController.selectedClass.getPhoto());

        masterField.setText(MyClassController.selectedClass.getMasterObj().getFirstName() +
                " " + MyClassController.selectedClass.getMasterObj().getLastName());
        classNumberField.setText(Integer.toString(MyClassController.selectedClass.getClassNumber()));
        lessonField.setText(MyClassController.selectedClass.getLessonName());

        senderLBL.setText(MyClassController.selectedClass.getMasterObj().getUserName());
        lessonLBL.setText(MyClassController.selectedClass.getLessonName());
//        subjectField.setText(MyClassController.selectedClass.getPost().getSubject());
        area.setText(MyClassController.selectedClass.getPost().getArea());


        backBTN.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Student/MyClassesPage.fxml"));

            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            showPane.getChildren().setAll(pane);
        });

    }
}
