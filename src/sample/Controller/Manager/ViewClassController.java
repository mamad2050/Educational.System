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
import java.util.ArrayList;
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
    @FXML
    private JFXTextField masterPhoneField;

    @FXML
    private JFXTextField masterMailField;


    // this class columns
    @FXML
    private TableView<Student> thisClassStudentTable;
    @FXML
    private TableColumn<Student, Integer> studentIdThisClassColumn;
    @FXML
    private TableColumn<Student, String> fNameThisClassColumn;
    @FXML
    private TableColumn<Student, String> lNameThisClassColumn;
    @FXML
    private TableColumn<Student, String> phoneColumn;

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

    Student selectStudent;

    

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // this class fields
        classIdField.setText(Integer.toString(ManageClassesController.selectedClass.getClassId()));
        masterField.setText(ManageClassesController.selectedClass.getMasterObj().getFirstName()+" "+
                ManageClassesController.selectedClass.getMasterObj().getLastName()  );
        numberOfStudentField.setText(Integer.toString(ManageClassesController.selectedClass.getClassNumber()));
        lessonField.setText(ManageClassesController.selectedClass.getLessonName());
        capacityField.setText(Integer.toString(ManageClassesController.selectedClass.getCapacity()));
        masterPhoneField.setText(ManageClassesController.selectedClass.getMasterObj().getPhone());
        masterMailField.setText(ManageClassesController.selectedClass.getMasterObj().getEmail());

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
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        thisClassStudentTable.setItems(observableListThisClass);


        // end students this class


        addBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (checkThisStudent(selectStudent)) {
                    ManageClassesController.selectedClass.getStudentsList().add(selectStudent);
                    thisClassStudentTable.getItems().add(selectStudent);
                }


            }
        });


        studentsTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectStudent = studentsTable.getSelectionModel().getSelectedItem();
                studentIdField.setText(selectStudent.getStringId());
                firstnameField.setText(selectStudent.getFirstName());
                lastnameField.setText(selectStudent.getLastName());
                userField.setText(selectStudent.getUserName());
                phoneField.setText(selectStudent.getPhone());
                mailField.setText(selectStudent.getEmail());
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

    private boolean checkThisStudent(Student student) {

        ArrayList<Student> thisClassList = ManageClassesController.selectedClass.getStudentsList();

        if (thisClassList.isEmpty()) {
            return true;
        }

        for (Student std : thisClassList) {
            if (std == student) {
                return false;

            }
        }
        return true;
    }
}
