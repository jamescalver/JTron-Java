/*

	This is a modified vesrion of the simpleServer supplied in the APP examples.
	It is from and "Another" demo.

*/

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer extends Thread {

  protected ServerSocket server;
  protected JTronServer theServer;

  /*****************************************************************************/
  public SimpleServer(JTronServer callingServer, int port ) throws IOException {
	server = new ServerSocket( port );
	theServer = callingServer;
  }

  /*****************************************************************************/
  public void run() {
  	/* Listen for new clients */
  	
   	while( true ) {  /* Note infinite loop. */
	  try {
		System.out.println( "Waiting for a connection..." );

		/* SimpleServer blocks here. */
		Socket client = server.accept();  
		System.out.println( "   " + client.getInetAddress() + " connected." );

		/* H now handles this client */
		ConnectionHandler h = new ConnectionHandler(theServer, client);
		h.start();  
		
		sleep(500);

	  } catch( IOException ex ) {
		ex.printStackTrace();
	  } catch( InterruptedException ex) {
		ex.printStackTrace();
	  }
	}//End while loop
  }
}

