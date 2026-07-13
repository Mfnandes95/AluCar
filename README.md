# AluCar - Sistema de Aluguel de Veículos

## Colaboradores

- **Marcos Fernandes** 
- **Nicolas Alves** 
- **Hudson de Jesus** 
- **Carlos Eduardo Santos**
- **Willison Santos**

O **AluCar** é um sistema completo para locação de veículos, permitindo que usuários realizem cadastro,
escolham veículos disponíveis por data e reportem problemas durante o uso. Ideal para locadoras que buscam digitalizar e automatizar seu processo de aluguel.

---

## Requisitos Funcionais

 1. Cadastro de Usuário
O sistema deve permitir que o usuário se cadastre fornecendo as seguintes informações obrigatórias:

| Campo | Descrição | Validação |
|-------|-----------|-----------|
| Nome completo | Nome do usuário | Mínimo 3 caracteres |
| CPF | Cadastro de Pessoa Física | Validar formato e unicidade |
| Idade | Data de nascimento | Mínimo 18 anos (ou conforme legislação) |
| RG | Registro Geral | Validar formato |
| CNH | Carteira Nacional de Habilitação | Número válido e categoria (B, C, D, etc.) |

2. Autenticação
O usuário deve se registrar e acessar o sistema utilizando:
- **E-mail** (único por usuário)
- **Senha** (mínimo 6 caracteres, com criptografia)

3. Reserva de Veículos
Funcionalidades para escolha do veículo:
- Buscar veículos disponíveis **por data de retirada e devolução**
- Exibir lista de veículos compatíveis com o período selecionado
- Permitir que o usuário **decida qual veículo alugar** baseado no resultado da busca
- Validar disponibilidade em tempo real (evitar dupla reserva)

4. Reporte de Problemas
Durante ou após o uso do veículo, o usuário pode relatar:
- Descrição do problema (texto livre)
- Tipo do problema (ex: mecânico, elétrico, lataria, limpeza)
- Gravidade (baixa, média, alta, crítica)
- Data/hora do ocorrido (opcional)
- Anexar fotos (se aplicável)

> ⚠️ **Importante:** O sistema deve armazenar todos os relatos para análise da locadora.
