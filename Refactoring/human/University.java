package com.javarush.task.task29.task2909.human;

import java.util.ArrayList;
import java.util.List;

public class University {
    private List<Student> students = new ArrayList<>();
    private String name;
    private int age;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public University(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Student getStudentWithMaxAverageGrade() {
        double max = 0;
        Student student = null;
        for (Student d: students){
            if (d.getAverageGrade() > max){
                max = d.getAverageGrade();
                student = d;
            }
        }
        return student;
    }

    public Student getStudentWithAverageGrade(double averageGrade) {
        for (Student d: students){
            if (d.getAverageGrade() == averageGrade)
                return d;
        }
        return null;
    }

    public Student getStudentWithMinAverageGrade() {
        double min = Double.MAX_VALUE;
        Student student = null;
        for (Student d: students){
            if (d.getAverageGrade() < min){
                min = d.getAverageGrade();
                student = d;
            }
        }
        return student;
    }

    public void expel(Student student) {
        students.remove(student);
    }
}