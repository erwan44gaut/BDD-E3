
DELIMITER //

CREATE PROCEDURE UpdateOrderStatus(
    IN order_id INT,
    IN new_status ENUM('ACCEPTED', 'IN_PREPARATION', 'IN_DELIVERY', 'COMPLETED', 'CANCELED')
)
BEGIN
    UPDATE Pizza_Order SET Pizza_Order.order_status = new_status WHERE Pizza_order.order_id = order_id;
END //

DELIMITER ;