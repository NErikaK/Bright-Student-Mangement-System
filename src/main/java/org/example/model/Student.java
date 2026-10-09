package org.example.model;

public class Student {
    int student_id;
    String name;
    int age;
    String department;

    public Student(int student_id, String name,int age, String department){
        this.student_id = student_id;
        this.name = name;
        this.age = age;
        this.department = department;
    }

    public Student(int student_id, int age, String department){
        this.student_id = student_id;
        this.age = age;
        this.department = department;
    }

    public Student(){}

    public int getStudent_id() {return student_id;}

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString(){
        return "ID: " + student_id + "\n" +
                "Name: " + name + "\n" +
                "Age: " + age + "\n" +
                "Department: " + department + "\n" ;
    }
}


