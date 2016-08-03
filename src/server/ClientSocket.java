package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSocket implements ClientInterface {

	private Socket client;
	private DataInputStream fromClient;
	private DataOutputStream toClient;
	private String userName;
	
	ClientSocket(Socket socket) throws IOException {
		client = socket;
		fromClient = new DataInputStream(socket.getInputStream());
		toClient = new DataOutputStream(socket.getOutputStream());
		
		client.setSoTimeout(50);
	}
	
	public String receive() throws IOException {
		return fromClient.readUTF();
	}
	
	public String getName() {
		return userName;
	}
	
	public void send(String str) throws IOException {
		toClient.writeUTF(str);
	}
}
