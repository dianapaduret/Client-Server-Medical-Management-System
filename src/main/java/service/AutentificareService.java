package service;

import dao.PacientDAO;
import dao.DoctorDAO;
import model.Doctor;
import model.Pacient;
import util.ValidatorUtil;

public class AutentificareService {

    private PacientDAO pacientDAO = new PacientDAO();
    private DoctorDAO doctorDAO = new DoctorDAO();

    public boolean existaUtilizator(String email) {
        return pacientDAO.findByEmail(email) != null || doctorDAO.findByEmail(email) != null;
    }

    public void inregistreazaPacient(String nume, String email, String parola,
                                     String alergii, String afectiuni, String medicatie, String istoric) {
        if (!ValidatorUtil.esteEmailValid(email)) {
            System.out.println("Email invalid!");
            return;
        }

        if (existaUtilizator(email)) {
            System.out.println("Email deja folosit.");
            return;
        }

        if (!ValidatorUtil.esteParolaValida(parola)) {
            System.out.println("Parola este prea slabă. Ea trebuie să conțină minim 6 caractere, o literă mare și o cifră.");
            return;
        }

        Pacient p = new Pacient(0, nume, email, parola, alergii, afectiuni, medicatie, istoric);
        pacientDAO.save(p);
        System.out.println("Pacient înregistrat cu succes!");
    }


    public void inregistreazaDoctor(String nume, String email, String parola, String specializare) {
        if (!ValidatorUtil.esteEmailValid(email)) {
            System.out.println("Email invalid!");
            return;
        }

        if (existaUtilizator(email)) {
            System.out.println("Email deja folosit.");
            return;
        }

        if (!ValidatorUtil.esteParolaValida(parola)) {
            System.out.println("Parola este prea slabă. Ea trebuie să conțină minim 6 caractere, o literă mare și o cifră.");
            return;
        }

        Doctor d = new Doctor(0, nume, email, parola, specializare);
        doctorDAO.save(d);
        System.out.println("Doctor înregistrat cu succes!");
    }


    public Object autentificare(String email, String parola) {
        Pacient p = pacientDAO.findByEmail(email);
        if (p != null && p.getParola().equals(parola)) {
            return p;
        }
        Doctor d = doctorDAO.findByEmail(email);
        if (d != null && d.getParola().equals(parola)) {
            return d;
        }
        return null;
    }
}
