package sample.Controller.Manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Main;
import sample.Model.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewClassController implements Initializable {

    @FXML
    private AnchorPane showPane;

    // all student table
    @FXML
    private TableView<Student> studentsTable;
    @FXML
    private TableColumn<Student, Integer> studentIdColumn;
    @FXML
    private TableColumn<Student, String> firstnameColumn;
    @FXML
    private TableColumn<Student, String> lastnameColumn;


    // class fields

    @FXML
    private JFXTextField lessonField;
    @FXML
    private JFXTextField masterField;
    @FXML
    private JFXTextField capacityField;
    @FXML
    private JFXTextField numberOfStudentField;
    @FXML
    private JFXTextField classIdField;
    // this class columns
    @FXML
    private TableView<Student> thisClassStudentTable;
    @FXML
    private TableColumn<Student, Integer> studentIdThisClassColumn;
    @FXML
    private TableColumn<Student, String> fNameThisClassColumn;
    @FXML
    private TableColumn<Student, String> lNameThisClassColumn;

    //select student fields
    @FXML
    private JFXTextField studentIdField;
    @FXML
    private JFXTextField firstnameField;
    @FXML
    private JFXTextField userField;
    @FXML
    private JFXTextField lastnameField;
    @FXML
    private JFXTextField phoneField;
    @FXML
    private JFXTextField mailField;

    // buttons
    @FXML
    private JFXButton addBTN;
    @FXML
    private JFXButton deleteBTN;
    @FXML
    private JFXButton backBTN;

    AnchorPane pane = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // this class fields
        classIdField.setText(Integer.toString(ManageClassesController.selectedClass.getClassId()));
        masterField.setText(ManageClassesController.selectedClass.getMaster());
        numberOfStudentField.setText(Integer.toString(ManageClassesController.selectedClass.getClassNumber()));
        lessonField.setText(ManageClassesController.selectedClass.getLessonName());
        capacityField.setText(Integer.toString(ManageClassesController.selectedClass.getCapacity()));


        // all students list
        ObservableList<Student> observableList = FXCollections.observableArrayList(Student.studentList);

        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        studentsTable.setItems(observableList);
        // end all students list


        // students this class

        ObservableList<Student> observableListThisClass =
                FXCollections.observableArrayList(ManageClassesController.selectedClass.getStudentsList());

        studentIdThisClassColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        fNameThisClassColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lNameThisClassColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        thisClassStudentTable.setItems(observableListThisClass);


        // end students this class



        addBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Student student = null;
                try {
                    student = new Student(firstnameField.getText()
                            ,lastnameField.getText(),userField.getText()," ",mailField.getText(),phoneField.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ManageClassesController.selectedClass.getStudentsList().add(student);
                thisClassStudentTable.getItems().add(student);
            }
        });


        studentsTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Student student = studentsTable.getSelectionModel().getSelectedItem();
                studentIdField.setText(student.getStringId());
                firstnameField.setText(student.getFirstName());
                lastnameField.setText(student.getLastName());
                userField.setText(student.getUserName());
                phoneField.setText(student.getPhone());
                mailField.setText(student.getEmail());
            }
        });


        backBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Manager/ManageClassesPage.fxml"));
                try {
                    pane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                showPane.getChildren().setAll(pane);
            }

        });

    }
}
