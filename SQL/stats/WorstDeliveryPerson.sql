DELIMITER //

CREATE PROCEDURE GetWorstDeliveryPerson()
BEGIN
   SELECT DP.delivery_person_id, DP.delivery_person_name, V.vehicle_type, V.vehicle_model, COUNT(*) AS num_late_deliveries
   FROM Delivery D
   JOIN Delivery_Person DP ON D.delivery_person_id = DP.delivery_person_id
   JOIN Vehicle V ON DP.vehicle_id = V.vehicle_id
   WHERE D.delivery_status = 'LATE'
   GROUP BY DP.delivery_person_id, DP.delivery_person_name, V.vehicle_type, V.vehicle_model
   ORDER BY num_late_deliveries DESC
   LIMIT 1;
END //

DELIMITER ;