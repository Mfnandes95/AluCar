# 🏗️ Documento de Arquitetura e Design — AluCar

Este documento detalha as decisões arquiteturais, padrões de software, princípios de design e justificativas tecnológicas adotadas no desenvolvimento da landing page **AluCar**.

---

## 1. 📐 Padrão Arquitetural Escolhido

**Padrão:** *Client-Side Rendering (CSR) / Component-Based Architecture (Jamstack)*

### 💡 Justificativa
A aplicação foi estruturada seguindo uma arquitetura moderna orientada a **componentes de interface** desacoplados, processados diretamente no lado do cliente (*Client-Side*). 

Essa escolha se justifica por:
* **Desempenho e Fluidez:** Roteamento e interações ocorrem sem a necessidade de recarregar a página (*Single Page Application / Single Page Landing*), proporcionando uma UX imediata ao usuário.
* **Desacoplamento:** O front-end fica totalmente independente da camada de dados/backend. Caso a AluCar integre uma API REST de reservas no futuro, a interface já está pronta para consumir esses serviços via requisições assíncronas (`fetch`/`axios`).

---

## 2. 📊 Diagrama de Arquitetura

O diagrama abaixo ilustra a organização dos módulos da aplicação e o fluxo de dados na navegação do usuário:

```mermaid
graph TD
    A[Navegador do Cliente] -->|Carrega Bundle| B[Vite / HTML5]
    
    subgraph Interface UI [Camada de Apresentação - Tailwind CSS]
        B --> C[Header & Navbar]
        B --> D[Hero Section]
        B --> E[Seção Frota]
        B --> F[Seção Retirada & FAQ]
        B --> G[Seção Suporte & Footer]
    end

    subgraph Lógica & Comportamento [Camada de Scripts]
        C -->|Smooth Scroll & Menu Mobile| H[main.js]
        E -->|Filtros / Interatividade| H
    end