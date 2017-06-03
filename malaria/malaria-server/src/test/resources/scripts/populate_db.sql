-- INSERT TEST DATA

-- Stations
INSERT INTO station(id, uuid, name) VALUES(1, 'aaaaaaaa-1111-2222-3333-bbbbbbbbbbbb', 'testStation');
INSERT INTO station(id, uuid, name) VALUES(2, 'f8ffba00-9134-4828-b34d-c03b4b2ee736', 'testStation2');

-- Patients
INSERT INTO patient(id, local_id, first_name, middle_name, last_name, day_of_birth, station_id) VALUES (100, 1, 'joe', 'jim', 'green', '2017-06-03', 1);
INSERT INTO patient(id, local_id, first_name, middle_name, last_name, day_of_birth, station_id) VALUES (101, 1, 'marry', 'ann', 'white', '2017-06-04', 2);

-- Batches
INSERT INTO batch(id, local_id, finished, patient_id, station_id) VALUES (100, 1, true, 100, 1);
INSERT INTO batch(id, local_id, finished, patient_id, station_id) VALUES (101, 1, false, 101, 2);

