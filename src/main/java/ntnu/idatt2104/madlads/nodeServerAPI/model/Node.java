package ntnu.idatt2104.madlads.nodeServerAPI.model;

import java.util.Objects;

public class Node {
    private final int port;
    private final String address;

    /**
     * Constructor
     * @param port Port
     * @param address Address
     */


    public Node(int port, String address) {
        this.port = port;
        this.address = address;
    }

    /**
     * Constructor that automatically parses a payload
     * @param payload address and port combined as String
     */

    public Node (String payload){
        String[] splitString = payload.split(":");
        this.port= Integer.parseInt(splitString[1]);
        this.address=splitString[0];
    }

    /**
     * Returns port number
     * @return port number
     */
    public int getPort() {
        return port;
    }

    /**
     * Return address(ip)
     * @return Ip address
     */

    public String getAddress() {
        return address;
    }

    /**
     * Equals method
     * @param o Object to equals
     * @return boolean
     */

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

    /**
     * To string method of this object
     * @return ip Adress and port
     */

    @Override
    public String toString() {
        return address + ":" + port;
    }
}
