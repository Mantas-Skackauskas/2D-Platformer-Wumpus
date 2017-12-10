//WUMPUS CLASS
package src;
public class Wumpus {


    private int left;
    private int right;
    private int up;
    private int down;


    private int wumpusX;
    private int wumpusY;


    //wumpus constructor
    public Wumpus() {


        left = wumpusX - 1;
        right = wumpusX + 1;
        up = wumpusY - 1;
        down = wumpusY + 1;

    }

    public Wumpus(int wumpusX, int wumpusY) {


        this.wumpusX = wumpusX;
        this.wumpusY = wumpusY;

        left = (wumpusX + 19) % 20;
        right = (wumpusX + 1) % 20;
        up = (wumpusY + 19) % 20;
        down = (wumpusY + 1) % 20;

    }

    public void setWumpusX(int wumpusX) {
        this.wumpusX = wumpusX;
    }

    public void setWumpusY(int wumpusY) {
        this.wumpusY = wumpusY;
    }

    public int getWumpusX() {
        return wumpusX;
    }

    public int getWumpusY() {
        return wumpusY;
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

