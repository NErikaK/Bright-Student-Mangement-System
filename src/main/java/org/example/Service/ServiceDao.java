package org.example.Service;

import org.example.exception.InvalidAgeException;
import org.example.exception.StudentNotFound;
import org.example.model.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ServiceDao {
    int create(Student student) throws SQLException, InvalidAgeException;
    int update(Student student) throws SQLException, InvalidAgeException;
    int delete(int id) throws SQLException, StudentNotFound;
    Student findByID(int id)throws SQLException;
    List<Student> findAll() throws SQLException;
    int transferDept(Student student) throws SQLException;
    int[] addStuBatch(List<Student>students) throws SQLException;

}
