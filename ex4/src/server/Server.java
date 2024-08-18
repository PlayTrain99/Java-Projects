package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static final int PORT = 1234;
    private static ConcurrentHashMap<String, Socket> clientMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Serwer nasłuchuje na porcie " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Thread clientThread = new Thread(new ClientHandler(clientSocket));
            clientThread.start();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private String clientId;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            this.clientId = socket.getRemoteSocketAddress().toString();
            clientMap.put(clientId, clientSocket);
            System.out.println("Połączony klient: " + clientId);
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ", 2);
                    String targetId = parts[0];
                    String message = parts.length > 1 ? parts[1] : "";

                    if (clientMap.containsKey(targetId)) {
                        PrintWriter targetOut = new PrintWriter(clientMap.get(targetId).getOutputStream(), true);
                        targetOut.println("Wiadomość od " + clientId + ": " + message);
                    } else {
                        System.out.println("Nie znaleziono klienta o ID: " + targetId);
                    }
                }
            } catch (IOException e) {
                System.out.println("Błąd w połączeniu z klientem: " + clientId);
            } finally {
                try {
                    clientMap.remove(clientId);
                    clientSocket.close();
                    System.out.println("Rozłączony klient: " + clientId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
