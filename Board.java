import java.io.InputStream;
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
}
