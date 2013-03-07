/*

	This is the main Server for JTron

*/

import java.util.Vector;

public class JTronServer extends Thread{

  public static final int port = 1701;   // Port number

  public final int kMaxPlayers = 2;

  public int currentNumPlayer = 0;
  public Vector PlayerList = new Vector(kMaxPlayers,1);
  public int numAlivePlayers;

  public boolean gamePlaying = false;

  /**************************************************************************/
  public JTronServer() {

  }

  /**************************************************************************/
  public static void main(String args[]) {
	/* Make a Server object and then run it */

	JTronServer theServer = new JTronServer();
	theServer.run();

  }//End main 

  /**************************************************************************/
  public void run() {
	/* Start the simpleServer to listen of players */
	/* Then start the game*/

	startSimpleServer(port);
	startGame();

  }
  /**************************************************************************/
  public void startSimpleServer(int port) {

	try {
	  SimpleServer s = new SimpleServer(this, port);
	  s.start();
	} catch (Exception e) {
	  System.out.println(e);
	}

  }

  /**************************************************************************/
  public void startGame() {

	/* Start playing the game */
	/* Wait for 4 players */
	while(currentNumPlayer < kMaxPlayers){ 
	  try {
		sleep(100);
	  } catch(Exception e) {
		e.printStackTrace();
	  }
	}//End while loop

	/*Send start message to all players*/
	Player tempPlayer;
	
	for(int i = 0; i < PlayerList.size(); i++) {
	  tempPlayer = (Player)PlayerList.get(i);
	  
	  /* make a start message */
	  Start newStart = new Start(tempPlayer.playerNumber);
	  System.out.println("Send Start message");
	  tempPlayer.client.sendMessage(newStart);

	}//End for loop

	/* Wait a few seconds, in this case 5sec */
	try {
	  sleep(5000);
	} catch (Exception e) {
	  e.printStackTrace();
	}

	/* Start game */
	//System.out.println("RunGame()");
	runGame();

  }

  /**************************************************************************/
  public void runGame() {
	/* Start the game running */

	Player tempPlayer;
	int s1,s2,s3,s4 = 0;
	char p1,p2,p3,p4 = ' ';

	gamePlaying = true;
	numAlivePlayers = 4;

	/* Send the first Grid message to the clients */
	System.out.println("Send First Grid message.");

	tempPlayer = (Player)PlayerList.get(0);
	s1 = tempPlayer.score;

        tempPlayer = (Player)PlayerList.get(1);
	s2 = tempPlayer.score;

//	tempPlayer = (Player)PlayerList.get(2);
	s3 = tempPlayer.score;

//	tempPlayer = (Player)PlayerList.get(3);
	s4 = tempPlayer.score;

	Grid newGrid = new Grid('S',s1, 'W',s2, 'N',s3, 'E',s4);
	broadcastMessage(newGrid);

	while(gamePlaying) {

	  /* Sleep, and wait for new move messages */
	  try {
		sleep(250);
	  }catch (Exception e) {
		e.printStackTrace();
	  }

	  /* Did the game end while we were sleeping? */
	  if (gamePlaying == false)
		break;

	/* Make the next Grid message */
	  System.out.println("Send Next Grid message.");
	  tempPlayer = (Player)PlayerList.get(0);
	  s1 = tempPlayer.score;
	  p1 = tempPlayer.currentDirection;

	  tempPlayer = (Player)PlayerList.get(1);
	  s2 = tempPlayer.score;
	  p2 = tempPlayer.currentDirection;

//	  tempPlayer = (Player)PlayerList.get(2);
	  s3 = tempPlayer.score;
	  p3 = tempPlayer.currentDirection;

//	  tempPlayer = (Player)PlayerList.get(3);
	  s4 = tempPlayer.score;
	  p4 = tempPlayer.currentDirection;

	  if (gamePlaying == true) {
		newGrid = new Grid(p1,s1,p2,s2,p3,s3,p4,s4);
		System.out.println("New Directions = "+p1+p2+p3+p4);
		broadcastMessage(newGrid);
	  }

	  if (numAlivePlayers == 1)
		gamePlaying = false;
	}//End while loop

	/* Start another game */
	startGame();

  }
  /**************************************************************************/
  public void broadcastMessage(Message msg) {
	/* Send a broadcast message to all players */

	Player tempPlayer = null;
	ConnectionHandler tempClient = null;

	/* Send a message to all players */
	for (int i = 0; i < PlayerList.size(); i++) {
	  tempPlayer = (Player)PlayerList.get(i);
	  tempClient = tempPlayer.client;
	  if (tempPlayer != null)
		tempClient.sendMessage(msg);
	}//End for loop
  }
  /**************************************************************************/
  public void handleMessage(Message message, ConnectionHandler client) {
	/* Handle incoming messages */

	if(message.type == Message.kJoinMessage)
	  processJoin( (Join)message, client );
	if(message.type == Message.kMoveMessage)
	  processMove( (Move)message );
	if(message.type == Message.kTermMessage)
	  processTerm( (Term)message );
  }

  /**************************************************************************/
  synchronized public void processJoin(Join message, ConnectionHandler client) {

	/* Set up the new Player */
	if (currentNumPlayer >= kMaxPlayers) {
	  /* Game full */
	  System.out.println("Game Is full!!");
	  Error fullGame = new Error(Message.kFullGame);
	  client.sendMessage(fullGame);
	}
	else if (gamePlaying == true) {
	  /* Game in progress must wait */
	  System.out.println("Game Is playing!!");
	  Error fullGame = new Error(Message.kGamePlaying);
	  client.sendMessage(fullGame);
	}

	else {

	  System.out.println("New Player!!");
	  currentNumPlayer += 1;

	  Player newPlayer = new Player(client);
	  newPlayer.playerNumber = currentNumPlayer;
	  newPlayer.alive = true;

	  /* Set start Postions */
	  if (currentNumPlayer == 1) {
		newPlayer.addTrail(150, 1, 'S');
		newPlayer.updateTrail();
		newPlayer.updateTrail();
	  }
	  if (currentNumPlayer == 2) {
		newPlayer.addTrail(299, 150, 'W');
		newPlayer.updateTrail();
		newPlayer.updateTrail();
	  }
	  if (currentNumPlayer == 3) {
		newPlayer.addTrail(150, 299, 'N');
		newPlayer.updateTrail();
		newPlayer.updateTrail();
	  }
	  if (currentNumPlayer == 4) {
		newPlayer.addTrail(1, 150, 'E');
		newPlayer.updateTrail();
		newPlayer.updateTrail();
	  }
	  
	  PlayerList.add(newPlayer);
	  
	}
	
  }//End processJoin()

  /**************************************************************************/
  synchronized public void processMove(Move message) {

	Location tempLocation;
	char newDir;

	/* Update Grid array */

	/* Update Player position */
	for (int i = 0; i < PlayerList.size(); i++) {
	  Player tempPlayer = (Player)PlayerList.get(i);
	  
	  if (message.playerNum == tempPlayer.playerNumber){

		if (tempPlayer.currentDirection == message.moveDirection) {
		  System.out.println(" Update player Trail = " + tempPlayer.currentDirection);
		  System.out.println("PLayer number = "+tempPlayer.playerNumber);
		  tempPlayer.updateTrail();
		}
		else {
		  tempLocation = tempPlayer.currentLocation();
		  tempPlayer.addTrail(tempLocation.x, tempLocation.y, message.moveDirection);
		  tempPlayer.updateTrail();
		}//End else

		
		/* Check for Collision */
		/* This part does not work.
		
		Player temp2;
		boolean collision = false;
		Location tempL2;
	
		for (int j = 0; j < PlayerList.size(); j++){
		  temp2 = (Player)PlayerList.get(j);
		  tempL2 = temp2.currentLocation();
		  if (tempPlayer.isCollision(tempL2.x, tempL2.y))
			collision = true;
		}
		if (collision) {
		  tempPlayer.alive = false;
		  tempPlayer.Trails.clear();
		}
		*/		

	  }

	}//end For loop
	

  }//End processMove()

  /**************************************************************************/
  public void broadcastGameOver(int playerNumber) {
	/* Send a term message to all remaining clients */

	for (int i = 0; i < PlayerList.size(); i++) {
	  if (playerNumber != i)
		((Player)PlayerList.get(i-1)).client.sendMessage(new Term(0));
	}
  }
  /**************************************************************************/
  public void processTerm(Term message) {

	/* Process client termination */

	if (message != null) {
	  System.out.println("Player #"+message.playerNumber+" has left the game.");
	  System.out.println("The game is over.");
	}
	if (message == null) {
	  System.out.println("A player's connection has died.");
	  System.out.println("The game is over.");
	}

	Player tempPlayer;
	for (int i = 0; i < PlayerList.size(); i++ ) {
	  tempPlayer = (Player)PlayerList.get(i);
	  if (tempPlayer.client.alive == true)
		tempPlayer.client.sendMessage(new Term(0));
	}

	restartGame();

  }//End processTerm()

  /**************************************************************************/
  public void restartGame() {
	/* Reset the game */
	
	currentNumPlayer = 0;

	for (int i = 0; i < PlayerList.size(); i++)
	  ((Player)PlayerList.get(i)).client.keepRunning = false;

	PlayerList.clear();
	gamePlaying = false;

	startGame();
	
  }
  /**************************************************************************/
}

