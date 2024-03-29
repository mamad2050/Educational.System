package sample.File;

import sample.Model.Class;
import sample.Model.Master;
import sample.Model.Student;
import java.io.*;

public class WriteAndReadFile {
    public static void write() throws IOException {

     //student

        FileWriter fileWriter = new FileWriter("File/Student.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(String.valueOf(Student.lastId));
        bufferedWriter.newLine();

        for (int i = 0; i < Student.studentList.size(); i++) {
            Student student = Student.studentList.get(i);
            String forSave = student.getStudentId() + " " + student.getFirstName() + " " + student.getLastName() + " " +
                    student.getUserName() + " " + student.getPassword() + " " + student.getEmail() + " " + student.getPhone()
                   ;
            bufferedWriter.write(forSave);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
        fileWriter.close();

// master


         fileWriter = new FileWriter("File/Master.txt");
         bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(String.valueOf(Master.lastId));
        bufferedWriter.newLine();

        for (int i = 0; i < Master.masterList.size(); i++) {
            Master master = Master.masterList.get(i);
            String forSave = master.getMasterId() + " " + master.getFirstName() + " " + master.getLastName() + " " +
                    master.getUserName() + " " + master.getPassword() + " " + master.getEmail() + " " + master.getPhone();
            bufferedWriter.write(forSave);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
        fileWriter.close();



 //class

        fileWriter = new FileWriter("File/Class.txt");
        bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(String.valueOf(Class.lastId));
        bufferedWriter.newLine();

        for (int i = 0; i < Class.classList.size(); i++) {
            Class classs = Class.classList.get(i);
            String forSave = classs.getCapacity()+ " " + classs.getOccupy() + " " +
                    classs.getClassNumber() + " " + classs.getLessonName() + " " ;

            forSave += classs.getMasterObj().getFirstName() + " " + classs.getMasterObj().getLastName() +" "
           + classs.getMasterObj().getUserName() + " " + classs.getMasterObj().getPassword()+" "
                    + classs.getMasterObj().getEmail() +" " + classs.getMasterObj().getPhone()
                     ;

            bufferedWriter.write(forSave);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
        fileWriter.close();

    }

    public static void read() throws IOException {

    // student

        FileReader fileReader = new FileReader("File/Student.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        Student.lastId = Integer.parseInt(bufferedReader.readLine());
        Student.lastId = 1;
        String str;
        while ((str = bufferedReader.readLine()) != null) {

            String[] tmp = str.split(" ");
            Student student = new Student(tmp[1], tmp[2], tmp[3], tmp[4], tmp[5], tmp[6]);
//            student.setPhoto(new Image(tmp[7]));

            Student.studentList.add(student);
            Student.lastId++;
        }
        bufferedReader.close();
        fileReader.close();

// master
         fileReader = new FileReader("File/Master.txt");
         bufferedReader = new BufferedReader(fileReader);

        Master.lastId = Integer.parseInt(bufferedReader.readLine());
        Master.lastId = 1;
        while ((str = bufferedReader.readLine()) != null) {

            String[] tmp = str.split(" ");
            Master master = new Master(tmp[1], tmp[2], tmp[3], tmp[4], tmp[5], tmp[6]);

           Master.masterList.add(master);
            Master.lastId++;
        }
        bufferedReader.close();
        fileReader.close();








        //class
//
//        fileReader = new FileReader("File/Class.txt");
//        bufferedReader = new BufferedReader(fileReader);
//
//        Class.lastId = Integer.parseInt(bufferedReader.readLine());
//        Class.lastId = 1;
//        while ((str = bufferedReader.readLine()) != null) {
//            String[] tmp = str.split(" ");
//            Master master = new Master(tmp[4],tmp[5],tmp[6],tmp[7],tmp[8],tmp[9]);
//            Class classs = new Class(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[2]), tmp[3], master, Integer.parseInt(tmp[1]) );
//
//            Class.classList.add(classs);
//            for (Master mas : Master.masterList) {
//                if (master == mas) {
//                    mas.getMyClasses().add(classs);
//                }
//            }
//
//            Class.lastId++;
//        }
//        bufferedReader.close();
//        fileReader.close();
//


    }
}