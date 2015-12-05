
-- http://stackoverflow.com/questions/16572207/how-to-save-foreign-key-entity-in-jpa  <--- how to set foreign key in JPA

insert into Airport(iatacode,name,city,timezone) values ('CPH','copenhagen airport','Copenhagen','+1');
insert into Airport(iatacode,name,city,timezone) values ('BAR','Barcelona airport','Barcelona','-1');
insert into Airport(iatacode,name,city,timezone) values ('LAX','Los Angeles airport','Los Angeles ROX','+10');



insert into FlightInstance(departureDate,departuretime,flightTime,flightNumber,availableSeats,price,FLIESTO_IATACODE,FLIESFROM_IATACODE) values ('1990-01-01',1230,1300,1,250,180,'CPH','BAR');
insert into FlightInstance(departureDate,departuretime,flightTime,flightNumber,availableSeats,price,FLIESTO_IATACODE,FLIESFROM_IATACODE) values ('1990-01-01',0650,0700,2,100,230,'BAR','LAX');
insert into FlightInstance(departureDate,departuretime,flightTime,flightNumber,availableSeats,price,FLIESTO_IATACODE,FLIESFROM_IATACODE) values ('1990-01-01',1330,1400,3,150,340,'LAX','CPH');
 

insert into airline(name) values ('timetravel');


insert into Flight(fligthNumber,numberOfSeats,airline_name) values (1,200,'timetravel');
insert into Flight(fligthNumber,numberOfSeats,airline_name) values (2,250,'timetravel');
insert into Flight(fligthNumber,numberOfSeats,airline_name) values (3,80,'timetravel');






/*fliesTo fliesFrom de skal ikke være i airport, de bliver tilføjet gennem flightInstance..*/
 