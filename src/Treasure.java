package src;
//TREASURE CLASS
public class Treasure {


    private int left;
    private int right;
    private int up;
    private int down;


    private int treasureX;
    private int treasureY;


    //treasure constructor
    public Treasure(int treasureX, int treasureY) {


        this.treasureX = treasureX;
        this.treasureY = treasureY;

        left = (treasureX + 19) % 20;
        right = (treasureX + 1) % 20;
        up = (treasureY + 19) % 20;
        down = (treasureY + 1) % 20;

    }


    public int getTreasureX() {
        return treasureX;
    }

    public int getTreasureY() {
        return treasureY;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getUp() {
        return up;
    }

    public int getDown() {
        return down;
    }

}

