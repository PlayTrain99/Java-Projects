package server;

import java.io.*;
import java.net.*;

public class server {
    public static void main(String[] args) throws IOException {
        int portNumber = 1234; // Port na którym serwer będzie nasłuchiwał

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Serwer nasłuchuje na porcie " + portNumber);

            Socket clientSocket = serverSocket.accept(); // Akceptuj połączenia od klientów
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
                 PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"), true)) {

                String fromClient;
                do {
                    fromClient = in.readLine();
                    if (fromClient != null) {
                        System.out.println("Odebrano: " + fromClient);
                        out.println(fromClient);
                    }
                } while (fromClient != null && !fromClient.equals("quit"));

            } catch (IOException e) {
                System.out.println("Błąd przy próbie odczytu danych: " + e.getMessage());
            } finally {
                clientSocket.close(); // Zamknij połączenie z klientem
            }
            System.out.println("Serwer zakończył działanie.");
        } catch (IOException e) {
            System.out.println("Nie można uruchomić serwera: " + e.getMessage());
        }
    }
}
