package service;

import dao.DoctorDAO;
import dao.PacientDAO;
import dao.ProgramareDAO;
import model.Programare;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ProgramareService {

    private ProgramareDAO programareDAO = new ProgramareDAO();
    private DoctorDAO doctorDAO = new DoctorDAO();
    private PacientDAO pacientDAO = new PacientDAO();

    public void afiseazaProgramarilePacientului(int pacientId) {
        List<Programare> lista = programareDAO.findByPacient(pacientId);
        if (lista.isEmpty()) {
            System.out.println("Nu există programări.");
        } else {
            for (Programare p : lista) {
                String numeDoctor = doctorDAO.findNumeById(p.getDoctorId());
                System.out.println("ID: " + p.getId() +
                        " | Data: " + p.getData() +
                        " | Ora: " + p.getOra() +
                        " | Doctor: " + numeDoctor +
                        " | Observatii: " + p.getObservatii());
            }
        }
    }

    public void afiseazaProgramarileDoctoruluiInZi(int doctorId, LocalDate data) {
        List<Programare> lista = programareDAO.findByDoctorAndDate(doctorId, data);
        if (lista.isEmpty()) {
            System.out.println("Nu există programări pentru ziua selectată.");
        } else {
            for (Programare p : lista) {
                String numePacient = pacientDAO.findNumePacientById(p.getPacientId());
                System.out.println(p.getOra() + " | Pacient: " + numePacient + " | Observatii: " + p.getObservatii());
            }
        }
    }

    public void adaugaProgramare(int pacientId, int doctorId, LocalDate data, LocalTime ora, String observatii) {
        if (data.isBefore(LocalDate.now())) {
            System.out.println(" Nu poți face o programare în trecut.");
            return;
        }

        if (ora.isBefore(LocalTime.of(8, 0)) || ora.isAfter(LocalTime.of(18, 0))) {
            System.out.println(" Ora este în afara programului (8:00–18:00). Alege o oră validă.");
            return;
        }

        if (programareDAO.existaConflict(doctorId, data, ora)) {
            System.out.println(" Doctorul este deja programat la acea oră. Alege alt interval.");
            return;
        }

        Programare p = new Programare(0, pacientId, doctorId, data, ora, observatii);
        programareDAO.save(p);
        System.out.println(" Programare înregistrată cu succes!");
    }

    public void afiseazaOreDisponibile(int doctorId, LocalDate data) {
        List<LocalTime> ocupate = programareDAO.findOreOcupate(doctorId, data);
        System.out.println("\n--- Ore disponibile pentru " + data + " ---");

        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(18, 0);

        while (start.isBefore(end)) {
            if (!ocupate.contains(start)) {
                System.out.println(start + " disponibil");
            }
            start = start.plusMinutes(30);
        }
    }

    public void anuleazaProgramare(int pacientId, int programareId) {
        if (!programareDAO.apartinePacientului(programareId, pacientId)) {
            System.out.println(" Nu poți anula o programare care nu îți aparține.");
            return;
        }

        programareDAO.delete(programareId);
        System.out.println(" Programarea a fost anulată cu succes.");
    }

    public List<LocalTime> getOreDisponibile(int doctorId, LocalDate data) {
        List<LocalTime> ocupate = programareDAO.findOreOcupate(doctorId, data);
        List<LocalTime> disponibile = new ArrayList<>();

        LocalTime ora = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(18, 0);

        while (ora.isBefore(end)) {
            if (!ocupate.contains(ora)) {
                disponibile.add(ora);
            }
            ora = ora.plusMinutes(30);
        }

        return disponibile;
    }

    public boolean esteProgramareaPacientului(int idProgramare, int idPacient) {
        return programareDAO.apartinePacientului(idProgramare, idPacient);
    }

}
