package ntnu.idatt2104.madlads.nodeServerAPI.model;

import java.security.PublicKey;

public class Node {
    private int port;
    private String address;


    public Node(int port, String address) {
        this.port = port;
        this.address = address;
    }


    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }


    @Override
    public String toString() {
        return "{" + "\"port:\"" + port + "\", \"address:\"" + address + "\"}";
    }
}
