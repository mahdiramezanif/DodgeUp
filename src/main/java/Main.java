import processing.core.PApplet;
import processing.core.PImage;

import java.awt.event.MouseEvent;
import java.util.*;

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
    static float humanSize = 50;
    static float humanY = 600;
    MouseEvent e;
    PImage img;
    PImage man;
    Display display = new Display();
    static int forGamingOver = 0;
    static int scoreItem = 0;
    static int passedBrick;
    static int score;
    boolean finish = false;

    public static void main(String[] args) {
        PApplet.main("Main", args);
    }

    @Override
    public void setup() {
        processing = this;
        img = loadImage("background.jpg");
        img.resize(width, height); // resize the image to match window size

        man = loadImage("man.jpg");

        Brick brick = new Brick();
        Item item = new Item();

        while (Brick.madeBricks < objectNumber) {
            brick.showObjects(random(screenLeft + (Brick.brickWidth / 2), screenRight - (Brick.brickWidth / 2)),
                    random(-50 * Brick.brickHeight, screenTop - Brick.brickHeight / 2));
        }
        while (Item.madeItems < objectNumber) {
            item.showObjects(random(screenLeft + (Brick.brickWidth / 2), screenRight - (Brick.brickWidth / 2)),
                    random(-50 * Brick.brickHeight, screenTop - Brick.brickHeight / 2));
        }
    }

    @Override
    public void settings() {
        size(400, 700);
    }

    @Override
    public void draw () {
        if (forGamingOver > 2){
                background(0);
                fill(250, 0, 0);
                textSize(60);
                text("GAME OVER !!!", 20, 350);
                fill(250, 0, 0);
                textSize(20);
                String s = "Score : ".concat(Integer.toString(score));
                text(s, 40, 450);
        }
        else if(finish){
            background(255);
            fill(0, 250, 0);
            textSize(60);
            text("You Won :) ", 40, 350);
            fill(0, 250, 0);
            textSize(20);
            String s = "Total score : ".concat(Integer.toString(score));
            text(s, 40, 450);
        }
        else {
            background(img);

            display.show();
            image(man, mouseX, humanY, humanSize, humanSize);
            hit();
            score = scoreItem + passed();
            fill(255);
            textSize(20);
            String s = "Live : ".concat(Integer.toString(3-forGamingOver)).concat("   ,  Score : ")
                    .concat(Integer.toString(score));
            text(s, 20, 20);
        }
}

    public void hit(){
        float safeY = (humanSize/2) + ((Brick.brickHeight)/2);
        float safeX = (humanSize/2) + ((Brick.brickWidth)/2);
        for (Brick b : bricks){
            float yDistance = b.brickY - humanY;
            float xDistance = b.brickX - mouseX;
            if ((yDistance<=safeY && yDistance>=(-1*safeY))&&(xDistance<=safeX && xDistance>=(-1*safeX))){
                lost();
            }
        }
        for (Item i : items){
            float yDistance = i.y - humanY;
            float xDistance = i.x - mouseX;
            if ((yDistance<=safeY && yDistance>=(-1*safeY))&&(xDistance<=safeX && xDistance>=(-1*safeX))){
                scoreItem ++ ;
            }
        }
    }
    public void lost(){
            background(0);
            fill(0, 0, 250);
            textSize(40);
            text("You Lost !", 40, 350);
            forGamingOver++;
    }
    public int passed(){
        passedBrick = 0;
        for (Brick b : bricks){
            if (b.brickY>(screenBottom+((Brick.brickHeight)/2)))
                passedBrick ++;
        }
        for (Item i : items){
            if (i.y>(screenBottom+((Brick.brickHeight)/2)))
                passedBrick ++;
        }
        if (passedBrick == 10)
            finish = true;

        return passedBrick;
    }
    public int press(){
        if (keyPressed) {
            if (key == 'f' || key == 'F') {
                return 1;
            } else if (key == 's' || key == 'S') {
                return 2;
            } else if (keyCode == 32) { // 32 is the keyCode for space bar
                return 3;
            }
        }
        return 0;
    }
}
