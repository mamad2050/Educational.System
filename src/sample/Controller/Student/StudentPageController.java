package sample.Controller.Student;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Controller.Login.LoginPageController;
import sample.Controller.Manager.ManagerPageController;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentPageController implements Initializable {

    @FXML
    private ImageView imageField;

    @FXML
    private JFXListView<Label> listview;

    @FXML
    private JFXButton logoutBTN;

    @FXML
    private Label userLBL;


    @FXML
    private AnchorPane showPane;

    static AnchorPane pane = null;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        imageField.setImage(LoginPageController.studentLoggedIn.getPhoto());

        try {
            CreatePage("DashboardPage");
        } catch (IOException e) {
            e.printStackTrace();
        }


        userLBL.setText(LoginPageController.studentLoggedIn.getUserName());
        Label dashboardLBL = new Label("Dashboard");
        Label profileLBL = new Label("Profile");
        Label classesLBL = new Label("My Classes");
        Label lessonsLBL = new Label("My Lessons");
        Label messagesLBL = new Label("My Messages");
        listview.getItems().addAll(dashboardLBL, profileLBL, classesLBL, lessonsLBL, messagesLBL);
        listview.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    listViewListener();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        logoutBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ManagerPageController.logout(logoutBTN);

            }
        });
    }

    private void listViewListener() throws IOException {

        String selectedLabel = listview.getSelectionModel().getSelectedItem().getText().trim();


        if (selectedLabel.equals(listview.getItems().get(0).getText().trim())) {
            CreatePage("DashboardPage");
        } else if (selectedLabel.equals(listview.getItems().get(1).getText())) {
            CreatePage("ProfilePage");
        } else if (selectedLabel.equals(listview.getItems().get(2).getText().trim())) {
            CreatePage("MyClassesPage");
        } else if (selectedLabel.equals(listview.getItems().get(3).getText().trim())) {
            CreatePage("ManageClassesPage");
        }

    }

    public void CreatePage(String address) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Student/" + address + ".fxml"));
        pane = loader.load();
        showPane.getChildren().setAll(pane);
    }

}
