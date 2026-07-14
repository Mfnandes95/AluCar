-- parte 3: reserva e contrato_locacao
-- essa e a parte central do sistema, onde cliente + veiculo + funcionario se conectam

CREATE TABLE reserva (
    id_reserva           INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente           INT NOT NULL,
    id_veiculo           INT NOT NULL,
    data_reserva         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_inicio_prevista DATE NOT NULL,
    data_fim_prevista    DATE NOT NULL,
    status               ENUM('ativa','confirmada','cancelada','expirada') NOT NULL DEFAULT 'ativa',
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    FOREIGN KEY (id_veiculo) REFERENCES veiculo(id_veiculo),
    CHECK (data_fim_prevista >= data_inicio_prevista)
);

-- contrato pode nascer de uma reserva ou ser feito direto no balcao (por isso id_reserva aceita null)
CREATE TABLE contrato_locacao (
    id_contrato             INT AUTO_INCREMENT PRIMARY KEY,
    id_reserva              INT NULL,
    id_cliente              INT NOT NULL,
    id_veiculo              INT NOT NULL,
    id_funcionario          INT NOT NULL,
    id_filial_retirada      INT NOT NULL,
    id_filial_devolucao     INT NOT NULL, -- pode ser diferente da retirada
    data_retirada           DATETIME NOT NULL,
    data_devolucao_prevista DATETIME NOT NULL,
    data_devolucao_real     DATETIME NULL,
    km_saida                INT NOT NULL,
    km_entrada              INT NULL,
    valor_diaria_contratada DECIMAL(10,2) NOT NULL,
    valor_total             DECIMAL(10,2) NULL,
    status                  ENUM('em_andamento','finalizado','cancelado') NOT NULL DEFAULT 'em_andamento',
    FOREIGN KEY (id_reserva) REFERENCES reserva(id_reserva) ON DELETE SET NULL,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    FOREIGN KEY (id_veiculo) REFERENCES veiculo(id_veiculo),
    FOREIGN KEY (id_funcionario) REFERENCES funcionario(id_funcionario),
    FOREIGN KEY (id_filial_retirada) REFERENCES filial(id_filial),
    FOREIGN KEY (id_filial_devolucao) REFERENCES filial(id_filial),
    CHECK (km_entrada IS NULL OR km_entrada >= km_saida)
);

CREATE INDEX idx_reserva_status ON reserva(status);
CREATE INDEX idx_contrato_status ON contrato_locacao(status);
CREATE INDEX idx_contrato_cliente ON contrato_locacao(id_cliente);