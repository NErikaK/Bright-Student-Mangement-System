package org.example.Service;

import org.example.utility.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionPractice {
    public static void main(String[] args) {


        try(Connection conn = DBConnection.getConnection()){
            conn.setAutoCommit(false);

            String insertSQL = "INSERT INTO student (student_id, name, age, department) VALUES (?,?,?,?)";
            String updateSQL = "UPDATE student SET name = ? WHERE student_id = ?";
            try(PreparedStatement psInsert = conn.prepareStatement(insertSQL);
                PreparedStatement psUpdate = conn.prepareStatement(updateSQL)){

                psInsert.setInt(1,6);
                psInsert.setString(2,"George");
                psInsert.setInt(3,20);
                psInsert.setString(4,"IT");
                psInsert.executeUpdate();

                psUpdate.setString(1,"Em");
                psUpdate.setInt(2,4);
                int rowsUpdated = psUpdate.executeUpdate();
                if(rowsUpdated == 0 ){
                    throw new SQLException("No student found");
                }
                conn.commit();

            }
            catch (SQLException e){
                conn.rollback();
                System.out.println("Error Message: " + e.getMessage());
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}
