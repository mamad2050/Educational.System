package sample.Model;

import java.util.ArrayList;

public class Class {

    public static int lastId = 1;
    private int classId;
    private int capacity;
    private int classNumber;
    private String master;
    private String lessonName;

    public static ArrayList<Class> classList = new ArrayList<>();
    private ArrayList<Student> studentsList = new ArrayList<>();

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

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
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

    public Class(int capacity, int classNumber, String master, String lessonName) {

        this.capacity = capacity;
        this.master = master;
        this.lessonName = lessonName;
        this.classNumber = classNumber;
        setClassId();
    }
}
