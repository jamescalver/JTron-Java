/*

	This is the JTron Client program.  It handles everything for the client.

*/

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

public class JTronClient {

  public static Socket clientSocket = null;
  public static ObjectInputStream input = null;
  public static ObjectOutputStream output = null;
  public GUI gameWindow;

  public Vector Players = new Vector(4,1);
  public int myNumber;

  public static final int port = 1701;  //port number

  /***********************************************************************/
  public JTronClient() {
	/* Make a GUI for the client, and a list of the players */

   	gameWindow = new GUI(this);

	for (int i =0; i<4; i++) {
	  Player tempPlayer = new Player(null);
	  tempPlayer.playerNumber = i+1;
	  tempPlayer.alive = true;

	  Players.add(tempPlayer);
	}
  }

  /***********************************************************************/
  public static void main (String args[]) throws IOException {

	if (args.length != 1)
	  throw new RuntimeException ("Syntax: JTronClient <host>");
	
	/* Setup and connect a new socket connection */
	try {
	  clientSocket = new Socket(args[0], port);

	  output = new ObjectOutputStream(clientSocket.getOutputStream());
	  input = new ObjectInputStream(clientSocket.getInputStream());

	} catch (UnknownHostException e) {
	  System.err.println("Can't find host: " + args[0]);
	} catch (IOException e) {
	  System.err.println("Couldn't get I/O for the connection to: " + args [0]);
	}

	/* Make a client and start it */
	JTronClient theClient = new JTronClient();
	theClient.joinGame();

  }//End Main
  /**********************************************************************/
  public void waitForStart() {
	
	boolean startGame = false;
	Message inMsg;


	while(startGame == false) {
	  /* Block waiting for next Message */
	  
	  /* Block until there is input, then read it */
	  inMsg = readMessage();
	  
	  /* Check message type, if kStartMessage, exit loop */
	  if (inMsg == null) {
		//wrong message keep waiting
	  }
	  else if (inMsg.type == Message.kStartMessage) {
		startGame = true;
		myNumber = ((Start)inMsg).playerNumber;
		System.out.println("My number is " +myNumber);
	  }
	  else if (inMsg.type == Message.kErrorMessage) {
		if (((Error)inMsg).errorCode == Message.kFullGame)
		  gameWindow.message("Game is full, please try again later");
	  }
	  
	}//End while loop
	
	/* A start message has arrived, get ready to play the game */
	gameWindow.message("You are player #"+myNumber);
	gameWindow.message("The game is about to begin.\nRacers to your positions.");

	/* Get first Grid message */
	/* Block waiting for next message */
	inMsg = (Grid)readMessage();
	if (inMsg != null)
	  if (inMsg.type == Message.kTermMessage)
		return;
	  
	gameWindow.message("Go!!!!");
	
	/* Setuo the players location for the start of the game */
	Player tempPlayer;
	if (inMsg.type == Message.kGridMessage) {
	  tempPlayer = (Player)Players.get(0);
	  tempPlayer.addTrail(150,1,'S');
	  tempPlayer.updateTrail();
	  tempPlayer.updateTrail();
	  
	  tempPlayer = (Player)Players.get(1);
	  tempPlayer.addTrail(299,150,'W');
	  tempPlayer.updateTrail();
	  tempPlayer.updateTrail();
	  
	  tempPlayer = (Player)Players.get(2);
	  tempPlayer.addTrail(150,299,'N');
	  tempPlayer.updateTrail();
	  tempPlayer.updateTrail();

	  tempPlayer = (Player)Players.get(3);
	  tempPlayer.addTrail(1,150,'E');
	  tempPlayer.updateTrail();
	  tempPlayer.updateTrail();
	}
	
	playGame();

  }//End waitForStart()

  /*********************************************************************/
  public void playGame() {

	boolean gameOver = false;
	Message inMsg;

	while (gameOver == false) {

	  /* Block waiting for next message */
	  inMsg = readMessage();

	  if (inMsg.type == Message.kTermMessage) {
		gameWindow.message("The Server has terminated the game");
		return;
	  }


	  if (inMsg.type == Message.kGridMessage) {

		System.out.println("Grid message recieved");
		/* Send next move */
		try {Thread.sleep(myNumber*2);} catch(Exception e) {}
		Move newMove = new Move(getMove(), myNumber);

		sendMessage(newMove);

		/* Update GUI */
		updateGrid((Grid)inMsg);


	  }//End if KGridMessage
	}//End while loop
  }//End playGame()

  /*******************************************************************/
  public void updateGrid(Grid newGrid) {
	Player tempPlayer;
	Location tempLocation;

	//	System.out.println("Update based on the Grid message");
	
	tempPlayer = (Player)Players.get(0);
	tempPlayer.score = newGrid.s1;
	if (tempPlayer.currentDirection == newGrid.p1) {
	  tempPlayer.updateTrail();
	  //      System.out.println("Update Trail player 1, newM = "+ newGrid.p1);
	}
	else {
	  if (newGrid.p1 == 'D') {
		/*Player is dead*/
		tempPlayer.alive = false;
		tempPlayer.Trails.clear();
	  }// End if
	  else {
		//System.out.println("Add trail p1");
		tempLocation = tempPlayer.currentLocation();
		tempPlayer.currentDirection = newGrid.p1;
		tempPlayer.addTrail(tempLocation.x, tempLocation.y, newGrid.p1);
		tempPlayer.updateTrail();
	  }
	}

	tempPlayer = (Player)Players.get(1);
	tempPlayer.score = newGrid.s2;
	if (tempPlayer.currentDirection == newGrid.p2) {
	  tempPlayer.updateTrail();
	  //	  System.out.println("Update Trail player 2, newM = "+ newGrid.p2);
	}
	else {
	  if (newGrid.p2 == 'D') {
		/*Player is dead*/
		tempPlayer.alive = false;
		tempPlayer.Trails.clear();
	  }// End if
	  else {
		//System.out.println("Add trail p2");
		tempLocation = tempPlayer.currentLocation();
		tempPlayer.currentDirection = newGrid.p2;
		tempPlayer.addTrail(tempLocation.x, tempLocation.y, newGrid.p2);
		tempPlayer.updateTrail();
	  }
	}
	
	tempPlayer = (Player)Players.get(2);
	tempPlayer.score = newGrid.s3;
	if (tempPlayer.currentDirection == newGrid.p3) {
	  tempPlayer.updateTrail();
	  //	  System.out.println("Update Trail player 3");
	}
	else {
	  if (newGrid.p3 == 'D') {
		/*Player is dead*/
		tempPlayer.alive = false;
		tempPlayer.Trails.clear();
	  }// End if
	  else {
		//System.out.println("Add trail p3");
		tempLocation = tempPlayer.currentLocation();
		tempPlayer.currentDirection = newGrid.p3;
		tempPlayer.addTrail(tempLocation.x, tempLocation.y, newGrid.p3);
		tempPlayer.updateTrail();
	  }
	}
	
	tempPlayer = (Player)Players.get(3);
	tempPlayer.score = newGrid.s4;
	if (tempPlayer.currentDirection == newGrid.p4) {
	  tempPlayer.updateTrail();
	  //	  System.out.println("Update Trail player 4");
	}
	else {
	  if (newGrid.p4 == 'D') {
		/*Player is dead*/
		tempPlayer.alive = false;
		tempPlayer.Trails.clear();
	  }// End if
	  else {
		//System.out.println("Add trail p4");
		tempLocation = tempPlayer.currentLocation();
		tempPlayer.currentDirection = newGrid.p4;
		tempPlayer.addTrail(tempLocation.x, tempLocation.y, newGrid.p4);
		tempPlayer.updateTrail();
	  }
	}
	
	gameWindow.gameGrid.repaint(10);
	
  }
  /*******************************************************************/
  public char getMove() {
	/* Get the players next move for the Move message */

	Player tempPlayer = (Player)Players.get(myNumber-1);

	if (gameWindow.currentDir == 'C')
	  return(tempPlayer.currentDirection);
	else
	  return(gameWindow.currentDir);

  }

  /*******************************************************************/
  public void joinGame() {
	/* Send a join message */

	Join newJoin = new Join();

	sendMessage(newJoin);

	waitForStart();

  }

  /*******************************************************************/
  public void quit(int i){
	/* Send a term message */
	Term newTerm = new Term(myNumber);

	sendMessage(newTerm);

	System.exit(i);
  }
  /*******************************************************************/
  public void sendMessage(Message outMsg) {
	/* Send a message to the Server */

	try {
	  output.writeObject(outMsg);
	} catch (IOException e) {
	  gameWindow.message("The Server seems to have died.");
	  gameWindow.message("The game is over");
	}

  }

  /*******************************************************************/
  public Message readMessage() {
/* Read in a message from the server */

	Message inMsg = null;

	try {
	  inMsg = (Message)input.readObject();
	} catch (IOException e) {
	  gameWindow.message("The Server seems to have died.");
	  gameWindow.message("The game is over");
	} catch (ClassNotFoundException e) {
	  gameWindow.message("The Server seems to have died.");
	  gameWindow.message("The game is over");
	}

	// If the players quits, inMsg might be null
	if (inMsg == null) {
		return(null);
	}
	
	if (inMsg.type == Message.kTermMessage) {
	  processTerm((Term)inMsg);
	  return((Term)inMsg);
	}

	return(inMsg);

  }
  /*******************************************************************/
  public void processTerm(Term Msg) {
/* Process a recieved Term message */

	gameWindow.message("A player has left the game.");
	gameWindow.message("The game is now over.");

	Players.clear();

	try {
	  clientSocket.close();
	  input.close();
	  output.close();	
	} catch (IOException e) {
	  //Don't worry, we want the links closed.
	}

  }
  /*******************************************************************/
}//End Class JTronClient





