package sample.Controller.Manager;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sample.Controller.Login.LoginPageController;
import sample.File.WriteAndReadFile;
import sample.Main;
import sample.Model.Class;
import sample.Model.Master;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class ManageClassesController implements Initializable {

    @FXML
    private AnchorPane showPane;

    @FXML
    private TableView<Class> classTable;

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
    private JFXButton openBTN;

    @FXML
    private JFXButton deleteBTN;

    @FXML
    private JFXTextField classNumField;

    @FXML
    private JFXTextField masterNameField;

    @FXML
    private JFXTextField capacityField;

    @FXML
    private JFXTextField lessonField;

    @FXML
    private JFXTextField masterIdField;

    @FXML
    private StackPane stackPane;

    AnchorPane pane;

    Class classs;

    public static Class selectedClass;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        errorLBL.setText("");
        ObservableList<Class> observableList = FXCollections.observableArrayList(Class.classList);
        classIdColumn.setCellValueFactory(new PropertyValueFactory<>("classId"));
        classNumColumn.setCellValueFactory(new PropertyValueFactory<>("classNumber"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        lessonColumn.setCellValueFactory(new PropertyValueFactory<>("lessonName"));

        occupyColumn.setCellValueFactory(new PropertyValueFactory<>("occupy"));
        masterPhoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().
                getMasterObj().getPhone()));
        masterColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMasterObj()
                .getFirstName() + " " + cellData.getValue().getMasterObj().getLastName()));


        classTable.setItems(observableList);
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
                classs = new Class(Integer.parseInt(capacityField.getText()), Integer.parseInt(classNumField.getText())
                        , lessonField.getText(), findMaster());
                addClassToTable(classs);
                try {
                    WriteAndReadFile.write();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        classTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectedClass = classTable.getSelectionModel().getSelectedItem();
//                capacityField.setText(Integer.toString(selectedClass.getCapacity()));
//                lessonField.setText(selectedClass.getLessonName());
//                masterNameField.setText(selectedClass.getMaster());
//                classNumField.setText(Integer.toString(selectedClass.getClassNumber()));

                openBTN.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Manager/ViewCLassPage.fxml"));
                        try {
                            pane = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        showPane.getChildren().setAll(pane);
                    }
                });
            }
        });


        deleteBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Class deleteClass = classTable.getSelectionModel().getSelectedItem();
                Class.classList.remove(deleteClass);
                classTable.getItems().remove(deleteClass);
                Class.classList.remove(deleteClass);
            }
        });

    }

    private void addClassToTable(Class classs) {

        Class.classList.add(classs);
        classTable.getItems().add(classs);
        Class.lastId++;
        clearFields();


    }

    private boolean checkAllField() {
        if (capacityField.getText().isEmpty() || classNumField.getText().isEmpty()
                || lessonField.getText().isEmpty() || masterNameField.getText().isEmpty() || masterIdField.getText().isEmpty()) {

            LoginPageController.loadDialog(stackPane,"Add Class", "Please fill all fields.");

            return false;
        }
        return true;
    }


    private void clearFields() {
        capacityField.clear();
        lessonField.clear();
        masterNameField.clear();
        classNumField.clear();
        masterIdField.clear();

    }

    private boolean checkIntegerFields() {
        if (!classNumField.getText().matches("\\d{1,3}") || !capacityField.getText().matches("\\d{2}")) {

            LoginPageController.loadDialog(stackPane,"Add Class", "Please check your inputs.");
            return false;
        }

        return true;
    }

    //
    private boolean checkMaster() {

        if (masterIdField.getText().equals("")) {
            return false;
        }

        for (Master master : Master.masterList) {
            if (Integer.parseInt(masterIdField.getText()) == master.getMasterId()) {
                masterNameField.setText(master.getFirstName() + " " + master.getLastName());
                return true;

            }
        }

        LoginPageController.loadDialog(stackPane,"Add Class", "Master Not Found");
        return false;
    }


    private Master findMaster() {

        for (Master master : Master.masterList) {
            if (Integer.parseInt(masterIdField.getText()) == master.getMasterId()) {
                master.getMyClasses().add(classs);
                return master;
            }
        }
        return null;
    }
}