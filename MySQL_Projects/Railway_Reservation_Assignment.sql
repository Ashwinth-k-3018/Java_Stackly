create database Railway_Reservation;
use Railway_Reservation; 

CREATE TABLE trains (
    train_id INT PRIMARY KEY AUTO_INCREMENT,
    train_name VARCHAR(50),
    source VARCHAR(50),
    destination VARCHAR(50)
);

INSERT INTO trains (train_name, source, destination) VALUES
('Express1','Chennai','Madurai'),
('Express2','Coimbatore','Salem'),
('Express3','Madurai','Chennai');

select * from trains;

CREATE TABLE bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    train_id INT,
    passenger_name VARCHAR(50),
    fare DECIMAL(10,2),
    status VARCHAR(20),
    FOREIGN KEY (train_id) REFERENCES trains(train_id)
);

INSERT INTO bookings (train_id, passenger_name, fare, status) VALUES
(1,'Janani',500,'Confirmed'),
(1,'Arun',500,'Waiting'),
(2,'Priya',300,'Confirmed'),
(3,'Karthik',450,'Cancelled'),
(2,'Meena',300,'Confirmed');

select * from bookings;

-- Retrieve all bookings with fare greater than 400. 

select * from bookings where fare > 400;

-- Find bookings where status is not 'Confirmed'

select * from bookings where status != 'Confirmed';

-- Display trains starting from Chennai. 

select * from trains where source In ('Chennai');

-- Retrieve bookings with fare between 300 and 500. 

select * from bookings where fare between 300 and 500;

-- Find passengers whose names start with 'A'.

select passenger_name from bookings where passenger_name like 'a%';

-- Retrieve train names along with passenger names.  

select t.train_name , b.passenger_name
from trains t
Inner Join bookings b 
on t.train_id = b.train_id;

-- Count number of bookings for each train. 

select t.train_id,count(booking_id) as count_booking
from trains t
Inner Join bookings b
on t.train_id = b.train_id
group by train_id;

-- Display train names and total fare collected for each train. 

select t.train_name , sum(b.fare)
from trains t
Inner Join bookings b
on t.train_id = b.train_id
group by t.train_id;

-- Find bookings with fare equal to the highest fare. 

select * 
from bookings
where fare = ( select Max(fare) 
				from bookings ) ;
                
-- Retrieve trains that have more than one booking. 

select * 
from trains 
where train_id In ( select train_id 
					from bookings 
                    group by train_id
                    having count(booking_id) > 1 );
