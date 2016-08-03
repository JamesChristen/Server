package server;

import java.io.IOException;

public interface ClientInterface {

	public String receive() throws IOException;
	public void send(String str) throws IOException;
	
}
