package sample.Model;

import java.util.ArrayList;

public class Master extends Person {

    private int masterId;
    private ArrayList<Class> myClasses = new ArrayList<>();

    public ArrayList<Class> getMyClasses() {
        return myClasses;
    }

    public void setMyClasses(ArrayList<Class> myClasses) {
        this.myClasses = myClasses;
    }

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
