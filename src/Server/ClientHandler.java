package Server;

import Server.handler.*;
import common.Request;
import common.Response;
import Server.DAO.UserDAO;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private String currentUsername = null;

    private final HandlerRegistry registry = new HandlerRegistry();
    private final UserDAO userDAO = new UserDAO();
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());

            while (true) {
                Request req = (Request) input.readObject();
                if ("login".equalsIgnoreCase(req.getAction())) {
                    currentUsername = (String) req.get("username");
                }

//                if ("logout".equalsIgnoreCase(req.getAction())) {
//                    String username = (String) req.get("username");
//                    userDAO.setOffline(username);
//                    break;
//                }
                Response res = handleRequest(req);
                output.writeObject(res);
                output.flush();
            }
        } catch (Exception e) {
            System.out.println("Client disconnected: " + e.getMessage());
            if (currentUsername != null) {
                userDAO.setOffline(currentUsername);
            }
        }
    }

    private Response handleRequest(Request req) {
        RequestHandler handler = registry.getHandler(req.getAction());
        if (handler != null) return handler.handle(req);
        return new Response(false, "Action không hỗ trợ");
    }
}
