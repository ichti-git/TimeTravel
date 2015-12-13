
-- http://stackoverflow.com/questions/16572207/how-to-save-foreign-key-entity-in-jpa  <--- how to set foreign key in JPA
INSERT INTO `AIRPORT` (`IATACODE`, `CITY`, `NAME`, `TIMEZONE`) VALUES ('BCN', 'Barcelona', 'Barcelona International', '0');
INSERT INTO `AIRPORT` (`IATACODE`, `CITY`, `NAME`, `TIMEZONE`) VALUES ('CDG', 'Paris', 'Charles de Gualle International', '0');
INSERT INTO `AIRPORT` (`IATACODE`, `CITY`, `NAME`, `TIMEZONE`) VALUES ('CPH', 'Copenhagen', 'Copenhagen Kastrup', '0');
INSERT INTO `AIRPORT` (`IATACODE`, `CITY`, `NAME`, `TIMEZONE`) VALUES ('SXF', 'Berlin', 'Berlin-Schönefeld International', '0');
INSERT INTO `AIRPORT` (`IATACODE`, `CITY`, `NAME`, `TIMEZONE`) VALUES ('STN', 'London', 'London Stansted', '0');
INSERT INTO `AIRPORT` (`IATACODE`, `CITY`, `NAME`, `TIMEZONE`) VALUES ('LAX', 'Los Angeles', 'Los Angeles International', '0');
INSERT INTO `AIRPORT` (`IATACODE`, `CITY`, `NAME`, `TIMEZONE`) VALUES ('SFO', 'San Francisco', 'San Francisco International', '0');
INSERT INTO `AIRPORT` (`IATACODE`, `CITY`, `NAME`, `TIMEZONE`) VALUES ('AMS', 'Amsterdam', 'Amsterdam Schiphol Airport', '0');
INSERT INTO `AIRPORT` (`IATACODE`, `CITY`, `NAME`, `TIMEZONE`) VALUES ('FCO', 'Rome', 'Leonardo da Vinci-Fiumicino Airport', '0');
INSERT INTO `AIRPORT` (`IATACODE`, `CITY`, `NAME`, `TIMEZONE`) VALUES ('HEM', 'Helsinki', 'Helsinki Malmi Airport', '0');
INSERT INTO `AIRLINE` (`NAME`, `URL`) VALUES ('AngularJS Airline', 'http://angularairline-plaul.rhcloud.com/');
INSERT INTO `AIRLINE` (`NAME`, `URL`) VALUES ('COSGrp2 Airline', 'http://wildfly-x.cloudapp.net/airline/');
INSERT INTO `AIRLINE` (`NAME`, `URL`) VALUES ('TimeTravel', 'http://timetravel-tocvfan.rhcloud.com/');

insert into Airport(iatacode,name,city,timezone) values ('CPH','copenhagen airport','Copenhagen','+1');
insert into Airport(iatacode,name,city,timezone) values ('BCN','Barcelona airport','Barcelona','-1');
insert into Airport(iatacode,name,city,timezone) values ('LAX','Los Angeles airport','Los Angeles ROX','+10');



insert into FlightInstance(departureDate,departuretime,flightTime,flightNumber,availableSeats,price,FLIESTO_IATACODE,FLIESFROM_IATACODE,FLIGHT_FLIGTHNUMBER) values ('1990-01-01',1230,1300,1,250,180,'CPH','BCN',1);
insert into FlightInstance(departureDate,departuretime,flightTime,flightNumber,availableSeats,price,FLIESTO_IATACODE,FLIESFROM_IATACODE,FLIGHT_FLIGTHNUMBER) values ('1990-01-01',0650,0700,2,100,230,'BCN','LAX',2);
insert into FlightInstance(departureDate,departuretime,flightTime,flightNumber,availableSeats,price,FLIESTO_IATACODE,FLIESFROM_IATACODE,FLIGHT_FLIGTHNUMBER) values ('1990-01-01',1330,1400,3,150,340,'LAX','CPH',3);
 

insert into airline(name) values ('timetravel');
insert into Flight(fligthNumber,numberOfSeats,airline_name) values (1,200,'timetravel');
insert into Flight(fligthNumber,numberOfSeats,airline_name) values (2,250,'timetravel');
insert into Flight(fligthNumber,numberOfSeats,airline_name) values (3,80,'timetravel');


insert into userrole(rolename) values ('User');
insert into userrole(rolename) values ('Admin');

insert into systemuser(USERNAME, EMAIL, FIRSTNAME, LASTNAME, PASSWORD, PHONE) VALUES ('testuser', 'reserveeEmail@hotmail.com', 'hans', 'jørgen', 'test', 'ReservePhone');
                                                                                
-- /*fliesTo fliesFrom de skal ikke være i airport, de bliver tilføjet gennem flightInstance..*/
 