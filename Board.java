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
	private int matchRoundNumber = 1;
	private int matchNumber;

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
        // clear ALL from outset 
		for (int i = 0; i < 24; i++)
	        points.get(i).clear();
	    for (int i = 0; i < 2; i++)
	    	bars.get(i).clear();
	    for (int i = 0; i < 2; i++)
	    	endpoints.get(i).clear();
        // adding checkers to starting positions
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

	public void setCurrentPlayer (int playerIndex){
		this.players[0] = players[playerIndex];
	}

	private boolean isPathClear (int start, int end) { // Check if the path is clear for a move
		if (start < 0)
	        start = 0;
		if (end > 23)
			end = 23;
	    for (int i = start + 1; i < end; i++) {
	        if (!points.get(i).empty() && points.get(i).peek().getCheckerTemplate() == players[0].getCheckerTemp()) {
	            return false;
	        }
	    }
	    return true;
	}
	
	private int getPlayerNumber () { // Get the player number
    	if (players[0].getCheckerTemplate() == CheckerTemplate.WHITE) {
    		return 1;
    	} else // players[0].getCheckerTemplate() == PieceEntity.R
    		return 0;
    }
	// determines furthest occupied point in the player's inner table or the starting point of their move
	private int findFurthestOccupiedpoint (InputCheck command, List<Stack<Checker>> points, int playerIndex) { 
		int maxpoint = -1;
		if (playerIndex == 1) {
			for (int i = 0; i <= 5; i++) {
	            Stack<Checker> pointi = points.get(i);
	            if (!pointi.isEmpty())
	                maxpoint = i;
	        }
			if (command.getSrcPile() >= 6)
            	maxpoint = command.getSrcPile();
		} 
		
		else if (playerIndex == 2) {
			for (int i = 23; i >= 18; i--) {
	            Stack<Checker> pointi = points.get(i);
	            if (!pointi.isEmpty())
	                maxpoint = i;
	        }
			if (command.getSrcPile() <= 17)
            	maxpoint = command.getSrcPile();
		}
		return maxpoint;
	}

    // Roll the dice
    public void rollDice () { 
		dice.roll();
	}

	public Stack<Checker> getPoint(int i){
		return points.get(i);
	}  

	public Stack<Checker> getBar (int i){
		return bars.get(i);
	}

	public Stack<Checker> getEndpoint (int i){
		return endpoints.get(i);
	}

	public int getMatchNumber(){
		return matchNumber;
	}

	public void setMatchNumber(int matchnum){
		this.matchNumber = matchnum;
	}

	public int getMatchRound(){
		return matchRoundNumber;
	}

	public void setMatchRound(int roundnum){
		this.matchRoundNumber = roundnum;
	}


	public int getSize (String index) { // Get the size of the largest stack of Checkers for the specified index (uppoint or downpoint)
		int uppointSize = 0;
		int downpointSize = 0;
		List<Stack<Checker>> up12points = points.subList(0, 12);
		List<Stack<Checker>> down12points = points.subList(12, 24);
		for (Stack<Checker> point : up12points)
		    if (point.size() > uppointSize)
		    	uppointSize = point.size();
		if (bars.get(1).size() > uppointSize)
	    	uppointSize = bars.get(1).size();
		for (Stack<Checker> point : down12points)
		    if (point.size() > downpointSize)
		    	downpointSize = point.size();
		if (bars.get(0).size() > downpointSize)
	    	downpointSize = bars.get(0).size();
		return switch(index) {
			case "uppoint" -> uppointSize;
			case "downpoint" -> downpointSize;
			default -> 0;
		};
	}

	// calculate number of pips
	public void calcPips () { 
		int pip1 = 0;
		int pip2 = 0;
   		for (int i=0; i<24; i++) {
   			if (!points.get(i).empty())
   				if (points.get(i).peek().getCheckerTemplate() == CheckerTemplate.RED) {
   					pip1 += (i+1)*points.get(i).size();
   				} else if (points.get(i).peek().getCheckerTemplate() == CheckerTemplate.WHITE)
   					pip2 += (24-i)*points.get(i).size();
   			if (endpoints.get(0).size() == 15)
   				pip1 = 0;
   			if (endpoints.get(1).size() == 15)
   				pip2 = 0;
   			players[1].setPips(pip1);
   			players[2].setPips(pip2);
   		}
	}
	

//

}
