package Client.Controller;

import Client.ClientConnection;
import common.Request;
import common.Response;

import java.util.List;
import java.util.Map;

public class HistoryController {
    private final ClientConnection connection;

    public HistoryController(ClientConnection connection) {
        this.connection = connection;
    }

    public Response getHistory() {
        Request req = new Request("getHistory");
        return connection.sendRequest(req);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getHistoryList(Response response) {
        Object data = response.get("historyList");
        if (data instanceof List) {
            return (List<Map<String, Object>>) data;
        }
        return null;
    }
}