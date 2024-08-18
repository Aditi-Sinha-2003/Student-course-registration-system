import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourseRemoval {

    public static void dropCourse(Connection conn, int studentId, int courseId) {
        PreparedStatement pstmt = null;
        try {
            // Remove registration
            String removeQuery = "DELETE FROM registrations WHERE student_id = ? AND course_id = ?";
            pstmt = conn.prepareStatement(removeQuery);
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                // Update slots available
                String updateSlotsQuery = "UPDATE courses SET slots_available = slots_available + 1 WHERE course_id = ?";
                pstmt = conn.prepareStatement(updateSlotsQuery);
                pstmt.setInt(1, courseId);
                pstmt.executeUpdate();

                System.out.println("Course dropped successfully.");
            } else {
                System.out.println("No registration found for the specified student and course.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
