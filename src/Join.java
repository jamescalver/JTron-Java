/*

	This is the Join message class.  It is sent by the clients to 
	the server when they want to join a game.

*/

import java.io.*;
import java.net.*;

public class Join extends Message implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public Join() {

	type = kJoinMessage;

	try {
	  sender = InetAddress.getLocalHost();
	} catch (UnknownHostException e) {
	  e.printStackTrace();
	}
	
  }
  
  public String toString() {
	return("Type = "+ type +"; Join Message, host = "+ sender);
  }
}//End Class
