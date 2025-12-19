create database if not exists final_assmnt;
use final_assmnt;

-- USERS table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'MANAGER', 'EMPLOYEE') NOT NULL,
    is_active BOOLEAN DEFAULT TRUE
);

-- EMPLOYEES table
CREATE TABLE employees (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    department_id INT,
    created_by INT,
    FOREIGN KEY (created_by) REFERENCES users(user_id)
);

-- PROJECTS table
CREATE TABLE projects (
    project_id INT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(100) NOT NULL,
    start_date DATE,
    end_date DATE,
    created_by INT,
    FOREIGN KEY (created_by) REFERENCES users(user_id)
);

-- EMPLOYEE_PROJECTS table
CREATE TABLE employee_projects (
    employee_id INT,
    project_id INT,
    assigned_date DATE,
    PRIMARY KEY (employee_id, project_id),
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id),
    FOREIGN KEY (project_id) REFERENCES projects(project_id)
);
