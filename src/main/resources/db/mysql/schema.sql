CREATE TABLE `categories`
(
    `id`   int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `changes`
(
    `id`             int(11)   NOT NULL AUTO_INCREMENT,
    `page_id`        int(11)   NOT NULL,
    `diff`           text           DEFAULT NULL,
    `error_response` text           DEFAULT NULL,
    `created_at`     timestamp NULL DEFAULT NULL,
    `updated_at`     timestamp NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 40
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `pages`
(
    `id`           int(11)      NOT NULL AUTO_INCREMENT,
    `category_id`  int(11)           DEFAULT NULL,
    `name`         varchar(255) NOT NULL,
    `url`          varchar(255) NOT NULL,
    `filter_from`  varchar(255)      DEFAULT NULL,
    `filter_to`    varchar(255)      DEFAULT NULL,
    `last_content` mediumtext        DEFAULT '',
    `is_active`    bit(1)       NOT NULL,
    `created_at`   timestamp    NULL DEFAULT NULL,
    `updated_at`   timestamp    NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

