create table booking
(
    id               bigint       not null auto_increment,
    booking_no       varchar(255) not null,
    booking_state    varchar(255),
    arrival_date     date,
    departure_date   date,
    arrival_time     time,
    number_persons   integer,
    guest_id         varchar(255),
    card_holder_name varchar(255),
    card_number      varchar(255),
    card_valid_thru  varchar(255),
    card_cvc         varchar(255),
    payment_type     varchar(255),
    primary key (id)
);

create table booking_selected_categories_room_counts
(
    booking_id bigint       not null,
    category   varchar(255) not null,
    number     integer,
    primary key (booking_id, category)
);

create table category
(
    id                        bigint       not null auto_increment,
    category_id               varchar(255) not null,
    name                      varchar(255),
    description               varchar(255),
    max_persons               integer,
    price_half_board_amount   decimal(19, 2),
    price_half_board_currency varchar(255),
    price_full_board_amount   decimal(19, 2),
    price_full_board_currency varchar(255),
    primary key (id)
);

create table guest
(
    id                bigint       not null auto_increment,
    guest_id          varchar(255) not null,
    organization_name varchar(255),
    discount_rate     decimal(19, 2),
    salutation        varchar(255),
    first_name        varchar(255),
    last_name         varchar(255),
    date_of_birth     date,
    address_street    varchar(255),
    address_zipcode   varchar(255),
    address_city      varchar(255),
    address_country   varchar(255),
    special_notes     varchar(255),
    primary key (id)
);

create table invoice
(
    id                            bigint       not null auto_increment,
    invoice_no                    varchar(255) not null,
    created_date                  date,
    due_date                      date,
    nights                        integer,
    sub_total_per_night_amount    decimal(19, 2),
    sub_total_per_night_currency  varchar(255),
    sub_total_amount              decimal(19, 2),
    sub_total_currency            varchar(255),
    discountrate                  decimal(19, 2),
    discount_amount               decimal(19, 2),
    discount_currency             varchar(255),
    sub_total_discounted_amount   decimal(19, 2),
    sub_total_discounted_currency varchar(255),
    tax_amount                    decimal(19, 2),
    tax_currency                  varchar(255),
    grand_total_amount            decimal(19, 2),
    grand_total_currency          varchar(255),
    stay_id                       bigint,
    primary key (id)
);

create table invoice_line
(
    id             bigint       not null auto_increment,
    type           varchar(255) not null,
    product        varchar(255),
    description    varchar(255),
    quantity       integer,
    price_amount   decimal(19, 2),
    price_currency varchar(255),
    invoice_no     bigint,
    primary key (id)
);

create table room
(
    id          bigint       not null auto_increment,
    room_number varchar(255) not null,
    room_state  varchar(255),
    category_id bigint,
    primary key (id)
);

create table room_occupancy
(
    id         bigint       not null auto_increment,
    start_date date,
    end_date   date,
    stay_id    varchar(255) not null,
    room_id    bigint       not null,
    primary key (id)
);

create table stay
(
    id               bigint       not null auto_increment,
    stay_id          varchar(255) not null,
    booking_no       varchar(255),
    stay_state       varchar(255),
    checked_in_at    timestamp,
    checked_out_at   timestamp,
    arrival_date     date,
    departure_date   date,
    arrival_time     time,
    number_persons   integer,
    guest_id         varchar(255),
    card_holder_name varchar(255),
    card_number      varchar(255),
    card_valid_thru  varchar(255),
    card_cvc         varchar(255),
    payment_type     varchar(255),
    primary key (id)
);

create table stay_selected_categories_room_counts
(
    stay_id  bigint       not null,
    category varchar(255) not null,
    number   integer,
    primary key (stay_id, category)
);


alter table invoice
    add constraint UK_ece1srr6o3r3wc7i4yi5l6alf unique (invoice_no);


alter table room
    add constraint UK_fvetq5dj3wcvmdf19bbof0os6 unique (room_number);


alter table booking_selected_categories_room_counts
    add constraint FK777unqmcmsd8jxdml5n4uyevw
        foreign key (booking_id)
            references booking (id);


alter table invoice
    add constraint FKr6fioixy78vs9nee9qfmqhlu6
        foreign key (stay_id)
            references stay (id);


alter table invoice_line
    add constraint FKp46lni561ea8itkb2r9mlr2cq
        foreign key (invoice_no)
            references invoice (id);


alter table room
    add constraint FKllkgnps1iryk3347aokxwbxxm
        foreign key (category_id)
            references category (id);


alter table room_occupancy
    add constraint FKdhkoh8wcuhhbblx5spnoxbf2i
        foreign key (room_id)
            references room (id);


alter table stay_selected_categories_room_counts
    add constraint FK3f3i5boa8fldw6l2wg9cevjpx
        foreign key (stay_id)
            references stay (id);

CREATE SEQUENCE seq_invoice START WITH 1 INCREMENT BY 1;