import org.junit.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class BoardTest {
    
    private Board board;
    private InputCheck cmd;

    // setting up a board with users and specified roll before each test
    @BeforeEach
    void setUp() {
        board = new Board();
        board.setPlayer(1, new Player("Anthony", CheckerTemplate.RED));
	    board.setPlayer(2, new Player("Stephen", CheckerTemplate.WHITE));
	    board.setCurrentPlayer(1);
	    cmd = new InputCheck("ROLL12"); 
        board.setFace(cmd);
        board.initBoard();
        board.setGameNumber(2);
    }

    // test to ensure that the board object is successfully created
    @Test 
    void testBoard() {
        assertNotNull(board);
    }

    
    @Test 
    void testBoardInputStream() {
        InputStream inputStream = new ByteArrayInputStream("Player 1\nPlayer 2\n5\n".getBytes());
        Board boardWithInputStream = new Board(inputStream);
        assertNotNull(boardWithInputStream);
    }

    @Test
    void testInitPlayer() {
    	InputStream inputStream = new ByteArrayInputStream("Player 1\nPlayer 2\n".getBytes(StandardCharsets.UTF_8));
		board = new Board(inputStream);
        board.initPlayer(1);
        assertNotNull(board.getPlayer(1));
        assertEquals(CheckerTemplate.RED, board.getPlayer(1).getCheckerTemp());
        board.initPlayer(2);
        assertNotNull(board.getPlayer(2));
        assertEquals(CheckerTemplate.WHITE, board.getPlayer(2).getCheckerTemp());
    }

    @Test 
	void testEndTurn() {
		board.endTurn();
		assertEquals(board.getPlayer(0), board.getPlayer(2));
	}

    @Test 
    void testInitBoard() {
        assertEquals(2, board.getPoint(0).size());
        assertEquals(CheckerTemplate.WHITE, board.getPoint(0).peek().getCheckerTemplate());
        assertEquals(3, board.getPoint(7).size());
        assertEquals( CheckerTemplate.RED, board.getPoint(7).peek().getCheckerTemplate());
        assertEquals(5, board.getPoint(12).size());
        assertEquals( CheckerTemplate.RED, board.getPoint(12).peek().getCheckerTemplate());
    }

    @Test 
    void testLegalMove() {
        cmd = new InputCheck("2423");
        assertTrue(board.moveisLegal(cmd));
    }

    @Test 
    void testMove() {
        cmd = new InputCheck("2423");
        board.move(cmd); 
        assertEquals(1, board.getPoint(22).size());
        assertEquals(1, board.getPoint(23).size());
    }

    @Test 
    void testgetFace() {
        assertEquals(1, board.getFace(1)); // Assert the expected result
        assertEquals(2, board.getFace(2));
    }

    @Test 
    void testRoundOver() {
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

    @Test 
    void testGameOver() {
        board.addRoundNumber();
        board.addRoundNumber();
    	assertEquals(board.getGameNumber() + 1, board.getRoundNumber());
    }

    @Test 
    void testGetSize() {
        assertEquals(5, board.getSize("uppoint")); 
        assertEquals(5, board.getSize("downpoint"));
    }

    @Test 
    void testCalcPips() {
        cmd = new InputCheck("2423");
        board.move(cmd); 
        cmd = new InputCheck("2422");
        board.move(cmd);
        board.calcPips(); 
        assertEquals(164, board.getPlayer(1).getPips()); 
        assertEquals(167, board.getPlayer(2).getPips());
    }

    @Test 
    void testrollDice() {
        board.rollDice();
        assertNotEquals(0, board.getTotalNumMoves());
    }

    @Test
    void testgetTotalNumMoves() {
        board.setDiceStepVals(3, 4);
        assertEquals(7, board.getTotalNumMoves());
    }

    @Test 
    void testSetZeroDice() {
        board.setZeroDice();
        assertEquals(0, board.getTotalNumMoves());
    }

    @Test 
    void testGetPlayer() {
        Player player1 = board.getPlayer(1);
        assertEquals(InterfaceColours.RED + "Anthony" + InterfaceColours.RESET, player1.dispName());
    }

    @Test 
    void testSetCurrentPlayer() {
        board.setCurrentPlayer(2);
        assertEquals(InterfaceColours.WHITE + "Stephen" + InterfaceColours.RESET, board.getPlayer(0).dispName());
    }

    @Test 
    void testgetPoint() {
        assertNotNull(board.getPoint(1));
    }

    @Test 
    void testGetBar() {
        assertNotNull(board.getBar(1));
    }

    @Test 
    void testGetEndpoint() {
        assertNotNull(board.getEndpoint(1));
    }

    @Test 
    void testGetGameNumber() {
        assertEquals(2, board.getGameNumber());
    }

    @Test 
    void testSetGameNumber() {
        board.setGameNumber(5);
        assertEquals(5, board.getGameNumber());
    }

    @Test 
    void testgetRound() {
        board.setRound(2);
        assertEquals(2, board.getRound());
    }

    @Test
    void testsetRound() {
        board.setRound(3);
        assertEquals(3, board.getRound());
    }

    @Test 
    void testaddGamenumber() {
        board.setRound(2);
        board.addGamenumber();
        assertEquals(2, board.getRound());
    }

    @Test
    void testsetZeroScore() {
        board.setZeroScore();
        assertEquals(0, board.getPlayer(1).getScore());
        assertEquals(0, board.getPlayer(2).getScore());
    }

    @Test 
    void testaddScore() {
        board.addScore();
        assertEquals(10, board.getPlayer(0).getScore());
    }

    @Test 
    void testGetMoveStep() {
        board.setDiceStepVals(4, 0); 
        assertEquals(4, board.getMoveStep(1));
        assertEquals(0, board.getMoveStep(2));
    }

    @Test 
    void testsetDiceStepVals() {
        board.setDiceStepVals(4, 0); 
        assertEquals(4, board.getTotalNumMoves()); 
    }

    @Test
    void testSetPlayer() {
        Player newPlayer = new Player("Franklin", CheckerTemplate.RED); 
        board.setPlayer(1, newPlayer);
        assertEquals(InterfaceColours.RED + "Franklin" + InterfaceColours.RESET, board.getPlayer(1).dispName()); 
    }
}
