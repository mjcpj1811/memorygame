package Server;

import Server.handlers.*;
import common.Request;
import common.Response;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private String currentUsername = null;

    private final AuthHandler authHandler = new AuthHandler(this);


    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    @Override
    public void run() {
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());

            while (true) {
                Request req = (Request) input.readObject();
                Response res = routeRequest(req);
                output.writeObject(res);
                output.flush();
            }
        } catch (Exception e) {
            System.out.println("Client disconnected: " + e.getMessage());
        } finally {
            if (currentUsername != null) {
                authHandler.forceLogout(currentUsername);
            }
            try { socket.close(); } catch (IOException ignored) {}
        }
    }

    private Response routeRequest(Request req) {
        String action = req.getAction();

        if (action.startsWith("auth.")) {
            return authHandler.handle(req);
        } else {
            return new Response(false, "Yêu cầu không hợp lệ: " + action);
        }
    }
}
