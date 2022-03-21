package ntnu.idatt2104.madlads.nodeServerAPI.model;

import java.util.Objects;

public class Node {
    private final int port;
    private final String address;


    public Node(int port, String address) {
        this.port = port;
        this.address = address;
    }

    public Node (String payload){
        String[] splitString = payload.split(":");
        this.port= Integer.parseInt(splitString[1]);
        this.address=splitString[0];
    }


    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return port == node.port && Objects.equals(address, node.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(port, address);
    }

    @Override
    public String toString() {
        return address + ":" + port;
    }
}
