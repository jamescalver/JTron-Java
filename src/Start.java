/*

	The Start message class

*/

import java.io.*;

public class Start extends Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int playerNumber;

	public Start(int player) {

		type = Message.kStartMessage ;
		playerNumber= player;
	}

	public String toString() {
		return("Type = "+ type +"; Player= "+ playerNumber);
	}

}//End Class