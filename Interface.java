import java.util.*;

public class Interface {
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
			System.out.print("           |");
		}

		// printing the player board
		System.out.print("\n");
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

	}
	
	// prints the result after dice rolled
	public void printDice (int face1, int face2) { 
		if (face1 != face2) {
			System.out.println("The number of 2 dice thrown are " + face1 + " and " + face2 + ". You can move 2 times");
		} else if (face1 == face2)
			System.out.println("The number of 2 dice thrown are " + face1 + " and " + face2 + ". You can move 4 times");
	}

	// decide who plays first based on roll
	public void firstTurn (Board board) { // Determine the first player based on the first dice roll
		do {
			board.rollDice();
			if (board.getDiceFace(1) > board.getDiceFace(2)) {
				System.out.println("Die 1 is " + board.getDiceFace(1) + ". Die 2 is " + board.getDiceFace(2)+ ". Dice 1 > Dice 2 -> Red goes first.");
			} else if (board.getDiceFace(1) < board.getDiceFace(2)) {
				System.out.println("Die 1 is " + board.getDiceFace(1) + ". Die 2 is " + board.getDiceFace(2)+ ". Dice 2 > Dice 1 -> White goes first.");
			} else if (board.getDiceFace(1) == board.getDiceFace(2)) {
				System.out.println("Die 1 is " + board.getDiceFace(1) + ". Die 2 is " + board.getDiceFace(2)+ ". Dice 1 = Dice 2 -> Reroll");
			}
		} while (board.getDiceFace(1) == board.getDiceFace(2));
	}

	// print a message when the match is over
	public void displayWholeMatchOver (Board board) { 
		System.out.println("Game over.");
	}

	// print  message when user quits
	public void displayQuit () { 
		System.out.println("Quit.");
	}
}