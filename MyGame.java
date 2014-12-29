//Barsha Shrestha
import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyGame extends JPanel {

	private static SilverGameGUIController silverGameGUIController;


	// Constructor
	public MyGame() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// create a new JFrame to hold TicTacToePanel
		JFrame coinFrame = new JFrame();
		int gameBoardSize;
		int totalCoins;
		// set size
		coinFrame.setSize(720, 250);
		// use a border layout
		coinFrame.setLayout(new BorderLayout());
		//if the user has provided both the gameBoardSize and the totalCoins, call constructor which takes both these parameters in 
				if(args.length==2)
				{
					 gameBoardSize= Integer.parseInt(args[0]);
					 totalCoins =Integer.parseInt(args[1]);
					silverGameGUIController = new SilverGameGUIController(gameBoardSize,totalCoins); 
				}
				//if the user has provided the gameBoardSize, call constructor which takes this parameter in 
				else if (args.length ==1)
				{
					 gameBoardSize= Integer.parseInt(args[0]);
					 silverGameGUIController = new SilverGameGUIController(gameBoardSize); 
				}
					//if no arguments or more than 2 arguments passed, call default constructor
				else
					 silverGameGUIController = new SilverGameGUIController(); 
		

		// create a TetrisGameTextController and add it
		coinFrame.add(silverGameGUIController, BorderLayout.CENTER);

		// exit normally on closing the window
		coinFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// show frame
		coinFrame.setVisible(true);

	}

}
