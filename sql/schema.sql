USE closet_visualizer;

DROP TABLE IF EXISTS clothes;
DROP TABLE IF EXISTS categories;

CREATE TABLE categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(15) NOT NULL,
    display_order INT NOT NULL
);

CREATE TABLE clothes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    category_id INT NOT NULL,
    color VARCHAR(6),
    scene VARCHAR(6),
    season VARCHAR(4),
    price INT,
    size VARCHAR(5),
    rating INT,
    memo VARCHAR(400),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id)
        REFERENCES categories (id)
);
