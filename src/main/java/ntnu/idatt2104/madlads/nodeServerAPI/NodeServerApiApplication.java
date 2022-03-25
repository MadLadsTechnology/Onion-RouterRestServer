package ntnu.idatt2104.madlads.nodeServerAPI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class NodeServerApiApplication {

	public static void main(String[] args) throws UnknownHostException {
		Logger logger = LoggerFactory.getLogger(NodeServerApiApplication.class);
		SpringApplication.run(NodeServerApiApplication.class, args);
		logger.info("Address of this server: "+ InetAddress.getLocalHost());
	}
}