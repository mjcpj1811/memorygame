package Client.Controller;

import Client.ClientConnection;
import common.Request;
import common.Response;

public class HistoryController {
    private final ClientConnection connection;

    public HistoryController(ClientConnection connection) {
        this.connection = connection;
    }

    public Response getMatchHistory() {
        Request req = new Request("history.get_history");
        return connection.sendRequest(req);
    }
}

