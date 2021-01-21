package sample.Controller.Login;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.Main;
import sample.Model.Student;
import sample.SendMail;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable {

    private Stage stage;

    private Student student;

    public Stage getStage() {
        return stage;
    }


    @FXML
    private StackPane stackpane;

    @FXML
    private Button sendBTN;

    @FXML
    private Button backBTN;


    @FXML
    private JFXTextField verifField;

    @FXML
    private JFXTextField mailField;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        sendBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (Student student : Student.studentList) {
                    if (mailField.getText().equals(student.getEmail())) {

                        SendMail sendMail = new SendMail();
                        try {
                            sendMail.sendEmailTo(student.getEmail());
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                        student.setPassword(SendMail.sb.toString());
                        LoginPageController.loadDialog(stackpane, "Reset Password Request ", "Your New Password send to you mail.");
                    }
                }
            }
        });


        backBTN.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/Login/LoginPage.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            LoginPageController controller = loader.getController();
            controller.setStage(stage);
            stage.setScene(new Scene(loader.getRoot()));
            stage.show();
        });
    }

}
