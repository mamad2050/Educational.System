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
import javafx.scene.layout.VBox;
import sample.Controller.Login.LoginPageController;
import sample.Main;
import sample.Model.Comment;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class PostPageController implements Initializable {

// --------------------------------  components -----------------------
    @FXML
    private AnchorPane showPane;
    @FXML
    private JFXButton backBTN;
    @FXML
    private JFXButton sendBTN;
    @FXML
    private TextArea area;
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
// -------------------------------------- end ------------------------
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        content.getChildren().clear();
        for (Comment comment : Main.comments) {
            content.getChildren().add(comment.getRoot());
        }

       //--------------------- set components -------------------------------------
        masterIMG.setImage(MyClassController.selectedClass.getMasterObj().getPhoto());
        imageField.setImage(MyClassController.selectedClass.getPhoto());
        masterField.setText(MyClassController.selectedClass.getMasterObj().getFirstName() +
                " " + MyClassController.selectedClass.getMasterObj().getLastName());
        classNumberField.setText(Integer.toString(MyClassController.selectedClass.getClassNumber()));
        lessonField.setText(MyClassController.selectedClass.getLessonName());

        senderLBL.setText(MyClassController.selectedClass.getMasterObj().getUserName());
        lessonLBL.setText(MyClassController.selectedClass.getLessonName());

        area.setText(MyClassController.selectedClass.getPost().getArea());
        // --------------------------- back to classes page -----------------
        backBTN.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Student/MyClassesPage.fxml"));

            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            showPane.getChildren().setAll(pane);

        });
        //  ----------------------   send comment --------------------------

        sendBTN.setOnAction(event -> {
            Main.comments.add(new Comment(LoginPageController.studentLoggedIn.getUserName(), LoginPageController
                    .studentLoggedIn.getPhoto(), messageField.getText()));
            messageField.clear();
            refreshPage();
        });

    }
// ------------------------ refresh after comment ----------------------
    private void refreshPage() {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Student/PostPage.fxml"));

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showPane.getChildren().setAll(pane);
    }
}
