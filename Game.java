
package main;

import objects.GameChar;
import objects.Pipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import objects.BottomPipe;
import objects.TopPipe;


public class Game extends JPanel implements ActionListener, KeyListener {
    
    public static final int BOARD_WIDTH = 360;
    public static final int BOARD_HEIGHT = 640;

    // Images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    // Game logic
    GameChar bird;
    int velocityX = -4;
    int velocityY = 0;
    int gravity = 1;
    
    int pipeHeight = 512;
    int pipeWidth = 64;
    int pipeY = 0;
    int pipeX = BOARD_WIDTH;
    
    
    ArrayList<TopPipe> topPipes;
    ArrayList<BottomPipe> bottomPipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipeTimer;
    boolean gameOver = false;
    double score = 0;
    double highscore = 0;
    

    Game() throws IOException {
        
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        // Load images
        backgroundImg = GameUI.loadImg(GameUI.BACKGROUND);
        birdImg = GameUI.loadImg(GameUI.BIRD);
        topPipeImg = GameUI.loadImg(GameUI.TOP_PIPE);
        bottomPipeImg = GameUI.loadImg(GameUI.BOTTOM_PIPE);

        bird = new GameChar(birdImg);
        topPipes = new ArrayList<>();
        bottomPipes = new ArrayList<>();

        // Place pipes timer
        placePipeTimer = new Timer(1800, e -> placePipes());
        placePipeTimer.start();

        // Game timer
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }
    
    void setHighscore(double score){
        if (score > highscore)
            highscore = score;
    }

    void placePipes() {
        int openingSpace = BOARD_HEIGHT / 4;
        int randomTopPipeY = random.nextInt(pipeY-pipeHeight + BOARD_HEIGHT / 5,
                pipeY-pipeHeight + 352);
        TopPipe topPipe = new TopPipe(pipeX, randomTopPipeY, pipeWidth, pipeHeight, topPipeImg, score);
        
        int randomBottomPipeY = randomTopPipeY + pipeHeight + openingSpace;
        BottomPipe bottomPipe = new BottomPipe(BOARD_WIDTH, randomBottomPipeY, 64, 512, bottomPipeImg, score);
        
        if (score >= 5) {
        float velocityAdjustment = 0.6f;
        topPipe.setPipeVelocity(velocityAdjustment);
        bottomPipe.setPipeVelocity(velocityAdjustment);
        
        int initialDirection = random.nextBoolean() ? 1 : -1;
        topPipe.setDirection(initialDirection);
        bottomPipe.setDirection(initialDirection);
        }

        topPipes.add(topPipe);
        bottomPipes.add(bottomPipe);
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, BOARD_WIDTH, BOARD_HEIGHT, null);
        g.drawImage(bird.getImg(), bird.getBirdX(), bird.getBirdY(),
                bird.getBirdWidth(), bird.getBirdHeight(), null);

        for (TopPipe pipe : topPipes) {
            g.drawImage(pipe.getImg(), pipe.getPipeX(), pipe.getPipeY(),
                    pipe.getPipeWidth(), pipe.getPipeHeight(), null);
        }
        
        for (BottomPipe pipe : bottomPipes) {
            g.drawImage(pipe.getImg(), pipe.getPipeX(), pipe.getPipeY(),
                    pipe.getPipeWidth(), pipe.getPipeHeight(), null);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.ROMAN_BASELINE, 32));
        if (gameOver) {
            g.drawString("Game Over: " + (int) score, 10, 35);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
            g.drawString("HighScore: " + String.valueOf((int) highscore), 150, 35);
        }
    }

    public void move() {
        velocityY += gravity;
        
        bird.setBirdY(Math.min(bird.getBirdY() + velocityY, BOARD_HEIGHT));

        for (TopPipe pipe : topPipes) {
            pipe.moveHorizontally(velocityX);
            if (score >= 5) {
                pipe.moveVertically();
            }
            if (!pipe.isPassed() && bird.getBirdX() > pipe.getPipeX() + pipe.getPipeWidth()) {
                score += 0.5;
                pipe.setPassed(true);
            }
            if (collision(bird, pipe)) {
                setHighscore(score);
                gameOver = true;
            }
        }

        for (BottomPipe pipe : bottomPipes) {
            pipe.moveHorizontally(velocityX);
            if (score >= 5) {
                pipe.moveVertically();
                
            }
            if (!pipe.isPassed() && bird.getBirdX() > pipe.getPipeX() + pipe.getPipeWidth()) {
                score += 0.5;
                pipe.setPassed(true);
            }
            if (collision(bird, pipe)) {
                setHighscore(score);
                gameOver = true;
            }
        }

        if (bird.getBirdY() >= BOARD_HEIGHT) {
            gameOver = true;
        }
    }

    boolean collision(GameChar a, Pipe b) {
        return a.getBirdX() < b.getPipeX() + b.getPipeWidth()
                && a.getBirdX() + a.getBirdWidth() > b.getPipeX()
                && a.getBirdY() < b.getPipeY() + b.getPipeHeight()
                && a.getBirdY() + a.getBirdHeight() > b.getPipeY();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
            repaint();
        } else {
            placePipeTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameOver) {
                resetGame();
            } else {
                velocityY = -9;
            }
        }
    }

    private void resetGame() {
        velocityY = 0;
        bird.setBirdY(BOARD_HEIGHT / 2);
        topPipes.clear();
        bottomPipes.clear();
        gameOver = false;
        score = 0;
        gameLoop.start();
        placePipeTimer.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
