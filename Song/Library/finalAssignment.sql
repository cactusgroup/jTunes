drop database if exists finalAssignment;
create database finalAssignment;
use finalAssignment;

-- table for storing type of genre
CREATE TABLE Genre(
	genreID int NOT NULL AUTO_INCREMENT,
	genreName VARCHAR(50),
	CONSTRAINT genre_pk PRIMARY KEY(genreID)
);

-- table for storing artist name, genre
CREATE TABLE Artist(
	artistID int NOT NULL AUTO_INCREMENT,
	artistName VARCHAR(50),
	genreID int,
	CONSTRAINT artist_pk PRIMARY KEY(artistID),
	CONSTRAINT assign_fk FOREIGN KEY(genreID)
	REFERENCES Genre(genreID)
);

-- table for storing album name, year, genre and artist
CREATE TABLE Album(
	albumID int NOT NULL AUTO_INCREMENT,
	albumName VARCHAR(50),
	albumYear int,
	artistID int,
    genreID int,
	CONSTRAINT album_pk PRIMARY KEY(albumID),
	CONSTRAINT assign_FK1 FOREIGN KEY(artistid) 
	REFERENCES Artist(artistID),
	CONSTRAINT assign_FK2 FOREIGN KEY(genreID)
	REFERENCES Genre(genreID)
);

-- insert data into songs table
CREATE TABLE Songs(
	songID int NOT NULL AUTO_INCREMENT,
	songTitle VARCHAR(50),
    songPath VARCHAR(5000),
    albumID int,
    CONSTRAINT song_pk PRIMARY KEY(songID),
    CONSTRAINT assignFK1 FOREIGN KEY(albumID) 
    REFERENCES Album(albumID)
   );

-- insert data into the genre table
INSERT INTO Genre(genreName) VALUES('Hip-hop'),
('R&B'),
('Pop'),
('Rock');

-- insert data into the artist table
INSERT INTO Artist(artistName, genreID) VALUES('Prince', (SELECT genreID FROM Genre WHERE genreName = 'Pop')),
('Drake', (SELECT genreID FROM Genre WHERE genreName = 'R&B')),
('Kanye West', (SELECT genreID FROM Genre WHERE genreName = 'Hip-hop')),
('Radiohead', (SELECT genreID FROM Genre WHERE genreName = 'Rock')),
('J. Cole', (SELECT genreID FROM Genre WHERE genreName = 'Hip-hop')),
('Pharrell Williams', (SELECT genreID FROM Genre WHERE genreName = 'R&B')),
('Beyonce', (SELECT genreID FROM Genre WHERE genreName = 'R&B')),
('Lukas Graham', (SELECT genreID FROM Genre WHERE genreName = 'Pop')),
('Twenty One Pilots', (SELECT genreID FROM Genre WHERE genreName = 'Hip-hop'));

-- insert data into the album table
INSERT INTO Album(albumName, albumYear, artistID, genreID) VALUES ('1999', 1982, 
(SELECT artistID FROM Artist WHERE artistName = "Prince"), (SELECT genreID FROM Artist WHERE artistName = "Prince")),
('Dirty Mind', 1980, 
(SELECT artistID FROM Artist WHERE artistName = "Prince"), (SELECT genreID FROM Artist WHERE artistName = "Prince")),
('My Beautiful Dark Twisted Fantasy', 2010, 
(SELECT artistID FROM Artist WHERE artistName = "Kanye West"), (SELECT genreID FROM Artist WHERE artistName = "Kanye West")),
('808s & Heartbreak', 2008, 
(SELECT artistID FROM Artist WHERE artistName = "Kanye West"), (SELECT genreID FROM Artist WHERE artistName = "Kanye West")),
('The Life Of Pablo', 2016, 
(SELECT artistID FROM Artist WHERE artistName = "Kanye West"), (SELECT genreID FROM Artist WHERE artistName = "Kanye West")),
('Views', 2016, 
(SELECT artistID FROM Artist WHERE artistName = "Drake"), (SELECT genreID FROM Artist WHERE artistName = "Drake")),
('Take Care', 2011, 
(SELECT artistID FROM Artist WHERE artistName = "Drake"), (SELECT genreID FROM Artist WHERE artistName = "Drake")),
('Pablo Honey', 1993, 
(SELECT artistID FROM Artist WHERE artistName = "Radiohead"), (SELECT genreID FROM Artist WHERE artistName = "Radiohead")),
('Hail to the Thief', 2008, 
(SELECT artistID FROM Artist WHERE artistName = "Radiohead"), (SELECT genreID FROM Artist WHERE artistName = "Radiohead")),
('Born Sinner', 2013, 
(SELECT artistID FROM Artist WHERE artistName = "J. Cole"), (SELECT genreID FROM Artist WHERE artistName = "J. Cole")),
('G I R L', 2014, 
(SELECT artistID FROM Artist WHERE artistName = "Pharrell Williams"), (SELECT genreID FROM Artist WHERE artistName = "Pharrell Williams")),
('I AM... Sasha Fierce', 2008, 
(SELECT artistID FROM Artist WHERE artistName = "Beyonce"), (SELECT genreID FROM Artist WHERE artistName = "Beyonce")),
('Dangerously in Love', 2003, 
(SELECT artistID FROM Artist WHERE artistName = "Beyonce"), (SELECT genreID FROM Artist WHERE artistName = "Beyonce")),
('Lukas Graham(Blue Album)', 2015, 
(SELECT artistID FROM Artist WHERE artistName = "Lukas Graham"), (SELECT genreID FROM Artist WHERE artistName = "Lukas Graham")),
('Blurry Face', 2015, 
(SELECT artistID FROM Artist WHERE artistName = "Twenty One Pilots"), (SELECT genreID FROM Artist WHERE artistName = "Twenty One Pilots"));

-- insert data into songs
INSERT INTO Songs(songTitle,albumID) VALUES ('1999', (SELECT albumID FROM Album WHERE albumName = '1999')),
('Do It All Night', (SELECT albumID FROM Album WHERE albumName = 'Dirty Mind')),
('Blame Game', (SELECT albumID FROM Album WHERE albumName = 'My Beautiful Dark Twisted Fantasy')),
('Gorgeous',(SELECT albumID FROM Album WHERE albumName = 'My Beautiful Dark Twisted Fantasy')),
('Heartless',(SELECT albumID FROM Album WHERE albumName = '808s & Heartbreak')),
('Ultralight Beam',(SELECT albumID FROM Album WHERE albumName = 'The Life of Pablo')),
('Pop Style', (SELECT albumID FROM Album WHERE albumName = 'Views')),
('Headline', (SELECT albumID FROM Album WHERE albumName = 'Take Care')),
('Doing it Wrong', (SELECT albumID FROM Album WHERE albumName = 'Take Care')),
('Creep',(SELECT albumID FROM Album WHERE albumName = 'Pablo Honey')),
('Prove yourself',(SELECT albumID FROM Album WHERE albumName = 'Pablo Honey')),
('A Wolf at the Door',(SELECT albumID FROM Album WHERE albumName = 'Hail to the Thief')),
('Crooked Smile',(SELECT albumID FROM Album WHERE albumName = 'Born Sinner')),
('Power Trip',(SELECT albumID FROM Album WHERE albumName = 'Born Sinner')),
('Happy',(SELECT albumID FROM Album WHERE albumName = 'G I R L')),
('Gust of Winds',(SELECT albumID FROM Album WHERE albumName = 'G I R L')),
('Single Ladies',(SELECT albumID FROM Album WHERE albumName = 'I AM... Sasha Fierce')),
('Crazy in Love',(SELECT albumID FROM Album WHERE albumName = 'Dangerously in Love')),
('7 Years',(SELECT albumID FROM Album WHERE albumName = 'Lukas Graham(Blue Album)')),
('Stressed Out',(SELECT albumID FROM Album WHERE albumName = 'Blurry Face')),
('Ride',(SELECT albumID FROM Album WHERE albumName = 'Blurry Face'));


