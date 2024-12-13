-- Создание таблицы cars
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS cars (
                                    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    make VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    year INTEGER NOT NULL CHECK (year >= 1886 AND year <= EXTRACT(YEAR FROM CURRENT_DATE)),
    price DOUBLE PRECISION NOT NULL CHECK (price > 0),
    vin VARCHAR(17) NOT NULL UNIQUE
    );
