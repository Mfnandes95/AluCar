-- 1. TABELAS INDEPENDENTES
CREATE TABLE IF NOT EXISTS filial (
    id_filial     SERIAL PRIMARY KEY,
    nome          VARCHAR(100) NOT NULL,
    endereco      VARCHAR(200) NOT NULL,
    cidade        VARCHAR(80) NOT NULL,
    uf            CHAR(2) NOT NULL,
    telefone      VARCHAR(20),
    email         VARCHAR(120),
    data_abertura DATE
);

CREATE TABLE IF NOT EXISTS categoria_veiculo (
    id_categoria    SERIAL PRIMARY KEY,
    descricao       VARCHAR(60) NOT NULL,
    valor_diaria    DECIMAL(10,2) NOT NULL,
    franquia_padrao DECIMAL(10,2) DEFAULT 0,
    CHECK (valor_diaria >= 0)
);

CREATE TABLE IF NOT EXISTS cliente (
    id_cliente      SERIAL PRIMARY KEY,
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
    data_cadastro   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de usuários (login/autenticação da aplicação)
CREATE TABLE IF NOT EXISTS usuarios (
    id       BIGSERIAL PRIMARY KEY,
    nome     VARCHAR(150) NOT NULL,
    email    VARCHAR(150) NOT NULL UNIQUE,
    senha    VARCHAR(255) NOT NULL,
    cpf      VARCHAR(14) UNIQUE,
    telefone VARCHAR(20),
    cnh      VARCHAR(20),
    endereco VARCHAR(255)
);

-- 2. TABELAS COM DEPENDÊNCIAS SIMPLES
CREATE TABLE IF NOT EXISTS funcionario (
    id_funcionario SERIAL PRIMARY KEY,
    nome           VARCHAR(120) NOT NULL,
    cpf            VARCHAR(14) NOT NULL UNIQUE,
    cargo          VARCHAR(60) NOT NULL,
    telefone       VARCHAR(20),
    email          VARCHAR(120) UNIQUE,
    data_admissao  DATE NOT NULL,
    id_filial      INT NOT NULL,
    FOREIGN KEY (id_filial) REFERENCES filial(id_filial)
);

CREATE TABLE IF NOT EXISTS veiculo (
    id_veiculo     SERIAL PRIMARY KEY,
    placa          VARCHAR(8) NOT NULL UNIQUE,
    marca          VARCHAR(50) NOT NULL,
    modelo         VARCHAR(60) NOT NULL,
    ano_fabricacao SMALLINT NOT NULL,
    ano_modelo     SMALLINT NOT NULL,
    cor            VARCHAR(30),
    km_atual       INT NOT NULL DEFAULT 0,
    status         VARCHAR(30) NOT NULL DEFAULT 'disponivel',
    id_categoria   INT NOT NULL,
    id_filial      INT NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES categoria_veiculo(id_categoria),
    FOREIGN KEY (id_filial) REFERENCES filial(id_filial),
    CHECK (km_atual >= 0)
);

-- 3. TABELAS COM DEPENDÊNCIAS COMPLEXAS
CREATE TABLE IF NOT EXISTS reserva (
    id_reserva SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_veiculo INT NOT NULL, -- Corrigido de id_carro para id_veiculo
    id_filial_retirada INT NOT NULL,
    id_filial_devolucao INT NOT NULL,
    data_reserva TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_inicio_prevista DATE NOT NULL,
    data_fim_prevista DATE NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'ativa',
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_veiculo) REFERENCES veiculo(id_veiculo), -- Apontando para a tabela correta
    FOREIGN KEY (id_filial_retirada) REFERENCES filial(id_filial),
    FOREIGN KEY (id_filial_devolucao) REFERENCES filial(id_filial),
    CHECK (data_fim_prevista >= data_inicio_prevista)
);

CREATE TABLE IF NOT EXISTS contrato_locacao (
    id_contrato             SERIAL PRIMARY KEY,
    id_reserva              INT NULL,
    id_cliente              INT NOT NULL,
    id_veiculo              INT NOT NULL,
    id_funcionario          INT NOT NULL,
    id_filial_retirada      INT NOT NULL,
    id_filial_devolucao     INT NOT NULL,
    data_retirada           TIMESTAMP NOT NULL,
    data_devolucao_prevista TIMESTAMP NOT NULL,
    data_devolucao_real     TIMESTAMP NULL,
    km_saida                INT NOT NULL,
    km_entrada              INT NULL,
    valor_diaria_contratada DECIMAL(10,2) NOT NULL,
    valor_total             DECIMAL(10,2) NULL,
    status                  VARCHAR(30) NOT NULL DEFAULT 'em_andamento',
    FOREIGN KEY (id_reserva) REFERENCES reserva(id_reserva) ON DELETE SET NULL,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    FOREIGN KEY (id_veiculo) REFERENCES veiculo(id_veiculo),
    FOREIGN KEY (id_funcionario) REFERENCES funcionario(id_funcionario),
    FOREIGN KEY (id_filial_retirada) REFERENCES filial(id_filial),
    FOREIGN KEY (id_filial_devolucao) REFERENCES filial(id_filial),
    CHECK (km_entrada IS NULL OR km_entrada >= km_saida)
);

-- 4. ÍNDICES
CREATE INDEX IF NOT EXISTS idx_reserva_status ON reserva(status);
CREATE INDEX IF NOT EXISTS idx_contrato_status ON contrato_locacao(status);
CREATE INDEX IF NOT EXISTS idx_contrato_cliente ON contrato_locacao(id_cliente);
CREATE INDEX IF NOT EXISTS idx_cliente_cpf ON cliente(cpf);
CREATE INDEX IF NOT EXISTS idx_veiculo_status ON veiculo(status);
CREATE INDEX IF NOT EXISTS idx_reserva_carro ON reserva(id_veiculo);

-- 5. INSERTS (protegidos contra duplicação em reinicializações)

-- filial não tem coluna UNIQUE além da PK, então usamos o nome como chave de deduplicação
INSERT INTO filial (nome, endereco, cidade, uf, telefone, email, data_abertura)
SELECT 'Acquaville', 'Tv. lago verde, 100', 'Santana', 'AP', '(96) 3000-1000', 'centro@aluguelcarros.com', '2018-03-10'
WHERE NOT EXISTS (SELECT 1 FROM filial WHERE nome = 'Acquaville');

INSERT INTO filial (nome, endereco, cidade, uf, telefone, email, data_abertura)
SELECT 'Filial Aeroporto', 'Rod. Josmar Chaves Pinto, s/n', 'Macapá', 'AP', '(96) 3000-2000', 'aeroporto@aluguelcarros.com', '2020-06-01'
WHERE NOT EXISTS (SELECT 1 FROM filial WHERE nome = 'Filial Aeroporto');

-- categoria_veiculo também não tem UNIQUE em descricao; mesma estratégia
INSERT INTO categoria_veiculo (descricao, valor_diaria, franquia_padrao)
SELECT 'Econômico', 120.00, 800.00
WHERE NOT EXISTS (SELECT 1 FROM categoria_veiculo WHERE descricao = 'Econômico');

INSERT INTO categoria_veiculo (descricao, valor_diaria, franquia_padrao)
SELECT 'Intermediário', 180.00, 1200.00
WHERE NOT EXISTS (SELECT 1 FROM categoria_veiculo WHERE descricao = 'Intermediário');

INSERT INTO categoria_veiculo (descricao, valor_diaria, franquia_padrao)
SELECT 'SUV', 260.00, 1800.00
WHERE NOT EXISTS (SELECT 1 FROM categoria_veiculo WHERE descricao = 'SUV');

INSERT INTO categoria_veiculo (descricao, valor_diaria, franquia_padrao)
SELECT 'Luxo', 450.00, 3000.00
WHERE NOT EXISTS (SELECT 1 FROM categoria_veiculo WHERE descricao = 'Luxo');

-- cliente tem UNIQUE em cpf: ON CONFLICT funciona direto
INSERT INTO cliente (nome, cpf, rg, data_nascimento, cnh_numero, cnh_validade, telefone, email, endereco, cidade, uf)
VALUES ('Carlos Pereira', '333.333.333-33', '1234567', '1990-05-12', '98765432100', '2027-05-12', '(96) 99333-3333', 'carlos.pereira@email.com', 'Rua das Flores, 45', 'Macapá', 'AP')
ON CONFLICT (cpf) DO NOTHING;

-- funcionario tem UNIQUE em cpf
INSERT INTO funcionario (nome, cpf, cargo, telefone, email, data_admissao, id_filial)
VALUES ('Ana Souza', '111.111.111-11', 'Atendente', '(96) 99111-1111', 'ana.souza@aluguelcarros.com', '2021-02-15', 1)
ON CONFLICT (cpf) DO NOTHING;

INSERT INTO funcionario (nome, cpf, cargo, telefone, email, data_admissao, id_filial)
VALUES ('Bruno Lima', '222.222.222-22', 'Gerente', '(96) 99222-2222', 'bruno.lima@aluguelcarros.com', '2019-08-20', 2)
ON CONFLICT (cpf) DO NOTHING;

-- veiculo tem UNIQUE em placa
INSERT INTO veiculo (placa, marca, modelo, ano_fabricacao, ano_modelo, cor, km_atual, status, id_categoria, id_filial)
VALUES ('ABC1D23', 'Chevrolet', 'Onix', 2023, 2024, 'Prata', 15000, 'disponivel', 1, 1)
ON CONFLICT (placa) DO NOTHING;

INSERT INTO veiculo (placa, marca, modelo, ano_fabricacao, ano_modelo, cor, km_atual, status, id_categoria, id_filial)
VALUES ('XYZ9F87', 'Jeep', 'Compass', 2022, 2023, 'Preto', 22000, 'disponivel', 3, 2)
ON CONFLICT (placa) DO NOTHING;

ALTER TABLE usuarios ADD COLUMN IF NOT EXISTS role VARCHAR(20) NOT NULL DEFAULT 'CLIENTE';