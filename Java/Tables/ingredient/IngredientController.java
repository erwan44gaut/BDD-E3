package Tables.ingredient;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.net.httpserver.HttpExchange;
import src.ExchangeUtil;
import src.ResultSetConverter;

public class IngredientController {
    
    public static void getIngredients(HttpExchange exchange)
    {
        ResultSet ingredients = IngredientService.getIngredients();
        String response = ResultSetConverter.convertToJson(ingredients);
        ExchangeUtil.sendResponse(exchange, 200, response);
    }
}
