package ntnu.idatt2104.madlads.nodeServerAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ntnu.idatt2104.madlads.nodeServerAPI.controller.NodeAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NodeServerApiApplicationTests {

	@Autowired
	NodeAPI nodeApi;

	@Test
	public void putNodeTest() throws Exception {

		ObjectNode node = new ObjectMapper().readValue("{ \"address\" : \"localhost:1234\"}", ObjectNode.class);

		Assertions.assertTrue(nodeApi.putNode(node));
	}
	@Test
	public void deleteNodeTest() throws Exception {

		ObjectNode node = new ObjectMapper().readValue("{ \"address\" : \"localhost:1234\"}", ObjectNode.class);
		nodeApi.putNode(node);

		Assertions.assertTrue(nodeApi.deleteNode(node));
	}

	@Test
	public void getAllNodesTest() throws Exception {

		ObjectNode node1 = new ObjectMapper().readValue("{ \"address\" : \"localhost:1234\"}", ObjectNode.class);
		ObjectNode node2 = new ObjectMapper().readValue("{ \"address\" : \"localhost:2345\"}", ObjectNode.class);

		nodeApi.putNode(node1);
		nodeApi.putNode(node2);

		Assertions.assertEquals(nodeApi.getAllNodes(), "{\"nodes\":[{\"port\":1234,\"ip\":\"localhost\"},{\"port\":2345,\"ip\":\"localhost\"}]}");
	}
}