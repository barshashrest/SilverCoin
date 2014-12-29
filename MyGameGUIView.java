//Barsha Shrestha
//Assignment0 GUI
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.Timer;

public class MyGameGUIView extends JPanel implements MouseListener, KeyListener {
	// initialize a variable of type SilverGame
	private SilverGameBoard board;
	// array of x component position for each rectangle/circle
	private int[] arrayOfXComp;
	// x component of each circle
	private int XComp;
	// boolean to check if the mouse click was actually on the circle that
	// represents the circle
	private boolean clickedOnCircle = false;
	// boolean to check if it it player's turn
	private boolean playerTurn = true;
	// boolean to check if computer's turn
	private boolean computerTurn = false;
	// int that holds the array position of each square
	private int arrayPositionOfEachSquare;
	// boolean to check if the user has already chosen a coin to move and is
	// choosing a square to move it at
	private boolean secondChoosing = false;
	// int that gives the x comp of the coin that user clicked on
	private int firstXcomp;
	// boolean to check if player's turn is over
	private boolean player1TurnOver = false;
	// instance for timer
	private Timer timer;
	// boolean to check if the coin to be moved has been found
	private boolean coinToMoveFound;
	// int to get x component of the current coin
	private int XCompForCurrentCompCoin;
	//int to store the position of coin in grid
	private int positionOfCoinInGrid;
	//int to store the random spaces where the coin will be used by the computer
	private int randomPosition;
	//a coin
	private Coin coin;

	// constructor
	public MyGameGUIView(SilverGameBoard s) {
		// call super
		super();
		board = s;

		addMouseListener(this);
		addKeyListener(this);
		this.setFocusable(true);
		arrayOfXComp = new int[board.getCoins()];

	}

	/** Methods **/
	// method to compute the best block size for the current width and height.
	// returns size (for a square block) in pixels
	private int computeBlockSize() {
		int blockSize = (int) Math.floor((getWidth() / board.getGameSquares()));

		return blockSize;

	}

	// method that paints the game
	// Overrides paint in class javax.swing.JComponent
	public void paint(Graphics g) {
		 coin = new Coin();
		// paint the outline of the rectangular blocks
		paintBlockOutline(g, computeBlockSize());
		// paint the total squares
		for (int i = 0; i < board.getGameSquares(); i++) {
			g.setColor(Color.RED);
			// paint the piece so it is visible
			paintRect(g, computeBlockSize(), i * computeBlockSize(),
					computeBlockSize());

		}
		// int for each place in the array of x comp of the coins
		int a = 0;

		// for every array space (columns) in the board
		for (int i = 0; i < board.getGameSquares(); i++) {
			// if there is a coin, paint the coin
			if (board.hasCoin(i)) {
				// paint the coin
				coin = new Coin(g, i * computeBlockSize(), computeBlockSize(),
						computeBlockSize());
				// get the x comp beginning of the coin
				arrayOfXComp[a] = i * computeBlockSize();
				// increment a by 1 so array can hold another x comp in another
				// position
				a = a + 1;

			}
		}
		// if circle/silver coin has been clicked, either by user or the computer
		if (clickedOnCircle) {
			// change color to blue to show the player what coin he/she clicked
			// on
			coin.paintNewColorForSelectedCoin( g, XComp, computeBlockSize(),
					computeBlockSize(), Color.BLUE);

		}
		
		
				

	}

	// method that creates the outline for the board
	public void paintBlockOutline(Graphics g, int blockSize) {
		// set the color of the outline then draw the rectangular outline
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth(), getHeight());

	}

	public void paintRect(Graphics g, int row, int col, int rectSize) {
		g.fillRect(col, row, rectSize, rectSize);
		// set the color of the border of the block then draw the border
		g.setColor(Color.BLACK);

		g.drawRect(col, row, rectSize, rectSize);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		// only activate mouseEvent if it is the player's turn
		if (playerTurn == true) {
			// this mouseEvent is for when the player is pressing on the coin
			// so the circle/coin shouldn't have been clicked yet
			if (!clickedOnCircle) {
				// for every coin, check to see that the coin is actually being
				// clicked
				for (int i = 0; i < board.getCoins(); i++) {
					if (e.getX() >= arrayOfXComp[i]
							&& e.getX() <= arrayOfXComp[i] + computeBlockSize()
							&& e.getY() >= computeBlockSize()
							&& e.getY() <= computeBlockSize()
									+ computeBlockSize()) {

						// get the starting x component of the particular coin
						XComp = arrayOfXComp[i];
						// get x comp of the clicked coin
						firstXcomp = e.getX();
						//a coin has been chosen
						clickedOnCircle = true;
						//now it is time to choose a position where the player wants to put the coin
						secondChoosing = true;
						repaint();

					}
				}
			}
			// if coin has already been clicked, now a rectangle needs to be
			// clicked where the coin can be placed and only to the left
			else if (secondChoosing) {
				// get x and y components of the second click
				int secondXComp = e.getX();
				int secondYComp = e.getY();
				// now check for first only validity to the left i.e. going to
				// right of coin isn't valid
				if (checkOnlyLeftValid(XComp, secondXComp, secondYComp)) {

					// now check if while clicking on the left rectangle is
					// valid i.e the left rectangle where the coin is to be put
					// doesn't have any coin and there were none in between the
					// last position and this new position
					// checkCoin() returns true if move to that place is valid
					if (checkCoin(firstXcomp, secondXComp))

					{
						// get array position of the coin clicked
						int arrayPos1 = computeArrayPosition(firstXcomp);
						// array position of where the coin is to be put
						int arrayPos2 = computeArrayPosition(secondXComp);
						// change boolean values accordingly
						board.getBoard()[arrayPos1] = false;
						board.getBoard()[arrayPos2] = true;
						// computer's turn now, no more player's turn
						computerTurn = true;
						playerTurn = false;
						// for next time, a silver coin hasn't been clicked on
						// yet because it is computer's turn now
						clickedOnCircle = false;

						repaint();
						// if game is over, it is no one's turn
						if (gameDone()) {
							playerTurn = false;
							computerTurn = false;
							JOptionPane.showMessageDialog(null, "YOU WON!");
						}

					}
					// if the place chosen for the coin to go isn't valid, find
					// another spot
					else {
						secondXComp = e.getX();
					}

				}
			}

		}

	}

	// compute array position for each coin, takes in the x component of the
	// mouseclick then returns the array position of the coin 
	public int computeArrayPosition(int XComp) {

		// for every x-component
		for (int i = 0; i < board.getGameSquares(); i++) {
			if (XComp <= (i + 1) * computeBlockSize()) {
				arrayPositionOfEachSquare = i;

				return arrayPositionOfEachSquare;
			}

		}
		return arrayPositionOfEachSquare;

	}

	// check if there are coins when moving to the left
	// return true if there are no coins
	public boolean checkCoin(int firstXComp, int secondXComp) {
		int arrayPos1 = computeArrayPosition(firstXComp);
		int arrayPos2 = computeArrayPosition(secondXComp);
		// if the coin wanting to be moved is the first coin, it cannot be
		// moved. so second position where this coin is to be moved is the first
		// position itself i.e. array position of 0
		if (arrayPos1 == 0 && arrayPos2 != 0)
			return false;
		// check from -1 spot of where coin is to where coin is to go. if there
		// are coins in between, it is invalid
		for (int i = arrayPos1 - 1; i >= arrayPos2; i--) {
			if (board.hasCoin(i)) {

				return false;
			}

		}
		return true;
	}

	// checking if there are coins while moving to the left by taking in the
	// position of coin in grid and the places to moves
	public boolean checkCoinForComputer(int coin, int move) {
		if (coin == 0 && move != 0)
			return false;
		// same as for player checkCoin()
		for (int i = coin - 1; i >= coin - move; i--) {
			if (board.hasCoin(i))
				return false;

		}
		return true;

	}

	// check if coin can only be moved to the left
	public boolean checkOnlyLeftValid(int x, int mouseClickXComp,
			int mouseClickYComp) {
		// make sure that only left and within the rectangle moves are valid
		if (mouseClickXComp > x + computeBlockSize()
				|| mouseClickYComp > computeBlockSize() + computeBlockSize()
				|| mouseClickYComp < computeBlockSize()) {

			return false;
		}

		// otherwise
		return true;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	// keypressed is right arrow to say that computer's turn is now
	public void keyPressed(KeyEvent e) {

		// first check that it is computer's turn
		if (computerTurn) {
			
			// different behavior depending on which key was released
			switch (e.getKeyCode()) {
		
			// if it's the space bar
			case 32:
				// then computer's move
				// int to increment array position
				int n = 0;
				int[] rearrangedCoinPosition = new int[board.getCoins()];

				// check to see where the coins are on the board
				for (int k = 0; k < board.getGameSquares(); k++) {
					// if there is a coin
					if (board.hasCoin(k)) {
						// get the position of the coin
						rearrangedCoinPosition[n] = k;
						// increase n value so another position can be held at
						// another position in the array
						n = n + 1;

					}
				}
				// first get a random coin
				int randomCoin = (int) (Math.floor(Math.random()
						* board.getCoins())) + 1;
				// find position of random coin
				 positionOfCoinInGrid = rearrangedCoinPosition[randomCoin - 1];

				// if there is a coin in the left of the chosen coin or if the
				// coin is at position 0
				while (positionOfCoinInGrid == 0
						|| board.hasCoin(positionOfCoinInGrid - 1)) {
					// get another random coin
					randomCoin = (int) (Math.floor(Math.random()
							* board.getCoins())) + 1;
					// get new position
					positionOfCoinInGrid = rearrangedCoinPosition[randomCoin - 1];

				}
				
				//set Xcomp to be the position of coin in grid
				XComp= positionOfCoinInGrid*computeBlockSize();
				//the computer has now chosen a coin, so highlight the coin
				clickedOnCircle=true;
				repaint();
				//turns blue done
				
				// random position to the left can only be upto total positions
				// on the left of coin
			 randomPosition = (int) (Math.floor(Math.random()
						* (positionOfCoinInGrid))) + 1;
				// check that the random position is a valid position with no
				// coins in between

				while (!checkCoinForComputer(positionOfCoinInGrid,
						randomPosition)) {
					// keep changing the random position
					randomPosition = (int) (Math.floor(Math.random()
							* (positionOfCoinInGrid))) + 1;

				}
				// get x component of this new position
				XCompForCurrentCompCoin = (positionOfCoinInGrid - randomPosition)
						* computeBlockSize();
				// timer to change colors
				 setupTimer();
				
				
				
				break;

			default:
				break;
			}
		}

	}

	public void setupTimer() {
		int dropRate = 1500;
		// start the moving down

		// create a timer to advance the color
		timer = new Timer(dropRate, new ActionListener() {
			/**
			 * Invoked every time the timer finishes.
			 */
			public void actionPerformed(ActionEvent e) {

				coinToMoveFound = true;
				
				// if the coin to be moved by the computer found
				// change color to show where the coin has been moved
				if (coinToMoveFound) {
					
					
					// change boolean positions
					board.getBoard()[positionOfCoinInGrid] = false;
					board.getBoard()[positionOfCoinInGrid - randomPosition] = true;
					// change computer's turn to false and player to true

					computerTurn = false;
					
					playerTurn = true;
					// another circle needs to be clicked again
					clickedOnCircle = false;
					
					coinToMoveFound = false;
				
					repaint();
					// if game over, no one's turn
					if (gameDone()) {
						
						playerTurn = false;
						computerTurn = false;
						JOptionPane
								.showMessageDialog(null, "LOL the computer won!");
					}
	
				}

			}
		});

		timer.start();
		timer.setRepeats(false);
		
	}

	

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	// check to see if the game has been won
	public boolean gameDone() {
		// check for all the n coins to be occupying the first n leftmost
		// squares in the grid
		for (int i = 0; i < board.getCoins(); i++)
		{
			
			// if any of the positions is not filled with coins yet
			if (!board.hasCoin(i))
				return false;
		}
		// otherwise, game has been won
		
		return true;

	}

}
