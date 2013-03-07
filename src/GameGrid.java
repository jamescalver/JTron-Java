/*

	This is the class for controlling the game grid graphic in the GUI.
	
*/

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Line2D;

public class GameGrid extends Canvas {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public static final int kMapHeight = 300;
  public static final int kMapWidth = 300;

  public int topX, topY = 0;
  public int bottomX, bottomY = 0;

  public GUI theGUI;

  Image OffScreenBuffer = null;
  Graphics OffScreen;
	

  /***********************************************************************/
  public GameGrid(GUI callingGUI) {
	theGUI = callingGUI;
  }

  /***********************************************************************/
  public void update(Graphics g) {
	paint(g);
  }

  /***********************************************************************/
  public void paint(Graphics g) {
	int i,j;

	/* Create a buffer to reduce flicker */
	if (OffScreenBuffer == null)
	  OffScreenBuffer = createImage( kMapWidth, kMapHeight );

	OffScreen = OffScreenBuffer.getGraphics();

	/*Clear screen*/
	OffScreen.setColor(Color.white );
	OffScreen.fillRect( 0, 0, kMapWidth, kMapHeight );

	/*Draw border*/
	OffScreen.setColor(Color.black);
	OffScreen.drawRect(0, 0, kMapWidth-1, kMapHeight-1);

	/*Draw trails*/
	Player tempPlayer;
	Line2D.Double tempLine = null;

	/* For each player, draw their bike trails on the gameGrid */
	/* Draw each trail in the colour for that player */
	for (i = 0; i < theGUI.theClient.Players.size(); i++) {
	  tempPlayer = (Player)theGUI.theClient.Players.get(i);

	  if (tempPlayer.playerNumber == 1) {
		OffScreen.setColor(theGUI.kPlayer1Color);
		for (j = 0; j < tempPlayer.Trails.size(); j++) {
		  tempLine = (Line2D.Double)tempPlayer.Trails.get(j);
		  OffScreen.drawLine((int)tempLine.x1, (int)tempLine.y1, (int)tempLine.x2, (int)tempLine.y2);
		}//End for Loop
	  }//End if player 1

	  else if (tempPlayer.playerNumber == 2) {
		OffScreen.setColor(theGUI.kPlayer2Color);
		for (j = 0; j < tempPlayer.Trails.size(); j++) {
		  tempLine = (Line2D.Double)tempPlayer.Trails.get(j);
		  OffScreen.drawLine((int)tempLine.x1, (int)tempLine.y1, (int)tempLine.x2, (int)tempLine.y2);
		}//End for Loop
	  }//end if player 2

	  else if (tempPlayer.playerNumber == 3) {
		OffScreen.setColor(theGUI.kPlayer3Color);
		for (j = 0; j < tempPlayer.Trails.size(); j++) {
		  tempLine = (Line2D.Double)tempPlayer.Trails.get(j);
		  OffScreen.drawLine((int)tempLine.x1, (int)tempLine.y1, (int)tempLine.x2, (int)tempLine.y2);
		}//End for Loop
	  }//end If player 3

	  else {
		OffScreen.setColor(theGUI.kPlayer4Color);
		for (j = 0; j < tempPlayer.Trails.size(); j++) {
		  tempLine = (Line2D.Double)tempPlayer.Trails.get(j);
		  OffScreen.drawLine((int)tempLine.x1, (int)tempLine.y1, (int)tempLine.x2, (int)tempLine.y2);
		}//End for Loop
	  }//end last else
	}//end for i...

	/* Draw the offscreen buffer on the real screen */
	g.drawImage(OffScreenBuffer, 0, 0, this);
  }
}


