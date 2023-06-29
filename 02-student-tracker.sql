DROP DATABASE IF EXISTS web_student_tracker;
CREATE DATABASE  IF NOT EXISTS web_student_tracker;
USE web_student_tracker;

-- Table structure for table student
-- Error Code: 1192. Can't execute the given command because you have active locked tables or an active transaction

DROP TABLE IF EXISTS student;

CREATE TABLE student (
  id int NOT NULL AUTO_INCREMENT,
  first_name varchar(100) DEFAULT NULL,
  last_name varchar(100)  DEFAULT NULL,
  email varchar(100)  DEFAULT NULL,
  PRIMARY KEY (id)
);


-- Dumping data for table student

LOCK TABLES student WRITE;

INSERT INTO student (first_name, last_name, email) VALUES ('Ahmed','Public','ahmed@gmail.com'),('Micheal','David','daid@gmail.com'),('Zahra','ibrahem','zahra@gmail.com'),('Seif','Mohamed','seif@gmail.com'),('Sarah','Greock','sarah@gmail.com');

UNLOCK TABLES;

-- Dump completed --




SELECT * FROM student;