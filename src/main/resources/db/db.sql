insert into users(create_date, confirm_the_password, email, gender, last_name, local, name, password, phone_number,
                  role)
values ('05-12-2023', '$2a$12$21dcsWqrwZuVaBst7YU/9uLY8IhkCVuRyB68JG/QOM5I45YaAG.ry', 'admin@gmail.com', 'male',
        'Adminov', 'ky_KG', 'Admin',
        '$2a$12$21dcsWqrwZuVaBst7YU/9uLY8IhkCVuRyB68JG/QOM5I45YaAG.ry',
        '+796657646',
        'ADMIN'),
       ('05-03-2024', '$2a$12$t9LPvZjsr04gTWJSB886dOS5bzpI5uW3Hm/nRnQltQ5awCqKuY3fK', 'user@gmail.com', 'female',
        'User1', 'ky_KG', 'User1',
        '$2a$12$t9LPvZjsr04gTWJSB886dOS5bzpI5uW3Hm/nRnQltQ5awCqKuY3fK', '+799987689',
        'USER'),
       ('2024-07-10', '$2a$12$.0uBxKL//rIxaSOxxLWozeoD9WZj1574XZ8lfu35pJIFTJhg12eAC', 'user1@gmail.com', 'male',
        'User2', 'ky_KG', 'User2',
        '$2a$12$.0uBxKL//rIxaSOxxLWozeoD9WZj1574XZ8lfu35pJIFTJhg12eAC', '+709786756',
        'USER');

insert into payments(payment_systems, card_number, cvc, month_date, year_date, user_name)

values ('VISA', '1234567812345678', '123', 7, 2024, 'Admin'),
       ('MASTERCARD', '8765432187654321', '456', 12, 2025, 'User1'),
       ('МИР', '123412341234123', '789', 6, 2023, 'User2');

insert into brands(brand_name)
values ('Apple'),
       ('Samsung');

insert into categories (electronic_type)
values ('Mobile Phones'),
       ('Laptops'),
       ('Tablets');

insert into sub_categories(category_id, name_of_sub_category)
values (1, 'Smartphones'),
       (1, 'Feature Phones'),
       (1, 'Gaming Laptops'),
       (1, 'Ultrabooks'),
       (1, 'Android Tablets'),
       (1, 'iOS Tablets');

insert into products (product_name, product_status, category_id, memory, color, operation_memory, screen,
                      operation_system, operation_system_num, date_of_release, sim_card, processor, weight, guarantee,
                      rating, discount, price, create_date, brand_id)

values ('iPhone 13', 'AVAILABLE', 1, '128GB', 'BLACK', ' GB_16', '6.1', 'IOS', '15', '2021-09-14', 'Nano-SIM',
        'A15 Bionic', 174, '1 Year', '4.5', 10, 79900, 2021-09-14, 1),
       ('Galaxy S21', 'AVAILABLE', 1, '256GB', 'WHITE', ' GB_16', '6.2', 'ANDROID', '11', '2021-01-14', 'Nano-SIM',
        'Exynos 2100', 169, '1 Year', '4.4', 15, 69900, 2021-01-14, 2);


-- insert into basket_products(basket_id,product_id)
-- values (),
--
--
-- insert into baskets()
-- values ();
--
-- insert into deliveries (delivery_status, order_time, user_id)
-- VALUES ('PICKUP_FROM_STORE', '2024-07-10', 1),
--        ('DELIVERY_BY_COURIER', '2024-08-01', 2),
--        ('PICKUP_FROM_STORE', '2024-09-15', 3);
--
--
