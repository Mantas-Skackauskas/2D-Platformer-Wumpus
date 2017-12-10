package  src;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

//GRIDTILES CLASS TO STORE INFORMATION ABOUT THE GRID
public class GridTiles {


    //DEFINING VARIABLES
    private int x;
    private int y;
    private int a, b;

    private static int gridWidth = 20;
    private static int gridHeight = 20;

    private int gridTileSize;
    public int[][] grid = new int[gridHeight][gridWidth];


    private Treasure treasure = new Treasure(randomCoordinate(), randomCoordinate());
    private Wumpus wumpus = new Wumpus();
    private Pitfall[] pitfall = new Pitfall[7];
    private Arrow arrow = new Arrow();

    private int wumpusGridX;
    private int wumpusGridY;

    private int exitX;
    private int exitY;

    private Bat[] bat = new Bat[4];

    private BufferedImage tileSet;
    private Tile[][] tiles;

    //GRIDTILES CLASS CONSTRUCTOR
    public GridTiles(int gridTileSize) {

        this.gridTileSize = gridTileSize;
        //startPositionTreasure);

        //treasure main point
        setGridTile(treasure.getTreasureX(), treasure.getTreasureY(), 3);

        //treasure side grid tiles
        setGridTile(treasure.getLeft(), treasure.getTreasureY(), 2);
        setGridTile(treasure.getRight(), treasure.getTreasureY(), 2);
        setGridTile(treasure.getTreasureX(), treasure.getUp(), 2);
        setGridTile(treasure.getTreasureX(), treasure.getDown(), 2);

        //getting wumpus' coordinates
        do {
            a = randomCoordinate();
            b = randomCoordinate();
        } while (!checkCoordinates(a, b));

        //giving coordinates to wumpus
        wumpus = new Wumpus(a, b);
        setGridTile(wumpus.getWumpusX(), wumpus.getWumpusY(), 5);
        //wumpus side grid tiles
        setGridTile(wumpus.getLeft(), wumpus.getWumpusY(), 4);
        setGridTile(wumpus.getRight(), wumpus.getWumpusY(), 4);
        setGridTile(wumpus.getWumpusX(), wumpus.getUp(), 4);
        setGridTile(wumpus.getWumpusX(), wumpus.getDown(), 4);

        wumpusGridX = a;
        wumpusGridY = b;

        //creating pitfalls
        for (int i = 0; i < 7; i++) {
            //checking pitfall coordinates
            do {
                a = randomBatCoordinate();
                b = randomBatCoordinate();
            } while (!checkCoordinates(a, b));


            pitfall[i] = new Pitfall(a, b);
            setGridTile(pitfall[i].getPitfallX(), pitfall[i].getPitfallY(), 10);
            //wumpus side grid tiles
            setGridTile(pitfall[i].getLeft(), pitfall[i].getPitfallY(), 11);
            setGridTile(pitfall[i].getRight(), pitfall[i].getPitfallY(), 11);
            setGridTile(pitfall[i].getPitfallX(), pitfall[i].getUp(), 11);
            setGridTile(pitfall[i].getPitfallX(), pitfall[i].getDown(), 11);
        }


        //arrow placement
        do {
            a = randomCoordinate();
            b = randomCoordinate();
        } while (!checkCoordinate(a, b));

        arrow = new Arrow(a, b);
        setGridTile(arrow.getArrowX(), arrow.getArrowY(), 19);


        //bat placement
        for (int i = 0; i <= 3; i++) {
            //checking bat coordinates
            do {
                a = randomCoordinate();
                b = randomCoordinate();

            } while (!checkCoordinates(a, b));

            bat[i] = new Bat(a, b);
            setGridTile(bat[i].getBatX(), bat[i].getBatY(), 18);
        }
    }


    //getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridTileSize() {
        return gridTileSize;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public int getGridCol(int x) {
        return x / gridTileSize;
    }

    public int getGridRow(int y) {
        return y / gridTileSize;
    }

    public int getGridTile(int row, int col) {

        //  System.out.println("asdasdasd    "+grid[col][row]);
        return grid[col][row];
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getWumpusGridX() {
        return wumpus.getWumpusX();
    }

    public int getWumpusGridY() {
        return wumpus.getWumpusY();
    }


    //setters
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWumpusGridX(int wumpusGridX) {
        wumpus.setWumpusX(wumpusGridX);
    }

    public void setWumpusGridY(int wumpusGridY) {
        wumpus.setWumpusY(wumpusGridY);
    }

    public void setGridTile(int x, int y, int number) {
        if ((grid[x][y] != 6) && (grid[x][y] != 7) && (grid[x][y] != 8) && (grid[x][y] != 12) && (grid[x][y] != 16) && (grid[x][y] != 17)
                && (grid[x][y] != 14) && (grid[x][y] != 9) && (grid[x][y] != 5) && (grid[x][y] != 4) && (grid[x][y] != 10) && (grid[x][y] != 11))
            grid[x][y] = number;

    }


    //checking free tiles for object placement
    public boolean checkCoordinates(int a, int b) {
        if ((grid[(a + 19) % 20][b] == 0) && (grid[a][(b + 19) % 20] == 0) && (grid[a][(b + 1) % 20] == 0) && (grid[(a + 1) % 20][b] == 0) && (grid[a][b] == 0))
            return true;
        else return false;
    }


    //checking free tiles for objcet placement
    public boolean checkCoordinate(int a, int b) {
        if (grid[a][b] <= 1) {
            return true;
        } else {
            return false;
        }
    }


    //generating random coordinates

    public int randomCoordinate() {
        return ((int) (4.0 + Math.random() * 11));
    }


    //random bat or similar spawning object coordinate generation
    public int randomBatCoordinate() {
        return ((int) (Math.random() * 20));
    }


    //loading tiles
    public void loadTiles(String s) {
        try {
            tileSet = ImageIO.read(new File(s));
            int numTilesAcross = (tileSet.getWidth() + 1 / (gridTileSize + 1));
            tiles = new Tile[2][numTilesAcross];

            BufferedImage subimage;
            for (int col = 0; col < numTilesAcross; col++) {
                subimage = tileSet.getSubimage(
                        col * gridTileSize + col,
                        0,
                        gridTileSize,
                        gridTileSize
                );

                tiles[0][col] = new Tile(subimage, false);
                subimage = tileSet.getSubimage(
                        col * gridTileSize + col,
                        gridTileSize + 1,
                        gridTileSize,
                        gridTileSize
                );

                tiles[1][col] = new Tile(subimage, true);
            }
        } catch (Exception e) {
        }
    }


    public void update() {

    }


    //random number generation
    public int randomNumber() {
        return (int) (Math.random() * getGridHeight());
    }

    //exit appears after finding a treasure
    public void exitAppears() {
        // double random = (Math.random());
        do {
            a = randomNumber();
            b = randomNumber();
        }
        while ((getGridTile(a, b) > 1) ||
                (getGridTile((a + 1) % 20, b) > 1) ||
                (getGridTile(a, (b + 1) % 20) > 1) ||
                (getGridTile((a + 19) % 20, b) > 1) ||
                (getGridTile(a, (b + 19) % 20) > 1)
                );

        exitX = a;
        exitY = b;

    }

    //terminating wumpus
    public void exterminateWumpus() {
        grid[getWumpusGridX()][getWumpusGridY()] = 1;
        //fix numbers->done
        //up
        if (grid[getWumpusGridX()][(getWumpusGridY() + 19) % 20] == 4) {
            //System.out.println("kas ce");
            grid[getWumpusGridX()][(getWumpusGridY() + 19) % 20] = 0;
        } else {
            grid[getWumpusGridX()][(getWumpusGridY() + 19) % 20] = 1;
        }

        //down
        if (grid[getWumpusGridX()][(getWumpusGridY() + 1) % 20] == 4) {
            grid[getWumpusGridX()][(getWumpusGridY() + 1) % 20] = 0;
        } else {
            grid[getWumpusGridX()][(getWumpusGridY() + 1) % 20] = 1;
        }

        //left
        if (grid[(getWumpusGridX() + 19) % 20][getWumpusGridY()] == 4) {
            grid[(getWumpusGridX() + 19) % 20][getWumpusGridY()] = 0;
        } else {
            grid[(getWumpusGridX() + 19) % 20][getWumpusGridY()] = 1;
        }

        //right
        if (grid[(getWumpusGridX() + 1) % 20][getWumpusGridY()] == 4) {
            grid[(getWumpusGridX() + 1) % 20][getWumpusGridY()] = 0;
        } else {
            grid[(getWumpusGridX() + 1) % 20][getWumpusGridY()] = 1;
        }

    }


    //drawing method
    public void draw(Graphics2D graphics) {


        for (int row = 0; row < gridHeight; row++) {
            for (int col = 0; col < gridWidth; col++) {
                int rc = grid[row][col];
                //sprite extraction point

                // uncomment if works
                int r = rc / tiles[0].length;
                int c = rc % tiles[0].length;

                graphics.drawImage(tiles[r][c].getImage(),
                        x + col * gridTileSize,
                        y + row * gridTileSize,
                        null
                );
            }
        }
    }

    //showing hint
    public void showHint() {
        setGridTile(exitX, exitY, 9);
    }

    //checking if disturbed wumpus can move
    public boolean checkWumpusMovePossible(int x1, int y1, int px, int py) {
        if ((getGridTile(x1, y1) < 2) &&
                (getGridTile((x1 + 1) % 20, y1) < 2) &&
                (getGridTile(x1, (y1 + 1) % 20) < 2) &&
                (getGridTile((x1 + 19) % 20, y1) < 2) &&
                (getGridTile(x1, (y1 + 19) % 20) < 2) &&
                x1 != py && y1 != px
            //  x1 != px && y1 != py
                ) {

            return true;
        } else {
            return false;
        }
    }


}
