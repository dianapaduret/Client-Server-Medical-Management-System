package model;

import java.util.Objects;

public class Pacient extends Utilizator {
    private String alergii;
    private String afectiuniCronice;
    private String medicatieCurenta;
    private String istoricFamilial;

    public Pacient(int id, String nume, String email, String parola,
                   String alergii, String afectiuni, String medicatie, String istoric) {
        super(id, nume, email, parola);
        this.alergii = alergii;
        this.afectiuniCronice = afectiuni;
        this.medicatieCurenta = medicatie;
        this.istoricFamilial = istoric;
    }

    @Override
    public String getRol() {
        return "Pacient";
    }

    public String getAlergii() {
        return alergii;
    }

    public void setAlergii(String alergii) {
        this.alergii = alergii;
    }

    public String getAfectiuniCronice() {
        return afectiuniCronice;
    }

    public void setAfectiuniCronice(String afectiuniCronice) {
        this.afectiuniCronice = afectiuniCronice;
    }

    public String getMedicatieCurenta() {
        return medicatieCurenta;
    }

    public void setMedicatieCurenta(String medicatieCurenta) {
        this.medicatieCurenta = medicatieCurenta;
    }

    public String getIstoricFamilial() {
        return istoricFamilial;
    }

    public void setIstoricFamilial(String istoricFamilial) {
        this.istoricFamilial = istoricFamilial;
    }

    @Override
    public String toString() {
        return "Pacient: " + getNume() +
                " | Email: " + getEmail() +
                " | Alergii: " + alergii;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pacient)) return false;
        Pacient pacient = (Pacient) o;
        return getId() == pacient.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
