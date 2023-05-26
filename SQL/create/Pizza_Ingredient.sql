-- Create Pizza_Ingredient table
CREATE TABLE Pizza_Ingredient (
    pizza_id INT,
    ingredient_id INT,
    quantity INT,
    
    PRIMARY KEY (pizza_id, ingredient_id),
    FOREIGN KEY (pizza_id) REFERENCES Pizza(id),
    FOREIGN KEY (ingredient_id) REFERENCES Ingredient(id)
);