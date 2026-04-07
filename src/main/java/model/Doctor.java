package model;

import java.util.Objects;

public class Doctor extends Utilizator {
    private String specializare;

    public Doctor(int id, String nume, String email, String parola, String specializare) {
        super(id, nume, email, parola);
        this.specializare = specializare;
    }

    @Override
    public String getRol() {
        return "Doctor";
    }

    public String getSpecializare() {
        return specializare;
    }

    public void setSpecializare(String specializare) {
        this.specializare = specializare;
    }

    @Override
    public String toString() {
        return "Doctor: " + getNume() +
                " | Specializare: " + specializare +
                " | Email: " + getEmail();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        Doctor doctor = (Doctor) o;
        return getId() == doctor.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}