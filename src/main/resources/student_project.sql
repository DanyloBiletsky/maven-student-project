DROP TABLE IF EXISTS jc_student_child;
DROP TABLE IF EXISTS jc_student_order;
DROP TABLE IF EXISTS jc_passport_office;
DROP TABLE IF EXISTS jc_register_office;
DROP TABLE IF EXISTS jc_ukraine_structure;
DROP TABLE IF EXISTS jc_universities;
DROP TABLE IF EXISTS jc_streets;


CREATE TABLE jc_streets
(
    street_code varchar(10) not null,
    street_name varchar(100),
    PRIMARY KEY (street_code)
);

CREATE TABLE jc_universities
(
    university_id varchar(100) not null,
    university_name varchar(100),
    PRIMARY KEY (university_id)
);

CREATE TABLE jc_ukraine_structure
(
    area_id varchar(20) not null,
    area_name varchar(200),
    PRIMARY KEY (area_id)
);

CREATE TABLE jc_passport_office
(
    p_office_id varchar(10) not null,
    p_office_area_id varchar(20) not null,
    p_office_name varchar(100),
    PRIMARY KEY (p_office_id),
    FOREIGN KEY (p_office_area_id) REFERENCES jc_ukraine_structure(area_id) ON DELETE RESTRICT
);

CREATE TABLE jc_register_office
(
    r_office_id varchar(10) not null,
    r_office_area_id varchar(20) not null,
    r_office_name varchar(100),
    PRIMARY KEY (r_office_id),
    FOREIGN KEY (r_office_area_id) REFERENCES jc_ukraine_structure(area_id) ON DELETE RESTRICT
);

CREATE TABLE jc_student_order
(
    student_order_id SERIAL,
    student_order_status int not null,
    student_order_date timestamp not null,
    h_surname varchar(100) not null,
    h_name varchar(100) not null,
    h_patronymic varchar(100) not null,
    h_date_of_birth date not null,
    h_passport_number varchar(10) not null,
    h_passport_date date not null,
    h_passport_department varchar(100) not null,
    h_post_code varchar(10),
    h_street_code varchar(10) not null,
    h_city varchar(100) not null,
    h_building varchar(10) not null,
    h_extension varchar(10),
    h_apartment varchar(10),
    h_university_id varchar(100) not null,
    h_university_name varchar(100),
    h_student_number varchar(30) not null,
    w_surname varchar(100) not null,
    w_name varchar(100) not null,
    w_patronymic varchar(100) not null,
    w_date_of_birth date not null,
    w_passport_number varchar(10) not null,
    w_passport_date date not null,
    w_passport_department varchar(100) not null,
    w_post_code varchar(10),
    w_street_code varchar(10) not null,
    w_city varchar(100) not null,
    w_building varchar(10) not null,
    w_extension varchar(10),
    w_apartment varchar(10),
    w_university_id varchar(100) not null,
    w_university_name varchar(100),
    w_student_number varchar(30) not null,
    marriage_certificate_id varchar(20) not null,
    register_office_id varchar not null,
    marriage_date date not null,
    PRIMARY KEY (student_order_id),
    FOREIGN KEY (h_street_code) REFERENCES jc_streets(street_code) ON DELETE RESTRICT,
    FOREIGN KEY (h_university_id) REFERENCES jc_universities(university_id) ON DELETE RESTRICT,
    FOREIGN KEY (w_university_id) REFERENCES jc_universities(university_id) ON DELETE RESTRICT,
    FOREIGN KEY (w_street_code) REFERENCES jc_streets(street_code) ON DELETE RESTRICT,
    FOREIGN KEY (register_office_id) REFERENCES jc_register_office(r_office_id) ON DELETE RESTRICT
);

CREATE TABLE jc_student_child
(
    student_child_id SERIAL,
    student_order_id varchar not null,
    с_surname varchar(100) not null,
    с_name varchar(100) not null,
    с_patronymic varchar(100) not null,
    с_date_of_birth date not null,
    c_birth_certificate_number varchar(100) not null,
    c_register_office_id varchar(10) not null,
    c_date_of_receiving_birth_certificate date not null,
    с_post_code varchar(10),
    c_street_code varchar(10) not null,
    с_city varchar(100) not null,
    с_building varchar(10) not null,
    с_extension varchar(10),
    с_apartment varchar(10),
    PRIMARY KEY (student_child_id),
    FOREIGN KEY (c_street_code) REFERENCES jc_streets(street_code) ON DELETE RESTRICT,
    FOREIGN KEY (c_register_office_id) REFERENCES jc_register_office(r_office_id) ON DELETE RESTRICT
);

CREATE INDEX idx_student_order_status ON jc_student_order(student_order_status);
CREATE INDEX idx_student_order_id ON jc_student_child (student_order_id);
