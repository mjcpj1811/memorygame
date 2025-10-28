package Server.handler;

import common.Request;
import common.Response;
import Server.DAO.MatchDAO;
import java.util.List;
import java.util.Map;

public class MatchHistoryHandler implements RequestHandler {
    private final MatchDAO matchDAO = new MatchDAO();

    @Override
    public Response handle(Request req) {
        List<Map<String, Object>> history = matchDAO.getAllMatchHistory();
        Response res = new Response(true, "Lấy lịch sử trận đấu thành công");
        res.put("history", history);
        return res;
    }
}
