package client;

import java.io.*;
import java.net.*;

public class client {
    public static void main(String[] args) throws IOException {
        String hostName = "127.0.0.1"; // Adres serwera
        int portNumber = 1234; // Port, na który serwer nasłuchuje
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(10000); // Ustawia timeout na 10 sekund

            String message = "Hello UDP Server\n";
            byte[] buf = message.getBytes();
            InetAddress address = InetAddress.getByName(hostName);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, portNumber);

            System.out.println("Wysyłanie do " + hostName + " na porcie " + portNumber + ": " + message);
            socket.send(packet); // Wysyła datagram

            // Odbiera odpowiedź (opcjonalnie)
            packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Odpowiedź serwera: " + received);
            } catch (SocketTimeoutException e) {
                System.out.println("Timeout: Brak odpowiedzi od serwera");
            }

            socket.close();
            System.out.println("Klient UDP zakończył działanie.");
        } catch (IOException e) {
            System.out.println("Błąd klienta: " + e.getMessage());
        }
    }
}
