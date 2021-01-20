package sample.Model;

import javafx.scene.image.Image;


import java.util.ArrayList;

public class Manager extends Person {

    private Image photo;

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public static ArrayList<Manager> managerList = new ArrayList<>();
    private int id = 1;

    public Manager(String firstName, String lastName, String userName, String password, String email, String phone) {
        super(firstName, lastName, userName, password, email, phone);

        this.photo = new Image("sample/View/Drawable/manager.png");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
