package ntnu.idatt2104.madlads.nodeServerAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ntnu.idatt2104.madlads.nodeServerAPI.model.Node;
import ntnu.idatt2104.madlads.nodeServerAPI.model.NodeList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
    public String getAllNodes() throws JSONException {

        HashMap<String, Node> list = nodeList.getListOfAllNodes();
        ArrayList<String> listOfAllKeys = new ArrayList<>(list.keySet());

        JSONArray array = new JSONArray();

        for (String key: listOfAllKeys) {
            Node node = list.get(key);

            JSONObject jo = new JSONObject();
            jo.put("port", node.getPort());
            jo.put("ip", node.getAddress());
            jo.put("publicKey", key);

            array.put(jo);

        }
        JSONObject mainObj = new JSONObject();
        mainObj.put("nodes", array);

        logger.info("List of all nodes sent. Length of list: " + list.size());
        return mainObj.toString();
    }

    @PostMapping ("/putNode")
    public void putNode(@RequestBody ObjectNode payload) {

        String pubKey  = payload.get("publicKey").asText();

        String[] splitString = payload.get("address").asText().split(":");

        nodeList.addNode(pubKey, new Node(Integer.parseInt(splitString[1]), splitString[0]));

        logger.info("Added node with address: " + splitString[1] +":"+splitString[0] +"\nand public key: " + pubKey);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping(value="/getAddress")
    public String getAddressOfSpecifiedNode(@RequestParam String payload){
        String address = nodeList.getAddressOfSpecifiedNode(payload);
        if(address.equals("")){
            logger.info("the given request could not be found");
        }else {
            logger.info("Address returned: " + address);
        }
        return address;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value="/deleteNode", method=RequestMethod.DELETE)
    public void deleteNode(@RequestParam String payload){
        if(nodeList.removeNode(payload)){
            logger.info("Removed node with publicKey: " + payload);
        }else{
            logger.info("Specified node does not exist: " + payload);
        }
    }


}