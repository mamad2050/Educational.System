package sample.Controller.Master;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Controller.Login.LoginPageController;
import sample.Controller.Manager.ManagerPageController;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MasterPageController implements Initializable {
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

        userLBL.setText(LoginPageController.masterLoggedIn.getUserName());
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
//                ((Stage) logoutBTN.getScene().getWindow()).close();
////
////                Stage stage = new Stage();
////                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../../View/Login/LoginPage.fxml"));
////                try {
////                    loader.load();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////                LoginPageController controller = loader.getController();
////                controller.setStage(stage);
////                stage.setScene(new Scene(loader.getRoot()));
////                stage.show();
            }
        });
    }

    private void listViewListener() throws IOException {

        String selectedLabel = listview.getSelectionModel().getSelectedItem().getText().trim();

        if (selectedLabel.equals(listview.getItems().get(0).getText().trim())) {
            CreatePage("ManagerDashboard");
        } else if (selectedLabel.equals(listview.getItems().get(1).getText())) {
            CreatePage("ProfilePage");
        } else if (selectedLabel.equals(listview.getItems().get(2).getText().trim())) {
            CreatePage("ManageMastersPage");
        } else if (selectedLabel.equals(listview.getItems().get(3).getText().trim())) {
            CreatePage("ManageClassesPage");
        }

    }

    public void CreatePage(String address) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Master/" + address + ".fxml"));
        pane = loader.load();
        showPane.getChildren().setAll(pane);
    }
}

