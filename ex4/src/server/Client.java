package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = new Socket(host, 1234);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Połączono z serwerem. Wprowadź ID docelowego klienta i wiadomość.");
        String userInput;

        while ((userInput = stdIn.readLine()) != null) {
            if ("exit".equalsIgnoreCase(userInput)) {
                break;
            }
            out.println(userInput);
        }

        socket.close();
    }
}
