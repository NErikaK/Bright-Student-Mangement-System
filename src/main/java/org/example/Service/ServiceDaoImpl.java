package org.example.Service;

import org.example.dao.StudentDao;
import org.example.dao.StudentDaoImpl;
import org.example.exception.InvalidAgeException;
import org.example.exception.StudentNotFound;
import org.example.model.Student;
import org.example.utility.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;


public class ServiceDaoImpl implements ServiceDao{
    private StudentDao studentDao = new StudentDaoImpl();

    @Override
    public int create(Student student) throws SQLException, InvalidAgeException {
        //even though the age is checked in main, we keep this exception handling because they have diff purposes.
        if(student.getAge() < 16 || student.getAge() > 60){
            throw new InvalidAgeException("Age must be between 16 and 60.");
        }
        return studentDao.addStudent(student);

    }

    @Override
    public int update(Student student) throws SQLException, InvalidAgeException{
        if(student.getAge() < 16 || student.getAge() > 60){
            throw new InvalidAgeException("Age must be between 16 and 60.");
        }
        return studentDao.updateStudent(student);
    }

    @Override
    public int delete(int id) throws SQLException , StudentNotFound {
        if(studentDao.findStudentByID(id) == null){
            throw new StudentNotFound("Student with ID " + id + " not Found.");
        }
        return studentDao.deleteStudent(id);
    }

    @Override
    public Student findByID(int id)throws SQLException{
      return studentDao.findStudentByID(id);
    }

    @Override
    public List<Student> findAll()throws SQLException{
        return studentDao.findAllStudents();
    }

    @Override
    public int transferDept(Student student) throws SQLException{
        try(Connection conn = DBConnection.getConnection()){
            conn.setAutoCommit(false);

            try {
                studentDao.transferStuDept(student, conn);
                conn.commit();
                return 1;
            }

            catch (SQLException e){
                conn.rollback();
                throw e;
            }
        }


    }

    @Override
    public int[] addStuBatch(List<Student> students) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                int[] rowsAffected = studentDao.addStudentBatch(students, conn);
                conn.commit();
                return rowsAffected;


            }
            catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }

    }

}
