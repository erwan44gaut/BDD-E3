DELIMITER //

CREATE PROCEDURE CalculateAverageDeliveryTime()
BEGIN
   DECLARE avg_delivery_time DECIMAL(10, 2);

   -- Calculer le temps moyen de livraison des commandes complétées
   SELECT AVG(TIMESTAMPDIFF(MINUTE, P.order_datetime, D.delivery_datetime)) INTO avg_delivery_time
   FROM Pizza_Order P
   JOIN Delivery D ON P.order_id = D.order_id
   WHERE P.order_status = 'COMPLETED'
   AND D.delivery_datetime IS NOT NULL;

   SELECT avg_delivery_time AS average_delivery_time;
END //

DELIMITER ;