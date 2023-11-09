public class InputCheck {
    private enum gameCommand{
        MOVE,
        ROLL,
        QUIT,
        START
    };

    private gameCommand command; // 3 possible commands
    private int[] faces;

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
        else{
            command = gameCommand.MOVE;
        }
    }

    public static boolean validMove(String input){
        String inputUpper = input.toUpperCase().trim();
        return (inputUpper.equals("QUIT")) || (inputUpper.equals("ROLL")) ||
                (inputUpper.matches("START"));
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

    // returns the  face value 
    public int getFace (int index) { 
		return switch (index) {
			case 1 -> faces[0];
			case 2 -> faces[1];
			default -> 0;
		};
	}
}
