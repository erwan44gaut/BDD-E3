package tables.ingredient;

import java.sql.ResultSet;
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
