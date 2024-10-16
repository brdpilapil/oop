package objects;

import java.awt.Image;

public class TopPipe extends Pipe {
    
    private int speed;
    public TopPipe(int x, int y, int width, int height, Image img, double score) {
        super(x, y, width, height, img);
        this.speed = (int) (score/5 * 2);;
    }

    public void moveVertically() {
        
        
        setPipeY(getPipeY() + speed * direction);

        if (getPipeY() + getPipeHeight() <= 128 || getPipeY() + getPipeHeight() >= 512 - 160) {
            speed = -speed;
        }
    }

    public int getPipeY() {
        return pipeY;
    }

    public void setPipeY(int pipeY) {
        this.pipeY = pipeY;
    }

    public int getPipeHeight() {
        return pipeHeight;
    }

    public void setPipeHeight(int pipeHeight) {
        this.pipeHeight = pipeHeight;
    }
    
}
