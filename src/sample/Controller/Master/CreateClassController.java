package sample.Controller.Master;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import sample.Controller.Login.LoginPageController;
import sample.Main;
import sample.Model.Class;
import sample.Model.Master;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateClassController implements Initializable {

    @FXML
    private AnchorPane showPane;

    @FXML
    private TableView<Class> myClassTable;

    @FXML
    private TableColumn<Class, Integer> classIdColumn;

    @FXML
    private TableColumn<Class, Integer> classNumColumn;

    @FXML
    private TableColumn<Class, Integer> capacityColumn;

    @FXML
    private TableColumn<Class, String> lessonColumn;
    @FXML
    private TableColumn<Class, String> masterPhoneColumn;

    @FXML
    private TableColumn<Class, String> masterColumn;

    @FXML
    private TableColumn<Class, Integer> occupyColumn;

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
    private JFXTextField capacityField;

    @FXML
    private JFXTextField lessonField;

    @FXML
    private Label errorLBL;

    @FXML
    private TextField searchField;

    Master master;

    @Override


    public void initialize(URL location, ResourceBundle resources) {

        masterIdField.setText(Integer.toString(LoginPageController.masterLoggedIn.getMasterId()));
        masterNameField.setText(LoginPageController.masterLoggedIn.getFirstName() + " " + LoginPageController
                .masterLoggedIn.getLastName());





        errorLBL.setText("");



        for (Master find : Master.masterList) {
            if (find == LoginPageController.masterLoggedIn) {
                master = find;
            }
        }
        ObservableList<Class> observableList = FXCollections.observableArrayList();
        for (int i = 0; i < Class.classList.size() ; i++) {
            if (Class.classList.get(i) .getMasterObj() == master ) {
               observableList.add(Class.classList.get(i));
            }
        }




        classIdColumn.setCellValueFactory(new PropertyValueFactory<>("classId"));
        classNumColumn.setCellValueFactory(new PropertyValueFactory<>("classNumber"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        lessonColumn.setCellValueFactory(new PropertyValueFactory<>("lessonName"));
        occupyColumn.setCellValueFactory(new PropertyValueFactory<>("occupy"));
        masterPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));


        myClassTable.setItems(observableList);


        masterIdField.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!checkMaster()) {
                    masterNameField.setText("");
                }
            }
        });
        addBTN.setOnAction(event -> {
            if (checkAllField() && checkIntegerFields()) {

                addClassToTable(new Class(Integer.parseInt(capacityField.getText()), Integer.parseInt(classNumField.getText())
                        , lessonField.getText(), findMaster()));


            }

        });

//
//        myClassTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                selectedClass = classTable.getSelectionModel().getSelectedItem();
////                capacityField.setText(Integer.toString(selectedClass.getCapacity()));
////                lessonField.setText(selectedClass.getLessonName());
////                masterNameField.setText(selectedClass.getMaster());
////                classNumField.setText(Integer.toString(selectedClass.getClassNumber()));
//
//
//                FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Manager/ViewCLassPage.fxml"));
//                try {
//                    pane = loader.load();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                showPane.getChildren().setAll(pane);
//            }
//        });


        deleteBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Class deleteClass = myClassTable.getSelectionModel().getSelectedItem();
                Class.classList.remove(deleteClass);
                myClassTable.getItems().remove(deleteClass);
                Class.classList.remove(deleteClass);
            }
        });

    }

    private void addClassToTable(Class classs) {

        Class.classList.add(classs);
        myClassTable.getItems().add(classs);
        Class.lastId++;
        clearFields();
        errorLBL.setText("");

    }

    //
    private boolean checkAllField() {
        if (capacityField.getText().isEmpty() || classNumField.getText().isEmpty()
                || lessonField.getText().isEmpty() || masterNameField.getText().isEmpty() || masterIdField.getText().isEmpty()) {
            errorLBL.setText("Please fill all fields.");
            return false;
        }
        return true;
    }


    private void clearFields() {
        capacityField.clear();
        lessonField.clear();
//        masterNameField.clear();
        classNumField.clear();
//        masterIdField.clear();
    }

    private boolean checkIntegerFields() {
        if (!classNumField.getText().matches("\\d{1,3}") || !capacityField.getText().matches("\\d{2}")) {
            errorLBL.setText("Please check your inputs.");
            return false;
        }

        return true;
    }


    private boolean checkMaster() {

        if (masterIdField.getText().equals("")) {
            return false;
        }

        for (Master master : Master.masterList) {
            if (Integer.parseInt(masterIdField.getText()) == master.getMasterId()) {
                masterNameField.setText(master.getFirstName() + " " + master.getLastName());

                errorLBL.setText("");
                return true;
            }
        }
        errorLBL.setTextFill(Color.RED);
        errorLBL.setText("Master Not Found");
        return false;
    }


    private Master findMaster() {

        for (Master master : Master.masterList) {
            if (Integer.parseInt(masterIdField.getText()) == master.getMasterId()) {
                return master;
            }
        }
        return null;
    }
}



