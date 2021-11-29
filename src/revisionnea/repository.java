
package revisionnea;
import revisionnea.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
public class repository {
    private static User currentUser;
    private static final String DatabaseLocation = System.getProperty("user.dir") + "\\RevisionNEA.accdb";
    private static Connection con;
    
     public static Connection getConnection() {
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            return con;
        } catch (Exception e) {
            System.out.println("Error in the repository class: " + e);
        }
        return null;
    }
     
    public static void insertNewUser(User user) {

        try {
            String sql = "SELECT * FROM Userdetails";
            ResultSet rs = SQLexecute.executeQuery(getConnection(), sql);
            if (rs.next()) {
                rs.moveToInsertRow();
                rs.updateString("NameOU", user.getName());
                rs.updateString("Username", user.getUsername());
                rs.updateString("Password", user.getHPassword());
                rs.updateString("E-mail", user.getEmail());
                rs.updateString("School", user.getSchool());
                
                rs.insertRow();
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error in the repository class: " + e);
       
        }
    }
        public static boolean LogIn(String userID, String password) {
        try {
            String sql = "SELECT * FROM Userdetails where Username = '" + userID + "'";
            ResultSet rs = SQLexecute.executeQuery(getConnection(), sql);

            if (rs.next()) {
                currentUser = new User(rs.getString("NameOU"), rs.getString("Username"), rs.getString("Password"), rs.getString("E-mail"),rs.getString("School"));
                if (!hashing.CompareHashed(currentUser.getHPassword(), password)) {
                    return false;
                }
            }
            rs.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Error in the repository class: " + e);

        }
        return currentUser != null;
    }
}
