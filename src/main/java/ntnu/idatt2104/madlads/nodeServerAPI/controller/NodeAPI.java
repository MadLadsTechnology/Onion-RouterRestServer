package ntnu.idatt2104.madlads.nodeServerAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ntnu.idatt2104.madlads.nodeServerAPI.model.Node;
import ntnu.idatt2104.madlads.nodeServerAPI.model.NodeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class NodeAPI {
    Logger logger = LoggerFactory.getLogger(NodeAPI.class);
    NodeList nodeList= new NodeList();

    @Autowired
    private ObjectMapper mapperBuilder;

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value="/getAllNodes", method= RequestMethod.GET)
    public ArrayNode getAllNodes(){
        ObjectNode objectNode = mapperBuilder.createObjectNode();

        HashMap<String, Node> list = nodeList.getListOfAllNodes();
        ArrayList<String> listOfAllKeys = new ArrayList<>(list.keySet());
        ArrayNode array = objectNode.putArray("list");
        for (String key: listOfAllKeys) {
            JsonNode jsonNode = mapperBuilder.valueToTree(list.get(key));
            array.add(jsonNode);

            //"publickey" : "adsadssadkjjfasdfs"
            //"address" : "192.168.1.232:8080"
        }
        logger.info("List of all nodes sent. Length of list: " + list.size());
        return array;
    }

    @PostMapping ("/putNode")
    public void putNode(@RequestBody ObjectNode payload) {

        String pubKey  = payload.get("publicKey").asText();

        String[] splitString = payload.get("address").asText().split(":");

        nodeList.addNode(pubKey, new Node(Integer.parseInt(splitString[1]), splitString[0]));

        logger.info("Added node with address: " + splitString[1] +":"+splitString[0] +"\nand public key: " + pubKey.toString());
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value="/getAddress", method= RequestMethod.GET)
    public String getAddressOfSpecifiedNode(@RequestParam String payload){
        return nodeList.getAddressOfSpecifiedNode(payload);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value="/deleteNode", method=RequestMethod.DELETE)
    public void deleteNode(@RequestParam String payload){
        if(nodeList.removeNode(payload)){
            logger.info("Removed node with publicKey: " + payload);
        }else{
            logger.info("Spesified node does not exist: " + payload);
        }
    }


}