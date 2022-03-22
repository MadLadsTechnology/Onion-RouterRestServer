package ntnu.idatt2104.madlads.nodeServerAPI.model;

import java.util.ArrayList;

public class NodeList {

    private ArrayList<Node> listOfAllNodes;

    /**
     * Constructor
     */
    public NodeList() {
        listOfAllNodes = new ArrayList<>();
    }

    /**
     * Returns all nodes as an ArrayList
     * @return ArrayList
     */
    public ArrayList<Node> getListOfAllNodes() {
        return listOfAllNodes;
    }

    /**
     * Checks if a node already exists and adds it if it doesn't
     * @param node Node object
     * @return Boolean
     */

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

    /**
     * Checks if node exists and removes it if it does
     * @param node Node
     * @return Boolean
     */

    public boolean removeNode(Node node){
        if (listOfAllNodes.contains(node)) {
            listOfAllNodes.remove(node);
            return true;
        }else return false;
    }

    public void removeAllNodes(){
        listOfAllNodes = new ArrayList<>();
    }
}