
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CourseListing {
    public void listCourses() {
        String url = "jdbc:mysql://localhost:3306/javaProject";
        String user = "root";
        String password = "pass123";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM courses")) {

            while (rs.next()) {
                int courseId = rs.getInt("course_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int capacity = rs.getInt("capacity");
                String schedule = rs.getString("schedule");
                int slotsAvailable = rs.getInt("slots_available");

                System.out.println("Course ID: " + courseId);
                System.out.println("Title: " + title);
                System.out.println("Description: " + description);
                System.out.println("Capacity: " + capacity);
                System.out.println("Schedule: " + schedule);
                System.out.println("Available Slots: " + slotsAvailable);
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
