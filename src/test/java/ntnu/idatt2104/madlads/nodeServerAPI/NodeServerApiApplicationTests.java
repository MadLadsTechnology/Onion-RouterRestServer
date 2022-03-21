package ntnu.idatt2104.madlads.nodeServerAPI;

import ntnu.idatt2104.madlads.nodeServerAPI.model.Node;
import ntnu.idatt2104.madlads.nodeServerAPI.model.NodeList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static ntnu.idatt2104.madlads.nodeServerAPI.APIService.*;

@SpringBootTest
class NodeServerApiApplicationTests {

	String JSON_DATA;
	NodeList nodes = new NodeList();

	@Test
	public void postNodeTest() throws Exception {
		removeAllNodes("http://localhost:8080/api/deleteAllNodes");
		apiPOSTNode("http://localhost:8080/api/putNode", "localhost:" + 1234);
		apiPOSTNode("http://localhost:8080/api/putNode", "localhost:" + 12378);
		apiPOSTNode("http://localhost:8080/api/putNode", "localhost:" + 12378);
		JSON_DATA = apiGETRequest("http://localhost:8080/api/getAllNodes");
		fillNodes();
		ArrayList<Node> listOfAllNodes = nodes.getListOfAllNodes();
		Assertions.assertEquals(2, listOfAllNodes.size());
		Assertions.assertEquals("localhost:1234", listOfAllNodes.get(0).toString());
	}

	@Test
	public void deleteNodeTest() throws Exception {
		removeAllNodes("http://localhost:8080/api/deleteAllNodes");
		apiPOSTNode("http://localhost:8080/api/putNode", "localhost:" + 1234);
		apiPOSTNode("http://localhost:8080/api/putNode", "localhost:" + 12378);

		apiDELETENode("http://localhost:8080/api/deleteNode", "localhost:" + 1234);
		JSON_DATA = apiGETRequest("http://localhost:8080/api/getAllNodes");
		fillNodes();

		ArrayList<Node> listOfAllNodes = nodes.getListOfAllNodes();
		Assertions.assertEquals(1, listOfAllNodes.size());
	}

	@Test
	public void deleteAllNodes() throws Exception {
		apiPOSTNode("http://localhost:8080/api/putNode", "localhost:" + 1234);
		apiPOSTNode("http://localhost:8080/api/putNode", "localhost:" + 12378);
		removeAllNodes("http://localhost:8080/api/deleteAllNodes");
		JSON_DATA = apiGETRequest("http://localhost:8080/api/getAllNodes");
		fillNodes();

		ArrayList<Node> listOfAllNodes = nodes.getListOfAllNodes();
		Assertions.assertEquals(0, listOfAllNodes.size());
	}



	private void fillNodes() throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject  = (JSONObject) parser.parse(JSON_DATA);

		JSONArray nodesArray = (JSONArray) jsonObject.get("nodes");

		nodesArray.forEach( node -> parseNodeObject( (JSONObject) node ));
	}

	private void parseNodeObject(JSONObject node){
		//Get node's public key
		String host = (String) node.get("ip");
		int port = Integer.parseInt(String.valueOf((long) node.get("port")));;

		nodes.addNode(new Node(port, host));
	}
}