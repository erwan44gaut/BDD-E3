DELIMITER //

CREATE PROCEDURE GetDeliveriesDetails()
BEGIN
    SELECT
		   Delivery.delivery_id,
		   Pizza_Order.order_id,
           Delivery.delivery_person_id,
           Delivery_Person.delivery_person_name,
           Delivery_Person.vehicle_id,
           Vehicle.vehicle_type,
           Delivery.delivery_status,
           Delivery.delivery_datetime
    FROM Delivery
    LEFT JOIN Pizza_Order ON Delivery.order_id = Pizza_Order.order_id
    LEFT JOIN Delivery_Person ON Delivery_Person.delivery_person_id = Delivery.delivery_person_id
    LEFT JOIN Vehicle ON Vehicle.vehicle_id = Delivery_Person.vehicle_id
    ORDER BY Delivery.delivery_datetime;
END; //

DELIMITER ;