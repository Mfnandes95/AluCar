-- parte 5: manutencao dos veiculos
-- fecha o modelo. cada veiculo pode ter varios registros de manutencao ao longo do tempo

CREATE TABLE manutencao (
    id_manutencao INT AUTO_INCREMENT PRIMARY KEY,
    id_veiculo    INT NOT NULL,
    tipo          ENUM('preventiva','corretiva','revisao','troca_pneu','outro') NOT NULL,
    descricao     VARCHAR(255),
    data_entrada  DATE NOT NULL,
    data_saida    DATE NULL,
    custo         DECIMAL(10,2) DEFAULT 0,
    FOREIGN KEY (id_veiculo) REFERENCES veiculo(id_veiculo) ON DELETE CASCADE,
    CHECK (data_saida IS NULL OR data_saida >= data_entrada)
);

-- observacao pra quem for rodar em postgres em vez de mysql:
-- trocar AUTO_INCREMENT por GENERATED ALWAYS AS IDENTITY, ENUM por VARCHAR + CHECK,
-- e DATETIME por TIMESTAMP