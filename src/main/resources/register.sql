DROP TABLE IF EXISTS city_register_address_person;
DROP TABLE IF EXISTS city_register_person;
DROP TABLE IF EXISTS city_register_addresses;
DROP TABLE IF EXISTS city_register_districts;
DROP TABLE IF EXISTS city_register_streets;


CREATE TABLE city_register_districts(
    district_code varchar(50) not null,
    district_name varchar(50) not null,
    PRIMARY KEY (district_code)
);

INSERT INTO city_register_districts(district_code, district_name)
VALUES ('UA80000000000126643', 'Голосіївський район'),
    ('UA80000000000210193', 'Дарницький район'),
    ('UA80000000000336424', 'Деснянський район'),
    ('UA80000000000479391', 'Дніпровський район'),
    ('UA80000000000551439', 'Оболонський район'),
    ('UA80000000000624772', 'Печерський район'),
    ('UA80000000000719633', 'Подільський район'),
    ('UA80000000000875983', 'Святошинський район'),
    ('UA80000000000980793', 'Солом*янський район'),
    ('UA80000000001078669', 'Шевченківський район');

CREATE TABLE city_register_streets(
    street_code varchar(50) not null,
    street_name varchar(50) not null,
    PRIMARY KEY (street_code)
);

INSERT INTO city_register_streets(street_code, street_name)
VALUES ('10084', 'Берлінського Максима'),
       ('11202', 'Ольжича'),
       ('10329', 'Глазунова');

CREATE TABLE city_register_addresses(
    address_id SERIAL,
    district_code varchar(50) not null,
    street_code varchar(50) not null,
    building varchar(10) not null,
    extension varchar(10),
    apartment varchar(10),
    PRIMARY KEY (address_id),
    FOREIGN KEY (district_code) REFERENCES city_register_districts(district_code) ON DELETE RESTRICT,
    FOREIGN KEY (street_code) REFERENCES city_register_streets(street_code) ON DELETE RESTRICT
);

INSERT INTO city_register_addresses(district_code, street_code, building, extension, apartment)
VALUES ('UA80000000001078669', '10084', '321', 'A', '913'),
        ('UA80000000000624772', '10329', '2', 'А', '312');

CREATE TABLE city_register_person(
    person_id SERIAL,
    surname varchar(100) not null,
    name varchar(100) not null,
    patronymic varchar(100) not null,
    date_of_birth date not null,
    passport_number varchar(10),
    passport_date date,
    passport_department varchar(100),
    c_birth_certificate_number varchar(50),
    date_of_receiving_birth_certificate date,
    PRIMARY KEY (person_id)
);

INSERT INTO city_register_person (surname, name, patronymic, date_of_birth, passport_number, passport_date, passport_department, c_birth_certificate_number, date_of_receiving_birth_certificate)
VALUES ('Шевченко', 'Тарас', 'Григорович', '1997-03-09', '290301', '2012-04-09', '1234', null, null);

INSERT INTO city_register_person (surname, name, patronymic, date_of_birth, passport_number, passport_date, passport_department, c_birth_certificate_number, date_of_receiving_birth_certificate)
VALUES ('Шевченко', 'Людмила', 'Миколаївна', '1999-05-12', '292501', '2014-07-10', '5678', null, null);

INSERT INTO city_register_person (surname, name, patronymic, date_of_birth, passport_number, passport_date, passport_department, c_birth_certificate_number, date_of_receiving_birth_certificate)
VALUES ('Шевченко', 'Катерина', 'Тарасівна', '2020-01-20', null, null, null, '1234', '2020-02-01');

INSERT INTO city_register_person (surname, name, patronymic, date_of_birth, passport_number, passport_date, passport_department, c_birth_certificate_number, date_of_receiving_birth_certificate)
VALUES ('Шевченко', 'Аврелія', 'Тарасівна', '2021-05-12', null, null, null, '4321', '2021-06-01');

CREATE TABLE city_register_address_person(
    person_address_id SERIAL,
    address_id integer not null,
    person_id integer not null,
    start_date date not null,
    end_date date,
    temporal boolean DEFAULT false,
    PRIMARY KEY (person_address_id),
    FOREIGN KEY (address_id) REFERENCES city_register_addresses(address_id) ON DELETE RESTRICT,
    FOREIGN KEY (person_id) REFERENCES city_register_person(person_id) ON DELETE RESTRICT
);

INSERT INTO city_register_address_person (address_id, person_id, start_date, end_date, temporal)
VALUES (1, 1, '2012-05-10', null, false),
        (1, 2, '2012-05-11', null, false),
        (1, 3, '2020-02-10', null, false),
        (1, 4, '2021-05-20', null, false);


