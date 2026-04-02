import HomePage from "../support/pages/HomePage";
import SearchPage from "../support/pages/SearchPage";

describe('Busca', () => {
  beforeEach(() => {
    HomePage.goTo()
  })

  it('Deve realizar uma busca com resultados', () => {
    HomePage.searchFor('emprestimo')
    SearchPage.validateSearchResults('emprestimo')
    SearchPage.validateHasResults()
  })

  it('Deve acessar um resultado da busca', () => {
    HomePage.searchFor('emprestimo')
    SearchPage.validateHasResults()
    SearchPage.accessFirstResult()
    cy.get('.entry-title, h1').should('be.visible')
  })

  it('Deve exibir mensagem para busca sem resultados', () => {
    HomePage.searchFor('xyznonexistent12345')
    SearchPage.validateSearchResults('xyznonexistent12345')
    SearchPage.validateNoResults()
  })
})
