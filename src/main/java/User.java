import java.util.Collections;
import java.util.Comparator;

public class User {
    static int count = 0;
    int id;
    String name;
    int score;

    public User(String name, int score) {
        this.name = name;
        this.score = score;
        count++;
        id = count ;
    }
    public String showTop(){
        String s = "Top players are : \n";
        Collections.sort(Main.users, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return Integer.compare(u2.score, u1.score);
            }
        });

        // Print sorted list
        for (User user : Main.users) {
            s = s.concat(user.name + " : " + user.score + "\n");
        }
        return s;
    }
}
