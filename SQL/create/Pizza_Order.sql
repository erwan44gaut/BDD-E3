-- Create Order table
-- CREATE TABLE Pizza_Order (
--     order_id INT PRIMARY KEY AUTO_INCREMENT,
--     customer_id INT,
--     pizza_id INT,
--     delivery_time DATETIME,
--     delivery_vehicle VARCHAR(20),
--     order_status VARCHAR(20),

--     CHECK (delivery_vehicle IN ('Bicycle', 'Car', 'Motorcycle')),
--     CHECK (order_status IN ('Pending', 'In Preparation', 'In Delivery', 'Delivered', 'Cancelled')),

--     FOREIGN KEY (customer_id) REFERENCES Customer(id),
--     FOREIGN KEY (pizza_id) REFERENCES Pizza(id)
-- );

CREATE TABLE Pizza_Order (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    pizza_id INT,
    order_status ENUM('Pending', 'In Preparation', 'In Delivery', 'Delivered', 'Cancelled') NOT NULL,
    order_time DATETIME NOT NULL,

    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
    FOREIGN KEY (pizza_id) REFERENCES Pizza(pizza_id),
);