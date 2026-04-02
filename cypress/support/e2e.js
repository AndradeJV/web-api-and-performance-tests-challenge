import './commands'

Cypress.on('uncaught:exception', (err, runnable) => {
  // Ignora erros de aplicação terceira que não são dos testes
  return false
})
