-- Add Margherita Pizza
INSERT INTO Pizza (name, price, size)
VALUES ('Margherita', 10.99, 'Medium');

INSERT INTO Pizza_Ingredient (pizza_id, ingredient_id, quantity)
VALUES (
    (SELECT id FROM Pizza WHERE name = 'Margherita'),
    (SELECT id FROM Ingredient WHERE name = 'Tomato Sauce'),
    150 -- Quantity (150g)
);
INSERT INTO Pizza_Ingredient (pizza_id, ingredient_id, quantity)
VALUES (
    (SELECT id FROM Pizza WHERE name = 'Margherita'),
    (SELECT id FROM Ingredient WHERE name = 'Mozzarella Cheese'),
    200
);
INSERT INTO Pizza_Ingredient (pizza_id, ingredient_id, quantity)
VALUES (
    (SELECT id FROM Pizza WHERE name = 'Margherita'),
    (SELECT id FROM Ingredient WHERE name = 'Basil Leaves'),
    10
);

-- Add Regina Pizza
INSERT INTO Pizza (name, price, size)
VALUES ('Regina', 12.99, 'Medium');

INSERT INTO Pizza_Ingredient (pizza_id, ingredient_id, quantity)
VALUES (
    (SELECT id FROM Pizza WHERE name = 'Regina'),
    (SELECT id FROM Ingredient WHERE name = 'Tomato Sauce'),
    150
);
INSERT INTO Pizza_Ingredient (pizza_id, ingredient_id, quantity)
VALUES (
    (SELECT id FROM Pizza WHERE name = 'Regina'),
    (SELECT id FROM Ingredient WHERE name = 'Mozzarella Cheese'),
    200
);
INSERT INTO Pizza_Ingredient (pizza_id, ingredient_id, quantity)
VALUES (
    (SELECT id FROM Pizza WHERE name = 'Regina'),
    (SELECT id FROM Ingredient WHERE name = 'Ham'),
    100
);
INSERT INTO Pizza_Ingredient (pizza_id, ingredient_id, quantity)
VALUES (
    (SELECT id FROM Pizza WHERE name = 'Regina'),
    (SELECT id FROM Ingredient WHERE name = 'Mushrooms'),
    50
);

-- Add Calzone Pizza
INSERT INTO Pizza (name, price, size)
VALUES ('Calzone', 11.99, 'Medium');

INSERT INTO Pizza_Ingredient (pizza_id, ingredient_id, quantity)
VALUES (
    (SELECT id FROM Pizza WHERE name = 'Calzone'),
    (SELECT id FROM Ingredient WHERE name = 'Tomato Sauce'),
    150
);
INSERT INTO Pizza_Ingredient (pizza_id, ingredient_id, quantity)
VALUES (
    (SELECT id FROM Pizza WHERE name = 'Calzone'),
    (SELECT id FROM Ingredient WHERE name = 'Mozzarella Cheese'),
    200
);
INSERT INTO Pizza_Ingredient (pizza_id, ingredient_id, quantity)
VALUES (
    (SELECT id FROM Pizza WHERE name = 'Calzone'),
    (SELECT id FROM Ingredient WHERE name = 'Pepperoni'),
    80
);
INSERT INTO Pizza_Ingredient (pizza_id, ingredient_id, quantity)
VALUES (
    (SELECT id FROM Pizza WHERE name = 'Calzone'),
    (SELECT id FROM Ingredient WHERE name = 'Bell Peppers'),
    50
);
