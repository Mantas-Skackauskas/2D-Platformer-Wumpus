//SCREEN CLASS FOR PUTTING INFORMATION ON THE SCREEN
package src;

import sun.font.ScriptRun;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.Font;
import java.awt.image.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Screen extends JPanel implements Runnable, KeyListener {

    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 700;


    private Thread thread;
    private boolean isRunning;
    private boolean gameStart = false;
    private boolean computer = false;
    private boolean startSetUp = false;

    private boolean menuPick = true;

    private boolean hintUsed = false;
    //menu


    //ends here

    private BufferedImage image;
    private Graphics2D graphics1;

    private BufferedImage menu;


    private int FPS = 60;
    private int targetTime = 1000 / FPS;


    private GridTiles gridTiles;
    private Player player;
    private AI ai = new AI();

    private int lastKey;
    private File fontFile;
    private Font font;

    //CONSTRUCTOR
    public Screen() {

        super();

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    public void changeMenuPick(boolean menuPick) {
        if (menuPick) {
            this.menuPick = false;
        } else {
            this.menuPick = true;
        }
    }

    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }

    public void setGridTiles(GridTiles gridTiles) {
        this.gridTiles = gridTiles;
    }

    public void setGameStart() {
        gameStart = true;
    }

    public void setComputer(boolean computer) {
        this.computer = computer;
    }


    public boolean getGameStart() {
        return gameStart;
    }


    //RUN METHOD FOR RUNNING A GAME AND PROVIDING CONSTANT FRAMES PER SECOND UPDATING
    public void run() {

        initialise();
        long timeStart;
        long timeUrd;
        long timeWait;

        while (isRunning) {
            timeStart = System.nanoTime();

            update();
            render();
            draw();
            /*update();
            render();
            draw();*/

            timeUrd = (System.nanoTime() - timeStart) / 1000000;
            timeWait = targetTime - timeUrd;
            try {
                Thread.sleep(timeWait);
            } catch (Exception e) {

            }
            if (player.isPlayerTreasureFound()) {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {

                }
                player.setPlayerTreasureFound(false);
            }
            if (player.getWumpusShowDead()) {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {

                }
                player.setWumpusShowDead(false);
            }
        }
    }

    //INITIALISING GRAPHICS
    public void initialise() {
        isRunning = true;
        image = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics1 = (Graphics2D) image.getGraphics();


        //grid tiles testMap is no longer used, make sure to delete this
        gridTiles = new GridTiles(30);
        gridTiles.loadTiles("testMap4.png");

        player = new Player(gridTiles);

        //  player.startPositionPlayer();

        player.loadPlayerTile("player.png");


    }


    /*
    *
    * methods
    *
    * */

    private void update() {


        gridTiles.update();

        if (!startSetUp) {
            player.startPositionPlayer();
            startSetUp = true;
        }

        player.update();
        gridTiles = player.getGridTiles();

        if (gameStart && computer) {
            ai.makeMove(player, gridTiles);
        }

    }

    //RENDERING METHOD
    private void render() {
        graphics1.setColor(Color.BLACK);
        graphics1.fillRect(0, 0, WIDTH, HEIGHT);


        //remember to stop the game outside!!!!

        if (!gameStart) {
            try {
                menu = ImageIO.read(new File("startMenuLogo.png"));
            } catch (IOException e) {
            }

            graphics1.drawImage(menu, 150, 100, null);

            fontFile = new File("ka1.ttf");
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(40f);
            } catch (Exception e) {
                e.printStackTrace();
            }
            graphics1.setColor(Color.WHITE);
            graphics1.setFont(font);
            graphics1.drawString("Hunt The Wumpus", 60, 470);

            fontFile = new File("font3.ttf");
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(27f);
            } catch (Exception e) {
                e.printStackTrace();
            }
            graphics1.setColor(Color.BLACK);
            graphics1.fillRect(0, 500, 600, 300);
            graphics1.setFont(font);
            if (!menuPick) {

                graphics1.setColor(Color.WHITE);

                graphics1.drawString("       Play game       ", 160, 590);
                graphics1.drawString("   [ Start AI demo ]   ", 162, 550);
            } else {

                graphics1.setColor(Color.WHITE);

                graphics1.drawString("     [ Play game ]     ", 162, 590);
                graphics1.drawString("     Start AI demo     ", 160, 550);
            }

        } else {

            gridTiles.draw(graphics1);

            player.draw(graphics1);

            //ADDS TEXT
            paint(graphics1);


        }
    }

    private void draw() {
        Graphics graphics2 = getGraphics();
        graphics2.drawImage(image, 0, 0, null);
        graphics2.dispose();

    }


    public void keyTyped(KeyEvent key) {

    }


    //KEYBOARD CONTROLS -> KEY PRESSED
    public void keyPressed(KeyEvent key) {
        int code = key.getKeyCode();
        if (!computer) {
            if (lastKey != code) {
                lastKey = code;
                if (code == KeyEvent.VK_LEFT) {
                    player.setLeft(true);

                } else if (code == KeyEvent.VK_RIGHT) {
                    player.setRight(true);

                } else if (code == KeyEvent.VK_UP) {
                    player.setUp(true);
                    //menu controls
                    if (!gameStart)
                        changeMenuPick(menuPick);

                } else if (code == KeyEvent.VK_DOWN) {
                    player.setDown(true);
                    //menu controls
                    if (!gameStart)
                        changeMenuPick(menuPick);

                } else if (code == KeyEvent.VK_SPACE) {
                    if (!gameStart) {
                        if (menuPick) {
                            player.setPlayerAlive();
                            setGameStart();
                        } else {
                            player.setPlayerAlive();
                            setGameStart();
                            setComputer(true);
                        }
                    }

                    /**
                     * shooting arrows key pressed
                     */

                } else if (code == KeyEvent.VK_A) {
                    player.setShootLeft(true);

                } else if (code == KeyEvent.VK_D) {
                    player.setShootRight(true);

                } else if (code == KeyEvent.VK_W) {
                    player.setShootUp(true);

                } else if (code == KeyEvent.VK_S) {
                    player.setShootDown(true);

                } else if (code == KeyEvent.VK_H) {
                    if (player.getPlayerAllowHint()) {
                        gridTiles.showHint();
                        hintUsed = true;
                    }
                }
            }
        }


        //maybe this kills the diagonal movement bug???


    }

    //KEYBOARD CONTROLS -> KEY RELEASED
    public void keyReleased(KeyEvent key) {

        int code = key.getKeyCode();
        if (!computer) {
            if (code == KeyEvent.VK_LEFT) {
                player.setLeft(false);
                lastKey = 0;
            } else if (code == KeyEvent.VK_RIGHT) {
                player.setRight(false);
                lastKey = 0;

            } else if (code == KeyEvent.VK_UP) {
                player.setUp(false);
                lastKey = 0;

            } else if (code == KeyEvent.VK_DOWN) {
                player.setDown(false);
                lastKey = 0;

                /**
                 * shooting arrows key released
                 */

            } else if (code == KeyEvent.VK_A) {
                player.setShootLeft(false);
                lastKey = 0;

            } else if (code == KeyEvent.VK_D) {
                player.setShootRight(false);
                lastKey = 0;

            } else if (code == KeyEvent.VK_W) {
                player.setShootUp(false);
                lastKey = 0;

            } else if (code == KeyEvent.VK_S) {
                player.setShootDown(false);
                lastKey = 0;

            }
        }
    }


    //--------------------------------


    public void paint(Graphics2D g) {


        //antialiasing

        //ANTIALIASING IS OFF DUE TO "8-BIT LIKE" GRAPHICS DESIGN DECISIONS

        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);


        Dimension d = this.getPreferredSize();

        //INSTALLING FONTS
        fontFile = new File("font3.ttf");
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(27f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        g.setColor(Color.BLACK);
        g.fillRect(0, 600, 600, 200);


        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString("Moves made: " + Integer.toString(player.getMovesMade()), 50, 650);
        g.drawString("Arrows: " + Integer.toString(player.getPlayerArrows()), 350, 650);
        if (player.getPlayerAllowHint() && !hintUsed) {
            g.drawString("Press \"H\" for a hint", 50, 680);

        }


        fontFile = new File("ka1.ttf");
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(30f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        graphics1.setColor(Color.WHITE);
        graphics1.setFont(font);
        //font.deriveFont(10f);
        //  g.setFont(font);

        //   g.drawString("Wumpus killed: ", 50, 700);


        //STATEMENTS FOR IN-GAME MESSAGES IN THE MIDDLE OF THE SCREEN

        if (player.isPlayerDead()) {

            g.drawString("You have lost!", 150, 300);
        } else if (player.isPlayerWon()) {


            g.drawString("You have won!", 160, 300);
        } else if (player.isPlayerTreasureFound()) {
            g.drawString("Treasure found!", 130, 300);
        } else if (player.getWumpusShowDead()) {
            g.drawString("Wumpus killed!", 140, 300);
        }

    }
    //--------------------------------


}
