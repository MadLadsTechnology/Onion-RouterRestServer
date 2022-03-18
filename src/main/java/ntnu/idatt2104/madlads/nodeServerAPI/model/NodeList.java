package ntnu.idatt2104.madlads.nodeServerAPI.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class NodeList {

    private HashMap<String, Node> listOfAllNodes;
    Logger logger = LoggerFactory.getLogger(NodeList.class);

    public NodeList(HashMap<String, Node> listOfAllNodes) {
        this.listOfAllNodes = listOfAllNodes;
    }

    public NodeList() {
        listOfAllNodes = new HashMap<>();
    }

    public HashMap<String, Node> getListOfAllNodes() {
        return listOfAllNodes;
    }

    public String getAddressOfSpecifiedNode(String key){
        Node node;
        if ((node = listOfAllNodes.get(key)) != null){
            return node.getAddress()+":"+node.getPort();
        }return null;
    }

    public boolean addNode(String publicKey, Node node){
        if (listOfAllNodes.size()==0){
            listOfAllNodes.put(publicKey, node);
            return true;
        }else {
            if (Objects.equals(listOfAllNodes.get(publicKey), null)){
                listOfAllNodes.put(publicKey, node);
                return true;
            }else return false;
        }
    }

    public boolean removeNode(String publicKey){
        if (listOfAllNodes.get(publicKey) !=null) {
            listOfAllNodes.remove(publicKey);
            return true;
        }else return false;
    }
}