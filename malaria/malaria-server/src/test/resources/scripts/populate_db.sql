-- RESET ALL RELEVANT TABLES

DELETE FROM station;
DELETE FROM patient;
DELETE FROM batch;
DELETE FROM sample;
DELETE FROM result_set;
DELETE FROM analysis;

-- INSERT TEST DATA

-- Stations
INSERT INTO station(id, uuid, name) VALUES(1, 'aaaaaaaa-1111-2222-3333-bbbbbbbbbbbb', 'testStation');
INSERT INTO station(id, uuid, name) VALUES(2, 'f8ffba00-9134-4828-b34d-c03b4b2ee736', 'testStation2');

-- Patients
INSERT INTO patient(id, local_id, first_name, middle_name, last_name, day_of_birth, station_id) VALUES (100, 100, 'joe', 'jim', 'green', '2017-06-03', 1);
INSERT INTO patient(id, local_id, first_name, middle_name, last_name, day_of_birth, station_id) VALUES (101, 100, 'marry', 'ann', 'white', '2017-06-04', 2);

-- Batches
INSERT INTO batch(id, local_id, created, finished, patient_id, station_id) VALUES (100, 100, now(), true, 100, 1);
INSERT INTO batch(id, local_id, created, finished, patient_id, station_id) VALUES (101, 100, now(), false, 101, 2);

-- Samples
INSERT INTO sample(id, local_id, created, update_time, finished, batch_id, station_id) VALUES (100, 100, now(), now(), true, 100, 1);
INSERT INTO sample(id, local_id, created, update_time, finished, batch_id, station_id) VALUES (101, 100, now(), now(), false, 101, 2);

--- Result sets
INSERT INTO result_set(id, local_id) VALUES (100, 100);
INSERT INTO result_set(id, local_id) VALUES (101, 101);

-- Analyses
INSERT INTO analysis(id, local_id, created, algorithm_version, sample_id, result_set_id, station_id) VALUES (100, 100, now(), 1.0, 100, 100, 1);
INSERT INTO analysis(id, local_id, created, algorithm_version, sample_id, result_set_id, station_id) VALUES (101, 101, PARSEDATETIME('25-11-2016 22:00:00.00', 'dd-MM-yyyy hh:mm:ss.SS'), 1.0, 100, 101, 1);

