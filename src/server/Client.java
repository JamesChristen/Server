package server;

import java.io.*;
import java.net.Socket;

public class Client {

	private Socket client;
	private DataInputStream fromServer;
	private DataOutputStream toServer;
	
	private final static String HOST = "127.0.0.1";
	private final static int PORT = 117;
	
	public Client() {}

	private void runStreams() throws IOException {
		fromServer = new DataInputStream(client.getInputStream());
		toServer = new DataOutputStream(client.getOutputStream());
		
		receive();
		while(!client.isClosed()) {
			receive();
		}
	}
	
	private void receive() throws IOException {
		String message = fromServer.readUTF();
		decode(message);
	}
	
	private void decode(String message) {
		
	}
	
	private void createNewGame() {
		
	}
	
	private boolean receivedGameSetup(String message) {
		if(message.equals("Original") || message.equals("Imhotep") || message.equals("Dynasty")){
			return true;
		}
		return false;
	}
	
	private boolean isMove(String message) {
		String[] split = message.split(",");
		if(split[0].equals("ROT") || split[0].equals("MOVE")) {
			return true;
		}
		return false;
	}

	public void send(String str) {
		/*try {
			toServer.writeUTF(str);
		} catch (IOException e) {
			new ErrorMessage("Failed to send message/move...\nMessage: " + str);
		}*/
	}
}
