import model.Doctor;
import model.Pacient;
import service.AutentificareService;
import ui.MeniuDoctor;
import ui.MeniuPacient;
import util.ValidatorUtil;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AutentificareService auth = new AutentificareService();

        while (true) {
            System.out.println("\n=== Meniu Principal ===");
            System.out.println("1. Înregistrare pacient");
            System.out.println("2. Înregistrare doctor");
            System.out.println("3. Login");
            System.out.println("0. Ieșire");
            System.out.print("Alege: ");
            String opt = sc.nextLine();

            switch (opt) {
                case "1":
                    System.out.print("Nume: ");
                    String numeP = sc.nextLine();
                    String emailP;
                    while (true) {
                        System.out.print("Email: ");
                        emailP = sc.nextLine();
                        if (!ValidatorUtil.esteEmailValid(emailP)) {
                            System.out.println(" Email invalid. Formatul trebuie să fie de tipul nume@domeniu.com");
                        } else {
                            break;
                        }
                    }
                    String parolaP;
                    while (true) {
                        System.out.print("Parola: ");
                        parolaP = sc.nextLine();
                        System.out.print("Confirmare parola: ");
                        String confirmareP = sc.nextLine();

                        if (!parolaP.equals(confirmareP)) {
                            System.out.println(" Parolele nu coincid. Încearcă din nou.");
                            continue;
                        }
                        if (!ValidatorUtil.esteParolaValida(parolaP)) {
                            System.out.println(" Parola trebuie să aibă minim 6 caractere, o literă mare și o cifră.");
                            continue;
                        }
                        break;
                    }
                    System.out.print("Alergii: ");
                    String alergii = sc.nextLine();
                    System.out.print("Afecțiuni cronice: ");
                    String afectiuni = sc.nextLine();
                    System.out.print("Medicație curentă: ");
                    String medicatie = sc.nextLine();
                    System.out.print("Istoric familial: ");
                    String istoric = sc.nextLine();
                    auth.inregistreazaPacient(numeP, emailP, parolaP, alergii, afectiuni, medicatie, istoric);
                    break;

                case "2":
                    System.out.print("Nume: ");
                    String numeD = sc.nextLine();
                    String emailD;
                    while (true) {
                        System.out.print("Email: ");
                        emailD = sc.nextLine();
                        if (!ValidatorUtil.esteEmailValid(emailD)) {
                            System.out.println(" Email invalid. Formatul trebuie să fie de tipul nume@domeniu.com");
                        } else {
                            break;
                        }
                    }

                    String parolaD;
                    while (true) {
                        System.out.print("Parola: ");
                        parolaD = sc.nextLine();
                        System.out.print("Confirmare parola: ");
                        String confirmareD = sc.nextLine();

                        if (!parolaD.equals(confirmareD)) {
                            System.out.println(" Parolele nu coincid. Încearcă din nou.");
                            continue;
                        }
                        if (!ValidatorUtil.esteParolaValida(parolaD)) {
                            System.out.println(" Parola trebuie să aibă minim 6 caractere, o literă mare și o cifră.");
                            continue;
                        }
                        break;
                    }
                    System.out.print("Specializare: ");
                    String specializare = sc.nextLine();
                    auth.inregistreazaDoctor(numeD, emailD, parolaD, specializare);
                    break;

                case "3":
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Parola: ");
                    String parola = sc.nextLine();
                    Object utilizator = auth.autentificare(email, parola);

                    if (utilizator instanceof Pacient pacient) {
                        MeniuPacient.start(pacient);
                    } else if (utilizator instanceof Doctor doctor) {
                        MeniuDoctor.start(doctor);
                    } else {
                        System.out.println("Autentificare eșuată. Verifică emailul și parola.");
                    }
                    break;

                case "0":
                    System.out.println("La revedere!");
                    return;

                default:
                    System.out.println("Opțiune invalidă.");
            }
        }
    }
}
