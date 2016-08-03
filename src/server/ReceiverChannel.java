package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ReceiverChannel {
    
	public ReceiverChannel() {
		System.out.println("Receiver start");
		
		SocketChannel sChannel;
		try {
			sChannel = SocketChannel.open();
			sChannel.configureBlocking(true);
	        if (sChannel.connect(new InetSocketAddress("localhost", ServerChannel.PORT))) {
	            ObjectInputStream ois = 
	                     new ObjectInputStream(sChannel.socket().getInputStream());

	            String s = (String)ois.readObject();
	            System.out.println("String is: '" + s + "'");
	        }
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("Receiver end");
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
        new ReceiverChannel();
    }
}