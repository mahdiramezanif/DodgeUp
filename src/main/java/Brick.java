import processing.core.PApplet;

import java.util.*;

public class Brick extends PApplet implements Interface{
    static float brickWidth = 40;
    static float brickHeight = 70;
    int brickColorR;
    int brickColorG;
    int brickColorB;
    float brickX;
    float brickY;
    static int madeBricks = 0;

    public Brick() {
        Random rand = new Random();
        int r =255;
        int g =255;
        int b =255;
        while (r==255 && g==255&& b == 255) {
            r = rand.nextInt(256);
            g = rand.nextInt(256);
            b = rand.nextInt(256);
        }
        this.brickColorR = rand.nextInt(256);
        this.brickColorG = rand.nextInt(256);
        this.brickColorB = rand.nextInt(256);
    }
    public void setPosition(float x, float y){
        this.brickX = x;
        this.brickY = y;
    }
    public static boolean collidesWith(Brick oldBrick, Brick newBrick){
//        Checks whether these two bricks overlap or not
        float xDistance = newBrick.brickX - oldBrick.brickX;
        float yDistance = newBrick.brickY - oldBrick.brickY;
        if ((xDistance<=brickWidth && xDistance>=(-1*brickWidth)) ||
                (yDistance<=brickHeight && yDistance>=(-1*brickHeight)))
            return true;
        return false;
    }
    public void showObjects(float x, float y){
            boolean validPosition = true;
            Brick brick = new Brick();

            brick.setPosition(x, y); // set random position at top of screen

                // check if the brick collides with any other bricks already on the screen
                for (Brick existingBrick : Main.bricks) {
                    if (collidesWith(existingBrick, brick)) {
                        validPosition = false;
                        break; // no need to check other bricks once collision is detected
                    }
                }

            if (validPosition){
                ArrayList<Brick> closedBricks = new ArrayList<>();
//                This array is a collection of bricks that have a small vertical distance from the current brick
                closedBricks.add(brick);
                double closeDistance = (1.5*Main.humanSize)+brickHeight;
                for (Brick b:Main.bricks) {
                    float yDistance = brick.brickY - b.brickY;
                    if (yDistance<=(closeDistance) && yDistance>=(-1*closeDistance))
                        closedBricks.add(b);
                }
                Collections.sort(closedBricks, Comparator.comparingDouble(Brick::getBrickX));
//                Sort the bricks by their x-component

                if (closedBricks.size() == 1){
                    Main.bricks.add(brick);
                    madeBricks ++ ;
                }
                else {
                    for (int i = 0; i < closedBricks.size() - 1; i++) {
                        if (closedBricks.get(i + 1).getBrickX() - closedBricks.get(i).getBrickX() > Main.humanSize) {
                            Main.bricks.add(brick);
                            madeBricks++;
                            break;
                        }
                    }
                }
            }
    }

    public float getBrickX() {
        return brickX;
    }

}
