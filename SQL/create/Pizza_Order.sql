-- Create Order table
CREATE TABLE Pizza_Order (
    order_id INT PRIMARY KEY,
    customer_id INT,
    pizza_id INT,
    delivery_time DATETIME,
    delivery_vehicle VARCHAR(20),
    order_status VARCHAR(20),

    CHECK (delivery_vehicle IN ('Bicycle', 'Car', 'Motorcycle')),
    CHECK (order_status IN ('Pending', 'In Preparation', 'In Delivery', 'Delivered', 'Cancelled')),

    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
    FOREIGN KEY (pizza_id) REFERENCES Pizza(pizza_id)
);