/*

	This is the main GUI class.  It layouts all of the components for the GUI,
	and creates the gameGird object.

*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GUI extends JFrame {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public static final int kWindowHeight = 400;
  public static final int kWindowWidth = 800;
  public static final int kXCenter = 200;
  public static final int kYCenter = 300;

  public static final Color kPlayer1Color = Color.red;
  public static final Color kPlayer2Color = Color.blue;
  public static final Color kPlayer3Color = Color.green;
  public static final Color kPlayer4Color = Color.pink;

  char currentDir = 'C';  //compass. or 'C' for current dir

  public GameGrid gameGrid;
  JTextArea updateWindow;

  public JTronClient theClient;
  public int p1Score, p2Score, p3Score, p4Score = 0;

  /***********************************************************************/
  public GUI(JTronClient callingThread) {

	theClient = callingThread;

	/* Setup the main frame window */
	setTitle("JTron, James Calver;ID:9307102");
	setSize(kWindowWidth , kWindowHeight );
	setBackground(Color.white);
	getContentPane().setLayout(new GridLayout(1,2));
	
	/* Make the Game Grid */
	JPanel gamePanel = new JPanel();
	gamePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Game Grid"));
	getContentPane().add(gamePanel, BorderLayout.WEST);
	
	gameGrid = new GameGrid(this);
	gameGrid.setSize(GameGrid.kMapWidth, GameGrid.kMapHeight);
	gamePanel.add(gameGrid);

	/* Make Game info Panel*/
	JPanel gameInfoPanel = new JPanel(new GridLayout(3,1));
	gameInfoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Game Info"));

	JPanel scorePane = new JPanel(new GridLayout(2,2));
	JLabel player1 = new JLabel("Player 1 = " + p1Score);
	JLabel player2 = new JLabel("Player 2 = " + p2Score);
	JLabel player3 = new JLabel("Player 3 = " + p3Score);
	JLabel player4 = new JLabel("Player 4 = " + p4Score);

	player1.setForeground(kPlayer1Color);
	player2.setForeground(kPlayer2Color);
	player3.setForeground(kPlayer3Color);
	player4.setForeground(kPlayer4Color);

	scorePane.add(player1);
	scorePane.add(player3);
	scorePane.add(player2);
	scorePane.add(player4);
	gameInfoPanel.add(scorePane);

	updateWindow = new JTextArea(5, 20);
	updateWindow.setEditable(false);
	JScrollPane scrollPane = new JScrollPane(updateWindow, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
											 JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	updateWindow.append("Welcome to JTron\n");
	
	gameInfoPanel.add(scrollPane);

	getContentPane().add(gameInfoPanel, BorderLayout.EAST);
	
	/* Create the direction buttons */
	JPanel buttonPanel = new JPanel();

	JButton north = new JButton("North");
	north.addActionListener( new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  changeDirection('N');
		}
	});
	buttonPanel.add(north);

	JButton east = new JButton("East");
	east.addActionListener( new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  changeDirection('E');
		}
	});
	buttonPanel.add(east);

	JButton south = new JButton("South");
	south.addActionListener( new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  changeDirection('S');
		}
	});
	buttonPanel.add(south);

	JButton west = new JButton("West");
	west.addActionListener( new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  changeDirection('W');
		}
	});
	buttonPanel.add(west);

	JButton quit = new JButton("Quit");
	quit.addActionListener( new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		  theClient.quit(0);
		 }
	});
	buttonPanel.add(quit);

	gameInfoPanel.add(buttonPanel);

	setVisible(true);
  }

  /*********************************************************************/
  public void message(String msg) {

	updateWindow.append(msg + "\n");
  }

  /*********************************************************************/
  synchronized public void changeDirection(char newD) {
	/* update the players current direction */
	currentDir= newD;
  }

  /*********************************************************************/
  public void updateScore(int p1, int p2, int p3, int p4) {
	/* update the players current score */
	p1Score = p1;
	p2Score = p2;
	p3Score = p3;
	p4Score = p4;

  }
}
