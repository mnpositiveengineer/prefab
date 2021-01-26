-- unit tests of prefab_sales database

USE prefab_sales;

-- preparing test exit report

DROP TABLE IF EXISTS unit_tests;

CREATE TABLE unit_tests
(
	id INT UNSIGNED NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    result BOOLEAN NOT NULL
);

-- 1. Should add entries to Prospect table

DROP PROCEDURE IF EXISTS ShouldAddEntriesToProspectTable;
DELIMITER $$
CREATE PROCEDURE ShouldAddEntriesToProspectTable()
BEGIN
	DECLARE existing_number_of_rows INT;
	DECLARE expected_number_of_rows INT;
	DECLARE actual_number_of_rows INT;
	DECLARE difference TINYINT;
    
    SELECT COUNT(*)
    INTO existing_number_of_rows
    FROM Prospects;
    
    SET expected_number_of_rows = existing_number_of_rows + 5;
    
    START TRANSACTION;
		INSERT IGNORE INTO Prospects (name, tax)
		VALUES ('Prefab S.A', '123456789');
		
		INSERT IGNORE INTO Prospects (name, tax)
		VALUES ('ąĄćĆęĘłŁńŃóÓśŚźŹżŻåÅØøæÆäÄöÖüÜß', 'K');
		
		INSERT IGNORE INTO Prospects (name, tax)
		VALUES ('St. Jame\'s', 'MNHJD1234581LKAM123561LKS1');
		
		INSERT IGNORE INTO Prospects (name, tax)
		VALUES ('Co-Co', '4');
		
		INSERT IGNORE INTO Prospects (name, tax)
		VALUES ('Party77', 'AZ09');
		
		SELECT COUNT(*)
		INTO actual_number_of_rows
		FROM Prospects;
    
    ROLLBACK;
    
    IF actual_number_of_rows = expected_number_of_rows
    THEN
		INSERT INTO unit_tests
        VALUES (1, 'ShouldAddEntriesToProspectTable', TRUE);
	ELSE
		INSERT INTO unit_tests
        VALUES (1, 'ShouldAddEntriesToProspectTable', FALSE);
	END IF;
END$$
DELIMITER ;
CALL ShouldAddEntriesToProspectTable();
DROP PROCEDURE ShouldAddEntriesToProspectTable;

-- 2. Should NOT add entries to Prospect table

DROP PROCEDURE IF EXISTS ShouldNotAddEntriesToProspectTable;
DELIMITER $$
CREATE PROCEDURE ShouldNotAddEntriesToProspectTable()
BEGIN
	DECLARE existing_number_of_rows INT;
	DECLARE expected_number_of_rows INT;
	DECLARE actual_number_of_rows INT;
	DECLARE difference TINYINT;
    
    SELECT COUNT(*)
    INTO existing_number_of_rows
    FROM Prospects;
    
    SET expected_number_of_rows = existing_number_of_rows;
    
    START TRANSACTION;
		INSERT IGNORE INTO Prospects (name, tax)
		VALUES ('<>', '123456789');
        
		INSERT IGNORE INTO Prospects (name, tax)
		VALUES ('prefab', ' ');
        
		INSERT IGNORE INTO Prospects (name, tax)
		VALUES ('prefab', '%$^');
        
		INSERT IGNORE INTO Prospects (name, tax)
		VALUES ('prefab', '111111111111111111111111111');
		
		INSERT IGNORE INTO Prospects (name, tax)
		VALUES ('prefab', '111111111111111111111111111');
    
		SELECT COUNT(*)
		INTO actual_number_of_rows
		FROM Prospects;
	ROLLBACK;
    
    IF actual_number_of_rows = expected_number_of_rows
    THEN
		INSERT INTO unit_tests
        VALUES (2, 'ShouldNotAddEntriesToProspectTable', TRUE);
	ELSE
		INSERT INTO unit_tests
        VALUES (2, 'ShouldNotAddEntriesToProspectTable', FALSE);
	END IF;
END$$
DELIMITER ;
CALL ShouldNotAddEntriesToProspectTable();
DROP PROCEDURE ShouldNotAddEntriesToProspectTable;

-- 3. Should add entries to PersonOfContacts table

DROP PROCEDURE IF EXISTS ShouldAddEntriesToPersonOfContactsTable;
DELIMITER $$
CREATE PROCEDURE ShouldAddEntriesToPersonOfContactsTable()
BEGIN
	DECLARE existing_number_of_rows INT;
	DECLARE expected_number_of_rows INT;
	DECLARE actual_number_of_rows INT;
	DECLARE difference TINYINT;
    
    SELECT COUNT(*)
    INTO existing_number_of_rows
    FROM PersonOfContacts;
    
    SET expected_number_of_rows = existing_number_of_rows + 5;
    
    START TRANSACTION;
		INSERT IGNORE INTO PersonOfContacts(first_name, last_name, email, phone_number, position)
		VALUES ('Łukasz', 'Müller', 'lukaszmuller@gmail.com', '999111000', 'director');
		
		INSERT IGNORE INTO PersonOfContacts(first_name, last_name, email, phone_number, position)
		VALUES ('Mr.', 'Novak', 'johndoe1@domainsample.com', '123', 'assistant');
		
		INSERT IGNORE INTO PersonOfContacts(first_name, last_name, email, phone_number, position)
		VALUES ('Patrick', 'O\'reily', 'john.doe@domainsample.net', '321456', 'CEO');
		
		INSERT IGNORE INTO PersonOfContacts(first_name, last_name, email, phone_number, position)
		VALUES ('ąĄćĆęĘłŁńŃóÓśŚź', 'ŹżŻåÅØøæÆäÄöÖüÜß', 'john.doe43@domainsample.co.uk', '111111111', 'AVP');
		
		INSERT IGNORE INTO PersonOfContacts(first_name, last_name, email, phone_number, position)
		VALUES ('Kasia', 'Bryl-Nowak', 'kasia.bryl-nowak@artless-design.pl', '519872240', 'Assistant Vice President');
    
		SELECT COUNT(*)
		INTO actual_number_of_rows
		FROM PersonOfContacts;
	ROLLBACK;
    
    IF actual_number_of_rows = expected_number_of_rows
    THEN
		INSERT INTO unit_tests
        VALUES (3, 'ShouldAddEntriesToPersonOfContactsTable', TRUE);
	ELSE
		INSERT INTO unit_tests
        VALUES (3, 'ShouldAddEntriesToPersonOfContactsTable', FALSE);
	END IF;
END$$
DELIMITER ;
CALL ShouldAddEntriesToPersonOfContactsTable();
DROP PROCEDURE ShouldAddEntriesToPersonOfContactsTable;

-- 4. Should NOT add entries to PersonOfContacts table

DROP PROCEDURE IF EXISTS ShouldNotAddEntriesToPersonOfContactsTable;
DELIMITER $$
CREATE PROCEDURE ShouldNotAddEntriesToPersonOfContactsTable()
BEGIN
	DECLARE existing_number_of_rows INT;
	DECLARE expected_number_of_rows INT;
	DECLARE actual_number_of_rows INT;
	DECLARE difference TINYINT;
    
    SELECT COUNT(*)
    INTO existing_number_of_rows
    FROM PersonOfContacts;
    
    SET expected_number_of_rows = existing_number_of_rows;
    
    START TRANSACTION;
		INSERT IGNORE INTO PersonOfContacts(first_name, last_name, email, phone_number, position)
		VALUES ('123', 'Meller', 'lukaszmuller@gmail.com', '123456789', 'director');
		
		INSERT IGNORE INTO PersonOfContacts(first_name, last_name, email, phone_number, position)
		VALUES ('<>', 'Meller', 'lukaszmuller@gmail.com', '123456789', 'director');
		
		INSERT IGNORE INTO PersonOfContacts(first_name, last_name, email, phone_number, position)
		VALUES ('Łukasz', '123', 'lukaszmuller@gmail.com', '123456789', 'director');
		
		INSERT IGNORE INTO PersonOfContacts(first_name, last_name, email, phone_number, position)
		VALUES ('Łukasz', '<>', 'lukaszmuller@gmail.com', '123456789', 'director');
		
		INSERT IGNORE INTO PersonOfContacts(first_name, last_name, email, phone_number, position)
		VALUES ('Łukasz', 'Müller', 'abc', '123456789', 'director');
		
		INSERT IGNORE INTO PersonOfContacts(first_name, last_name, email, phone_number, position)
		VALUES ('Łukasz', 'Müller', '123', '123456789', 'director');
		
		INSERT IGNORE INTO PersonOfContacts(first_name, last_name, email, phone_number, position)
		VALUES ('Łukasz', 'Müller', '@domainsample.com', '123456789', 'director');
		
		INSERT IGNORE INTO PersonOfContacts(first_name, last_name, email, phone_number, position)
		VALUES ('Łukasz', 'Müller', 'johndoedomainsample.com', '123456789', 'director');
		
		INSERT IGNORE INTO PersonOfContacts(first_name, last_name, email, phone_number, position)
		VALUES ('Łukasz', 'Müller', 'john.doe43@domainsample', '123456789', 'director');
		
		INSERT IGNORE INTO PersonOfContacts(first_name, last_name, email, phone_number, position)
		VALUES ('Łukasz', 'Müller', 'lukaszmuller@gmail.com', 'abc', 'director');
		
		INSERT IGNORE INTO PersonOfContacts(first_name, last_name, email, phone_number, position)
		VALUES ('Łukasz', 'Müller', 'lukaszmuller@gmail.com', '1122', '999');
		
		SELECT COUNT(*)
		INTO actual_number_of_rows
		FROM PersonOfContacts;
	ROLLBACK;
    
    IF actual_number_of_rows = expected_number_of_rows
    THEN
		INSERT INTO unit_tests
        VALUES (4, 'ShouldNotAddEntriesToPersonOfContactsTable', TRUE);
	ELSE
		INSERT INTO unit_tests
        VALUES (4, 'ShouldNotAddEntriesToPersonOfContactsTable', FALSE);
	END IF;
END$$
DELIMITER ;
CALL ShouldNotAddEntriesToPersonOfContactsTable();
DROP PROCEDURE ShouldNotAddEntriesToPersonOfContactsTable;

-- 5. Should add entries to Projects table

DROP PROCEDURE IF EXISTS ShouldAddEntriesToProjectsTable;
DELIMITER $$
CREATE PROCEDURE ShouldAddEntriesToProjectsTable()
BEGIN
	DECLARE existing_number_of_rows INT;
	DECLARE expected_number_of_rows INT;
	DECLARE actual_number_of_rows INT;
	DECLARE difference TINYINT;
	DECLARE added_to_address BOOLEAN DEFAULT FALSE;
    
    SELECT COUNT(*)
    INTO existing_number_of_rows
    FROM Projects;
    
    SET expected_number_of_rows = existing_number_of_rows + 4;
    
    START TRANSACTION;
		IF NOT EXISTS (SELECT id FROM Addresses WHERE id = 1)
		THEN
			INSERT INTO Addresses
			VALUES (1, 'address', 'city', 'country', 'for testing');
			SET added_to_address = TRUE;
		END IF;
		
		INSERT IGNORE INTO Projects (name, address)
		VALUES ('ąĄćĆęĘłŁńŃóÓśŚźŹżŻåÅØøæÆäÄöÖüÜß', 1);
		
		INSERT IGNORE INTO Projects (name, address)
		VALUES ('Byggnad 3', 1);
		
		INSERT IGNORE INTO Projects (name, address)
		VALUES ('Polo-market', 1);
		
		INSERT IGNORE INTO Projects (name, address)
		VALUES ('EY\'s', 1);
		
		SELECT COUNT(*)
		INTO actual_number_of_rows
		FROM Projects;
	ROLLBACK;
    
    IF actual_number_of_rows = expected_number_of_rows
    THEN
		INSERT INTO unit_tests
        VALUES (5, 'ShouldAddEntriesToProjectsTable', TRUE);
	ELSE
		INSERT INTO unit_tests
        VALUES (5, 'ShouldAddEntriesToProjectsTable', FALSE);
	END IF;
END$$
DELIMITER ;
CALL ShouldAddEntriesToProjectsTable();
DROP PROCEDURE ShouldAddEntriesToProjectsTable;

-- 6. Should  NOT add entries to Projects table

DROP PROCEDURE IF EXISTS ShouldNotAddEntriesToProjectsTable;
DELIMITER $$
CREATE PROCEDURE ShouldNotAddEntriesToProjectsTable()
BEGIN
	DECLARE existing_number_of_rows INT;
	DECLARE expected_number_of_rows INT;
	DECLARE actual_number_of_rows INT;
	DECLARE difference TINYINT;
	DECLARE added_to_address BOOLEAN DEFAULT FALSE;
    
    SELECT COUNT(*)
    INTO existing_number_of_rows
    FROM Projects;
    
    SET expected_number_of_rows = existing_number_of_rows;
    
    START TRANSACTION;
		IF NOT EXISTS (SELECT id FROM Addresses WHERE id = 1)
		THEN
			INSERT INTO Addresses
			VALUES (1, 'address', 'city', 'country', 'for testing');
			SET added_to_address = TRUE;
		END IF;
		
		INSERT IGNORE INTO Projects (name, address)
		VALUES ('<script>', 1);
		
		SELECT COUNT(*)
		INTO actual_number_of_rows
		FROM Projects;
	ROLLBACK;
	IF actual_number_of_rows = expected_number_of_rows
	THEN
		INSERT INTO unit_tests
		VALUES (6, 'ShouldNotAddEntriesToProjectsTable', TRUE);
	ELSE
		INSERT INTO unit_tests
		VALUES (6, 'ShouldNotAddEntriesToProjectsTable', FALSE);
	END IF;
END$$
DELIMITER ;
CALL ShouldNotAddEntriesToProjectsTable();
DROP PROCEDURE ShouldNotAddEntriesToProjectsTable;

-- 7. Should add entries to SalesPersons table

DROP PROCEDURE IF EXISTS ShouldAddEntriesToSalesPersonsTable;
DELIMITER $$
CREATE PROCEDURE ShouldAddEntriesToSalesPersonsTable()
BEGIN
	DECLARE existing_number_of_rows INT;
	DECLARE expected_number_of_rows INT;
	DECLARE actual_number_of_rows INT;
	DECLARE difference TINYINT;
	DECLARE added_to_address BOOLEAN DEFAULT FALSE;
    
    SELECT COUNT(*)
    INTO existing_number_of_rows
    FROM SalesPersons;
    
    SET expected_number_of_rows = existing_number_of_rows + 4;
    
    START TRANSACTION;
		INSERT IGNORE INTO SalesPersons (email, first_name, last_name)
		VALUES ('jimmy.brown@company.com', 'Jimmy', 'Brown');
		
		INSERT IGNORE INTO SalesPersons (email, first_name, last_name)
		VALUES ('kate-wrist2@company.com', 'Kate', 'Wrist');
		
		INSERT IGNORE INTO SalesPersons (email, first_name, last_name)
		VALUES ('megan_cox@company-company.org', 'Megan', 'Cox');
		
		INSERT IGNORE INTO SalesPersons (email, first_name, last_name)
		VALUES ('jakemontana@qwerty.net', 'Jake', 'Montana');
		
		SELECT COUNT(*)
		INTO actual_number_of_rows
		FROM SalesPersons;
    ROLLBACK;
    
    IF actual_number_of_rows = expected_number_of_rows
    THEN
		INSERT INTO unit_tests
        VALUES (7, 'ShouldAddEntriesToSalesPersonsTable', TRUE);
	ELSE
		INSERT INTO unit_tests
        VALUES (7, 'ShouldAddEntriesToSalesPersonsTable', FALSE);
	END IF;
END$$
DELIMITER ;
CALL ShouldAddEntriesToSalesPersonsTable();
DROP PROCEDURE ShouldAddEntriesToSalesPersonsTable;

-- 8. Should NOT add entries to SalesPersons table

DROP PROCEDURE IF EXISTS ShouldNotAddEntriesToSalesPersonsTable;
DELIMITER $$
CREATE PROCEDURE ShouldNotAddEntriesToSalesPersonsTable()
BEGIN
	DECLARE existing_number_of_rows INT;
	DECLARE expected_number_of_rows INT;
	DECLARE actual_number_of_rows INT;
	DECLARE difference TINYINT;
	DECLARE added_to_address BOOLEAN DEFAULT FALSE;
    
    SELECT COUNT(*)
    INTO existing_number_of_rows
    FROM SalesPersons;
    
    SET expected_number_of_rows = existing_number_of_rows;
    
    START TRANSACTION;
		INSERT IGNORE INTO SalesPersons (email, first_name, last_name)
		VALUES ('jimmy.browncompany.com', 'Jimmy', 'Brown');
		
		INSERT IGNORE INTO SalesPersons (email, first_name, last_name)
		VALUES ('kate-wrist@company', 'Kate', 'Wrist');
		
		INSERT IGNORE INTO SalesPersons (email, first_name, last_name)
		VALUES ('@company-company.org', 'Megan', 'Cox');
		
		INSERT IGNORE INTO SalesPersons (email, first_name, last_name)
		VALUES ('jakemontana@.ne', 'Jake', 'Montana');
		
		INSERT IGNORE INTO SalesPersons (email, first_name, last_name)
		VALUES ('jakemontana@gmail.com', '123', 'Montana');
		
		INSERT IGNORE INTO SalesPersons (email, first_name, last_name)
		VALUES ('jakemontana@gmail.com', 'Jake', '123');

		SELECT COUNT(*)
		INTO actual_number_of_rows
		FROM SalesPersons;
	ROLLBACK;
    
    IF actual_number_of_rows = expected_number_of_rows
    THEN
		INSERT INTO unit_tests
        VALUES (8, 'ShouldNotAddEntriesToSalesPersonsTable', TRUE);
	ELSE
		INSERT INTO unit_tests
        VALUES (8, 'ShouldNotAddEntriesToSalesPersonsTable', FALSE);
	END IF;
END$$
DELIMITER ;
CALL ShouldNotAddEntriesToSalesPersonsTable();
DROP PROCEDURE ShouldNotAddEntriesToSalesPersonsTable;

-- 9. Should Assign Project to Existing SalesPerson

DROP PROCEDURE IF EXISTS ShouldAssignProjectToExistingSalesPerson;
DELIMITER $$
CREATE PROCEDURE ShouldAssignProjectToExistingSalesPerson()
BEGIN
	DECLARE assigned BOOLEAN;
    
	START TRANSACTION;
		IF NOT EXISTS (SELECT id FROM SalesPersons WHERE id = 1)
		THEN
			INSERT INTO SalesPersons
			VALUES (1, 'director@prefab.com', 'director','director');
			
			IF NOT EXISTS (SELECT id FROM Addresses WHERE id = 1)
			THEN
				INSERT INTO Addresses
				VALUES (1, 'address', 'city', 'country', 'for testing');
			END IF;
		
			INSERT INTO Projects (name, address)
			VALUES ('prefab', 1);
			
			IF EXISTS (SELECT * 
						FROM ProjectsSalesPersons 
						WHERE sales_person_id = 1 
						AND project_id = LAST_INSERT_ID())
			THEN
				SET assigned = TRUE;
			ELSE
				SET assigned = FALSE;
			END IF;
		ELSE
				IF NOT EXISTS (SELECT id FROM Addresses WHERE id = 1)
				THEN
					INSERT INTO Addresses
					VALUES (1, 'address', 'city', 'country', 'for testing');
				END IF;
			
				INSERT INTO Projects (name, address)
				VALUES ('prefab', 1);
				
				IF EXISTS (SELECT * 
							FROM ProjectsSalesPersons 
							WHERE sales_person_id = 1 
							AND project_id = LAST_INSERT_ID())
				THEN
					SET assigned = TRUE;
				ELSE
					SET assigned = FALSE;
				END IF;
		END IF;
	ROLLBACK;
	IF assigned = TRUE
		THEN
			INSERT INTO unit_tests
			VALUES (9, 'ShouldAssignProjectToExistingSalesPerson', TRUE);
		ELSE
			INSERT INTO unit_tests
			VALUES (9, 'ShouldAssignProjectToExistingSalesPerson', FALSE);
		END IF;
END$$
DELIMITER ;
CALL ShouldAssignProjectToExistingSalesPerson();
DROP PROCEDURE ShouldAssignProjectToExistingSalesPerson;

-- 10. Should Assign Project to New SalesPerson

DROP PROCEDURE IF EXISTS ShouldAssignProjectToNewSalesPerson;
DELIMITER $$
CREATE PROCEDURE ShouldAssignProjectToNewSalesPerson()
BEGIN
	DECLARE new_salesperson BOOLEAN;
	START TRANSACTION;
		IF EXISTS (SELECT id
					FROM SalesPersons
                    WHERE id = 1)
		THEN
			DELETE FROM Projects
            WHERE id IN (SELECT project_id
						FROM ProjectsSalesPersons
                        WHERE sales_person_id = 1);
            DELETE FROM SalesPersons
            WHERE id = 1;
		END IF;
        
		IF NOT EXISTS (SELECT id 
						FROM Addresses 
                        WHERE id = 1)
		THEN
				INSERT INTO Addresses
				VALUES (1, 'address', 'city', 'country', 'for testing');
		END IF;
        
		INSERT INTO Projects (name, address)
		VALUES ('prefab', 1);
        
		IF EXISTS (SELECT id
					FROM SalesPersons
                    WHERE id = 1)
		THEN
			SET new_salesperson = TRUE;
		ELSE
			SET new_salesperson = FALSE;
		END IF;
            
		IF EXISTS (SELECT *
					FROM ProjectsSalespersons
                    WHERE sales_person_id = 1
                    AND project_id = LAST_INSERT_ID())
		THEN
			SET new_salesperson = TRUE;
		ELSE
			SET new_salesperson = FALSE;
		END IF;
    ROLLBACK;
	IF new_salesperson = TRUE
	THEN
			INSERT INTO unit_tests
			VALUES (10, 'ShouldAssignProjectToNewSalesPerson', TRUE);
		ELSE
			INSERT INTO unit_tests
			VALUES (10, 'ShouldAssignProjectToNewSalesPerson', FALSE);
	END IF;
END$$
DELIMITER ;
CALL ShouldAssignProjectToNewSalesPerson();
DROP PROCEDURE ShouldAssignProjectToNewSalesPerson;

-- 11. Should Not Delete SalesPerson From Table

DROP PROCEDURE IF EXISTS ShouldNotDeleteSalesPersonFromTable;
DELIMITER $$
CREATE PROCEDURE ShouldNotDeleteSalesPersonFromTable()
BEGIN
	DECLARE salesperson BOOLEAN DEFAULT FALSE;
    
	DECLARE CONTINUE HANDLER FOR 1644
	BEGIN
        SET salesperson = TRUE;
	END;
    
	START TRANSACTION;
		IF EXISTS (SELECT id
					FROM SalesPersons
                    WHERE id = 1)
		THEN
			DELETE FROM Projects
            WHERE id IN (SELECT project_id
						FROM ProjectsSalesPersons
                        WHERE sales_person_id = 1);
			DELETE FROM SalesPersons
            WHERE id = 1;
		END IF;
        
		IF NOT EXISTS (SELECT id 
						FROM Addresses 
                        WHERE id = 1)
		THEN
				INSERT INTO Addresses
				VALUES (1, 'address', 'city', 'country', 'for testing');
		END IF;
        
		INSERT INTO Projects (name, address)
		VALUES ('prefab', 1);
        
        DELETE IGNORE FROM SalesPersons
        WHERE id = 1;
	ROLLBACK;
	IF salesperson = TRUE
	THEN
			INSERT INTO unit_tests
			VALUES (11, 'ShouldNotDeleteSalesPersonFromTable', TRUE);
		ELSE
			INSERT INTO unit_tests
			VALUES (11, 'ShouldNotDeleteSalesPersonFromTable', FALSE);
	END IF;
END$$
DELIMITER ;
CALL ShouldNotDeleteSalesPersonFromTable();
DROP PROCEDURE ShouldNotDeleteSalesPersonFromTable;

-- 12. Shoould Not Delete SalesPersonToProjectAssignment

DROP PROCEDURE IF EXISTS ShouldNotDeleteSalesPersonToProjectAssignment;
DELIMITER $$
CREATE PROCEDURE ShouldNotDeleteSalesPersonToProjectAssignment()
BEGIN
	DECLARE salesperson BOOLEAN DEFAULT FALSE;
	DECLARE CONTINUE HANDLER FOR 1644
	BEGIN
        SET salesperson = TRUE;
	END;
    
	START TRANSACTION;
		IF EXISTS (SELECT id
					FROM SalesPersons
                    WHERE id = 1)
		THEN
			DELETE FROM Projects
            WHERE id IN (SELECT project_id
						FROM ProjectsSalesPersons
                        WHERE sales_person_id = 1);
			DELETE FROM SalesPersons
            WHERE id = 1;
		END IF;
        
		IF NOT EXISTS (SELECT id 
						FROM Addresses 
                        WHERE id = 1)
		THEN
				INSERT INTO Addresses
				VALUES (1, 'address', 'city', 'country', 'for testing');
		END IF;
        
		INSERT INTO Projects (name, address)
		VALUES ('prefab', 1);
        
        DELETE FROM ProjectsSalesPersons
        WHERE sales_person_id = 1;
    ROLLBACK;
	IF salesperson = TRUE
	THEN
		INSERT INTO unit_tests
		VALUES (12, 'ShouldNotDeleteSalesPersonToProjectAssignment', TRUE);
	ELSE
		INSERT INTO unit_tests
		VALUES (12, 'ShouldNotDeleteSalesPersonToProjectAssignment', FALSE);
	END IF;
END$$
DELIMITER ;
CALL ShouldNotDeleteSalesPersonToProjectAssignment();
DROP PROCEDURE ShouldNotDeleteSalesPersonToProjectAssignment;

-- 13. Should calculate volume of one consoles

DROP PROCEDURE IF EXISTS ShouldCalculateVolumeOfOneConsoles;
DELIMITER $$
CREATE PROCEDURE ShouldCalculateVolumeOfOneConsoles()
BEGIN
	DECLARE expected_value DECIMAL (4,2);
	DECLARE actual_value DECIMAL (4,2);
    DECLARE actual_id INT;
	DECLARE CONTINUE HANDLER FOR 1305
	BEGIN
	END;
    START TRANSACTION;
		INSERT INTO bidelements (type_of_element_id, name, amount)
        VALUES (1, 'S1', 10);
        
        SET actual_id = LAST_INSERT_ID();
        
        CALL add_console_to_bid_element (actual_id, 2, 0.3, 0.4, 0.5);
        
        SELECT calculate_volume_of_consoles(actual_id)
        INTO actual_value;
    ROLLBACK;
	
    SET expected_value = 2*0.3*0.4*0.5;
    
	IF actual_value = expected_value 
	THEN
		INSERT INTO unit_tests
		VALUES (13, 'ShouldCalculateVolumeOfOneConsoles', TRUE);
	ELSE
		INSERT INTO unit_tests
		VALUES (13, 'ShouldCalculateVolumeOfOneConsoles', FALSE);
	END IF;
END$$
DELIMITER ;
CALL ShouldCalculateVolumeOfOneConsoles();
DROP PROCEDURE ShouldCalculateVolumeOfOneConsoles;

-- 14. Should calculate volume of Two consoles

DROP PROCEDURE IF EXISTS ShouldCalculateVolumeOfTwoConsoles;
DELIMITER $$
CREATE PROCEDURE ShouldCalculateVolumeOfTwoConsoles()
BEGIN
	DECLARE expected_value DECIMAL (4,2);
	DECLARE actual_value DECIMAL (4,2);
    DECLARE actual_id INT;
	DECLARE CONTINUE HANDLER FOR 1305
	BEGIN
	END;
    START TRANSACTION;
		INSERT INTO bidelements (type_of_element_id, name, amount)
        VALUES (1, 'S1', 10);
        
        SET actual_id = LAST_INSERT_ID();
        
        CALL add_console_to_bid_element (actual_id, 2, 0.3, 0.4, 0.5);
        CALL add_console_to_bid_element (actual_id, 3, 0.1, 0.1, 0.7);
        
        SELECT calculate_volume_of_consoles(actual_id)
        INTO actual_value;
    ROLLBACK;
	
    SET expected_value = 2*0.3*0.4*0.5 + 3*0.1*0.1*0.7;
    
	IF actual_value = expected_value 
	THEN
		INSERT INTO unit_tests
		VALUES (14, 'ShouldCalculateVolumeOfTwoConsoles', TRUE);
	ELSE
		INSERT INTO unit_tests
		VALUES (14, 'ShouldCalculateVolumeOfTwoConsoles', FALSE);
	END IF;
END$$
DELIMITER ;
CALL ShouldCalculateVolumeOfTwoConsoles();
DROP PROCEDURE ShouldCalculateVolumeOfTwoConsoles;

-- 15. Should calculate volume of consoles after removing

DROP PROCEDURE IF EXISTS ShouldCalculateVolumeAfterRemovingOneConsole;
DELIMITER $$
CREATE PROCEDURE ShouldCalculateVolumeAfterRemovingOneConsole()
BEGIN
	DECLARE expected_value DECIMAL (4,2);
	DECLARE actual_value DECIMAL (4,2);
    DECLARE actual_id INT;
	DECLARE CONTINUE HANDLER FOR 1305
	BEGIN
	END;
    START TRANSACTION;
		INSERT INTO bidelements (type_of_element_id, name, amount)
        VALUES (1, 'S1', 10);
        
        SET actual_id = LAST_INSERT_ID();
        
        CALL add_console_to_bid_element (actual_id, 2, 0.3, 0.4, 0.5);
        CALL add_console_to_bid_element (actual_id, 3, 0.1, 0.1, 0.7);
        CALL add_console_to_bid_element (actual_id, 1, 0.2, 0.2, 0.2);
        CALL remove_console_from_bid_element(actual_id, 2);
        
        SELECT calculate_volume_of_consoles(actual_id)
        INTO actual_value;
    ROLLBACK;
	
    SET expected_value = 2*0.3*0.4*0.5 + 3*0.1*0.1*0.7 + 1*0.2*0.2*0.2 - 2*0.3*0.4*0.5; 
    
	IF actual_value = expected_value 
	THEN
		INSERT INTO unit_tests
		VALUES (15, 'ShouldCalculateVolumeAfterRemovingOneConsole', TRUE);
	ELSE
		INSERT INTO unit_tests
		VALUES (15, 'ShouldCalculateVolumeAfterRemovingOneConsole', FALSE);
	END IF;
END$$
DELIMITER ;
CALL ShouldCalculateVolumeAfterRemovingOneConsole();
DROP PROCEDURE ShouldCalculateVolumeAfterRemovingOneConsole;

-- 16. Should calculate volume of one cutout

DROP PROCEDURE IF EXISTS ShouldCalculateVolumeOfOneCutout;
DELIMITER $$
CREATE PROCEDURE ShouldCalculateVolumeOfOneCutout()
BEGIN
	DECLARE expected_value DECIMAL (4,2);
	DECLARE actual_value DECIMAL (4,2);
    DECLARE actual_id INT;
	DECLARE CONTINUE HANDLER FOR 1305
	BEGIN
	END;
    START TRANSACTION;
		INSERT INTO bidelements (type_of_element_id, name, amount)
        VALUES (1, 'S1', 10);
        
        SET actual_id = LAST_INSERT_ID();
        
        CALL add_cutout_to_bid_element (actual_id, 2, 0.3, 0.4, 0.5);
        
        SELECT calculate_volume_of_cutouts(actual_id)
        INTO actual_value;
    ROLLBACK;
	
    SET expected_value = 2*0.3*0.4*0.5;
    
	IF actual_value = expected_value 
	THEN
		INSERT INTO unit_tests
		VALUES (16, 'ShouldCalculateVolumeOfOneCutout', TRUE);
	ELSE
		INSERT INTO unit_tests
		VALUES (16, 'ShouldCalculateVolumeOfOneCutout', FALSE);
	END IF;
END$$
DELIMITER ;
CALL ShouldCalculateVolumeOfOneCutout();
DROP PROCEDURE ShouldCalculateVolumeOfOneCutout;

-- 17. Should calculate volume of Two cutouts

DROP PROCEDURE IF EXISTS ShouldCalculateVolumeOfTwoCutouts;
DELIMITER $$
CREATE PROCEDURE ShouldCalculateVolumeOfTwoCutouts()
BEGIN
	DECLARE expected_value DECIMAL (4,2);
	DECLARE actual_value DECIMAL (4,2);
    DECLARE actual_id INT;
	DECLARE CONTINUE HANDLER FOR 1305
	BEGIN
	END;
    START TRANSACTION;
		INSERT INTO bidelements (type_of_element_id, name, amount)
        VALUES (1, 'S1', 10);
        
        SET actual_id = LAST_INSERT_ID();
        
        CALL add_cutout_to_bid_element  (actual_id, 2, 0.3, 0.4, 0.5);
        CALL add_cutout_to_bid_element  (actual_id, 3, 0.1, 0.1, 0.7);
        
        SELECT calculate_volume_of_cutouts(actual_id)
        INTO actual_value;
    ROLLBACK;
	
    SET expected_value = 2*0.3*0.4*0.5 + 3*0.1*0.1*0.7;
    
	IF actual_value = expected_value 
	THEN
		INSERT INTO unit_tests
		VALUES (17, 'ShouldCalculateVolumeOfTwoCutouts', TRUE);
	ELSE
		INSERT INTO unit_tests
		VALUES (17, 'ShouldCalculateVolumeOfTwoCutouts', FALSE);
	END IF;
END$$
DELIMITER ;
CALL ShouldCalculateVolumeOfTwoCutouts();
DROP PROCEDURE ShouldCalculateVolumeOfTwoCutouts;

-- 18. Should calculate volume of cutouts after removing

DROP PROCEDURE IF EXISTS ShouldCalculateVolumeAfterRemovingOneCutout;
DELIMITER $$
CREATE PROCEDURE ShouldCalculateVolumeAfterRemovingOneCutout()
BEGIN
	DECLARE expected_value DECIMAL (4,2);
	DECLARE actual_value DECIMAL (4,2);
    DECLARE actual_id INT;
	DECLARE CONTINUE HANDLER FOR 1305
	BEGIN
	END;
    START TRANSACTION;
		INSERT INTO bidelements (type_of_element_id, name, amount)
        VALUES (1, 'S1', 10);
        
        SET actual_id = LAST_INSERT_ID();
        
        CALL add_cutout_to_bid_element (actual_id, 2, 0.3, 0.4, 0.5);
        CALL add_cutout_to_bid_element (actual_id, 3, 0.1, 0.1, 0.7);
        CALL add_cutout_to_bid_element (actual_id, 1, 0.2, 0.2, 0.2);
        CALL remove_cutout_from_bid_element(actual_id, 2);
        
        SELECT calculate_volume_of_cutouts(actual_id)
        INTO actual_value;
    ROLLBACK;
	
    SET expected_value = 2*0.3*0.4*0.5 + 3*0.1*0.1*0.7 + 1*0.2*0.2*0.2 - 2*0.3*0.4*0.5; 
    
	IF actual_value = expected_value 
	THEN
		INSERT INTO unit_tests
		VALUES (18, 'ShouldCalculateVolumeAfterRemovingOneCutout', TRUE);
	ELSE
		INSERT INTO unit_tests
		VALUES (18, 'ShouldCalculateVolumeAfterRemovingOneCutout', FALSE);
	END IF;
END$$
DELIMITER ;
CALL ShouldCalculateVolumeAfterRemovingOneCutout();
DROP PROCEDURE ShouldCalculateVolumeAfterRemovingOneCutout;

-- 19. Should calculate volume without consoles and cutouts

DROP PROCEDURE IF EXISTS ShouldCalculateVolumeWithoutConsolesAndCutouts;
DELIMITER $$
CREATE PROCEDURE ShouldCalculateVolumeWithoutConsolesAndCutouts()
BEGIN
	DECLARE expected_value DECIMAL (4,2);
	DECLARE actual_value DECIMAL (4,2);
	DECLARE actual_id INT;
	DECLARE CONTINUE HANDLER FOR 1305
	BEGIN
    END;
	START TRANSACTION;
		INSERT INTO bidelements (type_of_element_id, name, amount, height, width, length)
		VALUES (1, 'S1', 10, 0.3, 0.5, 2.75);
		
		SET actual_id = LAST_INSERT_ID();
        
        SELECT calculate_volume(actual_id)
        INTO actual_value;
	ROLLBACK;
    
	SET expected_value = 0.3*0.5*2.75; 
    
	IF actual_value = expected_value 
	THEN
		INSERT INTO unit_tests
		VALUES (19, 'ShouldCalculateVolumeWithoutConsolesAndCutouts', TRUE);
	ELSE
		INSERT INTO unit_tests
		VALUES (19, 'ShouldCalculateVolumeWithoutConsolesAndCutouts', FALSE);
	END IF;
END$$
DELIMITER ;
CALL ShouldCalculateVolumeWithoutConsolesAndCutouts();
DROP PROCEDURE ShouldCalculateVolumeWithoutConsolesAndCutouts;

-- 20. Should calculate volume with consoles and cutouts

DROP PROCEDURE IF EXISTS ShouldCalculateVolumeWithConsolesAndCutouts;
DELIMITER $$
CREATE PROCEDURE ShouldCalculateVolumeWithConsolesAndCutouts()
BEGIN
	DECLARE expected_value DECIMAL (4,2);
	DECLARE actual_value DECIMAL (4,2);
	DECLARE actual_id INT;
	DECLARE CONTINUE HANDLER FOR 1305
	BEGIN
    END;
	START TRANSACTION;
		INSERT INTO bidelements (type_of_element_id, name, amount, height, width, length)
		VALUES (1, 'S1', 10, 0.3, 0.5, 2.75);
		
		SET actual_id = LAST_INSERT_ID();
        
		CALL add_console_to_bid_element (actual_id, 4, 0.5, 0.5, 0.5);
		CALL add_cutout_to_bid_element (actual_id, 2, 0.2, 0.2, 0.2);
        
        SELECT calculate_volume(actual_id)
        INTO actual_value;
	ROLLBACK;
    
	SET expected_value = 0.3*0.5*2.75 + 4*0.5*0.5*0.5 - 2*0.2*0.2*0.2; 
    
	IF actual_value >= expected_value -0.01 AND actual_value <= expected_value + 0.01
	THEN
		INSERT INTO unit_tests
		VALUES (20, 'ShouldCalculateVolumeWithConsolesAndCutouts', TRUE);
	ELSE
		INSERT INTO unit_tests
		VALUES (20, 'ShouldCalculateVolumeWithConsolesAndCutouts', FALSE);
	END IF;
END$$
DELIMITER ;
CALL ShouldCalculateVolumeWithConsolesAndCutouts();
DROP PROCEDURE ShouldCalculateVolumeWithConsolesAndCutouts;