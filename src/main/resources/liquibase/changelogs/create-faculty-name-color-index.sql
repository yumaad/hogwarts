-- liquibase formatted sql
-- changeset your_github_username:create_faculty_name_color_index

CREATE INDEX IF NOT EXISTS idx_faculty_name_color ON faculty(name, color);

-- rollback DROP INDEX IF EXISTS idx_faculty_name_color;