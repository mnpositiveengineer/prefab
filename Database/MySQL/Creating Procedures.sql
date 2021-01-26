-- creating procedures

USE prefab_sales;
SET GLOBAL log_bin_trust_function_creators = 1;

-- 1. Creating procedure that adds console to bid element

/*
EXAMPLE OF JSON FILE WITH 3 CONSOLES:
{
	"consoles": 
		[
			{"amount": 3, "dimensions": [0.30, 0.30, 0.30]},
			{"amount": 2, "dimensions": [0.20, 0.20, 0.20]}, 
			{"amount": 1, "dimensions": [0.10, 0.10, 0.10]}
        ]
}
*/

DROP PROCEDURE IF EXISTS add_console_to_bid_element;

DELIMITER $$
CREATE PROCEDURE add_console_to_bid_element 
(
	bid_element_id INT,
    amount INT, 
    height DECIMAL (3,2), 
    width DECIMAL (3,2), 
    length DECIMAL (3,2)
)
BEGIN
	IF (SELECT b.other_properties 
		FROM bidelements b 
        WHERE b.id = bid_element_id) 
        IS NULL
    THEN
		UPDATE bidelements b
		SET other_properties = JSON_OBJECT
		(
			'consoles', JSON_ARRAY
			(
				JSON_OBJECT
				(
					'amount', amount,
					'dimensions', JSON_ARRAY(height, width, length)
				)
			)
		)
		WHERE b.id = bid_element_id;
    ELSE
		IF (SELECT JSON_EXTRACT(other_properties, '$.consoles')
			FROM bidelements b
			WHERE b.id = bid_element_id) IS NOT NULL
        THEN
			UPDATE bidelements b
			SET other_properties = JSON_ARRAY_INSERT
			(
				other_properties,
				'$.consoles[0]', JSON_OBJECT
					(
						'amount', amount,                     
						'dimensions', JSON_ARRAY(height, width, length)
					)
			)
			WHERE b.id = bid_element_id;
		ELSE
			UPDATE bidelements b
			SET other_properties = JSON_SET
			(
				other_properties,
				'$.consoles', JSON_ARRAY
					(
						JSON_OBJECT
						(
							'amount', amount,
							'dimensions', JSON_ARRAY(height, width, length)
						)
					)
			)
			WHERE b.id = bid_element_id;
		END IF;
	END IF;
END$$
DELIMITER ;

-- 2. Creating procedure that removes consoles from bid element

DROP PROCEDURE IF EXISTS remove_console_from_bid_element;

DELIMITER $$
CREATE PROCEDURE remove_console_from_bid_element 
(
	bid_element_id INT,
    number INT
)
BEGIN
	UPDATE bidelements b
	SET other_properties = JSON_REMOVE
	(
		other_properties,
		CONCAT('$.consoles[', number, ']')
	)
	WHERE b.id = bid_element_id;
END$$
DELIMITER ;

-- 3. Creating procedure that adds cutout to bid element


/*
EXAMPLE OF JSON FILE WITH 3 COUTOUTS:
{
	"cutouts": 
		[
			{"amount": 3, "dimensions": [0.30, 0.30, 0.30]},
            {"amount": 2, "dimensions": [0.20, 0.20, 0.20]},
            {"amount": 1, "dimensions": [0.10, 0.10, 0.10]}
		]
}

EXAMPLE OF JSON FILE WITH 3 COUTOUTS AND 3 CONSOLES:

{
	"cutouts": 
		[
			{"amount": 3, "dimensions": [0.30, 0.30, 0.30]},
            {"amount": 2, "dimensions": [0.20, 0.20, 0.20]},
            {"amount": 1, "dimensions": [0.10, 0.10, 0.10]}
		], 
	"consoles": 
		[
			{"amount": 3, "dimensions": [0.30, 0.30, 0.30]},
            {"amount": 2, "dimensions": [0.20, 0.20, 0.20]},
            {"amount": 1, "dimensions": [0.10, 0.10, 0.10]}
		]
}
*/

DROP PROCEDURE IF EXISTS add_cutout_to_bid_element;

DELIMITER $$
CREATE PROCEDURE add_cutout_to_bid_element 
(
	bid_element_id INT,
    amount INT, 
    height DECIMAL (3,2), 
    width DECIMAL (3,2), 
    length DECIMAL (3,2)
)
BEGIN
	IF (SELECT b.other_properties 
		FROM bidelements b 
        WHERE b.id = bid_element_id) 
        IS NULL
    THEN
		UPDATE bidelements b
		SET other_properties = JSON_OBJECT
		(
			'cutouts', JSON_ARRAY
			(
				JSON_OBJECT
				(
					'amount', amount,
					'dimensions', JSON_ARRAY(height, width, length)
				)
			)
		)
		WHERE b.id = bid_element_id;
    ELSE
		IF (SELECT JSON_EXTRACT(other_properties, '$.cutouts')
			FROM bidelements b
			WHERE b.id = bid_element_id) IS NOT NULL
        THEN
			UPDATE bidelements b
			SET other_properties = JSON_ARRAY_INSERT
			(
				other_properties,
				'$.cutouts[0]', JSON_OBJECT
					(
						'amount', amount,                     
						'dimensions', JSON_ARRAY(height, width, length)
					)
			)
			WHERE b.id = bid_element_id;
		ELSE
			UPDATE bidelements b
			SET other_properties = JSON_SET
			(
				other_properties,
				'$.cutouts', JSON_ARRAY
					(
						JSON_OBJECT
						(
							'amount', amount,
							'dimensions', JSON_ARRAY(height, width, length)
						)
					)
			)
			WHERE b.id = bid_element_id;
		END IF;
	END IF;
END$$
DELIMITER ;

-- 4. Creating procedure that removes cutouts from bid element

DROP PROCEDURE IF EXISTS remove_cutout_from_bid_element;

DELIMITER $$
CREATE PROCEDURE remove_cutout_from_bid_element 
(
	bid_element_id INT,
    number INT
)
BEGIN
	UPDATE bidelements b
	SET other_properties = JSON_REMOVE
	(
		other_properties,
		CONCAT('$.cutouts[', number, ']')
	)
	WHERE b.id = bid_element_id;
END$$
DELIMITER ;

-- 5. Creating procedure to set assembly_start and assembly_end of Project

DROP PROCEDURE IF EXISTS assign_date_of_project;

DELIMITER $$
CREATE PROCEDURE assign_date_of_Project(project_id INT , assembly_start DATE, assembly_end DATE)
BEGIN
	IF assembly_start > assembly_end THEN
		SIGNAL SQLSTATE '22003'
		SET MESSAGE_TEXT = "Assembly start must not be later than assembly end.";
    ELSE
		UPDATE bidelements b
        SET b.assembly_start = assembly_start, b.assembly_end = assembly_end
        WHERE b.project_id = project_id;
    END IF;
END$$
DELIMITER ;

-- 6. Create procedure that assign_financial_details_to_Projects

DROP PROCEDURE IF EXISTS assign_financial_details_to_project;

DELIMITER $$
CREATE PROCEDURE assign_financial_details_to_project
(
	project_id INT,
	concrete_cost DECIMAL(9,2),
	steel_cost DECIMAL(9,2),
    tension_steel_cost DECIMAL(9,2),
	framework_cost DECIMAL(9,2),
    man_hour_cost DECIMAL(9,2),
    energy_water_cost DECIMAL(9,2),
    faculty_cost DECIMAL(9,2),
    transport_cost DECIMAL(9,2),
    assembly_cost DECIMAL(9,2),
    markup DECIMAL(2,2)
)
BEGIN
	IF EXISTS (SELECT * 
				FROM financialdetails fd 
                WHERE fd.project_id = project_id)
    THEN
		UPDATE financialdetails fd
        SET 
			fd.concrete_cost = concrete_cost,
			fd.steel_cost = steel_cost, 
			fd.tension_steel_cost = tension_steel_cost,
			fd.framework_cost = framework_cost, 
			fd.man_hour_cost = man_hour_cost,
			fd.energy_water_cost = energy_water_cost,
			fd.faculty_cost = faculty_cost,
			fd.transport_cost = transport_cost,
			fd.assembly_cost = assembly_cost,
			fd.markup = markup
		WHERE fd.project_id = project_id;
	ELSE
		INSERT INTO financialdetails 
		(
			project_id,
			concrete_cost,
			steel_cost,
			tension_steel_cost,
			framework_cost,
			man_hour_cost,
			energy_water_cost,
			faculty_cost,
			transport_cost,
			assembly_cost,
			markup
		) 
		VALUES
		(	
			project_id,
			concrete_cost,
			steel_cost,
			tension_steel_cost,
			framework_cost,
			man_hour_cost,
			energy_water_cost,
			faculty_cost,
			transport_cost,
			assembly_cost,
			markup
		);
	END IF;
END$$
DELIMITER ;

-- 7. Creating Procedure that creates an offer for a project

DROP PROCEDURE IF EXISTS create_offer;

DELIMITER $$
CREATE PROCEDURE create_offer (project INT)
BEGIN
	IF NOT EXISTS (SELECT p.id FROM projects p WHERE p.id = project)
    THEN
		SIGNAL SQLSTATE '22003'
		SET MESSAGE_TEXT = "No such project in database.";
	ELSE
		START TRANSACTION;
		BEGIN
			DECLARE v_total_production_cost DECIMAL(11,2);
			DECLARE v_total_transport_cost DECIMAL(11,2);
			DECLARE v_total_assembly_cost DECIMAL(11,2);
			DECLARE v_total_cost DECIMAL(11,2);
			DECLARE v_markup DECIMAL(3,2);
			DECLARE v_total_price DECIMAL(11,2);
			DECLARE v_offer_id INT;
            
            SELECT
				SUM(pb.total_production_cost),
                SUM(pb.total_transport_cost),
                SUM(pb.total_assembly_cost),
                SUM(pb.total_element_cost),
                SUM(pb.total_element_price)
			INTO
				v_total_production_cost,
				v_total_transport_cost,
				v_total_assembly_cost,
				v_total_cost,
				v_total_price
			FROM prices_of_bid_elements pb
            WHERE pb.project_id = project
            GROUP BY pb.project_id;
            
            SELECT markup
            INTO v_markup
            FROM financialdetails
            WHERE project_id = project;
                
			INSERT INTO offers 
				(
					project_id, 
					concrete_cost,
					steel_cost,
					tension_steel_cost,
					framework_cost,
					man_hour_cost,
					energy_water_cost,
					faculty_cost,
					transport_cost,
					assembly_cost,
					total_production_cost,
					total_transport_cost,
					total_assembly_cost,
					total_cost,
					markup,
					total_price,
					status_id,
                    creation_date
				)
			VALUES
				(
					project,
					(SELECT concrete_cost FROM financialdetails WHERE project_id = project),
					(SELECT steel_cost FROM financialdetails WHERE project_id = project),
					(SELECT tension_steel_cost FROM financialdetails WHERE project_id = project),
					(SELECT framework_cost FROM financialdetails WHERE project_id = project),
					(SELECT man_hour_cost FROM financialdetails WHERE project_id = project),
					(SELECT energy_water_cost FROM financialdetails WHERE project_id = project),
					(SELECT faculty_cost FROM financialdetails WHERE project_id = project),
					(SELECT transport_cost FROM financialdetails WHERE project_id = project),
					(SELECT assembly_cost FROM financialdetails WHERE project_id = project),
                    v_total_production_cost,
                    v_total_transport_cost,
					v_total_assembly_cost,
					v_total_cost,
					v_markup,
					v_total_price,
                    3,
                    NOW()
				);
                
				SET v_offer_id = LAST_INSERT_ID();
			
                INSERT INTO bidelemenets_in_offer
					(
						id,
						offer_id,
						type_of_element_id,
						name,
						amount,
						height,
						width,
						length,
						steel_saturation,
						tension_steel_saturation,
						assembly_start,
						assembly_end,
						other_properties
                    )
					SELECT
						id,
						v_offer_id,
						type_of_element_id,
						name,
						amount,
						height,
						width,
						length,
						steel_saturation,
						tension_steel_saturation,
						assembly_start,
						assembly_end,
						other_properties
					FROM bidelements
                    WHERE project_id = project;
		END;
        COMMIT;
	END IF;
END$$
DELIMITER ;