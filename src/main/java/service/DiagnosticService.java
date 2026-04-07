package service;

import dao.DiagnosticDAO;
import model.Diagnostic;

import java.time.LocalDate;

public class DiagnosticService {

    private DiagnosticDAO diagnosticDAO = new DiagnosticDAO();

    public void adaugaDiagnostic(int programareId, String text, String tratament, String recomandari) {
        Diagnostic diag = new Diagnostic(0, programareId, text, tratament, recomandari, LocalDate.now());
        diagnosticDAO.save(diag);
        System.out.println(" Diagnostic adăugat cu succes.");
    }

    public Diagnostic cautaDupaProgramare(int programareId) {
        return diagnosticDAO.findByProgramare(programareId);
    }
}
