DROP TABLE application;

CREATE TABLE application(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    path VARCHAR(512) NOT NULL,
    message VARCHAR(256) NOT NULL DEFAULT '',
    version INT NOT NULL,
    create_time DATETIME NOT NULL DEFAULT NOW()
)

INSERT INTO application(name, message, version, create_time)
VALUES('test', 'mess', 1, NOW());

CREATE TABLE workflow_instance(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    application_name VARCHAR(256) NOT NULL,
    workflow_name VARCHAR(256) NOT NULL,
    workflow_class VARCHAR(512) NOT NULL,
    workflow_params TEXT NOT NULL,
    workflow_graph TEXT NOT NULL,
    status VARCHAR(16) NOT NULL,
    start_time DATETIME,
    end_time DATETIME,
    create_time DATETIME NOT NULL DEFAULT NOW()
)

INSERT INTO workflow_instance(
    application_name,
    workflow_name,
    workflow_params,
    workflow_graph,
    status,
    start_time,
    end_time,
    create_time) values()

CREATE TABLE task_instance(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    workflow_instance_id BIGINT NOT NULL,
    task_name VARCHAR(256) NOT NULL,
    task_type VARCHAR(64) NOT NULL,
    task_class VARCHAR(256),
    task_params TEXT NOT NULL,
    status VARCHAR(16) NOT NULL,
    start_time DATETIME,
    end_time DATETIME,
    create_time DATETIME NOT NULL DEFAULT NOW()
)

CREATE TABLE user_info(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(128) NOT NULL,
    `add_time` DATETIME NOT NULL DEFAULT NOW()
);

CREATE TABLE orders(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    create_time DATETIME NOT NULL DEFAULT NOW(),
    amount DECIMAL(2) NOT NULL
);