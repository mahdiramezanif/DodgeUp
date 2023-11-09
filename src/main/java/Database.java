import java.sql.*;
public class Database {
    private static String url;
    private static String user = "root";
    private static String password = "mahdi_ramezani1234";

    public static void read() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            url = "jdbc:mysql://127.0.0.1:3306/game";

            Connection conn = DriverManager.getConnection(url, user, password);

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM  gameinformation.users");

            while (rs.next()) {
                Main.users.add(new User(rs.getString("name"),rs.getInt("score")));
            }
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void write() {
        url = "jdbc:mysql://127.0.0.1:3306/game";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            // Start transaction
            conn.setAutoCommit(false);

            try {
                for (User u : Main.users) {
                    PreparedStatement ps = conn.prepareStatement("SELECT * FROM gameinformation.users WHERE name = ?");
                    ps.setString(1, u.name);
                    ResultSet rs = ps.executeQuery();

                    // If the user exists, update their score
                    if (rs.next()) {
                        int currentScore = rs.getInt("score");
                        int newScore = currentScore + u.score;
                        ps = conn.prepareStatement("UPDATE gameinformation.users SET score = ? WHERE name = ?");
                        ps.setInt(1, newScore);
                        ps.setString(2, u.name);
                        ps.executeUpdate();
                    }
                    // If the user doesn't exist, insert a new row
                    else {
                        ps = conn.prepareStatement("INSERT INTO gameinformation.users (name, score) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, u.name);
                        ps.setInt(2, u.score);
                        ps.executeUpdate();

                        // Get the generated id for the newly inserted row
                        ResultSet generatedKeys = ps.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            u.id= generatedKeys.getInt(3); // Set the id of the User object to the generated id
                        }
                    }
                }

                // Commit transaction
                conn.commit();
            } catch (Exception e) {
                // Rollback transaction if an error occurred
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }

            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}