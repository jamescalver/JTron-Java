/*

	The is the Grid message class.  It is sent by the Server to the Clients

*/

import java.io.Serializable;

public class Grid extends Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/* Each players new direction */
  public char p1;  //Compass directions
  public char p2;  
  public char p3;
  public char p4;

	/* Each players current score */
  public int s1;
  public int s2;
  public int s3;
  public int s4;

  public Grid(char p1, int s1, char p2, int s2, char p3, int s3, char p4, int s4) {

	type = Message.kGridMessage;
	this.p1 = p1;
	this.p2 = p2;
	this.p3 = p3;
	this.p4 = p4;

	this.s1 = s1;
	this.s2 = s2;
	this.s3 = s3;
	this.s4 = s4;

  }
  
  public String toString() {
	return("Type = "+ type +"; Player = "+ p1);
  }
}//End Class
