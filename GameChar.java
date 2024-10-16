package objects;

import java.awt.Image;
import static main.Game.BOARD_WIDTH;


public class GameChar {

    private int birdX = BOARD_WIDTH / 8;
    private int birdY = BOARD_WIDTH / 2;
    private int birdWidth = 34;
    private int birdHeight = 24;


    Image birdImg;

    public GameChar(Image img) {
        this.birdImg = img;
        
    }

    public int getBirdX() {
        return birdX;
    }

    public int getBirdY() {
        return birdY;
    }

    public int getBirdWidth() {
        return birdWidth;
    }

    public int getBirdHeight() {
        return birdHeight;
    }

    public void setBirdX(int birdX) {
        this.birdX = birdX;
    }

    public void setBirdY(int birdY) {
        this.birdY = birdY;
    }

    public void setBirdWidth(int birdWidth) {
        this.birdWidth = birdWidth;
    }

    public void setBirdHeight(int birdHeight) {
        this.birdHeight = birdHeight;
    }

    public Image getImg() {
        return birdImg;
    }



}
