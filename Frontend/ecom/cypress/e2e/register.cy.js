describe('New user registration', () => {
  beforeEach(() => {
    cy.visit('/register') // assuming the component is rendered at "/register" URL
  })

  it('should display error message if form fields are not filled', () => {
    cy.get('button').contains('Register ny bruker').click()
    cy.contains('Alle feltene må være utfylt')
  })

  it('should successfully register new user', () => {
    const username = 'testuser'
    const password = 'testpassword'
    const email = 'testuser@example.com'
    const firstName = 'Test'
    const lastName = 'User'

    cy.get('.username input').type(username)
    cy.get('.password input').type(password)
    cy.get('.email input').type(email)
    cy.get('.Forname input').type(firstName)
    cy.get('.Surname input').type(lastName)
    cy.get('button').contains('Register ny bruker').click()

    // assuming the server responds with a success message upon successful registration
    cy.contains('Ny bruker var registert')
    cy.url().should('include', '/login') // assuming the user is redirected to login page after successful registration
  })

  it('should display error message if registration fails', () => {
    // assuming the server responds with an error message upon failed registration
    cy.intercept('POST', 'http://localhost:9090/api/users/register', {
      statusCode: 500,
      body: {
        data: null,
        error: 'Something went wrong'
      }
    })
    const username = 'testuser'
    const password = 'testpassword'
    const email = 'testuser@example.com'
    const firstName = 'Test'
    const lastName = 'User'

    cy.get('.username input').type(username)
    cy.get('.password input').type(password)
    cy.get('.email input').type(email)
    cy.get('.Forname input').type(firstName)
    cy.get('.Surname input').type(lastName)
    cy.get('button').contains('Register ny bruker').click()

    cy.contains('Registering av fungerte ikke. Prøv igjen....')
  })
})
