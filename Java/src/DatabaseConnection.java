package src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection 
{
    private static Connection connection;

    public static void connect()
    {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } 
        catch (ClassNotFoundException e) 
        {
            e.printStackTrace();
        }
        try 
        {
            connection = DriverManager.getConnection
            (
                Config.getProperty("database.url"),
                Config.getProperty("database.username"),
                Config.getProperty("database.password")
            );
            System.out.println("Database connection opened");
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    public static void disconnect() throws SQLException
    {
        connection.close();
        System.out.println("Database connection closed");
    }

    public static boolean execute(String action)
    {
        Statement statement;
        try 
        {
            statement = connection.createStatement();
            statement.execute(action);
            return true;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return false;
        }
    }

    public static ResultSet query(String query)
    {
        Statement statement;
        try 
        {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            return result;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return null;
        }
    }
}
