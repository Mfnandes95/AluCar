# AluCar Backend API

## 📋 Visão Geral
O backend da **AluCar** foi projetado para gerenciar a frota de veículos, usuários e o processo de reserva de aluguel. A arquitetura segue padrões de serviços RESTful, garantindo segurança via JWT (JSON Web Tokens) e persistência de dados relacional.

---

## 🏗️ Arquitetura
O sistema é organizado para separar responsabilidades:

- **Controllers**: Camada de entrada, definindo os endpoints e manipulando requisições/respostas.
- **Services**: Lógica de negócio, como validação de disponibilidade de veículos e processamento de reservas.
- **Repositories**: Interface com o banco de dados (Spring Data JPA).
- **DTOs**: Objetos de transferência de dados para isolar a estrutura interna do banco dos dados expostos à API.
- **Security**: Configuração de autenticação e autorização via Spring Security.

---

## 🔗 Endpoints Principais

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/auth/login` | Autentica o usuário e retorna um token JWT. |
| `POST` | `/usuarios` | Realiza o cadastro de um novo usuário/condutor. |
| `GET` | `/carros-disponiveis` | Retorna uma lista paginada dos carros disponíveis. |
| `GET` | `/carros/disponiveis` | Busca carros disponíveis em um período específico (parâmetros: `inicio`, `fim`). |
| `POST` | `/reservas` | Cria uma nova reserva para o usuário logado (requer token). |

---

## 🔒 Segurança
A segurança é baseada em autenticação **stateless** usando JWT:

- O token deve ser enviado no cabeçalho `Authorization` como `Bearer {token}`.
- As rotas `/auth/login` e `/usuarios` são **públicas**.
- A rota `/reservas` é **protegida** e exige autenticação válida.

---

## 🛠️ Requisitos e Tecnologias

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Security** (JWT)
- **Spring Data JPA**
- **Banco de Dados**: Configurável via `application.properties` ou `application.yml`

---

## 📦 Como Executar

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/seu-usuario/alucar-backend.git
