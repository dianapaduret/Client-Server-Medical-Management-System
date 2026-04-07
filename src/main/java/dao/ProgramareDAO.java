package dao;

import db.DatabaseConnection;
import model.Programare;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ProgramareDAO {

    public void save(Programare p) {
        String sql = "INSERT INTO programari (pacient_id, doctor_id, data, ora, observatii) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getPacientId());
            stmt.setInt(2, p.getDoctorId());
            stmt.setDate(3, Date.valueOf(p.getData()));
            stmt.setTime(4, Time.valueOf(p.getOra()));
            stmt.setString(5, p.getObservatii());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Programare> findByDoctorAndDate(int doctorId, LocalDate data) {
        List<Programare> lista = new ArrayList<>();
        String sql = "SELECT * FROM programari WHERE doctor_id = ? AND data = ? ORDER BY ora";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);
            stmt.setDate(2, Date.valueOf(data));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Programare p = new Programare(
                        rs.getInt("id"),
                        rs.getInt("pacient_id"),
                        rs.getInt("doctor_id"),
                        rs.getDate("data").toLocalDate(),
                        rs.getTime("ora").toLocalTime(),
                        rs.getString("observatii")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Programare> findByPacient(int pacientId) {
        List<Programare> lista = new ArrayList<>();
        String sql = "SELECT * FROM programari WHERE pacient_id = ? ORDER BY data, ora";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pacientId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Programare p = new Programare(
                        rs.getInt("id"),
                        rs.getInt("pacient_id"),
                        rs.getInt("doctor_id"),
                        rs.getDate("data").toLocalDate(),
                        rs.getTime("ora").toLocalTime(),
                        rs.getString("observatii")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean existaConflict(int doctorId, LocalDate data, LocalTime ora) {
        String sql = "SELECT COUNT(*) FROM programari WHERE doctor_id = ? AND data = ? AND ora = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setTime(3, Time.valueOf(ora));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //metoda pentru anularea programarilor
    public void delete(int id) {
        String sql = "DELETE FROM programari WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //metoda pentru afisarea orelor in care doctorul este deja ocupat
    public List<LocalTime> findOreOcupate(int doctorId, LocalDate data) {
        List<LocalTime> ore = new ArrayList<>();
        String sql = "SELECT ora FROM programari WHERE doctor_id = ? AND data = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);
            stmt.setDate(2, Date.valueOf(data));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ore.add(rs.getTime("ora").toLocalTime());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ore;
    }

    public boolean apartinePacientului(int programareId, int pacientId) {
        String sql = "SELECT COUNT(*) FROM programari WHERE id = ? AND pacient_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, programareId);
            stmt.setInt(2, pacientId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean esteProgramareaPacientului(int idProgramare, int idPacient) {
        return apartinePacientului(idProgramare, idPacient);
    }

}
