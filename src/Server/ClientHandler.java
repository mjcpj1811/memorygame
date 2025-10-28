package Server;

import Server.handler.*;
import common.Request;
import common.Response;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private final HandlerRegistry registry = new HandlerRegistry();

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
        }
    }

    private Response handleRequest(Request req) {
        RequestHandler handler = registry.getHandler(req.getAction());
        if (handler != null) return handler.handle(req);
        return new Response(false, "Action không hỗ trợ");
    }
}
