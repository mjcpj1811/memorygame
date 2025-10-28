package Server;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ServerMain {
    private static final int PORT = 1234;
    private static ExecutorService pool = Executors.newFixedThreadPool(20);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());
                pool.execute(new ClientHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
