package sample.Controller.Master;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import sample.Controller.Login.LoginPageController;
import sample.Controller.Manager.ManageClassesController;
import sample.Main;
import sample.Model.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

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

    @FXML
    private JFXButton openBTN;

    @FXML
    private ImageView imageField;

    @FXML
    private TextField searchField;

    // buttons
    @FXML
    private JFXButton addBTN;

    @FXML
    private JFXButton deleteBTN;

    @FXML
    private JFXButton postBTN;

    @FXML
    private JFXButton backBTN;

    AnchorPane pane = null;

    public static Student selectStudent;

    @FXML
    private StackPane stackPane;


    @Override

    public void initialize(URL location, ResourceBundle resources) {

        imageField.setImage(CreateClassController.selectedClass.getPhoto());

        // this class fields
        classIdField.setText(Integer.toString(CreateClassController.selectedClass.getClassId()));
        masterField.setText(CreateClassController.selectedClass.getMasterObj().getFirstName() + " " +
                CreateClassController.selectedClass.getMasterObj().getLastName());
        numberOfStudentField.setPromptText("Occupy");
        numberOfStudentField.setText(Integer.toString(CreateClassController.selectedClass.getOccupy()));
        lessonField.setText(CreateClassController.selectedClass.getLessonName());
        capacityField.setText(Integer.toString(CreateClassController.selectedClass.getCapacity()));
        masterPhoneField.setText(CreateClassController.selectedClass.getMasterObj().getPhone());
        masterMailField.setText(CreateClassController.selectedClass.getMasterObj().getEmail());

        // all students list
        ObservableList<Student> observableList = FXCollections.observableArrayList(Student.studentList);

        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        studentsTable.setItems(observableList);
        // end all students list


        // students this class


        ObservableList<Student> observableListThisClass =
                FXCollections.observableArrayList(CreateClassController.selectedClass.getStudentsList());

        studentIdThisClassColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        fNameThisClassColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lNameThisClassColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        thisClassStudentTable.setItems(observableListThisClass);


        // end students this class


        //  ------------------  search box for all student -----------------
        FilteredList<Student> filteredList = new FilteredList<>(observableList, e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super Student>) student -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (student.getStringId().contains(newValue)) {
                        return true;
                    } else if (student.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (student.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Student> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(studentsTable.comparatorProperty());
            studentsTable.setItems(sortedList);
        });
        //    ------------------------------------------------

        addBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (selectStudent == null) {
                    LoginPageController.loadDialog(stackPane, "Add Student", "Please Select Student. ");
                } else if (checkThisStudent(selectStudent)) {
                    CreateClassController.selectedClass.getStudentsList().add(selectStudent);
                    thisClassStudentTable.getItems().add(selectStudent);
                    CreateClassController.selectedClass.setOccupy("+");
                    LoginPageController.loadDialog(stackPane, "Add Student",
                            "A student with the following profile was added to this class : \n\n\n"
                                    + "Student id : " + selectStudent.getStringId() + "\n" +
                                    "Name : " + selectStudent.getFirstName() + " " + selectStudent.getLastName() + "\n"
                                    + "Phone : " + selectStudent.getPhone());
                    for (Student student : Student.studentList) {
                        if (student == selectStudent) {
                            student.getMyClasses().add(CreateClassController.selectedClass);
                            student.setNotification(true);
                            clearFields();
                        }
                    }
                    numberOfStudentField.setText(Integer.toString(CreateClassController.selectedClass.getOccupy()));
                }
            }
        });

        postBTN.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Class/PostPage.fxml"));
            try {

                pane =loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            showPane.getChildren().setAll(pane);
        });

        deleteBTN.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                CreateClassController.selectedClass.getStudentsList().remove(selectStudent);
                thisClassStudentTable.getItems().remove(selectStudent);
                CreateClassController.selectedClass.setOccupy("-");

                LoginPageController.loadDialog(stackPane, "Remove Student",
                        "A student with the following profile was Deleted from this class : \n\n\n"
                                + "Student id : " + selectStudent.getStringId() + "\n" +
                                "Name : " + selectStudent.getFirstName() + " " + selectStudent.getLastName() + "\n"
                                + "Phone : " + selectStudent.getPhone());
                clearFields();
                for (Student student : Student.studentList) {
                    if (student == selectStudent) {
                        student.getMyClasses().remove(CreateClassController.selectedClass);
                        clearFields();
                    }
                }
                numberOfStudentField.setText(Integer.toString(CreateClassController.selectedClass.getOccupy()));

            }

        });
//

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
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Master/CreateClassPage.fxml"));
                try {
                    pane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                showPane.getChildren().setAll(pane);
                ManageClassesController.selectedClass = null;
            }

        });

    }

    private boolean checkThisStudent(Student student) {

        ArrayList<Student> thisClassList = CreateClassController.selectedClass.getStudentsList();

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

    private void clearFields() {
        firstnameField.clear();
        lastnameField.clear();
        studentIdField.clear();
        userField.clear();
        phoneField.clear();
        mailField.clear();
    }
}
