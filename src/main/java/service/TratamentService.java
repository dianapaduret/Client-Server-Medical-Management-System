package service;

import dao.TratamentDAO;
import model.Tratament;

import java.time.LocalDate;

public class TratamentService {

    private TratamentDAO tratamentDAO = new TratamentDAO();

    public void adaugaTratament(int programareId, String medicamente, String durata, String instructiuni) {
        Tratament t = new Tratament(0, programareId, medicamente, durata, instructiuni, LocalDate.now());
        tratamentDAO.save(t);
        System.out.println(" Tratament salvat cu succes.");
    }

    public Tratament cautaDupaProgramare(int programareId) {
        return tratamentDAO.findByProgramare(programareId);
    }
}
