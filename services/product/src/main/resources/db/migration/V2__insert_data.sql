-- V2 Migration Script: Insert data into the category and product tables

-- Insert data into the category table
INSERT INTO category (id, description, name) VALUES
    (nextval('category_seq'), 'Electronic gadgets and devices', 'Electronics'),
    (nextval('category_seq'), 'Books of various genres and topics', 'Books'),
    (nextval('category_seq'), 'Clothing and fashion items', 'Apparel'),
    (nextval('category_seq'), 'Food items and groceries', 'Groceries');

-- Insert data into the product table
INSERT INTO product (id, description, name, available_quantity, price, category_id) VALUES
    (nextval('product_seq'), 'Smartphone with 64GB storage', 'Smartphone', 100, 699.99,
        (SELECT id FROM category WHERE name = 'Electronics')),
    (nextval('product_seq'), '4K Ultra HD Smart TV', 'Smart TV', 50, 1199.99,
        (SELECT id FROM category WHERE name = 'Electronics')),
    (nextval('product_seq'), 'Fantasy novel', 'The Great Adventure', 200, 15.99,
        (SELECT id FROM category WHERE name = 'Books')),
    (nextval('product_seq'), 'Men''s T-Shirt, size L', 'T-Shirt', 150, 19.99,
        (SELECT id FROM category WHERE name = 'Apparel')),
    (nextval('product_seq'), 'Fresh organic apples, 1kg', 'Organic Apples', 300, 3.99,
        (SELECT id FROM category WHERE name = 'Groceries'));
