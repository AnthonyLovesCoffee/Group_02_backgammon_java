public class Game {
    Board board = new Board();
    Interface intface = new Interface();
    InputCheck command;

    public void initGame(){
        int startControl = 0;
        boolean matchOverDisplayed = false;
        intface.gameWelcome();
        intface.starterScreen();
        do {
			boolean cmdEntered = false;
			do {
				command = intface.getUserInput();

                // when user has started the game 
				if (startControl == 1 && !board.isMatchOver()){
					if (command.roll()) {
						board.rollDice();
						intface.printDice(board.getDiceFace(1), board.getDiceFace(2));
						intface.displayBoard(board);
						intface.showLegalMoves(board);
						cmdEntered = true;
					} 
					else if (command.start()) {
                        intface.starterScreen();
						startControl--;
						cmdEntered = true;
					} 
					else if (command.quit()) {
						cmdEntered = true;
					} 
					else if (command.move()) {
						if (board.moveisLegal(command)) {
							board.move(command);
							board.calcPips();
							intface.displayBoard(board);
							if (board.getTotalNumMoves() != 0) {
								intface.showLegalMoves(board);
							} 
							else if (board.getTotalNumMoves() == 0){
								board.endTurn();
							}
							cmdEntered = true;
						} 
						else {
							intface.printInvalidCmd();
						}
					}
					else if (command.showHint()) {
						intface.controls();
					}
					else if (command.showPip()) {
						intface.printPips(board);
					}
					if (!board.isMatchOver() && matchOverDisplayed){
					    if (command.start()) {
							startControl--;
							matchOverDisplayed = false;
							cmdEntered = true;
						} else if (command.quit())
							cmdEntered = true;
					}
					if (!matchOverDisplayed  && !command.start()){
						matchOverDisplayed = true;
					}
				}
					
				
				// if user has not started a game yet
				if (startControl == 0) {
					if (command.start()) {
                        intface.gameIntro(board);
						board.initBoard();
						board.calcPips();
						intface.firstTurn(board);
						intface.printDice(board.getDiceFace(1), board.getDiceFace(2));
						intface.displayBoard(board);
						intface.showLegalMoves(board);
						startControl++;
						cmdEntered = true;
					} 
					else if (command.quit()){
						cmdEntered = true;
					}
					else if (command.showHint()) {
						intface.controls();
					}
					else if (command.showPip()) {
						intface.printPips(board);
					}
				}
			} while (!cmdEntered);
		} while (!command.quit() && !board.isMatchOver());
        
		if (board.isMatchOver()) {
			intface.GameOver(board);
		} 
		else {
			intface.displayQuit();
		}		
	}
}
