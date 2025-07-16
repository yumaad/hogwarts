-- liquibase formatted sql
-- changeset your_github_username:create_student_name_index

CREATE INDEX IF NOT EXISTS idx_student_name ON student(name);

-- rollback DROP INDEX IF EXISTS idx_student_name;