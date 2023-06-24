DELIMITER //

CREATE PROCEDURE CalculateAverageDeliveryTime()
BEGIN
   DECLARE avg_delivery_time INT;

   -- Calculer le temps moyen de livraison des commandes complétées
   SELECT AVG(TIMESTAMPDIFF(MINUTE, order_datetime, delivery_datetime)) INTO avg_delivery_time
   FROM Pizza_Order
   WHERE order_status = 'COMPLETED'
   AND delivery_datetime IS NOT NULL;

   SELECT avg_delivery_time AS average_delivery_time;
END //

DELIMITER ;