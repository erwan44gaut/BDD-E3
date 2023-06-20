DELIMITER //

CREATE PROCEDURE UpdateOrderStatus(
    IN new_status VARCHAR(50),
    IN order_id INT
)
BEGIN
    UPDATE Pizza_Order SET order_status = new_status WHERE order_id = order_id;
END //

DELIMITER ;
