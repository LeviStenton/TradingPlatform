import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;

public class DBSource {

    private final Connection connection;

    private final String LOGIN_DETAILS = "SELECT Username, Password FROM AccountDetails WHERE Username=?";
    private final String CREATE_ACCOUNT = "INSERT INTO AccountDetails(Username, Password, OrganizationID) VALUES (?, ?, ?)";

    private PreparedStatement loginVerification;
    private PreparedStatement accountCreation;

    public DBSource(){
        connection = DBConnection.getInstance();

        try {
            loginVerification = connection.prepareStatement(LOGIN_DETAILS);
            accountCreation = connection.prepareStatement(CREATE_ACCOUNT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean loginAttempt(String userName, String password){
        ResultSet rs;
        try {
            loginVerification.setString(1, userName);
            rs = loginVerification.executeQuery();
            rs.next();
            return rs.getString("Username").equals(userName) && rs.getString("Password").equals(password);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void CreateAccount(String userName, String password, Integer orgID){
        ResultSet rs;
        try {
            accountCreation.setString(1, userName);
            accountCreation.setString(2, password);
            accountCreation.setString(3, orgID.toString());
            accountCreation.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
