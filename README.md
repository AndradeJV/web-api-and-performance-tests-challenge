# 🐕 Dog API - Automated API Tests

Suíte de testes automatizados para validar os endpoints da [Dog API](https://dog.ceo/dog-api/), garantindo que a API responda corretamente, que os dados retornados estejam no formato esperado e que a aplicação se comporte conforme o esperado em diferentes cenários.

## 📋 Índice

- [Tecnologias](#-tecnologias)
- [Pré-requisitos](#-pré-requisitos)
- [Configuração do Ambiente](#-configuração-do-ambiente)
- [Execução dos Testes](#-execução-dos-testes)
- [Relatórios](#-relatórios)
- [CI/CD](#-cicd)
- [Cobertura de Testes](#-cobertura-de-testes)
- [Estrutura do Projeto](#-estrutura-do-projeto)

## 🛠 Tecnologias

| Tecnologia | Versão | Finalidade |
|---|---|---|
| **Java** | 17 | Linguagem principal |
| **Maven** | 3.8+ | Gerenciamento de dependências e build |
| **JUnit 5** | 5.10.2 | Framework de testes |
| **REST Assured** | 5.4.0 | Testes de API REST |
| **Allure** | 2.25.0 | Relatórios de testes |
| **GitHub Actions** | — | CI/CD |

## ✅ Pré-requisitos

- **Java JDK 17** ou superior
- **Apache Maven 3.8+**

### Verificar instalação

```bash
java -version   # Deve exibir versão 17+
mvn -version    # Deve exibir versão 3.8+
```

### Instalação rápida

**macOS (Homebrew):**
```bash
brew install openjdk@17 maven
```

**Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install openjdk-17-jdk maven
```

**Windows (Chocolatey):**
```powershell
choco install openjdk17 maven
```

## ⚙ Configuração do Ambiente

1. **Clone o repositório:**
   ```bash
   git clone <url-do-repositorio>
   cd dog-api-project
   ```

2. **Instale as dependências:**
   ```bash
   mvn clean install -DskipTests
   ```

## 🚀 Execução dos Testes

### Executar todos os testes
```bash
mvn clean test
```

### Executar por tag/endpoint
```bash
# Testes de listagem de raças
mvn test -Dgroups="breeds"

# Testes de imagens por raça
mvn test -Dgroups="images"

# Testes de imagem aleatória
mvn test -Dgroups="random"
```

### Executar uma classe específica
```bash
mvn test -Dtest="ListAllBreedsTest"
mvn test -Dtest="BreedImagesTest"
mvn test -Dtest="RandomImageTest"
```

## 📊 Relatórios

### Allure Report

Após executar os testes, gere o relatório interativo:

```bash
# Gerar e abrir o relatório no navegador
mvn allure:serve

# Ou gerar o relatório estático em target/site/allure-maven-plugin/
mvn allure:report
```

O relatório inclui:
- ✅ Resultados de cada teste (sucesso/falha)
- 📝 Descrição detalhada dos cenários
- 🔴 Informações detalhadas sobre erros encontrados
- 📈 Gráficos de tendência e estatísticas
- 🏷️ Categorização por Epic, Feature e Severity

## 🔄 CI/CD

O projeto inclui pipeline **GitHub Actions** que executa automaticamente:

1. **Checkout** do código
2. **Setup** do Java 17
3. **Execução** dos testes (`mvn clean test`)
4. **Geração** do relatório Allure
5. **Deploy** do relatório para GitHub Pages

O pipeline é acionado em:
- Push nas branches `main`, `master` e `develop`
- Pull Requests para `main` e `master`
- Execução manual via `workflow_dispatch`

## 🧪 Cobertura de Testes

### `GET /breeds/list/all` — 9 testes

| # | Cenário | Severidade |
|---|---------|-----------|
| 1 | Retorna status 200 com sucesso | Blocker |
| 2 | Content-Type é application/json | Critical |
| 3 | Objeto de raças não é vazio | Critical |
| 4 | Contém raças conhecidas (labrador, bulldog, poodle) | Normal |
| 5 | Sub-raças corretas para bulldog | Normal |
| 6 | Array vazio para raças sem sub-raças | Normal |
| 7 | Tempo de resposta dentro do limite | Minor |
| 8 | Retorna pelo menos 100 raças | Normal |
| 9 | Sub-raças corretas para hound | Normal |

### `GET /breed/{breed}/images` — 11 testes

| # | Cenário | Severidade |
|---|---------|-----------|
| 1 | Retorna 200 para raça válida | Blocker |
| 2 | Array de imagens não vazio | Critical |
| 3 | URLs apontam para images.dog.ceo | Normal |
| 4 | URLs terminam com extensão de imagem válida | Normal |
| 5 | URLs contêm o nome da raça | Normal |
| 6 | Teste parametrizado com 5 raças diferentes | Critical |
| 7 | Retorna 404 para raça inválida | Critical |
| 8 | Trata caracteres especiais no nome da raça | Normal |
| 9 | Retorna erro para nome de raça vazio | Normal |
| 10 | Tempo de resposta dentro do limite | Minor |
| 11 | Content-Type é application/json | Critical |

### `GET /breeds/image/random` — 12 testes

| # | Cenário | Severidade |
|---|---------|-----------|
| 1 | Retorna status 200 com sucesso | Blocker |
| 2 | URL de imagem válida do dog.ceo | Critical |
| 3 | URL termina com extensão de imagem | Normal |
| 4 | Mensagem não é vazia ou nula | Normal |
| 5 | Content-Type é application/json | Critical |
| 6 | Tempo de resposta dentro do limite | Minor |
| 7 | Retorna exatamente N imagens ao solicitar N | Critical |
| 8 | Retorna 1 imagem ao solicitar 1 | Normal |
| 9 | Retorna máximo de 50 imagens | Normal |
| 10 | URLs válidas na resposta múltipla | Normal |
| 11 | Demonstra aleatoriedade entre requisições | Minor |

> **Total: 32 cenários de teste**

## 📁 Estrutura do Projeto

```
dog-api-project/
├── .github/
│   └── workflows/
│       └── tests.yml              # Pipeline CI/CD
├── src/
│   └── test/
│       ├── java/
│       │   └── com/dogapi/tests/
│       │       ├── BaseTest.java           # Configuração base
│       │       ├── ListAllBreedsTest.java   # Testes GET /breeds/list/all
│       │       ├── BreedImagesTest.java     # Testes GET /breed/{breed}/images
│       │       └── RandomImageTest.java     # Testes GET /breeds/image/random
│       └── resources/
│           └── allure.properties           # Configuração Allure
├── .gitignore
├── pom.xml                                 # Dependências Maven
└── README.md                               # Este arquivo
```

## 📄 Licença

Este projeto é de uso acadêmico/avaliativo.
