insert into users(create_date, email, gender, last_name, local, name, password, phone_number,
                  role)
values ('05-12-2023', 'admin@gmail.com', 'male',
        'Adminov', 'ky_KG', 'Admin',
        '$2a$12$21dcsWqrwZuVaBst7YU/9uLY8IhkCVuRyB68JG/QOM5I45YaAG.ry',
        '+796657646',
        'ADMIN'),
       ('05-03-2024', 'user@gmail.com', 'female',
        'User1', 'ky_KG', 'User1',
        '$2a$12$t9LPvZjsr04gTWJSB886dOS5bzpI5uW3Hm/nRnQltQ5awCqKuY3fK', '+799987689',
        'USER'),
       ('2024-07-10', 'user2@gmail.com', 'male',
        'User2', 'ky_KG', 'User2',
        '$2a$12$.0uBxKL//rIxaSOxxLWozeoD9WZj1574XZ8lfu35pJIFTJhg12eAC', '+709786756',
        'USER');

insert into payments(payment_systems, card_number, cvc, month_date, year_date, user_name)

values ('VISA', '1234567812345678', '123', 7, 2024, 'Admin'),
       ('MASTERCARD', '8765432187654321', '456', 12, 2025, 'User1'),
       ('МИР', '123412341234123', '789', 6, 2023, 'User2');

insert into brands (brand_name)
values ('Samsung'),
       ('Apple'),
       ('OnePlus'),
       ('Huawei');

insert into categories (electronic_type)
values ('Smartphones'),
       ('Laptops'),
       ('Tablets'),
       ('Accessories');


insert into sub_categories (category_id, name_of_sub_category)
values (1, 'Smartphones'),
       (1, 'Feature Phones'),
       (2, 'Ultrabooks'),
       (2, 'Gaming Laptops'),
       (3, 'Android Tablets'),
       (3, 'iOS Tablets'),
       (4, 'Smartwatches'),
       (4, 'Fitness Bands');

insert into products (create_date, discount, price, weight, brand_id, sub_category_id, color,
                      date_of_release, guarantee, memory,
                      operation_memory, operation_system, operation_system_num,
                      processor, product_name, product_status,
                      rating, screen, sim_card)
values ('2023-07-01', 1000, 69999, 150, 1, 4, 'BLACK', '2023-06-15', '24 months', 'GB_128', 'GB_8', 'ANDROID', '11.0',
        'Snapdragon 888', 'Galaxy S21', 'NEW_DEVICES', '4.5', '6.2 inches', 'Dual'),
       ('2023-06-15', 1500, 99999, 160, 1, 2, 'SILVER', '2023-05-20', '24 months', 'GB_256', 'GB_12', 'IOS', '14.5',
        'A14 Bionic', 'iPhone 12 Pro', 'SALES', '4.7', '6.1 inches', 'Single'),
       ('2023-05-30', 5, 79999, 155, 1, 1, 'WHITE', '2023-04-10', '12 months', 'GB_128', 'GB_8', 'ANDROID', '11.0',
        'Exynos 2100', 'Galaxy S21+', 'BY_REDUCING_THE_PRICE', '4.6', '6.7 inches', 'Dual'),
       ('2023-07-10', 20, 119999, 180, 1, 3, 'BLUE', '2023-07-01', '24 months', 'GB_512', 'GB_16', 'ANDROID', '12.0',
        'Snapdragon 895', 'OnePlus 9 Pro', 'BY_INCREASING_THE_PRICE', '4.8', '6.7 inches', 'Dual'),
       ('2023-06-25', 8, 89999, 165, 1, 4, 'GREEN', '2023-06-01', '24 months', 'GB_256', 'GB_12', 'ANDROID', '11.0',
        'Kirin 9000', 'Huawei P40 Pro', 'SALES', '4.6', '6.6 inches', 'Dual');

insert into baskets (id)
values (1),
       (2),
       (3),
       (4);

