package sample.Model;

import java.util.ArrayList;

public class Class {

    public static int lastId = 1;
    private int classId;
    private int capacity;
    private int classNumber;
    private String master;
    private String lessonName;
    private Master masterObj;

    private String phone;

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

    public void setOccupy(int occupy) {
        this.occupy = occupy;
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

    public String getMasterName() {
        return master;
    }

    public void setMasterName(String master) {
        this.master = master;
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

    public Class(int capacity, int classNumber, String master, String lessonName, Master masterObj) {

        this.capacity = capacity;
        this.master = master;
        this.lessonName = lessonName;
        this.classNumber = classNumber;
        this.masterObj = masterObj;
        this.phone = masterObj.getPhone();
        setClassId();
    }

}
