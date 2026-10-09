package org.example.dao;

import org.example.model.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.example.utility.DBConnection;

public class StudentDaoImpl implements StudentDao{

    @Override
    public int addStudent(Student student) throws SQLException{
        String insertSql = "INSERT INTO student (student_id,name, age, department) VALUES (?, ? ,? , ?)";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(insertSql)){
            ps.setInt(1,student.getStudent_id());
            ps.setString(2,student.getName());
            ps.setInt(3,student.getAge());
            ps.setString(4,student.getDepartment());
            int rowsCreated = ps.executeUpdate();
           return rowsCreated;
        }
    }

    @Override
    public int updateStudent(Student student) throws SQLException {
        String updateSQL = "UPDATE student SET name = ? , age = ? ,department = ? WHERE student_id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps= conn.prepareStatement(updateSQL)){
            ps.setString(1, student.getName());
            ps.setInt(2,student.getAge());
            ps.setString(3,student.getDepartment());
            ps.setInt(4,student.getStudent_id());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated;

        }

    }

    @Override
    public int deleteStudent(int id) throws SQLException {
        String deleteStudent= "DELETE FROM student WHERE student_id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(deleteStudent)){
            ps.setInt(1,id);
            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted;
        }

    }

    @Override
    public Student findStudentByID(int id) throws SQLException{
        String findByIDSQL = "SELECT * FROM student WHERE student_id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(findByIDSQL);) {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
               Student student = new Student(rs.getInt("student_id"),
                                             rs.getString("name"),
                                             rs.getInt("age"),
                                             rs.getString("department"));

               return student;
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Student> findAllStudents()throws SQLException{
        List<Student> students = new ArrayList<>();
        String findAllSQL = "SELECT * FROM student";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(findAllSQL);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()) {
                Student student = new Student(rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("department"));


                students.add(student);
            }

        }

        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return students;
    }

    @Override
    public int transferStuDept(Student student, Connection conn) throws SQLException{

        String updateDept = "UPDATE student SET department = ? WHERE student_id = ?";
        String updateAge = "UPDATE student SET age = ? WHERE student_id = ?";

        try(PreparedStatement ps = conn.prepareStatement(updateDept);
            PreparedStatement psAge = conn.prepareStatement(updateAge)) {

            ps.setString(1, student.getDepartment());
            ps.setInt(2, student.getStudent_id());
            int updatedDept = ps.executeUpdate();
            if(updatedDept == 0){
                throw new SQLException("No student was updated.");
            }

            psAge.setInt(1, student.getAge());
            psAge.setInt(2, student.getStudent_id());
            int updatedAge = psAge.executeUpdate();
            if(updatedAge == 0){
                throw new SQLException("No students was updated.");
            }

            return 1;
        }


    }

    @Override
    public int[] addStudentBatch(List<Student> students, Connection conn) throws SQLException{
        String insertSQL = "INSERT INTO student (student_id, name, age, department) VALUES (?,?,?,?)";
        try(
            PreparedStatement ps = conn.prepareStatement(insertSQL)) {
            for (Student student : students) {
                ps.setInt(1, student.getStudent_id());
                ps.setString(2, student.getName());
                ps.setInt(3, student.getAge());
                ps.setString(4, student.getDepartment());
                ps.addBatch();
            }
            int[] addedRows = ps.executeBatch();
            return addedRows;
        }
    }
}
