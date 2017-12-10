package src;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Player {

    private int x;
    private int y;


    private int dx;
    private int dy;

    private int temporarX;
    private int temporarY;

    private int width;
    private int height;

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;


    //shooting arrows booleans
    private boolean shootUp;
    private boolean shootDown;
    private boolean shootLeft;
    private boolean shootRight;


    private boolean playerDead = true;

    private boolean foundArrow = false;
    private boolean playerWon = false;
    private boolean playerTreasureFound = false;
    private boolean playerAIskip = false;
    private boolean playerAllowHint = false;

    private boolean wumpusShowDead = false;
    //private boolean moving = false;

    private int playerSpeed;
    private int newX;
    private int newY;

    //move count

    private int movesMade = 0;

    private GridTiles gridTiles;

    private BufferedImage playerTileSet;
    private Tile playerTile;

    private int playerArrows = 1;


    public Player(GridTiles gridTiles) {

        this.gridTiles = gridTiles;

        width = 30;
        height = 30;

        playerSpeed = 30;
        //playerFootsteps = 0;
        //  playerMaxSpeed = 4;
        //  playerStopSpeed = 0;
    }

    //setters

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setShootUp(boolean shootUp) {
        this.shootUp = shootUp;
    }

    public void setShootDown(boolean shootDown) {
        this.shootDown = shootDown;
    }

    public void setShootLeft(boolean shootLeft) {
        this.shootLeft = shootLeft;
    }

    public void setShootRight(boolean shootRight) {
        this.shootRight = shootRight;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    //zaidimo kazkas

    public void setPlayerAIskip(boolean playerAIskip) {
        this.playerAIskip = playerAIskip;
    }

    public void setFoundArrow(boolean foundArrow) {
        this.foundArrow = foundArrow;
    }

    public void setPlayerTreasureFound(boolean playerTreasureFound) {
        this.playerTreasureFound = playerTreasureFound;
    }

    public void setWumpusShowDead(boolean wumpusShowDead) {
        this.wumpusShowDead = wumpusShowDead;
    }

    public boolean getWumpusShowDead() {
        return wumpusShowDead;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPlayerArrows() {
        return playerArrows;
    }

    public int getNewX() {
        return newX;
    }

    public void setNewX(int newX) {
        this.newX = newX;
    }

    public int getNewY() {
        return newY;
    }

    public void setNewY(int newY) {
        this.newY = newY;
    }


    public boolean getPlayerAllowHint() {
        return playerAllowHint;
    }

    public int getMovesMade() {
        return movesMade;
    }

    public boolean getPlayerAIskip() {
        return playerAIskip;
    }

    public GridTiles getGridTiles() {
        return gridTiles;
    }

    public boolean isFoundArrow() {
        return foundArrow;
    }

    public boolean isPlayerDead() {
        return playerDead;
    }

    public boolean isPlayerWon() {
        return playerWon;
    }

    public boolean isPlayerTreasureFound() {
        return playerTreasureFound;
    }


    public void setPlayerAlive() {
        playerDead = false;
    }

    public void arrowShoot() {
        playerArrows--;
    }

    public int randomNumber() {
        return (int) (Math.random() * gridTiles.getGridHeight());
    }

    public void playerAddArrows(int number) {
        playerArrows += number;
    }

    public void loadPlayerTile(String s) {
        try {
            playerTileSet = ImageIO.read(new File(s));
            playerTile = new Tile(playerTileSet, false);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startPositionPlayer() {
        do {
            double random = (Math.random());
            //fix this
            if (random < 0.25) {
                x = 0;
                y = randomNumber() * gridTiles.getGridTileSize();
            } else if (random < 0.5) {
                x = (gridTiles.getGridHeight() - 1) * gridTiles.getGridTileSize();
                y = randomNumber() * gridTiles.getGridTileSize();
            } else if (random < 0.75) {
                y = (gridTiles.getGridHeight() - 1) * gridTiles.getGridTileSize();
                x = randomNumber() * gridTiles.getGridTileSize();
            } else {
                y = 0;
                x = randomNumber() * gridTiles.getGridTileSize();
            }
            temporarX = x / 30;
            temporarY = y / 30;


        }//galima pakeisti is karto
        while ((gridTiles.getGridTile(x / 30, y / 30) > 1) ||
                (gridTiles.getGridTile(((x / 30) + 1) % 20, y / 30) > 1) ||
                (gridTiles.getGridTile(x / 30, ((y / 30) + 1) % 20) > 1) ||
                (gridTiles.getGridTile(((x / 30) + 19) % 20, y / 30) > 1) ||
                (gridTiles.getGridTile(x / 30, ((y / 30) + 19) % 20) > 1)
                ); // &&

        setX(x);
        setY(y);

    }


    //observation method for testing purposes
    public void getPlayerObservationCoordinates() {


        if (x == gridTiles.getGridWidth() * gridTiles.getGridTileSize()) {
            x = 0;
        } else if (x == -gridTiles.getGridTileSize()) {
            x = (gridTiles.getGridHeight() - 1) * gridTiles.getGridTileSize();
        }

        if (y == gridTiles.getGridHeight() * gridTiles.getGridTileSize()) {
            y = 0;
        } else if (y == -gridTiles.getGridTileSize()) {
            y = (gridTiles.getGridHeight() - 1) * gridTiles.getGridTileSize();
        }
    }


    //updating player
    public void update() {


        //player row and column coordinates
        int playerCol = gridTiles.getGridCol(x);
        int playerRow = gridTiles.getGridRow(y);


        if (gridTiles.grid[playerRow][playerCol] == 2) {

            //treasure side change
            gridTiles.grid[playerRow][playerCol] = 6;

        } else if (gridTiles.grid[playerRow][playerCol] == 4) {

            //wumpus side change
            gridTiles.grid[playerRow][playerCol] = 7;

        } else if (gridTiles.grid[playerRow][playerCol] == 3) {

            //treasure main point change
            gridTiles.grid[playerRow][playerCol] = 8;
            playerTreasureFound = true;
            playerAllowHint = true;
            // System.out.println("allows the hint");
            gridTiles.exitAppears();

        } else if (gridTiles.grid[playerRow][playerCol] == 11) {

            //pitfall side change
            gridTiles.grid[playerRow][playerCol] = 12;

        } else if (gridTiles.grid[playerRow][playerCol] == 10) {

            //pitfall center change
            gridTiles.grid[playerRow][playerCol] = 17;
            playerDead = true;

        } else if (gridTiles.grid[playerRow][playerCol] == 5) {

            //wumpus center change
            gridTiles.grid[playerRow][playerCol] = 16;
            playerDead = true;

        } else if (gridTiles.grid[playerRow][playerCol] == 19) {

            //arrows change
            gridTiles.grid[playerRow][playerCol] = 13;

            gridTiles.grid[playerRow][playerCol] = 1;
            playerAddArrows(3);
            foundArrow = true;

        } else if (gridTiles.grid[playerRow][playerCol] == 18 || gridTiles.grid[playerRow][playerCol] == 14) {

            gridTiles.grid[playerRow][playerCol] = 14;

            do {
                setX(gridTiles.randomBatCoordinate() * gridTiles.getGridTileSize());
                setY(gridTiles.randomBatCoordinate() * gridTiles.getGridTileSize());
            }

            while (gridTiles.grid[getY() / 30][getX() / 30] > 1);

            newY = getY() / 30;
            newX = getX() / 30;

            playerAIskip = true;
        } else if (gridTiles.getA() == playerRow && gridTiles.getB() == playerCol) {

            gridTiles.setGridTile(playerRow, playerCol, 9);
            playerWon = true;

        } else {
            gridTiles.setGridTile(playerRow, playerCol, 1);
        }

        if (gridTiles.grid[playerRow][playerCol] == 16) {
            playerDead = true;
        }


        //movement
        if (left) {
            dx = -playerSpeed;
            left = false;

        } else if (right) {
            dx = playerSpeed;
            right = false;

        } else if (up) {
            dy = -playerSpeed;
            up = false;

        } else if (down) {
            dy = playerSpeed;
            down = false;

        }
        //check obstacles


        if (!playerDead && !playerWon && !playerAIskip) {
            x += dx;
            y += dy;
            if (dx != 0 || dy != 0) {
                movesMade++;
            }

        }
        playerAIskip = false;
        dy = 0;
        dx = 0;

        if (x == gridTiles.getGridWidth() * gridTiles.getGridTileSize()) {
            x = 0;
        } else if (x == -gridTiles.getGridTileSize()) {
            x = (gridTiles.getGridHeight() - 1) * gridTiles.getGridTileSize();
        }

        if (y == gridTiles.getGridHeight() * gridTiles.getGridTileSize()) {
            y = 0;
        } else if (y == -gridTiles.getGridTileSize()) {
            y = (gridTiles.getGridHeight() - 1) * gridTiles.getGridTileSize();
        }


        //shooting arrows
        if (playerArrows > 0) {
            if (shootUp) {
                shootUp = false;
                if (gridTiles.getGridTile(playerCol, (playerRow + 19) % 20) == 5) {
                    gridTiles.exterminateWumpus();
                    wumpusShowDead = true;
                } else if (wumpusNear()) {
                    moveWumpus();
                }
                arrowShoot();

            } else if (shootDown) {
                shootDown = false;
                if (gridTiles.getGridTile(playerCol, (playerRow + 1) % 20) == 5) {
                    gridTiles.exterminateWumpus();
                    wumpusShowDead = true;

                } else if (wumpusNear()) {

                    moveWumpus();
                }
                arrowShoot();

            } else if (shootLeft) {
                shootLeft = false;
                if (gridTiles.getGridTile((playerCol + 19) % 20, playerRow) == 5) {
                    gridTiles.exterminateWumpus();
                    wumpusShowDead = true;


                } else if (wumpusNear()) {
                    moveWumpus();
                }
                arrowShoot();

            } else if (shootRight) {
                shootRight = false;
                if (gridTiles.getGridTile((playerCol + 1) % 20, playerRow) == 5) {
                    gridTiles.exterminateWumpus();
                    wumpusShowDead = true;
                } else if (wumpusNear()) {

                    moveWumpus();
                }
                arrowShoot();

            }

        }

    }

    //checks if wumpus is near
    public boolean wumpusNear() {
        int x = getX() / 30;
        int y = getY() / 30;
        if (gridTiles.getGridTile((x + 1) % 20, y) == 5 ||
                gridTiles.getGridTile((x + 19) % 20, y) == 5 ||
                gridTiles.getGridTile(x, (y + 1) % 20) == 5 ||
                gridTiles.getGridTile(x, (y + 19) % 20) == 5
                ) {
            return true;
        } else {
            return false;
        }
    }

    //drawing method
    public void draw(Graphics2D graphics) {
        int tx = gridTiles.getX();
        int ty = gridTiles.getY();


        if (!playerDead && !playerWon) {

            graphics.drawImage(playerTile.getImage(),
                    x,
                    y,
                    null
            );
        }

    }


    //player caused wumpus move
    public void moveWumpus() {
        System.out.println("afs");
        if (gridTiles.checkWumpusMovePossible((gridTiles.getWumpusGridX() + 19) % 20, gridTiles.getWumpusGridY(), getX() / 30, getY() / 30)) {
           // System.out.println("good");
            gridTiles.exterminateWumpus();
            updateWumpus((gridTiles.getWumpusGridX() + 19) % 20, gridTiles.getWumpusGridY());

            gridTiles.setWumpusGridX((gridTiles.getWumpusGridX() + 19) % 20);
            gridTiles.setWumpusGridY(gridTiles.getWumpusGridY());

        } else if (gridTiles.checkWumpusMovePossible((gridTiles.getWumpusGridX() + 1) % 20, gridTiles.getWumpusGridY(), getX() / 30, getY() / 30)) {
          //  System.out.println("good");
            gridTiles.exterminateWumpus();

            updateWumpus((gridTiles.getWumpusGridX() + 1) % 20, gridTiles.getWumpusGridY());

            gridTiles.setWumpusGridX((gridTiles.getWumpusGridX() + 1) % 20);
            gridTiles.setWumpusGridY(gridTiles.getWumpusGridY());

        } else if (gridTiles.checkWumpusMovePossible(gridTiles.getWumpusGridX(), (gridTiles.getWumpusGridY() + 1) % 20, getX() / 30, getY() / 30)) {
          //  System.out.println("good");
            gridTiles.exterminateWumpus();

            updateWumpus(gridTiles.getWumpusGridX(), (gridTiles.getWumpusGridY() + 1) % 20);

            gridTiles.setWumpusGridX(gridTiles.getWumpusGridX());
            gridTiles.setWumpusGridY((gridTiles.getWumpusGridY() + 1) % 20);

        } else if (gridTiles.checkWumpusMovePossible(gridTiles.getWumpusGridX(), (gridTiles.getWumpusGridY() + 19) % 20, getX() / 30, getY() / 30)) {
           // System.out.println("good");
            gridTiles.exterminateWumpus();

            updateWumpus(gridTiles.getWumpusGridX(), (gridTiles.getWumpusGridY() + 19) % 20);

            gridTiles.setWumpusGridX(gridTiles.getWumpusGridX());
            gridTiles.setWumpusGridY((gridTiles.getWumpusGridY() + 19) % 20);

        }
    }


    //updating wumpus position when player disturbed him
    public void updateWumpus(int a, int b) {

        gridTiles.setWumpusGridX(a);
        gridTiles.setWumpusGridY(b);

      //  System.out.println(a + " " + b);
       // System.out.println(gridTiles.getGridTile(b, a));
        if (a != getX() / 30 && b != getY() / 30) {
            if (gridTiles.getGridTile(b, a) == 5 || gridTiles.getGridTile(b, a) == 4 || gridTiles.getGridTile(b, a) == 0) {

                gridTiles.grid[a][b] = 5;

            } else {
                gridTiles.grid[a][b] = 16;
            }


            if (gridTiles.getGridTile(b, (a + 1) % 20) == 4 || gridTiles.getGridTile(b, (a + 1) % 20) == 5 || gridTiles.getGridTile(b, (a + 1) % 20) == 0) {

                gridTiles.grid[(a + 1) % 20][b] = 4;
            } else {
                gridTiles.grid[(a + 1) % 20][b] = 7;
            }


            if (gridTiles.getGridTile(b, (a + 19) % 20) == 4 || gridTiles.getGridTile(b, (a + 19) % 20) == 5 || gridTiles.getGridTile(b, (a + 19) % 20) == 0) {

                gridTiles.grid[(a + 19) % 20][b] = 4;
            } else {
                gridTiles.grid[(a + 19) % 20][b] = 7;

            }


            if (gridTiles.getGridTile((b + 1) % 20, a) == 4 || gridTiles.getGridTile((b + 1) % 20, a) == 5 || gridTiles.getGridTile((b + 1) % 20, a) == 0) {

                gridTiles.grid[a][(b + 1) % 20] = 4;
            } else {
                gridTiles.grid[a][(b + 1) % 20] = 7;

            }


            if (gridTiles.getGridTile((b + 19) % 20, a) == 4 || gridTiles.getGridTile((b + 19) % 20, a) == 5 || gridTiles.getGridTile((b + 19) % 20, a) == 0) {

                gridTiles.grid[a][(b + 19) % 20] = 4;
            } else {
                gridTiles.grid[a][(b + 19) % 20] = 7;

            }

        }
    }

}


