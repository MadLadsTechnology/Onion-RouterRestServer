package ntnu.idatt2104.madlads.nodeServerAPI.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class NodeList {

    private ArrayList<Node> listOfAllNodes;
    Logger logger = LoggerFactory.getLogger(NodeList.class);

    public NodeList(ArrayList<Node> listOfAllNodes) {
        this.listOfAllNodes = listOfAllNodes;
    }

    public NodeList() {
        listOfAllNodes = new ArrayList<>();
    }

    public ArrayList<Node> getListOfAllNodes() {
        return listOfAllNodes;
    }

    public boolean addNode(Node node){
        if (listOfAllNodes.size()==0){
            listOfAllNodes.add(node);
            return true;
        }else {
            if (!listOfAllNodes.contains(node)){
                listOfAllNodes.add(node);
                return true;
            }else return false;
        }
    }

    public boolean removeNode(String payload){
        Node node = new Node(payload);
        if (listOfAllNodes.contains(node)) {
            listOfAllNodes.remove(node);
            return true;
        }else return false;
    }
}