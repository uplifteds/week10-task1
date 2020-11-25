drop table if exists Likes;
drop table if exists Friendships;
drop table if exists Posts;
drop table if exists Users;

--CREATE SEQUENCE Users_seq;
CREATE TABLE Users (
  id int NOT NULL,
   -- DEFAULT NEXTVAL ('Users_seq'),
  name varchar(64) DEFAULT NULL CHECK (name not SIMILAR TO '%(@|#|$)%'),
  surname varchar(64) DEFAULT NULL CHECK (name not SIMILAR TO '%(@|#|$)%'),
  birthdate date,
  PRIMARY KEY (id)
)  ;
--ALTER SEQUENCE Users_seq RESTART WITH 1;

--CREATE SEQUENCE Posts_seq;
CREATE TABLE Posts (
  id int NOT NULL,
   -- DEFAULT NEXTVAL ('Posts_seq'),
  userid int,
  text varchar(64) DEFAULT NULL,
  ts TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (userid) REFERENCES  Users(id)
)  ;
--ALTER SEQUENCE Posts_seq RESTART WITH 1;

-- CREATE SEQUENCE Likes_seq;
CREATE TABLE Likes (
  id int NOT NULL, 
  -- DEFAULT NEXTVAL ('Likes_seq'),
  userid int,
  postid int,
  ts TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (userid) REFERENCES  Users(id),
  FOREIGN KEY (postid) REFERENCES  Posts(id)
)  ;
--ALTER SEQUENCE Likes_seq RESTART WITH 1;

-- CREATE SEQUENCE Friendships_seq;
CREATE TABLE Friendships (
  id int NOT NULL, 
  -- DEFAULT NEXTVAL ('Friendships_seq'),
  userid1 int,
  userid2 int,
  ts TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (userid1) REFERENCES  Users(id),
  FOREIGN KEY (userid2) REFERENCES  Users(id)
)  ;
--ALTER SEQUENCE Friendships_seq RESTART WITH 1;