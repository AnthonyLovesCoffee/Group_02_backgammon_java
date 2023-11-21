public class Player {
    private String name;
    private int score, pips;
    private CheckerTemplate checkerTemp;

    Player(String name, CheckerTemplate checkerTemp){
        this.name = name;
        this.checkerTemp = checkerTemp;
        this.score = 0;
        this.pips = 167;
    }

    public String dispName(){
        return checkerTemp.getDisplay() + name + InterfaceColours.RESET;
    }

    // getter functions
    public CheckerTemplate getCheckerTemp(){
        return checkerTemp;
    }
    public String getColourString(){
        return checkerTemp.getColour();
    }
    public int getScore () { 
        return score;
    } 
    public CheckerTemplate getCheckerTemplate () {
        return checkerTemp;
    }
    public int getPips(){ 
        return pips;
    }
    // sets the player's pips
    public void setPips (int pips) {
    this.pips = pips;
    }
}
