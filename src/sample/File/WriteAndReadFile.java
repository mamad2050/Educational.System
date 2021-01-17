package sample.File;

import sample.Model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class WriteAndReadFile {
    public static void write() throws IOException {

        FileWriter fileWriter = new FileWriter("File/Student.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(String.valueOf(Student.lastId));
        bufferedWriter.newLine();

        for (int i = 0; i < Student.studentList.size(); i++) {
            Student student = Student.studentList.get(i);
            String forSave = student.getStudentId() + " " + student.getFirstName() + " " + student.getLastName() + " " +
                    student.getUserName() + " " + student.getPassword() + " " + student.getEmail() + " " + student.getPhone();
            bufferedWriter.write(forSave);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
        fileWriter.close();
    }


    public static void read() throws IOException {

        FileReader fileReader = new FileReader("File/Student.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        Student.lastId = Integer.parseInt(bufferedReader.readLine());
        Student.lastId = 1;
        String str;
        while ((str = bufferedReader.readLine()) != null) {

            String[] tmp = str.split(" ");
            Student student = new Student(tmp[1], tmp[2], tmp[3], tmp[4], tmp[5], tmp[6]);

            Student.studentList.add(student);
            Student.lastId++;
        }
        bufferedReader.close();
        fileReader.close();
    }
}