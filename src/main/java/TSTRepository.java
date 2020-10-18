import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * TST repository.
 */
public class TSTRepository {
    
    private static TSTRepository instance;
    
    private Connection connection;
    
    private TSTRepository() {
    }
    
    public static TSTRepository getInstance() {
        if (instance == null) {
            instance = new TSTRepository();
        }
        return instance;
    }
    
    /**
     * Get all TST fields.
     *
     * @return list with fields.
     */
    public List<Integer> getAllTSTFields() {
        List<Integer> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM TST;");
            while (rs.next()) {
                result.add(Integer.valueOf(rs.getString("field")));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * Close connection from database.
     */
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Connect to database.
     */
    public void connectToDb() {
        String url = "jdbc:mysql://localhost/TestJob";
        String username = "root";
        String password = "rootroot123";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            disconnect();
        }
    }
    
    
    /**
     * Add fields.
     *
     * @param fieldsList list with fields.
     */
    public void addTSTFields(List<Integer> fieldsList) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO TST (field) VALUE (?);");
            preparedStatement.execute("TRUNCATE TABLE TST;");
            connection.commit();
            
            for (int i = 0; i < fieldsList.size(); i++) {
                preparedStatement.setInt(1, fieldsList.get(i));
                preparedStatement.addBatch();
                if (i % 100 == 0 || i == fieldsList.size() - 1) {
                    preparedStatement.executeBatch();
                }
            }
            connection.commit();
            preparedStatement.close();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
