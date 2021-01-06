drop table if exists Tickets;
drop table if exists Users;
drop table if exists Events;

--CREATE SEQUENCE Events_seq;
CREATE TABLE Events (
  id int NOT NULL,
    -- DEFAULT NEXTVAL ('Events_seq'),
  eventname varchar(64),
  place varchar(64),
  capacity int,
  date varchar(64),
  create_date varchar(64),
  update_date varchar(64),
  ticketprice int,
  PRIMARY KEY (id)
);
--ALTER SEQUENCE Events_seq RESTART WITH 1;

--CREATE SEQUENCE Users_seq;
CREATE TABLE Users (
  id int NOT NULL,
    -- DEFAULT NEXTVAL ('Users_seq'),
  username varchar(64) DEFAULT NULL CHECK (username not SIMILAR TO '%(@|#|$)%'),
  first_name varchar(64),
  last_name varchar(64),
  age int,
  email varchar(64),
  create_date varchar(64),
  update_date varchar(64),
  PRIMARY KEY (id)
);
--ALTER SEQUENCE Users_seq RESTART WITH 1;

-- CREATE SEQUENCE Tickets_seq;
CREATE TABLE Tickets (
  id int NOT NULL,
  -- DEFAULT NEXTVAL ('Tickets_seq'),
  category varchar(64),
  place varchar(64),
  cinema_name varchar(64),
  cinema_address varchar(64),
  price int,
  cinema_phone varchar(64),
  cinema_facility varchar(64),
  purchase_date TIMESTAMP,
  create_date varchar(64),
  update_date varchar(64),
  eventid int,
  userid int,
  PRIMARY KEY (id),
  FOREIGN KEY (userid) REFERENCES  Users(id),
  FOREIGN KEY (eventid) REFERENCES  Events(id)
);
--ALTER SEQUENCE Tickets_seq RESTART WITH 1;

