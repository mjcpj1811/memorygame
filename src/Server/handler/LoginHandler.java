package Server.handler;

import common.Request;
import common.Response;
import Server.DAO.UserDAO;
import Server.model.User;

public class LoginHandler implements RequestHandler {
    private final UserDAO userDAO = new UserDAO();

    @Override
    public Response handle(Request req) {
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
}
