# 🧪 Web, API & Performance Tests Challenge

Repositório contendo **três projetos de automação de testes**, cada um em sua respectiva branch. Os projetos cobrem testes de API, testes front-end E2E e testes de performance.

---

## 📋 Projetos

### 1. 🔌 Testes de API — Dog API
**Branch:** [`api-tests`](https://github.com/AndradeJV/web-api-and-performance-tests-challenge/tree/api-tests)

Automação de testes de API REST para a [Dog API](https://dog.ceo/dog-api/) utilizando **Java**, **REST Assured** e **JUnit 5**.

| Item | Detalhe |
|---|---|
| **Tecnologias** | Java 17, REST Assured, JUnit 5, Maven, Allure Reports |
| **Cenários** | Listagem de raças, imagens aleatórias, imagens por raça |
| **CI/CD** | GitHub Actions com geração de relatório Allure |

---

### 2. 🌐 Testes Front-end E2E — Blog do Agi
**Branch:** [`front-tests`](https://github.com/AndradeJV/web-api-and-performance-tests-challenge/tree/front-tests)

Automação de testes end-to-end para o [Blog do Agi](https://blogdoagi.com.br) utilizando **Cypress** e **Page Object Model**.

| Item | Detalhe |
|---|---|
| **Tecnologias** | Cypress 15, JavaScript, Page Object Model |
| **Cenários** | Navegação em Serviços (3 testes), Busca de conteúdo (3 testes) |
| **CI/CD** | GitHub Actions com Chrome headless |
| **Total de Testes** | 6 cenários — 100% passando |

---

### 3. 🚀 Testes de Performance — BlazeDemo
**Branch:** [`performance-tests`](https://github.com/AndradeJV/web-api-and-performance-tests-challenge/tree/performance-tests)

Testes de carga e pico para o fluxo de compra de passagem aérea no [BlazeDemo](https://www.blazedemo.com) utilizando **Apache JMeter**.

| Item | Detalhe |
|---|---|
| **Tecnologias** | Apache JMeter 5.6.3, Java 17 |
| **Cenários** | Teste de Carga (250 threads) e Teste de Pico/Spike (500 threads) |
| **Fluxo Testado** | Homepage → Buscar Voos → Selecionar Voo → Confirmar Compra |
| **CI/CD** | GitHub Actions com relatório HTML como artefato |

---

## 🚀 Como Acessar Cada Projeto

Cada branch possui seu próprio **README** com instruções detalhadas de instalação e execução:

```bash
# Clonar o repositório
git clone https://github.com/AndradeJV/web-api-and-performance-tests-challenge.git
cd web-api-and-performance-tests-challenge

# Acessar o projeto de testes de API
git checkout api-tests

# Acessar o projeto de testes front-end
git checkout front-tests

# Acessar o projeto de testes de performance
git checkout performance-tests
```

---

## 👤 Autor

**João Andrade** — [GitHub](https://github.com/AndradeJV)
