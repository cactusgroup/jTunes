drop database if exists finalAssignment;
create database finalAssignment;
use finalAssignment;

-- table for storing type of genre;

CREATE TABLE Genre(
	genreID int NOT NULL AUTO_INCREMENT,
	genreName VARCHAR(50),
	CONSTRAINT genre_pk PRIMARY KEY(genreID)
);

-- table for storing artist name, genre;

CREATE TABLE Artist(
	artistID int NOT NULL AUTO_INCREMENT,
	artistName VARCHAR(50),
	genreID int,
	CONSTRAINT artist_pk PRIMARY KEY(artistID),
	CONSTRAINT assign_fk FOREIGN KEY(genreID)
	REFERENCES Genre(genreID)
);

-- table for storing album name, year, genre and artist;

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

-- insert data into songs table;

CREATE TABLE Songs(
	songID int NOT NULL AUTO_INCREMENT,
	songTitle VARCHAR(50),
    songPath VARCHAR(5000),
    albumID int,
    CONSTRAINT song_pk PRIMARY KEY(songID),
    CONSTRAINT assignFK1 FOREIGN KEY(albumID) 
    REFERENCES Album(albumID)
   );

-- insert data into the genre table;

INSERT INTO Genre(genreName) VALUES('Hip-hop'),
('R&B'),
('Pop'),
('Rock');

-- insert data into the artist table;

INSERT INTO Artist(artistName, genreID) VALUES('Prince', (SELECT genreID FROM Genre WHERE genreName = 'Pop')),
('Drake', (SELECT genreID FROM Genre WHERE genreName = 'R&B')),
('Kanye West', (SELECT genreID FROM Genre WHERE genreName = 'Hip-hop')),
('Radiohead', (SELECT genreID FROM Genre WHERE genreName = 'Rock'));

-- insert data into the album table;

INSERT INTO Album(albumName, albumYear, artistID, genreID) VALUES ('1999', 1982, 
(SELECT artistID FROM Artist WHERE artistName = "Prince"), (SELECT genreID FROM Artist WHERE artistName = "Prince")),
('Dirty Mind', 1980, 
(SELECT artistID FROM Artist WHERE artistName = "Prince"), (SELECT genreID FROM Artist WHERE artistName = "Prince")),
('My Beautiful Dark Twisted Fantasy', 2010, 
(SELECT artistID FROM Artist WHERE artistName = "Kanye West"), (SELECT genreID FROM Artist WHERE artistName = "Kanye West")),
('Views', 2016, 
(SELECT artistID FROM Artist WHERE artistName = "Drake"), (SELECT genreID FROM Artist WHERE artistName = "Drake")),
('Pablo Honey', 1993, 
(SELECT artistID FROM Artist WHERE artistName = "Radiohead"), (SELECT genreID FROM Artist WHERE artistName = "Radiohead"));

-- insert data into songs;

INSERT INTO Songs(songTitle,albumID) VALUES ('1999', (SELECT albumID FROM Album WHERE albumName = '1999')),
('Do It All Night', (SELECT albumID FROM Album WHERE albumName = 'Dirty Mind')),
('Blame Game', (SELECT albumID FROM Album WHERE albumName = 'My Beautiful Dark Twisted Fantasy')),
('Pop Style', (SELECT albumID FROM Album WHERE albumName = 'Views')),
('Creep',(SELECT albumID FROM Album WHERE albumName = 'Pablo Honey'));

