import java.awt.*;
import javax.swing.*;

public class GUI_old {

	public static int mapHeight = 200;
	public static int mapWidth = 200;

	public static Dimension GameSize = new Dimension(mapWidth, mapHeight);
	static Graphics gameMap;

/*********************************************************************/
  public static void main (String args[]) /*throws IOException*/ {

	try {
		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());	
	} catch (Exception e) {}
	JFrame mainWindow = new JFrame("JTron");
	mainWindow.getContentPane().setLayout(new GridBagLayout());


/* Create game window */
	JPanel gamePanel = new JPanel(new BorderLayout());
	//	gamePanel.setBorder(new TitledBorder(new EtchedBorder(), "Game Grid"));
	//	gamePanel.add(left, BorderLayout.CENTER);

//	gamePanel.setBorder(new BorderLayout());

	gamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//	gamePanel.setSize(GameSize);
//	gamePanel.setMinimumSize(GameSize);
//	gamePanel.setMaximumSize(GameSize);
	
	gameMap = gamePanel.getGraphics();

/* Create Info window */
	JPanel infoPanel = new JPanel();
	infoPanel.setBorder(BorderFactory.createTitledBorder("Info Window"));
	infoPanel.setLayout(new GridLayout(6,1));

	JLabel player1 = new JLabel("Player 1  =  ");
	infoPanel.add(player1);

	JLabel player2 = new JLabel("Player 2  =  ");
	infoPanel.add(player2);

	JLabel player3 = new JLabel("Player 3  =  ");
	infoPanel.add(player3);

	JLabel player4 = new JLabel("Player 4  =  ");
	infoPanel.add(player4);

	//Add scroll box

	//add panel with buttons


//...create the components to go into the frame... 
//...stick them in a container named contents... 
	mainWindow.getContentPane().add(gamePanel);
	mainWindow.getContentPane().add(infoPanel);

//Finish setting up the frame, and show it. 
//frame.addWindowListener(...); 
	mainWindow.pack(); 
	mainWindow.setSize(300,300);
	mainWindow.setVisible(true);
	}

	/*********************************************************************/
//	public void paint(Graphics g) {
//	}

}
