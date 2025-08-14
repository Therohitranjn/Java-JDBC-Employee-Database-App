package emp;
import java.sql.*;
import java.util.Scanner;

public class EmployeeDBApp {

    // ORACLE Database Configuration
    static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl"; 
    static final String USER = "RANJAN";  // My username
    static final String PASSWORD = "HANUMAN";  // my password

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // 1. Load Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // 2. Connect to database
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to Oracle Database");

            while (true) {
                System.out.println("--- Employee Database Menu ---");
                System.out.println("1. Add Employee");
                System.out.println("2. View Employees");
                System.out.println("3. Update Employee Salary");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine(); 
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Department: ");
                        String dept = sc.nextLine();
                        System.out.print("Enter Salary: ");
                        double sal = sc.nextDouble();

                        PreparedStatement ps = con.prepareStatement(
                                "INSERT INTO employees (id, name, department, salary) VALUES (?, ?, ?, ?)");
                        ps.setInt(1, id);
                        ps.setString(2, name);
                        ps.setString(3, dept);
                        ps.setDouble(4, sal);
                        ps.executeUpdate();
                        System.out.println(" Employee Added");
                        break;

                    case 2:
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery("SELECT * FROM employees");
                        System.out.println("\nID\tName\tDepartment\tSalary");
                        while (rs.next()) {
                            System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" +
                                    rs.getString(3) + "\t" + rs.getDouble(4));
                        }
                        break;

                    case 3:
                        System.out.print("Enter Employee ID to Update Salary: ");
                        int uid = sc.nextInt();
                        System.out.print("Enter New Salary: ");
                        double newSal = sc.nextDouble();

                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE employees SET salary = ? WHERE id = ?");
                        ps2.setDouble(1, newSal);
                        ps2.setInt(2, uid);
                        int updated = ps2.executeUpdate();
                        if (updated > 0)
                            System.out.println(" Salary Updated");
                        else
                            System.out.println("Employee Not Found");
                        break;

                    case 4:
                        System.out.print("Enter Employee ID to Delete: ");
                        int did = sc.nextInt();
                        PreparedStatement ps3 = con.prepareStatement(
                                "DELETE FROM employees WHERE id = ?");
                        ps3.setInt(1, did);
                        int deleted = ps3.executeUpdate();
                        if (deleted > 0)
                            System.out.println(" Employee Deleted");
                        else
                            System.out.println("Employee Not Found");
                        break;

                    case 5:
                        con.close();
                        System.out.println("Goodbye!");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("SInvalid Choice");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
