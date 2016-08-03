package server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.channels.*;

public class ServerChannel {

	ServerSocketChannel ssChannel;
	public final static int PORT = 117;

	private ArrayList<ClientSocket> connectedClients = new ArrayList<>();
	private Thread clientAcceptanceThread;
	
	public ServerChannel() {
		System.out.println("Sender start");
		
		try {
			ssChannel = ServerSocketChannel.open();
			ssChannel.configureBlocking(true);
			ssChannel.socket().bind(new InetSocketAddress(PORT));
			
			String obj ="testtext";
	        while (true) {
	            SocketChannel sChannel = ssChannel.accept();

	            ObjectOutputStream  oos = new 
	                      ObjectOutputStream(sChannel.socket().getOutputStream());
	            oos.writeObject(obj);
	            oos.close();

	            System.out.println("Connection ended");
	        }
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new ServerChannel();
	}
	
}
