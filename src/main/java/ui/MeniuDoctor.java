package ui;

import model.Diagnostic;
import model.Doctor;
import model.Tratament;
import service.DiagnosticService;
import service.ProgramareService;
import service.TratamentService;

import java.time.LocalDate;
import java.util.Scanner;

public class MeniuDoctor {

    private static final Scanner sc = new Scanner(System.in);
    private static final ProgramareService programareService = new ProgramareService();

    public static void start(Doctor doctor) {
        boolean gata = false;
        while (!gata) {
            System.out.println("\n--- Meniu Doctor ---");
            System.out.println("1. Vezi programările dintr-o zi");
            System.out.println("2. Prescrie diagnostic pentru o programare");
            System.out.println("3. Vizualizează diagnostic pentru o programare");
            System.out.println("4. Adaugă tratament pentru o programare");
            System.out.println("5. Vizualizează tratamentul unei programări");
            System.out.println("0. Deconectare");
            System.out.print("Alege: ");
            String opt = sc.nextLine();

            switch (opt) {
                case "1":
                    try {
                        System.out.print("Data (yyyy-mm-dd): ");
                        LocalDate data = LocalDate.parse(sc.nextLine());
                        programareService.afiseazaProgramarileDoctoruluiInZi(doctor.getId(), data);
                    } catch (Exception e) {
                        System.out.println(" Format dată invalid. Exemplu corect: 2025-06-20");
                    }
                    break;

                case "2":
                    try {
                        System.out.println("\n--- Programările tale de azi ---");
                        LocalDate azi = LocalDate.now();
                        programareService.afiseazaProgramarileDoctoruluiInZi(doctor.getId(), azi);

                        System.out.print("ID programare: ");
                        int idProg = Integer.parseInt(sc.nextLine());

                        System.out.print("Diagnostic: ");
                        String diagText = sc.nextLine();

                        System.out.print("Tratament recomandat: ");
                        String tratament = sc.nextLine();

                        System.out.print("Recomandări speciale (CT, RMN etc.): ");
                        String recomandari = sc.nextLine();

                        new DiagnosticService().adaugaDiagnostic(idProg, diagText, tratament, recomandari);
                        System.out.println(" Diagnostic adăugat cu succes.");

                    } catch (Exception e) {
                        System.out.println(" Eroare la introducerea datelor. Verifică formatul și încearcă din nou.");
                    }
                    break;

                case "3":
                    try {
                        System.out.print("ID programare: ");
                        int idDiag = Integer.parseInt(sc.nextLine());

                        Diagnostic diag = new DiagnosticService().cautaDupaProgramare(idDiag);

                        if (diag == null) {
                            System.out.println(" Nu există diagnostic pentru această programare.");
                        } else {
                            System.out.println("\n=== Diagnostic ===");
                            System.out.println("Data: " + diag.getData());
                            System.out.println("Diagnostic: " + diag.getDiagnosticText());
                            System.out.println("Tratament: " + diag.getTratament());
                            System.out.println("Recomandări: " + diag.getRecomandari());
                        }
                    } catch (Exception e) {
                        System.out.println(" ID invalid.");
                    }
                    break;

                case "4":
                    try {
                        System.out.print("ID programare: ");
                        int idProgT = Integer.parseInt(sc.nextLine());

                        System.out.print("Medicamente prescrise: ");
                        String medicamente = sc.nextLine();

                        System.out.print("Durata tratamentului (ex: 7 zile): ");
                        String durata = sc.nextLine();

                        System.out.print("Instrucțiuni: ");
                        String instructiuni = sc.nextLine();

                        new TratamentService().adaugaTratament(idProgT, medicamente, durata, instructiuni);
                    } catch (Exception e) {
                        System.out.println(" Eroare la introducerea tratamentului.");
                    }
                    break;

                case "5":
                    try {
                        System.out.print("ID programare: ");
                        int idVerificare = Integer.parseInt(sc.nextLine());

                        Tratament t = new TratamentService().cautaDupaProgramare(idVerificare);
                        if (t == null) {
                            System.out.println(" Nu există tratament înregistrat pentru această programare.");
                        } else {
                            System.out.println("\n=== Tratament ===");
                            System.out.println("Data: " + t.getData());
                            System.out.println("Medicamente: " + t.getMedicamente());
                            System.out.println("Durata: " + t.getDurata());
                            System.out.println("Instrucțiuni: " + t.getInstructiuni());
                        }
                    } catch (Exception e) {
                        System.out.println(" ID invalid.");
                    }
                    break;

                case "0":
                    gata = true;
                    break;

                default:
                    System.out.println(" Opțiune invalidă.");
            }
        }
    }
}
