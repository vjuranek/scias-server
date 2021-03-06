-- CLEAN ALL DATA
DROP ALL OBJECTS;

-- CREATE TABLES
CREATE TABLE IF NOT EXISTS client_version (
    id integer PRIMARY KEY,
    major smallint,
    minor smallint,
    released timestamp
);
CREATE SEQUENCE client_version_id_seq INCREMENT BY 50; -- Hibernate default allocation size


CREATE TABLE IF NOT EXISTS client (
    id integer PRIMARY KEY,
    version_id integer NOT NULL UNIQUE REFERENCES client_version(id)
);
CREATE SEQUENCE client_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS scias_role (
    id integer PRIMARY KEY,
    role VARCHAR(32) NOT NULL,
    description VARCHAR(255)
);
CREATE SEQUENCE scias_role_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS scias_user (
    id integer PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);
CREATE SEQUENCE scias_user_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS user_role (
    scias_user_id integer NOT NULL REFERENCES scias_user(id),
    scias_role_id integer NOT NULL REFERENCES scias_role(id)
);


CREATE TABLE IF NOT EXISTS station_group (
    id integer PRIMARY KEY,
    name varchar(255),
    parent_group_id integer REFERENCES station_group(id)
);
CREATE SEQUENCE station_group_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS station (
    id integer PRIMARY KEY,
    uuid char(36) UNIQUE NOT NULL,
    name varchar(255),
    station_group_id integer REFERENCES station_group(id)
);
CREATE SEQUENCE station_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS locality (
    id integer PRIMARY KEY,
    local_id integer NOT NULL,
    longtitude double precision NOT NULL,
    latitude double precision NOT NULL,
    altitude double precision NOT NULL
);
CREATE SEQUENCE locality_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS patient (
    id integer PRIMARY KEY,
    local_id integer NOT NULL,
    first_name varchar(255),
    middle_name varchar(255),
    last_name varchar(255),
    day_of_birth date,
    station_id integer NOT NULL REFERENCES station(id)
);
CREATE SEQUENCE patient_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS batch (
    id integer PRIMARY KEY,
    local_id integer NOT NULL,
    created time,
    finished boolean,
    patient_id integer REFERENCES patient(id),
    station_id integer NOT NULL REFERENCES station(id)
);
CREATE SEQUENCE batch_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS result_set (
    id integer PRIMARY KEY,
    local_id integer NOT NULL
);
CREATE SEQUENCE result_set_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS sample (
    id integer PRIMARY KEY,
    local_id integer NOT NULL,
    created time,
    update_time time,
    finished boolean,
    locality_id integer REFERENCES locality(id),
    batch_id integer NOT NULL REFERENCES batch(id),
    station_id integer NOT NULL REFERENCES station(id)
);
CREATE SEQUENCE sample_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS analysis (
    id integer PRIMARY KEY,
    local_id integer NOT NULL,
    created timestamp,
    algorithm_version varchar(255),
    sample_id integer REFERENCES sample(id),
    result_set_id integer NOT NULL UNIQUE REFERENCES result_set(id),
    station_id integer NOT NULL REFERENCES station(id)
);
CREATE SEQUENCE analysis_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS cell (
    id integer PRIMARY KEY,
    local_id integer NOT NULL,
    x integer,
    y integer,
    width integer,
    height integer,
    result_set_id integer NOT NULL REFERENCES result_set(id)
);
CREATE SEQUENCE cell_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS image (
    id integer PRIMARY KEY,
    local_id integer NOT NULL,
    path varchar(4096),
    name varchar(255),
    sha256 char(255),
    width integer,
    height integer,
    pixel_size double precision,
    created timestamp,
    mime_type varchar(16) NOT NULL, -- TODO use mimetype custom type
    bytes bytea
);
CREATE SEQUENCE image_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS detected_object (
    id integer PRIMARY KEY,
    local_id integer NOT NULL,
    cell_id integer NOT NULL REFERENCES cell(id),
    class_id integer NOT NULL,
    x integer,
    y integer,
    width integer,
    height integer,
    resolved boolean,
    resolved_by_id integer REFERENCES scias_user(id),
    resolved_time timestamp
    -- image_id integer REFERENCES image(id)
);
CREATE SEQUENCE detected_object_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS input_data (
    id integer PRIMARY KEY,
    local_id integer NOT NULL,
    image_id integer UNIQUE REFERENCES image(id),
    analysis_id integer NOT NULL UNIQUE REFERENCES analysis(id),
    client_id integer  REFERENCES client(id)
);
CREATE SEQUENCE input_data_id_seq INCREMENT BY 50;

-- INSERT DATA
INSERT INTO station(id, uuid, name) VALUES(1, 'aaaaaaaa-1111-2222-3333-bbbbbbbbbbbb', 'testStation');
INSERT INTO station(id, uuid, name) VALUES(2, 'f8ffba00-9134-4828-b34d-c03b4b2ee736', 'testStation2');