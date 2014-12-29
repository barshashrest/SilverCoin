//Barsha Shrestha
// 1.24.14
//SilverCoin

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SilverGameBoard {

	// an array of booleans that checks to see if a coin is in the board
	private boolean[] gameBoard;

	// array to hold coin positions in the board in proper order
	private int[] rearrangedCoinPosition;
	// int to store total game squares for each board
	private int gameSquares;

	// int to store total coins
	private int coins;

	public SilverGameBoard() {
		// if no arguments passed, let the default total squares be 10
		gameSquares = 10;
		// set size of game board
		gameBoard = new boolean[gameSquares];

		// initialize the board to have no coins (all false)
		for (int j = 0; j < gameSquares; j++) {
			gameBoard[j] = false;
		}

		// set some spaces to be true indicating the coins
		coins = 4 + (int) (Math.floor(Math.random() * 3));

		// instantiate this array to hold total size that is equal to total
		// number of coins as each coin's placement in the board will be given
		// by this array
		rearrangedCoinPosition = new int[coins];

		// set the coins in the board
		for (int i = 0; i < coins; i++) {
			// at what position in the board the coin will be placed
			int a = (int) Math.floor(Math.random() * gameSquares);

			// avoid repetition by checking if there is already a coin
			// if already a coin
			while (gameBoard[a]) {
				// change value of a
				// at what position in the board the coin will be placed
				a = (int) Math.floor(Math.random() * gameSquares);
			}

			// after a position is found for a coin, change the boolean value in
			// the board to be true
			gameBoard[a] = true;

		}

	}

	// second constructor for when the total game squares is passed

	public SilverGameBoard(int passedGameSquares) {
		// provide the local value to the global variable
		gameSquares = passedGameSquares;
		// set size of game board
		gameBoard = new boolean[gameSquares];

		// initialize the board to have no coins (all false)
		for (int j = 0; j < gameSquares; j++) {
			gameBoard[j] = false;
		}

		// set some spaces to be true indicating the coins
		coins = gameSquares / 2 + (int) Math.floor(Math.random() * 3);
		

		rearrangedCoinPosition = new int[coins];

		// set the coins in the board
		for (int i = 0; i < coins; i++) {
			// at what position in the board the coin will be placed
			int a = (int) Math.floor(Math.random() * gameSquares);

			// avoid repetition by checking if there is already a coin
			// if already a coin
			while (gameBoard[a]) {
				// change value of a
				// at what position in the board the coin will be placed
				a = (int) Math.floor(Math.random() * gameSquares);
			}

			// after a position is found for a coin, change the boolean value in
			// the board to be true
			gameBoard[a] = true;

			// hold the position where each coin is added in the board

		}

	}

	// third constructor for when total game squares and total coins is passed
	public SilverGameBoard(int passedGameSquares, int totalCoins) {

		// provide the local value to the global variable
		gameSquares = passedGameSquares;
		coins = totalCoins;
		// set size of game board, should change though
		gameBoard = new boolean[gameSquares];

		// initialize the board to have no coins (all false)
		for (int j = 0; j < gameSquares; j++) {
			gameBoard[j] = false;
		}

		rearrangedCoinPosition = new int[coins];

		// set the coins in the board
		for (int i = 0; i < coins; i++) {
			// at what position in the board the coin will be placed
			int a = (int) Math.floor(Math.random() * gameSquares);

			// avoid repetition by checking if there is already a coin
			// if already a coin
			while (gameBoard[a]) {
				// change value of a
				// at what position in the board the coin will be placed
				a = (int) Math.floor(Math.random() * gameSquares);
			}

			gameBoard[a] = true;

		}

	}

	// make the board view
	public String boardView() {
		String boardWithStrings = "";
		// int n to increase array position for the coins
		int n = 0;
		// check to see where the coins are on the board
		for (int k = 0; k < gameSquares; k++) {
			// if there is a coin
			if (gameBoard[k]) {
				// get the position of the coin
				rearrangedCoinPosition[n] = k;
				// increase n value so another position can be held at another
				// position in the array
				n = n + 1;
				// add an "o" to denote the coin
				boardWithStrings = boardWithStrings + "o";
			}

			else
				// otherwise add a blank space

				boardWithStrings += "_";

		}

		return boardWithStrings;
	}

	

	

	// check to see if what the user has inputted is an integer
	public boolean checkInteger(String string) {
		try {
			// try to change the string to integer
			Integer.parseInt(string);

		}
		// if not an integer
		catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	// check to see if the nth coin the user wants to move is a coin available
	// on the board
	public boolean checkValidCoin(String string) {
		int coinValue = Integer.parseInt(string);
		if (coinValue > 0 && coinValue <= coins)
			return true;
		return false;
	}

	// check if coin can be moved the said spaces to the left
	public boolean checkLeftValid(String totalPlaces, int positionOfCoinInGrid) {
		int totalPlacesInt = Integer.parseInt(totalPlaces);
		if (totalPlacesInt < 0)
			return false;
		else {
			for (int i = 1; i <= totalPlacesInt; i++) {
				// if there already is a coin where the current coin is trying
				// to be placed or if it is going out of bounds
				if ((positionOfCoinInGrid - i) < 0
						|| gameBoard[positionOfCoinInGrid - i])
					// current coin cannot be placed there
					return false;

			}
		}
		// otherwise
		return true;
	}

	// check to see if the game has been won
	public boolean gameDone() {
		// check for all the n coins to be occupying the first n leftmost
		// squares in the grid
		for (int i = 0; i < coins; i++)
			// if any of the positions is not filled with coins yet
			if (!gameBoard[i])
				return false;
		// otherwise, game has been won
		
		return true;

	}

	// getter of total squares in the game
	public int getGameSquares() {
		return gameSquares;
	}

	// a method to return if there is a coin at one array position in the board
	public boolean hasCoin(int arrayPosition) {
		if (gameBoard[arrayPosition]) {
			return true;
		}
		return false;
	}

	// getter for total coins
	public int getCoins() {
		return coins;
	}

	// getter of gameBoard
	public boolean[] getBoard() {
		return gameBoard;
	}

	public int[] getCoinPosition() {
		return rearrangedCoinPosition;
	}

}
