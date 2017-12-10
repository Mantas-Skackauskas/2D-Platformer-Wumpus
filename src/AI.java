//AI CLASS
package src;
import java.awt.event.KeyEvent;


/**
 *
 * AI CLASS
 *
 * THIS IS A SECOND ATTEMPT OF AI CLASS
 * THE FIRST AI EXPLORED BY A COORDINATED
 * RANDOM MOVEMENT BUT WAS INEFFICIENT DUE
 * TO LACK OF CONNECTION BETWEEN AI AND SURROUNDINGS
 * -------------------------------------
 * UPDATED AI VERSION DESCRIPTION BELOW
 * -------------------------------------
 * AI WORKS BY STORING PREVIOUS MOVES DATA
 * AND DECIDING WHICH NEARBY TILES HAVE
 * BEEN VISITED THE LEAST AMOUNT OF TIMES.
 * -------------------------------------
 * BY USING THIS DATA THE AI EXPLORES
 * THE GRID IN A SCANNING MANNER THAT IS THE MOST EFFICIENT WAY
 * IN THE CASE OF NOT KNOWING THE EXACT POSITIONS OF THE GAME
 * OBJECTIVE OBJECTS.
 * -------------------------------------
 * THE AI NEVER LOSES THE GAME SINCE
 * IT PERFECTLY EXECUTES ALL THE MANEUVERS
 * AND AVOIDS OBSTACLES
 *
 */
public class AI {

    private boolean asd = true;
    private int x = 0;
    private int numberOfMoves = 0;
    private int randomNumber = (int) (Math.random() * 4);
    private int previousDirection = 0;
    private int playerX;
    private int playerY;
    private int previousCoordinate;
    private boolean wentBack = false;
    private boolean treasureFind = false;
    private int numberOfSearching;
    private boolean treasureCaught = false;
    private int k = 7;
    private boolean truOrFalse = true;
    private int[] sides = new int[4];

    int count = 0;

    int min;

    int[][] gridMoves = new int[20][20];


    //MAKING A MOVE
    public void makeMove(Player player, GridTiles gridTiles) {
        if (count % 2 != 0) {

            playerX = player.getX() / 30;
            playerY = player.getY() / 30;

            gridMoves[playerY][playerX]++;


            //checking surroundings
            if ((gridTiles.getGridTile(playerX, playerY) != 4) && (gridTiles.getGridTile(playerX, playerY) != 7) && (gridTiles.getGridTile(playerX, playerY) != 11) && (gridTiles.getGridTile(playerX, playerY) != 12)) {
                //down
                sides[0] = gridMoves[(playerY + 1) % 20][playerX];
                //up
                sides[1] = gridMoves[(playerY + 19) % 20][playerX];
                //right
                sides[2] = gridMoves[playerY][(playerX + 1) % 20];
                //left
                sides[3] = gridMoves[playerY][(playerX + 19) % 20];

                min = 0;

                for (int i = 1; i < 4; i++) {
                    if (sides[min] >= sides[i]) {
                        min = i;
                    }
                }

                randomNumber = min;
            } else {
                previousCoordinate = gridTiles.getGridTile(playerX, playerY);

                if (numberOfMoves == 0) {
                    numberOfMoves = (int) (1 + Math.random() * 4); //1-4

                    while (randomNumber == previousDirection) {
                        randomNumber = (int) (Math.random() * 4);

                    }

                }

            }

            //moves when the treasure is nearby
            if (treasureFind == true) {

                //resets grid
                gridMoves = new int[20][20];
                if (numberOfSearching == 7) {
                    player.setUp(true);
                    if (treasureCaught == true) {
                        treasureFind = false;
                    } else {
                        numberOfSearching--;
                    }
                } else if (numberOfSearching == 6) {
                    if ((gridTiles.getGridTile(playerX, playerY) == 3) || (gridTiles.getGridTile(playerX, playerY) == 8)) {
                        player.setUp(true);
                        numberOfSearching++;
                        treasureCaught = true;
                    } else {
                        player.setDown(true);
                        numberOfSearching--;
                    }
                } else if (numberOfSearching == 5) {
                    player.setRight(true);
                    if (treasureCaught == true) {
                        treasureFind = false;
                    } else numberOfSearching--;
                } else if (numberOfSearching == 4) {

                    if ((gridTiles.getGridTile(playerX, playerY) == 3) || (gridTiles.getGridTile(playerX, playerY) == 8)) {
                        player.setRight(true);
                        numberOfSearching++;
                        treasureCaught = true;
                    } else {
                        player.setLeft(true);
                        numberOfSearching--;
                    }
                } else if (numberOfSearching == 3) {
                    player.setDown(true);
                    if (treasureCaught == true) {
                        treasureFind = false;
                    } else numberOfSearching--;
                } else if (numberOfSearching == 2) {
                    if ((gridTiles.getGridTile(playerX, playerY) == 3) || (gridTiles.getGridTile(playerX, playerY) == 8)) {
                        player.setDown(true);
                        numberOfSearching++;
                        treasureCaught = true;
                    } else {
                        player.setUp(true);
                        numberOfSearching--;
                    }
                } else if (numberOfSearching == 1) {
                    player.setLeft(true);
                    if (treasureCaught == true) {
                        treasureFind = false;
                    } else numberOfSearching--;
                } else if (numberOfSearching == 0) {
                    if ((gridTiles.getGridTile(playerX, playerY) == 3) || (gridTiles.getGridTile(playerX, playerY) == 8)) {
                        player.setLeft(true);
                        numberOfSearching++;
                        treasureCaught = true;
                    }
                    //sito gal nereikia
                    else {
                        player.setRight(true);
                        numberOfSearching--;
                    }
                }


            } else {
                if (gridTiles.getGridTile(playerX, playerY) > 1) {

                    if (((gridTiles.getGridTile(playerX, playerY) == 2) || (gridTiles.getGridTile(playerX, playerY) == 6)) && (treasureCaught == false)) {
                        treasureFind = true;
                        numberOfSearching = 7;
                        treasureCaught = false;
                    } else {

                        if (previousDirection == 0) {
                            player.setUp(true);
                        } else if (previousDirection == 1) {
                            player.setDown(true);
                        } else if (previousDirection == 2) {
                            player.setLeft(true);
                        } else if (previousDirection == 3) {
                            player.setRight(true);
                        }
                    }


                } else {


                    if (randomNumber == 0) {
                        player.setDown(true);
                        previousDirection = 0;
                    } else if (randomNumber == 1) {
                        player.setUp(true);
                        previousDirection = 1;
                    } else if (randomNumber == 2) {
                        player.setRight(true);
                        previousDirection = 2;
                    } else {
                        player.setLeft(true);
                        previousDirection = 3;
                    }
                }

            }

            numberOfMoves--;
            player.setPlayerAIskip(false);

            try {
                Thread.sleep(50);
            } catch (Exception e) {
            }

        }
        count++;
    }

}
