package sample.Model;


import javafx.scene.image.Image;

import java.io.IOException;
import java.util.ArrayList;

public class Student extends Person {


    private int studentId;

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public static int lastId = 1;
    public static ArrayList<Student> studentList = new ArrayList<>();

    private Image photo;

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public Student(String firstName, String lastName, String userName, String password, String email, String phone) throws IOException {
        super(firstName, lastName, userName, password, email, phone);
        setStudentId();
        this.photo = new Image("sample/view/drawable/teacher.png");
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStringId() {
        return Integer.toString(studentId);
    }

    public void setStudentId() {
        studentId = lastId;
    }
}
