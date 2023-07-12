CREATE TABLE `big_city_library`.`reservation` (
    `book_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `notified_at` TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (`book_id`, `user_id`),
    INDEX `reservation_user_id_FK_idx` (`user_id` ASC) VISIBLE,
    INDEX `reservation_book_id_FK_idx` (`book_id` ASC) VISIBLE,
    CONSTRAINT `reservation_book_id_FK` FOREIGN KEY (`book_id`) REFERENCES `big_city_library`.`book` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `reservation_user_id_FK` FOREIGN KEY (`user_id`) REFERENCES `big_city_library`.`user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_bin;
