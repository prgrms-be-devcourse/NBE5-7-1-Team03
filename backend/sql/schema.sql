drop database if exists `gc_coffee`;

create database `gc_coffee`;

use gc_coffee;

show tables;

create TABLE image_files (
    image_file_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    upload_file_name VARCHAR(255),
    store_file_name VARCHAR(255)
);

create TABLE items (
    item_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    price INT,
    stock INT,
    image_file_id BIGINT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    FOREIGN KEY (image_file_id) REFERENCES image_files(image_file_id)
);

create TABLE users (
    user_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    nickname VARCHAR(50),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    role TINYINT, -- 0 = USER, 1 = ADMIN
    INDEX (email)
);

create TABLE orders (
    order_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    email VARCHAR(255),
    address TEXT,
    zipcode VARCHAR(20),
    status ENUM('RECEIVED', 'SHIPPING', 'COMPLETED', 'CANCELLED') DEFAULT 'RECEIVED',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME,
    deleted_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

create TABLE order_items (
    order_item_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    item_id BIGINT,
    quantity INT,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON delete CASCADE,
    FOREIGN KEY (item_id) REFERENCES items(item_id)
);
