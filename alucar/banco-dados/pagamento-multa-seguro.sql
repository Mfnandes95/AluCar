-- parte 4: tudo que depende do contrato ja fechado
-- pagamento, multa e seguro so existem depois que ha um contrato

CREATE TABLE pagamento (
    id_pagamento    INT AUTO_INCREMENT PRIMARY KEY,
    id_contrato     INT NOT NULL,
    valor           DECIMAL(10,2) NOT NULL,
    forma_pagamento ENUM('cartao_credito','cartao_debito','pix','dinheiro','boleto') NOT NULL,
    data_pagamento  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status          ENUM('pendente','aprovado','recusado','estornado') NOT NULL DEFAULT 'pendente',
    FOREIGN KEY (id_contrato) REFERENCES contrato_locacao(id_contrato) ON DELETE CASCADE,
    CHECK (valor > 0)
);

CREATE TABLE multa (
    id_multa        INT AUTO_INCREMENT PRIMARY KEY,
    id_contrato     INT NOT NULL,
    tipo            ENUM('infracao_transito','avaria','atraso','combustivel','outro') NOT NULL,
    descricao       VARCHAR(255),
    valor           DECIMAL(10,2) NOT NULL,
    data_ocorrencia DATE NOT NULL,
    status          ENUM('pendente','paga','contestada') NOT NULL DEFAULT 'pendente',
    FOREIGN KEY (id_contrato) REFERENCES contrato_locacao(id_contrato) ON DELETE CASCADE
);

-- um contrato so pode ter um seguro (por isso o UNIQUE em id_contrato)
CREATE TABLE seguro (
    id_seguro       INT AUTO_INCREMENT PRIMARY KEY,
    id_contrato     INT NOT NULL UNIQUE,
    tipo_cobertura  ENUM('basica','completa','premium') NOT NULL,
    valor_cobertura DECIMAL(10,2) NOT NULL,
    valor_franquia  DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_contrato) REFERENCES contrato_locacao(id_contrato) ON DELETE CASCADE
);