package model;

import java.time.LocalDate;

public class Tratament {
    private int id;
    private int programareId;
    private String medicamente;
    private String durata; // ex: "7 zile"
    private String instructiuni;
    private LocalDate data;

    public Tratament(int id, int programareId, String medicamente, String durata, String instructiuni, LocalDate data) {
        this.id = id;
        this.programareId = programareId;
        this.medicamente = medicamente;
        this.durata = durata;
        this.instructiuni = instructiuni;
        this.data = data;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProgramareId() { return programareId; }
    public void setProgramareId(int programareId) { this.programareId = programareId; }

    public String getMedicamente() { return medicamente; }
    public void setMedicamente(String medicamente) { this.medicamente = medicamente; }

    public String getDurata() { return durata; }
    public void setDurata(String durata) { this.durata = durata; }

    public String getInstructiuni() { return instructiuni; }
    public void setInstructiuni(String instructiuni) { this.instructiuni = instructiuni; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
}
