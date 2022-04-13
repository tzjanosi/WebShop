CREATE TABLE IF NOT EXISTS `users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `email` VARCHAR(255) UNIQUE COLLATE utf8mb3_hungarian_ci NOT NULL,
  `pass` BIGINT NOT NULL,
  `name` VARCHAR(255) COLLATE utf8mb3_hungarian_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3  COLLATE utf8mb3_hungarian_ci;

CREATE TABLE IF NOT EXISTS `product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) COLLATE utf8mb3_hungarian_ci NOT NULL,
  `price` INT NOT NULL,
  `amount` INT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3  COLLATE utf8mb3_hungarian_ci;

CREATE TABLE IF NOT EXISTS `buying` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT NOT NULL,
  `date_and_time_of_buying` DATETIME,
  FOREIGN KEY (`user_id`) REFERENCES users(`id`)
 ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `bought_product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `buying_id` BIGINT NOT NULL,
  `product_id` BIGINT NOT NULL,
  `amount` INT NOT NULL,
  FOREIGN KEY(`buying_id`) REFERENCES buying(`id`),
  FOREIGN KEY(`product_id`) REFERENCES product(`id`)
 ) ENGINE=InnoDB;
