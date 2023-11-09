import java.util.Random;

public class Dice {
    int numMoves; // number of moves a player can make
    private int[] faces, numSteps;
    private Random rand;
    private Interface intrface;

    Dice(){
        rand = new Random();
        this.faces = new int[2];
        this.numSteps = new int[2];

        numSteps[0] = 1;
        numSteps[1] = 1;

        numMoves = numSteps[0] + numSteps[1];
        faces[0] = rand.nextInt(1, 7);
        faces[1] = rand.nextInt(1, 7);
        if (faces[0] == faces[1]) {
        	numSteps[0] = 4;
        	numSteps[1] = 0;
        	numMoves = numSteps[0] + numSteps[1];
        }
    }

    // rolls the dice - updatwa face and move values 
    public void roll () { 
    	numSteps[0] = 1;
    	numSteps[1] = 1;
    	numMoves = numSteps[0] + numSteps[1];
    	faces[0] = rand.nextInt(1, 7);
        faces[1] = rand.nextInt(1, 7);
        if (faces[0] == faces[1]) {
        	numSteps[0] = 4;
        	numSteps[1] = 0;
        	numMoves = numSteps[0] + numSteps[1];
        }
    }

    public void setFace (int face1, int face2) { // Sets the face values of the dice and updates the move values accordingly.
    	numSteps[0] = 1;
    	numSteps[1] = 1;
    	numMoves = numSteps[0] + numSteps[1];
        faces[0] = face1;
        faces[1] = face2;
        if (faces[0] == faces[1]) {
        	numSteps[0] = 4;
        	numSteps[1] = 0;
        	numMoves = numSteps[0] + numSteps[1];
        }
        intrface.printDice(face1, face2);
    }

    public int getFace (int index) { // face on dice(index)
    	return switch (index) {
			case 1 -> faces[0];
			case 2 -> faces[1];
			default -> 0;
		};
    }

     // returns the step value for the specified index
    public int getMoveStep (int index) {
    	return switch (index) {
			case 1 -> numSteps[0];
			case 2 -> numSteps[1];
			default -> 0;
		};
    }
}

