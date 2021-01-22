package sample.Controller.Class;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import sample.Controller.Login.LoginPageController;
import sample.Controller.Master.CreateClassController;
import sample.Main;
import sample.Model.Post;
import sample.Model.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PostController implements Initializable {


    @FXML
    private AnchorPane showPane;

    @FXML
    private JFXButton backBTN;

    @FXML
    private TextArea textArea;

    @FXML
    private JFXButton sendBTN;

    @FXML
    private StackPane stackpane;

    @FXML
    private JFXTextField subjectField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sendBTN.setOnAction(event -> {
            Post post = new Post(subjectField.getText(), textArea.getText());
            Post.posts.add(post);
            CreateClassController.selectedClass.setPost(post);
            LoginPageController.loadDialog(stackpane,"Send Post" , "Successful");

        });

        backBTN.setOnAction(event -> {

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Master/ViewClassPage.fxml"));
            AnchorPane pane = null;
            try {
                pane=loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            showPane.getChildren().setAll(pane);
        });
    }
}
