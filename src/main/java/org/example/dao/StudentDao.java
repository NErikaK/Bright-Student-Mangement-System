package org.example.dao;

import org.example.model.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface StudentDao {
     int addStudent(Student student) throws SQLException;
     int updateStudent(Student student) throws SQLException;
     int deleteStudent(int id) throws SQLException ;
     Student  findStudentByID(int id) throws SQLException;
     List<Student> findAllStudents()throws SQLException;
     int transferStuDept(Student student,Connection conn) throws SQLException;
     int[] addStudentBatch(List<Student> students, Connection conn) throws SQLException;



}
