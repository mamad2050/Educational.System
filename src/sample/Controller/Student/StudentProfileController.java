package sample.Controller.Student;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.Controller.Login.LoginPageController;
import sample.Controller.FileChooser;
import sample.File.WriteAndReadFile;
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

    @FXML
    private ImageView imageField;

    @FXML
    private JFXButton chooseBTN;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        imageField.setImage(LoginPageController.studentLoggedIn.getPhoto());

        chooseBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser chooser = new FileChooser(LoginPageController.studentLoggedIn);
                chooser.chooseImageButtonPush(event,imageField);

            }
        });




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
