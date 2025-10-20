package common;

import java.io.Serializable;
import java.util.HashMap;

public class Request implements Serializable {
    private String action;
    private HashMap<String, Object> data = new HashMap<>();

    public Request(String action) {
        this.action = action;
    }






























    public String getAction() {
        return action;
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
