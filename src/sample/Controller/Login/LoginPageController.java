package sample.Controller.Login;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import sample.Main;
import sample.Model.Manager;
import sample.Model.Master;
import sample.Model.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    private Stage stage;
    public static Manager managerLoggedIn;
    public static Student studentLoggedIn;
    public static Master masterLoggedIn;

    @FXML
    private StackPane stackPane;
    @FXML
    private Label errorLBL;

    @FXML
    private Hyperlink resetPassLink;

    @FXML
    private JFXButton loginBTN;

    @FXML
    private JFXPasswordField passField;

    @FXML
    private JFXTextField userField;


    public Stage getStage() {
        return stage;
    }


    public JFXPasswordField getPassField() {
        return passField;
    }

    public void setPassField(JFXPasswordField passField) {
        this.passField = passField;
    }

    public JFXTextField getUserField() {
        return userField;
    }

    public void setUserField(JFXTextField userField) {
        this.userField = userField;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    static Manager manager = new Manager("Matin", "Zahedi Nezhad",
            "1", "1", "mamadzn2050@gmail.com", "09106444880");


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Manager.managerList.add(manager);
        errorLBL.setText("");
        resetPassLink.setOnAction(new EventHandler<ActionEvent>() {

            @Override

            public void handle(ActionEvent actionEvent) {

                FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Login/ForgotPasswordPage.fxml"));
                try {
                    loader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ForgotPasswordController controller = loader.getController();
                controller.setStage(stage);
                stage.setScene(new Scene(loader.getRoot()));
                stage.setResizable(false);
                stage.show();

            }
        });


        loginBTN.setOnAction(event -> {
            try {
                findUser();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    private void findUser() throws IOException {


        //search in students
        for (int i = 0; i < Student.studentList.size(); i++) {
            if (userField.getText().equals(Student.studentList.get(i).getUserName())
                    && passField.getText().equals(Student.studentList.get(i).getPassword())) {
                studentLoggedIn = Student.studentList.get(i);
                createPage("Student/StudentPage");
            }
        }
        // search in masters
        for (Master master : Master.masterList) {
            if (userField.getText().equals(master.getUserName())
                    && passField.getText().equals(master.getPassword())) {
                masterLoggedIn = master;
                createPage("Master/MasterPage");
            }
        } //check manager
        if (manager.getUserName().equals(userField.getText()) &&
                manager.getPassword().equals(passField.getText())) {
            managerLoggedIn = manager;
            createPage("Manager/ManagerPage");
        } else {

            loadDialog(stackPane, "Error", "User Not Found.");

        }

    }


    private void createPage(String address) throws IOException {
        ((Stage) loginBTN.getScene().getWindow()).close();

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/" + address + ".fxml"));
        loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.getRoot()));
        stage.setResizable(false);
        stage.show();

    }

    public static void loadDialog(StackPane stackPane, String Heading, String Body) {

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(Heading));
        content.setBody(new Text(Body));
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.BOTTOM);
        dialog.show();
        JFXButton button = new JFXButton("OK");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        content.setActions(button);
    }


}
