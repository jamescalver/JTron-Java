/*

	The move message class, sent by clients to the server

*/

import java.io.*;
import java.net.*;

public class Move extends Message implements Serializable {
  
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
int playerNum;
  char moveDirection;  //compass point

  public Move(char direction, int pNum) {

	type = Message.kMoveMessage ;
	moveDirection = direction;
	playerNum = pNum;

	try {
	  sender = InetAddress.getLocalHost();
	} catch (UnknownHostException e) {
	  e.printStackTrace();
	}

  }
  
  public String toString() {
	return("Type = "+ type +"; Move Direction = "+ moveDirection);
  }
}//End Class
