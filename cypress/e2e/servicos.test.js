import HomePage from "../support/pages/HomePage";
import ServicosPage from "../support/pages/ServicosPage";

describe('Serviços', () => {
  beforeEach(() => {
    HomePage.goTo()
    HomePage.accessServiceMenu()
  })

  it('Deve acessar a página de serviços', () => {
    cy.url().should('include', '/servicos')
    cy.contains('Serviços')
  })

  it('Deve exibir artigos na página de serviços', () => {
    ServicosPage.validatePageTitle()
    ServicosPage.validateArticlesAreDisplayed()
  })

  it('Deve acessar uma matéria específica', () => {
    ServicosPage.validateArticlesAreDisplayed()
    ServicosPage.getFirstArticleTitle().then(($el) => {
      const articleTitle = $el.text().trim()
      $el.click()
      cy.get('.entry-title, h1').should('contain.text', articleTitle)
    })
  })
})