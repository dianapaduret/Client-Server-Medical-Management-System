package dao;

import db.DatabaseConnection;
import model.Tratament;

import java.sql.*;
import java.time.LocalDate;

public class TratamentDAO {

    public void save(Tratament t) {
        String sql = "INSERT INTO tratamente (programare_id, medicamente, durata, instructiuni, data) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, t.getProgramareId());
            stmt.setString(2, t.getMedicamente());
            stmt.setString(3, t.getDurata());
            stmt.setString(4, t.getInstructiuni());
            stmt.setDate(5, Date.valueOf(t.getData()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Tratament findByProgramare(int programareId) {
        String sql = "SELECT * FROM tratamente WHERE programare_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, programareId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Tratament(
                        rs.getInt("id"),
                        rs.getInt("programare_id"),
                        rs.getString("medicamente"),
                        rs.getString("durata"),
                        rs.getString("instructiuni"),
                        rs.getDate("data").toLocalDate()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
