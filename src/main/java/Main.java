import processing.core.PApplet;
import processing.core.PImage;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Main extends PApplet {
    public static int speedY = 5;
    public static PApplet processing;
    public static ArrayList<Brick> bricks = new ArrayList<Brick>();
    public static ArrayList<Item> items = new ArrayList<Item>();
    static int screenTop = 0;
    static int screenBottom = 700;
    static int screenLeft = 0;
    static int screenRight = 400;
    int objectNumber = 5;
    MouseEvent e;
    PImage img;
    Display display = new Display();

    public static void main(String[] args) {
        PApplet.main("Main", args);
    }

    @Override
    public void setup() {
        processing = this;
        img = loadImage("background.jpg");
        img.resize(width, height); // resize the image to match window size

        Brick brick = new Brick();
        Item item = new Item();

        while (Brick.madeBricks < objectNumber){
            brick.showObjects(random(screenLeft + (Brick.brickWidth / 2),screenRight - (Brick.brickWidth / 2)),
                    random(-50*Brick.brickHeight,screenTop - Brick.brickHeight / 2));
        }
        while (Item.madeItems < objectNumber){
            item.showObjects(random(screenLeft + (Item.r / 2),screenRight - (Item.r / 2)),
                    random(-50*Item.r,screenTop - Item.r / 2));
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
            speedY += 5;
        } else if (e != null && e.getButton() == MouseEvent.BUTTON3) {
            speedY -= 5;
        }
//        for (Brick b : bricks) {
//            showBrick(b.brickX, b.brickY, b.brickColorR, b.brickColorG, b.brickColorB);
//        }
//        for (Brick b : bricks) {
//            b.brickY += speedY;
//        }
//        noStroke();

        display.show();
//        fill(12,60,34);
//        circle(100,100,50);
    }
//    public void showBrick ( float x, float y, int R, int G, int B){
//        fill(R, G, B);
//        rect(x, y, Brick.brickWidth, Brick.brickHeight);
////        rect(10,10,10,10);
//    }
}
