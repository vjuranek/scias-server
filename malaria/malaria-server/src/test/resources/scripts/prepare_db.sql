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
    version_id integer UNIQUE REFERENCES client_version(id) -- TODO NOT NULL
);
CREATE SEQUENCE client_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS scias_role (
    id integer PRIMARY KEY,
    role VARCHAR(32) NOT NULL,
    description VARCHAR(256)
);
CREATE SEQUENCE scias_role_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS scias_user (
    id integer PRIMARY KEY,
    username VARCHAR(256) NOT NULL,
    password VARCHAR(256) NOT NULL
);
CREATE SEQUENCE scias_user_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS user_role (
    scias_user_id integer NOT NULL REFERENCES scias_user(id),
    scias_role_id integer NOT NULL REFERENCES scias_role(id)
);


CREATE TABLE IF NOT EXISTS station_group (
    id integer PRIMARY KEY,
    name varchar(256),
    parent_group_id integer REFERENCES station_group(id) -- TODO NOT NULL
);
CREATE SEQUENCE station_group_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS station (
    id integer PRIMARY KEY,
    uuid char(36) UNIQUE NOT NULL,
    name varchar(256),
    station_group_id integer REFERENCES station_group(id) -- TODO NOT NULL
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
    first_name varchar(256),
    middle_name varchar(256),
    last_name varchar(256),
    day_of_birth date,
    station_id integer REFERENCES station(id) -- TODO NOT NULL
);
CREATE SEQUENCE patient_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS batch (
    id integer PRIMARY KEY,
    local_id integer NOT NULL,
    created time,
    finished boolean,
    patient_id integer REFERENCES patient(id),
    station_id integer REFERENCES station(id) -- TODO NOT NULL
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
    batch_id integer REFERENCES batch(id), -- TODO NOT NULL
    station_id integer REFERENCES station(id) -- TODO NOT NULL
);
CREATE SEQUENCE sample_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS analysis (
    id integer PRIMARY KEY,
    local_id integer NOT NULL,
    created timestamp,
    algorithm_version varchar(256),
    sample_id integer REFERENCES sample(id),
    result_set_id integer UNIQUE REFERENCES result_set(id), -- TODO NOT NULL
    station_id integer REFERENCES station(id) -- TODO NOT NULL
);
CREATE SEQUENCE analysis_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS result (
    id integer PRIMARY KEY,
    local_id integer NOT NULL,
    class_id integer,
    amount double precision,
    result_set_id integer REFERENCES result_set(id) -- TODO NOT NULL
);
CREATE SEQUENCE result_id_seq INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS image (
    id integer PRIMARY KEY,
    local_id integer NOT NULL,
    path varchar(4096),
    name varchar(256),
    sha256 char(256),
    width integer,
    height integer,
    pixel_size double precision,
    created timestamp,
    mime_type varchar(16) NOT NULL, -- TODO use mimetype custom type
    client_id integer REFERENCES client -- TODO NOT NOLL
);
CREATE SEQUENCE image_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS unclassified_object (
    id integer PRIMARY KEY,
    local_id integer NOT NULL,
    class_id integer,
    resolved boolean,
    resolved_time timestamp,
    result_set_id integer REFERENCES result_set(id), -- TODO NOT NULL
    resolved_by_id integer REFERENCES scias_user(id),
    image_id integer REFERENCES image(id)
);
CREATE SEQUENCE unclassified_object_id_seq INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS input_data (
    id integer PRIMARY KEY,
    local_id integer NOT NULL,
    image_id integer UNIQUE REFERENCES image(id),
    analysis_id integer UNIQUE REFERENCES analysis(id), -- TODO NOT NULL
    client_id integer  REFERENCES client(id)
);
CREATE SEQUENCE input_data_id_seq INCREMENT BY 50;