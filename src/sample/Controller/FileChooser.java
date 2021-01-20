package sample.Controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.Controller.Login.LoginPageController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileChooser {

    private javafx.stage.FileChooser fileChooser;
    private File filePath;

    public void chooseImageButtonPush(ActionEvent event, ImageView imageField ) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle("open");
        this.filePath = fileChooser.showOpenDialog(stage);

        String userDirectoryString = System.getProperty("user.home");

        File userDirectory;

//        if (!userDirectory.canRead()) {
        userDirectory = new File("c:/");
        fileChooser.setInitialDirectory(userDirectory);
        try {
            BufferedImage bufferedImage = ImageIO.read(filePath);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            LoginPageController.managerLoggedIn.setPhoto(image);
            imageField.setImage(LoginPageController.managerLoggedIn.getPhoto());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

//    }

    }
}
