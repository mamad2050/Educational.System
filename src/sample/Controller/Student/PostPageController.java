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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import sample.Controller.Login.LoginPageController;
import sample.Main;
import sample.Model.Comment;
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
    private TextArea area;

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
    private JFXTextField messageField;

    @FXML
    private Label lessonLBL;

    @FXML
    private ImageView masterIMG;

    AnchorPane pane;
    public VBox content;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        TextFlow textFlow =new TextFlow(new Text(""));


        content.getChildren().clear();
        for (Comment comment : Main.comments) {
            content.getChildren().add(comment.getRoot());
        }


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

            Main.comments.add(new Comment(LoginPageController.studentLoggedIn.getUserName(),LoginPageController
                    .studentLoggedIn.getPhoto(),messageField.getText()));
        });

    }
}
