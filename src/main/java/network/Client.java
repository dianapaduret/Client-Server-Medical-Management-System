package network;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PORT = 12345;

        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner sc = new Scanner(System.in)) {

            System.out.println("Conectat la server.");
            System.out.println("Comenzi disponibile:");
            System.out.println(" login|email|parola");
            System.out.println(" lista|pacient_id");
            System.out.println(" programare|pacient_id|doctor_id|yyyy-mm-dd|HH:mm|observatii");
            System.out.println(" diagnostic|programare_id|text|tratament|recomandari");
            System.out.println(in.readLine());

            while (true) {
                System.out.print(">>> ");
                String comanda = sc.nextLine();
                if (comanda.equalsIgnoreCase("exit")) {
                    System.out.println("Închidere client.");
                    break;
                }
                out.println(comanda);

                String raspuns;
                while ((raspuns = in.readLine()) != null) {
                    System.out.println(raspuns);
                    if (!in.ready()) break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
