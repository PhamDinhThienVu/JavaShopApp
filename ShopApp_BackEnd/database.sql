CREATE DATABASE ShopApp;
USE ShopApp;

CREATE TABLE users(
    id INT PRIMARY KEY AUTO_INCREMENT,
    fullname VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(10) NOT NULL,
    address VARCHAR(200) DEFAULT '',
    password VARCHAR(100) NOT NULL DEFAULT '',
    created_at DATETIME,
    updated_at DATETIME,
    is_active TINYINT(1) DEFAULT 1,
    date_of_birth DATE,
    facebook_account_id INT DEFAULT 0,
    google_account_id INT DEFAULT 0
);

--Role cua user
ALTER TABLE users ADD COLUMN role_id INT;

CREATE TABLE roles(
    id INT PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

ALTER TABLE users ADD FOREIGN KEY (role_id) REFERENCES roles(id);

--Phien dang nhap nguoi dung
CREATE TABLE tokens(
    id INT PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(255) UNIQUE NOT NULL,
    token_type VARCHAR(50) NOT NULL,
    expiration_date DATETIME,
    revoked TINYINT(1) NOT NULL,
    expired TINYINT(1) NOT NULL,
    user_id int, 
    FOREIGN KEY (user_id) REFERENCES users(id)
);

--Dang nhap tu facebook va google
CREATE TABLE social_accounts(
    id INT PRIMARY KEY AUTO_INCREMENT,
    provider VARCHAR(20) NOT NULL COMMENT 'Provider social network',
    provider_id VARCHAR(50) NOT NULL,
    email VARCHAR(150) NOT NULL COMMENT 'Email tai khoan',
    name VARCHAR(100) NOT NULL COMMENT 'UserName tai khoan',
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- The loai san pham
CREATE TABLE categories(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL COMMENT 'VD: Ten danh muc, vd: do dien tu'
);
-- San pham
CREATE TABLE products(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(350) COMMENT 'Ten san pham',
    price FLOAT NOT NULL CHECK(price >= 0), 
    thumbnail VARCHAR(300) DEFAULT '',
    `description` LONGTEXT DEFAULT '',
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES categories(id)
)

--Oders
CREATE TABLE oders(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id int,
    FOREIGN KEY (user_id) REFERENCES users(id),
    fullname VARCHAR(100),
    email VARCHAR(100),
    phone_number VARCHAR(20) NOT NULL,
    address VARCHAR(200) NOT NULL,
    note VARCHAR(100) DEFAULT '',
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    `status` VARCHAR(20),
    total_money FLOAT CHECK(total_money >= 0)
);
ALTER TABLE oders ADD COLUMN `shipping_method` VARCHAR(100);
ALTER TABLE oders ADD COLUMN `shipping_address` VARCHAR(100);
ALTER TABLE oders ADD COLUMN `shipping_date` VARCHAR(100);
ALTER TABLE oders ADD COLUMN `tracking_number` VARCHAR(100);
ALTER TABLE oders ADD COLUMN `payment_method` VARCHAR(100);
--Xoa 1 don hang => xoa mem => them truong active
ALTER TABLE oders ADD COLUMN active TINYINT(1);
--Trang thai don hang chi duoc cac gia tri cu the sau
ALTER TABLE oders
MODIFY COLUMN status ENUM('pending','processing', 'shipped', 'delivered', 'cancelled')
COMMENT "Cac trang thai cua don hang";




CREATE TABLE order_details(
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    product_id INT,
    price FLOAT CHECK(price >= 0),
    `number_of_products` INT CHECK(`number_of_products` > 0),
    `total_money` FLOAT CHECK(`total_money` >= 0),
    color VARCHAR(20) DEFAULT ''
);

ALTER TABLE order_details ADD FOREIGN KEY (order_id) REFERENCES oders(id);
ALTER TABLE order_details ADD FOREIGN KEY (product_id) REFERENCES products(id);