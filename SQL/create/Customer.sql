-- ALEX: source C:/Users/foulo/OneDrive/Projets/Projets ESIEE/Projet-bdd-pizzeria/SQL/create/Customer.sql;
CREATE TABLE Customer (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(24),
    balance DECIMAL(10, 2)
);