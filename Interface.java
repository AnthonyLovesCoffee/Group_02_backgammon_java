import java.util.*;

public class Interface {
    Scanner key;
    InputCheck input;

    Interface(){
        key = new Scanner(System.in);
    }

	// display message when game starts
    public void gameIntro(){
        System.out.println("Welcome to Backgammon");
    }

    public InputCheck getUserInput () {
		boolean keyEntered = false;
		do {
			System.out.print("Enter command: ");
			String in = key.nextLine();
			if (InputCheck.validMove(in)) {
				input = new InputCheck(in);
				keyEntered = true;
			} else {
				System.out.println("The command is invalid. Try again.");
			}
		}
		while (!keyEntered);
		return input;
    }
}