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
    valid_id_type varchar(200),
    valid_id_number varchar(100),
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

drop table if exists supplier_complaint cascade;
create table supplier_complaint (
	supplier_complaint_id serial primary key,
    supplier_id int,
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
    foreign key(supplier_id) references supplier(supplier_id) on delete cascade
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
    is_final_offer_submitted boolean default false,
    is_final_offer_accepted boolean default false,
    is_transaction_completed boolean default false,
    preferred_payment_mode varchar(50),
    date_created timestamp,
    date_modified timestamp,
    foreign key(post_id) references post_advertisement(post_id) on delete cascade,
    foreign key(farmer_id) references farmer(farmer_id) on delete cascade    
);

drop table if exists sell_crop_details cascade;
create table sell_crop_details (
	sell_id serial primary key,
    farmer_id int,
    post_response_id int,
    crop_name varchar(100),
    price float,
    quantity varchar(200),
    mobilenum_banknumber varchar(50),
    payment_mode varchar(100),
    foreign key(farmer_id) references farmer(farmer_id) on delete cascade,
    foreign key(post_response_id) references post_advertisement_responses(post_response_id) on delete cascade
);

-----------------------------------------
drop sequence if exists crop_order_sequence;
CREATE SEQUENCE crop_order_sequence
	as bigint
	increment by 1
	start with 1
;

CREATE OR REPLACE FUNCTION crop_order_sequence_function()
RETURNS text AS $$
DECLARE
    today_date text;
BEGIN
    today_date := concat('ORD', to_char(now()::date, 'YYYYMMDD'));
    RETURN today_date || nextval('crop_order_sequence')::text;
END;
$$ LANGUAGE plpgsql;

drop table if exists crop_orders cascade;
create table crop_orders (
	order_id_ref text default crop_order_sequence_function() primary key,
    sell_id int,
    supplier_id int,
    address text,
    is_proof_of_payment_submitted boolean default 'f',
    is_crop_received_by_supplier boolean default 'f',
    is_payment_received_by_farmer boolean default 'f',
    order_received_date timestamp,
    payment_received_date timestamp,
    order_status varchar(255),
    cancel_reason text,
    foreign key(sell_id) references sell_crop_details(sell_id) on delete cascade,
    foreign key(supplier_id) references supplier(supplier_id) on delete cascade
);
-----------------------------------------

-----------------------------------------
drop sequence if exists crop_payment_sequence;
CREATE SEQUENCE crop_payment_sequence
	as bigint
	increment by 1
	start with 1
;

CREATE OR REPLACE FUNCTION crop_payment_sequence_function()
RETURNS text AS $$
DECLARE
    today_date text;
BEGIN
    today_date := concat('TX', to_char(now()::date, 'YYYYMMDD'));
    RETURN today_date || nextval('crop_payment_sequence')::text;
END;
$$ LANGUAGE plpgsql;

drop table if exists crop_payment cascade;
create table crop_payment (
	payment_id text default crop_payment_sequence_function() primary key,
    pay_date timestamp,
    paid_by varchar(100),
    order_id_ref text,
    proof_of_payment_image text,
    transaction_reference_number varchar(100),
    foreign key(order_id_ref) references crop_orders(order_id_ref) on delete cascade
);
-----------------------------------------

drop table if exists user_tokens;
create table user_tokens (
	user_id int primary key,
	token varchar(250),
	foreign key(user_id) references users(user_id) on delete cascade
);

drop table if exists user_notifications cascade;
create table user_notifications (
	notification_id bigserial primary key,
	user_id int,
	notification_title varchar(255),
	notification_message varchar(255),
   	is_read boolean,
   	date_created timestamp,
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
insert into users(username, password, email, contact_no, socials, first_name, middle_name, last_name, user_type, birth_date, address, gender, nationality, active_status, active_deactive, date_created, is_validated, valid_id_picture, valid_id_type, valid_id_number) 
values('nika', '123456', 'nika@gmail.com', '9055261296', array['https://www.facebook.com/megalodon218'], 'Nika', 'Artuz', 'Astrero', 'Farmer', '1999-07-08', 'Vista Verde, Cainta', 'Male', 'Filipino', 'true', 'true', '2023-08-15 9:55:00', 't', 'http://localhost:8080/api/file/display/image/2x2_pic.jpg', 'SSS Card', '2354647');
insert into farmer(user_id) values (2);

insert into users(username, password, email, contact_no, socials, first_name, middle_name, last_name, user_type, birth_date, address, gender, nationality, active_status, active_deactive, date_created, is_validated) 
values('vanlester', '123456', 'van@gmail.com', '9055261295', array['https://www.facebook.com/megalodon218'], 'Van', 'Artuz', 'Astrero', 'Farmer', '1999-07-08', 'Vista Verde, Cainta', 'Male', 'Filipino', 'true', 'true', '2023-08-15 9:55:00', 'f');
insert into farmer(user_id) values (3);

--Insert into users and Supplier
insert into users(username, password, email, contact_no, socials, first_name, middle_name, last_name, user_type, birth_date, address, gender, nationality, active_status, active_deactive, date_created, is_validated, valid_id_picture, valid_id_type, valid_id_number) 
values('norbz', '123456', 'norbz@gmail.com', '9055261291', array['https://www.facebook.com/norbz'], 'Norbz', 'Artuz', 'Astrero', 'Supplier', '1999-07-08', 'Vista Verde, Cainta', 'Male', 'Filipino', 'true', 'true', '2023-08-15 9:55:00', 't', 'http://localhost:8080/api/file/display/image/2x2_pic.jpg', 'SSS Card', '2354647');
insert into supplier(user_id) values (4);



