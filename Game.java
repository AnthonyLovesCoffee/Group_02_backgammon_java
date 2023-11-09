public class Game {
    Board board = new Board();
    Interface intface = new Interface();
    InputCheck command;

    public void initGame(){
        int startControl = 0;
        boolean matchOverDisplayed = false;
        intface.gameWelcome();
        do {
			boolean commandDone = false;
			do {
				command = intface.getUserInput();
                // when user has started the game 
				if (startControl == 1 && !board.isMatchOver())
					if (command.roll()) {
						board.rollDice();
						intface.printDice(board.getDiceFace(1), board.getDiceFace(2));
						intface.displayBoard(board);
						commandDone = true;
					} 
					 else if (command.start()) {
                        intface.starterScreen();
						startControl--;
						commandDone = true;
					} else if (command.quit()) {
						commandDone = true;
					}  
					if (!board.isMatchOver() && matchOverDisplayed){
					    if (command.start()) {
							intface.starterScreen();
							startControl--;
							matchOverDisplayed = false;
							commandDone = true;
						} else if (command.quit())
							commandDone = true;
					if (!matchOverDisplayed  && !command.start())
						matchOverDisplayed = true;
				}
                // if user has not started a game yet
				if (startControl == 0) {
					if (command.start()) {
						intface.gameIntro(board);
						board.initBoard();
						intface.firstTurn(board);
						intface.printDice(board.getDiceFace(1), board.getDiceFace(2));
						intface.displayBoard(board);
						startControl++;
						commandDone = true;
					} 
					 else if (command.quit())
						commandDone = true;
				}
			} while (!commandDone);
		} while (!command.quit() && !board.isMatchOver());
		if (board.isMatchOver()) {
			intface.displayWholeMatchOver(board);
		} else
			intface.displayQuit();
	}
}

