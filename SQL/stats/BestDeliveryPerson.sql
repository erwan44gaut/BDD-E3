DELIMITER //

CREATE PROCEDURE GetBestDeliveryPerson()
BEGIN
    SELECT D.delivery_person_id, V.vehicle_type, COUNT(*) as num_complete_deliveries
    FROM Delivery D
    JOIN Vehicle V ON D.vehicle_id = V.vehicle_id
    WHERE D.delivery_status = 'COMPLETE'
    GROUP BY D.delivery_person_id, V.vehicle_type
    ORDER BY num_complete_deliveries DESC
    LIMIT 1;
END //

DELIMITER ;