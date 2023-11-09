import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Board {
    public static final int NUM_POINTS = 24;
	public static final int NUM_BARS = CheckerTemplate.values().length; 
	public static final int NUM_ENDPOINTS = CheckerTemplate.values().length; 
	
    private List<Stack<Checker>> points;
	private List<Stack<Checker>> bars;
	private List<Stack<Checker>> endpoints;
	
	private Player[] players;
	private Interface intrface;
	private Scanner input;
	private Dice dice;

    Board(){
        intrface = new Interface();
        input = new Scanner(System.in);
        dice = new Dice();
        // 3 player instances - 2 ingame, 1 for pregame
        this.players = new Player[3];

        points = new ArrayList<>(NUM_POINTS);
        bars = new ArrayList<>(NUM_BARS);
        endpoints = new ArrayList<>(NUM_ENDPOINTS);

        // initialise points, bars, terminus'
        for (int i=0; i<NUM_POINTS; i++){
            points.add(new Stack<>());
        }
        for (int i=0; i<NUM_BARS; i++){
            bars.add(new Stack<>());
        }  
        for (int i=0; i<NUM_ENDPOINTS; i++){
            endpoints.add(new Stack<>());
        }
    }

    // initialize the board
    public void initBoard () { 
		for (int i = 0; i < 24; i++)
	        points.get(i).clear();
	    for (int i = 0; i < 2; i++)
	    	bars.get(i).clear();
	    for (int i = 0; i < 2; i++)
	    	endpoints.get(i).clear();
		for (int i = 0; i < 2; i++) {
			points.get(0).push(new Checker(CheckerTemplate.WHITE));
			points.get(23).push(new Checker(CheckerTemplate.RED));
		}
		for (int i = 0; i < 3; i++) {
			points.get(16).push(new Checker(CheckerTemplate.WHITE));
			points.get(7).push(new Checker(CheckerTemplate.RED));
		}
		for (int i = 0; i < 5; i++) {
			points.get(11).push(new Checker(CheckerTemplate.WHITE));
			points.get(18).push(new Checker(CheckerTemplate.WHITE));
			points.get(5).push(new Checker(CheckerTemplate.RED));
			points.get(12).push(new Checker(CheckerTemplate.RED));
		}
	}

    // initialise player given index in array
    public void initPlayer (int playerIndex) {
		String playerName = input.nextLine();
		if (playerIndex == 1) {
			players[playerIndex] = new Player(playerName, CheckerTemplate.RED);
		} else if (playerIndex == 2)
			players[playerIndex] = new Player(playerName, CheckerTemplate.WHITE);
	}

    // return player object at given index
    public Player getPlayer (int playerIndex) { 
    	return switch (playerIndex) {
			case 0 -> players[0];
			case 1 -> players[1];
			case 2 -> players[2];
			default -> players[0];
    	};
    }

    // set dice face according to command
    public void setDiceFace (InputCheck command) { 
		dice.setFace(command.getFace(1), command.getFace(2));
	}
	
    // return dice face for the given index
	public int getDiceFace (int index) { 
		return switch (index) {
			case 1 -> dice.getFace(1);
			case 2 -> dice.getFace(2);
			default -> 0;
		};
	}

    // return move step of the dice given the index
    public int getDiceMoveStep (int index) { 
    	return switch (index) {
			case 1 -> dice.getMoveStep(1);
			case 2 -> dice.getMoveStep(2);
			default -> 0;
		};
    }

    // return if game is over
    public boolean isMatchOver () { 
		for (Stack<Checker> endpoint : endpoints)
			if (endpoint.size() == 15)
				return true;
		return false;
	}

    // Roll the dice
    public void rollDice () { 
		dice.roll();
	}
}
