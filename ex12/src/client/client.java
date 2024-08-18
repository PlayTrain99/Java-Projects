package client;

import java.io.*;
import java.net.*;

public class client {
    public static void main(String[] args) throws IOException {
        String hostName = "127.0.0.1"; // Adres IP serwera
        int portNumber = 1234; // Port, na którym nasłuchuje serwer

        try (Socket echoSocket = new Socket(hostName, portNumber);
             PrintWriter out = new PrintWriter(new OutputStreamWriter(echoSocket.getOutputStream(), "UTF-8"), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream(), "UTF-8"));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in, "UTF-8"))) {

            System.out.println("Połączono z serwerem. Wpisz tekst do wysłania lub 'quit' aby zakończyć:");
            String userInput;
            do {
                userInput = stdIn.readLine();
                out.println(userInput);
                if (!userInput.equals("quit")) {
                    System.out.println("Odpowiedź serwera: " + in.readLine());
                }
            } while (!userInput.equals("quit"));

            System.out.println("Klient zakończył działanie.");

        } catch (UnknownHostException e) {
            System.out.println("Nieznany host: " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Nie można uzyskać I/O dla połączenia do " + hostName);
            System.exit(1);
        }
    }
}
