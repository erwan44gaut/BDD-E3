DELIMITER //

CREATE PROCEDURE GetBestDeliveryPerson()
BEGIN
    SELECT D.delivery_person_id, V.vehicle_type, COUNT(*) as num_complete_deliveries
    FROM Delivery D
    JOIN Delivery_Person DP ON D.delivery_person_id = DP.delivery_person_id
    JOIN Vehicle V ON DP.vehicle_id = V.vehicle_id
    WHERE D.delivery_status = 'COMPLETE'
    GROUP BY D.delivery_person_id, V.vehicle_type
    ORDER BY num_complete_deliveries DESC
    LIMIT 1;
END //

DELIMITER ;
