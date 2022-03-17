package ntnu.idatt2104.madlads.nodeServerAPI.model;

import java.security.PublicKey;
import java.util.*;

public class NodeList {

    private HashMap<String, Node> listOfAllNodes;

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
        if (listOfAllNodes.get(publicKey) ==null) {
            listOfAllNodes.put(publicKey, node);
            return true;
        }else return false;
    }

    public boolean removeNode(String publicKey){
        if (listOfAllNodes.get(publicKey) !=null) {
            listOfAllNodes.remove(publicKey);
            return true;
        }else return false;
    }
/*
    //TODO: change publicKey to string
    public Map<PublicKey, Node> getCirciut(int maxAmountOfNodes){
        Map<PublicKey,Node> nodesToBeSent= new HashMap<>();
        if (listOfAllNodes.size()>maxAmountOfNodes){
            throw new IllegalArgumentException("Not enough nodes present to fulfill request");
        }else{
            Random rand = new Random();
            int min = 3;
            int numberOfNodes = (int)Math.floor(Math.random()*(maxAmountOfNodes-min+1)+min);
            ArrayList<Node> nodes = (ArrayList<Node>) listOfAllNodes.values();
            ArrayList<PublicKey> listOfAllKeys = new ArrayList<>(listOfAllNodes.keySet());

            for (int successfullAdds = 0; successfullAdds < numberOfNodes;) {
                int randomInt = (int)Math.floor(Math.random()*(listOfAllNodes.size()+1));
                PublicKey key;
                if ((key = listOfAllKeys.get(randomInt)) != null){
                    successfullAdds+=1;
                    nodesToBeSent.put(key,listOfAllNodes.get(key));
                }
            }
        }
        return nodesToBeSent;
    }*/
}