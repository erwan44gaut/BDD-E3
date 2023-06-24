DELIMITER //

CREATE PROCEDURE GetWorstDeliveryPerson()
BEGIN
    SELECT D.delivery_person_id, V.vehicle_type, COUNT(*) as num_late_deliveries
    FROM Delivery D
    JOIN Vehicle V ON D.vehicle_id = V.vehicle_id
    WHERE D.delivery_status = 'LATE'
    GROUP BY D.delivery_person_id, V.vehicle_type
    ORDER BY num_late_deliveries DESC
    LIMIT 1;
END //

DELIMITER ;