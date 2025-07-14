-- Создание таблицы "машина"
CREATE TABLE car (
    id BIGSERIAL PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    cost NUMERIC(10, 2) NOT NULL
);

-- Создание таблицы "человек"
CREATE TABLE person (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INTEGER NOT NULL,
    has_license BOOLEAN NOT NULL,
    car_id BIGINT REFERENCES car(id)
);