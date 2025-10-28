package Client.Controller;

import Client.ClientConnection;
import common.Request;
import common.Response;

public class AuthController {
    private final ClientConnection connection;

    public AuthController(ClientConnection connection) {
        this.connection = connection;
    }

    public Response register(String username, String password, String email) {
        Request req = new Request("register");
        req.put("username", username);
        req.put("password", password);
        req.put("email", email);
        return connection.sendRequest(req);
    }

    public Response login(String username, String password) {
        Request req = new Request("login");
        req.put("username", username);
        req.put("password", password);
        return connection.sendRequest(req);
    }

    public Response logout(String username) {
        Request req = new Request("logout");
        req.put("username", username);
        return connection.sendRequest(req);
    }
}
