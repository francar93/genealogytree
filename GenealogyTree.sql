-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Creato il: Apr 20, 2016 alle 15:38
-- Versione del server: 10.1.9-MariaDB
-- Versione PHP: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `genealogytree`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `richieste`
--

CREATE TABLE `richieste` (
  `idreciver` varchar(10) NOT NULL,
  `idsender` varchar(10) NOT NULL,
  `relazione` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

CREATE TABLE `user` (
  `nome` varchar(50) DEFAULT NULL,
  `cognome` varchar(50) DEFAULT NULL,
  `datanascita` date DEFAULT NULL,
  `id` varchar(50) NOT NULL,
  `citta` varchar(50) DEFAULT NULL,
  `sesso` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `info` text NOT NULL,
  `idmadre` varchar(50) DEFAULT NULL,
  `idpadre` varchar(50) DEFAULT NULL,
  `idpartner` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`nome`, `cognome`, `datanascita`, `id`, `citta`, `sesso`, `email`, `password`, `info`, `idmadre`, `idpadre`, `idpartner`) VALUES
(NULL, NULL, NULL, '', NULL, NULL, 'luca@gmail.com', NULL, '', NULL, NULL, NULL),
('Matteo', 'Capodicasa', '1992-09-05', '1', 'Pianella', 'M', 'matteo.capodicasa@gmail.com', 'ciao', 'asfadass', '32QT7NNMT6', 'I8CMNU6CRC', '4'),
('Francesco', 'Caruso', '1993-12-30', '181IQZP3P6', 'Lanciano', 'M', 'francesco.caruso@gmail.com', 'ciaociao', '', NULL, NULL, NULL),
('Loredana', 'D''Alfonso', '1962-08-06', '32QT7NNMT6', 'Pescara', 'F', 'loredanadalfonso@gmail.com', 'ciao', '', NULL, NULL, NULL),
('valerio', 'd''aurelio', '1996-04-23', '4ALI5R3CZ4', 'Como', 'M', 'valerio@univaq.it', 'Ciaociao', '', NULL, NULL, NULL),
('Moira', 'Di Silvio', '1993-05-22', 'ERZQPROMB4', 'Lanciano', 'F', 'moira.disilvio@gmai.com', 'Ciaoooooo', '', NULL, NULL, NULL),
('Fausto', 'Capodicasa', '1957-05-12', 'I8CMNU6CRC', 'Pescara', 'M', 'faustocapodicasa@libero.it', 'ciao', '', NULL, NULL, NULL),
('Luca', 'di giacomo', '1993-12-30', 'IH3AV8N72C', 'lugano', 'M', 'digiacomo@gmail.com', 'Ciaociao', '', NULL, NULL, NULL),
('Chiara', 'Capodicasa', '2007-09-12', 'IROD17GPB6', 'Pescara', 'F', 'chiaracapodicasa@gmail.com', 'ciaociao', '', NULL, NULL, NULL),
('Dario', 'Tenuta', '1992-04-11', 'LDAOE60LNC', 'Crotone', 'M', 'dariotenuta@gmail.com', 'ciaociao', '', NULL, NULL, NULL),
('fikygusgfty', 'gykutfy', '1992-09-05', 'P9AZBILUN3', 'gkavsd', 'M', 'adsgfdhggs@gmail.com', 'ciaociao', '', NULL, NULL, NULL),
('Fabrizio', 'Capodicasa', '1996-07-11', 'PFPFCN0QIU', 'Pescara', 'M', 'fabriziocapodicasa@gmai.com', 'ciao', '', NULL, NULL, NULL);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD UNIQUE KEY `email` (`email`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
