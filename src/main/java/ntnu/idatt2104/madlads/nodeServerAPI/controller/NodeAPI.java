package ntnu.idatt2104.madlads.nodeServerAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ntnu.idatt2104.madlads.nodeServerAPI.model.Node;
import ntnu.idatt2104.madlads.nodeServerAPI.model.NodeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
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
    public ObjectNode getAllNodes(){
        ObjectNode objectNode = mapperBuilder.createObjectNode();

        HashMap<PublicKey, Node> list = nodeList.getListOfAllNodes();
        ArrayList<PublicKey> listOfAllKeys = new ArrayList<>(list.keySet());
        for (PublicKey key: listOfAllKeys) {
            String strKey= key.toString();
            Node node = list.get(key);
            objectNode.put("publicKey", strKey );
            objectNode.put("adress", node.getAddress()+":"+node.getPort());

            //"publickey" : "adsadssadkjjfasdfs"
            //"address" : "192.168.1.232:8080"
        }
        logger.info("List of all nodes sent. Length of list: " + list.size());
        return objectNode;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value="/putNode", method = RequestMethod.POST)
    public void putNode(@RequestBody ObjectNode payload) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        byte[] bytePubkey  = Base64.getDecoder().decode(payload.get("publickey").asText());
        KeyFactory factory = KeyFactory.getInstance("ECDSA", "BC");
        PublicKey publicKey = factory.generatePublic(new X509EncodedKeySpec(bytePubkey));
        String[] splitString = payload.get("address").asText().split(":");
        nodeList.addNode(publicKey, new Node(Integer.parseInt(splitString[1]), splitString[0]));
        logger.info("Added node with address: " + splitString[1] +":"+splitString[0] +"\nand public key: " + publicKey.toString());
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value="/getAllNodes", method= RequestMethod.GET)
    public String getAddressOfSpecifiedNode(@RequestParam String payload) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        byte[] bytePubkey  = Base64.getDecoder().decode(payload);
        KeyFactory factory = KeyFactory.getInstance("ECDSA", "BC");
        PublicKey publicKey = factory.generatePublic(new X509EncodedKeySpec(bytePubkey));

        return nodeList.getAddressOfSpesifiedNode(publicKey);
    }
}