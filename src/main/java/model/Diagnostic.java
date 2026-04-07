package model;

import java.time.LocalDate;

public class Diagnostic {
    private int id;
    private int programareId;
    private String diagnosticText;
    private String tratament;
    private String recomandari;
    private LocalDate data;

    public Diagnostic(int id, int programareId, String diagnosticText, String tratament, String recomandari, LocalDate data) {
        this.id = id;
        this.programareId = programareId;
        this.diagnosticText = diagnosticText;
        this.tratament = tratament;
        this.recomandari = recomandari;
        this.data = data;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProgramareId() { return programareId; }
    public void setProgramareId(int programareId) { this.programareId = programareId; }

    public String getDiagnosticText() { return diagnosticText; }
    public void setDiagnosticText(String diagnosticText) { this.diagnosticText = diagnosticText; }

    public String getTratament() { return tratament; }
    public void setTratament(String tratament) { this.tratament = tratament; }

    public String getRecomandari() { return recomandari; }
    public void setRecomandari(String recomandari) { this.recomandari = recomandari; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
}
