package Server.handler;

import common.Request;
import common.Response;

public interface RequestHandler {
    Response handle(Request req);
}
