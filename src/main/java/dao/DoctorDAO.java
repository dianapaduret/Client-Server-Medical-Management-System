package dao;

import model.Doctor;
import db.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    public void save(Doctor doctor) {
        String sqlUtilizator = "INSERT INTO utilizatori (nume, email, parola, rol) VALUES (?, ?, ?, ?) RETURNING id";
        String sqlDoctor = "INSERT INTO doctori (id, specializare) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtUtilizator = conn.prepareStatement(sqlUtilizator);
             PreparedStatement stmtDoctor = conn.prepareStatement(sqlDoctor)) {

            stmtUtilizator.setString(1, doctor.getNume());
            stmtUtilizator.setString(2, doctor.getEmail());
            stmtUtilizator.setString(3, doctor.getParola());
            stmtUtilizator.setString(4, "doctor");

            ResultSet rs = stmtUtilizator.executeQuery();
            if (rs.next()) {
                int idGenerat = rs.getInt("id");
                doctor.setId(idGenerat);

                stmtDoctor.setInt(1, idGenerat);
                stmtDoctor.setString(2, doctor.getSpecializare());
                stmtDoctor.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Doctor findByEmail(String email) {
        String sql = "SELECT u.id, u.nume, u.email, u.parola, d.specializare FROM utilizatori u " +
                "JOIN doctori d ON u.id = d.id WHERE u.email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Doctor(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getString("email"),
                        rs.getString("parola"),
                        rs.getString("specializare")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Doctor> findAll() {
        List<Doctor> doctori = new ArrayList<>();
        String sql = "SELECT u.id, u.nume, u.email, u.parola, d.specializare " +
                "FROM utilizatori u JOIN doctori d ON u.id = d.id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Doctor doctor = new Doctor(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getString("email"),
                        rs.getString("parola"),
                        rs.getString("specializare")
                );
                doctori.add(doctor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctori;
    }

    public String findNumeById(int id) {
        String sql = "SELECT nume FROM utilizatori WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("nume");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "N/A";
    }
}
