package com.anagha.petclinic.utils;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/petclinic";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "R@njith1992";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        }
        return connection;
    }
    
    
    public static boolean verifyOwnerExists(String fullName) {
        String query = "SELECT COUNT(*) FROM owners WHERE CONCAT(first_name, ' ', last_name) = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, fullName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static List<String> getAllOwnerNames() throws SQLException {
        List<String> owners = new ArrayList<>();
        String query = "SELECT CONCAT(first_name, ' ', last_name) AS full_name FROM owners";

        try (Statement stmt = getConnection().createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                owners.add(rs.getString("full_name"));
            }
        }
        return owners;
    }
    public static void insertOwnerToDB(String firstname, String lastname, String address, String city, String telephone) throws SQLException {
        Connection conn = getConnection();  // Reuse your DB connection method

        String sql = "INSERT INTO owners (first_name, last_name, address, city, telephone) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, firstname);
        ps.setString(2, lastname);
        ps.setString(3, address);
        ps.setString(4, city);
        ps.setString(5, telephone);
        ps.executeUpdate();

        ps.close();
        conn.close();
    }
    
    public static void insertPetToDB(String name, String birthDate, String type, int ownerid) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection(); // your existing method to get DB connection

            String sql = "INSERT INTO pets (name, birth_date, type, owner_id) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);

            // convert to SQL date format
            java.sql.Date sqlDate = java.sql.Date.valueOf(convertToSqlFormat(birthDate)); // yyyy-MM-dd
            stmt.setDate(2, sqlDate);

            stmt.setString(3, type);
            stmt.setInt(4, ownerid);

            stmt.executeUpdate();
            System.out.println("✔ Pet inserted into DB: " + name);

        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }

    private static String convertToSqlFormat(String dob) {
        // input format is dd-MM-yyyy, convert to yyyy-MM-dd
        try {
            java.text.SimpleDateFormat input = new java.text.SimpleDateFormat("dd-MM-yyyy");
            java.text.SimpleDateFormat output = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return output.format(input.parse(dob));
        } catch (Exception e) {
            throw new RuntimeException("Invalid date format: " + dob);
        }
    }
    public static boolean verifyPetExists(String name, String dob, String type, int ownerId) {
    	String query = "SELECT p.name, p.birth_date, t.name as type, p.owner_id " +
                "FROM pets p " +
                "JOIN types t ON p.type_id = t.id " +
                "WHERE p.name = ? AND p.birth_date = ? AND t.name = ? AND p.owner_id = ?";


        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setDate(2, java.sql.Date.valueOf(dob)); // dob in yyyy-MM-dd format
            stmt.setString(3, type);
            stmt.setInt(4, ownerId);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // returns true if a matching record exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void insertVisitIntoDB(int ownerId, int petId, String date, String description) throws SQLException {
        Connection conn = getConnection();
        String query = "INSERT INTO visit (owner_id, pet_id, visit_date, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, ownerId);
            stmt.setInt(2, petId);
            stmt.setDate(3, java.sql.Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            stmt.setString(4, description);
            stmt.executeUpdate();
            
        } catch (Exception e) {
        	 e.printStackTrace();
             
        }
    }
    public static boolean isVisitPresentInDB(int ownerId, int petId, String date, String description) throws SQLException {
        String query = "SELECT COUNT(*) FROM visit WHERE owner_id = ? AND pet_id = ? AND visit_date = ? AND description = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, ownerId);
            stmt.setInt(2, petId);
            stmt.setString(3, date);
            stmt.setString(4, description);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    



    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}