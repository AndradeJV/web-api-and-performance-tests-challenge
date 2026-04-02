export default new class ServicosPage {
  accessSpecificService(name){
    cy.contains('a', name).first().click()
  }

  validatePageTitle(){
    cy.get('.ast-archive-description').should('contain.text', 'Serviços')
  }

  validateArticlesAreDisplayed(){
    cy.get('article').should('have.length.greaterThan', 0)
  }

  getFirstArticleTitle(){
    return cy.get('article .entry-title a').first()
  }
}