REVOKE CONNECT ON DATABASE last_majorrevalida FROM public;
SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'last_majorrevalida';

drop database if exists last_majorrevalida;
create database last_majorrevalida;

\c last_majorrevalida

drop table if exists users cascade;
create table users (
	user_id serial primary key,
    username varchar(100) unique,
    password varchar(150),
 	email varchar(50) unique,
    contact_no varchar(50),
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
    image varchar(255),
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
   	tip_message text,
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
    crop_specialization_id int,
    crop_name varchar(100),
    description text,
    crop_image varchar(255),
    quantity int,
    price float,
    date_posted timestamp,
    date_modified timestamp,
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
    complaint_message text,
    admin_reply_message text,
    is_read boolean default 'f',
   	is_resolved boolean default 'f',
   	read_date timestamp,
   	date_submitted timestamp,
   	active_deactive boolean,
    foreign key(farmer_id) references farmer(farmer_id) on delete cascade
);


drop table if exists crop_details cascade;
create table crop_details (
	crop_id serial primary key,
    farmer_id int,
    crop_name varchar(100),
    price float,
    quantity int,
    crop_image varchar(255),
    foreign key(farmer_id) references farmer(farmer_id) on delete cascade
);

drop table if exists crop_orders cascade;
create table crop_orders (
	order_id_ref serial primary key,
    crop_id int,
    supplier_id int,
    quantity int,
    price float,
    address text,
    foreign key(crop_id) references crop_details(crop_id) on delete cascade,
    foreign key(supplier_id) references supplier(supplier_id) on delete cascade
);

drop table if exists crop_payment cascade;
create table crop_payment (
	payment_id serial primary key,
    pay_date timestamp,
    payment_mode varchar(50),
    paid_by varchar(100),
    status varchar(20),
    order_id_ref int,
    foreign key(order_id_ref) references crop_orders(order_id_ref) on delete cascade
);

drop table if exists course cascade;
create table course (
	course_id serial primary key,
    course_name varchar(100),
    description text,
    duration_in_days date
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
    quantity int,
    is_accepted boolean,
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

--Insert into users and administrator
insert into users(username, password, email, contact_no, socials, first_name, middle_name, last_name, user_type, birth_date, address, gender, nationality, active_status, active_deactive, date_created) 
values('pastrero', '123456', 'patzluke12@gmail.com', '9055261296', array['https://www.facebook.com/megalodon218'], 'Patrick', 'Artuz', 'Astrero', 'Administrator', '1999-07-08', 'Vista Verde, Cainta', 'Male', 'Filipino', 'true', 'true', '2023-08-15 9:55:00');
insert into administrator(user_id) values (1);

--Insert into users and farmer
insert into users(username, password, email, contact_no, socials, first_name, middle_name, last_name, user_type, birth_date, address, gender, nationality, active_status, active_deactive, date_created) 
values('nika', '123456', 'nika@gmail.com', '9055261296', array['https://www.facebook.com/megalodon218'], 'Nika', 'Artuz', 'Astrero', 'Farmer', '1999-07-08', 'Vista Verde, Cainta', 'Male', 'Filipino', 'true', 'true', '2023-08-15 9:55:00');
insert into farmer(user_id) values (2);

insert into users(username, password, email, contact_no, socials, first_name, middle_name, last_name, user_type, birth_date, address, gender, nationality, active_status, active_deactive, date_created) 
values('norbz', '123456', 'norbz@gmail.com', '9055261296', array['https://www.facebook.com/norbz'], 'Norbz', 'Artuz', 'Astrero', 'Supplier', '1999-07-08', 'Vista Verde, Cainta', 'Male', 'Filipino', 'true', 'true', '2023-08-15 9:55:00');
insert into supplier(user_id) values (3);

--insert into farmer complaints
insert into farmer_complaint(farmer_id, complaint_title, complaint_message, date_submitted, active_deactive) 
values (1, 'The Bigas', 'ang pangit ng bigas', '2023-08-17 12:55:00', 't'),
(1, 'The Mais', 'ang pangit ng mais', '2023-08-17 12:55:00', 't'),
(1, 'The Siomai', 'ang pangit ng siomai', '2023-08-17 12:55:00', 't'),
(1, 'The Carrots', 'ang pangit ng carrots', '2023-08-17 12:55:00', 't');
