DELIMITER //

CREATE PROCEDURE GenerateSalesDistributionByPizzaSize()
BEGIN
   -- Table temporaire pour stocker les résultats intermédiaires
   CREATE TEMPORARY TABLE temp_sales_distribution (
      pizza_size ENUM('SMALL', 'MEDIUM', 'LARGE'),
      sales_count INT
   );

   -- Insérer les données de répartition des ventes par taille de pizza dans la table temporaire
   INSERT INTO temp_sales_distribution (pizza_size, sales_count)
   SELECT pizza_size, COUNT(*) AS count
   FROM Pizza_Order
   WHERE order_status = 'COMPLETED'
   GROUP BY pizza_size;

   -- Calculer le total des ventes
   DECLARE total_sales INT;
   SELECT SUM(sales_count) INTO total_sales FROM temp_sales_distribution;

   -- Sélectionner la répartition des ventes par taille de pizza avec la part dans le total des ventes
   SELECT pizza_size, sales_count, (sales_count / total_sales) * 100 AS sales_percentage
   FROM temp_sales_distribution;

   -- Supprimer la table temporaire
   DROP TABLE IF EXISTS temp_sales_distribution;
END //

DELIMITER ;