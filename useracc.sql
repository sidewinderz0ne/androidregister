-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 09, 2020 at 04:57 AM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `useracc`
--

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `mobile` varchar(30) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `email` varchar(30) NOT NULL,
  `birth` varchar(30) NOT NULL,
  `gender` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `mobile`, `firstname`, `lastname`, `email`, `birth`, `gender`) VALUES
(1, '081230588877', 'qwdfqw', 'qwdfwq', 'qwd@wawqe', '', ''),
(5, '081230588866', '3wr23', '3r23r', '23r23r@fd23f', '', ''),
(6, '081230588864', 'wer', '32r', 'sdz@sdz.com', '', ''),
(7, '081230576444', '4234', '25235', 'fafasdfsdf@dqwdw.com', '', ''),
(8, '081230576883', 'Pelangi', 'Aji', 'pelangi.s.aji@gmail.com', '', ''),
(9, '081230388866', 'Pelangi', 'Aji', 'pelangi.s.aji@gmail.coma', '', ''),
(10, '081231588866', 'Pelangi', 'Aji', 'pelangi.s.aji@gmail.com12', '', ''),
(11, '0812303888661233', 'Pelangi', 'Aji', 'pelangi.s.aji@gmail.comqwe', '', ''),
(12, '081230128869', 'Pelangi', 'Aji', 'pelangi.s.aji@gmail.comqdw', '', ''),
(13, '081234388866', 'Pelangi', 'Aji', 'pelangi.s.aji@gmail.c12312om', '', ''),
(16, '081422244444', 'erqr12r', 'r21r12r', '12r12@2er32', 'na', 'na'),
(17, '081422242454', 'fqfq', 'fqwfwqf', 'qwfqwfqwf@dfqwefqwef', 'na', 'na'),
(18, '081225677754', 'r', '3432', '2345@feff', 'na', 'na'),
(19, '081230524877', 'fwefwe', 'wefwegfa', 'dfqwfqw@fdqwefefe', 'January-3-2019', 'na'),
(20, '081212388866', '4124124', '141241', '12412421@e324324', 'na', 'na');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `mobile` (`mobile`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
