/*
   One ConnectionHandler is spawned (i.e., as a new thread)
   for each connection the SimpleServer accepts.

   Modified by James Calver
   Taken from the APP example "Another"

*/

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionHandler extends Thread {

  protected Socket s;
  protected ObjectInputStream cinput = null;
  protected ObjectOutputStream coutput = null;

  public JTronServer theServer;
  public boolean keepRunning = true;

  public boolean alive = true;

  /*****************************************************************************************/
  public ConnectionHandler(JTronServer callingServer, Socket client ) throws IOException {

	/* Take the socket and create input and output streams */
	s = client;
	
	coutput = new ObjectOutputStream( s.getOutputStream() );
	cinput = new ObjectInputStream( s.getInputStream() );
	
	/* Set the pointer to the Game Server */
	theServer = callingServer;
	
	/* If the Server point is null, something is very wrong */
	if (theServer == null)
	  System.out.println("The Server point is NULL!!!");
  }
  
  /****************************************************************************************/
  public void sendMessage(Message msg) {
/* Send a message to the host that this handler is connected to */
	try {

	  /* Send Message */
	  coutput.writeObject(msg);
	  
	/* Catch any IOExceptions, and call processTerm, because the connection is down */
	} catch ( IOException e) {
	  System.out.println("lost connection 1");
	  alive = false;
	  theServer.processTerm(null);
	}

  }
  /****************************************************************************************/
  public void run() {
/* The main part of the handler */
/* Sleep to let other threads run then check for input */
/* Send any input to the Severs handleMessage() method */
	Message inMsg;

	try {
	  while( keepRunning ) {
		sleep(50);

		inMsg = (Message)cinput.readObject(); 
		theServer.handleMessage(inMsg, this);
	  }//end While loop

	} catch (InterruptedException e) {
	  e.printStackTrace();
		
	} catch (Exception e) {
	  alive = false;
	  System.out.println("lost connection 2");
	  theServer.processTerm(null);

	} finally {  /* Performed always (in case of normal exit or exception). */
	  try {
		cinput.close();
		coutput.close();
		s.close();
	  } catch( IOException ex ) {
		ex.printStackTrace();
	  }
	  
	}//End Finally clause

  }//End run()
  
}//End Class ConnectionHandler







