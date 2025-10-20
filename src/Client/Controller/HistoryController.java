package Client.Controller;

import Server.DAO.MatchDAO;
import Server.model.MatchDetail;
import common.Response;

import java.util.List;

public class HistoryController {
    private final MatchDAO matchDAO;
    
    public HistoryController() {
        this.matchDAO = new MatchDAO();
    }
    
    public Response getMatchOverviews() {
        try {
            java.util.List<Object[]> list = matchDAO.getMatchOverviews();
            Response response = new Response(true, "Lấy danh sách trận đấu thành công");
            response.put("overviews", list);
            return response;
        } catch (Exception e) {
            return new Response(false, "Lỗi khi lấy danh sách trận đấu: " + e.getMessage());
        }
    }

    // Các API cũ không còn dùng đã được loại bỏ để đơn giản hóa controller
}
