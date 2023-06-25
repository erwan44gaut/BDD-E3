DELIMITER //

CREATE PROCEDURE GetMostOrderedPizza()
BEGIN
   -- Sélectionner la pizza la plus commandée avec son ID, nom et nombre de commandes
   SELECT PO.pizza_id, P.pizza_name, COUNT(*) AS order_count
   FROM Pizza_Order PO
   JOIN Pizza P ON PO.pizza_id = P.pizza_id
   WHERE PO.order_status = 'COMPLETED'
   GROUP BY PO.pizza_id, P.pizza_name
   ORDER BY order_count DESC
   LIMIT 1;
END //

DELIMITER ;