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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import sample.Controller.Login.LoginPageController;
import sample.File.WriteAndReadFile;
import sample.Model.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ManageStudentsController implements Initializable {
 //--------------------------------- components ---------------------------
    @FXML
    private JFXButton deleteBTN;
    @FXML
    private JFXButton editBTN;
    @FXML
    private JFXButton addBTN;
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
    @FXML
    private JFXButton clearBTN;
    @FXML
    private ImageView imageField;
    @FXML
    private ImageView addimg;
    @FXML
    private StackPane stackPane;

    Student selectStudent;

    String currentUser;
// -------------------------------------- end ----------------------------

    @Override
    public void initialize(URL location, ResourceBundle resources) {
// ----------------------- set table properties ------------------
        ObservableList<Student> observableList = FXCollections.observableArrayList(Student.studentList);

        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        studentTable.setItems(observableList);


        //---------------------------------------- search box -------------------------------
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
        //--------------------------------------- end search ------------------------


      // ---------------------------------  set on actions  -------------- ----------
        addBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    studentCheckConditions( new Student(firstNameField.getText(), lastNameField.getText(), userField.getText(),
                            userField.getText(), mailField.getText(), phoneField.getText()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // --------------------- clear fields --------------
        clearBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearFields();
                addimg.setVisible(true);
                imageField.setImage(new Image("sample/view/drawable/teacher.png"));
            }
        });
        // --------------  select student from table -------------------
        studentTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addimg.setVisible(false);
                selectStudent = studentTable.getSelectionModel().getSelectedItem();
                firstNameField.setText(selectStudent.getFirstName());
                lastNameField.setText(selectStudent.getLastName());
                userField.setText(selectStudent.getUserName());
                phoneField.setText(selectStudent.getPhone());
                mailField.setText(selectStudent.getEmail());
                currentUser = userField.getText();
                imageField.setImage(selectStudent.getPhoto());
            }
        });

        editBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
// ------------------------ check conditions for add student -------------------
                if (userEditable(userField.getText()) && checkAllField() && checkUserField() && checkMailField()
                        && checkPhoneField() && checkNameField() ) {
                    Student.studentList.get(selectStudent.getStudentId() - 1).setFirstName(firstNameField.getText());
                    Student.studentList.get(selectStudent.getStudentId() - 1).setLastName(lastNameField.getText());
                    Student.studentList.get(selectStudent.getStudentId() - 1).setUserName(userField.getText());
                    Student.studentList.get(selectStudent.getStudentId() - 1).setPhone(phoneField.getText());
                    Student.studentList.get(selectStudent.getStudentId() - 1).setEmail(mailField.getText());
                    studentTable.refresh();
                    errorLBL.setText("");
                    // ------------------- write in file ------------------
                    try {
                        WriteAndReadFile.write();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
  //------------------------ delete student from system -------------------
        deleteBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Student deleteStudent = studentTable.getSelectionModel().getSelectedItem();
                Student.studentList.remove(deleteStudent);
                studentTable.getItems().remove(deleteStudent);
                LoginPageController.loadDialog(stackPane, "Delete Student",
                        "A student with the following profile was deleted from System : \n\n\n"
                                + "Student id : " + selectStudent.getStringId() + "\n" +
                                "Name : " + selectStudent.getFirstName() + " " + selectStudent.getLastName() + "\n"
                                + "Phone : " + selectStudent.getPhone());
                try {
                  //  --------------  write in file ------------------
                    WriteAndReadFile.write();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
   //   ------------------------------ end set on actions -------------------------------
    }
   // ------------------------ check conditions for add student -------------------
    private void studentCheckConditions(Student student) throws IOException {
        if (checkAllField()) {
            if (nonDuplicatedUser(student.getUserName())) {
                if (checkPhoneField() && checkMailField() && checkUserField() && checkNameField()) {
                    Student.studentList.add(student);
                    studentTable.getItems().add(student);
                    Student.lastId++;
                    clearFields();
                    LoginPageController.loadDialog(stackPane, "Add Student",
                            "A student with the following profile was added to the System : \n\n\n"
                                    + "Student id : " + student.getStringId() + "\n" +
                                    "Name : " + student.getFirstName() + " " + student.getLastName() + "\n"
                                    + "Phone : " + student.getPhone());
                    // -------------- write in file ------------
                                  WriteAndReadFile.write();
                }
            }
        }

    }
    // ------------------------ check empty fields   -------------------
    private boolean checkAllField() {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || userField.getText().isEmpty()
                || phoneField.getText().isEmpty() || mailField.getText().isEmpty()) {

            LoginPageController.loadDialog(stackPane, "Register Error","Please fill all fields.");
            return false;
        }
        return true;
    }
    // ------------------------ check non duplicated user -------------------
    private boolean nonDuplicatedUser(String user) {
        for (Student student : Student.studentList) {
            if (student.getUserName().equalsIgnoreCase(user)) {
                LoginPageController.loadDialog(stackPane, "Register Error","This username has already been selected.");
                return false;
            }

        }
        return true;
    }
    // ------------------------ check phone number  -------------------
    public boolean checkPhoneField() {
        if (phoneField.getText().matches("\\d{11}") && phoneField.getText().startsWith("09")) {
            return true;
        }
        LoginPageController.loadDialog(stackPane, "Register Error","Phone must start with (09) and contains 11 digits.");

        return false;

    }
    // ------------------------ check email  -------------------
    private boolean checkMailField() {
        if (mailField.getText().contains("@") && mailField.getText().contains(".")) {
            return true;
        }
        LoginPageController.loadDialog(stackPane, "Register Error","Mail must contain (@) and (.)");

        return false;
    }
    // ------------------------ check username -------------------
    private boolean checkUserField() {
        if (userField.getText().matches("[a-zA-Z0-9]{3,12}")) {
            return true;
        }
        LoginPageController.loadDialog(stackPane, "Register Error","Username must 3 - 12 character");

        return false;
    }
    // ------------------------ clear fields -------------------
    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        userField.clear();
        mailField.clear();
        phoneField.clear();
    }
    // ------------------------ check user for edit  -------------------
    private boolean userEditable(String user) {

        if (currentUser.equals(userField.getText())) {
            return true;
        }
        for (Student student : Student.studentList) {
            if (student.getUserName().equals(user)) {
                LoginPageController.loadDialog(stackPane, "Register Error","This username has already been selected.");
                return false;
            }
        }
        errorLBL.setText("");
        return true;
    }
  // ----------------------- check first and last name ----------------
    private boolean checkNameField(){
        if (firstNameField.getText().matches("^[a-zA-Z\\s]+") && lastNameField.getText().matches("^[a-zA-Z\\s]+") ) {
            return true;
        }
        LoginPageController.loadDialog(stackPane,"Register Error"," Firstname and Lastname must contains a-zA-Z");
        return  false;
    }


}

