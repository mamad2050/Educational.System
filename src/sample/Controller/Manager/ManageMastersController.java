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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Controller.Login.LoginPageController;
import sample.File.WriteAndReadFile;
import sample.Main;
import sample.Model.Master;
import sample.Model.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ManageMastersController implements Initializable {

// ---------------------------------- components -------------------
    @FXML
    private ImageView imageField;
    @FXML
    private ImageView addimg;
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
    private StackPane stackPane;
    @FXML
    private TextField searchField;
    @FXML
    private JFXButton clearBTN;

    Master selectMaster;

    String currentUser;
    // ------------------------------------- end ------------------------------

    @Override
    public void initialize(URL location, ResourceBundle resources) {
   // ------------------------------------ table properties ------------------------------
        ObservableList<Master> observableList = FXCollections.observableArrayList(Master.masterList);
        masterIdColumn.setCellValueFactory(new PropertyValueFactory<>("masterId"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        masterTable.setItems(observableList);


    // ------------------------------------ set on actions ------------------------------------
        addBTN.setOnAction(event -> {
            try {
                // ----------------- add master ------------
                masterCheckConditions(new Master(firstNameField.getText(), lastNameField.getText(), userField.getText()
                        , "0000", mailField.getText(), phoneField.getText()));

            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        // --------------------- clear fields --------------
        clearBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearFields();
                addimg.setVisible(true);
                imageField.setImage(new Image("sample/view/drawable/headmaster.png"));
            }
        });
        // ----------------- select master from table --------------
        masterTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectMaster = masterTable.getSelectionModel().getSelectedItem();
                addimg.setVisible(false);
                firstNameField.setText(selectMaster.getFirstName());
                lastNameField.setText(selectMaster.getLastName());
                userField.setText(selectMaster.getUserName());
                phoneField.setText(selectMaster.getPhone());
                mailField.setText(selectMaster.getEmail());
                currentUser = userField.getText();
                imageField.setImage(selectMaster.getPhoto());
            }
        });

       // --------------- edit master by manager ----------
        editBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
       // ---------------- check conditions --------------
                if (userEditable(userField.getText()) && checkAllField() && checkUserField() && checkMailField() &&
                        checkPhoneField() && checkNameField() ) {
                    Master.masterList.get(selectMaster.getMasterId() - 1).setFirstName(firstNameField.getText());
                    Master.masterList.get(selectMaster.getMasterId() - 1).setLastName(lastNameField.getText());
                    Master.masterList.get(selectMaster.getMasterId() - 1).setUserName(userField.getText());
                    Master.masterList.get(selectMaster.getMasterId() - 1).setPhone(phoneField.getText());
                    Master.masterList.get(selectMaster.getMasterId() - 1).setEmail(mailField.getText());
                    masterTable.refresh();

                }
            }
        });

// --------------------------- delete master from system ----------------
        deleteBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // -------------------------- delete select master from system---------------
                Master deleteMaster = masterTable.getSelectionModel().getSelectedItem();
                Master.masterList.remove(deleteMaster);
                masterTable.getItems().remove(deleteMaster);
                // ---------  write in file  ---------
                try {
                    WriteAndReadFile.write();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //-------------------------------- search box for masters -----------------------
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
        //----------------------------------  end search box ---------------------------
    }
  // -------------------- check fields for add master --------------------
    private void masterCheckConditions(Master master) throws IOException {

        if (checkAllField()) {
            if (nonDuplicatedUser(master.getUserName())) {
                if (checkPhoneField() && checkMailField() && checkUserField() && checkNameField()) {
                    Master.masterList.add(master);
                    WriteAndReadFile.write();
                    masterTable.getItems().add(master);
                    Master.lastId++;
                    clearFields();
                }
            }
        }
    }
    // --------------------------------- check empty fields ----------------

    private boolean checkAllField() {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || userField.getText().isEmpty()
                || phoneField.getText().isEmpty() || mailField.getText().isEmpty()) {

            LoginPageController.loadDialog(stackPane,"Register Error","Please fill all fields.");

            return false;
        }
        return true;
    }
 // --------------------- check nonDuplicated user ---------------------
    private boolean nonDuplicatedUser(String username) {
        for (Master master : Master.masterList) {
            if (master.getUserName().equals(username)) {
                LoginPageController.loadDialog(stackPane,"Register Error","This username has already been selected. ");
                return false;
            }
        }
        return true;
    }
// ------------------ check phone number ---------------------
    private boolean checkPhoneField() {
        if (phoneField.getText().matches("\\d{11}") && phoneField.getText().startsWith("09")) {
            return true;
        }
        LoginPageController.loadDialog(stackPane,"Register Error","Phone must start with (09) and contains 11 digits. ");
        return false;
    }
    // ------------------ check E Mail  ---------------------
    private boolean checkMailField() {
        if (mailField.getText().matches("^(.+)@(.+)$")) {
            return true;
        }
        LoginPageController.loadDialog(stackPane,"Register Error","Mail must contain (@) and (.com)");

        return false;
    }
    // ------------------ check username ---------------------
    private boolean checkUserField() {
        if (userField.getText().matches("[a-zA-Z0-9]{3,12}")) {
            return true;
        }
        LoginPageController.loadDialog(stackPane,"Register Error","Username must 3 - 12 character");

        return false;
    }
// ------------------ clear fields -----------------------
    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        userField.clear();
        mailField.clear();
        phoneField.clear();
    }
// -------------------- check user for edit -----------------------
    private boolean userEditable(String user) {

        if (currentUser.equals(userField.getText())) {
            return true;
        }
        for (Master master : Master.masterList) {
            if (master.getUserName().equals(user)) {
                LoginPageController.loadDialog(stackPane,"Register Error"," This username has already been selected.");

                return false;
            }

        }

        return true;
    }
 // -------------------------- check first and last name -------------------
    private boolean checkNameField(){
        if (firstNameField.getText().matches("^[a-zA-Z\\s]+") && lastNameField.getText().matches("^[a-zA-Z\\s]+") ) {
            return true;
        }
        LoginPageController.loadDialog(stackPane,"Register Error"," Firstname and Lastname must contains a-zA-Z");
        return  false;
    }
}




