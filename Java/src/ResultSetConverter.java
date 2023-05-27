package src;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ResultSetConverter 
{
    public static String convertToJson(ResultSet resultSet)
    {
        List<Object> rows = new ArrayList<>();
        ResultSetMetaData metaData;
        int columnCount;
        try 
        {
            metaData = resultSet.getMetaData();
            columnCount = metaData.getColumnCount();
        } catch (SQLException e) 
        {
            e.printStackTrace();
            return "SQL Exception when converting ResultSet to string";
        }

        try 
        {
            while (resultSet.next()) 
            {
                // Create a new object for each row
                ObjectNode row = new ObjectNode(JsonNodeFactory.instance);

                for (int i = 1; i <= columnCount; i++) 
                {
                    // Get column name and value
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);

                    // Add column name and value to the row object
                    row.put(columnName, columnValue.toString());
                }

                // Add the row object to the list of rows
                rows.add(row);
            }
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        // Create a JSON array from the list of rows
        ObjectMapper objectMapper = new ObjectMapper();
        try 
        {
            return objectMapper.writeValueAsString(rows);
        } 
        catch (JsonProcessingException e) 
        {
            e.printStackTrace();
            return "JsonProcessingException when converting ResultSet to string";
        }
    } 
}
