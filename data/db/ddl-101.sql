drop table if exists company_user cascade;
drop table if exists company_user_group cascade;

create table company_user_group (group_id varchar(30) not null, name varchar(30) not null, outline varchar(200), description varchar(400), status_type smallint not null, start_day date not null, end_day date, primary key (group_id));
create table company_user (user_id varchar(30) not null, group_id varchar(30) not null, name varchar(30) not null, name_kana varchar(30) not null, mail_address varchar(254) not null, profile varchar(200), description varchar(400), authority_type smallint not null, status_type smallint not null, primary key (user_id));
