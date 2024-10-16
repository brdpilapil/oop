package objects;

import java.awt.Image;

public class BottomPipe extends Pipe {

    private int speed;
    public BottomPipe(int x, int y, int width, int height, Image img, double score) {
        super(x, y, width, height, img);
        this.speed = (int) (score/5 * 2);
    }  

    public void moveVertically() {
      
        
        setPipeY(getPipeY() + speed * direction);

        if (getPipeY() <= 128 + 160 || getPipeY() >= 512) {
            speed = -speed;
        }
    }

    public int getPipeY() {
        return pipeY;
    }

    public void setPipeY(int pipeY) {
        this.pipeY = pipeY;
    }

}
