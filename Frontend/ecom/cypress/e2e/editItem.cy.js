describe('Edit Item Test with Login', () => {
  let testUser;

  before(async () => {
    const email = '1@gmail.com';
    const password = '123';
    
    try {
      const response = await getJwtToken(email, password);
      testUser = {
        email: email,
        token: response.data,
      };
    } catch (error) {
      console.error('Error getting test user token:', error);
    }
  });

  beforeEach(() => {
    cy.visit('/login');
    cy.get('#email').type("1@gmail.com");
    cy.get('#password').type("123");
    cy.get('.submit > button').click();
    cy.url().should('include', '/');

    cy.wait(1000)
    cy.visit('/update-item/1652');
  });

  it('Updates the brief description', () => {
    cy.wait(3000)
    cy.get('#brief-description').type("123")
    cy.wait(10000)
    cy.get('#brief-description').should('have.value', "test123");
    cy.wait(10000)
  });

  it('Updates the full description', () => {
    cy.wait(3000)
    cy.get('#full-description').type("123")
    cy.wait(10000)
    cy.get('#full-description').should('have.value', 'test | \nLocation: ladeveien 22123');
    cy.wait(10000);
  });
  
  it('Updates the price', () => {
    cy.wait(3000)
    cy.get('#price').type("123")
    cy.get('#price').should('have.value',"123123");
  });
  
  it('Submits the form successfully', () => {
    cy.get('#submit').click();
    cy.url().should('include', '/');
  });
});
