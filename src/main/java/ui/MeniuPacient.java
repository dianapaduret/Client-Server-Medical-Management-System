package ui;

import dao.DoctorDAO;
import model.Diagnostic;
import model.Doctor;
import model.Pacient;
import model.Tratament;
import service.DiagnosticService;
import service.ProgramareService;
import dao.PacientDAO;
import service.TratamentService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class MeniuPacient {

    private static final Scanner sc = new Scanner(System.in);
    private static final ProgramareService programareService = new ProgramareService();
    private static final PacientDAO pacientDAO = new PacientDAO();

    public static void start(Pacient pacient) {
        boolean gata = false;
        while (!gata) {
            System.out.println("\n--- Meniu Pacient ---");
            System.out.println("1. Programeaza-te");
            System.out.println("2. Vezi programarile tale");
            System.out.println("3. Vezi dosarul medical");
            System.out.println("4. Editeaza dosarul medical");
            System.out.println("5. Vezi ore disponibile pentru un medic într-o zi");
            System.out.println("6. Anulează o programare");
            System.out.println("7. Vezi diagnosticul unei programări");
            System.out.println("8. Vezi tratamentul unei programări");
            System.out.println("0. Deconectare");
            System.out.print("Alege: ");
            String opt = sc.nextLine();

            DoctorDAO doctorDAO = new DoctorDAO();
            List<Doctor> listaDoctori = doctorDAO.findAll();
            switch (opt) {
                case "1":
                    System.out.println("\n--- Lista doctorilor disponibili ---");
                    for (Doctor d : listaDoctori) {
                        System.out.println("ID: " + d.getId() + " | Nume: " + d.getNume() + " | Specializare: " + d.getSpecializare());
                    }

                    int idDoctor = -1;
                    while (true) {
                        System.out.print("ID Doctor: ");
                        try {
                            idDoctor = Integer.parseInt(sc.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println(" Te rog introdu un număr valid pentru ID-ul doctorului.");
                        }
                    }

                    LocalDate data = null;
                    while (data == null) {
                        System.out.print("Data (yyyy-mm-dd): ");
                        try {
                            LocalDate tentativa = LocalDate.parse(sc.nextLine());
                            if (tentativa.isBefore(LocalDate.now())) {
                                System.out.println(" Nu poți selecta o dată din trecut.");
                            } else {
                                data = tentativa;
                            }
                        } catch (Exception e) {
                            System.out.println(" Format invalid. Exemplu: 2025-06-20");
                        }
                    }

                    List<LocalTime> disponibile = programareService.getOreDisponibile(idDoctor, data);

                    if (disponibile.isEmpty()) {
                        System.out.println(" Nu există ore disponibile pentru această zi.");
                        break;
                    }

                    System.out.println("\n--- Ore disponibile ---");
                    for (int i = 0; i < disponibile.size(); i++) {
                        System.out.println((i + 1) + ". " + disponibile.get(i));
                    }

                    int optiuneOra = -1;
                    while (true) {
                        System.out.print("Alege numărul unei ore disponibile: ");
                        try {
                            optiuneOra = Integer.parseInt(sc.nextLine());
                            if (optiuneOra >= 1 && optiuneOra <= disponibile.size()) {
                                break;
                            } else {
                                System.out.println(" Opțiune invalidă. Alege un număr din listă.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println(" Trebuie să introduci un număr.");
                        }
                    }

                    LocalTime ora = disponibile.get(optiuneOra - 1);

                    System.out.print("Observații: ");
                    String observatii = sc.nextLine();

                    programareService.adaugaProgramare(pacient.getId(), idDoctor, data, ora, observatii);
                    break;
                case "2":
                    programareService.afiseazaProgramarilePacientului(pacient.getId());
                    break;
                case "3":
                    afiseazaDosarMedical(pacient);
                    break;
                case "4":
                    editeazaDosarMedical(pacient);
                    break;
                case "5":
                    System.out.println("\n--- Lista doctorilor disponibili ---");
                    for (Doctor d : listaDoctori) {
                        System.out.println("ID: " + d.getId() + " | Nume: " + d.getNume() + " | Specializare: " + d.getSpecializare());
                    }

                    int idDoc = -1;
                    while (true) {
                        System.out.print("ID Doctor: ");
                        try {
                            idDoc = Integer.parseInt(sc.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println(" Te rog introdu un număr valid pentru ID-ul doctorului.");
                        }
                    }

                    LocalDate zi = null;
                    while (zi == null) {
                        System.out.print("Data (yyyy-mm-dd): ");
                        try {
                            LocalDate tentativa = LocalDate.parse(sc.nextLine());
                            if (tentativa.isBefore(LocalDate.now())) {
                                System.out.println(" Nu poți selecta o dată din trecut.");
                            } else {
                                zi = tentativa;
                            }
                        } catch (Exception e) {
                            System.out.println(" Format invalid. Exemplu: 2025-06-20");
                        }
                    }

                    disponibile = programareService.getOreDisponibile(idDoc, zi);
                    if (disponibile.isEmpty()) {
                        System.out.println(" Nu există ore disponibile pentru această zi.");
                    } else {
                        System.out.println("\n--- Ore disponibile ---");
                        for (int i = 0; i < disponibile.size(); i++) {
                            System.out.println((i + 1) + ". " + disponibile.get(i));
                        }
                    }

                    break;
                case "6":
                    programareService.afiseazaProgramarilePacientului(pacient.getId());
                    System.out.print("Introdu ID-ul programării de anulat: ");
                    int idAnulare = Integer.parseInt(sc.nextLine());

                    System.out.print("Ești sigur că vrei să anulezi această programare? (da/nu): ");
                    String confirmare = sc.nextLine().trim().toLowerCase();

                    if (confirmare.equals("da")) {
                        programareService.anuleazaProgramare(pacient.getId(), idAnulare);
                    } else {
                        System.out.println("Anularea a fost anulată.");
                    }
                    break;
                case "7":
                    System.out.println("\n--- Programările tale ---");
                    programareService.afiseazaProgramarilePacientului(pacient.getId());

                    System.out.print("ID programare: ");
                    int idP = Integer.parseInt(sc.nextLine());

                    if (!programareService.esteProgramareaPacientului(idP, pacient.getId())) {
                        System.out.println(" Nu ai acces la această programare.");
                        break;
                    }

                    DiagnosticService ds = new DiagnosticService();
                    Diagnostic diagP = ds.cautaDupaProgramare(idP);

                    if (diagP == null) {
                        System.out.println(" Nu există diagnostic asociat.");
                    } else {
                        System.out.println("\n=== Diagnostic ===");
                        System.out.println("Data: " + diagP.getData());
                        System.out.println("Diagnostic: " + diagP.getDiagnosticText());
                        System.out.println("Tratament: " + diagP.getTratament());
                        System.out.println("Recomandări: " + diagP.getRecomandari());
                    }
                    break;
                case "8":
                    System.out.println("\n--- Programările tale ---");
                    programareService.afiseazaProgramarilePacientului(pacient.getId());

                    System.out.print("ID programare: ");
                    int idTratament = Integer.parseInt(sc.nextLine());

                    if (!programareService.esteProgramareaPacientului(idTratament, pacient.getId())) {
                        System.out.println(" Nu ai acces la această programare.");
                        break;
                    }

                    Tratament tratament = new TratamentService().cautaDupaProgramare(idTratament);
                    if (tratament == null) {
                        System.out.println(" Nu există tratament pentru această programare.");
                    } else {
                        System.out.println("\n=== Tratament medical ===");
                        System.out.println("Data: " + tratament.getData());
                        System.out.println("Medicamente: " + tratament.getMedicamente());
                        System.out.println("Durata: " + tratament.getDurata());
                        System.out.println("Instrucțiuni: " + tratament.getInstructiuni());
                    }
                    break;

                case "0":
                    gata = true;
                    break;
                default:
                    System.out.println("Opțiune invalidă.");
            }
        }
    }

    private static void afiseazaDosarMedical(Pacient pacient) {
        System.out.println("\n=== Dosar medical personal ===");
        System.out.println("Nume: " + pacient.getNume());
        System.out.println("Email: " + pacient.getEmail());
        System.out.println("Alergii: " + pacient.getAlergii());
        System.out.println("Afecțiuni cronice: " + pacient.getAfectiuniCronice());
        System.out.println("Medicație curentă: " + pacient.getMedicatieCurenta());
        System.out.println("Istoric familial: " + pacient.getIstoricFamilial());
    }

    private static void editeazaDosarMedical(Pacient pacient) {
        System.out.println("\n--- Editare Dosar Medical ---");

        System.out.print("Alergii noi: ");
        pacient.setAlergii(sc.nextLine());

        System.out.print("Afecțiuni cronice noi: ");
        pacient.setAfectiuniCronice(sc.nextLine());

        System.out.print("Medicație curentă nouă: ");
        pacient.setMedicatieCurenta(sc.nextLine());

        System.out.print("Istoric familial nou: ");
        pacient.setIstoricFamilial(sc.nextLine());

        pacientDAO.update(pacient);
        System.out.println(" Dosarul medical a fost actualizat cu succes.");
    }
}
