package sample.Model;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Lesson {

    private String imageUrl;
    private String lesson;
    private String master;
    private Pane root;


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public Lesson(String imageUrl, String lesson, String master) {
        this.imageUrl = imageUrl;
        this.lesson = lesson;
        this.master = master;
    }

    public Pane getRoot() {
        return root;
    }

    private void setDesign() {
        root = new Pane();
        Image image = new Image(imageUrl);
        ImageView imageView = new ImageView(image);
        Label masterLBL = new Label(master);
        Label lessonLBL = new Label(lesson);

        root = new Pane();
        root.getChildren().addAll(imageView,masterLBL,lessonLBL);

    }
}
