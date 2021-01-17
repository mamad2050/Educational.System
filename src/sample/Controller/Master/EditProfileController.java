package sample.Controller.Master;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Controller.Login.LoginPageController;
import sample.Main;
import sample.Model.Master;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditProfileController implements Initializable {

    @FXML
    private AnchorPane showPane;
    

    @FXML
    private JFXButton changePassBTN;

    @FXML
    private Label changeLBL;

    @FXML
    private Label saveLBL;

    @FXML
    private JFXTextField firstnameField;

    @FXML
    private JFXTextField lastnameField;

    @FXML
    private JFXTextField personnelField;

    @FXML
    private JFXTextField phoneField;

    @FXML
    private JFXTextField mailField;

    @FXML
    private JFXTextField IdField;

    @FXML
    private JFXButton saveBTN;

    @FXML
    private JFXTextField currentPassField;

    @FXML
    private JFXTextField newPassField;

    @FXML
    private JFXButton backBTN;

    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    static int masterId = LoginPageController.masterLoggedIn.getMasterId() - 1;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        firstnameField.setText(LoginPageController.masterLoggedIn.getFirstName());
        lastnameField.setText(LoginPageController.masterLoggedIn.getLastName());
        personnelField.setText(LoginPageController.masterLoggedIn.getUserName());
        mailField.setText(LoginPageController.masterLoggedIn.getEmail());
        phoneField.setText(LoginPageController.masterLoggedIn.getPhone());
        IdField.setText(Integer.toString(LoginPageController.masterLoggedIn.getMasterId()));


        backBTN.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Master/ProfilePage.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AnchorPane pane = loader.getRoot();
            showPane.getChildren().setAll(pane);
        });

        saveBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                if (checkAllField() && checkPhoneField() && checkMailField() && checkUserField()) {
                    Master.masterList.get(masterId).setFirstName(firstnameField.getText());
                    Master.masterList.get(masterId).setLastName(lastnameField.getText());
                    Master.masterList.get(masterId).setUserName(personnelField.getText());
                    Master.masterList.get(masterId).setPhone(phoneField.getText());
                    Master.masterList.get(masterId).setEmail(mailField.getText());
                    saveLBL.setTextFill(Color.GREEN);
                    saveLBL.setText("Successful.");
                }
            }
        });

        changePassBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (currentPassField.getText().equals(Master.masterList.get(masterId).getPassword())) {
                    if (!newPassField.getText().isEmpty()) {
                        Master.masterList.get(masterId).setPassword(newPassField.getText());
                        changeLBL.setTextFill(Color.GREEN);
                        changeLBL.setText("Successful.");
                    }
                }
            }
        });
    }

    private boolean checkAllField() {
        if (firstnameField.getText().isEmpty() || lastnameField.getText().isEmpty() || personnelField.getText().isEmpty()
                || phoneField.getText().isEmpty() || mailField.getText().isEmpty()) {
            saveLBL.setTextFill(Color.RED);
            saveLBL.setText("Please fill all fields.");
            return false;
        }
        return true;
    }


    public boolean checkPhoneField() {
        if (phoneField.getText().matches("\\d{11}") && phoneField.getText().startsWith("09")) {
            return true;
        }
        saveLBL.setTextFill(Color.RED);
        saveLBL.setText("Phone must start with (09) and contains 11 digits. ");
        return false;
    }

    private boolean checkMailField() {
        if (mailField.getText().matches("^(.+)@(.+)$")) {
            return true;
        }
        saveLBL.setTextFill(Color.RED);
        saveLBL.setText("Mail must contain (@) and (.com)");
        return false;
    }

    private boolean checkUserField() {
        if (personnelField.getText().matches("[a-zA-Z0-9]{3,12}")) {
            return true;
        }
        saveLBL.setTextFill(Color.RED);
        saveLBL.setText("Username must 3 - 12 character");
        return false;
    }

}
