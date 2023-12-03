public class InputCheck {
    private enum gameCommand{
        MOVE,
        ROLL,
        QUIT,
        START,
        PIP,
        HINT,
        SHOWLEGALMOVES,
        SETDICE
    };

    private gameCommand command; // 3 possible commands
    private int[] faces;
    private String srcPile, destPile;
    private static String[] legalMoves = new String[100];
    private String[] dice;

    // parse user's input and assigning values to appropiate variable
    InputCheck(String input){
        String inputUpper = input.toUpperCase().trim(); // ensure case-insensitivity
        if (inputUpper.equals("ROLL")){
            command = gameCommand.ROLL;
        }
        else if (inputUpper.equals("QUIT")){
            command = gameCommand.QUIT;
        }
        else if (inputUpper.equals("START")){
            command = gameCommand.START;
        }
        else if (inputUpper.equals("PIP")){
            command = gameCommand.PIP;
        }
        else if (inputUpper.equals("HINT")){
            command = gameCommand.HINT;
        }
        else if (inputUpper.equals("MOVES")) {
			command = gameCommand.SHOWLEGALMOVES;
		}
        else if (inputUpper.equals("([1-9]|0[1-9]|[1-9][0-9])") && legalMoves[Integer.parseInt(inputUpper) - 1] != null){
            command = gameCommand.MOVE;
            srcPile = legalMoves[Integer.parseInt(inputUpper) - 1].substring(0,2); ;
            srcPile = legalMoves[Integer.parseInt(inputUpper) - 1].substring(2,4); 
        }
        else if (inputUpper.matches("(0[1-9]|1[0-9]|2[0-4]|B[1-2])(0[1-9]|1[0-9]|2[0-4]|E[1-2])")) {
			command = gameCommand.MOVE;
			srcPile = inputUpper.substring(0, 2);
			destPile = inputUpper.substring(2, 4);
        }
        else if (inputUpper.matches("R[1-6][1-6]")) {
			command = gameCommand.SETDICE;
			dice[0] = inputUpper.substring(1, 2);
			dice[1] = inputUpper.substring(2, 3);
			faces[0] = Integer.parseInt(dice[0]);
			faces[1] = Integer.parseInt(dice[1]);
        }
    }
    // check if input is valid move/command
    public static boolean validMove(String input){
        String inputUpper = input.toUpperCase().trim();
        return (inputUpper.equals("QUIT")) || (inputUpper.equals("ROLL")) ||
                (inputUpper.matches("START")) || (inputUpper.equals("PIP")) || (inputUpper.equals("HINT")) 
                || (inputUpper.equals("MOVES")) || inputUpper.matches("R[1-6][1-6]") || inputUpper.matches("(0[1-9]|1[0-9]|2[0-4]|B[1-2])(0[1-9]|1[0-9]|2[0-4]|E[1-2])") 
                || input.matches("([1-9]|0[1-9]|[1-9][0-9])") && legalMoves[Integer.parseInt(input) - 1] != null;
    }

    // checking source of move
    public boolean fromBarMove(){
        return srcPile.matches("B1|B2");
    }
    public boolean fromPointMove(){
        return srcPile.matches("0[1-9]|1[0-9]|2[0-4]");
    }

    // checking destination of move
    public boolean toEndMove(){
        return destPile.matches("E1|E2");
    }
    public boolean toPointMove(){
        return destPile.matches("0[1-9]|1[0-9]|2[0-4]");
    }

    // convert bar and endpoints to appropriate index
    private int barToInt(String bar){
        return switch(bar){
        case "B1" -> 0;
        case "B2" -> 1;
        default -> 0;
        };
    }
    private int endToInt(String endpoint){
        return switch(endpoint){
        case "E1" -> 0;
        case "E2" -> 1;
        default -> 0;
        };
    }


    public static boolean ifText (String input) { // Checks if the input string represents a text command.
		String inputFormatted = input.trim();
		return inputFormatted.matches("file:(.+\\.txt)");
	}

    public static String recText(String input) { // recievesa the text from the input string if it's a text command.
    String inputFormatted = input.trim();
    if (inputFormatted.length() > 5) {
        return inputFormatted.substring(5);
    } else 
        return ""; // Only for Test.
}


    // boolean methods 
    public boolean move(){
        return command == gameCommand.MOVE;
    }
    public boolean roll(){
        return command == gameCommand.ROLL;
    }
    public boolean quit(){
        return command == gameCommand.QUIT;
    }
    public boolean start(){
        return command == gameCommand.START;
    }
    public boolean showPip(){
        return command == gameCommand.PIP;
    }
    public boolean showHint(){
        return command == gameCommand.HINT;
    }
    public boolean showLegalMoves(){
        return command == gameCommand.SHOWLEGALMOVES;
    }
    public boolean setDice () { // Checks if the command is a SETFACE command.
		return command == gameCommand.SETDICE;
	}
    public boolean checkText(String input){
        String trimmed = input.trim();
        return trimmed.matches("test:(.+\\.txt)");
    }

    // return the index of the source pile
    public int getSrcPile() {
        if(fromPointMove()){
            return Integer.parseInt(srcPile) - 1;
        }
        else{
            return barToInt(srcPile);
        }
    }

    // return index of destination pile
    public int getDestPile(){
        if (toPointMove()){
            return Integer.parseInt(destPile) - 1;
        }
        else{
            return endToInt(destPile);
        }
    }

    // returns the  face value 
    public int getFace (int index) { 
		return switch (index) {
			case 1 -> faces[0];
			case 2 -> faces[1];
			default -> 0;
		};
	}

    // adds all the legal moves at an index to array
    public static void setLegalMoves (int i, String legalMove) { 
		legalMoves[i] = legalMove;
	}
	
    // returns array of legal moves
	public static String[] getLegalMoves () { 
		return legalMoves;
	}
}
