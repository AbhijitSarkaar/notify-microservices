
create database notify;
use notify;



-- USERS TABLE

create table users(
	user_id bigint primary key auto_increment,
    username varchar(75) not null unique,
    password varchar(255) not null,
    email varchar(75) not null unique
);



-- ROLE TABLE

create table roles(
	role_id int primary key auto_increment,
    role_name varchar(50) not null
); 

insert into roles(role_name) values
	("ROLE_USER"), 
    ("ROLE_ADMIN");
    


-- USER_ROLE TABLE 

create table user_role(
	user_id bigint not null,
    role_id int not null
); 


