import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRegistration {
    public static void registerCourse(String studentId, String courseId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Establish connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaProject", "root", "pass123");

            // Check if there are available slots
            String checkSlotsQuery = "SELECT slots_available FROM courses WHERE course_id = ?";
            pstmt = conn.prepareStatement(checkSlotsQuery);
            pstmt.setString(1, courseId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int slotsAvailable = rs.getInt("slots_available");
                if (slotsAvailable > 0) {
                    // Register student
                    String registerQuery = "INSERT INTO registrations (student_id, course_id) VALUES (?, ?)";
                    pstmt = conn.prepareStatement(registerQuery);
                    pstmt.setString(1, studentId);
                    pstmt.setString(2, courseId);
                    pstmt.executeUpdate();

                    // Update slots available
                    String updateSlotsQuery = "UPDATE courses SET slots_available = slots_available - 1 WHERE course_id = ?";
                    pstmt = conn.prepareStatement(updateSlotsQuery);
                    pstmt.setString(1, courseId);
                    pstmt.executeUpdate();

                    System.out.println("Registration successful!");
                } else {
                    System.out.println("No slots available for this course.");
                }
            } else {
                System.out.println("Course not found.");
            }
            
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
