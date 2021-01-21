package sample.Controller.Student;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sample.Controller.Login.LoginPageController;
import sample.Model.Class;

import java.net.URL;
import java.util.ResourceBundle;

public class MyClassController implements Initializable {


    @FXML
    private AnchorPane showPane;

    @FXML
    private TableView<Class> myClassTable;

    @FXML
    private TableColumn<Class, Integer> classIdColumn;

    @FXML
    private TableColumn<Class, Integer> classNumColumn;

    @FXML
    private TableColumn<Class, String> lessonColumn;

    @FXML
    private TableColumn<Class, String> masterColumn;

    @FXML
    private TableColumn<Class, String> masterPhoneColumn;

    @FXML
    private TableColumn<Class, Integer> occupyColumn;
    @FXML
    private TableColumn<Class, String> mailColumn;

    @FXML
    private JFXButton addBTN;

    @FXML
    private JFXButton editBTN;

    @FXML
    private JFXButton deleteBTN;

    @FXML
    private JFXTextField classNumField;

    @FXML
    private JFXTextField masterIdField;

    @FXML
    private JFXTextField masterNameField;

    @FXML
    private JFXTextField lessonField;

    @FXML
    private Label errorLBL;

    @FXML
    private TextField searchField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Class> observableList = FXCollections.observableArrayList();
        for (Class classs : Class.classList) {
            for (int i = 0; i < classs.getStudentsList().size(); i++) {
                if (classs.getStudentsList().get(i) == LoginPageController.studentLoggedIn) {
                    observableList.add(classs);
                }
            }
        }

        classIdColumn.setCellValueFactory(new PropertyValueFactory<>("classId"));
        classNumColumn.setCellValueFactory(new PropertyValueFactory<>("classNumber"));
        lessonColumn.setCellValueFactory(new PropertyValueFactory<>("lessonName"));

        occupyColumn.setCellValueFactory(new PropertyValueFactory<>("occupy"));
        masterPhoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().
                getMasterObj().getPhone()));
        masterColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMasterObj()
                .getFirstName() + " " + cellData.getValue().getMasterObj().getLastName()));
        mailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMasterObj().getEmail()));

        myClassTable.setItems(observableList);


    }
}
