package sample.Controller.Master;

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

public class MasterProfileController implements Initializable {

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
        firstNameLBL.setText(LoginPageController.masterLoggedIn.getFirstName());
        lastNameLBL.setText(LoginPageController.masterLoggedIn.getLastName());
        emailLBL.setText(LoginPageController.masterLoggedIn.getEmail());
        phoneLBL.setText(LoginPageController.masterLoggedIn.getPhone());
        idLBL.setText(Integer.toString(LoginPageController.masterLoggedIn.getMasterId()));
        userLBL.setText(LoginPageController.masterLoggedIn.getUserName());

        editBTN.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Master/ProfilePage.fxml"));
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
