package Server.handler;


import java.util.HashMap;
import java.util.Map;

public class HandlerRegistry {

    private final Map<String, RequestHandler> handlers = new HashMap<>();

    public HandlerRegistry() {
        register("login", new LoginHandler());
        register("register", new RegisterHandler());
        register("logout", new LogoutHandler());
        register("getHistory", new MatchHistoryHandler());

    }

    public void register(String action, RequestHandler handler) {
        handlers.put(action, handler);
    }

    public RequestHandler getHandler(String action) {
        return handlers.get(action);
    }
}
