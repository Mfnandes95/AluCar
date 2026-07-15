-- parte 1: estrutura base (filial e funcionario)
-- essas duas tabelas nao dependem de nenhuma outra, por isso vem primeiro

CREATE DATABASE IF NOT EXISTS aluguel_carros;
USE aluguel_carros;

CREATE TABLE filial (
    id_filial     INT AUTO_INCREMENT PRIMARY KEY,
    nome          VARCHAR(100) NOT NULL,
    endereco      VARCHAR(200) NOT NULL,
    cidade        VARCHAR(80) NOT NULL,
    uf            CHAR(2) NOT NULL,
    telefone      VARCHAR(20),
    email         VARCHAR(120),
    data_abertura DATE
);

CREATE TABLE funcionario (
    id_funcionario INT AUTO_INCREMENT PRIMARY KEY,
    nome           VARCHAR(120) NOT NULL,
    cpf            VARCHAR(14) NOT NULL UNIQUE,
    cargo          VARCHAR(60) NOT NULL,
    telefone       VARCHAR(20),
    email          VARCHAR(120) UNIQUE,
    data_admissao  DATE NOT NULL,
    id_filial      INT NOT NULL,
    FOREIGN KEY (id_filial) REFERENCES filial(id_filial)
);

-- dados iniciais so pra ter algo pra testar depois
INSERT INTO filial (nome, endereco, cidade, uf, telefone, email, data_abertura) VALUES
('Acquaville', 'Tv. lago verde, 100', 'Santana', 'AP', '(96) 3000-1000', 'centro@aluguelcarros.com', '2018-03-10'),
('Filial Aeroporto', 'Rod. Josmar Chaves Pinto, s/n', 'Macapá', 'AP', '(96) 3000-2000', 'aeroporto@aluguelcarros.com', '2020-06-01');

INSERT INTO funcionario (nome, cpf, cargo, telefone, email, data_admissao, id_filial) VALUES
('Ana Souza', '111.111.111-11', 'Atendente', '(96) 99111-1111', 'ana.souza@aluguelcarros.com', '2021-02-15', 1),
('Bruno Lima', '222.222.222-22', 'Gerente', '(96) 99222-2222', 'bruno.lima@aluguelcarros.com', '2019-08-20', 2);