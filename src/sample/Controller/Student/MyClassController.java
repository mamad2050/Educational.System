package sample.Controller.Student;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import sample.Controller.Login.LoginPageController;
import sample.Main;
import sample.Model.Class;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyClassController implements Initializable {

    // ---------------------------------- components ------------------------
    @FXML
    private ImageView imageField;

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
    private StackPane stackPane;

    @FXML
    private JFXTextField classNumField;

    @FXML
    private JFXTextField masterIdField;

    @FXML
    private JFXTextField masterNameField;

    @FXML
    private JFXTextField lessonField;

    @FXML
    private JFXButton openBTN;

    AnchorPane pane;
    // ---------------------------------- end ------------------------
    public static Class selectedClass;

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
// ---------------------------------- set table properties ------------------------
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

// ---------------------------------- select classs  ------------------------
        myClassTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectedClass = myClassTable.getSelectionModel().getSelectedItem();
                imageField.setImage(selectedClass.getPhoto());
                classNumField.setText(Integer.toString(selectedClass.getClassNumber()));
                masterNameField.setText(selectedClass.getMasterObj().getFirstName() + " " + selectedClass
                        .getMasterObj().getLastName());
                lessonField.setText(selectedClass.getLessonName());
                masterIdField.setText(selectedClass.getMasterObj().getEmail());
            }
        });
// ---------------------------------- open class ------------------------
        openBTN.setOnAction(event -> {
            if (selectedClass.getPost() == null) {
                LoginPageController.loadDialog(stackPane,"error","not found post.");
            }
            else {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Student/PostPage.fxml"));
                try {
                    pane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                showPane.getChildren().setAll(pane);
            }
        });
// ---------------------------------- end ------------------------
    }
}
