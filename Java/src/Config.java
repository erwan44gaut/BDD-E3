package src;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.nio.file.Paths;

public class Config 
{
    private static Properties properties;

    public static String getProperty(String key) 
    {
        if (properties == null) 
        {
            throw new IllegalStateException("Properties not loaded. Call load() method first.");
        }
        return properties.getProperty(key);
    }

    public static void load() 
    {
        // Avoid loading properties multiple times
        if (properties != null)
        {
            return;
        }

        // Load properties
        String projectRoot = Paths.get("").toAbsolutePath().toString();
        String propertiesPath = Paths.get(projectRoot, "config", "application.properties").toString();
        properties = loadProperties(propertiesPath);
        System.out.println("Loaded properties");
        // Load profile properties
        String profile = properties.getProperty("profile");
        if (!profile.isEmpty())
        {
            String profilePropertiesPath = Paths.get(projectRoot, "config", profile + ".properties").toString();
            Properties profileProperties = loadProperties(profilePropertiesPath);
            properties.putAll(profileProperties);
            System.out.println("Using profile: " + profile);
        }
    }

    private static Properties loadProperties(String filePath) 
    {
        Properties properties = new Properties();
        try (FileInputStream stream = new FileInputStream(filePath)) 
        {
            properties.load(stream);
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return properties;
    }
}
