package dao;

import db.DatabaseConnection;
import model.Diagnostic;

import java.sql.*;
import java.time.LocalDate;

public class DiagnosticDAO {

    public void save(Diagnostic diag) {
        String sql = "INSERT INTO diagnostice (programare_id, diagnostic_text, tratament, recomandari, data) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, diag.getProgramareId());
            stmt.setString(2, diag.getDiagnosticText());
            stmt.setString(3, diag.getTratament());
            stmt.setString(4, diag.getRecomandari());
            stmt.setDate(5, Date.valueOf(diag.getData()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Diagnostic findByProgramare(int programareId) {
        String sql = "SELECT * FROM diagnostice WHERE programare_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, programareId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Diagnostic(
                        rs.getInt("id"),
                        rs.getInt("programare_id"),
                        rs.getString("diagnostic_text"),
                        rs.getString("tratament"),
                        rs.getString("recomandari"),
                        rs.getDate("data").toLocalDate()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
