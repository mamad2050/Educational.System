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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sample.Controller.Login.LoginPageController;
import sample.Main;
import sample.Model.Class;
import sample.Model.Master;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.SimpleTimeZone;

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
    JFXButton openBTN;

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
    private TextField searchField;
    @FXML
    private StackPane stackPane;

    Master master;
    public static Class selectedClass;

    AnchorPane pane;


    @Override


    public void initialize(URL location, ResourceBundle resources) {

        masterIdField.setText(Integer.toString(LoginPageController.masterLoggedIn.getMasterId()));
        masterNameField.setText(LoginPageController.masterLoggedIn.getFirstName() + " " + LoginPageController
                .masterLoggedIn.getLastName());

//        errorLBL.setText("");


        for (Master find : Master.masterList) {
            if (find == LoginPageController.masterLoggedIn) {
                master = find;
            }
        }
        ObservableList<Class> observableList = FXCollections.observableArrayList();
        for (int i = 0; i < Class.classList.size(); i++) {
            if (Class.classList.get(i).getMasterObj() == master) {
                observableList.add(Class.classList.get(i));
            }
        }


        classIdColumn.setCellValueFactory(new PropertyValueFactory<>("classId"));
        classNumColumn.setCellValueFactory(new PropertyValueFactory<>("classNumber"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        lessonColumn.setCellValueFactory(new PropertyValueFactory<>("lessonName"));
        occupyColumn.setCellValueFactory(new PropertyValueFactory<>("occupy"));
        masterPhoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMasterObj().getPhone()));
        masterColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMasterObj().getFirstName()
                + " " + cellData.getValue().getMasterObj().getLastName()));


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
        editBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // ---------------- check conditions --------------
                if (checkAllField() && checkAllField()) {
                    Class.classList.get(selectedClass.getClassId() - 1).setClassNumber(Integer.parseInt(classNumField.getText()));
                    Class.classList.get(selectedClass.getClassId() - 1).setCapacity(Integer.parseInt(capacityField.getText()));
                    Class.classList.get(selectedClass.getClassId() - 1).setLessonName(lessonField.getText());
                    Class.classList.get(selectedClass.getClassId() - 1).setMasterObj(findMaster());
//
                    myClassTable.refresh();
                    LoginPageController.loadDialog(stackPane, "Edit Class", "Successful.");

                }
            }
        });

//
        myClassTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectedClass = myClassTable.getSelectionModel().getSelectedItem();
                capacityField.setText(Integer.toString(selectedClass.getCapacity()));
                lessonField.setText(selectedClass.getLessonName());
                classNumField.setText(Integer.toString(selectedClass.getClassNumber()));

            }
        });
        openBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (selectedClass == null) {
                    LoginPageController.loadDialog(stackPane, "Open Error", "Not Found.");
                }else {
                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Master/ViewCLassPage.fxml"));
                    try {
                        pane = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    showPane.getChildren().setAll(pane);
                }
            }
        });
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
        classs.setPhoto(new Image("sample/view/drawable/iconfinder_board-math-class-school_2824448.png"));
        clearFields();
//
    }


    private boolean checkAllField() {
        if (capacityField.getText().isEmpty() || classNumField.getText().isEmpty()
                || lessonField.getText().isEmpty() || masterNameField.getText().isEmpty() || masterIdField.getText().isEmpty()) {
//            errorLBL.setText("Please fill all fields.");
            LoginPageController.loadDialog(stackPane, "Add Class", "Please fill all fields.");
            return false;
        }
        return true;
    }


    private void clearFields() {
        capacityField.clear();
        lessonField.clear();
        classNumField.clear();
    }

    private boolean checkIntegerFields() {
        if (!classNumField.getText().matches("\\d{1,3}") || !capacityField.getText().matches("\\d{2}")) {
//            errorLBL.setText("Please check your inputs.");
            LoginPageController.loadDialog(stackPane, "Add Class", "Class number must between 1-999 \n"
                    + "Capacity must between 10-99");
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
                return true;
            }
        }
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



