-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Creato il: Giu 07, 2016 alle 16:57
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

--
-- Dump dei dati per la tabella `richieste`
--

INSERT INTO `richieste` (`idreciver`, `idsender`, `relazione`) VALUES
('1', 'HO3742869Q', 'sibling');

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
  `idpartner` varchar(50) DEFAULT NULL,
  `parentele` int(5) NOT NULL DEFAULT '0',
  `refresh` int(10) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`nome`, `cognome`, `datanascita`, `id`, `citta`, `sesso`, `email`, `password`, `info`, `idmadre`, `idpadre`, `idpartner`, `parentele`, `refresh`) VALUES
('gianfranco', 'Capodicasa', '1975-05-13', '03ABLZ7CUZ', 'Pescara', 'maschio', 'gianfrancoc@gmai.com', 'ciao', '', 'V0IRT5ZRF2', 'ENRZENMGEP', NULL, 0, 1),
('Matteo', 'Capodicasa', '1992-09-05', '1', 'Pianella', 'maschio', 'matteo.capodicasa@gmail.com', 'ciao', 'asfadass', '32QT7NNMT6', 'I8CMNU6CRC', 'LB03C40QGA', 0, 0),
('Vincenzo', 'D''Alfonso', '1992-09-05', '10CF97HV7H', 'pescara', 'maschio', NULL, NULL, '', NULL, NULL, NULL, 0, 1),
('luca', 'Capodicasa', '1992-09-06', '10CF97HV8H', 'Pescara', 'maschio', 'luca@gmail.com', 'ciao', '', '32QT7NNMT6', 'I8CMNU6CRC', NULL, 0, 1),
('Francesco', 'Caruso', '1993-12-30', '181IQZP3P6', 'Lanciano', 'maschio', 'francesco.caruso@gmail.com', 'ciaociao', '', NULL, NULL, NULL, 0, 0),
('Loredana', 'D''Alfonso', '1962-08-06', '32QT7NNMT6', 'Pescara', 'femmina', 'loredanadalfonso@gmail.com', 'ciao', '', 'M2UGENT3B7', '10CF97HV7H', 'I8CMNU6CRC', 0, 1),
('valerio', 'd''aurelio', '1996-04-23', '4ALI5R3CZ4', 'Como', 'maschio', 'valerio@univaq.it', 'Ciaociao', '', NULL, NULL, NULL, 0, 0),
('gianmarco', 'Capodicasa', '1992-04-03', '7RHQMZL5Q1', 'Pescara', 'maschio', 'gmc@gmail.com', NULL, '', NULL, 'BHH02PG9AL', NULL, 0, 1),
('Francesco', 'di canio', '1992-05-04', 'BGH5HTRU6U', 'Pescara', 'maschio', 'fdc@gmail.com', 'ciao', '', NULL, NULL, NULL, 0, 0),
('mirko', 'Capodicasa', '2007-09-12', 'BHH02PG9AL', 'Pescara', 'maschio', 'mirko@gmail.com', 'ciao', '', NULL, 'TMP6OF6OHI', 'GN1V7PFGN3', 0, 0),
('Tiziano', 'Capodicasa', '1992-11-06', 'CB49FHPPGF', 'Pescara', 'maschio', 'tiziano.capodicasa@gmail.com', 'ciao', '', '32QT7NNMT6', 'I8CMNU6CRC', NULL, 0, 1),
('Gianluca', 'carmine', '1992-04-12', 'D0UER2ZI17', 'Lanciano', 'maschio', 'ciaociaociao@gmai.com', 'ciao', '', NULL, NULL, NULL, 0, 0),
('Adamo', 'Capodicasa', '1940-02-12', 'ENRZENMGEP', 'Pescara', 'maschio', 'adm@gmail.com', NULL, '', NULL, NULL, NULL, 0, 1),
('gianmarco', 'Capodicasa', '1992-04-12', 'EO2AFLHN1O', 'Pescara', 'maschio', 'gianmacapo@gmail.com', 'ciao', '', '32QT7NNMT6', 'I8CMNU6CRC', NULL, 0, 1),
('Moira', 'Di Silvio', '1993-05-22', 'ERZQPROMB4', 'Lanciano', 'femmina', 'moira.disilvio@gmai.com', 'ciao', '', NULL, NULL, NULL, 0, 0),
('sgfd', 'sgdh', '1993-12-30', 'GH3U56QNIG', 'safg', 'femmina', 'dsafgdh@gmail.com', NULL, '', '32QT7NNMT6', 'I8CMNU6CRC', NULL, 0, 1),
('giulia', 'di michele', '1992-04-12', 'GN1V7PFGN3', 'chieti', 'femmina', 'giuliadi@gmai.com', NULL, '', NULL, NULL, 'BHH02PG9AL', 0, 1),
('giuseppe', 'Capodicasa', '2007-09-12', 'HO3742869Q', 'Pescara', 'maschio', 'giusep@gmail.com', 'ciao', '', NULL, NULL, NULL, 0, 0),
('Fausto', 'Capodicasa', '1957-05-12', 'I8CMNU6CRC', 'Pescara', 'maschio', 'faustocapodicasa@libero.it', 'ciao', '', 'V0IRT5ZRF2', 'ENRZENMGEP', '32QT7NNMT6', 0, 0),
('Luca', 'di giacomo', '1993-12-30', 'IH3AV8N72C', 'lugano', 'maschio', 'digiacomo@gmail.com', 'Ciaociao', '', NULL, NULL, NULL, 0, 0),
('Chiara', 'Capodicasa', '2007-09-12', 'IROD17GPB6', 'Pescara', 'femmina', 'chiaracapodicasa@gmail.com', 'ciaociao', '', NULL, NULL, NULL, 0, 0),
('cicco', 'Capodicasa', '2007-09-12', 'IUEZGZTHDZ', 'Pescara', 'maschio', 'ciccio@gmail.com', NULL, '', '32QT7NNMT6', 'I8CMNU6CRC', NULL, 0, 1),
('Ilaria', 'Di Giacinto', '1957-05-12', 'LB03C40QGA', 'Pescara', 'femmina', 'iladigi@gmail.com', NULL, '', NULL, NULL, '1', 0, 1),
('Dario', 'Tenuta', '1992-04-11', 'LDAOE60LNC', 'Crotone', 'maschio', 'dariotenuta@gmail.com', 'ciaociao', '', NULL, NULL, NULL, 0, 0),
('giuseppina', 'passeri', '1940-08-09', 'M2UGENT3B7', 'Pescara', 'femmina', 'giusi@gmail.com', NULL, '', NULL, NULL, NULL, 0, 1),
('Luca', 'd''andrea', '1992-04-03', 'MU6CEUMZTG', 'Lanciano', 'maschio', 'erica@gmail.com', NULL, '', NULL, NULL, NULL, 0, 0),
('ciao', 'ciao', '1992-09-05', 'NPUA14TNZ0', 'ciao', 'maschio', 'ciao@gmail.com', 'ciao', '', NULL, NULL, NULL, 0, 0),
('luca', 'giuliano', '1992-09-07', 'PFCTQEHTCE', 'pescara', 'maschio', 'lugui@gmail.com', NULL, '', 'ERZQPROMB4', NULL, NULL, 0, 1),
('Fabrizio', 'Capodicasa', '1996-07-11', 'PFPFCN0QIU', 'Pescara', 'maschio', 'fabriziocapodicasa@gmai.com', 'ciao', '', NULL, NULL, NULL, 0, 0),
('Marco', 'Capodicasa', '1988-09-04', 'R0F1ZHOB86', 'Pescara', 'maschio', 'marco.capodicasa@gmail.com', 'ciao', '', '32QT7NNMT6', 'I8CMNU6CRC', NULL, 0, 1),
('marica', 'giuliano', '1992-04-11', 'R9G47ILVD1', 'Lanciano', 'femmina', 'maricagiuliano@gmail.com', NULL, '', 'ERZQPROMB4', NULL, NULL, 0, 1),
('erik', 'Capodicasa', '2016-05-03', 'RG9CZIIHIE', 'Pescara', 'maschio', 'erik@gmail.com', 'ciao', '', '32QT7NNMT6', 'I8CMNU6CRC', NULL, 0, 1),
('giulio', 'Capodicasa', '2003-04-12', 'T7P2U8RFC7', 'pescara', 'maschio', NULL, NULL, '', NULL, '1', NULL, 0, 1),
('luca', 'capodicasa', '1940-08-09', 'TMP6OF6OHI', 'Pescara', 'maschio', NULL, NULL, '', NULL, NULL, NULL, 0, 0),
('giacomo', 'Capodicasa', '1999-05-14', 'UITQ1ODLH4', 'pescara', 'maschio', 'giacomoc@gmail.com', 'ciao', '', '32QT7NNMT6', 'I8CMNU6CRC', NULL, 0, 1),
('Assunta', 'di giacomo', '1940-06-04', 'V0IRT5ZRF2', 'Pescara', 'femmina', 'asdg@gmail.com', NULL, '', NULL, NULL, NULL, 0, 1);

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
