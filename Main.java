import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourseListing courseListing = new CourseListing();
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaProject", "root", "pass123");
            
            while (true) {
                System.out.println("Student Course Registration System");
                System.out.println("1. List Available Courses");
                System.out.println("2. Register for a Course");
                System.out.println("3. Drop a Course");
                System.out.println("4. Exit");
                System.out.print("Select an option: ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        courseListing.listCourses();
                        break;
                    case 2:
                        System.out.print("Enter your Student ID: ");
                        int studentId = scanner.nextInt();
                        System.out.print("Enter Course ID to register: ");
                        int courseId = scanner.nextInt();
                        StudentRegistration.registerCourse(conn, studentId, courseId);
                        break;
                    case 3:
                        System.out.print("Enter your Student ID: ");
                        studentId = scanner.nextInt();
                        System.out.print("Enter Course ID to drop: ");
                        courseId = scanner.nextInt();
                        CourseRemoval.dropCourse(conn, studentId, courseId);
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
                scanner.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
