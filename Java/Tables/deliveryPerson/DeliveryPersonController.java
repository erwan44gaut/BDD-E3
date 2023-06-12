package tables.deliveryPerson;

import java.sql.ResultSet;
import com.sun.net.httpserver.HttpExchange;
import src.ExchangeUtil;
import src.ResultSetConverter;

public class DeliveryPersonController {

    public static void getDeliveryPersons(HttpExchange exchange)
    {
        ResultSet deliveryPersons = DeliveryPersonService.getDeliveryPersons();
        String response = ResultSetConverter.convertToJson(deliveryPersons);
        ExchangeUtil.sendResponse(exchange, 200, response);
    }
}
