
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRegistration {

    public static void registerCourse(Connection conn, int studentId, int courseId) {
        try {
            // Check if the course exists and has available slots
            String query = "SELECT slots_available FROM courses WHERE course_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int slotsAvailable = rs.getInt("slots_available");

                if (slotsAvailable > 0) {
                    // Register the student for the course
                    String insertQuery = "INSERT INTO registrations (student_id, course_id) VALUES (?, ?)";
                    PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                    insertStmt.setInt(1, studentId);
                    insertStmt.setInt(2, courseId);
                    insertStmt.executeUpdate();

                    // Update the available slots
                    String updateQuery = "UPDATE courses SET slots_available = slots_available - 1 WHERE course_id = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                    updateStmt.setInt(1, courseId);
                    updateStmt.executeUpdate();

                    System.out.println("Successfully registered for the course!");
                } else {
                    System.out.println("Sorry, the course is full.");
                }
            } else {
                System.out.println("Course not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
