package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Programare {
    private int id;
    private int pacientId;
    private int doctorId;
    private LocalDate data;
    private LocalTime ora;
    private String observatii;

    public Programare(int id, int pacientId, int doctorId, LocalDate data, LocalTime ora, String observatii) {
        this.id = id;
        this.pacientId = pacientId;
        this.doctorId = doctorId;
        this.data = data;
        this.ora = ora;
        this.observatii = observatii;
    }


    public int getId() { return id; }
    public int getPacientId() { return pacientId; }
    public int getDoctorId() { return doctorId; }
    public LocalDate getData() { return data; }
    public LocalTime getOra() { return ora; }
    public String getObservatii() { return observatii; }


    public void setId(int id) { this.id = id; }
    public void setPacientId(int pacientId) { this.pacientId = pacientId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public void setData(LocalDate data) { this.data = data; }
    public void setOra(LocalTime ora) { this.ora = ora; }
    public void setObservatii(String observatii) { this.observatii = observatii; }
}
