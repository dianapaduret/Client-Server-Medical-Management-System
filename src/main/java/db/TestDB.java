package db;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDB {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                System.out.println(" Conexiune reușită la PostgreSQL!");
                conn.close();
            } else {
                System.out.println(" Conexiune eșuată.");
            }
        } catch (SQLException e) {
            System.out.println(" Eroare SQL: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(" Altă eroare: " + e.getMessage());
        }
    }
}
