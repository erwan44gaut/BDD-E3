use pizzeria;
CREATE TABLE Delivery(
   delivery_id INT PRIMARY KEY AUTO_INCREMENT,
   order_id INT,
   delivery_person_id INT,
   vehicle_id INT,
   order_status VARCHAR(50),
   delivery_time DATETIME NOT NULL,

    FOREIGN KEY (order_id) REFERENCES Pizza_Order(order_id),
    FOREIGN KEY (delivery_person_id) REFERENCES Delivery_Person(delivery_person_id),
    FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id)
);
