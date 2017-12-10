//PITFALL CLASS
package src;
public class Pitfall {
    private int left;
    private int right;
    private int up;
    private int down;


    private int pitfallX;
    private int pitfallY;

    public Pitfall() {

        left = pitfallX - 1;
        right = pitfallX + 1;
        up = pitfallY - 1;
        down = pitfallY + 1;
    }


    public Pitfall(int pitfallX, int pitfallY) {

        this.pitfallX = pitfallX;
        this.pitfallY = pitfallY;
        left = (pitfallX + 19) % 20;
        right = (pitfallX + 1) % 20;
        up = (pitfallY + 19) % 20;
        down = (pitfallY + 1) % 20;
    }

    public void setpitfallX(int pitfallX) {
        this.pitfallX = pitfallX;
    }

    public void setpitfallY(int pitfallY) {
        this.pitfallY = pitfallY;
    }

    public int getPitfallX() {
        return pitfallX;
    }

    public int getPitfallY() {
        return pitfallY;
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
