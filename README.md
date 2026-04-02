# 🚀 Testes de Performance — BlazeDemo

Testes de performance para o cenário de **compra de passagem aérea** no site [BlazeDemo](https://www.blazedemo.com) utilizando **Apache JMeter**.

---

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Cenário de Teste](#cenário-de-teste)
- [Critério de Aceitação](#critério-de-aceitação)
- [Pré-requisitos](#pré-requisitos)
- [Instalação do JMeter](#instalação-do-jmeter)
- [Execução dos Testes](#execução-dos-testes)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Relatório de Execução](#relatório-de-execução)
- [Análise e Conclusão](#análise-e-conclusão)
- [CI/CD](#cicd)

---

## 📖 Sobre o Projeto

Este projeto contém scripts de testes de performance (carga e pico) para o fluxo completo de compra de passagem aérea no BlazeDemo. O fluxo simulado contempla 4 etapas sequenciais que representam a jornada real do usuário.

---

## 🎯 Cenário de Teste

**Fluxo: Compra de passagem aérea — Passagem comprada com sucesso**

| Passo | Ação | Endpoint | Método |
|---|---|---|---|
| 1 | Acessar a homepage | `/` | GET |
| 2 | Buscar voos (Portland → Berlin) | `/reserve.php` | POST |
| 3 | Selecionar voo (Virgin America, Voo 43) | `/purchase.php` | POST |
| 4 | Confirmar compra com dados de pagamento | `/confirmation.php` | POST |

**Validações (Assertions):**
- Passo 1: Verifica texto "Welcome to the Simple Travel Agency!"
- Passo 2: Verifica texto "Choose This Flight"
- Passo 3: Verifica texto "Purchase Flight"
- Passo 4: Verifica texto "Thank you for your purchase today!"

---

## ✅ Critério de Aceitação

| Métrica | Meta |
|---|---|
| Vazão (Throughput) | ≥ 250 requisições/segundo |
| Tempo de Resposta (p90) | < 2 segundos |

---

## 🛠 Pré-requisitos

- **Java JDK** 8 ou superior
- **Apache JMeter** 5.6.3

### Verificar Java

```bash
java -version
```

---

## 📦 Instalação do JMeter

### Opção 1: Download manual

```bash
wget https://archive.apache.org/dist/jmeter/binaries/apache-jmeter-5.6.3.tgz
tar -xzf apache-jmeter-5.6.3.tgz
export PATH=$PWD/apache-jmeter-5.6.3/bin:$PATH
```

### Opção 2: macOS (Homebrew)

```bash
brew install jmeter
```

### Verificar instalação

```bash
jmeter --version
```

---

## ▶️ Execução dos Testes

### Teste de Carga

```bash
mkdir -p results/load
jmeter -n \
  -t scripts/load_test.jmx \
  -l results/load/load_results.jtl \
  -e -o results/load/report
```

### Teste de Pico (Spike)

```bash
mkdir -p results/spike
jmeter -n \
  -t scripts/spike_test.jmx \
  -l results/spike/spike_results.jtl \
  -e -o results/spike/report
```

### Visualizar Relatório HTML

Após a execução, abra o relatório dashboard no navegador:

```bash
open results/load/report/index.html    # Relatório de Carga
open results/spike/report/index.html   # Relatório de Pico
```

### Abrir no JMeter GUI (para edição)

```bash
jmeter -t scripts/load_test.jmx
jmeter -t scripts/spike_test.jmx
```

---

## 📁 Estrutura do Projeto

```
├── .github/
│   └── workflows/
│       └── performance-tests.yml   # Pipeline CI/CD (GitHub Actions)
├── scripts/
│   ├── load_test.jmx               # Script de teste de carga
│   └── spike_test.jmx              # Script de teste de pico
├── .gitignore
└── README.md
```

---

## 📊 Relatório de Execução

### Teste de Carga

**Configuração:**
- **Threads (usuários):** 250
- **Ramp-up:** 50 segundos
- **Duração:** 120 segundos
- **Loops:** 10

**Resultados:**

| Requisição | Amostras | Erros | Média (ms) | Mediana (ms) | p90 (ms) | p95 (ms) | p99 (ms) | Máx (ms) |
|---|---|---|---|---|---|---|---|---|
| 01 - Homepage | 2.500 | 0% | 445 | 421 | 530 | 595 | 788 | 1.011 |
| 02 - Buscar Voos | 2.500 | 0% | 310 | 286 | 410 | 468 | 571 | 770 |
| 03 - Selecionar Voo | 2.500 | 0% | 308 | 284 | 410 | 471 | 575 | 667 |
| 04 - Confirmar Compra | 2.500 | 0% | 308 | 286 | 401 | 464 | 579 | 702 |
| **TOTAL** | **10.000** | **0%** | **343** | **306** | **462** | **517** | **642** | **1.011** |

- **Throughput Total:** ~149 req/s
- **Taxa de Erro:** 0%

---

### Teste de Pico (Spike)

**Configuração:**

| Fase | Usuários | Ramp-up | Duração | Objetivo |
|---|---|---|---|---|
| Fase 1 - Base | 50 | 10s | 30s | Carga normal |
| Fase 2 - SPIKE | 500 | 5s | 30s | Pico súbito |
| Fase 3 - Recuperação | 50 | 5s | 30s | Volta ao normal |

**Resultados:**

| Requisição | Amostras | Erros | Média (ms) | Mediana (ms) | p90 (ms) | p95 (ms) | p99 (ms) | Máx (ms) |
|---|---|---|---|---|---|---|---|---|
| 01 - Homepage | 2.779 | 0% | 1.375 | 760 | 3.479 | 4.396 | 5.568 | 6.087 |
| 02 - Buscar Voos | 2.709 | 0% | 1.256 | 597 | 3.378 | 4.279 | 5.475 | 6.068 |
| 03 - Selecionar Voo | 2.623 | 0% | 1.312 | 585 | 3.605 | 4.506 | 5.488 | 5.961 |
| 04 - Confirmar Compra | 2.525 | 0% | 1.336 | 571 | 3.680 | 4.501 | 5.488 | 5.991 |
| **TOTAL** | **10.636** | **0%** | **1.320** | **640** | **3.546** | **4.412** | **5.500** | **6.087** |

- **Throughput Total:** ~159 req/s
- **Taxa de Erro:** 0%

---

## 📝 Análise e Conclusão

### Critério 1: Vazão ≥ 250 req/s

| Teste | Throughput Obtido | Status |
|---|---|---|
| Carga | ~149 req/s | ❌ Não atingido |
| Pico | ~159 req/s | ❌ Não atingido |

### Critério 2: Tempo de Resposta p90 < 2 segundos

| Teste | p90 Obtido | Status |
|---|---|---|
| Carga | 462 ms | ✅ Atendido |
| Pico | 3.546 ms | ❌ Não atingido |

### Conclusão

> **O critério de aceitação NÃO foi satisfatoriamente atendido.**

**Motivos:**

1. **Throughput abaixo da meta (250 req/s):** O servidor do BlazeDemo, por ser um ambiente de demonstração, possui limitações de capacidade. Com 250 threads concorrentes, o throughput máximo alcançado foi de ~149-159 req/s. Para atingir 250 req/s seria necessário aumentar significativamente o número de threads, porém isso causaria degradação nos tempos de resposta.

2. **Tempo de resposta no teste de carga (p90 = 462ms):** No cenário de carga constante, o p90 ficou em **462ms**, bem abaixo do limite de 2 segundos. Isso demonstra que, sob carga sustentada, o servidor responde dentro do critério.

3. **Degradação no teste de pico (p90 = 3.546ms):** No cenário de spike com 500 usuários simultâneos em 5 segundos de ramp-up, o p90 subiu para **3.546ms**, ultrapassando o limite. Isso é esperado em um pico súbito — o servidor não consegue escalar instantaneamente para absorver o pico.

4. **Zero erros em ambos os testes:** Apesar da degradação nos tempos de resposta durante o spike, nenhuma requisição falhou (0% de erros), indicando que o servidor é resiliente e não perde requisições sob pressão.

**Recomendações:**
- Para atingir 250 req/s com p90 < 2s, seria necessário aplicar técnicas de escalabilidade no servidor (load balancer, auto-scaling, cache).
- O BlazeDemo é um site de demonstração e não foi projetado para suportar alta carga real.
- Em um ambiente de produção real, essas métricas serviriam como baseline para dimensionamento de infraestrutura.

---

## ⚙️ CI/CD

O projeto possui uma pipeline configurada no **GitHub Actions** (`.github/workflows/performance-tests.yml`) que:

1. Instala o JMeter 5.6.3 automaticamente
2. Executa o **teste de carga** e gera relatório HTML
3. Executa o **teste de pico** e gera relatório HTML
4. Faz upload dos relatórios como **artefatos** do GitHub Actions

A pipeline é acionada em:
- **Push** na branch `performance-tests`
- **Pull Request** para a branch `performance-tests`

---

## 👤 Autor

**João Andrade** — [GitHub](https://github.com/AndradeJV)
