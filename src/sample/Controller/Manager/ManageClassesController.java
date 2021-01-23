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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import sample.Controller.Login.LoginPageController;
import sample.File.WriteAndReadFile;
import sample.Main;
import sample.Model.Class;
import sample.Model.Master;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ManageClassesController implements Initializable {
// --------------------------------    components  ---------------------------------
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
    private  JFXButton editBTN;
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
    @FXML
    private ImageView imageField;
    @FXML
    private ImageView addimg;
    @FXML
    private JFXButton clearBTN;
// --------------------------------   end   ---------------------------------

    AnchorPane pane;
    Class classs;

    public static Class selectedClass = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
// --------------------------------   table properties   ---------------------------------

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


        // --------------------------------   set master for add class   ---------------------------------

        masterIdField.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!checkMaster()) {
                    masterNameField.setText("");
                }
            }
        });
        // -------------------------------- add class     ---------------------------------

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
      // --------------------------------   select class from table   ---------------------------------


        classTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addimg.setVisible(false);
                selectedClass = classTable.getSelectionModel().getSelectedItem();
                capacityField.setText(Integer.toString(selectedClass.getCapacity()));
                lessonField.setText(selectedClass.getLessonName());
                masterNameField.setText(selectedClass.getMasterObj().getFirstName() + " " + selectedClass.getMasterObj().getLastName());
                classNumField.setText(Integer.toString(selectedClass.getClassNumber()));
                masterIdField.setText(Integer.toString(selectedClass.getMasterObj().getMasterId()));
                imageField.setImage(selectedClass.getPhoto());
            }
        });
// --------------------------------  clear fields    ---------------------------------

        clearBTN.setOnAction(e -> {
            addimg.setVisible(true);
            imageField.setImage(new Image("sample/view/drawable/iconfinder_board-math-class-school_2824448.png"));
            clearFields();
        });

// --------------------------------  open selected class    ---------------------------------

        openBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (selectedClass == null) {
                    LoginPageController.loadDialog(stackPane, "Open Error", "Not Found.");
                } else {
                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Manager/ViewCLassPage.fxml"));
                    try {
                        pane = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    showPane.getChildren().setAll(pane);
                }
            }
        });


        // --------------- edit master by manager ----------
        editBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // ---------------- check conditions --------------
                if (checkAllField() && checkAllField() ) {
                    Class.classList.get(selectedClass.getClassId() - 1).setClassNumber(Integer.parseInt(classNumField.getText()));
                    Class.classList.get(selectedClass.getClassId() - 1).setCapacity(Integer.parseInt(capacityField.getText()));
                    Class.classList.get(selectedClass.getClassId() - 1).setLessonName(lessonField.getText());
                    Class.classList.get(selectedClass.getClassId() - 1).setMasterObj(findMaster());
//
                    classTable.refresh();
                    LoginPageController.loadDialog(stackPane,"Edit Class","Successful.");

                }
            }
        });
// --------------------------------  delete class    ---------------------------------

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


// --------------------------------  add class to system    ---------------------------------

    private void addClassToTable(Class classs) {

        Class.classList.add(classs);
        classTable.getItems().add(classs);
        Class.lastId++;
        classs.setPhoto(new Image("sample/view/drawable/iconfinder_board-math-class-school_2824448.png"));
        clearFields();
    }
// --------------------------------   check empty fields   ---------------------------------

    private boolean checkAllField() {
        if (capacityField.getText().isEmpty() || classNumField.getText().isEmpty()
                || lessonField.getText().isEmpty() || masterNameField.getText().isEmpty() || masterIdField.getText().isEmpty()) {

            LoginPageController.loadDialog(stackPane, "Add Class", "Please fill all fields.");

            return false;
        }
        return true;
    }

// --------------------------------   clear fields   ---------------------------------

    private void clearFields() {
        capacityField.clear();
        lessonField.clear();
        masterNameField.clear();
        classNumField.clear();
        masterIdField.clear();

    }
// --------------------------------   check class number field and capacity field   ---------------------------------

    private boolean checkIntegerFields() {
        if (!classNumField.getText().matches("\\d{1,3}") || !capacityField.getText().matches("\\d{2}")) {

            LoginPageController.loadDialog(stackPane, "Add Class", "Class number must between 1-999 \n"
            + "Class number must between 10-99");
            return false;
        }

        return true;
    }

// --------------------------------   check available master   ---------------------------------

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
        LoginPageController.loadDialog(stackPane, "Add Class", "Master Not Found");
        return false;
    }

    // --------------------------------  find master from list    ---------------------------------

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