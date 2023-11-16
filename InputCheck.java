public class InputCheck {
    private enum gameCommand{
        MOVE,
        ROLL,
        QUIT,
        START,
        PIP,
        HINT,
        SHOWPOSSIBLEMOVES
    };

    private gameCommand command; // 3 possible commands
    private int[] faces;
    private String srcPile, destPile;
    private static String[] legalMoves = new String[100];

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
        else if (inputUpper.equals("([1-9]|0[1-9]|[1-9][0-9])") && legalMoves[Integer.parseInt(inputUpper) - 1] != null){
            command = gameCommand.MOVE;
            srcPile = legalMoves[Integer.parseInt(inputUpper) - 1].substring(0,2); ;
            srcPile = legalMoves[Integer.parseInt(inputUpper) - 1].substring(2,4); 
        }
    }

    // check if input is valid move/command
    public static boolean validMove(String input){
        String inputUpper = input.toUpperCase().trim();
        return (inputUpper.equals("QUIT")) || (inputUpper.equals("ROLL")) ||
                (inputUpper.matches("START"));
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
    public boolean showHints(){
        return command == gameCommand.HINT;
    }
    public boolean showPossibleMoves(){
        return command == gameCommand.SHOWPOSSIBLEMOVES;
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
    public static void setAllowedMoves (int i, String legalMove) { 
		legalMoves[i] = legalMove;
	}
	
    // returns array of legal moves
	public static String[] getLegalMoves () { 
		return legalMoves;
	}
}
