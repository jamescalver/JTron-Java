/*

	This is the class for the Error message.  This message is sent
	if there are ever any errors.

*/


import java.io.*;
import java.net.*;

public class Error extends Message implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public int errorCode;

  /*************************************************************/
  public Error(int errorID) {

	type = Message.kErrorMessage ;
	errorCode= errorID;

	try {
	  sender = InetAddress.getLocalHost();
	} catch (UnknownHostException e) {
	  e.printStackTrace();
	}
  }
  
  /*************************************************************/
  public String toString() {
	return("Type = "+ type +"; errorCode = "+ errorCode);
  }

}//End Class
