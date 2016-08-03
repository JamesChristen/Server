package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import javax.swing.*;

public class Server {

	ServerSocket server;
	public final static String HOST = "127.0.0.1";
	public final static int PORT = 117;

	private ArrayList<ClientSocket> connectedClients = new ArrayList<>();
	private Thread clientAcceptanceThread;
	
	public Server() {
		initFrame();
		try {
			server = new ServerSocket(PORT);
			(clientAcceptanceThread = new ClientAcceptanceThread()).start();
			runServer();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private JFrame frame;
	private void initFrame() {
		frame = new JFrame("Server");
		JPanel p = new JPanel();
		JButton b = new JButton("Quit");
		b.addActionListener(e -> System.exit(0));
		p.add(b);
		frame.add(p);
		frame.setSize(250, 75);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private void runServer() throws IOException {
		while(true) {
			ArrayList<Integer> disconnectedClients = new ArrayList<>();
			for(int i = 0; i < connectedClients.size(); i++) {
				ClientSocket client = connectedClients.get(i);
				try {
					String message = client.receive();
					message = "Player " + (i + 1) + ": " + message;
					for(int j = 0; j < connectedClients.size(); j++) {
						if(i != j) connectedClients.get(j).send(message);
					}
				}
				catch (SocketException ex) {
					disconnectedClients.add(i);
				}
				catch (SocketTimeoutException ex) {}
			}
			removeDisconnectedClients(disconnectedClients);
		}
	}

	private void removeDisconnectedClients(ArrayList<Integer> clients) {
		for(int i: clients) {
			connectedClients.remove(i);
		}
	}

	private class ClientAcceptanceThread extends Thread {

		ClientAcceptanceThread () {
			super(new Runnable() {
				@Override
				public void run() {
					while(connectedClients.size() < 2) {
						try {
							ClientSocket newClient = new ClientSocket(server.accept());
							connectedClients.add(newClient);
							newClient.send("Welcome to the server!");
						} catch (IOException ex) {
							ex.printStackTrace(System.err);
						}
					}
				}
			}, "Client Acceptance Thread");
		}
	}

	public static void main(String[] args) {
		new Server();
	}
}
