-- ALEX: source C:/Users/foulo/OneDrive/Projets/Projets ESIEE/Projet-bdd-pizzeria/SQL/create/Customer.sql;
CREATE TABLE Customer (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    balance DECIMAL(10,2)
);