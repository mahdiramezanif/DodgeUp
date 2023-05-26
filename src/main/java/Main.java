import processing.core.PApplet;
import processing.core.PImage;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Main extends PApplet {
    public static int speedY = 5;
    public static int speed = 5;
    public static PApplet processing;
    public static ArrayList<Brick> bricks = new ArrayList<Brick>();
    MouseEvent e;

    public static void main(String[] args) {
        PApplet.main("Main", args);
    }

    @Override
    public void setup() {
        processing = this;
        Brick brick = new Brick();
        while (Brick.madeBricks <= 50){
            brick.addBrick();
        }
    }

    @Override
    public void settings() {
        size(400, 700);
    }

    @Override
    public void draw() {
        PImage img;
        img = loadImage("D:\\Learning\\University\\planing\\code\\Java\\part2\\background.jpg");
        background(img);

        if (e.getButton() == MouseEvent.BUTTON1) {
                speed += 5;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
                speed -= 5;
        }
    }
}
