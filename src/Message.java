/*

	This is the base class for all message types.
	The type variable is used to set what type of message
	is being sent

*/

import java.io.*;
import java.net.*;

public class Message implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/* Message types */
  public final static int kMoveMessage  = 0;
  public final static int kGridMessage  = 1;
  public final static int kTermMessage  = 2;
  public final static int kStartMessage = 3;
  public final static int kErrorMessage = 4;	
  public final static int kJoinMessage  = 5;

  /* Error codes */
  public final static int kFullGame = 6;
  public final static int kGamePlaying = 7;

  public int type;
  public InetAddress sender;

}//End Class
