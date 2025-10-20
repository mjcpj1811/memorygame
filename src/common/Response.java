package common;

import java.io.Serializable;
import java.util.HashMap;

public class Response implements Serializable {
    private boolean success;
    private String message;
    private HashMap<String, Object> data = new HashMap<>();

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void put(String key, Object value) {
        data.put(key, value);
    }

    public Object get(String key) {
        return data.get(key);
    }
}

