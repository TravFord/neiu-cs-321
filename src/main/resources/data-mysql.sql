DROP TABLE User;

INSERT INTO Departments (departmentAbbr, departmentName) VALUES ('CS','Computer Science');
INSERT INTO Departments (departmentAbbr, departmentName) VALUES ('ENGL','English');

INSERT INTO Courses (title, courseNumber, dept) VALUES('Discrete Structures','201','CS');
INSERT INTO Courses (title, courseNumber, dept) VALUES('Programming 1','200','CS');
INSERT INTO Courses (title, courseNumber, dept) VALUES('Programming 2','207','CS');
INSERT INTO Courses (title, courseNumber, dept) VALUES('Client Side Web Dev','300','CS');
INSERT INTO Courses (title, courseNumber, dept) VALUES('Computer Organization','301','CS');
INSERT INTO Courses (title, courseNumber, dept) VALUES('Data Structures','304','CS');
INSERT INTO Courses (title, courseNumber, dept) VALUES('Operating Systems','308','CS');
INSERT INTO Courses (title, courseNumber, dept) VALUES('Modern Database Management','315','CS');
INSERT INTO Courses (title, courseNumber, dept) VALUES('Fund Software Engineering','319','CS');
INSERT INTO Courses (title, courseNumber, dept) VALUES('Server Side Web Dev','321','CS');
INSERT INTO Courses (title, courseNumber, dept) VALUES('Intro To Design Of Algorithms','324','CS');
INSERT INTO Courses (title, courseNumber, dept) VALUES('English','101','ENGL');

--
-- ----- Programming 2 ------
-- SET @courseID = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '207' AND dept = 'CS';
--
-- SET @prereqId = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '201' AND dept = 'CS';
-- INSERT INTO Course_Prereq(courseId, prereqId) VALUES(@courseID, @prereqId);
--
-- SET @prereqId = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '200' AND dept = 'CS';
-- INSERT INTO Course_Prereq(courseId, prereqId) VALUES(@courseID, @prereqId);
--
-- ----- Client Side ------
-- SET @courseID = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '300' AND dept = 'CS';
--
-- SET @prereqId = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '201' AND dept = 'CS';
-- INSERT INTO Course_Prereq(courseId, prereqId) VALUES(@courseID, @prereqId);
--
-- SET @prereqId = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '200' AND dept = 'CS';
-- INSERT INTO Course_Prereq(courseId, prereqId) VALUES(@courseID, @prereqId);
--
-- ----- Computer Org ------
-- SET @courseID = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '301' AND dept = 'CS';
--
-- SET @prereqId = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '201' AND dept = 'CS';
-- INSERT INTO Course_Prereq(courseId, prereqId) VALUES(@courseID, @prereqId);
--
-- SET @prereqId = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '200' AND dept = 'CS';
-- INSERT INTO Course_Prereq(courseId, prereqId) VALUES(@courseID, @prereqId);
--
-- ----- Data Structures ------
-- SET @courseID = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '304' AND dept = 'CS';
--
-- SET @prereqId = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '201' AND dept = 'CS';
-- INSERT INTO Course_Prereq(courseId, prereqId) VALUES(@courseID, @prereqId);
--
-- SET @prereqId = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '207' AND dept = 'CS';
-- INSERT INTO Course_Prereq(courseId, prereqId) VALUES(@courseID, @prereqId);
--
-- ----- Operating systems ------
-- SET @courseID = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '308' AND dept = 'CS';
--
-- SET @prereqId = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '207' AND dept = 'CS';
-- INSERT INTO Course_Prereq(courseId, prereqId) VALUES(@courseID, @prereqId);
--
-- SET @prereqId = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '301' AND dept = 'CS';
-- INSERT INTO Course_Prereq(courseId, prereqId) VALUES(@courseID, @prereqId);
--
-- ----- Database ------
-- SET @courseID = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '315' AND dept = 'CS';
--
-- SET @prereqId = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '207' AND dept = 'CS';
-- INSERT INTO Course_Prereq(courseId, prereqId) VALUES(@courseID, @prereqId);
--
-- ----- Software Engineering ------
-- SET @courseID = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '319' AND dept = 'CS';
--
-- SET @prereqId = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '304' AND dept = 'CS';
-- INSERT INTO Course_Prereq(courseId, prereqId) VALUES(@courseID, @prereqId);
--
-- SET @prereqId = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '101' AND dept = 'ENGL';
-- INSERT INTO Course_Prereq(courseId, prereqId) VALUES(@courseID, @prereqId);
--
-- ----- Server Side ------
-- SET @courseID = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '321' AND dept = 'CS';
--
-- SET @prereqId = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '207' AND dept = 'CS';
-- INSERT INTO Course_Prereq(courseId, prereqId) VALUES(@courseID, @prereqId);
--
-- SET @prereqId = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '300' AND dept = 'CS';
-- INSERT INTO Course_Prereq(courseId, prereqId) VALUES(@courseID, @prereqId);
--
-- ----- Intro To Design Of Algorithms ------
-- SET @courseID = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '324' AND dept = 'CS';
--
-- SET @prereqId = SELECT MAX(courseID) FROM Courses WHERE courseNumber = '304' AND dept = 'CS';
-- INSERT INTO Course_Prereq(courseId, prereqId) VALUES(@courseID, @prereqId);

