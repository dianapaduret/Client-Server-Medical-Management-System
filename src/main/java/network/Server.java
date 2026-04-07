package network;

import model.Doctor;
import model.Pacient;
import service.AutentificareService;
import service.DiagnosticService;
import service.ProgramareService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;

public class Server {

    public static void main(String[] args) {
        final int PORT = 12345;
        AutentificareService authService = new AutentificareService();
        ProgramareService programareService = new ProgramareService();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println(" Server activ pe portul " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println(" Client conectat!");

                new Thread(() -> {
                    try (
                            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
                    ) {
                        out.println("Comenzi disponibile:");
                        out.println(" login|email|parola");
                        out.println(" lista|pacient_id");
                        out.println(" programare|pacient_id|doctor_id|yyyy-mm-dd|HH:mm|observatii");
                        out.println(" diagnostic|programare_id|text|tratament|recomandari");

                        String linie;
                        while ((linie = in.readLine()) != null) {
                            String[] comenzi = linie.split("\\|");

                            switch (comenzi[0]) {
                                case "login":
                                    String email = comenzi[1];
                                    String parola = comenzi[2];
                                    Object utilizator = authService.autentificare(email, parola);
                                    if (utilizator instanceof Pacient p) {
                                        out.println(" Autentificat ca pacient: " + p.getNume() + " ID: " + p.getId());
                                    } else if (utilizator instanceof Doctor d) {
                                        out.println(" Autentificat ca doctor: " + d.getNume());
                                    } else {
                                        out.println(" Login eșuat.");
                                    }
                                    break;

                                case "lista":
                                    try {
                                        int idPacientLista = Integer.parseInt(comenzi[1]);
                                        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                                        PrintStream temp = new PrintStream(buffer);
                                        System.setOut(temp);
                                        programareService.afiseazaProgramarilePacientului(idPacientLista);
                                        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
                                        out.println(buffer.toString());
                                    } catch (Exception e) {
                                        out.println(" Eroare la afișare programări: " + e.getMessage());
                                    }
                                    break;

                                case "programare":
                                    try {
                                        int pid = Integer.parseInt(comenzi[1]);
                                        int did = Integer.parseInt(comenzi[2]);
                                        LocalDate data = LocalDate.parse(comenzi[3]);
                                        LocalTime ora = LocalTime.parse(comenzi[4]);
                                        String observatii = comenzi[5];

                                        programareService.adaugaProgramare(pid, did, data, ora, observatii);
                                        out.println(" Programare adăugată cu succes!");
                                    } catch (Exception e) {
                                        out.println(" Eroare la adăugarea programării: " + e.getMessage());
                                    }
                                    break;

                                case "diagnostic":
                                    try {
                                        int progId = Integer.parseInt(comenzi[1]);
                                        String diagText = comenzi[2];
                                        String tratament = comenzi[3];
                                        String recomandari = comenzi[4];

                                        new DiagnosticService().adaugaDiagnostic(progId, diagText, tratament, recomandari);
                                        out.println(" Diagnostic înregistrat!");
                                    } catch (Exception e) {
                                        out.println(" Eroare la adăugarea diagnosticului: " + e.getMessage());
                                    }
                                    break;

                                default:
                                    out.println(" Comandă necunoscută.");
                            }
                        }

                    } catch (Exception e) {
                        System.out.println(" Eroare client: " + e.getMessage());
                    }
                }).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
