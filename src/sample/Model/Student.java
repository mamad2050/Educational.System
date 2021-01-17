package sample.Model;

import sample.File.WriteAndReadFile;

import java.io.IOException;
import java.util.ArrayList;

public class Student extends Person {


    private int studentId;

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public static int lastId = 1;
    public static ArrayList<Student> studentList = new ArrayList<>();


    public Student(String firstName, String lastName, String userName, String password, String email, String phone) throws IOException {
        super(firstName, lastName, userName, password, email, phone);
        setStudentId();

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
