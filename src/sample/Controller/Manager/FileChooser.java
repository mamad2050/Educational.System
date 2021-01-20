package sample.Controller.Manager;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.Controller.Login.LoginPageController;
import sample.Model.Manager;
import sample.Model.Student;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileChooser {

    private javafx.stage.FileChooser fileChooser;
    private File filePath;

    private Manager manager;
    private Student student;

    public FileChooser(Manager manager) {
        this.manager = manager;
    }

    public FileChooser(Student student){
        this.student=student;
    }

    public void chooseImageButtonPush(ActionEvent event, ImageView imageField) {
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

            if (manager  != null) {
                manager.setPhoto(image);
                imageField.setImage(manager.getPhoto());
            }else if (student != null) {
                student.setPhoto(image);
                imageField.setImage(student.getPhoto());
            }


        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }


}
