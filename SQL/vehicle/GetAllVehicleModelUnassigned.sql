DELIMITER //

CREATE PROCEDURE GetAllVehicleModelUnassigned()
BEGIN
    SELECT *
    FROM Vehicle
    WHERE vehicle_id NOT IN (SELECT vehicle_id FROM Delivery_Person WHERE vehicle_id IS NOT NULL);
END //

DELIMITER ;