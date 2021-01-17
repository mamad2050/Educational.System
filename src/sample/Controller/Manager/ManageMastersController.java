package sample.Controller.Manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.collections.ImmutableObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Main;
import sample.Model.Master;
import sample.Model.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ManageMastersController implements Initializable {


    @FXML
    private AnchorPane showPane;

    @FXML
    private TableView<Master> masterTable;

    @FXML
    private JFXButton deleteBTN;

    @FXML
    private JFXButton editBTN;

    @FXML
    private JFXButton addBTN;

    @FXML
    private TableColumn<Master, Integer> masterIdColumn;

    @FXML
    private TableColumn<Master, String> firstnameColumn;

    @FXML
    private TableColumn<Master, String> lastnameColumn;

    @FXML
    private TableColumn<Master, String> usernameColumn;

    @FXML
    private TableColumn<Master, String> phoneColumn;

    @FXML
    private TableColumn<Master, String> emailColumn;
    @FXML
    private JFXTextField firstNameField;

    @FXML
    private JFXTextField lastNameField;

    @FXML
    private JFXTextField userField;

    @FXML
    private JFXTextField phoneField;

    @FXML
    private JFXTextField mailField;

    @FXML
    private Label errorLBL;

   @FXML
   private TextField searchField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Master> observableList = FXCollections.observableArrayList(Master.masterList);
        masterIdColumn.setCellValueFactory(new PropertyValueFactory<>("masterId"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        masterTable.setItems(observableList);

        addBTN.setOnAction(event -> {

            masterCheckConditions(new Master(firstNameField.getText(), lastNameField.getText(), userField.getText()
                    , "0000", mailField.getText(), phoneField.getText()));

        });
        masterTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Master master = masterTable.getSelectionModel().getSelectedItem();
                firstNameField.setText(master.getFirstName());
                lastNameField.setText(master.getLastName());
                userField.setText(master.getUserName());
                phoneField.setText(master.getPhone());
                mailField.setText(master.getEmail());
            }
        });

        deleteBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Master deleteMaster = masterTable.getSelectionModel().getSelectedItem();
                Student.studentList.remove(deleteMaster);
                masterTable.getItems().remove(deleteMaster);
                Student.studentList.remove(deleteMaster);
            }
        });

        // search box
        FilteredList<Master> filteredList = new FilteredList<>(observableList, e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super Master>) master -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (Integer.toString(master.getMasterId()).contains(newValue)) {
                        return true;
                    } else if (master.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (master.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Master> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(masterTable.comparatorProperty());
            masterTable.setItems(sortedList);
        });
        // end search

    }

    private void masterCheckConditions(Master master) {
        if (checkAllField()) {
            if (nonDuplicatedUser(master.getUserName())) {
                if (checkPhoneField() && checkMailField() && checkUserField()) {
                    Master.masterList.add(master);
                    masterTable.getItems().add(master);
                    Master.lastId++;
                    clearFields();
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

    private boolean nonDuplicatedUser(String username) {
        for (Master master : Master.masterList) {
            if (master.getUserName().equals(username)) {
                errorLBL.setText("This username has already been selected.");
                return false;
            }

        }
        return true;
    }

    private boolean checkPhoneField() {
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
}




