package sample.Controller.Manager;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Main;
import sample.Model.Class;
import sample.Model.Master;
import sample.Model.Student;

import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerDashboardController implements Initializable {

    @FXML
    private AnchorPane showPane;

    @FXML
    private Label managerCount;

    @FXML
    private Group masterGRP;

    @FXML
    private Label masterCount;

    @FXML
    private Group studentGRP;

    @FXML
    private Label studCount;

    @FXML
    private Group classGRP;

    @FXML
    private Label classCount;

    @FXML
    private Group profileGRP;

    @FXML
    private Group messageGRP;


    @FXML
    private Label messagesCount;



    AnchorPane pane = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        managerCount.setText("1");
        studCount.setText(Integer.toString(Student.lastId -1));
        masterCount.setText(Integer.toString(Master.lastId -1));
        classCount.setText(Integer.toString(Class.lastId -1));

        
        studentGRP.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                try {
                    createPage("ManageStudentsPage");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });  masterGRP.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                try {
                    createPage("ManageMastersPage");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });  classGRP.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                try {
                    createPage("ManageClassesPage");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }); profileGRP.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                try {
                    createPage("ManagerProfilePage");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });




    }

    void createPage(String address) throws IOException {




        FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Manager/" + address + ".fxml"));
        pane = loader.load();
        showPane.getChildren().setAll(pane);
    }


}
