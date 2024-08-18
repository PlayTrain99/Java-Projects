package server;

import java.io.*;
import java.net.*;

public class server {
    public static void main(String[] args) throws IOException {
        int portNumber = 1234; // Port nasłuchu
        try (DatagramSocket socket = new DatagramSocket(portNumber)) {
            byte[] receiveBuffer = new byte[256];

            System.out.println("Serwer UDP nasłuchuje na porcie " + portNumber);
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket); // Odbiera datagram

            String receivedData = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Otrzymano: " + receivedData);

            socket.close();
            System.out.println("Serwer UDP zakończył działanie.");
        } catch (IOException e) {
            System.out.println("Błąd serwera: " + e.getMessage());
        }
    }
}

