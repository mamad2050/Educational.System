package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controller.Login.LoginPageController;
import sample.File.WriteAndReadFile;
import sample.Model.Comment;
import sample.Model.Lesson;

import java.util.ArrayList;
import java.util.Collection;

public class Main extends Application {

   public static ArrayList<Comment> comments = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {

        WriteAndReadFile.read();


        fillInfo();


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("View/Login/LoginPage.fxml"));
        loader.load();
        LoginPageController controller = loader.getController();
        controller.setStage(primaryStage);
        primaryStage.setScene(new Scene(loader.getRoot()));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }


    private void fillInfo() {

    }

}
