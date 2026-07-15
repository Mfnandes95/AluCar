-- parte 2: veiculos e clientes
-- veiculo depende de categoria_veiculo e de filial (criada na parte 1)
-- cliente e independente, mas faz sentido colocar aqui junto

CREATE TABLE categoria_veiculo (
    id_categoria    INT AUTO_INCREMENT PRIMARY KEY,
    descricao       VARCHAR(60) NOT NULL,   -- economico, intermediario, suv, luxo...
    valor_diaria    DECIMAL(10,2) NOT NULL,
    franquia_padrao DECIMAL(10,2) DEFAULT 0,
    CHECK (valor_diaria >= 0)
);

CREATE TABLE veiculo (
    id_veiculo     INT AUTO_INCREMENT PRIMARY KEY,
    placa          VARCHAR(8) NOT NULL UNIQUE,
    marca          VARCHAR(50) NOT NULL,
    modelo         VARCHAR(60) NOT NULL,
    ano_fabricacao SMALLINT NOT NULL,
    ano_modelo     SMALLINT NOT NULL,
    cor            VARCHAR(30),
    km_atual       INT NOT NULL DEFAULT 0,
    status         ENUM('disponivel','alugado','manutencao','inativo') NOT NULL DEFAULT 'disponivel',
    id_categoria   INT NOT NULL,
    id_filial      INT NOT NULL, -- filial onde o carro esta no momento
    FOREIGN KEY (id_categoria) REFERENCES categoria_veiculo(id_categoria),
    FOREIGN KEY (id_filial) REFERENCES filial(id_filial),
    CHECK (km_atual >= 0)
);

CREATE TABLE cliente (
    id_cliente      INT AUTO_INCREMENT PRIMARY KEY,
    nome            VARCHAR(120) NOT NULL,
    cpf             VARCHAR(14) NOT NULL UNIQUE,
    rg              VARCHAR(20),
    data_nascimento DATE NOT NULL,
    cnh_numero      VARCHAR(20) NOT NULL UNIQUE,
    cnh_validade    DATE NOT NULL,
    telefone        VARCHAR(20) NOT NULL,
    email           VARCHAR(120) UNIQUE,
    endereco        VARCHAR(200),
    cidade          VARCHAR(80),
    uf              CHAR(2),
    data_cadastro   DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_cliente_cpf ON cliente(cpf);
CREATE INDEX idx_veiculo_status ON veiculo(status);

INSERT INTO categoria_veiculo (descricao, valor_diaria, franquia_padrao) VALUES
('Econômico', 120.00, 800.00),
('Intermediário', 180.00, 1200.00),
('SUV', 260.00, 1800.00),
('Luxo', 450.00, 3000.00);

INSERT INTO veiculo (placa, marca, modelo, ano_fabricacao, ano_modelo, cor, km_atual, status, id_categoria, id_filial) VALUES
('ABC1D23', 'Chevrolet', 'Onix', 2023, 2024, 'Prata', 15000, 'disponivel', 1, 1),
('XYZ9F87', 'Jeep', 'Compass', 2022, 2023, 'Preto', 22000, 'disponivel', 3, 2);

INSERT INTO cliente (nome, cpf, rg, data_nascimento, cnh_numero, cnh_validade, telefone, email, endereco, cidade, uf) VALUES
('Carlos Pereira', '333.333.333-33', '1234567', '1990-05-12', '98765432100', '2027-05-12', '(96) 99333-3333', 'carlos.pereira@email.com', 'Rua das Flores, 45', 'Macapá', 'AP');