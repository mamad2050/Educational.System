package sample.Model;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Comment {


    private String username;
//    private String imageUrl;
    private String text;


    private Pane root;
    private ImageView imageView;
    private Label label;
    private Text comment;
    private TextFlow textFlow;
    private Image image;


    public Comment(String username, Image image, String text) {
        this.username = username;
        this.image = image;
        this.text = text;
        setDesign();
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }
//
//    public String getUrl() {
//        return imageUrl;
//    }
//
//    public void setUrl(String url) {
//        this.imageUrl = url;
//    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Pane getRoot() {
        return root;
    }

    private void setDesign() {
        root = new Pane();
        root.setMinHeight(75);
        root.prefHeight(890);


//        Image image = new Image(imageUrl);
        imageView = new ImageView(image);
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        imageView.setLayoutX(12);
        imageView.setLayoutY(10);



        label = new Label(username);
        label.setLayoutX(98);
        label.setLayoutY(18);


        comment = new Text(text);
        textFlow = new TextFlow(comment);
        textFlow.setLayoutX(94);
        textFlow.setLayoutY(34);

        root.getChildren().addAll(imageView,label,textFlow);

    }
}
