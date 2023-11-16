import java.util.*;

public class Interface {
	private final static String BLANK = " ";
	private final static String BLANK2 = "  ";
	private final static String BLANK3 = "   ";
    Scanner key;
    InputCheck input;

    Interface(){
        key = new Scanner(System.in);
    }

	// display message when game starts
    public void gameWelcome(){
        System.out.println("Welcome to Backgammon");
    }

    public InputCheck getUserInput() {
		boolean keyEntered = false;
		do {
			System.out.print("Enter command: ");
			String in = key.nextLine();
			if (InputCheck.validMove(in)) {
				input = new InputCheck(in);
				keyEntered = true;
			} else {
				System.out.println("The command is invalid. Try again.");
			}
		}
		while (!keyEntered);
		return input;
    }

	// initial screen to get player names 
	public void gameIntro(Board board){
		System.out.print("Enter name of Player 1: ");
		board.initPlayer(1);
		System.out.println("Player 1: " + board.getPlayer(1).dispName() + ", Colour: RED");
		System.out.print("Enter name of Player 2: ");
		board.initPlayer(2);
		System.out.println("Player 2: " + board.getPlayer(2).dispName() + ", Colour: WHITE");
	}

	// start menu - options to start or exit the game
	public void starterScreen () { 
		System.out.println("Enter START to start the game or enter QUIT to exit");
	}

	
	public void displayBoard (Board board){

		
		String numberStringCurrentPlayerPips = Integer.toString(board.getPlayer(0).getPips());
		String numberStringPlayerREDScore = Integer.toString(board.getPlayer(1).getScore());
		String numberStringPlayerWHITEScore = Integer.toString(board.getPlayer(2).getScore());
		String numberStringMatch = Integer.toString(board.getMatchNumber());
		String numberStringMatchRound = Integer.toString(board.getMatchRound());
		int numberSpacesCurrentPlayerPips = 4 - numberStringCurrentPlayerPips.length();
		int numberSpacesPlayerREDScoreFormer = 7 - numberStringPlayerREDScore.length() / 2;
		int numberSpacesPlayerREDScoreLater = 8 - (numberStringPlayerREDScore.length() + 1) / 2;
		int numberSpacesPlayerWHITEScoreFormer = 7 - numberStringPlayerWHITEScore.length() / 2;
		int numberSpacesPlayerWHITEScoreLater = 8 - (numberStringPlayerWHITEScore.length() + 1) / 2;
		int numberSpacesMatch = 5 - numberStringMatch.length();
		int numberSpacesMatchRound = 5 - numberStringMatchRound.length();
		int numberUppoints = Math.max(board.getSize("uppoint"),1);
		int numberDownpoints = Math.max(board.getSize("downpoint"),1);
		System.out.println("|---------------------------------------------------------------------|");
		// print dice results
		if (board.getDiceFace(1) != board.getDiceFace(2)) {
			System.out.print("| Dice:                     ");
			
			if(board.getDiceMoveStep(1) == 1) {
				System.out.print(board.getDiceFace(1));
			} else
				System.out.print(" ");

			System.out.print("              ");

			if(board.getDiceMoveStep(2) == 1) {
				System.out.print(board.getDiceFace(2));
			} else
				System.out.print(" ");

			System.out.print("                          |");
		}
		else if (board.getDiceFace(1) == board.getDiceFace(2)) {
			System.out.print("| Dice:      ");
			if(board.getDiceMoveStep(1) >= 1) {
				System.out.print(board.getDiceFace(1));
			} else
				System.out.print(" ");
			System.out.print("              ");
			if(board.getDiceMoveStep(1) >= 2) {
				System.out.print(board.getDiceFace(1));
			} else
				System.out.print(" ");
			System.out.print("              ");
			if(board.getDiceMoveStep(1) >= 3) {
				System.out.print(board.getDiceFace(1));
			} else
				System.out.print(" ");
			System.out.print("              ");
			if(board.getDiceMoveStep(1) == 4) {
				System.out.print(board.getDiceFace(1));
			} else
				System.out.print(" ");
			System.out.print("           |\n");
		}

		// Printing Current player (Current player is indicated)
		if (board.getPlayer(0).getColourString() == "RED") {
			board.calcPips(); // calulates pip before printing
			System.out.print("\n| Current player's color: " + InterfaceColours.RED + board.getPlayer(0).getColourString() + InterfaceColours.RESET + "                               pips:  " +  board.getPlayer(0).getPips() + "|\n");
		} else if (board.getPlayer(0).getColourString() == "WHITE"){
			board.calcPips(); // calulates pip before printing
			System.out.print("\n| Current player's color: " + InterfaceColours.WHITE + board.getPlayer(0).getColourString() + InterfaceColours.RESET + "                             pips:  " + board.getPlayer(0).getPips()  + "|\n" );
		}

		// Top portion of board
		System.out.println("|");
		System.out.println("|---------------------------------------------------------------------|---------------|");
		System.out.println("| " + InterfaceColours.WHITE + "13   14   15   16   17   18" + InterfaceColours.RESET + " | " + InterfaceColours.WHITE + "B2" + InterfaceColours.RESET + " | " + InterfaceColours.WHITE + "19   20   21   22   23   24" + InterfaceColours.RESET + " | " + InterfaceColours.RED + "E1" + InterfaceColours.RESET + " |   " + InterfaceColours.RED + board.getPlayer(1).getColourString() + InterfaceColours.RESET + " Score   |");
		System.out.print("| " + InterfaceColours.RED + "12   11   10   09   08   07" + InterfaceColours.RESET);
		System.out.print(" | " + InterfaceColours.WHITE + "B2"  + InterfaceColours.RESET + " | ");
		System.out.print(InterfaceColours.RED + "06   05   04   03   02   01" + InterfaceColours.RESET + " | " + InterfaceColours.RED + "E1" + InterfaceColours.RESET + " |");
		for (int i = 0; i < numberSpacesPlayerREDScoreFormer; i++) 
            System.out.print(" ");
		System.out.print(board.getPlayer(1).getScore());
		for (int i = 0; i < numberSpacesPlayerREDScoreLater; i++)
            System.out.print(" ");
		System.out.println("|");
		// end  of top portion






		// Middle portion
		for (int row = 0; row < numberUppoints; row++) {
			System.out.print("| ");
			for (int up = 11; up > 6; up--) {
				Stack<Checker> points = board.getPoint(up);
				if (row < points.size()) {
					System.out.print(points.get(row) + BLANK3);
		        } else
		            System.out.print(BLANK2 + BLANK3);
			}
			Stack<Checker> points6 = board.getPoint(6);
			if (row < points6.size()) {
				System.out.print(points6.get(row) + " | ");
	        } else
	            System.out.print(BLANK2 + " | ");
			Stack<Checker> bar = board.getBar(1);
				if (row < bar.size()) {
					System.out.print(bar.get(row) + " | ");
		        } else
		        	System.out.print(BLANK2 + " | ");
			for (int up = 5; up > 0; up--) {
				Stack<Checker> points = board.getPoint(up);
				if (row < points.size()) {
					System.out.print(points.get(row) + BLANK3);
		        } else
		            System.out.print(BLANK2 + BLANK3);
			}
			Stack<Checker> points0 = board.getPoint(0);
			if (row < points0.size()) {
				System.out.print(points0.get(row));
	        } else
	            System.out.print(BLANK2);
			if (row==0) {
				if (board.getEndpoint(0).size() < 10)
			        System.out.print(" | " + InterfaceColours.RED + "0" + board.getEndpoint(0).size() + InterfaceColours.RESET + " |---------------|");
			    else
			    	System.out.print(" | " + InterfaceColours.RED + board.getEndpoint(0).size() + InterfaceColours.RESET + " |---------------|");
			} else
				System.out.print(" |    |");
			System.out.println();
		}
		System.out.println("|-----------------------------|----|-----------------------------|----|");
		for (int row = 0; row < numberDownpoints; row++) {
			System.out.print("| ");
			for (int up = 12; up < 17; up++) {
				Stack<Checker> points = board.getPoint(up);
				if (row < numberDownpoints - points.size()) {
					System.out.print(BLANK2 + BLANK3);
		        } else
		            System.out.print(points.get(numberDownpoints - row - 1) + BLANK3);
			}
			Stack<Checker> points17 = board.getPoint(17);
			if (row < numberDownpoints - points17.size()) {
				System.out.print(BLANK2 + " | ");
	        } else
	            System.out.print(points17.get(numberDownpoints - row - 1) + " | ");
			Stack<Checker> bar = board.getBar(0);
				if (row < numberDownpoints - bar.size()) {
					System.out.print(BLANK2 + " | ");
		        } else
		        	System.out.print(bar.get(numberDownpoints - row - 1) + " | ");
			for (int up = 18; up < 23; up++) {
				Stack<Checker> points = board.getPoint(up);
				if (row < numberDownpoints - points.size()) {
					System.out.print(BLANK2 + BLANK3);
		        } else
		        	System.out.print(points.get(numberDownpoints - row - 1) + BLANK3);
			}
			Stack<Checker> points23 = board.getPoint(23);
			if (row < numberDownpoints - points23.size()) {
				System.out.print(BLANK2);
	        } else
	            System.out.print(points23.get(numberDownpoints - row - 1));
			if (row == numberDownpoints - 1) {
				if (board.getEndpoint(1).size() < 10)
			        System.out.print(" | " + InterfaceColours.WHITE + "0" + board.getEndpoint(1).size() + InterfaceColours.RESET + " |---------------|");
			    else
			    	System.out.print(" | " + InterfaceColours.WHITE + board.getEndpoint(1).size() + InterfaceColours.RESET + " |---------------|");
			} else
				System.out.print(" |    |");
			System.out.println();
		}
		// end of middle portion



		// Bottom portion of board
		System.out.print("| " + InterfaceColours.WHITE + "12   11   10   09   08   07" + InterfaceColours.RESET);
		System.out.print(" | " + InterfaceColours.RED + "B1" + InterfaceColours.RESET + " | ");
		System.out.println(InterfaceColours.WHITE + "06   05   04   03   02   01" + InterfaceColours.RESET + " | " + InterfaceColours.WHITE + "E2" + InterfaceColours.RESET + " |  " + InterfaceColours.WHITE + board.getPlayer(2).getColourString() + InterfaceColours.RESET + " Score  |");
		System.out.print("| " + InterfaceColours.RED + "13   14   15   16   17   18" + InterfaceColours.RESET + " | " + InterfaceColours.RED + "B1" + InterfaceColours.RESET + " | " + InterfaceColours.RED + "19   20   21   22   23   24" + InterfaceColours.RESET + " | " + InterfaceColours.WHITE + "E2" + InterfaceColours.RESET + " |");
		for (int i = 0; i < numberSpacesPlayerWHITEScoreFormer; i++)
            System.out.print(" ");
		System.out.print(board.getPlayer(2).getScore());
		for (int i = 0; i < numberSpacesPlayerWHITEScoreLater; i++)
            System.out.print(" ");
		System.out.println("|");
		System.out.println("|---------------------------------------------------------------------|---------------|");
		// end of bottom portion



		/* Example board (for formatting purposes)
		 * 
		 * 		
		 * 
		System.out.print("  \n \n \n \n \n ");
		// printing the player board
		System.out.println("|---------------------------------------------------------------------|");
		System.out.println("| [0;37m13   14   15   16   17   18[0m | [0;37mB2[0m | [0;37m19   20   21   22   23   24[0m |    | ");
		System.out.println( "| [0;31m12   11   10   09   08   07[0m | [0;37mB2[0m | [0;31m06   05   04   03   02   01[0m |    |\n" + //
							"| [0;37mo[0m                  [0;31mo[0m        |    | [0;31mo[0m                         [0;37mo[0m |    |\n" + //
							"| [0;37mo[0m                  [0;31mo[0m        |    | [0;31mo[0m                         [0;37mo[0m |    |\n" + //
							"| [0;37mo[0m                  [0;31mo[0m        |    | [0;31mo[0m                           |    |\n" + //
							"| [0;37mo[0m                           |    | [0;31mo[0m                           |    |\n" + //
							"| [0;37mo[0m                           |    | [0;31mo[0m                           |    |\n" + //
							"|-----------------------------|----|-----------------------------|----|\n" + //
							"| [0;31mo[0m                           |    | [0;37mo[0m                           |    |\n" + //
							"| [0;31mo[0m                           |    | [0;37mo[0m                           |    |\n" + //
							"| [0;31mo[0m                   [0;37mo[0m       |    | [0;37mo[0m                           |    |\n" + //
							"| [0;31mo[0m                   [0;37mo[0m       |    | [0;37mo[0m                         [0;31mo[0m |    |\n" + //
							"| [0;31mo[0m                   [0;37mo[0m       |    | [0;37mo[0m                         [0;31mo[0m |    |\n" + //
							"| [0;37m12   11   10   09   08   07[0m | [0;31mB1[0m | [0;37m06   05   04   03   02   01[0m |    | \n" + //
							"| [0;31m13   14   15   16   17   18[0m | [0;31mB1[0m | [0;31m19   20   21   22   23   24[0m |    |");

		 * 
		 * 
		 */







	}


	// prints the result after dice rolled
	public void printDice (int face1, int face2) { 
		if (face1 != face2) {
			System.out.println("The number of 2 dice thrown are " + face1 + " and " + face2 + ". You can move 2 times");
		} else if (face1 == face2)
			System.out.println("The number of 2 dice thrown are " + face1 + " and " + face2 + ". You can move 4 times");
	}

	// decide who plays first based on roll
	public void firstTurn (Board board) { // Determine the first player based on the first dice roll and will set current player 
		do {
			board.rollDice();
			if (board.getDiceFace(1) > board.getDiceFace(2)) {
				System.out.println("Die 1 is " + board.getDiceFace(1) + ". Die 2 is " + board.getDiceFace(2)+ ". Dice 1 > Dice 2 -> Red goes first.");
				board.setCurrentPlayer(1); // Sets player 1 as current player if going first 
			} else if (board.getDiceFace(1) < board.getDiceFace(2)) {
				System.out.println("Die 1 is " + board.getDiceFace(1) + ". Die 2 is " + board.getDiceFace(2)+ ". Dice 2 > Dice 1 -> White goes first.");
				board.setCurrentPlayer(2); // Sets player 2 as current player if going first 
			} else if (board.getDiceFace(1) == board.getDiceFace(2)) {
				System.out.println("Die 1 is " + board.getDiceFace(1) + ". Die 2 is " + board.getDiceFace(2)+ ". Dice 1 = Dice 2 -> Reroll");
			}
		} while (board.getDiceFace(1) == board.getDiceFace(2));
	}




	public void GameOver (Board board) { // Display a message when the whole match is over
		if (board.getPlayer(1).getScore() > board.getPlayer(2).getScore()) {
			System.out.println(board.getPlayer(1).dispName() + " wins the game!.");
		} else if (board.getPlayer(1).getScore() < board.getPlayer(2).getScore()) {
			System.out.println(board.getPlayer(2).dispName() + " wins the game!.");
		} else if (board.getPlayer(1).getScore() == board.getPlayer(2).getScore())
			System.out.println("Its a draw :(");
		System.out.println("Game over.");
	}


	public void displayQuit () { // Display a message when the user quits
		System.out.println("You Quit. Loser");
	}
	
	public void playerTurnCurrent (Player player) { // Display a message when the current player's turn is over
		System.out.println(player + "(" + player.getColourString() + ") finishes moving. ☺ ");
	}
	
	public void playerTurnNext (Player player) { // Display a message when the next player's turn starts
		System.out.println("Now it's the " + player + "(" + player.getColourString() + ")'s turn to play. Lets go !");
	}


}