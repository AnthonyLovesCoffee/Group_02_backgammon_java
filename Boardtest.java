import org.junit.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class Boardtest {
    
    private Board board;
    private InputCheck command;

    @BeforeEach
    void setUp() {
        board = new Board();
        board.setPlayer(1, new Player("Player 1", CheckerTemplate.RED)); // Initialize players with the virtual inputs
	    board.setPlayer(2, new Player("Player 2", CheckerTemplate.WHITE));
	    board.setCurrentPlayer(1);
	    command = new InputCheck("R12"); // Create a command with 2 dice numbers as specified for the move
        board.setFace(command); // Set the dice face values
        board.initBoard();
        board.setGameNumber(3);
    }

    @Test // Test if the board object is created
    void testBoard() {
        assertNotNull(board);
    }

    @Test // Test if the board object is created with an InputStream
    void testBoardInputStream() {
        InputStream inputStream = new ByteArrayInputStream("Player 1\nPlayer 2\n5\n".getBytes());
        Board boardWithInputStream = new Board(inputStream);
        assertNotNull(boardWithInputStream);
    }

    @Test // Test if the players are initialized correctly
    void testInitializePlayer() {
    	InputStream inputStream = new ByteArrayInputStream("Player 1\nPlayer 2\n".getBytes(StandardCharsets.UTF_8));
		board = new Board(inputStream);
        board.initPlayer(1);
        assertNotNull(board.getPlayer(1));
        assertEquals(CheckerTemplate.RED, board.getPlayer(1).getCheckerTemp());
        board.initPlayer(2);
        assertNotNull(board.getPlayer(2));
        assertEquals(CheckerTemplate.WHITE, board.getPlayer(2).getCheckerTemp());
    }

    @Test // Test if the end turn function updates the current player correctly
	void testEndTurn() {
		board.endTurn();
		assertEquals(board.getPlayer(0), board.getPlayer(2));
	}

    @Test // Test if the board is initialized correctly
    void testInitializeBoard() {
        assertEquals(2, board.getPoint(0).size());
        assertEquals(CheckerTemplate.WHITE, board.getPoint(0).peek().getCheckerTemplate());
        assertEquals(3, board.getPoint(7).size());
        assertEquals( CheckerTemplate.RED, board.getPoint(7).peek().getCheckerTemplate());
        assertEquals(5, board.getPoint(12).size());
        assertEquals( CheckerTemplate.RED, board.getPoint(12).peek().getCheckerTemplate());
    }

    @Test // Test if a move is possible based on the given command
    void testMoveIsPossible() {
        command = new InputCheck("2423");
        assertTrue(board.moveisLegal(command));
    }

    @Test // Test move() method
    void testMove() {
        command = new InputCheck("2423");
        board.move(command); // Perform the move
        assertEquals(1, board.getPoint(22).size());
        assertEquals(1, board.getPoint(23).size());
    }

    @Test // Test setDiceFace() method
    void testSetDiceFace() {
        assertEquals(1, board.getFace(1)); // Assert the expected result
        assertEquals(2, board.getFace(2));
    }

    @Test // Test getFace() method
    void testgetFace() {
        assertEquals(1, board.getFace(1)); // Assert the expected result
        assertEquals(2, board.getFace(2));
    }

    @Test // Test isOneMatchOver() method
    void testIsOneMatchOver() {
    	for (int i = 0; i < 24; i++)
	        board.getPoint(i).clear();
	    for (int i = 0; i < 2; i++)
	    	board.getBar(i).clear();
	    for (int i = 0; i < 2; i++)
	    	board.getEndpoint(i).clear();
		for (int i = 0; i < 15; i++)
			board.getPoint(20).push(new Checker(CheckerTemplate.WHITE));
		for (int i = 0; i < 15; i++)
			board.getEndpoint(0).push(new Checker(CheckerTemplate.RED));
		assertEquals(15, board.getEndpoint(0).size());
    }

    @Test // Test isWholeMatchOver() method
    void testIsWholeMatchOver() {
    	for (int i = 0; i < 3; i++)
    		board.addRoundNumber();
    	assertEquals(board.getGameNumber() + 1, board.getRoundNumber());
    }

    @Test // Test getSize() method
    void testGetSize() {
        assertEquals(5, board.getSize("uppoint")); // Assert the expected result for upLane and downLane
        assertEquals(5, board.getSize("downpoint"));
    }

    @Test // Test calculateSetPips() method
    void testCalculateSetPips() {
        command = new InputCheck("2423");
        board.move(command); // Perform the move
        command = new InputCheck("2422");
        board.move(command);
        board.calcPips(); // Calculate the set pips
        assertEquals(164, board.getPlayer(1).getPips()); // Assert the expected result
        assertEquals(167, board.getPlayer(2).getPips());
    }

    @Test // Test if the dice roll generates a non-zero dice move number
    void testMakeDiceRoll() {
        board.rollDice();
        assertNotEquals(0, board.getTotalNumMoves());
    }

    @Test // Test if the sum of the dice move steps is returned correctly
    void testgetTotalNumMoves() {
        board.setDiceStepVals(3, 4);
        assertEquals(7, board.getTotalNumMoves());
    }

    @Test // Test if the dice move number is set to zero
    void testMakeDiceSetZero() {
        board.setZeroDice();
        assertEquals(0, board.getTotalNumMoves());
    }

    @Test // Test if the correct player is returned
    void testGetPlayer() {
        Player player1 = board.getPlayer(1);
        assertEquals(InterfaceColours.RED + "Player 1" + InterfaceColours.RESET, player1.dispName());
    }

    @Test // Test if the current player is set correctly
    void testSetCurrentPlayer() {
        board.setCurrentPlayer(2);
        assertEquals(InterfaceColours.WHITE + "Player 2" + InterfaceColours.RESET, board.getPlayer(0).dispName());
    }

    @Test // Test if a valid lane is returned
    void testgetPoint() {
        assertNotNull(board.getPoint(1));
    }

    @Test // Test if a valid bar is returned
    void testGetBar() {
        assertNotNull(board.getBar(1));
    }

    @Test // Test if a valid endpoint is returned
    void testGetEndpoint() {
        assertNotNull(board.getEndpoint(1));
    }

    @Test // Test if the correct match number is returned
    void testgetGameNumber() {
        assertEquals(3, board.getGameNumber());
    }

    @Test // Test if the match number is set correctly
    void testsetGameNumber() {
        board.setGameNumber(5);
        assertEquals(5, board.getGameNumber());
    }

    @Test // Test if the correct match round number is returned
    void testgetRound() {
        board.setRound(2);
        assertEquals(2, board.getRound());
    }

    @Test // Test if the match round number is set correctly
    void testsetRound() {
        board.setRound(3);
        assertEquals(3, board.getRound());
    }

    @Test // Test if the match round number is incremented correctly
    void testaddGamenumber() {
        board.setRound(1);
        board.addGamenumber();
        assertEquals(1, board.getRound());
    }

    @Test // Test if the players' scores are set to zero
    void testsetZeroScore() {
        board.setZeroScore();
        assertEquals(0, board.getPlayer(1).getScore());
        assertEquals(0, board.getPlayer(2).getScore());
    }

    @Test // Test if the current player's score is incremented correctly
    void testaddScore() {
        board.addScore();
        assertEquals(10, board.getPlayer(0).getScore());
    }

    @Test // Test if the correct dice move steps are returned
    void testgetMoveStep() {
        board.setDiceStepVals(4, 0); // Set the dice move steps
        assertEquals(4, board.getMoveStep(1)); // Test if the dice move steps are returned correctly
        assertEquals(0, board.getMoveStep(2));
    }

    @Test // Test if the dice move steps are set correctly
    void testsetDiceStepVals() {
        board.setDiceStepVals(4, 0); // Set the dice move steps
        assertEquals(4, board.getTotalNumMoves()); // Test if the dice move steps are set correctly
    }

    @Test // Test if the new player is set correctly
    void testSetPlayer() {
        Player newPlayer = new Player("New Player", CheckerTemplate.RED); // Set a new player for index 1
        board.setPlayer(1, newPlayer);
        assertEquals(InterfaceColours.RED + "New Player" + InterfaceColours.RESET, board.getPlayer(1).dispName()); // Test if the new player at index 1 is updated correctly
    }
}
