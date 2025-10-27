package Server;

import common.Request;
import common.Response;
import Server.DAO.UserDAO;
import Server.DAO.MatchDAO;
import Server.model.User;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private UserDAO userDAO = new UserDAO();
    private MatchDAO matchDAO = new MatchDAO();

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
                Response res = handleRequest(req);
                output.writeObject(res);
                output.flush();
            }
        } catch (Exception e) {
            System.out.println("Client disconnected: " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
        }
    }

    private Response handleRequest(Request req) {
        switch (req.getAction()) {
            case "register" -> {
                String username = (String) req.get("username");
                String password = (String) req.get("password");
                String email = (String) req.get("email");

                User user = new User(UUID.randomUUID().toString(), username, password, email, "OFFLINE", null);
                boolean ok = userDAO.registerUser(user);
                return new Response(ok, ok ? "Đăng ký thành công" : "Đăng ký thất bại");
            }

            case "login" -> {
                String username = (String) req.get("username");
                String password = (String) req.get("password");
                User user = userDAO.checkUser(username, password);
                if (user != null) {
                    Response res = new Response(true, "Đăng nhập thành công");
                    res.put("user", user);
                    return res;
                }
                return new Response(false, "Sai tên đăng nhập hoặc mật khẩu");
            }

            case "getHistory" -> {
                try {
                    List<Map<String, Object>> historyList = matchDAO.getAllMatchHistory();
                    Response res = new Response(true, "Lấy lịch sử thành công");
                    res.put("historyList", historyList);
                    return res;
                } catch (Exception e) {
                    return new Response(false, "Không thể lấy lịch sử: " + e.getMessage());
                }
            }

            default -> { return new Response(false, "Yêu cầu không hợp lệ"); }
        }
    }
}
