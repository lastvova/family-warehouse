CREATE TABLE IF NOT EXISTS `users` (
    `id` int AUTO_INCREMENT  PRIMARY KEY,
    `first_name` varchar(100) NOT NULL,
    `last_name` varchar(100),
    `email` varchar(100) NOT NULL,
    `password` varchar(100) NOT NULL,
    `mobile_number` varchar(20),
    `created_at` date NOT NULL,
    `created_by` varchar(20) NOT NULL,
    `updated_at` date DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
    );