package sample.Model;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.ArrayList;

public class Class {

    public static int lastId = 1;

    private int classId;
    private int capacity;
    private int classNumber;
    private String lessonName;
    private Master masterObj;
    private String phone;
    private Image photo;


    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public static ArrayList<Class> classList = new ArrayList<>();
    private ArrayList<Student> studentsList = new ArrayList<>();
    private int occupy = 0;

    public Master getMasterObj() {
        return masterObj;
    }

    public void setMasterObj(Master masterObj) {
        this.masterObj = masterObj;
    }

    public int getOccupy() {
        return occupy;
    }

    public void setOccupy(String s) {

        if (s.equals("+")) {
            occupy++;
        } else if (s.equals("-")) {
            occupy--;
        } else if (s.equals("0")) {
            occupy = 0;
        }

    }

    public int getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId() {
        this.classId = lastId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }


    public ArrayList<Student> getStudentsList() {
        return studentsList;
    }

    public void setStudentsList(ArrayList<Student> studentsList) {
        this.studentsList = studentsList;
    }

    public Class(int capacity, int classNumber, String lessonName, Master masterObj) {

        this.capacity = capacity;

        this.lessonName = lessonName;
        this.classNumber = classNumber;
        this.masterObj = masterObj;
        setClassId();
    }

    public Class(int capacity, int classNumber, String lessonName, Master masterObj, int occupy) {

        this.capacity = capacity;

        this.lessonName = lessonName;
        this.classNumber = classNumber;
        this.masterObj = masterObj;
        this.photo = new Image("sample/view/drawable/iconfinder_board-math-class-school_2824448.png");
        this.occupy =occupy;
        setClassId();
    }

}
