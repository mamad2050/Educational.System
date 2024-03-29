package sample.Controller.Manager;

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

public class ManagerProfileController implements Initializable {

// --------------------------------    components  ---------------------------------

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
        // --------------------------------    set profile labels  ---------------------------------

        firstNameLBL.setText(LoginPageController.managerLoggedIn.getFirstName());
        lastNameLBL.setText(LoginPageController.managerLoggedIn.getLastName());
        emailLBL.setText(LoginPageController.managerLoggedIn.getEmail());
        phoneLBL.setText(LoginPageController.managerLoggedIn.getPhone());
        idLBL.setText(Integer.toString(LoginPageController.managerLoggedIn.getId()));
        userLBL.setText(LoginPageController.managerLoggedIn.getUserName());
// --------------------------------    set on action for edit button  ---------------------------------

        editBTN.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Manager/EditManagerProfilePage.fxml"));
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
