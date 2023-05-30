import java.sql.*;
public class Database {
    private static String url;
    private static String user = "root";
    private static String password = "mahdi_ramezani1234";

    public static void read() {
        try {
            url = "jdbc:mysql://127.0.0.1:3306/game";

            Connection conn = DriverManager.getConnection(url, user, password);

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            while (rs.next()) {
                Main.users.add(new User(rs.getString("name"),rs.getInt("score")));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void write(){
        url = "jdbc:mysql://127.0.0.1:3306/game?useSSL=false";

        // Create a connection to the MySQL database
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Statement stmt = conn.createStatement();
            // Truncate the patient table to remove any existing data
            stmt.executeUpdate("TRUNCATE TABLE users");

            // Insert all patients from the array list into the patient table
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users(name," +
                    " score) VALUES (?, ?)");
            for (User u : Main.users) {
                ps.setString(1, u.name);
                ps.setInt(2, u.score);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}