package Client;

import common.Request;
import common.Response;

import java.io.*;
import java.net.*;

public class ClientConnection {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ClientConnection(String host, int port)  {
        try{
            socket = new Socket(host, port);
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Cannot connect to server: " + e.getMessage());
        }
    }

    public synchronized Response sendRequest(Request req){
        try {
            output.writeObject(req);
            output.flush();
            Object o = input.readObject();
            if (o instanceof Response) return (Response) o;
            else return new Response(false, "Invalid response from server");
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, "Connection error: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (output != null) output.close();
            if (input != null) input.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException ignored) {}
    }
}
