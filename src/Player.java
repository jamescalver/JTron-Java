/*

	This class contains information and methods for each player.
	The players trails and score are stored here, plus methods to
	manage these values.

*/

import java.awt.geom.Line2D;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.Vector;

public class Player {

  public ConnectionHandler client = null;
  public int playerNumber;
  public InetAddress playerHost;
  public boolean alive = false;

  public Vector Trails = new Vector(10,3);
  public char currentDirection;  //compass point
  public int score;

  /***************************************************************/
  public Player(ConnectionHandler c) {

	client = c;
	alive = false;
	if (c != null)
	  playerHost = c.s.getInetAddress();

  }//End Constructor

  /**************************************************************/
  public void termPlayer() {

	Trails.clear();
	alive = false;
	
  }//End termPlayer()  

  /**************************************************************/
  public void updateTrail() {
	/* Set the player current location.  The player moved forward. */

	Line2D.Double currentTrail;

	currentTrail = (Line2D.Double)Trails.get(0);

	//	System.out.println("Currentdir = " +currentDirection);

	if (currentDirection == 'N') 
	  currentTrail.y2 -= 1;
	if (currentDirection == 'E')
	  currentTrail.x2 += 1;
	if (currentDirection == 'S') 
	  currentTrail.y2 += 1;
	if (currentDirection == 'W')
	  currentTrail.x2 -= 1;

  }

  /**************************************************************/
  public void addTrail(int x, int y, char newDir) {
	/* Player has changed directions */
	/* newDir is a compass direction */

	Line2D.Double trail = new Line2D.Double(x,y,x,y);
	Trails.add(0, trail);
	
	currentDirection = newDir;/* Need to fix this, or atleast check */

  }//End addTrail()

  /**************************************************************/
  public Location currentLocation() {
	/* Return the players current point location */

	Location tempLocation = new Location();

	Line2D.Double tempLine;

	if (Trails.size() > 0) {
	  tempLine = (Line2D.Double)Trails.firstElement();
	  tempLocation.x = (int)tempLine.x2;
	  tempLocation.y = (int)tempLine.y2;
	}
	else 
	  System.out.println("!!!!!!!!     Trails is empty     !!!!!");

	return(tempLocation);

  }

  /**************************************************************/
  public boolean isCollision(int x, int y) {
	/* This method should detect collision, but it does not seem */
	/* to work. */
	
	boolean hit = false;
	Line2D.Double tempTrail;
	Iterator theData = Trails.iterator();

	while(theData.hasNext()) {
	  System.out.println("Check for collision");
	  tempTrail = (Line2D.Double)theData.next();
	  if (tempTrail.intersectsLine((double)x,(double)y,(double)x,(double)y))
		return(true);

	}

	return(false);

  }//End isCollision
}//End Class Player

