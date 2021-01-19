package sample.Model;

import java.util.ArrayList;

public class Master extends Person {

    private int masterId;




    public static int lastId = 1;
    public static ArrayList<Master> masterList = new ArrayList<>();
    public Master(String firstName, String lastName, String userName, String password, String email, String phone) {
        super(firstName, lastName, userName, password, email, phone);
       setMasterId();
    }

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId() {
        this.masterId = lastId;
    }
}
