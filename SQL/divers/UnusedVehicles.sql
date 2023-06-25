DELIMITER //

CREATE PROCEDURE FindUnusedVehicles()
BEGIN
   SELECT V.vehicle_id, V.vehicle_type, V.vehicle_model
   FROM Vehicle V
   LEFT JOIN Delivery_Person DP ON V.vehicle_id = DP.vehicle_id
   WHERE DP.vehicle_id IS NULL;
END //

DELIMITER ;