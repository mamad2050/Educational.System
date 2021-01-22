package sample.Controller.Manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Controller.FileChooser;
import sample.Controller.Login.LoginPageController;
import sample.Main;
import sample.Model.Manager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerPageController implements Initializable {
// --------------------------------   components   ---------------------------------

    @FXML
    private JFXListView<Label> listview;
    @FXML
    private JFXButton logoutBTN;
    @FXML
    private AnchorPane showPane;
    @FXML
    private ImageView imageField;
    @FXML
    private Button choose;
    @FXML
    private Label userLBL;
    static AnchorPane pane = null;
    private String user;
// --------------------------------   end   ---------------------------------


    @Override
    public void initialize(URL location, ResourceBundle resources) {
// --------------------------------   set image    ---------------------------------

        imageField.setImage(LoginPageController.managerLoggedIn.getPhoto());

// --------------------------------   choose photo   ---------------------------------

        choose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser chooser = new FileChooser(LoginPageController.managerLoggedIn);
                chooser.chooseImageButtonPush(event,imageField);
            }
        });
// --------------------------------   set user Label  ---------------------------------

        user = LoginPageController.managerLoggedIn.getUserName();
        userLBL.setText(Manager.managerList.get(0).getUserName());

        try {
            CreatePage("ManagerDashboard");

        } catch (IOException e) {
            e.printStackTrace();
        }
// --------------------------------  set list view labels    ---------------------------------

        Label dashboardLBL = new Label("Dashboard");
        Label studentsLBL = new Label("Students");
        Label mastersLBL = new Label("Masters");
        Label classesLBL = new Label("Classes");
        Label messagesLBL = new Label("Messages");
        Label profileLBL = new Label("Profile");

        listview.getItems().addAll(dashboardLBL, studentsLBL, mastersLBL, classesLBL,
                messagesLBL, profileLBL);

// --------------------------------  set on action for menu    ---------------------------------

        listview.setOnMouseClicked(event -> {
            if (!user.equals(Manager.managerList.get(0).getUserName())) {
                userLBL.setText(Manager.managerList.get(0).getUserName());
            }
            try {
                listViewListener();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
// --------------------------------   logout   ---------------------------------

        logoutBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logout(logoutBTN);
            }
        });

    }
// --------------------------------  call create page    ---------------------------------

    private void listViewListener() throws IOException {

        String selectedLabel = listview.getSelectionModel().getSelectedItem().getText().trim();

        if (selectedLabel.equals(listview.getItems().get(0).getText().trim())) {
            CreatePage("ManagerDashboard");

        } else if (selectedLabel.equals(listview.getItems().get(5).getText())) {
            CreatePage("ManagerProfilePage");
        } else if (selectedLabel.equals(listview.getItems().get(2).getText().trim())) {
            CreatePage("ManageMastersPage");
        } else if (selectedLabel.equals(listview.getItems().get(3).getText().trim())) {
            CreatePage("ManageClassesPage");
        } else if (selectedLabel.equals(listview.getItems().get(1).getText().trim())) {
            CreatePage("ManageStudentsPage");
        }


    }
// --------------------------------  create pages and load them    ---------------------------------

    public void CreatePage(String address) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Manager/" + address + ".fxml"));
        pane = loader.load();
        showPane.getChildren().setAll(pane);
    }
// --------------------------------  static method for logout from account    ---------------------------------

    public static void logout(JFXButton button) {

        ((Stage) button.getScene().getWindow()).close();

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Login/LoginPage.fxml"));
        Stage stage = new Stage();
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginPageController controller = loader.getController();
        controller.setStage(stage);
        stage.setScene(new Scene(loader.getRoot()));
        stage.show();

    }
// --------------------------------    end     ---------------------------------


}


