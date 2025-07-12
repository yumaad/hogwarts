-- Ограничение: возраст студента не может быть меньше 16 лет
ALTER TABLE student
ADD CONSTRAINT age_check CHECK (age >= 16);

-- Ограничение: имена студентов уникальны и не могут быть NULL
ALTER TABLE student
ALTER COLUMN name SET NOT NULL;

ALTER TABLE student
ADD CONSTRAINT name_unique UNIQUE (name);

-- Ограничение: пара "название-цвет" факультета уникальна
ALTER TABLE faculty
ADD CONSTRAINT name_color_unique UNIQUE (name, color);

-- Значение по умолчанию для возраста (20 лет)
ALTER TABLE student
ALTER COLUMN age SET DEFAULT 20;