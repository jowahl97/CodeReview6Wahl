-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 10. Jan 2020 um 11:33
-- Server-Version: 10.4.10-MariaDB
-- PHP-Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `cr06_johannes_wahl_school`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `class`
--

CREATE TABLE `class` (
  `classID` int(11) NOT NULL,
  `className` char(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `class`
--

INSERT INTO `class` (`classID`, `className`) VALUES
(1, '1A'),
(2, '2A'),
(3, '3A'),
(4, '4A');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `student`
--

CREATE TABLE `student` (
  `studentID` int(11) NOT NULL,
  `firstName` varchar(40) DEFAULT NULL,
  `lastName` varchar(40) DEFAULT NULL,
  `email` varchar(70) DEFAULT NULL,
  `classID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `student`
--

INSERT INTO `student` (`studentID`, `firstName`, `lastName`, `email`, `classID`) VALUES
(1, 'Johannes', 'Wahl', 'jowahl97@gmail.com', 4),
(2, 'Max', 'Mustermann', 'mustermann@muster.com', 4),
(3, 'Sabine', 'Musterfrau', 'musterfrau@muster.com', 4),
(4, 'Peter', 'Muster', 'peter@muster.com', 3),
(5, 'Simon', 'Retsum', 'simonretsum@gmx.at', 3),
(6, 'Think', 'Pad', 'thinkpad@gmail.com', 3),
(7, 'Leon', 'Ovo', 'leon.ovo@gmx.at', 2),
(8, 'Loui', 'Lau', 'louilau@muster.com', 2),
(9, 'Kathi', 'Musterin', 'musterinkathi@muster.com', 2),
(10, 'Peter', 'Jackson', 'peterj@jackson.com', 1),
(11, 'Sarah', 'Musterer', 'sarahmusterer@gmail.com', 1),
(12, 'Alex', 'Samarin', 'alexsamarin@gmail.com', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `subject`
--

CREATE TABLE `subject` (
  `subjectID` int(11) NOT NULL,
  `subjectName` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `subject`
--

INSERT INTO `subject` (`subjectID`, `subjectName`) VALUES
(1, 'German'),
(2, 'English'),
(3, 'Mathematics'),
(4, 'Biology'),
(5, 'Physics');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `subject_teacher`
--

CREATE TABLE `subject_teacher` (
  `subjectID` int(11) NOT NULL,
  `teacherID` int(11) NOT NULL,
  `classID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `subject_teacher`
--

INSERT INTO `subject_teacher` (`subjectID`, `teacherID`, `classID`) VALUES
(1, 4, 3),
(2, 1, 2),
(3, 3, 1),
(4, 2, 4);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `teacher`
--

CREATE TABLE `teacher` (
  `teacherID` int(11) NOT NULL,
  `firstName` varchar(40) DEFAULT NULL,
  `lastName` varchar(40) DEFAULT NULL,
  `email` varchar(70) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `teacher`
--

INSERT INTO `teacher` (`teacherID`, `firstName`, `lastName`, `email`) VALUES
(1, 'Sigi', 'Sender', 'sigisender@teacher.com'),
(2, 'Helmut', 'Langer', 'helmut.langer@gmail.com'),
(3, 'Paula', 'Müller', 'paulamueller@gmx.at'),
(4, 'Lena', 'Lang', 'lenalang@teacher.com');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `class`
--
ALTER TABLE `class`
  ADD PRIMARY KEY (`classID`);

--
-- Indizes für die Tabelle `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`studentID`),
  ADD KEY `classID` (`classID`);

--
-- Indizes für die Tabelle `subject`
--
ALTER TABLE `subject`
  ADD PRIMARY KEY (`subjectID`);

--
-- Indizes für die Tabelle `subject_teacher`
--
ALTER TABLE `subject_teacher`
  ADD KEY `subjectID` (`subjectID`),
  ADD KEY `teacherID` (`teacherID`),
  ADD KEY `classID` (`classID`);

--
-- Indizes für die Tabelle `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`teacherID`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `class`
--
ALTER TABLE `class`
  MODIFY `classID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT für Tabelle `student`
--
ALTER TABLE `student`
  MODIFY `studentID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT für Tabelle `subject`
--
ALTER TABLE `subject`
  MODIFY `subjectID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT für Tabelle `teacher`
--
ALTER TABLE `teacher`
  MODIFY `teacherID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `student_ibfk_1` FOREIGN KEY (`classID`) REFERENCES `class` (`classID`);

--
-- Constraints der Tabelle `subject_teacher`
--
ALTER TABLE `subject_teacher`
  ADD CONSTRAINT `subject_teacher_ibfk_1` FOREIGN KEY (`subjectID`) REFERENCES `subject` (`subjectID`),
  ADD CONSTRAINT `subject_teacher_ibfk_2` FOREIGN KEY (`teacherID`) REFERENCES `teacher` (`teacherID`),
  ADD CONSTRAINT `subject_teacher_ibfk_3` FOREIGN KEY (`classID`) REFERENCES `class` (`classID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
