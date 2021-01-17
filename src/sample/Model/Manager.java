package sample.Model;

import java.util.ArrayList;

public class Manager extends Person {
    public static ArrayList<Manager> managerList = new ArrayList<>();
    private int id = 1;

    public Manager(String firstName, String lastName, String userName, String password, String email, String phone) {
        super(firstName, lastName, userName, password, email, phone);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
