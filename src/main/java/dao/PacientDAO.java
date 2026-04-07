package dao;

import db.DatabaseConnection;
import model.Pacient;

import java.sql.*;

public class PacientDAO {

    public void save(Pacient pacient) {
        String sqlUtilizator = "INSERT INTO utilizatori (nume, email, parola, rol) VALUES (?, ?, ?, ?) RETURNING id";
        String sqlPacient = "INSERT INTO pacienti (id, alergii, afectiuni_cronice, medicatie_curenta, istoric_familial) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtUtilizator = conn.prepareStatement(sqlUtilizator);
             PreparedStatement stmtPacient = conn.prepareStatement(sqlPacient)) {

            // Inserare în tabela utilizatori
            stmtUtilizator.setString(1, pacient.getNume());
            stmtUtilizator.setString(2, pacient.getEmail());
            stmtUtilizator.setString(3, pacient.getParola());
            stmtUtilizator.setString(4, "pacient");

            ResultSet rs = stmtUtilizator.executeQuery();
            if (rs.next()) {
                int idGenerat = rs.getInt("id");
                pacient.setId(idGenerat);

                // Inserare în tabela pacienti
                stmtPacient.setInt(1, idGenerat);
                stmtPacient.setString(2, pacient.getAlergii());
                stmtPacient.setString(3, pacient.getAfectiuniCronice());
                stmtPacient.setString(4, pacient.getMedicatieCurenta());
                stmtPacient.setString(5, pacient.getIstoricFamilial());

                stmtPacient.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Pacient findByEmail(String email) {
        String sql = "SELECT u.id, u.nume, u.email, u.parola, " +
                "p.alergii, p.afectiuni_cronice, p.medicatie_curenta, p.istoric_familial " +
                "FROM utilizatori u JOIN pacienti p ON u.id = p.id WHERE u.email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Pacient(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getString("email"),
                        rs.getString("parola"),
                        rs.getString("alergii"),
                        rs.getString("afectiuni_cronice"),
                        rs.getString("medicatie_curenta"),
                        rs.getString("istoric_familial")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void update(Pacient pacient) {
        String sql = "UPDATE pacienti SET alergii = ?, afectiuni_cronice = ?, medicatie_curenta = ?, istoric_familial = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pacient.getAlergii());
            stmt.setString(2, pacient.getAfectiuniCronice());
            stmt.setString(3, pacient.getMedicatieCurenta());
            stmt.setString(4, pacient.getIstoricFamilial());
            stmt.setInt(5, pacient.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String findNumePacientById(int id) {
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
