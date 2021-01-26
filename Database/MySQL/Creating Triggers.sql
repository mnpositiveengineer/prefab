-- creating triggers

USE prefab_sales;

-- 1. Each Projects must be assigned to at least one SalesPersons
-- automatically assign new Projects to SalesPersons and if SalesPersons does not exist, create a SalesPersons (director)

DROP TRIGGER IF EXISTS AssignNewProjectsToSalesPersons;

DELIMITER //
CREATE TRIGGER AssignNewProjectsToSalesPersons
AFTER INSERT ON Projects
FOR EACH ROW
BEGIN
IF NOT EXISTS (SELECT * FROM SalesPersons WHERE id = 1)
THEN
INSERT INTO SalesPersons (id, email, first_name, last_name)
VALUES (1, 'director@prefab.com', 'director', 'director');
END IF;
INSERT INTO ProjectsSalesPersons (sales_person_id, project_id)
VALUES (1, NEW.id);
END//
DELIMITER ;

-- do not let delete SalesPersons if it will leave any Projects with no SalesPersons

DROP TRIGGER IF EXISTS DoNotLetDeleteFromSalesPersonsLeavingProjectsWithNoSalesPersons;

DELIMITER //
CREATE TRIGGER DoNotLetDeleteFromSalesPersonsLeavingProjectsWithNoSalesPersons
BEFORE DELETE ON SalesPersons
FOR EACH ROW
BEGIN
IF EXISTS 
(
	SELECT * FROM 
		(SELECT project_id, count(sales_person_id) AS number 
		FROM ProjectsSalesPersons 
		WHERE project_id IN 
			(SELECT project_id 
			FROM ProjectsSalesPersons 
			WHERE sales_person_id = OLD.id) 
	GROUP BY project_id) a
	WHERE a.number = 1
)
THEN
SIGNAL SQLSTATE '45000' 
SET MESSAGE_TEXT = 'Cannot Delete SalesPersons. Projects must be assigned to at least one SalesPersons.';
END IF;
END//
DELIMITER ;

DROP TRIGGER IF EXISTS DoNotDeleteFromProjSalesPersonsLeavingProjectsWithNoSalesPersons;

DELIMITER //
CREATE TRIGGER DoNotDeleteFromProjSalesPersonsLeavingProjectsWithNoSalesPersons
BEFORE DELETE ON ProjectsSalesPersons
FOR EACH ROW
BEGIN
IF EXISTS 
(
	SELECT * FROM 
		(SELECT project_id, count(sales_person_id) AS number 
		FROM ProjectsSalesPersons 
		WHERE project_id IN 
			(SELECT project_id 
			FROM ProjectsSalesPersons 
			WHERE sales_person_id = OLD.sales_person_id) 
	GROUP BY project_id) a
	WHERE a.number = 1
)
THEN
SIGNAL SQLSTATE '45000' 
SET MESSAGE_TEXT = 'Cannot Delete SalesPersons. Projects must be assigned to at least one SalesPersons.';
END IF;
END//
DELIMITER ;

-- 4. Create audit table which register all offer creation (on delete, on insert)
