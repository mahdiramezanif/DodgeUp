import java.util.Random;

public class Item implements Interface{
    private static final Random random = new Random();
    static float r = 40;
    float x;
    float y;
    static int madeItems = 0;

    public void showObjects(float x, float y){
        Item i = new Item();
        i.x = x;
        i.y = y;
        Main.items.add(i);
        madeItems++;
    }

}
