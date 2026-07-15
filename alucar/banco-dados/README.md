# Banco de Dados - Sistema de Aluguel de Carros

Modelagem e script SQL do banco de dados da minha parte no trabalho de Engenharia de Software. O sistema cobre o fluxo de uma locadora de veículos: cadastro de filiais, funcionários, veículos e clientes, reservas, contratos de locação, pagamentos, multas, seguro e manutenção da frota.

## Estrutura do banco

O banco tem 11 tabelas, divididas em 5 arquivos SQL para facilitar o versionamento:

| Arquivo | Tabelas criadas | Depende de |
|---|---|---|
| `01_filial_funcionario.sql` | filial, funcionario | - |
| `02_veiculo_cliente.sql` | categoria_veiculo, veiculo, cliente | 01 |
| `03_reserva_contrato.sql` | reserva, contrato_locacao | 01, 02 |
| `04_pagamento_multa_seguro.sql` | pagamento, multa, seguro | 03 |
| `05_manutencao.sql` | manutencao | 02 |

Os arquivos precisam ser executados nessa ordem, porque cada um cria chaves estrangeiras apontando pra tabelas dos arquivos anteriores.

## Modelo conceitual

- Uma **filial** tem vários **funcionários** e abriga vários **veículos**.
- Um **veículo** pertence a uma **categoria** (econômico, SUV, luxo etc.), que define o valor da diária.
- Um **cliente** pode fazer várias **reservas** antes de efetivamente alugar um carro.
- A **reserva** é opcional: o cliente pode alugar direto no balcão, sem passar por ela.
- O **contrato de locação** é o centro do sistema. Ele liga cliente, veículo, funcionário responsável e as filiais de retirada/devolução (que podem ser diferentes).
- A partir de um contrato podem existir vários **pagamentos**, **multas** (atraso, avaria, infração) e um **seguro** vinculado.
- Cada **veículo** tem seu próprio histórico de **manutenções**.

## Como rodar

Testado em MySQL 8 / MariaDB.

```bash
mysql -u seu_usuario -p < 01_filial_funcionario.sql
mysql -u seu_usuario -p < 02_veiculo_cliente.sql
mysql -u seu_usuario -p < 03_reserva_contrato.sql
mysql -u seu_usuario -p < 04_pagamento_multa_seguro.sql
mysql -u seu_usuario -p < 05_manutencao.sql
```

Ou, se preferir, cola o conteúdo de cada arquivo direto no MySQL Workbench / DBeaver, na mesma ordem.

Cada arquivo já vem com alguns `INSERT` de exemplo pra dar pra testar sem precisar cadastrar tudo na mão.

### Rodando em PostgreSQL

O script foi escrito pensando em MySQL. Pra rodar em Postgres precisa ajustar:

- `AUTO_INCREMENT` → `GENERATED ALWAYS AS IDENTITY`
- `ENUM(...)` → criar o tipo antes com `CREATE TYPE` ou trocar por `VARCHAR` + `CHECK`
- `DATETIME` → `TIMESTAMP`

## Diagrama

O diagrama entidade-relacionamento está na pasta `/docs` (ou anexado na entrega do trabalho).

## Autor

Parte do banco de dados feita por Hudson - Engenharia de Software, ciência da computação 2024.