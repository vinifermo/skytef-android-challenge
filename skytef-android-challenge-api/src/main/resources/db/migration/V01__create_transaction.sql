CREATE TABLE tb_transaction
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    establishment_name VARCHAR(255),
    card_number VARCHAR(255),
    transaction_value DECIMAL(10, 2)
);
