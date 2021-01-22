package sample.Controller.Class;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import sample.Controller.Login.LoginPageController;
import sample.Controller.Master.CreateClassController;
import sample.Main;
import sample.Model.Post;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PostController extends Control implements Initializable {

    // ----------------------------------------  components   ------------------------------------------
//    @FXML
//    public VBox content;

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

// ---------------------------------------- end    ------------------------------------------

    @Override
    public void initialize(URL location, ResourceBundle resources) {


// -----------------------------------  set on actions  -------------------------------------

        sendBTN.setOnAction(event -> {
          if  (  checkFields()   ) {
              Post post = new Post(subjectField.getText(), textArea.getText());
              Post.posts.add(post);
              CreateClassController.selectedClass.setPost(post);
              LoginPageController.loadDialog(stackpane, "Send Post", "Successful");
          }
        });
// --------------------------------     back to   all classes page  ---------------------------

        backBTN.setOnAction(event -> {

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Master/ViewClassPage.fxml"));
            AnchorPane pane = null;
            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            showPane.getChildren().setAll(pane);
        });
    }//----------------------------------------check fields -------------------------------------


    private boolean checkFields(){
        if (textArea.getText().isEmpty() || subjectField.getText().isEmpty()) {
            LoginPageController.loadDialog(stackpane, "Send Post", "Please fill all fields.");
            return false;

        }
        return true;
    }


    // ----------------------------------------   end  ------------------------------------------

}
