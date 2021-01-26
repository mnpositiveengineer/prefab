USE prefab_sales;

-- 1. Calculate volume of consoles in bid element

DROP FUNCTION IF EXISTS calculate_volume_of_consoles;

DELIMITER $$
CREATE FUNCTION calculate_volume_of_consoles(bid_element_id INT)
RETURNS DECIMAL (4,2)
READS SQL DATA
BEGIN
    DECLARE volume_of_consoles DECIMAL(4,2);
    DECLARE amount INT;
    DECLARE width DECIMAL(4,2);
    DECLARE length DECIMAL(4,2);
    DECLARE height DECIMAL(4,2);
    DECLARE number_of_consoles INT;
    DECLARE current_console INT;
    
    SELECT JSON_LENGTH (other_properties, "$.consoles") 
    INTO number_of_consoles
    FROM bidelements b 
    WHERE b.id = bid_element_id;
    
    SET volume_of_consoles = 0;
    SET current_console = 0;
    
    WHILE current_console < number_of_consoles DO
		SELECT JSON_EXTRACT(other_properties, CONCAT('$.consoles[', current_console, '].amount'))
        INTO amount
        FROM bidelements b
        WHERE b.id = bid_element_id;
        
		SELECT JSON_EXTRACT(other_properties, CONCAT('$.consoles[', current_console, '].dimensions[0]'))
        INTO width
        FROM bidelements b
        WHERE b.id = bid_element_id;
        
		SELECT JSON_EXTRACT(other_properties, CONCAT('$.consoles[', current_console, '].dimensions[1]'))
        INTO length
        FROM bidelements b
        WHERE b.id = bid_element_id;
        
		SELECT JSON_EXTRACT(other_properties, CONCAT('$.consoles[', current_console, '].dimensions[2]'))
        INTO height
        FROM bidelements b
        WHERE b.id = bid_element_id;
        
        SET volume_of_consoles = volume_of_consoles + amount * width * length * height;
        
        SET current_console = current_console + 1;
	END WHILE;
    RETURN volume_of_consoles;
END$$
DELIMITER ;

-- 2. Calculate volume of cutouts in bid element

DROP FUNCTION IF EXISTS calculate_volume_of_cutouts;

DELIMITER $$
CREATE FUNCTION calculate_volume_of_cutouts(bid_element_id INT)
RETURNS DECIMAL (4,2)
READS SQL DATA
BEGIN
    DECLARE volume_of_cutouts DECIMAL(4,2);
    DECLARE amount INT;
    DECLARE width DECIMAL(4,2);
    DECLARE length DECIMAL(4,2);
    DECLARE height DECIMAL(4,2);
    DECLARE number_of_cutouts INT;
    DECLARE current_cutouts INT;
    
    SELECT JSON_LENGTH (other_properties, "$.cutouts") 
    INTO number_of_cutouts
    FROM bidelements b 
    WHERE b.id = bid_element_id;
    
    SET volume_of_cutouts = 0;
    SET current_cutouts = 0;
    
    WHILE current_cutouts < number_of_cutouts DO
		SELECT JSON_EXTRACT(other_properties, CONCAT('$.cutouts[', current_cutouts, '].amount'))
        INTO amount
        FROM bidelements b
        WHERE b.id = bid_element_id;
        
		SELECT JSON_EXTRACT(other_properties, CONCAT('$.cutouts[', current_cutouts, '].dimensions[0]'))
        INTO width
        FROM bidelements b
        WHERE b.id = bid_element_id;
        
		SELECT JSON_EXTRACT(other_properties, CONCAT('$.cutouts[', current_cutouts, '].dimensions[1]'))
        INTO length
        FROM bidelements b
        WHERE b.id = bid_element_id;
        
		SELECT JSON_EXTRACT(other_properties, CONCAT('$.cutouts[', current_cutouts, '].dimensions[2]'))
        INTO height
        FROM bidelements b
        WHERE b.id = bid_element_id;
        
        SET volume_of_cutouts = volume_of_cutouts + amount * width * length * height;
        
        SET current_cutouts = current_cutouts + 1;
	END WHILE;
    RETURN volume_of_cutouts;
END$$
DELIMITER ;

-- 3. Creating function that calculates volume of bid element

DROP FUNCTION IF EXISTS calculate_volume;

DELIMITER $$
CREATE FUNCTION calculate_volume (bid_element_id INT)
RETURNS DECIMAL (4,2)
READS SQL DATA
BEGIN
	IF NOT EXISTS (SELECT b.name 
					FROM bidelements b 
                    WHERE b.id = bid_element_id)
	THEN 
		SIGNAL SQLSTATE '22003'
		SET MESSAGE_TEXT = "No such element in database.";
	ELSE
		BEGIN
			DECLARE length DECIMAL(4,2);
			DECLARE height DECIMAL(4,2);
			DECLARE width DECIMAL(4,2);
			DECLARE volume DECIMAL(4,2) DEFAULT 0;
    
			SELECT b.length, b.width, b.height
			INTO length, width, height
			FROM bidelements b
			WHERE b.id = bid_element_id;
    
			SET volume = length * height * width 
            + IFNULL(calculate_volume_of_consoles(bid_element_id),0)
			- IFNULL(calculate_volume_of_cutouts(bid_element_id),0);
			
            RETURN volume;
		END;
    END IF;
END$$
DELIMITER ;

-- 4. Creating function that calculates total_volume of bid element

DROP FUNCTION IF EXISTS calculate_total_volume;

DELIMITER $$
CREATE FUNCTION calculate_total_volume (bid_element_id INT)
RETURNS DECIMAL (9,2)
READS SQL DATA
BEGIN
	IF NOT EXISTS (SELECT b.name 
					FROM bidelements b 
                    WHERE b.id = bid_element_id)
	THEN 
		SIGNAL SQLSTATE '22003'
		SET MESSAGE_TEXT = "No such element in database.";
	ELSE
		BEGIN
			DECLARE volume DECIMAL(9,2);
			DECLARE amount INT;
			DECLARE total_volume DECIMAL(9,2) DEFAULT 0;
    
			SELECT b.amount
			INTO amount
			FROM bidelements b
			WHERE b.id = bid_element_id;
            
            SET volume = IFNULL(calculate_volume (bid_element_id),0);

			SET total_volume = volume * amount;
            
			RETURN total_volume;
		END;
	END IF;
END$$
DELIMITER ;

-- 5. Creating function that calculates area of element (width * length)

DROP FUNCTION IF EXISTS calculate_area;

DELIMITER $$
CREATE FUNCTION calculate_area(bid_element_id INT)
RETURNS DECIMAL (9,2)
READS SQL DATA
BEGIN
	IF NOT EXISTS (SELECT b.name 
					FROM bidelements b 
                    WHERE b.id = bid_element_id)
	THEN 
		SIGNAL SQLSTATE '22003'
		SET MESSAGE_TEXT = "No such element in database.";
	ELSE
		BEGIN
			DECLARE length DECIMAL(9,2);
			DECLARE width DECIMAL(9,2);
			DECLARE area DECIMAL(9,2) DEFAULT 0;
    
			SELECT b.length, b.width
			INTO length, width
			FROM bidelements b
			WHERE b.id = bid_element_id;
    
			SET area = length * width;
            
			RETURN area;
		END;
	END IF;
END$$
DELIMITER ;

-- 6. Creating function that calculates total_area of bid element

DROP FUNCTION IF EXISTS calculate_total_area;

DELIMITER $$
CREATE FUNCTION calculate_total_area (bid_element_id INT)
RETURNS DECIMAL (9,2)
READS SQL DATA
BEGIN
	IF NOT EXISTS (SELECT b.name 
					FROM bidelements b 
                    WHERE b.id = bid_element_id)
	THEN 
		SIGNAL SQLSTATE '22003'
		SET MESSAGE_TEXT = "No such element in database.";
	ELSE
		BEGIN
			DECLARE area DECIMAL(9,2);
			DECLARE amount INT;
			DECLARE total_area DECIMAL(9,2) DEFAULT 0;
    
			SELECT b.amount
			INTO amount
			FROM bidelements b
			WHERE b.id = bid_element_id;
            
            SET area = IFNULL(calculate_area(bid_element_id),0);
    
			SET total_area = area * amount;
			
            RETURN total_area;
		END;
    END IF;
END$$
DELIMITER ;

-- 7. Creating function that calculates weight of element (volume * 2,5)

DROP FUNCTION IF EXISTS calculate_weight;

DELIMITER $$
CREATE FUNCTION calculate_weight(bid_element_id INT)
RETURNS DECIMAL (9,2)
READS SQL DATA
BEGIN
	IF NOT EXISTS (SELECT b.name 
					FROM bidelements b 
                    WHERE b.id = bid_element_id)
	THEN 
		SIGNAL SQLSTATE '22003'
		SET MESSAGE_TEXT = "No such element in database.";
	ELSE
		BEGIN
			DECLARE volume DECIMAL(9,2);
			DECLARE weight DECIMAL(9,2) DEFAULT 0;
            
            SET volume = IFNULL(calculate_volume(bid_element_id),0);
    
			SET weight = volume * 2.5;
	
			RETURN weight;
		END;
    END IF;
END$$
DELIMITER ;

-- 8. Creating function that calculates total_weight of bid element

DROP FUNCTION IF EXISTS calculate_total_weight;

DELIMITER $$
CREATE FUNCTION calculate_total_weight (bid_element_id INT)
RETURNS DECIMAL (9,2)
READS SQL DATA
BEGIN
	IF NOT EXISTS (SELECT b.name 
					FROM bidelements b 
                    WHERE b.id = bid_element_id)
	THEN 
		SIGNAL SQLSTATE '22003'
		SET MESSAGE_TEXT = "No such element in database.";
	ELSE
		BEGIN
			DECLARE weight DECIMAL(9,2);
			DECLARE amount INT;
			DECLARE total_weight DECIMAL(9,2) DEFAULT 0;
    
			SELECT b.amount
			INTO amount
			FROM bidelements b
			WHERE b.id = bid_element_id;
            
            SET weight = IFNULL(calculate_weight(bid_element_id),0);
            
			SET total_weight = weight * amount;
            
			RETURN total_weight;
		END;
    END IF;
END$$
DELIMITER ;

-- 9. Creating Functions that calculates prices of bid elements

-- to calculate prices in price_of_bidelements table use following formulas:
-- concrete_cost = financialdetails.concrete_cost * volume
-- steel_cost = financialdetails.steel_cost * (steel_saturation * volume)
-- tension_steel_cost = financialdetails.tension_steel_cost * (tension_steel_saturation * volume)
-- framework_cost = financialdetails.framework_cost * (area + width * height * 2 + length * height * 2)
-- man_hour_cost = financialdetails.man_hour_cost * volume
-- energy_water_cost = financialdetails.energy_water_cost * volume
-- faculty_cost = financialdetails.faculty_cost * volume
-- accessory_cost = accessories_bidelements.amount * accesories.unit_price
-- production_cost = concrete_cost + steel_cost + tension_steel_cost + framework_cost + man_hour_cost + energy_water_cost + faculty_cost + accessory_cost
-- total_production_cost = production_cost * amount
-- transport_cost = (total_weight of all elements within this type of element / 21 ton) * financialdetails.transport_cost
-- total_transport_cost = transport_cost * amount
-- assembly_cost = financialdetails.assembly_cost
-- total_assembly_cost = total_assembly_cost * amount
-- element_cost = production_cost + transport_cost + ssembly_cost
-- total_element_cost = element_cost * amount
-- element_price = element_cost * (1 + markup)
-- total_element_price = element_price * amount

DROP FUNCTION IF EXISTS calculate_element_concrete_cost;

DELIMITER $$
CREATE FUNCTION calculate_element_concrete_cost (bid_element_id INT)
RETURNS DECIMAL (9,2)
READS SQL DATA
BEGIN
	DECLARE fd_concrete_cost DECIMAL (9,2);
    DECLARE b_volume DECIMAL (9,2);
    DECLARE pb_concrete_cost DECIMAL (9,2);
    
    SELECT fd.concrete_cost
    INTO fd_concrete_cost
    FROM bidelements b
    JOIN financialdetails fd
    USING (project_id)
    WHERE b.id = bid_element_id;
    
    SET b_volume = IFNULL(calculate_volume(bid_element_id),0);
    
    SET pb_concrete_cost = fd_concrete_cost * b_volume;
    
    RETURN pb_concrete_cost;
END$$
DELIMITER ;

DROP FUNCTION IF EXISTS calculate_element_steel_cost;

DELIMITER $$
CREATE FUNCTION calculate_element_steel_cost (bid_element_id INT)
RETURNS DECIMAL (9,2)
READS SQL DATA
BEGIN
    DECLARE fd_steel_cost DECIMAL (9,2);
    DECLARE b_steel_saturation INT;
	DECLARE b_volume DECIMAL (9,2);
    DECLARE pb_steel_cost DECIMAL (9,2);
    
    SELECT fd.steel_cost, b.steel_saturation
    INTO fd_steel_cost, b_steel_saturation
    FROM bidelements b
    JOIN financialdetails fd
    USING (project_id)
    WHERE b.id = bid_element_id;
    
    SET b_volume = IFNULL(calculate_volume(bid_element_id),0);
    
    SET pb_steel_cost = fd_steel_cost * b_steel_saturation * b_volume;
    
    RETURN pb_steel_cost;
END$$
DELIMITER ;

DROP FUNCTION IF EXISTS calculate_element_tension_steel_cost;

DELIMITER $$
CREATE FUNCTION calculate_element_tension_steel_cost (bid_element_id INT)
RETURNS DECIMAL (9,2)
READS SQL DATA
BEGIN
    DECLARE fd_tension_steel_cost DECIMAL (9,2);
    DECLARE b_tension_steel_saturation INT;
	DECLARE b_volume DECIMAL (9,2);
    DECLARE pb_tension_steel_cost DECIMAL (9,2);
    
    SELECT fd.tension_steel_cost, b.tension_steel_saturation
    INTO fd_tension_steel_cost, b_tension_steel_saturation
    FROM bidelements b
    JOIN financialdetails fd
    USING (project_id)
    WHERE b.id = bid_element_id;
    
    SET b_volume = IFNULL(calculate_volume(bid_element_id),0);
    
    SET pb_tension_steel_cost = fd_tension_steel_cost * b_tension_steel_saturation * b_volume;
    
    RETURN pb_tension_steel_cost;
END$$
DELIMITER ;

DROP FUNCTION IF EXISTS calculate_element_framework_cost;

DELIMITER $$
CREATE FUNCTION calculate_element_framework_cost (bid_element_id INT)
RETURNS DECIMAL (9,2)
READS SQL DATA
BEGIN
    DECLARE fd_framework_cost DECIMAL (9,2);
	DECLARE b_width DECIMAL (9,2);
	DECLARE b_height DECIMAL (9,2);
	DECLARE b_length DECIMAL (9,2);
    DECLARE pb_framework_cost DECIMAL (9,2);
    
    SELECT fd.framework_cost, b.width, b.height, b.length
    INTO fd_framework_cost, b_width, b_height, b_length
    FROM bidelements b
    JOIN financialdetails fd
    USING (project_id)
    WHERE b.id = bid_element_id;
    
    SET pb_framework_cost = fd_framework_cost * (b_length * b_width + 2 * b_height * b_width + 2 * b_height * b_length) ;
    
    RETURN pb_framework_cost;
END$$
DELIMITER ;

DROP FUNCTION IF EXISTS calculate_man_hour_cost;

DELIMITER $$
CREATE FUNCTION calculate_man_hour_cost (bid_element_id INT)
RETURNS DECIMAL (9,2)
READS SQL DATA
BEGIN
    DECLARE fd_man_hour_cost DECIMAL (9,2);
	DECLARE b_volume DECIMAL (9,2);
    DECLARE pb_man_hour_cost DECIMAL (9,2);
    
    SELECT fd.man_hour_cost
    INTO fd_man_hour_cost
    FROM bidelements b
    JOIN financialdetails fd
    USING (project_id)
    WHERE b.id = bid_element_id;
    
    SET b_volume = IFNULL(calculate_volume(bid_element_id),0);
    
    SET pb_man_hour_cost = fd_man_hour_cost * b_volume ;
    
    RETURN pb_man_hour_cost;
END$$
DELIMITER ;

DROP FUNCTION IF EXISTS calculate_energy_water_cost;

DELIMITER $$
CREATE FUNCTION calculate_energy_water_cost (bid_element_id INT)
RETURNS DECIMAL (9,2)
READS SQL DATA
BEGIN
    DECLARE fd_energy_water_cost DECIMAL (9,2);
	DECLARE b_volume DECIMAL (9,2);
    DECLARE pb_energy_water_cost DECIMAL (9,2);
    
    SELECT fd.energy_water_cost
    INTO fd_energy_water_cost
    FROM bidelements b
    JOIN financialdetails fd
    USING (project_id)
    WHERE b.id = bid_element_id;
    
    SET b_volume = IFNULL(calculate_volume(bid_element_id),0);
    
    SET pb_energy_water_cost = fd_energy_water_cost * b_volume ;
    
    RETURN pb_energy_water_cost;
END$$
DELIMITER ;

DROP FUNCTION IF EXISTS calculate_faculty_cost;

DELIMITER $$
CREATE FUNCTION calculate_faculty_cost (bid_element_id INT)
RETURNS DECIMAL (9,2)
READS SQL DATA
BEGIN
    DECLARE fd_faculty_cost DECIMAL (9,2);
	DECLARE b_volume DECIMAL (9,2);
    DECLARE pb_faculty_cost DECIMAL (9,2);
    
    SELECT fd.faculty_cost
    INTO fd_faculty_cost
    FROM bidelements b
    JOIN financialdetails fd
    USING (project_id)
    WHERE b.id = bid_element_id;
    
    SET b_volume = IFNULL(calculate_volume(bid_element_id),0);
    
    SET pb_faculty_cost = fd_faculty_cost * b_volume ;
    
    RETURN pb_faculty_cost;
END$$
DELIMITER ;

DROP FUNCTION IF EXISTS calculate_accessory_cost;

DELIMITER $$
CREATE FUNCTION calculate_accessory_cost(bid_element_id INT)
RETURNS DECIMAL (9,2)
READS SQL DATA
BEGIN
	DECLARE number_of_accessories_in_bid_element INT;
    DECLARE current_accessory INT;
    DECLARE amount DECIMAL(9,2);
    DECLARE unit_price DECIMAL(9,2);
    DECLARE pb_accessory_cost DECIMAL(9,2);
    
	SELECT count(*)
    INTO number_of_accessories_in_bid_element
    FROM accessoriesbidelements ab
    WHERE ab.bid_element_id = bid_element_id;
    
    SET current_accessory = 0;
    SET pb_accessory_cost = 0;
    
    WHILE current_accessory < number_of_accessories_in_bid_element DO
    
    SELECT ab.amount, a.unit_price
    INTO amount, unit_price
    FROM accessoriesbidelements ab 
    JOIN accessories a ON ab.accessory_id = a.id 
    WHERE ab.bid_element_id = bid_element_id
    LIMIT current_accessory,1;
    
    SET pb_accessory_cost = pb_accessory_cost  + amount * unit_price;
    
    SET current_accessory = current_accessory + 1;
    
    END WHILE;
    
	RETURN pb_accessory_cost;
END$$
DELIMITER ;

DROP FUNCTION IF EXISTS calculate_production_cost;

DELIMITER $$
CREATE FUNCTION calculate_production_cost (bid_element_id INT)
RETURNS DECIMAL (9,2)
READS SQL DATA
BEGIN
	DECLARE production_cost DECIMAL(9,2);
    SET production_cost = 
		IFNULL(calculate_element_concrete_cost (bid_element_id),0) +
        IFNULL(calculate_element_steel_cost (bid_element_id),0) +
        IFNULL(calculate_element_tension_steel_cost (bid_element_id),0) +
        IFNULL(calculate_element_framework_cost (bid_element_id),0) +
        IFNULL(calculate_man_hour_cost (bid_element_id),0) +
        IFNULL(calculate_energy_water_cost (bid_element_id),0) +
        IFNULL(calculate_faculty_cost (bid_element_id),0) +
        IFNULL(calculate_accessory_cost (bid_element_id),0);
    
    RETURN production_cost;
END$$
DELIMITER ;

DROP FUNCTION IF EXISTS calculate_total_production_cost;

DELIMITER $$
CREATE FUNCTION calculate_total_production_cost (bid_element_id INT)
RETURNS DECIMAL (9,2)
READS SQL DATA
BEGIN
	DECLARE total_production_cost DECIMAL (9,2);
    DECLARE amount INT;
    
    SELECT b.amount
    INTO amount
    FROM bidelements b
    WHERE b.id = bid_element_id;
    
    SET total_production_cost = IFNULL(calculate_production_cost(bid_element_id),0) * amount;
    
    RETURN total_production_cost;
END$$
DELIMITER ;

DROP FUNCTION IF EXISTS calculate_transport_cost;

DELIMITER $$
CREATE FUNCTION calculate_transport_cost (bid_element_id INT)
RETURNS DECIMAL(9,2)
READS SQL DATA
BEGIN
	DECLARE type_of_element INT;
    DECLARE project_id INT;
    DECLARE total_weight DECIMAL(9,2);
    DECLARE total_amount INT;
	DECLARE fd_transport_cost INT;
    DECLARE pb_transport_cost DECIMAL(9,2);
    
    SELECT b.type_of_element_id, b.project_id
    INTO type_of_element, project_id
    FROM bidelements b
    WHERE b.id = bid_element_id;
    
	SELECT SUM(b.total_weight), SUM(amount)
    INTO total_weight, total_amount
	FROM bid_elements_with_all_parameters b
	WHERE b.project_id = project_id
	AND b.type_of_element_id = type_of_element
	GROUP BY b.type_of_element_id;
    
    SELECT fd.transport_cost
    INTO fd_transport_cost
    FROM financialdetails fd
    WHERE fd.project_id = project_id;
    
    SET pb_transport_cost = (CEILING(total_weight/21) * fd_transport_cost)/total_amount;
    
    RETURN pb_transport_cost;
END$$
DELIMITER ;

DROP FUNCTION IF EXISTS calculate_total_transport_cost;

DELIMITER $$
CREATE FUNCTION calculate_total_transport_cost (bid_element_id INT)
RETURNS DECIMAL (9,2)
READS SQL DATA
BEGIN
	DECLARE total_transport_cost DECIMAL (9,2);
    DECLARE amount INT;
    
    SELECT b.amount
    INTO amount
    FROM bidelements b
    WHERE b.id = bid_element_id;
    
    SET total_transport_cost = IFNULL(calculate_transport_cost(bid_element_id),0) * amount;
    
    RETURN total_transport_cost;
END$$
DELIMITER ;

DROP FUNCTION IF EXISTS calculate_assembly_cost;

DELIMITER $$
CREATE FUNCTION calculate_assembly_cost (bid_element_id INT)
RETURNS INT
READS SQL DATA
BEGIN
	DECLARE assembly_cost INT;
    DECLARE project_id INT;
    
	SELECT b.project_id
    INTO project_id
    FROM bidelements b
    WHERE b.id = bid_element_id;
    
    SELECT fd.assembly_cost
    INTO assembly_cost
    FROM financialdetails fd
    WHERE fd.project_id = project_id;
    
    RETURN assembly_cost;
END$$
DELIMITER ;

DROP FUNCTION IF EXISTS calculate_total_assembly_cost;

DELIMITER $$
CREATE FUNCTION calculate_total_assembly_cost (bid_element_id INT)
RETURNS INT
READS SQL DATA
BEGIN
	DECLARE total_assembly_cost INT;
    DECLARE amount INT;
    
    SELECT b.amount
    INTO amount
    FROM bidelements b
    WHERE b.id = bid_element_id;
    
    SET total_assembly_cost = IFNULL(calculate_assembly_cost(bid_element_id),0) * amount;
    
    RETURN total_assembly_cost;
END$$
DELIMITER ;

DROP FUNCTION IF EXISTS calculate_element_cost;

DELIMITER $$
CREATE FUNCTION calculate_element_cost (bid_element_id INT)
RETURNS DECIMAL (9,2)
READS SQL DATA
BEGIN
	DECLARE element_cost DECIMAL (9,2);
    
    SELECT 
		IFNULL(calculate_production_cost (bid_element_id),0) +
        IFNULL(calculate_transport_cost (bid_element_id),0) +
        IFNULL(calculate_assembly_cost (bid_element_id),0)
    INTO element_cost;
    
    RETURN element_cost;
END$$
DELIMITER ;

DROP FUNCTION IF EXISTS calculate_total_element_cost;

DELIMITER $$
CREATE FUNCTION calculate_total_element_cost (bid_element_id INT)
RETURNS DECIMAL (9,2)
READS SQL DATA
BEGIN
	DECLARE total_element_cost DECIMAL (9,2);
    DECLARE amount INT;
    
    SELECT b.amount
    INTO amount
    FROM bidelements b
    WHERE b.id = bid_element_id;
    
    SET total_element_cost = IFNULL(calculate_element_cost(bid_element_id),0) * amount;
    
    RETURN total_element_cost;
END$$
DELIMITER ;

DROP FUNCTION IF EXISTS calculate_element_price;

DELIMITER $$
CREATE FUNCTION calculate_element_price(bid_element_id INT)
RETURNS DECIMAL (9,2)
READS SQL DATA
BEGIN
	DECLARE markup DECIMAL (2,2);
	DECLARE project_id INT;
    DECLARE element_price DECIMAL (9,2);
    
	SELECT b.project_id
    INTO project_id
    FROM bidelements b
    WHERE b.id = bid_element_id;
    
    SELECT fd.markup
    INTO markup
    FROM financialdetails fd
    WHERE fd.project_id = project_id;
    
    SET element_price = IFNULL(calculate_element_cost(bid_element_id),0) * (1 + IFNULL(markup,0) );
    
    RETURN element_price;
END$$
DELIMITER ;

DROP FUNCTION IF EXISTS calculate_total_element_price;

DELIMITER $$
CREATE FUNCTION calculate_total_element_price (bid_element_id INT)
RETURNS DECIMAL (9,2)
READS SQL DATA
BEGIN
	DECLARE total_element_price DECIMAL (9,2);
    DECLARE amount INT;
    
    SELECT b.amount
    INTO amount
    FROM bidelements b
    WHERE b.id = bid_element_id;
    
    SET total_element_price = IFNULL(calculate_element_price(bid_element_id),0) * amount;
    
    RETURN total_element_price;
END$$
DELIMITER ;