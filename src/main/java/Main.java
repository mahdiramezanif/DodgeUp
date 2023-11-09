import processing.core.PApplet;
import processing.core.PImage;
import java.awt.event.MouseEvent;
import java.util.*;

public class Main extends PApplet {
    public static int speedY = 5;
    int hit = 0;
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
    PImage img;
    PImage man;
    Display display = new Display();
    static int forGamingOver = 0;
    static int scoreItem = 0;
    static int passedBrick = 0;
    static int score = 0;
    boolean finish = false;
    public static ArrayList<User> users = new ArrayList<>();
    static User user;
    boolean notPrinted = true;
    int passedItem = 0;

    public static void main(String[] args) {
        Database.read();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name : ");
        String newName = scanner.nextLine();
        boolean exist = false;
        for (User u : users){
            if (u.name.equals(newName)) {
                exist = true;
                user = u;
                break;
            }
        }
        if (!exist){
            user =new User(newName,0);
            users.add(user);
        }
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
        if (forGamingOver > 1){
                background(0);
                fill(250, 0, 0);
                textSize(60);
                text("GAME OVER !!!", 20, 350);
                fill(250, 0, 0);
                textSize(20);
                String s = "Score : ".concat(Integer.toString(score));
                text(s, 40, 450);
            print();
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
            print();
        }
        else {
            background(img);
            display.show();
            image(man, mouseX, humanY, humanSize, humanSize);
            hit();
            score = scoreItem + passed();
            fill(255);
            textSize(20);
            String s = "Live : ".concat(Integer.toString(2-forGamingOver)).concat("   ,  Score : ")
                    .concat(Integer.toString(score));
            text(s, 20, 20);
        }
}

    public void hit(){
        float safeY = (humanSize/2) + ((Brick.brickHeight)/2);
        float safeX = (humanSize/2) + ((Brick.brickWidth)/2);
        Iterator<Brick> iter = bricks.iterator();
        while (iter.hasNext()) {
            Brick b = iter.next();
            float yDistance = b.brickY - humanY;
            float xDistance = b.brickX - mouseX;
            if ((yDistance<=safeY && yDistance>=(-1*safeY))&&(xDistance<=safeX && xDistance>=(-1*safeX))){
                forGamingOver++;
                hit ++ ;
                iter.remove();
            }
        }
        Iterator<Item> iter2 = items.iterator();
        while (iter2.hasNext()) {
            Item i = iter2.next();
            float yDistance = i.y - humanY;
            float xDistance = i.x - mouseX;
            if ((yDistance<=safeY && yDistance>=(-1*safeY))&&(xDistance<=safeX && xDistance>=(-1*safeX))){
                scoreItem ++ ;
                hit ++;
                iter2.remove();
            }
        }
    }
    public int passed(){
        passedBrick = 0;
        passedItem = 0;
        for (Brick b : bricks){
            if (b.brickY>(screenBottom+((Brick.brickHeight)/2)))
                passedBrick ++;
        }
        for (Item i : items){
            if (i.y>(screenBottom+((Brick.brickHeight)/2)))
                passedItem ++;
        }
        if ((passedBrick+passedItem) == 10-hit)
            finish = true;
        return passedBrick;
    }
    public void print(){
        if (notPrinted){
            Database.write();
            if (user.score < score)
                user.score = score;
            System.out.println(user.showTop());
            notPrinted = false;
        }
    }
}
