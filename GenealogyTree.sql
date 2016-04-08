-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Creato il: Apr 08, 2016 alle 11:47
-- Versione del server: 10.1.9-MariaDB
-- Versione PHP: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `GenealogyTree`
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
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `datanascita` date NOT NULL,
  `id` varchar(10) NOT NULL,
  `citta` varchar(50) NOT NULL,
  `sesso` varchar(10) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(64) NOT NULL,
  `info` text NOT NULL,
  `idmadre` varchar(10) NOT NULL,
  `idpadre` int(10) NOT NULL,
  `idpartner` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`nome`, `cognome`, `datanascita`, `id`, `citta`, `sesso`, `email`, `password`, `info`, `idmadre`, `idpadre`, `idpartner`) VALUES
('matteo', 'capodicasa', '0000-00-00', '', '', '', '', '', '', '', 0, '');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
