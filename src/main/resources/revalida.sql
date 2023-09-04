REVOKE CONNECT ON DATABASE last_majorrevalida FROM public;
SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'last_majorrevalida';

drop database if exists last_majorrevalida;
create database last_majorrevalida;

\c last_majorrevalida

drop table if exists user_applicants cascade;
create table user_applicants (
	applicant_id serial primary key,
    username varchar(100) unique,
    password varchar(150),
 	email varchar(50) unique,
    contact_no varchar(50) unique,
    socials varchar(100)[],
    first_name varchar(70),
    middle_name varchar(70),
    last_name varchar(70),
    user_type varchar(30),
    birth_date date,
    address text,
    civil_status varchar(20),
    gender varchar(15),
    nationality varchar(30),
    valid_id_picture varchar(255),
    is_validated boolean,
    is_activated boolean,
    date_registered timestamp
);

drop table if exists users cascade;
create table users (
	user_id serial primary key,
    username varchar(100) unique,
    password varchar(150),
 	email varchar(50) unique,
    contact_no varchar(50) unique,
    socials varchar(100)[],
    first_name varchar(70),
    middle_name varchar(70),
    last_name varchar(70),
    user_type varchar(30),
    birth_date date,
    address text,
    civil_status varchar(20),
    gender varchar(15),
    nationality varchar(30),
    active_status boolean,
    active_deactive boolean, --soft delete
    is_validated boolean,    
    image text,
    valid_id_picture text,
    date_created timestamp
);

--ADMINISTRATOR MODULE
drop table if exists administrator cascade;
create table administrator (
	administrator_id serial primary key,
    user_id int,
    foreign key(user_id) references users(user_id) on delete cascade
);

drop table if exists farming_tip cascade;
create table farming_tip (
	farming_tip_id serial primary key,
	title varchar(255),
   	description text,
   	link text,
   	image text,
   	date_created timestamp,
   	date_modified timestamp
);

--SUPPLIERS MODULE
drop table if exists supplier cascade;
create table supplier (
	supplier_id serial primary key,
    user_id int,
    foreign key(user_id) references users(user_id) on delete cascade
);

drop table if exists crop_specialization cascade;
create table crop_specialization (
	crop_specialization_id serial primary key,
    specialization_name varchar(100)
);

drop table if exists post_advertisement cascade;
create table post_advertisement (
	post_id serial primary key,
	supplier_id int,
    crop_specialization_id int,
    crop_name varchar(100),
    description text,
    crop_image varchar(255),
    quantity varchar(200),
    price float,
    date_posted timestamp,
    date_modified timestamp,
    active_deactive boolean,
    is_completed boolean,
    measurement varchar(30),
    foreign key(supplier_id) references supplier(supplier_id) on delete cascade,
    foreign key(crop_specialization_id) references crop_specialization(crop_specialization_id) on delete cascade
);

--FARMERS MODULE
drop table if exists farmer cascade;
create table farmer (
	farmer_id serial primary key,
    user_id int,
    crop_specialization_id int[],
    foreign key(user_id) references users(user_id) on delete cascade
);

drop table if exists farmer_complaint cascade;
create table farmer_complaint (
	farmer_complaint_id serial primary key,
    farmer_id int,
    complaint_title varchar(255),
    complaint_type varchar(255),
    complaint_message text,
    admin_reply_message text,
    is_read boolean default 'f',
   	is_resolved boolean default 'f',
   	read_date timestamp,
   	date_submitted timestamp,
   	active_deactive boolean,
   	image text,
    foreign key(farmer_id) references farmer(farmer_id) on delete cascade
);


drop table if exists sell_crop_details cascade;
create table sell_crop_details (
	sell_id serial primary key,
    farmer_id int,
    crop_name varchar(100),
    price float,
    quantity varchar(200),
    mobilenum_banknumber varchar(50),
    payment_mode varchar(100),
    foreign key(farmer_id) references farmer(farmer_id) on delete cascade
);

drop table if exists crop_orders cascade;
create table crop_orders (
	order_id_ref serial primary key,
    sell_id int,
    supplier_id int,
    address text,
    is_received_by_supplier boolean,
    order_status varchar(50),
    foreign key(sell_id) references sell_crop_details(sell_id) on delete cascade,
    foreign key(supplier_id) references supplier(supplier_id) on delete cascade
);

drop table if exists crop_payment cascade;
create table crop_payment (
	payment_id serial primary key,
    pay_date timestamp,
    paid_by varchar(100),
    order_id_ref int,
    proof_of_payment_image text,
    foreign key(order_id_ref) references crop_orders(order_id_ref) on delete cascade
);

drop table if exists course cascade;
create table course (
	course_id serial primary key,
    course_name varchar(100),
    description text,
    duration_in_days int,
    active_deactive boolean
);

drop table if exists course_enrolled cascade;
create table course_enrolled (
	enrollment_id serial primary key,
	farmer_id int,
	course_id int,
    enrollment_date date,
    end_of_enrollment date,
    foreign key(farmer_id) references farmer(farmer_id) on delete cascade,
    foreign key(course_id) references course(course_id) on delete cascade

);

--FARMER MODULE
drop table if exists post_advertisement_responses cascade;
create table post_advertisement_responses (
	post_response_id serial primary key,
    post_id int,
    farmer_id int,
    message text,
    price float,
    quantity varchar(200),
    is_accepted boolean default false,
    preferred_payment_mode varchar(50),
    date_created timestamp,
    date_modified timestamp,
    foreign key(post_id) references post_advertisement(post_id) on delete cascade,
    foreign key(farmer_id) references farmer(farmer_id) on delete cascade    
);

drop table if exists user_tokens;
create table user_tokens (
	user_id int primary key,
	token varchar(250),
	foreign key(user_id) references users(user_id) on delete cascade
);

insert into crop_specialization(specialization_name) 
values
('Feed Crops'),
('Fiber Crops'),
('Oil Crops');


--Insert into users and administrator
insert into users(username, password, email, contact_no, socials, first_name, middle_name, last_name, user_type, birth_date, address, gender, nationality, active_status, active_deactive, date_created, is_validated) 
values('pastrero', '123456', 'patzluke12@gmail.com', '9055261297', array['https://www.facebook.com/megalodon218'], 'Patrick', 'Artuz', 'Astrero', 'Administrator', '1999-07-08', 'Vista Verde, Cainta', 'Male', 'Filipino', 'true', 'true', '2023-08-15 9:55:00', 't');
insert into administrator(user_id) values (1);

--Insert into users and farmer
insert into users(username, password, email, contact_no, socials, first_name, middle_name, last_name, user_type, birth_date, address, gender, nationality, active_status, active_deactive, date_created, is_validated) 
values('nika', '123456', 'nika@gmail.com', '9055261296', array['https://www.facebook.com/megalodon218'], 'Nika', 'Artuz', 'Astrero', 'Farmer', '1999-07-08', 'Vista Verde, Cainta', 'Male', 'Filipino', 'true', 'true', '2023-08-15 9:55:00', 't');
insert into farmer(user_id) values (2);

insert into users(username, password, email, contact_no, socials, first_name, middle_name, last_name, user_type, birth_date, address, gender, nationality, active_status, active_deactive, date_created, is_validated) 
values('vanlester', '123456', 'van@gmail.com', '9055261295', array['https://www.facebook.com/megalodon218'], 'Van', 'Artuz', 'Astrero', 'Farmer', '1999-07-08', 'Vista Verde, Cainta', 'Male', 'Filipino', 'true', 'true', '2023-08-15 9:55:00', 't');
insert into farmer(user_id) values (3);

insert into users(username, password, email, contact_no, socials, first_name, middle_name, last_name, user_type, birth_date, address, gender, nationality, active_status, active_deactive, date_created, is_validated) 
values('vanessa', '123456', 'vanessa@gmail.com', '9055261294', array['https://www.facebook.com/megalodon218'], 'Vanessa', 'Artuz', 'Astrero', 'Farmer', '1999-07-08', 'Vista Verde, Cainta', 'Male', 'Filipino', 'true', 'true', '2023-08-15 9:55:00', 't');
insert into farmer(user_id) values (4);

insert into users(username, password, email, contact_no, socials, first_name, middle_name, last_name, user_type, birth_date, address, gender, nationality, active_status, active_deactive, date_created, is_validated) 
values('linda', '123456', 'linda@gmail.com', '9055261293', array['https://www.facebook.com/megalodon218'], 'Linda', 'Artuz', 'Astrero', 'Farmer', '1999-07-08', 'Vista Verde, Cainta', 'Male', 'Filipino', 'true', 'true', '2023-08-15 9:55:00', 't');
insert into farmer(user_id) values (5);

insert into users(username, password, email, contact_no, socials, first_name, middle_name, last_name, user_type, birth_date, address, gender, nationality, active_status, active_deactive, date_created, is_validated) 
values('robgerson', '123456', 'robgerson@gmail.com', '9055261292', array['https://www.facebook.com/megalodon218'], 'Robgerson', 'Artuz', 'Astrero', 'Farmer', '1999-07-08', 'Vista Verde, Cainta', 'Male', 'Filipino', 'true', 'true', '2023-08-15 9:55:00', 't');
insert into farmer(user_id) values (6);

--Insert into users and Supplier
insert into users(username, password, email, contact_no, socials, first_name, middle_name, last_name, user_type, birth_date, address, gender, nationality, active_status, active_deactive, date_created, is_validated) 
values('norbz', '123456', 'norbz@gmail.com', '9055261291', array['https://www.facebook.com/norbz'], 'Norbz', 'Artuz', 'Astrero', 'Supplier', '1999-07-08', 'Vista Verde, Cainta', 'Male', 'Filipino', 'true', 'true', '2023-08-15 9:55:00', 't');
insert into supplier(user_id) values (7);

--insert into farmer complaints
insert into farmer_complaint(farmer_id, complaint_title, complaint_type, complaint_message, date_submitted, active_deactive) 
values 
(1, 'The Bigas',  'Others', 'ang pangit ng bigas', '2023-08-17 12:55:00', 't'),
(1, 'The Mais',  'Others', 'ang pangit ng mais', '2023-08-17 12:55:00', 't'),
(1, 'The Siomai',  'Others', 'ang pangit ng siomai', '2023-08-17 12:55:00', 't'),
(1, 'The Carrots',  'Others', 'ang pangit ng carrots', '2023-08-17 12:55:00', 't');

insert into post_advertisement(supplier_id, crop_specialization_id, crop_name, description, crop_image, quantity, measurement, price, date_posted, active_deactive) 
values 
(1, 1, 'Princess Jasmin (Rice)', 'Must be brand new harvest and no tiny bugs that will be found', 'http://localhost:8080/api/file/display/image/WIN_20230220_16_25_22_Pro.jpg', '100', 'kg', 100000.00, '2023-08-23 15:47:00', 't'),
(1, 1, 'NFA (Rice)', 'Must be brand new harvest and no tiny bugs that will be found', 'http://localhost:8080/api/file/display/image/WIN_20230220_16_25_22_Pro.jpg', '100', 'kg', 100000.00, '2023-08-23 15:47:00', 't');

insert into post_advertisement_responses(post_id, farmer_id, message, price, quantity, is_accepted, preferred_payment_mode, date_created) 
values 
(1, 1, 'Hi there I have a message for you. Hope we can come to an agreement', 95000, '100 kg', 'f', 'Gcash', '2023-08-23 15:55:00'),
(1, 2, 'Hi there I have a message for you. Hope we can come to an agreement', 96000, '100 kg', 'f', 'Gcash', '2023-08-23 16:55:00'),
(1, 3, 'Hi there I have a message for you. Hope we can come to an agreement', 97000, '100 kg', 'f', 'Gcash', '2023-08-23 17:55:00'),
(1, 4, 'Hi there I have a message for you. Hope we can come to an agreement', 98000, '95 kg', 'f', 'Gcash', '2023-08-23 18:55:00'),
(1, 5, 'Hi there I have a message for you. Hope we can come to an agreement', 99000, '105 kg', 'f', 'Gcash', '2023-08-23 19:55:00');


