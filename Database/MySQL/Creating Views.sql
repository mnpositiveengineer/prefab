USE prefab_sales;

-- 1. Creating view that will show all bid elements with following values:
-- id 
-- project_id
-- type_of_element_id
-- name
-- amount
-- height
-- width
-- length
-- volume
-- total_volume
-- area
-- total_area
-- weight
-- total_weight
-- steel_saturation
-- tension_steel_saturation
-- assembly_start
-- assembly_end
-- other_properties

DROP VIEW IF EXISTS bid_elements_with_all_parameters;

CREATE OR REPLACE VIEW bid_elements_with_all_parameters
AS
SELECT
	b.id, 
	b.project_id, 
	b.type_of_element_id,
	b.name,
	b.amount,
	b.height,
	b.width,
	b.length,
	calculate_volume(id) AS volume,
	calculate_total_volume(id) AS total_volume,
	calculate_area(id) AS area,
	calculate_total_area(id) AS total_area,
	calculate_weight(id) AS weight,
	calculate_total_weight(id) AS total_weight,
	b.steel_saturation,
	b.tension_steel_saturation,
	b.assembly_start,
	b.assembly_end,
	b.other_properties
FROM bidelements b;

-- Error Code: 1356. 
-- View 'prefab_sales.bid_elements_with_all_parameters' 
-- references invalid table(s) or column(s) or function(s) or definer/invoker of view lack rights to use them

-- SOLUTION: functions had incorrect structure (RETURN variable after 'END' clause)

-- 1. Creating view that will show all prices of bid elements:
--     project_id
--     bid_element_id
--     amount
--     concrete_cost
--     steel_cost
--     tension_steel_cost
--     framework_cost
--     man_hour_cost
--     energy_water_cost
--     faculty_cost
--     accessory_cost
--     production_cost
--     total_production_cost
--     transport_cost
--     total_transport_cost
--     assembly_cost
--     total_assembly_cost
--     element_cost
--     total_element_cost
--     element_price
--     total_element_price

DROP VIEW IF EXISTS prices_of_bid_elements;

CREATE OR REPLACE VIEW prices_of_bid_elements
AS
SELECT
    project_id,
    id,
    amount,
    calculate_element_concrete_cost(id) AS concrete_cost,
    calculate_element_steel_cost(id) AS steel_cost,
    calculate_element_tension_steel_cost(id) AS tension_steel_cost,
    calculate_element_framework_cost(id) AS framework_cost,
    calculate_man_hour_cost(id) AS man_hour_cost,
    calculate_energy_water_cost(id) AS energy_water_cost,
    calculate_faculty_cost(id) AS faculty_cost,
    calculate_accessory_cost(id) AS accessory_cost,
    calculate_production_cost(id) AS production_cost,
    calculate_total_production_cost(id) AS total_production_cost,
    calculate_transport_cost(id) AS transport_cost,
    calculate_total_transport_cost(id) AS total_transport_cost,
    calculate_assembly_cost(id) AS assembly_cost,
    calculate_total_assembly_cost(id) AS total_assembly_cost,
    calculate_element_cost(id) AS element_cost,
    calculate_total_element_cost(id) AS total_element_cost,
    calculate_element_price(id) AS element_price,
    calculate_total_element_price(id) AS total_element_price
FROM bidelements;
