INSERT IGNORE INTO guest (id, guest_id, organization_name, discount_rate, salutation, first_name, last_name, date_of_birth, address_street, address_zipcode, address_city, address_country, special_notes) VALUES (1, '939CD907-5060-4B24-B84D-74A33E237D52', null, null, 'DIVERSE', 'Franz', 'Beckenbauer', '1999-12-24', 'Musterstrasse 1', '6850', 'Dornbirn', 'AT', 'I don''t want the housekeeping to disturb us');
INSERT IGNORE INTO guest (id, guest_id, organization_name, discount_rate, salutation, first_name, last_name, date_of_birth, address_street, address_zipcode, address_city, address_country, special_notes) VALUES (2, '91B1FDCF-BCE6-40C5-9B48-D8C8C23421FA', 'FHV', 0.25, 'MR', 'Fritz', 'Mayer', '1979-12-24', 'Musterstr. 123', '12345', 'München', 'DE', '');

INSERT IGNORE INTO category (id, category_id, name, description, max_persons, price_half_board_amount, price_half_board_currency, price_full_board_amount, price_full_board_currency) VALUES (1, 'A8EB8559-25D1-4244-A419-9D7504922C5A', 'Honeymoon Suite DZ', 'A honeymoon suite, or a ''romance suite'' aimed at couples and newlyweds.', 2, 120.00, 'EUR', 150.00, 'EUR');
INSERT IGNORE INTO category (id, category_id, name, description, max_persons, price_half_board_amount, price_half_board_currency, price_full_board_amount, price_full_board_currency) VALUES (2, '234600F0-9173-4F80-B94D-13721AD1CF76', 'Business Casual EZ', 'A casual accommodation for business guests.', 1, 90.00, 'EUR', 120.00, 'EUR');

INSERT IGNORE INTO room (id, room_number, room_state, category_id) VALUES (1, '124', 'MAINTENANCE', 1);
INSERT IGNORE INTO room (id, room_number, room_state, category_id) VALUES (2, '123', 'CLEANING', 1);
INSERT IGNORE INTO room (id, room_number, room_state, category_id) VALUES (3, '121', 'AVAILABLE', 1);
INSERT IGNORE INTO room (id, room_number, room_state, category_id) VALUES (4, '120', 'AVAILABLE', 1);
INSERT IGNORE INTO room (id, room_number, room_state, category_id) VALUES (5, '125', 'AVAILABLE', 1);
INSERT IGNORE INTO room (id, room_number, room_state, category_id) VALUES (6, '122', 'AVAILABLE', 1);
INSERT IGNORE INTO room (id, room_number, room_state, category_id) VALUES (7, '223', 'OCCUPIED', 2);
INSERT IGNORE INTO room (id, room_number, room_state, category_id) VALUES (8, '222', 'AVAILABLE', 2);
INSERT IGNORE INTO room (id, room_number, room_state, category_id) VALUES (9, '224', 'OCCUPIED', 2);
INSERT IGNORE INTO room (id, room_number, room_state, category_id) VALUES (10, '220', 'AVAILABLE', 2);
INSERT IGNORE INTO room (id, room_number, room_state, category_id) VALUES (11, '221', 'AVAILABLE', 2);
INSERT IGNORE INTO room (id, room_number, room_state, category_id) VALUES (12, '225', 'AVAILABLE', 2);
INSERT IGNORE INTO room (id, room_number, room_state, category_id) VALUES (13, '226', 'AVAILABLE', 2);

INSERT IGNORE INTO booking (id, booking_no, booking_state, arrival_date, departure_date, arrival_time, number_persons, guest_id, card_holder_name, card_number, card_valid_thru, card_cvc, payment_type) VALUES (1, 'B0001', 'PENDING', '2022-01-01', '2022-01-06', '19:05:21', 3, '939CD907-5060-4B24-B84D-74A33E237D52', 'Franz Beckenbauer', '1234 5678 9876 5432', '11/22', '123', 'CREDITCARD');
INSERT IGNORE INTO booking (id, booking_no, booking_state, arrival_date, departure_date, arrival_time, number_persons, guest_id, card_holder_name, card_number, card_valid_thru, card_cvc, payment_type) VALUES (2, 'B0002', 'CLOSED', '2022-01-01', '2022-01-06', null, 1, '91B1FDCF-BCE6-40C5-9B48-D8C8C23421FA', 'Hans-Peter Mayer', '5432 9876 5678 1234', '12/21', '123', 'CREDITCARD');

INSERT IGNORE INTO booking_selected_categories_room_counts (booking_id, category, number) VALUES (1, 'Business Casual EZ', 2);
INSERT IGNORE INTO booking_selected_categories_room_counts (booking_id, category, number) VALUES (1, 'Honeymoon Suite DZ', 1);
INSERT IGNORE INTO booking_selected_categories_room_counts (booking_id, category, number) VALUES (2, 'Business Casual EZ', 1);

INSERT IGNORE INTO stay (id, stay_id, booking_no, stay_state, checked_in_at, checked_out_at, arrival_date, departure_date, arrival_time, number_persons, guest_id, card_holder_name, card_number, card_valid_thru, card_cvc, payment_type) VALUES (1, 'S0001', 'B0001', 'CHECKED_IN', '2022-01-01 19:05:21.709889', null, '2022-01-01', '2022-01-06', '19:05:21', 1, '91B1FDCF-BCE6-40C5-9B48-D8C8C23421FA', 'Hans-Peter Mayer', '5432 9876 5678 1234', '12/21', '123', 'CREDITCARD');

INSERT IGNORE INTO stay_selected_categories_room_counts (stay_id, category, number) VALUES (1, 'Business Casual EZ', 1);

INSERT IGNORE INTO room_occupancy (id, start_date, end_date, stay_id, room_id) VALUES (1, '2021-11-23', '2021-11-25', '0', 9);
INSERT IGNORE INTO room_occupancy (id, start_date, end_date, stay_id, room_id) VALUES (2, '2021-11-19', '2021-11-22', '0', 9);
INSERT IGNORE INTO room_occupancy (id, start_date, end_date, stay_id, room_id) VALUES (3, '2022-01-01', '2022-01-06', 'S0001', 7);
