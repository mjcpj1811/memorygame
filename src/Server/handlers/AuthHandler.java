package Server.handlers;

import Server.ClientHandler;
import Server.DAO.UserDAO;
import Server.model.User;
import common.Request;
import common.Response;

import java.util.UUID;

public class AuthHandler {
    private final ClientHandler client;
    private final UserDAO userDAO = new UserDAO();

    public AuthHandler(ClientHandler client) {
        this.client = client;
    }

    public Response handle(Request req) {
        switch (req.getAction()) {
            case "auth.register" -> {
                String username = (String) req.get("username");
                String password = (String) req.get("password");
                String email = (String) req.get("email");

                User user = new User(UUID.randomUUID().toString(), username, password, email, "OFFLINE", null);
                boolean ok = userDAO.registerUser(user);
                return new Response(ok, ok ? "Đăng ký thành công" : "Đăng ký thất bại");
            }

            case "auth.login" -> {
                String username = (String) req.get("username");
                String password = (String) req.get("password");
                User user = userDAO.checkUser(username, password);
                if (user != null) {
                    client.setCurrentUsername(username);
                    Response res = new Response(true, "Đăng nhập thành công");
                    res.put("user", user);
                    return res;
                }
                return new Response(false, "Sai tên đăng nhập hoặc mật khẩu");
            }

            case "auth.logout" -> {
                String username = client.getCurrentUsername();
                if (username != null) {
                    userDAO.setOffline(username);
                    client.setCurrentUsername(null);
                    return new Response(true, "Đăng xuất thành công");
                }
                return new Response(false, "Chưa đăng nhập");
            }

            default -> {
                return new Response(false, "Yêu cầu không hợp lệ trong AuthHandler");
            }
        }
    }

    public void forceLogout(String username) {
        userDAO.setOffline(username);
    }
}
