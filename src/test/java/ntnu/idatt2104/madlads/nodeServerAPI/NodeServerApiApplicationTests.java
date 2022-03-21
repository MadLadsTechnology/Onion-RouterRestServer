package ntnu.idatt2104.madlads.nodeServerAPI;

import ntnu.idatt2104.madlads.nodeServerAPI.model.Node;
import ntnu.idatt2104.madlads.nodeServerAPI.model.NodeList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@SpringBootTest
class NodeServerApiApplicationTests {
	String JSON_DATA;
	NodeList nodes = new NodeList();
	@BeforeEach
	public void startup() {
	}

	@Test
	public void postNodeCheck() throws Exception {

		apiPOSTNode("http://localhost:8080/api/putNode", "YOU WERE THE CHOSEN ONE! It was said that you would destroy the Sith, not join them, bring balance to the force, not leave it in darkness", "localhost:" + 1234);
		apiPOSTNode("http://localhost:8080/api/putNode", "Don't lecture me, Obi-Wan. I see through the lies of the Jedi! I do not fear the dark side as you do. I have brought peace, freedom, justice, and security to my new empire!", "localhost:" + 12378);
		apiPOSTNode("http://localhost:8080/api/putNode", "Don't lecture me, Obi-Wan. I see through the lies of the Jedi! I do not fear the dark side as you do. I have brought peace, freedom, justice, and security to my new empire!", "localhost:" + 12379);

		System.out.println("Postet noder");
		JSON_DATA = apiGETRequest("http://localhost:8080/api/getAllNodes");

		fillNodes();
		ArrayList<Node> listOfAllNodes = nodes.getListOfAllNodes();
		Assertions.assertEquals(2, listOfAllNodes.size());

	}

	public static void apiPOSTNode(String url, String publicKey, String address) throws Exception {

		URL urlPost = new URL(url);
		HttpURLConnection http = (HttpURLConnection) urlPost.openConnection();
		http.setRequestMethod("POST");
		http.setDoOutput(true);
		http.setRequestProperty("Content-Type", "application/json");

		String data = "{ \"publicKey\":\""  + publicKey +"\", \"address\":\"" + address + "\"}";

		byte[] out = data.getBytes(StandardCharsets.UTF_8);

		OutputStream stream = http.getOutputStream();
		stream.write(out);

		System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
		http.disconnect();
	}

	public static String apiGETRequest(String url) throws Exception {
		URL urlForGetRequest = new URL(url);
		return getString(urlForGetRequest);
	}

	private static String getString(URL urlForGetRequest) throws Exception {
		HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
		connection.setRequestMethod("GET");
		int responseCode = connection.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			String readLine;
			while ((readLine = in.readLine()) != null) {
				response.append(readLine);
			}
			in.close();

			return String.valueOf(response);

		} else {
			throw new Exception("Could not connect");
		}
	}

	private void fillNodes() throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject  = (JSONObject) parser.parse(JSON_DATA);

		JSONArray nodesArray = (JSONArray) jsonObject.get("nodes");

		nodesArray.forEach( node -> parseNodeObject( (JSONObject) node ));
	}

	private void parseNodeObject(JSONObject node){
		//Get node's public key
		String publicKey = (String) node.get("publicKey");
		String host = (String) node.get("host");
		System.out.println(node.get("port"));
		int port = Integer.parseInt(String.valueOf((long) node.get("port")));

		nodes.addNode(new Node(port , host));
	}
}