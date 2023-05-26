import processing.core.PApplet;
import processing.core.PImage;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Main extends PApplet {
    public static int speedY = 5;
    public static int speed = 5;
    public static PApplet processing;
    public static ArrayList<Brick> bricks = new ArrayList<Brick>();
    static int screenTop = 0;
    static int screenBottom = 700;
    static int screenLeft = 0;
    static int screenRight = 400;
    MouseEvent e;
    PImage img;

    public static void main(String[] args) {
        PApplet.main("Main", args);
    }

    @Override
    public void setup() {
        processing = this;
        img = loadImage("background.jpg");
        img.resize(width, height); // resize the image to match window size
        Brick brick = new Brick();
        while (Brick.madeBricks <= 5){
            Brick.addBrick(random(screenLeft + (Brick.brickWidth / 2),screenRight - (Brick.brickWidth / 2)),
                    random(-50*Brick.brickHeight,screenTop - Brick.brickHeight / 2));
        }
    }

    @Override
    public void settings() {
        size(400, 700);
    }

    @Override
    public void draw() {
        background(img);

        if (e != null && e.getButton() == MouseEvent.BUTTON1) {
            speed += 5;
        } else if (e != null && e.getButton() == MouseEvent.BUTTON3) {
            speed -= 5;
        }
    }
}
