package main;

import javax.swing.JFrame;

/**
 *
 * @author Tuff Gaming
 */
public class Main {

    public static void main(String[] args) throws Exception {
        int BOARD_WIDTH = 360;
        int BOARD_HEIGHT = 640;

        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game game = new Game();
        frame.add(game);
        frame.pack();
        game.requestFocus();
        frame.setVisible(true);
    }
}