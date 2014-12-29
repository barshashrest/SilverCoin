//Barsha Shrestha

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SilverGameGUIController extends JPanel {
	private MyGameGUIView view;

	private SilverGameBoard board;

	// Constructor
	public SilverGameGUIController() {// call super class
		super();

		board = new SilverGameBoard();

		view = new MyGameGUIView(board);

		// use a border layout
		setLayout(new BorderLayout());

		// add the panel with the board to the center
		add(view, BorderLayout.CENTER);
	}

	// Another constructor for when user passes in board size
		public SilverGameGUIController(int boardSize) {// call super class
			super();

			board = new SilverGameBoard(boardSize);

			view = new MyGameGUIView(board);

			// use a border layout
			setLayout(new BorderLayout());

			// add the panel with the board to the center
			add(view, BorderLayout.CENTER);
		}
	
	// Another constructor for when the user passes in board size and total coins
		public SilverGameGUIController(int boardSize, int totalCoins) {// call super class
			super();

			board = new SilverGameBoard(boardSize, totalCoins);

			view = new MyGameGUIView(board);

			// use a border layout
			setLayout(new BorderLayout());

			// add the panel with the board to the center
			add(view, BorderLayout.CENTER);
		}

}
