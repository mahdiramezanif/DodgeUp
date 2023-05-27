import processing.core.PApplet;

public class Display extends PApplet {
    public static PApplet p = Main.processing;

    public void show() {
        for (Brick b : Main.bricks) {
            showBrick(b.brickX, b.brickY, b.brickColorR, b.brickColorG, b.brickColorB);
        }
        for (Brick b : Main.bricks) {
            b.brickY += Main.speedY;
        }
        for (Item i : Main.items) {
            showItem(i.x, i.y);
        }
        for (Item i : Main.items) {
            i.y += Main.speedY;
        }
    }

        public void showBrick ( float x, float y, int R, int G, int B){
            Main.processing.fill(R, G, B);
            Main.processing.rect(x, y, Brick.brickWidth, Brick.brickHeight);
        }
        public static void showItem ( float x, float y){
            Main.processing.fill(255);
            Main.processing.circle(x,y,Item.r);
        }
}
