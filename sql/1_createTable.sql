CREATE DATABASE s3a1_system_dev;

-- 1. 利用者 (user)
CREATE TABLE user (
    id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role INT NOT NULL DEFAULT 0 CHECK (role IN (0, 1, 2)),
    status INT NOT NULL DEFAULT 0 CHECK (status IN (0, 1)),
    borrow_count INT NOT NULL DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME,
    PRIMARY KEY (id)
);

-- 2. 著者 (author)
CREATE TABLE author (
    id INT AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME,
    PRIMARY KEY (id)
);

-- 3. 出版社 (publisher)
CREATE TABLE publisher (
    id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME,
    PRIMARY KEY (id)
);

-- 4. 分類 (category)
CREATE TABLE category (
    id INT AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    sort_order INT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME,
    PRIMARY KEY (id),
    -- 論理削除対応の複合ユニーク
    UNIQUE (name, deleted_at),
    UNIQUE (sort_order, deleted_at)
);

-- 5. 配置 (layout)
CREATE TABLE layout (
    id INT AUTO_INCREMENT,
    location VARCHAR(20) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME,
    PRIMARY KEY (id),
    -- 論理削除対応の複合ユニーク
    UNIQUE (location, deleted_at)
);

-- 6. 書籍マスタ (book_info)
CREATE TABLE book_info (
    id INT AUTO_INCREMENT,
    isbn VARCHAR(20) NOT NULL,
    title VARCHAR(255) NOT NULL,
    author_id INT NOT NULL,
    publisher_id INT NOT NULL,
    category_id INT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME,
    PRIMARY KEY (id),
    -- 論理削除対応の複合ユニーク
    UNIQUE (isbn, deleted_at),
    CONSTRAINT fk_bookinfo_author FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE RESTRICT,
    CONSTRAINT fk_bookinfo_publisher FOREIGN KEY (publisher_id) REFERENCES publisher(id) ON DELETE RESTRICT,
    CONSTRAINT fk_bookinfo_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE RESTRICT
);

-- 7. 図書 (book)
CREATE TABLE book (
    id INT AUTO_INCREMENT,
    book_info_id INT NOT NULL,
    book_number INT NOT NULL,
    layout_id INT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME,
    PRIMARY KEY (id),
    CONSTRAINT fk_book_bookinfo FOREIGN KEY (book_info_id) REFERENCES book_info(id) ON DELETE RESTRICT,
    CONSTRAINT fk_book_layout FOREIGN KEY (layout_id) REFERENCES layout(id) ON DELETE RESTRICT
);

-- 8. 貸出 (lend)
CREATE TABLE lend (
    id INT AUTO_INCREMENT,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    lend_date DATETIME NOT NULL,
    due_date DATETIME NOT NULL,
    return_date DATETIME,
    status INT NOT NULL DEFAULT 0 CHECK (status IN (0, 1, 2)),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME,
    PRIMARY KEY (id),
    CONSTRAINT fk_lend_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE RESTRICT,
    CONSTRAINT fk_lend_book FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE RESTRICT
);

-- 9. 予約 (reservation)
CREATE TABLE reservation (
    id INT AUTO_INCREMENT,
    user_id INT NOT NULL,
    book_info_id INT NOT NULL,
    book_id INT, -- ※ここだけNULLを許容（予約時は特定の本が未定なため）
    reservation_date DATETIME NOT NULL,
    expire_date DATETIME,
    status INT DEFAULT 0 CHECK (status IN (0, 1, 2, 3, 4)),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME,
    PRIMARY KEY (id),
    CONSTRAINT fk_reservation_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE RESTRICT,
    CONSTRAINT fk_reservation_bookinfo FOREIGN KEY (book_info_id) REFERENCES book_info(id) ON DELETE RESTRICT,
    CONSTRAINT fk_reservation_book FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE RESTRICT
);