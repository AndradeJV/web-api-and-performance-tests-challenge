export default new class SearchPage {
  validateSearchResults(term){
    cy.contains('Resultados encontrados para').should('be.visible')
    cy.url().should('include', '?s=')
  }

  validateHasResults(){
    cy.get('article').should('have.length.greaterThan', 0)
  }

  validateNoResults(){
    cy.contains('Lamentamos, mas nada foi encontrado').should('be.visible')
  }

  accessFirstResult(){
    cy.get('article .entry-title a').first().click()
  }
}
