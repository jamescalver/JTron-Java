/*

	The Term message class

*/

import java.io.*;
import java.net.*;

public class Term extends Message implements Serializable {
  
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
int playerNumber;

  public Term(int num) {

	type = kTermMessage;

	playerNumber = num;

	try {
	  sender = InetAddress.getLocalHost();
	} catch (UnknownHostException e) {
	  e.printStackTrace();
	}

  }
  
  public String toString() {
	return("Type = "+ type +"; Term Message");
  }
}//End Class
