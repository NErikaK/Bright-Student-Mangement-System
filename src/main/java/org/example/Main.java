package org.example;
import org.example.Service.ServiceDao;
import org.example.Service.ServiceDaoImpl;
import org.example.dao.StudentDao;
import org.example.dao.StudentDaoImpl;
import org.example.exception.InvalidAgeException;
import org.example.exception.StudentNotFound;
import org.example.model.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        ServiceDao serviceDao = new ServiceDaoImpl();
        ArrayList<String> menuList = new ArrayList<>();
        menuList.add("1.Create a new student.");
        menuList.add("2.Update a student's info.");
        menuList.add("3.Delete a student's info.");
        menuList.add("4.Find student by ID.");
        menuList.add("5.Show all students.");
        menuList.add("6.Transfer Student Department.");
        menuList.add("7.Add multiple students.");
        menuList.add("0.Exit.");

        Scanner scan = new Scanner(System.in);

        System.out.println("\n -----Welcome To Bright Student Management System-----");
        System.out.println("Here is the a list you can perform.\n");
        for(String menu : menuList){
            System.out.println(menu);
        }

        int userChoice;
        do {
            System.out.println("Please enter a number to proceed.");
            userChoice = scan.nextInt();


            switch (userChoice) {
                case 1:
                    System.out.println("Enter student ID: ");
                    int id = scan.nextInt();
                    scan.nextLine();
                    System.out.println("Enter student name: ");
                    String name =scan.nextLine();
                    int age = validateAge(scan);
                    scan.nextLine();
                    System.out.println("Enter student department: ");
                    String department = scan.nextLine();
                    Student s = new Student(id, name, age, department);
                    try{
                        int rowsCreated = serviceDao.create(s);
                        if(rowsCreated > 0){
                            System.out.println(rowsCreated + " student added successfully.");
                        }
                        else{
                            System.out.println("Fail to add a student.");
                        }
                    }
                    catch (SQLException e){
                        System.out.println("Error Message: " + e.getMessage());
                    }
                    catch (InvalidAgeException e){
                        System.out.println("Custom Error Message: " +e.getMessage());
                    }

                    break;

                case 2:
                    System.out.println("Enter the ID of the student you want to update: ");
                    id = scan.nextInt();
                    scan.nextLine();
                    System.out.println("Enter name of the student ID: " + id);
                    name = scan.nextLine();
                    age = validateAge(scan);
                    scan.nextLine();
                    System.out.println("Enter department of the student ID: " + id);
                    department = scan.nextLine();
                    s = new Student(id, name, age, department);
                    try {
                        int rowsUpdated = serviceDao.update(s);
                        if(rowsUpdated > 0){
                            System.out.println(rowsUpdated + " student updated successfully.");
                        }
                        else{
                            System.out.println("Fail to update.");
                        }
                    }
                    catch (SQLException e){
                        System.out.println("Error Message: " + e.getMessage());
                    }
                    catch (InvalidAgeException e){
                        System.out.println("Custom Error Message: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Enter the ID of the student you want to delete: ");
                    id = scan.nextInt();
                    try {
                        int rowsDeleted = serviceDao.delete(id);
                        if(rowsDeleted > 0){
                            System.out.println(rowsDeleted + " student deleted successfully.");
                        }
                        else{
                            System.out.println("Fail to delete.");
                        }
                    }
                    catch (SQLException e){
                        System.out.println("Error Message: " + e.getMessage());
                    }
                    catch (StudentNotFound e){
                        System.out.println("Custom Error Message: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("Enter the ID of the student you want to find: ");
                    id = scan.nextInt();
                    try {
                        s = serviceDao.findByID(id);
                        if (s != null) {
                            System.out.println("Student Found \n " + s);
                        } else {
                            System.out.println("Student Not Found.");
                        }
                    }
                    catch (SQLException e){
                        System.out.println(e.getMessage());
                    }

                    break;

                case 5:
                    try{
                    List<Student> students = serviceDao.findAll();
                       if(students.isEmpty()){
                           System.out.println("There are no student yet.");
                       }
                       else {
                           for(Student student : students){
                               System.out.println(student);
                           }
                       }
                    }
                    catch (SQLException e){
                        System.out.println("Error Message: " + e.getMessage());
                    }

                    break;

                case 6:
                    System.out.println("Enter the id of the student you want to update:");
                    id = scan.nextInt();
                    scan.nextLine();
                    System.out.println("Enter the department to change.");
                    department = scan.nextLine();
                    System.out.println("Enter the age of the student.");
                    age = scan.nextInt();
                    scan.nextLine();
                    s = new Student(id,age,department);
                    try{
                        int rowsAffected = serviceDao.transferDept(s);
                        if(rowsAffected > 0){
                            System.out.println("Successful Transfer.");
                        }
                        else System.out.println("Fail to transfer.");
                    }
                    catch (SQLException e){
                        System.out.println(e.getMessage());

                    }

                case 7:
                    int count;
                    do {
                        System.out.println("How many students do you want to insert?");
                        count = scan.nextInt();
                        scan.nextLine();
                    }
                    while(count <= 0);

                    List<Student> students = new ArrayList<>();
                    for(int i = 1; i <= count; i++) {
                        System.out.println("Enter the student_id: ");
                        id = scan.nextInt();
                        scan.nextLine();
                        System.out.println("Enter student name: ");
                        name = scan.nextLine();
                        System.out.println("Enter student age: ");
                        age = scan.nextInt();
                        scan.nextLine();
                        System.out.println("Enter student department: ");
                        department = scan.nextLine();
                        students.add(new Student(id, name, age, department));
                    }
                    try{
                        int[] rowsAffected = serviceDao.addStuBatch(students);
                        if(rowsAffected.length > 0){
                            System.out.println("Multiple students added successfully.");
                        }
                        else System.out.println("Fail to add multiple students.");
                    }
                    catch (SQLException e){
                        System.out.println(e.getMessage());
                    }



                case 0:
                    System.out.println("Thank you for using Bright Student Manangement System.");
            }
        }
        while (userChoice != 0);

        }

    private static int validateAge (Scanner scan){
        int studentAge;
        do {
            System.out.println("Enter age of the student: Note(Age must be between 16 and 60.");
            studentAge = scan.nextInt();
        }
        while (studentAge < 16 || studentAge > 60);
        return studentAge;
    }

    }


