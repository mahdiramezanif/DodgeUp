import processing.core.PApplet;

import java.util.*;

public class Brick extends PApplet {
    static PApplet a = Main.processing;
    static float brickWidth = 40;
    static float brickHeight = 70;
    static int screenTop = 0;
    static int screenBottom = 700;
    static int screenLeft = 0;
    static int screenRight = 400;
    int brickColorR;
    int brickColorG;
    int brickColorB;
    float brickX;
    float brickY;
    static int madeBricks = 0;

    public Brick() {
        Random rand = new Random();
        this.brickColorR = rand.nextInt(256);
        this.brickColorG = rand.nextInt(256);
        this.brickColorB = rand.nextInt(256);
    }
    public void setPosition(float x, float y){
        this.brickX = x;
        this.brickY = y;
    }
    public boolean collidesWith(Brick oldBrick, Brick newBrick){
        float xDistance = newBrick.brickX - oldBrick.brickX;
        float yDistance = newBrick.brickY - oldBrick.brickY;
        if ((xDistance<=brickWidth && xDistance>=(-1*brickWidth)) || (yDistance<=brickY && yDistance>=(-1*brickY)))
            return true;
        return false;
    }
    public void addBrick(){
            boolean validPosition = false;
            int attempt = 0;
            Brick brick = new Brick();

            // loop until we find a valid position for the brick that doesn't collide with any others
            while (!validPosition && attempt<1000) {
                brick.setPosition(random(screenLeft + (brickWidth / 2), screenRight - (brickWidth / 2)),
                        random(-50*brickHeight,screenTop - brickHeight / 2)); // set random position at top of screen
                validPosition = true; // assume position is valid unless proven otherwise

                // check if the brick collides with any other bricks already on the screen
                for (Brick existingBrick : Main.bricks) {
                    if (collidesWith(existingBrick, brick)) {
                        validPosition = false;
                        break; // no need to check other bricks once collision is detected
                    }
                }
                attempt ++;
            }

            if (validPosition){
                ArrayList<Brick> closedBricks = new ArrayList<>();
                closedBricks.add(brick);
                double closeDistance = (1.5*Human.high)+brickHeight;
                for (Brick b:Main.bricks) {
                    float yDistance = brick.brickY - b.brickY;
                    if (yDistance<=(closeDistance) && yDistance>=(-1*closeDistance))
                        closedBricks.add(b);
                }
                Collections.sort(closedBricks, Comparator.comparingDouble(Brick::getBrickX));

                for (int i = 0; i < closedBricks.size() - 1; i++) {
                    if (closedBricks.get(i + 1).getBrickX() - closedBricks.get(i).getBrickX() > Human.width) {
                        Main.bricks.add(brick);
                        madeBricks ++ ;
                        break;
                    }
                }
            }
    }

    public float getBrickX() {
        return brickX;
    }
}
