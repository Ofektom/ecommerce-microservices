-- V3 Migration Script: Insert more products into the product table for each category

-- Insert additional data into the product table for the 'Electronics' category
INSERT INTO product (id, description, name, available_quantity, price, category_id) VALUES
    (nextval('product_seq'), 'Wireless Noise-Cancelling Headphones', 'Headphones', 120, 299.99,
        (SELECT id FROM category WHERE name = 'Electronics')),
    (nextval('product_seq'), 'Gaming Laptop with 16GB RAM', 'Gaming Laptop', 80, 1499.99,
        (SELECT id FROM category WHERE name = 'Electronics')),
    (nextval('product_seq'), 'Bluetooth Speaker, waterproof', 'Bluetooth Speaker', 200, 79.99,
        (SELECT id FROM category WHERE name = 'Electronics'));

-- Insert additional data into the product table for the 'Books' category
INSERT INTO product (id, description, name, available_quantity, price, category_id) VALUES
    (nextval('product_seq'), 'Science Fiction novel', 'The Future World', 150, 19.99,
        (SELECT id FROM category WHERE name = 'Books')),
    (nextval('product_seq'), 'Historical Biography', 'Life of a Legend', 100, 25.99,
        (SELECT id FROM category WHERE name = 'Books')),
    (nextval('product_seq'), 'Self-Help Guide', 'Path to Success', 250, 12.99,
        (SELECT id FROM category WHERE name = 'Books'));

-- Insert additional data into the product table for the 'Apparel' category
INSERT INTO product (id, description, name, available_quantity, price, category_id) VALUES
    (nextval('product_seq'), 'Women''s Summer Dress, size M', 'Summer Dress', 200, 39.99,
        (SELECT id FROM category WHERE name = 'Apparel')),
    (nextval('product_seq'), 'Men''s Sneakers, size 42', 'Sneakers', 100, 89.99,
        (SELECT id FROM category WHERE name = 'Apparel')),
    (nextval('product_seq'), 'Winter Jacket, size L', 'Winter Jacket', 50, 149.99,
        (SELECT id FROM category WHERE name = 'Apparel'));

-- Insert additional data into the product table for the 'Groceries' category
INSERT INTO product (id, description, name, available_quantity, price, category_id) VALUES
    (nextval('product_seq'), 'Organic Bananas, 1kg', 'Organic Bananas', 400, 2.99,
        (SELECT id FROM category WHERE name = 'Groceries')),
    (nextval('product_seq'), 'Whole Grain Bread', 'Whole Grain Bread', 150, 3.49,
        (SELECT id FROM category WHERE name = 'Groceries')),
    (nextval('product_seq'), 'Almond Milk, 1L', 'Almond Milk', 250, 2.99,
        (SELECT id FROM category WHERE name = 'Groceries'));
