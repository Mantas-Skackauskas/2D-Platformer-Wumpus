package src;
//GAME CLASS

import javax.swing.JFrame;

public class Game {
    public static void main(String[] args) {
        //DEFINING WINDOW FRAMES
        JFrame window = new JFrame("Hunt The Wumpus");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new Screen());
        window.pack();
        window.setVisible(true);

    }
}
