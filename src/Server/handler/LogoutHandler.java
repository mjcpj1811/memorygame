package Server.handler;

import common.Request;
import common.Response;
import Server.DAO.UserDAO;

public class LogoutHandler implements RequestHandler {

    private final UserDAO userDAO = new UserDAO();

    @Override
    public Response handle(Request req) {
        String username = (String) req.get("username");

        boolean ok = userDAO.setOffline(username);
        return new Response(ok, ok ? "Đăng xuất thành công" : "Đăng xuất thất bại");
    }
}