package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controller.Login.LoginPageController;
import sample.File.WriteAndReadFile;
import sample.Model.Lesson;

import java.util.ArrayList;

public class Main extends Application {

   public static ArrayList<Lesson> lessons = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {

        WriteAndReadFile.read();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("View/Login/LoginPage.fxml"));
        loader.load();
        LoginPageController controller = loader.getController();
        controller.setStage(primaryStage);
        primaryStage.setScene(new Scene(loader.getRoot()));
        primaryStage.setResizable(false);
        primaryStage.show();

        lessons.add(new Lesson("sample/view/drawable/headmaster.png", "math", "ali abbasi"));
        lessons.add(new Lesson("sample/view/drawable/headmaster.png", "chem", "rez adibian"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
