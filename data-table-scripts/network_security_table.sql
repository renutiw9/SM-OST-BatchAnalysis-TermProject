create database db_schoolweb;

create table network_security(
	No integer,
	Time varchar(100), 
	Source varchar(100), 
	Destination varchar(100), 
	Protocol varchar(30), 
	Length integer,
	Info varchar(1000));

insert into network_security(No, Time, Source, Destination, Protocol, Length, Info) values(1, "0", "10.10.1.10", "12.18.1.40", "TCP", 114, "Dummy Info present here");

