package ntnu.idatt2104.madlads.nodeServerAPI.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import ntnu.idatt2104.madlads.nodeServerAPI.model.Node;
import ntnu.idatt2104.madlads.nodeServerAPI.model.NodeList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class NodeAPI {
    Logger logger = LoggerFactory.getLogger(NodeAPI.class);
    NodeList nodeList= new NodeList();

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value="/getAllNodes", method= RequestMethod.GET)
    public String getAllNodes() throws JSONException {

        ArrayList<Node> list = nodeList.getListOfAllNodes();

        JSONArray array = new JSONArray();

        for (Node node: list) {

            JSONObject jo = new JSONObject();
            jo.put("port", node.getPort());
            jo.put("ip", node.getAddress());

            array.put(jo);
        }
        JSONObject mainObj = new JSONObject();
        mainObj.put("nodes", array);

        logger.info("List of all nodes sent. Length of list: " + list.size());
        return mainObj.toString();
    }

    @PostMapping ("/putNode")
    public boolean putNode(@RequestBody ObjectNode payload) {

        Node node = new Node(payload.get("address").asText());

        if (nodeList.addNode(node)){

            logger.info("Added node with address: " + node);
            return true;
        }else{
            logger.info("Node with address" + node +"Already exists");
            return false;
        }
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value="/deleteNode", method=RequestMethod.DELETE)
    public boolean deleteNode(@RequestBody ObjectNode payload){

        Node node = new Node(payload.get("address").asText());

        if(nodeList.removeNode(node)){
            logger.info("Removed node with publicKey: " + node);
            return true;
        }else{
            logger.info("Specified node does not exist: " + node);
            return false;
        }
    }
}