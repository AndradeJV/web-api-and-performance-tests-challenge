export default new class HomePage {
  goTo(){
    cy.visit('/')
  }

  accessServiceMenu(){
    cy.get('#menu-item-3715 > .menu-link > .menu-text').click()
  }

  accessSuasFinancasMenu(){
    cy.get('#menu-item-3716 > .menu-link > .menu-text').click()
  }

  accessSeusBeneficiosMenu(){
    cy.get('#menu-item-3717 > .menu-link > .menu-text').click()
  }

  accessSuaSegurancaMenu(){
    cy.get('#menu-item-3718 > .menu-link > .menu-text').click()
  }

  openSearch(){
    cy.get('a.slide-search.astra-search-icon').click({ force: true })
  }

  searchFor(term){
    this.openSearch()
    cy.get('.search-field').first().clear({ force: true }).type(term + '{enter}', { force: true })
  }

  validateHomepageLoaded(){
    cy.get('.ast-site-identity').should('be.visible')
  }

  validateMenuIsVisible(){
    cy.get('#ast-hf-menu-1').should('be.visible')
  }
}