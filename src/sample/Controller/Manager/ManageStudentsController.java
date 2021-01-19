package sample.Controller.Manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.File.WriteAndReadFile;
import sample.Model.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ManageStudentsController implements Initializable {

    @FXML
    private JFXButton deleteBTN;
    @FXML
    private JFXButton editBTN;
    @FXML
    private JFXButton addBTN;
    @FXML
    private AnchorPane showPane;
    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, Integer> studentIdColumn;
    @FXML
    private TableColumn<Student, String> firstnameColumn;
    @FXML
    private TableColumn<Student, String> lastnameColumn;
    @FXML
    private TableColumn<Student, String> usernameColumn;
    @FXML
    private TableColumn<Student, String> phoneColumn;
    @FXML
    private TableColumn<Student, String> emailColumn;

    @FXML
    private JFXTextField firstNameField;
    @FXML
    private JFXTextField lastNameField;
    @FXML
    private JFXTextField userField;
    @FXML
    private JFXTextField mailField;
    @FXML
    private JFXTextField phoneField;
    @FXML
    private Label errorLBL;
    @FXML
    private TextField searchField;

    Student selectStudent;
    String currentUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Student> observableList = FXCollections.observableArrayList(Student.studentList);

        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        studentTable.setItems(observableList);

        // search box
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
            sortedList.comparatorProperty().bind(studentTable.comparatorProperty());
            studentTable.setItems(sortedList);
        });
        // end search

        addBTN.setOnAction(event -> {

            try {
                studentCheckConditions(new Student(firstNameField.getText(), lastNameField.getText(), userField.getText(),
                        userField.getText(), mailField.getText(), phoneField.getText()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        studentTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectStudent = studentTable.getSelectionModel().getSelectedItem();
                firstNameField.setText(selectStudent.getFirstName());
                lastNameField.setText(selectStudent.getLastName());
                userField.setText(selectStudent.getUserName());
                phoneField.setText(selectStudent.getPhone());
                mailField.setText(selectStudent.getEmail());
                currentUser = userField.getText();
            }
        });

        editBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if ( userEditable(userField.getText())&& checkAllField() && checkUserField() && checkMailField() && checkPhoneField()) {
                    Student.studentList.get(selectStudent.getStudentId() - 1).setFirstName(firstNameField.getText());
                    Student.studentList.get(selectStudent.getStudentId() - 1).setLastName(lastNameField.getText());
                    Student.studentList.get(selectStudent.getStudentId() - 1).setUserName(userField.getText());
                    Student.studentList.get(selectStudent.getStudentId() - 1).setPhone(phoneField.getText());
                    Student.studentList.get(selectStudent.getStudentId() - 1).setEmail(mailField.getText());
                    studentTable.refresh();
                    errorLBL.setText("");
                }
            }
        });

        deleteBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Student deleteStudent = studentTable.getSelectionModel().getSelectedItem();
                Student.studentList.remove(deleteStudent);
                studentTable.getItems().remove(deleteStudent);
                try {
                    WriteAndReadFile.write();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void studentCheckConditions(Student student) throws IOException {
        if (checkAllField()) {
            if (nonDuplicatedUser(student.getUserName())) {
                if (checkPhoneField() && checkMailField() && checkUserField()) {
                    Student.studentList.add(student);
                    WriteAndReadFile.write();
                    studentTable.getItems().add(student);
                    Student.lastId++;
                    clearFields();
                    errorLBL.setText("");
                }
            }
        }

    }

    private boolean checkAllField() {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || userField.getText().isEmpty()
                || phoneField.getText().isEmpty() || mailField.getText().isEmpty()) {
            errorLBL.setText("Please fill all fields.");
            return false;
        }
        return true;
    }

    private boolean nonDuplicatedUser(String user) {
        for (Student student : Student.studentList) {
            if (student.getUserName().equals(user)) {
                errorLBL.setText("This username has already been selected.");
                return false;
            }

        }
        return true;
    }

    public boolean checkPhoneField() {
        if (phoneField.getText().matches("\\d{11}") && phoneField.getText().startsWith("09")) {
            return true;
        }
        errorLBL.setText("Phone must start with (09) and contains 11 digits. ");
        return false;
    }

    private boolean checkMailField() {
        if (mailField.getText().matches("^(.+)@(.+)$")) {
            return true;
        }
        errorLBL.setText("Mail must contain (@) and (.com)");
        return false;
    }

    private boolean checkUserField() {
        if (userField.getText().matches("[a-zA-Z0-9]{3,12}")) {
            return true;
        }
        errorLBL.setText("Username must 3 - 12 character");
        return false;
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        userField.clear();
        mailField.clear();
        phoneField.clear();
    }

    private boolean userEditable(String user) {

        if (currentUser.equals(userField.getText())) {

            errorLBL.setText("");
            return true;
        }
        for (Student student : Student.studentList) {
            if (student.getUserName().equals(user)) {
                errorLBL.setText("This username has already been selected.");
                return false;
            }

        }
        errorLBL.setText("");
        return true;
    }

}

