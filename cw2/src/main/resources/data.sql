-- Очистка таблиц (для случаев повторного запуска)
DELETE FROM attribute_value;
DELETE FROM product;
DELETE FROM attribute_type;
DELETE FROM categories;

-- Сброс автоинкремента (для H2)
ALTER TABLE categories ALTER COLUMN id RESTART WITH 1;
ALTER TABLE attribute_type ALTER COLUMN id RESTART WITH 1;
ALTER TABLE product ALTER COLUMN id RESTART WITH 1;
ALTER TABLE attribute_value ALTER COLUMN id RESTART WITH 1;

-- Категории
INSERT INTO categories (id, name, slug, description) VALUES
                                                         (1, 'Смартфоны', 'smartphones', 'Лучшие смартфоны от ведущих производителей'),
                                                         (2, 'Ноутбуки', 'notebooks', 'Мощные ноутбуки для работы и игр'),
                                                         (3, 'Камеры', 'cameras', 'Фото- и видеокамеры для профессионалов и любителей');

-- Типы характеристик (привязаны к категориям)
INSERT INTO attribute_type (id, name, category_id) VALUES
                                                        (1, 'Цвет', 1),
                                                        (2, 'ОЗУ (GB)', 1),
                                                        (3, 'Процессор', 2),
                                                        (4, 'Оперативная память (GB)', 2),
                                                        (5, 'Тип камеры', 3),
                                                        (6, 'Матрица (MP)', 3);

-- Товары (смартфоны)
INSERT INTO product (id, name, slug, description, price, category_id) VALUES
                                                                           (1, 'iPhone 14', 'iphone-14', 'Флагман Apple с OLED-дисплеем', 79900, 1),
                                                                           (2, 'Samsung Galaxy S23', 'galaxy-s23', 'Мощный Android-смартфон', 74900, 1),
                                                                           (3, 'Xiaomi 12 Pro', 'xiaomi-12-pro', 'Быстрая зарядка и отличная камера', 62900, 1);

-- Товары (ноутбуки)
INSERT INTO product (id, name, slug, description, price, category_id) VALUES
                                                                           (4, 'MacBook Air M2', 'macbook-air-m2', 'Легкий и производительный', 112900, 2),
                                                                           (5, 'ASUS ROG Zephyrus', 'asus-rog-zephyrus', 'Игровой ноутбук с RTX 4060', 149900, 2);

-- Товары (камеры)
INSERT INTO product (id, name, slug, description, price, category_id) VALUES
                                                                           (6, 'Sony A7 IV', 'sony-a7-iv', 'Полнокадровая беззеркалка', 199900, 3),
                                                                           (7, 'GoPro HERO11', 'gopro-hero11', 'Экшн-камера для съемки в движении', 39900, 3);

-- Значения характеристик для смартфонов (товары 1,2,3)
-- iPhone 14 (id=1)
INSERT INTO attribute_value (id, product_id, attribute_type_id, value) VALUES
                                                                            (1, 1, 1, 'Чёрный'),
                                                                            (2, 1, 2, '6');
-- Samsung S23 (id=2)
INSERT INTO attribute_value (id, product_id, attribute_type_id, value) VALUES
                                                                            (3, 2, 1, 'Зелёный'),
                                                                            (4, 2, 2, '8');
-- Xiaomi 12 Pro (id=3)
INSERT INTO attribute_value (id, product_id, attribute_type_id, value) VALUES
                                                                            (5, 3, 1, 'Синий'),
                                                                            (6, 3, 2, '12');

-- Значения для ноутбуков
-- MacBook Air M2
INSERT INTO attribute_value (id, product_id, attribute_type_id, value) VALUES
                                                                            (7, 4, 3, 'Apple M2'),
                                                                            (8, 4, 4, '16');
-- ASUS ROG
INSERT INTO attribute_value (id, product_id, attribute_type_id, value) VALUES
                                                                            (9, 5, 3, 'Intel Core i9-13900H'),
                                                                            (10, 5, 4, '32');

-- Значения для камер
-- Sony A7 IV
INSERT INTO attribute_value (id, product_id, attribute_type_id, value) VALUES
                                                                            (11, 6, 5, 'Зеркальная'),
                                                                            (12, 6, 6, '33');
-- GoPro
INSERT INTO attribute_value (id, product_id, attribute_type_id, value) VALUES
                                                                            (13, 7, 5, 'Экшн-камера'),
                                                                            (14, 7, 6, '27');