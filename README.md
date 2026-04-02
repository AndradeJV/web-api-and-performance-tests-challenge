# 🧪 Testes E2E — Blog do Agi

Automação de testes end-to-end para o [Blog do Agi](https://blogdoagi.com.br) utilizando **Cypress** com o padrão **Page Object Model**.

---

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Execução dos Testes](#execução-dos-testes)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Cenários de Teste](#cenários-de-teste)
- [CI/CD](#cicd)

---

## 📖 Sobre o Projeto

Este projeto contém testes automatizados E2E para o Blog do Agi (blog.agibank.com.br), cobrindo funcionalidades de **navegação por serviços** e **busca de conteúdo**. Os testes seguem o padrão **Page Object Model (POM)** para garantir manutenibilidade e reuso de código.

---

## 🛠 Tecnologias

| Tecnologia | Versão | Finalidade |
|---|---|---|
| [Cypress](https://www.cypress.io/) | 15.13.0 | Framework de testes E2E |
| [Node.js](https://nodejs.org/) | >= 18 | Runtime JavaScript |
| [GitHub Actions](https://github.com/features/actions) | — | CI/CD |

---

## ✅ Pré-requisitos

Antes de começar, você precisa ter instalado:

- **Node.js** (versão 18 ou superior)
- **npm** (vem junto com o Node.js)
- **Git**

Para verificar se já possui:

```bash
node --version   # Deve retornar v18.x ou superior
npm --version    # Deve retornar 9.x ou superior
git --version
```

---

## 🚀 Instalação

1. **Clone o repositório:**

```bash
git clone https://github.com/AndradeJV/web-api-and-performance-tests-challenge.git
cd web-api-and-performance-tests-challenge
```

2. **Acesse a branch dos testes front-end:**

```bash
git checkout front-tests
```

3. **Instale as dependências:**

```bash
npm install
```

---

## ▶️ Execução dos Testes

### Modo Headless (terminal)

Executa todos os testes sem abrir o navegador:

```bash
npx cypress run
```

### Executar com navegador específico

```bash
npx cypress run --browser chrome
npx cypress run --browser firefox
npx cypress run --browser electron
```

### Executar um arquivo de teste específico

```bash
npx cypress run --spec "cypress/e2e/servicos.test.js"
npx cypress run --spec "cypress/e2e/busca.test.js"
```

### Modo Interativo (Cypress GUI)

Abre a interface gráfica do Cypress para acompanhar os testes visualmente:

```bash
npx cypress open
```

---

## 📁 Estrutura do Projeto

```
├── .github/
│   └── workflows/
│       └── cypress-tests.yml        # Pipeline CI/CD
├── cypress/
│   ├── e2e/
│   │   ├── busca.test.js            # Testes de busca
│   │   └── servicos.test.js         # Testes de serviços
│   ├── support/
│   │   ├── pages/
│   │   │   ├── HomePage.js          # Page Object - Página inicial
│   │   │   ├── SearchPage.js        # Page Object - Página de busca
│   │   │   └── ServicosPage.js      # Page Object - Página de serviços
│   │   ├── commands.js              # Comandos customizados do Cypress
│   │   └── e2e.js                   # Configuração global dos testes
│   └── fixtures/                    # Dados de teste (massa de dados)
├── cypress.config.js                # Configuração do Cypress
├── package.json                     # Dependências do projeto
└── README.md
```

### Page Objects

| Page Object | Responsabilidade |
|---|---|
| `HomePage.js` | Navegação pelo menu, abertura de busca, validação da homepage |
| `ServicosPage.js` | Validação de artigos, acesso a matérias específicas |
| `SearchPage.js` | Validação de resultados de busca (com e sem resultados) |

---

## 🧪 Cenários de Teste

### Serviços (`servicos.test.js`)

| # | Cenário | Descrição |
|---|---|---|
| 1 | Deve acessar a página de serviços | Valida que a URL contém `/servicos` e o título está presente |
| 2 | Deve exibir artigos na página de serviços | Valida que o título da página e os artigos são exibidos |
| 3 | Deve acessar uma matéria específica | Clica no primeiro artigo e valida que o conteúdo é carregado |

### Busca (`busca.test.js`)

| # | Cenário | Descrição |
|---|---|---|
| 1 | Deve realizar uma busca com resultados | Busca por "emprestimo" e valida que resultados aparecem |
| 2 | Deve acessar um resultado da busca | Busca e clica no primeiro resultado, validando o redirecionamento |
| 3 | Deve exibir mensagem para busca sem resultados | Busca termo inexistente e valida mensagem de "nada encontrado" |

**Total: 6 cenários de teste**

---

## ⚙️ CI/CD

O projeto possui uma pipeline configurada com **GitHub Actions** que executa automaticamente em:

- **Push** na branch `front-tests`
- **Pull Request** para a branch `front-tests`

### O que a pipeline faz:

1. Checkout do código
2. Setup do Node.js 20 com cache de dependências
3. Instalação das dependências (`npm ci`)
4. Execução dos testes com Chrome via `cypress-io/github-action@v6`
5. Upload de **screenshots** como artefato em caso de falha
6. Upload de **vídeos** como artefato (sempre)

### Status da execução

Os resultados podem ser acompanhados na aba **Actions** do repositório no GitHub.

---

## 📝 Observações

- O site `blogdoagi.com.br` redireciona para `blog.agibank.com.br`. A `baseUrl` do Cypress está configurada como `https://blogdoagi.com.br` e o redirecionamento é tratado automaticamente.
- Erros de JavaScript do próprio site (como `astra is not defined`) são ignorados via handler `Cypress.on('uncaught:exception')` no arquivo `e2e.js`, pois não são relacionados aos testes.

---

## 👤 Autor

**João Andrade** — [GitHub](https://github.com/AndradeJV)