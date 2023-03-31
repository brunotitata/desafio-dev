create table transaction(
    transaction_id uuid not null,
    transaction_type integer not null,
    date timestamp with time zone not null,
    amount numeric(19,2) not null,
    tax_id varchar(255) not null,
    card varchar(255) not null,
    owner_name varchar(255) not null,
    merchant_name varchar(255) not null,
    CONSTRAINT transaction_pkey PRIMARY KEY (transaction_id)
)