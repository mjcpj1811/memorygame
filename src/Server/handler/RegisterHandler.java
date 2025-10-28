package Server.handler;

import common.Request;
import common.Response;
import Server.DAO.UserDAO;
import Server.model.User;
import java.util.UUID;

public class RegisterHandler implements RequestHandler {

    private final UserDAO userDAO = new UserDAO();


    @Override
    public Response handle(Request req) {
        String username = (String) req.get("username");
        String password = (String) req.get("password");
        String email = (String) req.get("email");

        User user = new User(UUID.randomUUID().toString(), username, password, email, "OFFLINE", null);
        boolean ok = userDAO.registerUser(user);
        return new Response(ok, ok ? "Đăng ký thành công" : "Đăng ký thất bại");
    }
}
